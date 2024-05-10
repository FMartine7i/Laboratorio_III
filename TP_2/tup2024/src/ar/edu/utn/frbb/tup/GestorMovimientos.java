package ar.edu.utn.frbb.tup;
import ar.edu.utn.frbb.tup.utils.Cliente;
import ar.edu.utn.frbb.tup.utils.Cuenta;
import ar.edu.utn.frbb.tup.utils.TipoMovimiento;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class GestorMovimientos extends BaseInput{
    private LocalDateTime fechaHora;
    private int monto;
    private TipoMovimiento tipoMovimiento;
    private Cuenta cuenta;
    private GestorClientes gestorClientes;
    private List<String> registro = new ArrayList<>();

    public GestorMovimientos(GestorClientes gestorClientes){
        this.gestorClientes = gestorClientes;
    }

    public void setFechaHora() { this.fechaHora = LocalDateTime.now(); }
    public void setMonto(int monto) { this.monto = monto; }
    public void setTipoMovimiento(TipoMovimiento tipoMovimiento){ this.tipoMovimiento = tipoMovimiento; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public int getMonto() { return monto; }
    public TipoMovimiento getTipoMovimiento() { return tipoMovimiento; }

    public void transferencia() {
        tipoMovimiento = TipoMovimiento.TRANSFERENCIA;
        System.out.println("Seleccione ID de la persona a transferir");
        String id = input.nextLine();
        registro.add("transferidos a ");
    }

    public void deposito(Cuenta cuenta) {
        tipoMovimiento = TipoMovimiento.DEPOSITO;
        System.out.print("\nIngrese el dinero que desea depositar: ");
        int dineroDepositado = input.nextInt();
        cuenta.setSaldo(dineroDepositado + cuenta.getSaldo());
        registro.add("$" + dineroDepositado +" depositados.");
    }

    public void retirar(Cuenta cuenta) {
        tipoMovimiento = TipoMovimiento.RETIRO;
        System.out.print("\nIngrese cantidad de dinero a retirar: ");
        int dineroRetirado = input.nextInt();
        if (cuenta.getSaldo() < dineroRetirado)
            System.out.println("Error: no puede retirar más dinero del que posee en la cuenta.");
        else {
            cuenta.setSaldo(cuenta.getSaldo() - dineroRetirado);
            registro.add("$" + dineroRetirado + " retirados");
        }
    }

    public void obtenerSaldo(Cuenta cuenta){
        System.out.print("Saldo disponible: " + cuenta.getSaldo());
    }

    public void imprimirHistorial(){

    }

    public void ingresarCuenta(){
        System.out.print("\nIngrese la ID de cliente: ");
        String id = input.nextLine();
        Map<String, Cliente> clientes = gestorClientes.getClientesMap();
        Cliente cliente = clientes.get(id);
        if (cliente != null){
            System.out.println("Bienvenido " + cliente.getNombre() + " " + cliente.getApellido());


            System.out.println("Cuentas disponibles: ");
            for (Cuenta cuenta : cliente.getCuentas()){
                System.out.println("+-------------------------------------------+");
                System.out.println("1. Número de cuenta: " + cuenta.getNumeroAsociado());
                System.out.println("+-------------------------------------------+");
            }
            System.out.print("Ingrese el número de la cuenta que desee usar: ");
            long option = Long.parseLong(input.nextLine());

            if (option == cuenta.getNumeroAsociado()){
                int numOption;
                do {
                    System.out.println("+------ Seleccione la acción deseada ------+");
                    System.out.println("| 1. Hacer transferencia                   |");
                    System.out.println("| 2. Retirar                               |");
                    System.out.println("| 3. Depositar                             |");
                    System.out.println("| 4. Consultar saldo                       |");
                    System.out.println("| 5. Ver historial                         |");
                    System.out.println("| 6. Volver                                |");
                    System.out.println("+------------------------------------------+");
                    numOption = input.nextInt();

                    switch (numOption){
                        case 1:
                            transferencia();
                            break;
                        case 2:
                            retirar(cuenta);
                            break;
                        case 3:
                            deposito(cuenta);
                            break;
                        case 4:
                            obtenerSaldo(cuenta);
                            break;
                        case 5:
                            imprimirHistorial();
                            break;
                        case 6:
                            System.out.println("Volviendo al menú CUENTA...");
                            break;
                        default:
                            System.out.println("Error: opción no válida. Seleccione [1-5]");
                    }
                }
                while (numOption != 5);
            }
        }
        else
            System.out.println("Error: cliente no encontrado.");
    }
}
