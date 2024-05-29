package ntu.nmh61133637.shopeeapplicationfinalexam;

public class Product {
    int id;
    String name, image;
    int price, saleCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }

    public Product() {
    }

    public Product(int id, String name, String image, int price, int saleCount) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.saleCount = saleCount;
    }
}
