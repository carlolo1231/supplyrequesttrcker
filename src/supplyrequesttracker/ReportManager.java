package supplyrequesttracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReportManager {
    private List<DeliveryReport> reports = new ArrayList<>(); 
    private Scanner scanner;

    public ReportManager(Scanner scanner) {
        this.scanner = scanner;
    }

    public void addReport(Delivery delivery) {
        reports.add(new DeliveryReport(delivery));
        System.out.println("Report added successfully.");
    }

    public void viewAllReports() {
        if (reports.isEmpty()) {
            System.out.println("No delivery reports found.");
            return;
        }
        System.out.println("\n=================== Delivery Reports ===================");
        System.out.printf("%-15s %-15s %-25s %-10s %-15s%n", 
            "Report ID", "Delivery ID", "Supply", "Quantity", "Delivery Date");
        System.out.println("----------------------------------------------------------");
        for (int i = 0; i < reports.size(); i++) {
            Delivery delivery = reports.get(i).getDelivery();
            System.out.printf("%-15d %-15d %-25s %-10d %-15s%n",
                i + 1, 
                delivery.getId(),
                delivery.getSupplyName(),
                delivery.getQuantity(),
                delivery.getDeliveryDate());
        }
        System.out.println("==========================================================\n");
    }

    public void viewIndividualReport() {
        if (reports.isEmpty()) {
            System.out.println("No report found.");
            return;
        }

        int reportId = getValidIntInput("Enter Report ID to view: ");
        if (reportId < 1 || reportId > reports.size()) {
            System.out.println("Invalid Report ID.");
            return;
        }

        Delivery delivery = reports.get(reportId - 1).getDelivery();
        System.out.printf("Report ID: %d, Delivery ID: %d, Supply: %s, Quantity: %d, Date: %s%n",
            reportId, 
            delivery.getId(),
            delivery.getSupplyName(),
            delivery.getQuantity(),
            delivery.getDeliveryDate());
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
}