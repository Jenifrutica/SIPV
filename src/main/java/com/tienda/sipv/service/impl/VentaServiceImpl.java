package com.tienda.sipv.service.impl;

import com.tienda.sipv.dto.DevolucionDTO;
import com.tienda.sipv.dto.ReciboResponseDTO;
import com.tienda.sipv.dto.VentaRequestDTO;
import com.tienda.sipv.exception.OperacionInvalidaException;
import com.tienda.sipv.exception.RecursoNoEncontradoException;
import com.tienda.sipv.model.Cliente;
import com.tienda.sipv.model.Devolucion;
import com.tienda.sipv.model.Ejemplar;
import com.tienda.sipv.model.LineaRecibo;
import com.tienda.sipv.model.Obra;
import com.tienda.sipv.model.Recibo;
import com.tienda.sipv.model.enums.Condicion;
import com.tienda.sipv.model.enums.EstadoEjemplar;
import com.tienda.sipv.model.enums.EstadoRecibo;
import com.tienda.sipv.repository.ClienteRepository;
import com.tienda.sipv.repository.DevolucionRepository;
import com.tienda.sipv.repository.EjemplarRepository;
import com.tienda.sipv.repository.ObraRepository;
import com.tienda.sipv.repository.ReciboRepository;
import com.tienda.sipv.service.IVentaService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implementacion del modulo de Ventas (ventas, devoluciones, anulacion y clientes).
 */
@Service
public class VentaServiceImpl implements IVentaService {

    /** Porcentaje de IVA aplicado a las ventas. */
    private static final double IVA = 0.19;

    private final ReciboRepository reciboRepository;
    private final EjemplarRepository ejemplarRepository;
    private final ClienteRepository clienteRepository;
    private final ObraRepository obraRepository;
    private final DevolucionRepository devolucionRepository;

    public VentaServiceImpl(ReciboRepository reciboRepository,
                            EjemplarRepository ejemplarRepository,
                            ClienteRepository clienteRepository,
                            ObraRepository obraRepository,
                            DevolucionRepository devolucionRepository) {
        this.reciboRepository = reciboRepository;
        this.ejemplarRepository = ejemplarRepository;
        this.clienteRepository = clienteRepository;
        this.obraRepository = obraRepository;
        this.devolucionRepository = devolucionRepository;
    }

    @Override
    public ReciboResponseDTO registrarVenta(VentaRequestDTO dto) {
        // El cliente debe existir.
        clienteRepository.findByCedula(dto.getCedulaCliente())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe el cliente con cedula " + dto.getCedulaCliente()));

        List<LineaRecibo> lineas = new ArrayList<>();
        double subtotal = 0;

        for (String sku : dto.getSkusProductos()) {
            Ejemplar ejemplar = ejemplarRepository.findById(sku)
                    .orElseThrow(() -> new RecursoNoEncontradoException("No existe el ejemplar " + sku));

            // Solo se vende lo que esta en EXHIBICION o RESERVADO.
            if (ejemplar.getEstadoActual() != EstadoEjemplar.EXHIBICION
                    && ejemplar.getEstadoActual() != EstadoEjemplar.RESERVADO) {
                throw new OperacionInvalidaException(
                        "El ejemplar " + sku + " no esta disponible para la venta");
            }

            // Precio de la obra al momento de la venta (snapshot).
            Obra obra = obraRepository.findById(ejemplar.getObraId())
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                            "No existe la obra del ejemplar " + sku));
            double precio = obra.getPrecioListaNuevo();

            ejemplar.actualizarEstado(EstadoEjemplar.VENDIDO);
            ejemplarRepository.save(ejemplar);

            lineas.add(new LineaRecibo(sku, precio));
            subtotal += precio;
        }

        double iva = subtotal * IVA;
        double total = subtotal + iva;

        Recibo recibo = new Recibo(UUID.randomUUID().toString(), LocalDateTime.now(), subtotal, iva, total);
        recibo.setEstado(EstadoRecibo.EMITIDO);
        recibo.setLineas(lineas);
        reciboRepository.save(recibo);

        return new ReciboResponseDTO(recibo.getIdRecibo(), recibo.getSubtotal(), recibo.getIva(),
                recibo.getFecha(), recibo.getTotal(), recibo.getLineas());
    }

    @Override
    public void anularVenta(String idRecibo) {
        Recibo recibo = reciboRepository.findById(idRecibo)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el recibo " + idRecibo));
        if (recibo.getEstado() == EstadoRecibo.ANULADO) {
            throw new OperacionInvalidaException("El recibo " + idRecibo + " ya esta anulado");
        }

        // Reintegra cada ejemplar vendido de vuelta a exhibicion.
        for (LineaRecibo linea : recibo.getLineas()) {
            ejemplarRepository.findById(linea.getSku()).ifPresent(ejemplar -> {
                ejemplar.actualizarEstado(EstadoEjemplar.EXHIBICION);
                ejemplarRepository.save(ejemplar);
            });
        }

        // El recibo no se borra: solo cambia su estado a ANULADO.
        recibo.setEstado(EstadoRecibo.ANULADO);
        reciboRepository.save(recibo);
    }

    @Override
    public void procesarDevolucion(DevolucionDTO dto) {
        reciboRepository.findById(dto.getIdRecibo())
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el recibo " + dto.getIdRecibo()));
        Ejemplar ejemplar = ejemplarRepository.findById(dto.getSku())
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el ejemplar " + dto.getSku()));

        // La devolucion es un documento separado; no modifica el recibo original.
        Devolucion devolucion = new Devolucion(UUID.randomUUID().toString(), LocalDateTime.now(),
                dto.getMotivo(), dto.getIdRecibo(), dto.getSku());
        devolucionRepository.save(devolucion);

        // El ejemplar vuelve a exhibicion marcado como USADO.
        ejemplar.actualizarEstado(EstadoEjemplar.EXHIBICION);
        ejemplar.setCondicion(Condicion.USADO);
        ejemplarRepository.save(ejemplar);
    }

    @Override
    public List<Recibo> listarRecibos() {
        return reciboRepository.findAll();
    }

    @Override
    public Recibo obtenerRecibo(String idRecibo) {
        return reciboRepository.findById(idRecibo)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el recibo " + idRecibo));
    }

    @Override
    public Cliente registrarCliente(Cliente cliente) {
        if (clienteRepository.findByCedula(cliente.getCedula()).isPresent()) {
            throw new OperacionInvalidaException("Ya existe un cliente con la cedula " + cliente.getCedula());
        }
        if (cliente.getId() == null) {
            cliente.setId(UUID.randomUUID().toString());
        }
        return clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente obtenerCliente(String id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el cliente " + id));
    }

    @Override
    public Cliente editarCliente(String id, Cliente datos) {
        Cliente cliente = obtenerCliente(id);
        cliente.setNombre(datos.getNombre());
        cliente.setEmail(datos.getEmail());
        return clienteRepository.save(cliente);
    }
}
