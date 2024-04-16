package Practicos.TP_1;

//Ejercicio 23
public class Constructor {
    private int x;
    private int y;

    public Constructor(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int suma(){
        return x + y;
    }

    public static void main(String[] args) {

        if(args.length != 2)
            System.out.println("Error: No se ingresaron los parametros correctamente");
        else{
            int num1 = Integer.parseInt(args[0]);
            int num2 = Integer.parseInt(args[1]);

            Constructor obj = new Constructor(num1, num2);

            System.out.println(obj.suma());
        }
    }
}
