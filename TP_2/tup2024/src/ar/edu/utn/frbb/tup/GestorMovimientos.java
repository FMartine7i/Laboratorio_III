package ar.edu.utn.frbb.tup;
import ar.edu.utn.frbb.tup.utils.TipoMovimiento;
import java.time.LocalDateTime;

public class GestorMovimientos {
    private LocalDateTime fechaHora;
    private int monto;
    private TipoMovimiento tipoMovimiento;

    public void setFechaHora() { this.fechaHora = LocalDateTime.now(); }
    public void setMonto(int monto) { this.monto = monto; }
    public void setTipoMovimiento(TipoMovimiento tipoMovimiento){ this.tipoMovimiento = tipoMovimiento; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public int getMonto() { return monto; }
    public TipoMovimiento getTipoMovimiento() { return tipoMovimiento; }


}
