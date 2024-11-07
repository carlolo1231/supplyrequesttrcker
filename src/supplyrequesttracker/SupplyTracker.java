package supplyrequesttracker;

import java.util.Scanner;

public class SupplyTracker {
    private Scanner scanner = new Scanner(System.in);
    private SupplyManager supplyManager;
    private DeliveryManager deliveryManager;
    private ReportManager reportManager;

    public SupplyTracker() {
        this.supplyManager = new SupplyManager(scanner);
        this.deliveryManager = new DeliveryManager(scanner);
        this.reportManager = new ReportManager(scanner);
    }

    public void run() {
        int choice;
        do {
            displayMainMenu();
            choice = getValidChoice();
            switch (choice) {
                case 1:
                    handleSupplies();
                    break;
                case 2:
                    handleDeliveries();
                    break;
                case 3:
                    handleReports();
                    break;
                case 4:
                    System.out.println("Exiting... Thank you for using the Supply Tracker System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
        scanner.close();
    }

    void displayMainMenu() {
        System.out.println("----------- Supply Tracker System Menu -----------");
        System.out.println("1. Supply                                       |");
        System.out.println("2. Deliver                                      |");
        System.out.println("3. Reports                                      |");
        System.out.println("4. Exit                                         |");
        System.out.println("-----------------------------------------------");
        System.out.print("Enter your choice:                            ");
    }

    private void handleSupplies() {
        int choice;
        do {
            System.out.println("----------- Supply Management Menu -----------");
            System.out.println("1. Add Supply                                 |");
            System.out.println("2. View Supplies                              |");
            System.out.println("3. Update Supply                              |");
            System.out.println("4. Delete Supply                              |");
            System.out.println("5. Back to Main Menu                         |");
            System.out.println("-----------------------------------------------");
            System.out.print("Enter your choice:                            ");
            choice = getValidChoice();

            switch (choice) {
                case 1:
                    supplyManager.addSupply();
                    break;
                case 2:
                    supplyManager.viewSupplies();
                    break;
                case 3:
                    supplyManager.updateSupply();
                    break;
                case 4:
                    supplyManager.deleteSupply();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private void handleDeliveries() {
        int choice;
        do {
            System.out.println("----------- Delivery Management Menu -----------");
            System.out.println("1. Add Delivery                               |");
            System.out.println("2. View Deliveries                            |");
            System.out.println("3. Update Delivery                            |");
            System.out.println("4. Delete Delivery                            |");
            System.out.println("5. Back to Main Menu                         |");
            System.out.println("-----------------------------------------------");
            System.out.print("Enter your choice:                            ");
            choice = getValidChoice();

            switch (choice) {
                case 1:
                    deliveryManager.addDelivery();
                    break;
                case 2:
                    deliveryManager.viewDeliveries();
                    break;
                case 3:
                    deliveryManager.updateDelivery();
                    break;
                case 4:
                    deliveryManager.deleteDelivery();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private void handleReports() {
        int choice;
        do {
            System.out.println("----------- Reports Management Menu -----------");
            System.out.println("           !(IN WORK PROGRESS)!                   ");
            System.out.println("1. View All Delivery Reports                   |");
            System.out.println("2. View Individual Delivery Report             |");
            System.out.println("3. Back to Main Menu                          |");
            System.out.println("-----------------------------------------------");
            System.out.print("Enter your choice:                            ");
            choice = getValidChoice();

            switch (choice) {
                case 1:
                    reportManager.viewAllReports();
                    break;
                case 2:
                    reportManager.viewIndividualReport();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }

    private int getValidChoice() {
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
                continue;
            }
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 4) {
                    return choice;
                } else {
                    System.out.println("Choice must be between 1 and 4. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    public static void main(String[] args) {
        SupplyTracker tracker = new SupplyTracker();
        tracker.run();
    }
}