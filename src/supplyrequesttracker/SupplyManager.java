package supplyrequesttracker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SupplyManager {
    private config con = new config();
    private Scanner scanner;

    public SupplyManager(Scanner scanner) {
        this.scanner = scanner;
    }

    public void addSupply() {
        String name = getValidStringInput("Enter Supply Name: ");
        int quantity = getValidIntInput("Enter Quantity: ");
        String supplier = getValidStringInput("Enter Supplier Name: ");

        String sql = "INSERT INTO Supplies (name, quantity, supplier) VALUES (?, ?, ?)";
        try (Connection conn = con.connectDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, quantity);
            stmt.setString(3, supplier);
            stmt.executeUpdate();
            System.out.println("Supply added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding supply: " + e.getMessage());
        }
    }

    public void viewSupplies() {
        String sql = "SELECT * FROM Supplies";
        try (Connection conn = con.connectDB();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n================ Supply List ======================");
            System.out.printf("%-10s %-25s %-10s %-25s%n", "ID", "Name", "Quantity", "Supplier");
            System.out.println("---------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-10d %-25s %-10d %-25s%n",
                        rs.getInt("supply_id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getString("supplier"));
            }
            System.out.println("===================================================\n");

        } catch (SQLException e) {
            System.out.println("Error retrieving supplies: " + e.getMessage());
        }
    }

    public void updateSupply() {
        int id = getValidIntInput("Enter Supply ID to update: ");
        if (!supplyExists(id)) {
            System.out.println("No supply found with ID: " + id);
            return;
        }
        String name = getValidStringInput("Enter new Supply Name (or leave empty to keep current): ");
        int quantity = getValidIntInput("Enter new Quantity (or leave empty to keep current): ");
        String supplier = getValidStringInput("Enter new Supplier Name (or leave empty to keep current): ");

        String sql = "UPDATE Supplies SET name = COALESCE(NULLIF(?, ''), name), "
                + "quantity = COALESCE(NULLIF(?, -1), quantity), "
                + "supplier = COALESCE(NULLIF(?, ''), supplier) WHERE supply_id = ?";
        try (Connection conn = con.connectDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, quantity);
            stmt.setString(3, supplier);
            stmt.setInt(4, id);
            stmt.executeUpdate();
            System.out.println("Supply updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating supply: " + e.getMessage());
        }
    }

    public void deleteSupply() {
        int id = getValidIntInput("Enter Supply ID to delete: ");
        if (!supplyExists(id)) {
            System.out.println("No supply found with ID: " + id);
            return;
        }

        String sql = "DELETE FROM Supplies WHERE supply_id = ?";
        try (Connection conn = con.connectDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Supply deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting supply: " + e.getMessage());
        }
    }

    private boolean supplyExists(int id) {
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
}