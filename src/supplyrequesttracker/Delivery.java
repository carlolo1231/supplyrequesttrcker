package supplyrequesttracker;

public class Delivery {
    private int id;
    private String supplyName;
    private int quantity;
    private String deliveryDate;

    public Delivery(int id, String supplyName, int quantity, String deliveryDate) {
        this.id = id;
        this.supplyName = supplyName;
        this.quantity = quantity;
        this.deliveryDate = deliveryDate;
    }

    public int getId() {
        return id;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }
}