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


public class BookingDetailsServlet extends HttpServlet {
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

            String sql = "SELECT * FROM hotel_bookings";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            out.println("<html><body><h2>Booking Details</h2><table border='1'>");
            out.println("<tr><th>Name</th><th>Email</th><th>Phone</th><th>Check-in Date</th><th>Check-out Date</th><th>Room Type</th><th>Booking Date</th></tr>");

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String checkinDate = resultSet.getString("checkin_date");
                String checkoutDate = resultSet.getString("checkout_date");
                String roomType = resultSet.getString("room_type");
                String bookingDate = resultSet.getString("booking_date");

                out.println("<tr><td>" + name + "</td><td>" + email + "</td><td>" + phone + "</td><td>" + checkinDate + "</td><td>" + checkoutDate + "</td><td>" + roomType + "</td><td>" + bookingDate + "</td></tr>");
            }

            out.println("</table></body></html>");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("<html><body><h3>An error occurred while fetching booking details.</h3></body></html>");
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

