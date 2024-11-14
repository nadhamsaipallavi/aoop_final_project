package com.hotelmanagement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class BookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database credentials
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/hotel_management";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "5105080Pallavi#";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html");

        // Retrieve form parameters
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String checkinDate = request.getParameter("checkin");
        String checkoutDate = request.getParameter("checkout");
        String roomType = request.getParameter("roomtype");

        // JDBC variables
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);

            // Prepare SQL statement
            String sql = "INSERT INTO hotel_bookings (name, email, phone, checkin_date, checkout_date, room_type) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, checkinDate);
            preparedStatement.setString(5, checkoutDate);
            preparedStatement.setString(6, roomType);

            // Execute SQL statement
            int rowsInserted = preparedStatement.executeUpdate();

            // Respond to client
            if (rowsInserted > 0) {
                response.getWriter().println("<html><body><script>alert('Booking successful!');window.location='payment.html';</script></body></html>");
            } else {
                response.getWriter().println("<html><body><script>alert('Booking failed. Please try again.');window.history.back();</script></body></html>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.getWriter().println("<html><body><script>alert('An error occurred: " + e.getMessage() + "');window.history.back();</script></body></html>");
        } finally {
            // Close resources
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
