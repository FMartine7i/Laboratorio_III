package ar.edu.utn.frbb.tup;
import ar.edu.utn.frbb.tup.utils.Cuenta;
import ar.edu.utn.frbb.tup.utils.TipoCuenta;
import ar.edu.utn.frbb.tup.utils.Cliente;
import java.time.LocalDate;
import java.util.Map;

public class GestorCuenta extends BaseInput{
    private GestorClientes gestorClientes;  //referencia a GestorClientes para no perder valores cuando lo inicializo

    public GestorCuenta(GestorClientes gestorClientes){
        this.gestorClientes = gestorClientes;
    }
    public void verCuentasCliente(){
        System.out.print("\nIngrese número de identificación:");
        String id = input.nextLine();
        Map<String, Cliente> clientesMap = gestorClientes.getClientesMap();
        Cliente cliente = clientesMap.get(id);

        if (cliente != null){
            System.out.println("\nCuenta/s del cliente: " + cliente.getNombre() + " " + cliente.getApellido());
            for (Cuenta cuenta : cliente.getCuentas()){
                System.out.println("+-----------------------------------+");
                System.out.println("Cuenta número " + cuenta.getNumeroAsociado());
                System.out.println("Fecha de creación: " + cuenta.getFechaCreacion());
                System.out.println("Tipo: " + cuenta.getTipoCuenta());
                System.out.println("Saldo disponible: $" + cuenta.getSaldo());
                System.out.println("+-----------------------------------+\n");
            }
        }
        else
            System.out.println("Error: cliente no encontrado.");
    }

    public void agregarCuenta(){
        System.out.println("Ingresar datos de la cuenta:\n");
        System.out.print("ID del cliente asociado: ");
        String id = input.nextLine();
        Map<String, Cliente> clientesMap = gestorClientes.getClientesMap();
        Cliente cliente = clientesMap.get(id);

        if (cliente != null) {
            System.out.println("\nAdministrador de cuentas del cliente " + cliente.getNombre() + " " + cliente.getApellido());
            System.out.print("\nNúmero de cuenta: ");
            long numeroAsociado = Long.parseLong(input.nextLine());
            System.out.print("\nTipo de cuenta [Ahorro(A) - Corriente(C)]: ");
            TipoCuenta tipoCuenta = cuentaToString(input.nextLine());
            System.out.print("\nFecha de creación [Formato YYYY-MM-dd]: ");
            LocalDate fechaCreacion = dateToString(input.nextLine());
            System.out.print("\nSaldo: ");
            int saldo = input.nextInt();
            input.nextLine();

            add(cliente, numeroAsociado, tipoCuenta, fechaCreacion, saldo);
            System.out.println("\nCuenta agregada con éxito.");
        }
        else
            System.out.println("Error: cliente " + id + " no encontrado.");
    }

    public void add(Cliente cliente, long numeroAsociado, TipoCuenta tipoCuenta, LocalDate fechaCreacion, int saldo){
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroAsociado(numeroAsociado);
        cuenta.setTipoCuenta(tipoCuenta);
        cuenta.setFechaCreacion(fechaCreacion);
        cuenta.setSaldo(saldo);
        cliente.agregar(cuenta);
    }

    public void eliminarCuenta(){
        System.out.print("\nIngrese la ID del cliente:");
        String id = input.nextLine();
        Map<String, Cliente> clientes = gestorClientes.getClientesMap();
        Cliente cliente = clientes.get(id);

        if (cliente != null) {
            System.out.println("\nCuentas asociadas al cliente " + id + ":");
            for (Cuenta cuenta : cliente.getCuentas()){
                System.out.print(cuenta);
            }

            System.out.print("\nIngrese el número asociado a la cuenta a eliminar: ");
            long numeroAsociado = Long.parseLong(input.nextLine());

            for (Cuenta cuenta : cliente.getCuentas()) {
                if (cuenta.getNumeroAsociado() == numeroAsociado){
                    if (cliente.eliminar(cuenta))
                        System.out.println("Cuenta eliminada con éxito.\n");
                    else
                        System.out.println("Error: no se ha encontrado la cuenta.\n");
                }
            }
        }
        else
            System.out.println("Error: no se encontró al cliente con la ID proporcionada.\n");
    }

    //Devuelve como LocalDate la fecha ingresada como String
    private LocalDate dateToString(String fechaString) {
        boolean fechaValida = false;

        while (!fechaValida) {
            try {
                fechaValida = true;
                return LocalDate.parse(fechaString);
            }
            catch (Exception e) {
                System.out.println("Formato de fecha inválido. Ingrese la fecha en formato YYYY-MM-dd:");
            }
        }
        return null;
    }

    //Asigna un tipo de cuenta
    private TipoCuenta cuentaToString(String tipoCuentaStr) {
        for (TipoCuenta tipoCuenta : TipoCuenta.values()) {
            if (tipoCuenta.getDescripcion().equalsIgnoreCase(tipoCuentaStr)) {
                return tipoCuenta;
            }
        }
        System.out.println("Opción inválida. Se ha seleccionado tipo de cuenta corriente por defecto.");
        return TipoCuenta.CORRIENTE;
    }
}
