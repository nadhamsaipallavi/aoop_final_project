package com.hotelmanagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish a connection
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management", "root", "5105080Pallavi#");

            // SQL query to check if the user exists
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                // User found
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Login Successful!');");
                out.println("window.location.href = 'index.html';"); // redirect to a welcome page
                out.println("</script>");
            } else {
                // User not found
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Invalid username or password!');");
                out.println("window.location.href = 'login.html';"); // redirect back to the login page
                out.println("</script>");
            }

            // Close connections
            rs.close();
            pst.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            out.println("JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            out.println("SQL Error: " + e.getMessage());
        }
    }
}
