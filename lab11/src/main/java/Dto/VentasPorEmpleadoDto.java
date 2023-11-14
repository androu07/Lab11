package Dto;

public class VentasPorEmpleadoDto {
    private int dniEmployee;
    private int cantVentas;

    public int getDniEmployee() {
        return dniEmployee;
    }
    public void setDniEmployee(int dniEmployee) {
        this.dniEmployee = dniEmployee;
    }

    public int getCantVentas() {
        return cantVentas;
    }

    public void setCantVentas(int cantVentas) {
        this.cantVentas = cantVentas;
    }
}
