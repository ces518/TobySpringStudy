package me.june.mvc.serlvet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

public class SimpleGetServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");

        PrintWriter writer = response.getWriter();
        writer.print("<html><body>");
        writer.print("Hello " + name);
        writer.print("</body></html>");
    }
}
