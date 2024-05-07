package ar.edu.utn.frbb.tup.utils;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Cliente extends  Persona{
    private TipoPersona tipoPersona;
    private String banco;
    private LocalDate fechaAlta;
    private Set<Cuenta> cuentas = new HashSet<>();

    public void setTipoPersona(TipoPersona tipoPersona){ this.tipoPersona = tipoPersona; }
    public void setBanco(String banco){ this.banco = banco; }
    public void setFechaAlta(LocalDate fechaAlta){ this.fechaAlta = fechaAlta; }

    public TipoPersona getTipoPersona(){ return tipoPersona; }
    public String getBanco(){ return banco; }
    public LocalDate getFechaAlta(){ return fechaAlta; }

    public void agregarCuenta(Cuenta cuenta){
        this.cuentas.add(cuenta);
    }
}
