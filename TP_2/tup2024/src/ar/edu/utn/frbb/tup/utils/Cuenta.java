package ar.edu.utn.frbb.tup.utils;
import java.time.LocalDateTime;

public class Cuenta {
    String nombre;
    LocalDateTime fechaCreacion;
    int balance;

    public void setNombre(String nombre){ this.nombre = nombre; }
    public void setFechaCreacion(LocalDateTime fechaCreacion){ this.fechaCreacion = fechaCreacion; }
    public void setBalance( int balance ){ this.balance = balance; }

    public String getNombre(){ return nombre; }
    public LocalDateTime getFechaCreacion(){ return fechaCreacion; }
    public int getBalance(){ return balance; }
}
