package utils;

public enum TipoPersona {
    FISICA("F"), JURIDICA("J");
    private final String descripcion;

    TipoPersona(String descripcion){
        this.descripcion = descripcion;
    }

    public String getDescripcion(){ return descripcion; }
}
