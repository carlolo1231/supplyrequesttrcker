package supplyrequesttracker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DeliveryManager {
    private config con = new config();
    private Scanner scanner;

    public DeliveryManager(Scanner scanner) {
        this.scanner = scanner;
    }

   

  public void addDelivery() {
    int supplyId = getValidIntInput("Enter Supply ID for delivery: ");
    if (!supplyExists(supplyId)) {
        System.out.println("No supply found with ID: " + supplyId);
        return;
    }
    int quantity = getValidIntInput("Enter Quantity for delivery: ");
    String deliveryDate = getValidStringInput("Enter Delivery Date (YYYY-MM-DD): ");

    String sql = "INSERT INTO Deliveries (supply_id, quantity, delivery_date) VALUES (?, ?, ?)";
    try (Connection conn = con.connectDB();
         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        stmt.setInt(1, supplyId);
        stmt.setInt(2, quantity);
        stmt.setString(3, deliveryDate);
        stmt.executeUpdate();

        
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        int generatedDeliveryId = 0;
        if (generatedKeys.next()) {
            generatedDeliveryId = generatedKeys.getInt(1);
        }

        
        String supplyName = getSupplyNameById(supplyId);  

       
        Delivery delivery = new Delivery(generatedDeliveryId, supplyId, quantity, deliveryDate, supplyName);

       

        System.out.println("Delivery and report added successfully.");
    } catch (SQLException e) {
        System.out.println("Error adding delivery: " + e.getMessage());
    }
}



    public void viewDeliveries() {
        String sql = "SELECT d.delivery_id, s.name, d.quantity, d.delivery_date "
                + "FROM Deliveries d JOIN Supplies s ON d.supply_id = s.supply_id";
        
        try (Connection conn = con.connectDB();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n=================== Delivery List ===================");
            System.out.printf("%-15s %-25s %-10s %-15s%n", "Delivery ID", "Supply", "Quantity", "Delivery Date");
            System.out.println("-------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-15d %-25s %-10d %-15s%n",
                        rs.getInt("delivery_id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getString("delivery_date"));
            }
            System.out.println("=======================================================\n");

        } catch (SQLException e) {
            System.out.println("Error retrieving deliveries: " + e.getMessage());
        }
    }

    public void updateDelivery() {
        int id = getValidIntInput("Enter Delivery ID to update: ");
        if (!deliveryExists(id)) {
            System.out.println("No delivery found with ID: " + id);
            return;
        }

        int supplyId = getValidIntInput("Enter new Supply ID (hit enter ra if wlay changes): ");
        int quantity = getValidIntInput("Enter new Quantity (hit enter ra if wlay changes): ");
        String deliveryDate = getValidStringInput("Enter new Delivery Date (hit enter ra if wlay changes): ");

        String updateSql = "UPDATE Deliveries SET supply_id = COALESCE(NULLIF(?, -1), supply_id), "
                + "quantity = COALESCE(NULLIF(?, -1), quantity), "
                + "delivery_date = COALESCE(NULLIF(?, ''), delivery_date) WHERE delivery_id = ?";
        try (Connection conn = con.connectDB();
             PreparedStatement stmt = conn.prepareStatement(updateSql)) {
            stmt.setInt(1, supplyId);
            stmt.setInt(2, quantity);
            stmt.setString(3, deliveryDate);
            stmt.setInt(4, id);
            stmt.executeUpdate();
            System.out.println("Delivery updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating delivery: " + e.getMessage());
        }
    }

    public void deleteDelivery() {
        int id = getValidIntInput("Enter Delivery ID to delete: ");
        if (!deliveryExists(id)) {
            System.out.println("No delivery found with ID: " + id);
            return;
        }

        String sql = "DELETE FROM Deliveries WHERE delivery_id = ?";
        try (Connection conn = con.connectDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Delivery deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting delivery: " + e.getMessage());
        }
    }

    private boolean deliveryExists(int id) {
        String sql = "SELECT COUNT(*) FROM Deliveries WHERE delivery_id = ?";
        try (Connection conn = con.connectDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.out.println("Error checking delivery existence: " + e.getMessage());
            return false;
        }
    }

    private int getValidIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
                continue;
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    private String getValidStringInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            } else {
                System.out.println("Input cannot be empty. Please try again.");
            }
        }
    }
     public boolean supplyExists(int id) {
        String sql = "SELECT COUNT(*) FROM Supplies WHERE supply_id = ?";
        try (Connection conn = con.connectDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.out.println("Error checking supply existence: " + e.getMessage());
            return false;
        }
    }
     private String getSupplyNameById(int supplyId) {
    String sql = "SELECT name FROM Supplies WHERE supply_id = ?";
    try (Connection conn = con.connectDB(); 
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, supplyId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getString("name"); 
        } else {
            System.out.println("No supply found with ID: " + supplyId);
            return null;  
        }
    } catch (SQLException e) {
        System.out.println("Error fetching supply name: " + e.getMessage());
        return null;  
    }
}

}