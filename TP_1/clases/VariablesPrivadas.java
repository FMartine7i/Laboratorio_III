package Practicos.TP_1.clases;

//Ejercicio 24
public class VariablesPrivadas {
    private int x = 2;
    private int y = 6;

    public int suma() {
        return x + y;
    }

    public void sumar(String[] args) {
        VariablesPrivadas obj = new VariablesPrivadas();
        System.out.println(obj.suma());
    }
}
