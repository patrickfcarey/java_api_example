import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseQuery {
    public static void main(String[] args) {
        // Database credentials
        String url = "jdbc:mysql://localhost:3306/myapp"; // Replace 'mydb' with your database name
        String username = "root"; // Replace with your MySQL username
        String password = "CHANGE THIS"; // Replace with your MySQL password

        try {
            // Establish connection
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database!");

            // Create a statement
            Statement stmt = conn.createStatement();

            // Execute a query
            String sql = "SELECT * FROM person"; // Replace 'users' with your table name
            ResultSet rs = stmt.executeQuery(sql);

            // Process the results
            while (rs.next()) {
                int id = rs.getInt("id"); // Replace 'id' with your column name
                String name = rs.getString("name"); // Replace 'name' with your column name
                String email = rs.getString("email"); // Replace 'email' with your column name
                System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email);
            }

            // Clean up
            rs.close();
            stmt.close();
            conn.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}