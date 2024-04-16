package Practicos.TP_1;

//Ejercicio 25
public class MetodoEstatico {
    public static void main(String[] args) {

        if(args.length != 2)
            System.out.println("Error: No se ingresaron los parametros correctamente");
        else{
            int a = Integer.parseInt(args[0]);
            int b = Integer.parseInt(args[1]);
            System.out.println(suma(a, b));
        }
    }

    private static int suma(int x, int y){
        return x + y;
    }
}
