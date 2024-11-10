package supplyrequesttracker;

public class Delivery {
    private int id;             
    private int supplyId;     
    private String supplyName;  
    private int quantity;       
    private String deliveryDate; 

  
   public Delivery(int deliveryId, int supplyId, int quantity, String deliveryDate, String supplyName) {
   
    this.supplyId = supplyId;
    this.quantity = quantity;
    this.deliveryDate = deliveryDate;
    this.supplyName = supplyName;
}

    public int getId() { return id; }
    public String getSupplyName() { return supplyName; }
    public int getQuantity() { return quantity; }
    public String getDeliveryDate() { return deliveryDate; }
}
