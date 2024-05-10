package ar.edu.utn.frbb.tup.utils;
import java.time.LocalDateTime;

public class Movimiento {
    private LocalDateTime fechaHora;

    public void setFechaHora(LocalDateTime fechaHora){ this.fechaHora = fechaHora; }

    public LocalDateTime getFechaHora(){ return fechaHora; }
}
