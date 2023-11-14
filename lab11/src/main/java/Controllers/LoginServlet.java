package Controllers;

import Beans.Trabajadores;
import Daos.TrabajadoresDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession httpSession = request.getSession();
        Trabajadores employeeLogged = (Trabajadores) httpSession.getAttribute("usuarioLogueado");

        if(employeeLogged != null && employeeLogged.getEmployeeId() > 0){

            if(request.getParameter("a") != null){//logout
                httpSession.invalidate();
            }
            response.sendRedirect(request.getContextPath());
        }else{
            request.getRequestDispatcher("loginForm.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("username: " + username + " | password: " + password);
        TrabajadoresDao trabajadoresDao = new TrabajadoresDao();

        if(trabajadoresDao.validarUsuarioPasswordHashed(username,password)){
            System.out.println("usuario y password válidos");
            Trabajadores trabajador = trabajadoresDao.obtenerEmpleado(username);
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("usuarioLogueado",trabajador);
            response.sendRedirect(request.getContextPath());
        }else{
            System.out.println("usuario o password incorrectos");
            request.setAttribute("err","Usuario o password incorrectos");
            request.getRequestDispatcher("loginForm.jsp").forward(request,response);
        }
    }
}