package com.hotelmanagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password");
        
        if (password.equals(confirmPassword)) {
            try {
                // Load the JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Establish a connection
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management", "root", "5105080Pallavi#");

                // SQL query to insert data
                String query = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, username);
                pst.setString(2, email);
                pst.setString(3, password);

                int rowCount = pst.executeUpdate();
                if (rowCount > 0) {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Registration Successful!');");
                    out.println("window.location.href = 'login.html';");
                    out.println("</script>");
                } else {
                    out.println("<h1>Registration Failed!</h1>");
                }

                // Close connections
                pst.close();
                conn.close();
            } catch (ClassNotFoundException e) {
                out.println("JDBC Driver not found: " + e.getMessage());
            } catch (SQLException e) {
                out.println("SQL Error: " + e.getMessage());
            }
        } else {
            out.println("<h1>Passwords do not match!</h1>");
        }
    }
}
