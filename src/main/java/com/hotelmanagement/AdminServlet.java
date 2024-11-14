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

public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String jdbcURL = "jdbc:mysql://localhost:3306/hotel_management";
    private static final String jdbcUsername = "root";
    private static final String jdbcPassword = "5105080Pallavi#";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

            String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                out.println("<html><body><script>alert('Login successful!');window.location='index2.html';</script></body></html>");
            } else {
                out.println("<html><body><script>alert('Invalid username or password.');window.history.back();</script></body></html>");
            }

            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            out.println("<html><body><script>alert('JDBC Driver not found: " + e.getMessage() + "');window.history.back();</script></body></html>");
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<html><body><script>alert('Database connection problem: " + e.getMessage() + "');window.history.back();</script></body></html>");
        }
    }
}
