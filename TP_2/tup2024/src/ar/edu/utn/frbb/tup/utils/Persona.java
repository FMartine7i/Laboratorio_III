package ar.edu.utn.frbb.tup.utils;
import java.time.LocalDate;

public class Persona {
    private String nombre;
    private String apellido;
    private long dni;
    private LocalDate fechaNacimiento;

    public void setNombre(String nombre){ this.nombre = nombre; }
    public void setApellido(String apellido){ this.apellido = apellido; }
    public void setDni(long dni){ this.dni = dni; }
    public void setFechaNacimiento(LocalDate fechaNacimiento){ this.fechaNacimiento = fechaNacimiento; }

    public String getNombre(){ return nombre; }
    public String getApellido(){ return apellido; }
    public long getDni(){ return dni; }
    public LocalDate getFechaNacimiento(){ return fechaNacimiento; }
}
