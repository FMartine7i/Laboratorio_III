package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.model.*;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClienteServiceTest {

    @Mock
    private ClienteDao clienteDao;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testClienteMenor18Años() {
        Cliente clienteMenorDeEdad = new Cliente();
        clienteMenorDeEdad.setFechaNacimiento(LocalDate.of(2020, 2, 7));
        assertThrows(IllegalArgumentException.class, () -> clienteService.darDeAltaCliente(clienteMenorDeEdad));
    }

    @Test
    public void testClienteSuccess() throws ClienteAlreadyExistsException {
        Cliente cliente = new Cliente();
        cliente.setFechaNacimiento(LocalDate.of(1978,3,25));
        cliente.setDni(29857643);
        cliente.setTipoPersona(TipoPersona.PERSONA_FISICA);
        clienteService.darDeAltaCliente(cliente);

        verify(clienteDao, times(1)).save(cliente);
    }

    @Test
    public void testClienteAlreadyExistsException() throws ClienteAlreadyExistsException {
        Cliente pepeRino = new Cliente();
        pepeRino.setDni(26456437);
        pepeRino.setNombre("Pepe");
        pepeRino.setApellido("Rino");
        pepeRino.setFechaNacimiento(LocalDate.of(1978, 3,25));
        pepeRino.setTipoPersona(TipoPersona.PERSONA_FISICA);

        when(clienteDao.find(26456437, false)).thenReturn(new Cliente());

        assertThrows(ClienteAlreadyExistsException.class, () -> clienteService.darDeAltaCliente(pepeRino));
    }


    @Test
    public void testAgregarCuentaAClienteSuccess() throws TipoCuentaAlreadyExistsException {
        Cliente pepeRino = new Cliente();
        pepeRino.setDni(26456439);
        pepeRino.setNombre("Pepe");
        pepeRino.setApellido("Rino");
        pepeRino.setFechaNacimiento(LocalDate.of(1978, 3,25));
        pepeRino.setTipoPersona(TipoPersona.PERSONA_FISICA);

        Cuenta cuenta = new Cuenta()
                .setMoneda(TipoMoneda.PESOS)
                .setBalance(500000)
                .setTipoCuenta(TipoCuenta.CAJA_AHORRO);

        when(clienteDao.find(26456439, true)).thenReturn(pepeRino);

        clienteService.agregarCuenta(cuenta, pepeRino.getDni());

        verify(clienteDao, times(1)).save(pepeRino);

        assertEquals(1, pepeRino.getCuentas().size());
        assertEquals(pepeRino, cuenta.getTitular());

    }


    @Test
    public void testAgregarCuentaAClienteDuplicada() throws TipoCuentaAlreadyExistsException {
        Cliente luciano = new Cliente();
        luciano.setDni(26456439);
        luciano.setNombre("Pepe");
        luciano.setApellido("Rino");
        luciano.setFechaNacimiento(LocalDate.of(1978, 3,25));
        luciano.setTipoPersona(TipoPersona.PERSONA_FISICA);

        Cuenta cuenta = new Cuenta()
                .setMoneda(TipoMoneda.PESOS)
                .setBalance(500000)
                .setTipoCuenta(TipoCuenta.CAJA_AHORRO);

        when(clienteDao.find(26456439, true)).thenReturn(luciano);

        clienteService.agregarCuenta(cuenta, luciano.getDni());

        Cuenta cuenta2 = new Cuenta()
                .setMoneda(TipoMoneda.PESOS)
                .setBalance(500000)
                .setTipoCuenta(TipoCuenta.CAJA_AHORRO);

        assertThrows(TipoCuentaAlreadyExistsException.class, () -> clienteService.agregarCuenta(cuenta2, luciano.getDni()));
        verify(clienteDao, times(1)).save(luciano);
        assertEquals(1, luciano.getCuentas().size());
        assertEquals(luciano, cuenta.getTitular());
    }

    //Agregar una CA$ y CC$ --> success 2 cuentas, titular
    /**
     *
     * Se quiere probar que el cliente tiene dos cuentas (caja de ahorro y cuenta corriente) y el titular es el mismo cliente que las creó
     * el tamaño del set de cuentas de cliente debe ser 2, el titular de las cuentas debe ser el cliente y además cliente debe contener tanto cuenta1 como cuenta2
     * @throws TipoCuentaAlreadyExistsException
     */
    @Test
    public void testAgregarCajaAhorro() throws TipoCuentaAlreadyExistsException{
        final Long dni = 12345678L;
        Cliente cliente = new Cliente();
        cliente.setNombre("Peperino");
        cliente.setDni(dni);
        cliente.setTipoPersona(TipoPersona.PERSONA_FISICA);

        Cuenta cuenta1 = new Cuenta().setMoneda(TipoMoneda.PESOS).setBalance(500000).setTipoCuenta(TipoCuenta.CAJA_AHORRO);
        Cuenta cuenta2 = new Cuenta().setMoneda(TipoMoneda.PESOS).setBalance(500000).setTipoCuenta(TipoCuenta.CUENTA_CORRIENTE);
        when(clienteDao.find(dni, true)).thenReturn(cliente);
        clienteService.agregarCuenta(cuenta1, dni);
        clienteService.agregarCuenta(cuenta2, dni);

        verify(clienteDao, times(2)).save(cliente);
        assertEquals(2, cliente.getCuentas().size());
        assertEquals(cliente, cuenta1.getTitular());
        assertEquals(cliente, cuenta2.getTitular());
        assertTrue(cliente.getCuentas().contains(cuenta1));
        assertTrue(cliente.getCuentas().contains(cuenta2));
    }

    //Agregar una CA$ y CAU$S --> success 2 cuentas, titular peperino...

    /**
     *
     * Se quiere probar que el cliente tiene dos cuentas (caja de ahorro y caja de ahorro en dólares)
     * @throws TipoCuentaAlreadyExistsException
     */
    @Test
    public void testCajaAhorroDolares() throws TipoCuentaAlreadyExistsException{
        final Long dni = 12345678L;
        Cliente cliente = new Cliente();
        cliente.setNombre("Peperino");
        cliente.setDni(dni);
        cliente.setTipoPersona(TipoPersona.PERSONA_FISICA);

        Cuenta cuenta1 = new Cuenta().setMoneda(TipoMoneda.PESOS).setBalance(500000).setTipoCuenta(TipoCuenta.CAJA_AHORRO);
        Cuenta cuenta2 = new Cuenta().setMoneda(TipoMoneda.DOLARES).setBalance(500000).setTipoCuenta(TipoCuenta.CAJA_AHORRO);
        when(clienteDao.find(dni, true)).thenReturn(cliente);
        clienteService.agregarCuenta(cuenta1, dni);
        clienteService.agregarCuenta(cuenta2, dni);

        verify(clienteDao, times(2)).save(cliente);
        assertEquals(2, cliente.getCuentas().size());
        assertEquals(cliente, cuenta1.getTitular());
        assertEquals(cliente, cuenta2.getTitular());
    }

    //Testear clienteService.buscarPorDni

    /**
     * Testea el método buscarPorDni(): quiero probar que el cliente devuelto por el método buscarClientePorDni() será igual al cliente buscado
     * @throws IllegalArgumentException
     */
    @Test
    public void testBuscarPorDniSuccess() throws IllegalArgumentException {
        Long dni = 12345678L;
        Cliente cliente = new Cliente();
        cliente.setDni(dni);
        when(clienteDao.find(dni, true)).thenReturn(cliente);

        Cliente clienteBuscado = clienteService.buscarClientePorDni(dni);
        assertEquals(clienteBuscado.getDni(), cliente.getDni());
    }

    /**
     * Testea el método buscarClientePorDni(): quiero probar que la excepción será arrojada cuando se devuelve un cliente nulo
     * @throws IllegalArgumentException
     */
    @Test
    public void testBuscarPorDniFailure() throws IllegalArgumentException {
        Long dni = 12345678L;
        when(clienteDao.find(dni, true)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, ()-> clienteService.buscarClientePorDni(dni));
    }

    /**
     * Test para nueva validación agregado: quiero probar que las validaciones para el número de DNI ingresado funcionan correctamente
     * @throws IllegalArgumentException
     */
    @Test
    public void testValidacionSignoDni() throws IllegalArgumentException {
        long dniInvalido = -12345678L;

        assertThrows(IllegalArgumentException.class, ()-> clienteService.buscarClientePorDni(dniInvalido));
        assertThrows(IllegalArgumentException.class, ()-> clienteService.buscarClientePorDni(0));
    }
}