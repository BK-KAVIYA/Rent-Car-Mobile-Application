package domain;

public class CarDomain {
    private String name;
    private String description;
    private String image;
    private int rate;
    private int numberInCart;

    public CarDomain(String name, String description, String image, int rate) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.rate = rate;
    }

    public CarDomain(String name, String description, String image, int rate, int numberInCart) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.rate = rate;
        this.numberInCart = numberInCart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }
}
