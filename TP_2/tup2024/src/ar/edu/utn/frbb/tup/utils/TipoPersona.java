package ar.edu.utn.frbb.tup.utils;

public enum TipoPersona {
    FISICA("F"), JURIDICA("A");
    private final String descripcion;

    TipoPersona(String descripcion){
        this.descripcion = descripcion;
    }

    public String getDescripcion(){ return descripcion; }
}
