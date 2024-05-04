package Practicos.TP_1.clases;

public class OrdenarMayorMenor {
    public static void imprimirOrden(String[] args) {
        int[] numeros = new int[args.length];

        if (args.length == 0)
            System.out.println("Debe ingresar al menos un número.");
        else{
            boolean todosNumericos = true;

            for(int i = 0; i < args.length; i++){
                if(args[i].matches("-?\\d+")){
                    numeros[i] = Integer.parseInt(args[i]);                 
                }
                else{
                    todosNumericos = false;
                    break;
                }
            }
            
            if(todosNumericos){
                ordenar(numeros);
        
                System.out.println("Números ordenados:");
                for(int numero : numeros){
                    System.out.print(numero + " ");
                }    
            }
            else
                System.out.println("Debe ingresar números enteros");     
        }        
    }

    public static void ordenar(int[] arreglo){
        int aux;
        for(int i = 0; i < arreglo.length; i++){
            for(int j = i + 1; j < arreglo.length; j++){
                if(arreglo[i] > arreglo[j]){
                    aux = arreglo[i];
                    arreglo[i] = arreglo[j];
                    arreglo[j] = aux;
                }
            }
        }
    }
}
