package patterns;

import java.util.ArrayList;
import java.util.List;

public class ItemContainer extends FarmComponent {
    private List<FarmComponent> components = new ArrayList<>();

    public ItemContainer(String name, double x, double y, double width, double height) {
        super(name, x, y, width, height);
    }

    @Override
    public void add(FarmComponent component) {
        components.add(component);
    }

    @Override
    public void remove(FarmComponent component) {
        components.remove(component);
    }

    @Override
    public void display() {
        System.out.println("Container: " + name + " at (" + x + ", " + y + "), Dimensions: " + width + "x" + height);
        for (FarmComponent component : components) {
            component.display();
        }
    }

    public List<FarmComponent> getComponents() {
        return components;
    }
}

