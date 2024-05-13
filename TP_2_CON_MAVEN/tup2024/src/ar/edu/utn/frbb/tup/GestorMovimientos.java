import utils.Cliente;
import utils.Cuenta;
import utils.TipoMovimiento;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        this.fechaHora = LocalDateTime.now();
    }

    public void setFechaHora() { this.fechaHora = LocalDateTime.now(); }
    public void setMonto(int monto) { this.monto = monto; }
    public void setTipoMovimiento(TipoMovimiento tipoMovimiento){ this.tipoMovimiento = tipoMovimiento; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public int getMonto() { return monto; }
    public TipoMovimiento getTipoMovimiento() { return tipoMovimiento; }

    public void transferencia(Cuenta cuentaOrigen, String fechaHora) {
        setTipoMovimiento(TipoMovimiento.TRANSFERENCIA);
        String respuesta = "";

        do {
            System.out.print("Ingrese un monto: ");
            int monto = input.nextInt();

            if (cuentaOrigen.getSaldo() >= monto) {
                System.out.print("\nSeleccione ID de la persona a transferir: ");
                input.nextLine();
                String id = input.nextLine();
                Map<String, Cliente> clientes = gestorClientes.getClientesMap();
                Cliente clienteDestino = clientes.get(id);

                if (clienteDestino != null) {
                    System.out.println("\nCuentas disponibles de " + clienteDestino.getNombre() + " " + clienteDestino.getApellido());
                    for (Cuenta cuentaDestino : clienteDestino.getCuentas()) {
                        System.out.println("+-------------------------------------------+");
                        System.out.println("1. Número de cuenta: " + cuentaDestino.getNumeroAsociado());
                        System.out.println("+-------------------------------------------+");
                    }
                    System.out.print("\nSeleccione un número de cuenta: ");
                    int numero = input.nextInt();
                    input.nextLine();   //Consume salto de línea

                    //Buscar la cuenta destino específica
                    Cuenta cuentaDestino = null;
                    for (Cuenta cuenta : clienteDestino.getCuentas()) {
                        if (numero == cuenta.getNumeroAsociado()) {
                            cuentaDestino = cuenta;
                            break;
                        }
                    }
                    if (cuentaDestino != null) {
                        if (cuentaOrigen.getNumeroAsociado() != numero) {
                            cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - monto);
                            cuentaDestino.setSaldo(cuentaDestino.getSaldo() + monto);
                            registro.add(fechaHora + " - " + tipoMovimiento + "\n\tCliente destino: " + clienteDestino.getNombre() + " " + clienteDestino.getApellido() + ".\n\tMONTO: $" + monto + "\n");
                        } else
                            System.out.println("\nError: no se pueden realizar trasferencias a la misma cuenta.");
                    } else
                        System.out.println("\nError: cuenta no encontrada.");
                    System.out.println("\n¿Desea hacer otra transferencia [s/n]?");
                    respuesta = input.nextLine();
                } else
                    System.out.println("Error: cliente no encontrado.");
            } else
                System.out.println("Error: saldo insuficiente.");
        } while (respuesta.equalsIgnoreCase("s"));
    }

    public void deposito(Cuenta cuenta, String fechaHora) {
        tipoMovimiento = TipoMovimiento.DEPOSITO;
        System.out.print("\nIngrese el dinero que desea depositar: ");
        int dineroDepositado = input.nextInt();
        cuenta.setSaldo(dineroDepositado + cuenta.getSaldo());
        registro.add(fechaHora + " - " + tipoMovimiento + "\n\tMonto: $" + dineroDepositado + "\n");
    }

    public void retirar(Cuenta cuenta, String fechaHora) {
        tipoMovimiento = TipoMovimiento.RETIRO;
        System.out.print("\nIngrese cantidad de dinero a retirar: ");
        int dineroRetirado = input.nextInt();
        if (cuenta.getSaldo() < dineroRetirado)
            System.out.println("Error: no puede retirar más dinero del que posee en la cuenta.");
        else {
            cuenta.setSaldo(cuenta.getSaldo() - dineroRetirado);
            registro.add(fechaHora + " - " + tipoMovimiento + "\n\tMonto: $" + dineroRetirado + "\n");
        }
    }

    public void obtenerSaldo(Cuenta cuenta){
        System.out.print("Saldo disponible: " + cuenta.getSaldo());
    }

    public void imprimirHistorial(){
        for (int i = 0; i < registro.size(); i++) {
            System.out.println((i + 1) + ". " + registro.get(i));
        }
    }

    public void ingresarCuenta(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
        String fechaHoraFormateada = fechaHora.format(formatter);

        System.out.print("\nIngrese la ID de cliente: ");
        String id = input.nextLine();
        Map<String, Cliente> clientes = gestorClientes.getClientesMap();
        Cliente cliente = clientes.get(id);
        if (cliente != null){
            System.out.println("\nBienvenido, " + cliente.getNombre() + " " + cliente.getApellido());
            System.out.println("\nCuentas disponibles: ");
            Cuenta cuentaOrigen = null;

            for (Cuenta cuenta : cliente.getCuentas()){
                cuentaOrigen = cuenta;
                System.out.println("+-------------------------------------------+");
                System.out.println("1. Número de cuenta: " + cuenta.getNumeroAsociado());
                System.out.println("+-------------------------------------------+");
            }
            System.out.print("\nIngrese el número de la cuenta que desee usar: ");
            long option = Long.parseLong(input.nextLine());

            try {
                if (option == cuentaOrigen.getNumeroAsociado()) {
                    int numOption;
                    do {
                        System.out.println("\n+------ Seleccione la acción deseada ------+");
                        System.out.println("| 1. Hacer transferencia                   |");
                        System.out.println("| 2. Retirar                               |");
                        System.out.println("| 3. Depositar                             |");
                        System.out.println("| 4. Consultar saldo                       |");
                        System.out.println("| 5. Ver historial                         |");
                        System.out.println("| 6. Volver                                |");
                        System.out.println("+------------------------------------------+\n");
                        numOption = input.nextInt();

                        switch (numOption) {
                            case 1:
                                transferencia(cuentaOrigen, fechaHoraFormateada);
                                break;
                            case 2:
                                retirar(cuentaOrigen, fechaHoraFormateada);
                                break;
                            case 3:
                                deposito(cuentaOrigen, fechaHoraFormateada);
                                break;
                            case 4:
                                obtenerSaldo(cuentaOrigen);
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
                    while (numOption != 6);
                }
            }
            catch (Exception e){
                System.out.println("Message: " + e);
            }
        }
        else
            System.out.println("Error: cliente no encontrado.");
    }
}

