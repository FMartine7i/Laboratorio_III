package Practicos.TP_1;

public class ClaseB {
    private ClaseA claseA = new ClaseA();
    int x = 0;
    public int incrementa() {
    int x = claseA.incrementa();
    System.out.println("hola" + this.x);
    this.x++;
    System.out.println("chau" + x);
    return x;
    }
}
