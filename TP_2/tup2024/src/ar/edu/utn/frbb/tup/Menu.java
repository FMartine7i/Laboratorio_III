package ar.edu.utn.frbb.tup;
import java.util.Scanner;

public class Menu {
    boolean exit = false;
    protected Scanner input = new Scanner(System.in);

    public void renderMenu() {
        do {
            System.out.println("\n-------------- BANCO --------------");
            System.out.println("1. Nuevo cliente");
            System.out.println("2. Nueva cuenta");
            System.out.println("3. Generar un movimiento");
            System.out.println("4. Salir");
            System.out.println("Ingrese una opción [1-4]");
            System.out.println("------------------------------------\n");

            int op = input.nextInt();
            input.nextLine();

            switch (op) {
                case 1:
                    System.out.println("Creando nuevo cliente...");
                    break;
                case 2:
                    System.out.println("Creando nueva cuenta...");
                    break;
                case 3:
                    System.out.println("Generando movimiento...");
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
}
