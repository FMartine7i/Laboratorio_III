package utils;
import java.time.LocalDate;

public class Cuenta {
    long numeroAsociado;
    TipoCuenta tipoCuenta;
    LocalDate fechaCreacion;
    int saldo;

    public void setNumeroAsociado(long numeroAsociado){ this.numeroAsociado = numeroAsociado; }
    public void setFechaCreacion(LocalDate fechaCreacion){ this.fechaCreacion = fechaCreacion; }
    public void setSaldo(int saldo){ this.saldo = saldo; }
    public void setTipoCuenta(TipoCuenta tipoCuenta){ this.tipoCuenta = tipoCuenta; }

    public long getNumeroAsociado(){ return numeroAsociado; }
    public LocalDate getFechaCreacion(){ return fechaCreacion; }
    public int getSaldo(){ return saldo; }
    public TipoCuenta getTipoCuenta(){ return tipoCuenta; }
}
