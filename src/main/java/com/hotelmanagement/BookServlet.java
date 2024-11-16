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


public class BookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String checkin = request.getParameter("checkin");
        String checkout = request.getParameter("checkout");
        String roomtype = request.getParameter("roomtype");

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish a connection
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management", "root", "5105080Pallavi#");

            // SQL query to insert data
            String query = "INSERT INTO hotel_bookings (name, email, phone, checkin_date, checkout_date, room_type) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, phone);
            pst.setString(4, checkin);
            pst.setString(5, checkout);
            pst.setString(6, roomtype);

            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Booking Successful!');");
                out.println("window.location.href = 'payment.html';");
                out.println("</script>");
            } else {
                out.println("<h1>Booking Failed!</h1>");
            }

            // Close connections
            pst.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            out.println("JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            out.println("SQL Error: " + e.getMessage());
        }
    }
}
