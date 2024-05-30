package ntu.nmh61133637.shopeeapplicationfinalexam;

public class Cart {
    int id, productID, quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Cart() {
    }

    public Cart(int id, int productID, int quantity) {
        this.id = id;
        this.productID = productID;
        this.quantity = quantity;
    }
}
