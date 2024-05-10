package ar.edu.utn.frbb.tup;

public class Menu extends BaseInput{
    boolean exit = false;
    GestorClientes gestorClientes = new GestorClientes();
    GestorCuenta gestorCuenta = new GestorCuenta(gestorClientes);

    public void renderMenu() {
        do {
            System.out.println("\n+-------------- BANCO --------------+");
            System.out.println("| 1. Clientes                         |");
            System.out.println("| 2. Cuenta                           |");
            System.out.println("| 3. Historial de movimientos         |");
            System.out.println("| 4. Salir                            |");
            System.out.println("| -  Ingrese una opción [1-4]         |");
            System.out.println("+-------------------------------------+\n");

            int op = input.nextInt();
            input.nextLine();

            switch (op) {
                case 1:
                    subMenuClient();
                    break;
                case 2:
                    subMenuAccounts();
                    break;
                case 3:

                    break;
                case 4:
                    exit = true;
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Error: opción inválida.");
            }
        } while (!exit);
    }

    public void subMenuClient(){
        int option;

        do{
            System.out.println("\n+------------- CLIENTE -------------+");
            System.out.println("| 1. Agregar nuevo cliente          |");
            System.out.println("| 2. Buscar cliente                 |");
            System.out.println("| 3. Ver todos los clientes         |");
            System.out.println("| 4. Eliminar cliente               |");
            System.out.println("| 5. Actualizar cliente             |");
            System.out.println("| 6. Volver                         |");
            System.out.println("+-----------------------------------+");
            option = input.nextInt();

            switch (option) {
                case 1:
                    gestorClientes.agregarCliente();
                    break;
                case 2:
                    gestorClientes.printClient();
                    break;
                case 3:
                    gestorClientes.getClients();
                    break;
                case 4:
                    gestorClientes.delete();
                    break;
                case 5:
                    gestorClientes.actualizarCliente();
                    break;
                case 6:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.print("Opción no válida. Vuelva a ingresar el número [1-6]: ");
            }
        }
        while (option != 6);
    }

    public void subMenuAccounts(){
        int option;

        do{
            System.out.println("\n+------------- CUENTA --------------+");
            System.out.println("| 1. Agregar nueva cuenta           |");
            System.out.println("| 2. Eliminar cuenta                |");
            System.out.println("| 3. Ver cuentas asociadas          |");
            System.out.println("| 4. Realizar transferencia         |");
            System.out.println("| 5. Retirar dinero                 |");
            System.out.println("| 6. Consultar saldo                |");
            System.out.println("| 7. Volver                         |");
            System.out.println("+-----------------------------------+\n");
            option = input.nextInt();

            switch (option) {
                case 1:
                    gestorCuenta.agregarCuenta();
                    break;
                case 2:
                    gestorCuenta.eliminarCuenta();
                    break;
                case 3:
                    gestorCuenta.verCuentasCliente();
                    break;
                case 4:
                    gestorClientes.delete();
                    break;
                case 5:
                    gestorClientes.actualizarCliente();
                    break;
                case 6:
                    break;
                case 7:
                    System.out.println("Volviendo al menú principal...");
                default:
                    System.out.print("Opción no válida. Vuelva a ingresar el número [1-6]: ");
            }
        }
        while (option != 7);
    }
}
