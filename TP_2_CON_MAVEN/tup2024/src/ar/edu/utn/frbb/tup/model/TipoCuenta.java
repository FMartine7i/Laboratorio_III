package model;

public enum TipoCuenta {
    CORRIENTE("C"), AHORROS("A");
    private final String descripcion;

    TipoCuenta(String descripcion){
        this.descripcion = descripcion;
    }

    public String getDescripcion(){ return descripcion; }
}