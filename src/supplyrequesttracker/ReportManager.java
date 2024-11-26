package supplyrequesttracker;

import java.sql.*;
import java.util.Scanner;

public class ReportManager {
    private Scanner scanner;
    private Connection con;  

    public ReportManager(Scanner scanner) {
        this.scanner = scanner;
       this.con = config.connectDB();

    }

   
    public void viewAllReports() {
        String sql = "SELECT d.delivery_id, s.name AS supply_name, d.quantity, d.delivery_date " +
                     "FROM Deliveries d " +
                     "JOIN Supplies s ON d.supply_id = s.supply_id";

        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n=================== Delivery Reports ===================");
            System.out.printf("%-15s %-25s %-10s %-15s%n", 
                "Delivery ID", "Supply", "Quantity", "Delivery Date");
            System.out.println("----------------------------------------------------------");

            while (rs.next()) {
                
                int deliveryId = rs.getInt("delivery_id");
                String supplyName = rs.getString("supply_name");
                int quantity = rs.getInt("quantity");
                String deliveryDate = rs.getString("delivery_date");

               
                System.out.printf("%-15d %-25s %-10d %-15s%n",
                    deliveryId, supplyName, quantity, deliveryDate);
            }

            System.out.println("==========================================================\n");

        } catch (SQLException e) {
            System.out.println("Error fetching reports: " + e.getMessage());
        }
    }

  
    public void viewIndividualReport() {
        DeliveryManager.viewDeliveries();
        int deliveryId = getValidIntInput("Enter Delivery ID to view report: ");
        
        String sql = "SELECT d.delivery_id, s.name AS supply_name, d.quantity, d.delivery_date " +
                     "FROM Deliveries d " +
                     "JOIN Supplies s ON d.supply_id = s.supply_id " +
                     "WHERE d.delivery_id = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, deliveryId);  

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    
                    String supplyName = rs.getString("supply_name");
                    int quantity = rs.getInt("quantity");
                    String deliveryDate = rs.getString("delivery_date");

                    
                    System.out.println("\n===============================================");
                    System.out.println("               Individual Report              ");
                    System.out.println("===============================================");
                    System.out.printf("%-20s: %d%n", "Delivery ID", deliveryId);
                    System.out.printf("%-20s: %s%n", "Supply", supplyName);
                    System.out.printf("%-20s: %d%n", "Quantity Delivered", quantity);
                    System.out.printf("%-20s: %s%n", "Delivery Date", deliveryDate);
                    System.out.println("===============================================");
                } else {
                    System.out.println("No report found for Delivery ID: " + deliveryId);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching individual report: " + e.getMessage());
        }
    }
    public void viewIndividualSupplyReport() {
    
    DeliveryManager.viewDeliveries();
    
  
    int deliveryId = getValidIntInput("Enter Delivery ID to view supply report: ");
    
    
    String sql = "SELECT d.delivery_id, s.name AS supply_name, d.quantity, d.delivery_date, d.status " +
                 "FROM Deliveries d " +
                 "JOIN Supplies s ON d.supply_id = s.supply_id " +
                 "WHERE d.delivery_id = ?";

    try (PreparedStatement stmt = con.prepareStatement(sql)) {
        stmt.setInt(1, deliveryId);  

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
               
                String supplyName = rs.getString("supply_name");
                int quantity = rs.getInt("quantity");
                String deliveryDate = rs.getString("delivery_date");
                String status = rs.getString("status");

               
                System.out.println("\n===============================================");
                System.out.println("               Individual Supply Report        ");
                System.out.println("===============================================");
                System.out.printf("%-20s: %d%n", "Supply ID", deliveryId);
                System.out.printf("%-20s: %s%n", "Supply", supplyName);
                System.out.printf("%-20s: %d%n", "Quantity", quantity);
                System.out.printf("%-20s: %s%n", "Date", deliveryDate);
                System.out.printf("%-20s: %s%n", "Status Of the Delivery", status);
                System.out.println("===============================================");
            } else {
                System.out.println("No report found for Delivery ID: " + deliveryId);
            }
        }
    } catch (SQLException e) {
        System.out.println("Error fetching individual supply report: " + e.getMessage());
    }
}

   
    public int getValidIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim(); 
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
                continue;
            }

            try {
                int value = Integer.parseInt(input); 
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }
}
