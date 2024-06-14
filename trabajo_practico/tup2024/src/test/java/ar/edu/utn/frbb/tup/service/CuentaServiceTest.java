package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.TipoCuenta;
import ar.edu.utn.frbb.tup.model.TipoMoneda;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.CuentaNoSoportadaException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CuentaServiceTest {
    @Mock
    private CuentaDao cuentaDao;

    @Mock
    private ClienteDao clienteDao;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private CuentaService cuentaService;

    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cliente = new Cliente();
        cliente.setDni(1234567);
        cliente.setNombre("Peperino");
    }

    /**
     * Se busca probar que la cuenta ingresada ya existe y se espera que arroje la excepción CuentaAlreadyExistsException
     */
    @Test
    public void cuentaAlreadyExists() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(123456);
        cuenta.setTipoCuenta(TipoCuenta.CUENTA_CORRIENTE);
        cuenta.setBalance(2000000);
        cuenta.setTitular(cliente);
        cuenta.setMoneda(TipoMoneda.DOLARES);
        when(cuentaDao.find(123456)).thenReturn(new Cuenta());

        assertThrows(CuentaAlreadyExistsException.class, () -> cuentaService.darDeAltaCuenta(cuenta, cliente.getDni()));
    }

    /**
     * Se prueba que, cuando se ingresa un tipo de cuenta que no sea CUENTA_CORRIENTE o CAJA_AHORROS, se arroja la excepción CuentaNoSoportadaException
     */
    @Test
    public void cuentaNoSoportada() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(12345678);
        cuenta.setTitular(cliente);
        cuenta.setTipoCuenta(TipoCuenta.OTRO);

        when(cuentaDao.find(cuenta.getNumeroCuenta())).thenReturn(null);
        assertThrows(CuentaNoSoportadaException.class, ()-> cuentaService.darDeAltaCuenta(cuenta, cliente.getDni()));
    }

    /**
     * Se busca probar que, al ingresar un tipo de cuenta repetido para un mismo cliente, se arrojará la excepción TipoCuentaAlreadyExistsException
     * @throws TipoCuentaAlreadyExistsException
     */
    @Test
    public void testTipoCuentaDuplicado() throws TipoCuentaAlreadyExistsException {
        Cuenta cuenta = new Cuenta().setMoneda(TipoMoneda.PESOS).setBalance(500000).setTipoCuenta(TipoCuenta.CAJA_AHORRO);
        cuenta.setNumeroCuenta(124);
        cliente.addCuenta(cuenta);

        when(cuentaDao.find(cuenta.getNumeroCuenta())).thenReturn(null);
        doThrow(TipoCuentaAlreadyExistsException.class).when(clienteService).agregarCuenta(cuenta, cliente.getDni());
        assertThrows(TipoCuentaAlreadyExistsException.class, ()-> cuentaService.darDeAltaCuenta(cuenta, cliente.getDni()));
    }

    /**
     * Quiero probar que, al ingresar una cuenta con todas sus características, podrá ser guardada con éxito
     * @throws CuentaAlreadyExistsException
     * @throws TipoCuentaAlreadyExistsException
     * @throws CuentaNoSoportadaException
     */
    @Test
    public void testCuentaSuccess() throws CuentaAlreadyExistsException, TipoCuentaAlreadyExistsException, CuentaNoSoportadaException {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(1234567);
        cuenta.setBalance(100000);
        cuenta.setFechaCreacion(LocalDateTime.of(2022, 5, 21, 15, 20));
        cuenta.setTipoCuenta(TipoCuenta.CUENTA_CORRIENTE);
        cuenta.setMoneda(TipoMoneda.DOLARES);

        when(cuentaDao.find(cuenta.getNumeroCuenta())).thenReturn(null);
        cuentaService.darDeAltaCuenta(cuenta, cliente.getDni());

        verify(cuentaDao, times(1)).save(cuenta);
    }
}
