package Daos;

import Beans.Trabajadores;
import Dto.VentasPorEmpleadoDto;

import java.sql.*;
import java.util.ArrayList;

public class TrabajadoresDao extends DaoBase{
    public boolean validarUsuarioPasswordHashed(String username, String password){

        String sql = "SELECT * FROM trabajadores_credenciales where email = ? and password_hashed = sha2(?,256)";

        boolean exito = false;

        try(Connection connection = getConection();
            PreparedStatement pstmt = connection.prepareStatement(sql)){

            pstmt.setString(1,username);
            pstmt.setString(2,password);

            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    exito = true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return exito;
    }

    public Trabajadores obtenerEmpleado(String email) {

        Trabajadores employee = null;

        String sql = "SELECT *\n" +
                "FROM bicicentro.trabajadores\n" +
                "WHERE correo = ?";

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    employee = new Trabajadores();
                    fetchEmployeeData(employee, rs);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return employee;
    }

    private void fetchEmployeeData(Trabajadores employee, ResultSet rs) throws SQLException {
        employee.setEmployeeId(rs.getInt(1));
        employee.setFirstName(rs.getString(2));
        employee.setLastName(rs.getString(3));
        employee.setEmail(rs.getString(4));
        employee.setDni(rs.getInt(5));
    }

    public ArrayList<VentasPorEmpleadoDto> listarventxemp(){

        ArrayList<VentasPorEmpleadoDto> listaCantVent = new ArrayList<>();

        String sql = "SELECT dniTrabajador, COUNT(*) AS cantidadVentas\n" +
                "FROM bicicentro.ventas\n" +
                "GROUP BY dniTrabajador;\n";

        try (Connection conn = this.getConection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                VentasPorEmpleadoDto e = new VentasPorEmpleadoDto();
                e.setDniEmployee(rs.getInt(1));
                e.setCantVentas(rs.getInt(2));
                listaCantVent.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaCantVent;

    }

}
