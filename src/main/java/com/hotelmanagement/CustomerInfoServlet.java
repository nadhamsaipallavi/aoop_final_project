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


public class CustomerInfoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/hotel_management";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "5105080Pallavi#";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);

            String sql = "SELECT * FROM users";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            out.println("<html><body><h2>Customer Information</h2><table border='1'>");
            out.println("<tr><th>Username</th><th>Email</th></tr>");

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");

                out.println("<tr><td>" + username + "</td><td>" + email + "</td></tr>");
            }

            out.println("</table></body></html>");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("<html><body><h3>An error occurred while fetching customer details.</h3></body></html>");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

