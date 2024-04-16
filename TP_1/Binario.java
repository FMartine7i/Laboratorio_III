package Practicos.TP_1;

//Ejercicio 16
public class Binario {
    
    public static void main(String[] args) {
    
        if(args.length != 1)
            System.out.println("Error: No se ingresaron los parametros correctamente");
        else{
            int n = Integer.parseInt(args[0]);
            String binario = "";

            do{                
                String digito = String.valueOf(n % 2);
                binario += digito;
                n /= 2;
            }
            while(n > 0);

            String invertir = "";   // necesito invertir el string 'binario' para que se vea correctamente

            for(int i = binario.length() - 1; i >= 0; i--){
                invertir += binario.charAt(i);
            }
    
            System.out.println(args[0] + " en binario es: " + invertir);
        }        
    }
}
