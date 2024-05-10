package ar.edu.utn.frbb.tup;
import java.util.Scanner;

public class BaseInput {
    protected Scanner input = new Scanner(System.in);

    protected static void clear(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
