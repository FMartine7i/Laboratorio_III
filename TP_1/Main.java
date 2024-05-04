package Practicos.TP_1;
import Practicos.TP_1.clases.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean exit = false;
        int op;
        Scanner input = new Scanner(System.in);

        while(!exit) {
            System.out.println("\n\t\tTrabajo práctico 1");
            System.out.println("1. Ejercicio 15: ordenar menor a mayor");
            System.out.println("2. Ejercicio 16: convertir decimal a binario");
            System.out.println("3. Ejercicio 22: atributo de clase");
            System.out.println("4. Ejercicio 23: clase con constructor");
            System.out.println("5. Ejercicio 24: atributo privado y métodos públicos");
            System.out.println("6. Ejercicio 25: método estático");
            System.out.println("7. Ejercicio 26: variable final");
            System.out.println("0. Salir");
            System.out.println("Ingrese una opción [0-7]\n");

            op = input.nextInt();
            input.nextLine();
            String[] numberStrs = null;

            if(op >= 1 && op <= 7) {
                if (op == 1) {
                    System.out.println("Ingrese números separados por espacio");
                }
                else if (op == 4 || op == 5 || op == 6)
                    System.out.println("Ingrese dos números separados por espacio");
                numberStrs = input.nextLine().split("\\s+");
            }

            switch (op) {
                case 0:
                    System.out.println("Saliendo del programa...");
                    exit = true;
                    break;
                case 1:
                    OrdenarMayorMenor.imprimirOrden(numberStrs);
                case 2:
                    Binario.convertirABinario(numberStrs);
                    break;
                case 3:
                    Instanciar obj = new Instanciar();
                    obj.mostrarValor();
                    break;
                case 4:
                    Constructor.sumar(numberStrs);
                    break;
                case 5:
                    VariablesPrivadas variablesPrivadas = new VariablesPrivadas();
                    variablesPrivadas.sumar(numberStrs);
                    break;
                case 6:
                    MetodoEstatico.imprimir(numberStrs);
                    break;
                case 7:
                    VariableFinal variableFinal = new VariableFinal();
                    variableFinal.imprimir(numberStrs);
                    break;
                default:
                    System.out.println("Error: opción no válida");
                    break;
            }
        }
        input.close();
    }
}
