package ar.edu.utn.frbb.tup;
import ar.edu.utn.frbb.tup.utils.Cuenta;
import ar.edu.utn.frbb.tup.utils.TipoCuenta;
import ar.edu.utn.frbb.tup.utils.Cliente;
import java.time.LocalDate;
import java.util.Map;

public class GestorCuenta extends BaseInput{

    public void verCuentasCliente(){
        GestorClientes gestorClientes = new GestorClientes();
        System.out.print("\nIngrese número de identificación:");
        String id = input.nextLine();
        Map<String, Cliente> clientesMap = gestorClientes.getClientesMap();
        Cliente cliente = clientesMap.get(id);

        if (cliente != null){
            System.out.println("\nCuenta/s del cliente:");
            System.out.print(cliente.getCuentas());
        }
    }

    public void agregarCuenta(){
        System.out.println("Ingresar datos de la cuenta:");
        System.out.println("ID del cliente asociado: ");
        String id = input.nextLine();
        System.out.print("\nNúmero de cuenta: ");
        long numeroAsociado = Long.parseLong(input.nextLine());
        System.out.print("\nApellido: ");
        TipoCuenta tipoCuenta = cuentaToString(input.nextLine());
        System.out.print("\nFecha de creación [Formato YYYY-MM-dd]: ");
        LocalDate fechaCreacion = dateToString(input.nextLine());
        int saldo = input.nextInt();

        add(id, numeroAsociado, tipoCuenta, fechaCreacion, saldo);
    }

    public void add(String id, long numeroAsociado, TipoCuenta tipoCuenta, LocalDate fechaCreacion, int saldo){
        GestorClientes gestorClientes = new GestorClientes();
        Map<String, Cliente> clientesMap = gestorClientes.getClientesMap();
        Cliente cliente = clientesMap.get(id);

        if (cliente != null) {
            Cuenta cuenta = new Cuenta();
            cuenta.setNumeroAsociado(numeroAsociado);
            cuenta.setTipoCuenta(tipoCuenta);
            cuenta.setFechaCreacion(fechaCreacion);
            cuenta.setSaldo(saldo);
            cliente.agregar(cuenta);

            System.out.println("Cuenta agregada con éxito al cliente " + cliente.getNombre() + cliente.getApellido());
        }
        else
            System.out.println("Error: cliente " + id + " no encontrado.");
    }

    public void eliminarCuenta(){
        System.out.print("\nIngrese la ID del cliente:");
        String id = input.nextLine();
        GestorClientes gestorClientes = new GestorClientes();
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
