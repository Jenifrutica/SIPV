package com.tienda.sipv.service.impl;

import com.tienda.sipv.dto.ObraDTO;
import com.tienda.sipv.dto.RecepcionDTO;
import com.tienda.sipv.exception.OperacionInvalidaException;
import com.tienda.sipv.exception.RecursoNoEncontradoException;
import com.tienda.sipv.model.Ejemplar;
import com.tienda.sipv.model.Obra;
import com.tienda.sipv.model.Recepcion;
import com.tienda.sipv.model.enums.EstadoEjemplar;
import com.tienda.sipv.repository.EjemplarRepository;
import com.tienda.sipv.repository.ObraRepository;
import com.tienda.sipv.repository.RecepcionRepository;
import com.tienda.sipv.service.IInventarioService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implementacion del modulo de Inventario.
 */
@Service
public class InventarioServiceImpl implements IInventarioService {

    private final ObraRepository obraRepository;
    private final EjemplarRepository ejemplarRepository;
    private final RecepcionRepository recepcionRepository;

    public InventarioServiceImpl(ObraRepository obraRepository,
                                 EjemplarRepository ejemplarRepository,
                                 RecepcionRepository recepcionRepository) {
        this.obraRepository = obraRepository;
        this.ejemplarRepository = ejemplarRepository;
        this.recepcionRepository = recepcionRepository;
    }

    @Override
    public void registrarObra(ObraDTO dto) {
        // Evita ISBN duplicados.
        if (dto.getIsbn() != null && !dto.getIsbn().isBlank()
                && obraRepository.findByIsbn(dto.getIsbn()).isPresent()) {
            throw new OperacionInvalidaException("Ya existe una obra con el ISBN " + dto.getIsbn());
        }
        Obra obra = new Obra(UUID.randomUUID().toString(), dto.getTitulo(), dto.getMangaka(),
                dto.getEditorial(), dto.getDemografia(), dto.getIsbn(), dto.getPrecioListaNuevo());
        Obra guardada = obraRepository.save(obra);
        // Devuelve el id generado a traves del propio dto.
        dto.setId(guardada.getId());
    }

    @Override
    public List<ObraDTO> buscarPorTitulo(String titulo) {
        List<Obra> obras = (titulo == null || titulo.isBlank())
                ? obraRepository.findAll()
                : obraRepository.findByTituloContainingIgnoreCase(titulo);
        List<ObraDTO> resultado = new ArrayList<>();
        for (Obra obra : obras) {
            resultado.add(aDTO(obra));
        }
        return resultado;
    }

    @Override
    public ObraDTO obtenerObra(String id) {
        Obra obra = obraRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la obra " + id));
        return aDTO(obra);
    }

    @Override
    public ObraDTO actualizarObra(String id, ObraDTO datos) {
        Obra obra = obraRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la obra " + id));

        // Actualizacion parcial (PATCH): solo cambia los campos enviados.
        if (datos.getTitulo() != null && !datos.getTitulo().isBlank()) {
            obra.setTitulo(datos.getTitulo());
        }
        if (datos.getMangaka() != null) {
            obra.setMangaka(datos.getMangaka());
        }
        if (datos.getEditorial() != null) {
            obra.setEditorial(datos.getEditorial());
        }
        if (datos.getDemografia() != null) {
            obra.setDemografia(datos.getDemografia());
        }
        if (datos.getIsbn() != null) {
            // Evita dejar dos obras con el mismo ISBN.
            obraRepository.findByIsbn(datos.getIsbn()).ifPresent(otra -> {
                if (!otra.getId().equals(id)) {
                    throw new OperacionInvalidaException("Ya existe una obra con el ISBN " + datos.getIsbn());
                }
            });
            obra.setIsbn(datos.getIsbn());
        }
        if (datos.getPrecioListaNuevo() > 0) {
            obra.setPrecioListaNuevo(datos.getPrecioListaNuevo());
        }
        return aDTO(obraRepository.save(obra));
    }

    @Override
    public void eliminarObra(String id) {
        Obra obra = obraRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la obra " + id));
        if (ejemplarRepository.countByObraId(id) > 0) {
            throw new OperacionInvalidaException(
                    "No se puede eliminar la obra " + id + " porque tiene ejemplares asociados");
        }
        obraRepository.delete(obra);
    }

    @Override
    public List<Ejemplar> listarEjemplares() {
        return ejemplarRepository.findAll();
    }

    @Override
    public Ejemplar obtenerEjemplar(String sku) {
        return ejemplarRepository.findById(sku)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el ejemplar " + sku));
    }

    @Override
    public List<String> ingresarLote(RecepcionDTO dto) {
        Obra obra = obraRepository.findById(dto.getObraId())
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la obra " + dto.getObraId()));

        // Registra la recepcion (lote/caja).
        Recepcion recepcion = new Recepcion(UUID.randomUUID().toString(), obra.getId(),
                dto.getCantidad(), dto.getLoteCaja(), dto.getCondicion());
        recepcionRepository.save(recepcion);

        // Crea un ejemplar por cada unidad recibida, en estado BODEGA.
        List<String> skusGenerados = new ArrayList<>();
        for (int i = 0; i < dto.getCantidad(); i++) {
            String sku = UUID.randomUUID().toString();
            Ejemplar ejemplar = new Ejemplar(sku, obra.getId(), dto.getCondicion(),
                    dto.getLoteCaja(), EstadoEjemplar.BODEGA);
            ejemplarRepository.save(ejemplar);
            skusGenerados.add(sku);
        }
        return skusGenerados;
    }

    @Override
    public void cambiarEstadoEjemplar(String sku, String nuevoEstado) {
        Ejemplar ejemplar = ejemplarRepository.findById(sku)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el ejemplar " + sku));
        EstadoEjemplar estado;
        try {
            estado = EstadoEjemplar.valueOf(nuevoEstado);
        } catch (IllegalArgumentException ex) {
            throw new OperacionInvalidaException("Estado no valido: " + nuevoEstado);
        }
        // La entidad valida la transicion (lanza 409 si no es permitida).
        ejemplar.actualizarEstado(estado);
        ejemplarRepository.save(ejemplar);
    }

    @Override
    public long consultarDisponibilidad(String titulo) {
        List<Obra> obras = obraRepository.findByTituloContainingIgnoreCase(titulo);
        if (obras.isEmpty()) {
            throw new RecursoNoEncontradoException("No existe una obra con el titulo " + titulo);
        }
        // Disponibilidad = ejemplares en EXHIBICION de la obra (conteo dinamico).
        Obra obra = obras.get(0);
        return ejemplarRepository.countByObraIdAndEstadoActual(obra.getId(), EstadoEjemplar.EXHIBICION);
    }

    @Override
    public void darDeBaja(String sku) {
        Ejemplar ejemplar = ejemplarRepository.findById(sku)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el ejemplar " + sku));
        if (ejemplar.getEstadoActual() != EstadoEjemplar.DEFECTUOSO) {
            throw new OperacionInvalidaException("Solo se puede dar de baja un ejemplar DEFECTUOSO");
        }
        ejemplarRepository.delete(ejemplar);
    }

    /** Convierte una Obra de dominio a su DTO de salida. */
    private ObraDTO aDTO(Obra obra) {
        ObraDTO dto = new ObraDTO();
        dto.setId(obra.getId());
        dto.setTitulo(obra.getTitulo());
        dto.setMangaka(obra.getMangaka());
        dto.setEditorial(obra.getEditorial());
        dto.setDemografia(obra.getDemografia());
        dto.setIsbn(obra.getIsbn());
        dto.setPrecioListaNuevo(obra.getPrecioListaNuevo());
        return dto;
    }
}
