package supplyrequesttracker;

public class DeliveryReport {
    private Delivery delivery;

    public DeliveryReport(Delivery delivery) {
        this.delivery = delivery;
    }

    public Delivery getDelivery() {
        return delivery;
    }
}