package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import patterns.FarmComponent;
import patterns.FarmItem;
import patterns.ItemContainer;

public class MainController implements Initializable {

    @FXML
    private ImageView droneImageView;
    @FXML
    private AnchorPane screen2; // Initial visualization area
    @FXML
    private AnchorPane screen1; // Visualization pane for the farm layout
    @FXML
    private Button scanFarmButton;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private ComboBox<String> comboBox2;
    @FXML
    private TextArea statusTextArea;
    
    @FXML
    private TextField xField;
    @FXML
    private TextField yField;
    @FXML
    private TextField widthField;
    @FXML
    private TextField heightField;

    @FXML
    private Button addButton;
    @FXML
    private TextField nameField;
    @FXML
    private Button modifyButton;
    @FXML
    private Button saveButton;


    // Command Center Coordinates (top-left of the scan area)
    private final double commandCenterX = 0;
    private final double commandCenterY = 0;

    // Root container for the entire farm layout (Step 2.1)
    private ItemContainer rootContainer = new ItemContainer("Farm Layout", 0, 0, 800, 600);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Populate combo boxes
        comboBox.setItems(FXCollections.observableArrayList("RENAME", "CHANGE DIMENSIONS", "CHANGE LOCATION", "CHANGE PRICE", "DELETE ITEM"));
        comboBox2.setItems(FXCollections.observableArrayList("RENAME", "CHANGE DIMENSIONS", "CHANGE LOCATION", "CHANGE PRICE", "DELETE ITEM", "ADD ITEM", "ADD ITEM CONTAINER"));

        // Load the drone image
        droneImageView.setImage(new Image("file:src/application/drone.png")); // Replace with your drone image path
        droneImageView.setX(commandCenterX);
        droneImageView.setY(commandCenterY);

        // Attach button handlers
        scanFarmButton.setOnAction(event -> handleScanFarm());
    }

    // Handle the scan farm action
    @FXML
    public void handleScanFarm() {
        startDroneScan();
    }

    // Create a new layout (Step 2.2)
    @FXML
    public void createLayout() {
        statusTextArea.appendText("Creating a new farm layout...\n");

        try {
            // Get the name from the user input
            String name = nameField.getText().trim();

            // Validate that a name is provided
            if (name.isEmpty()) {
                statusTextArea.appendText("Error: Name cannot be empty.\n");
                return;
            }

            // Get the position and dimensions from the input fields
            double x = Double.parseDouble(xField.getText());
            double y = Double.parseDouble(yField.getText());
            double width = Double.parseDouble(widthField.getText());
            double height = Double.parseDouble(heightField.getText());

            // Create a new container with the user-provided name
            ItemContainer newContainer = new ItemContainer(name, x, y, width, height);
            rootContainer.add(newContainer);

            // Visualize the new layout
            Rectangle rectangle = new Rectangle(x, y, width, height);
            rectangle.setFill(javafx.scene.paint.Color.LIGHTBLUE);
            rectangle.setStroke(javafx.scene.paint.Color.BLACK);
            screen1.getChildren().add(rectangle);

            statusTextArea.appendText("Added " + name + " to the farm layout.\n");
        } catch (NumberFormatException e) {
            statusTextArea.appendText("Error: Invalid input! Please enter valid numeric values.\n");
        }
    }


  
    @FXML
    public void modifyLayout() {
        statusTextArea.appendText("Modifying the farm layout...\n");

        try {
            // Get the name from the user input
            String nameToModify = nameField.getText().trim();

            // Validate that a name is provided
            if (nameToModify.isEmpty()) {
                statusTextArea.appendText("Error: Name cannot be empty.\n");
                return;
            }

            // Get new dimensions
            double newWidth = Double.parseDouble(widthField.getText());
            double newHeight = Double.parseDouble(heightField.getText());

            // Iterate through components in rootContainer
            for (FarmComponent component : rootContainer.getComponents()) {
                if (component.getName().equals(nameToModify)) {
                    // Update component dimensions
                    component.setWidth(newWidth);
                    component.setHeight(newHeight);

                    // Update visualization
                    for (javafx.scene.Node node : screen1.getChildren()) {
                        if (node instanceof Rectangle) {
                            Rectangle rectangle = (Rectangle) node;

                            // Match rectangle to the component's position
                            if (rectangle.getX() == component.getX() && rectangle.getY() == component.getY()) {
                                rectangle.setWidth(newWidth);
                                rectangle.setHeight(newHeight);
                                break;
                            }
                        }
                    }

                    statusTextArea.appendText("Modified " + nameToModify + " in the farm layout.\n");
                    return;
                }
            }

            // No match found
            statusTextArea.appendText("Error: No matching component found to modify.\n");
        } catch (NumberFormatException e) {
            statusTextArea.appendText("Error: Invalid input! Please enter valid numeric values.\n");
        }
    }




    // Save the layout (Step 2.4)
    @FXML
    public void saveLayout() {
        statusTextArea.appendText("Saving the current farm layout...\n");
        rootContainer.display();
        statusTextArea.appendText("Farm layout saved.\n");
    }

    // Drone scan animation
    public void startDroneScan() {
        statusTextArea.appendText("Drone has started scanning the farm.\n");

        SequentialTransition sequentialTransition = new SequentialTransition();
        double stepSize = 50.0;
        double visualizationWidth = screen1.getPrefWidth();
        double visualizationHeight = screen1.getPrefHeight();
        double startX = 0.0;
        double startY = 0.0;

        droneImageView.setX(startX);
        droneImageView.setY(startY);

        int rows = (int) (visualizationHeight / stepSize);
        int cols = (int) (visualizationWidth / stepSize);

        for (int row = 0; row < rows; row++) {
            TranslateTransition moveRow = new TranslateTransition(Duration.seconds(2), droneImageView);
            moveRow.setByX((row % 2 == 0 ? 1 : -1) * (cols - 1) * stepSize);
            sequentialTransition.getChildren().add(moveRow);

            if (row < rows - 1) {
                TranslateTransition moveDown = new TranslateTransition(Duration.seconds(1), droneImageView);
                moveDown.setByY(stepSize);
                sequentialTransition.getChildren().add(moveDown);
            }
        }

        TranslateTransition returnToCommandCenter = new TranslateTransition(Duration.seconds(2), droneImageView);
        returnToCommandCenter.setToX(startX - droneImageView.getX());
        returnToCommandCenter.setToY(startY - droneImageView.getY());
        sequentialTransition.getChildren().add(returnToCommandCenter);

        sequentialTransition.setOnFinished(event -> {
            statusTextArea.appendText("Drone has finished scanning the farm.\n");
        });

        sequentialTransition.play();
    }
}
