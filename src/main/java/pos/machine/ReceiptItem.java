package pos.machine;


public class ReceiptItem {

    private  String name;
    private  int subTotal;
    private  int quantity;
    private  int unitPrice;


    public void setName(String name) {
        this.name = name;
    }
    public void setQuantity( Integer countOfItems) {
        this.quantity = countOfItems;
    }
    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }
    public void setSubTotal(int quantity, int unitPrice) {
        this.subTotal = quantity * unitPrice;
    }


    public String getName() {
        return name;
    }


    public int getQuantity() {
        return quantity;
    }
    public int getUnitPrice() {return unitPrice;}

    public int getSubTotal() {
        return subTotal;
    }
}
