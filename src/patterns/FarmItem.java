package patterns;

public class FarmItem extends FarmComponent {

    private double price;

    public FarmItem(String name, double x, double y, double width, double height, double price) {
        super(name, x, y, width, height);
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public void add(FarmComponent component) {
        throw new UnsupportedOperationException("Cannot add to a leaf item.");
    }

    @Override
    public void remove(FarmComponent component) {
        throw new UnsupportedOperationException("Cannot remove from a leaf item.");
    }

    @Override
    public void display() {
        System.out.println("Item: " + name + " at (" + x + ", " + y + "), Dimensions: " + width + "x" + height + ", Price: " + price);
    }
}
