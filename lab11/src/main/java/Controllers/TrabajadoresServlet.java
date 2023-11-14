package Controllers;

import Beans.Trabajadores;
import Daos.TrabajadoresDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@WebServlet(name = "TrabajadoresServlet", value = "/TrabajadoresServlet")
public class TrabajadoresServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");

        RequestDispatcher view;
        TrabajadoresDao employeeDao = new TrabajadoresDao();

        switch (action) {
            case "lista":
                request.setAttribute("listaCantVent", employeeDao.listarventxemp());
                view = request.getRequestDispatcher("employees/lista.jsp");
                view.forward(request, response);
                break;
            default:
                response.sendRedirect("TrabajadoresServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}