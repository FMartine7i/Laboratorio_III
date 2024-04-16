package Practicos.TP_1;

//Ejercicio 22
public class Instanciar {
    String texto = "Hola";

    public void mostrarValor(){
        System.out.println(texto);
    }

    public static void main(String[] args) {
        Instanciar obj = new Instanciar();
        obj.mostrarValor();
    }
}
