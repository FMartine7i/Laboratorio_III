package Practicos.TP_1;

public class ClaseC {
    public static void main(String[] args) {
    ClaseB claseB = new ClaseB();
    int x = claseB.incrementa();
    ClaseB otraB = new ClaseB();
    x = x + otraB.incrementa();
    System.out.println(x);
    }
}
