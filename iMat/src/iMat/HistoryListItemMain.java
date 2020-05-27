package iMat;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.List;

public class HistoryListItemMain extends AnchorPane {

    @FXML private Text historyOrderDateText;
    @FXML private Text historyOrderCountText;
    @FXML private Text historyOrderPriceText;

    Order order;
    iMatController parentController;

    public HistoryListItemMain(Order order, iMatController controller){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("history_list_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        }catch (IOException exception){
            throw new RuntimeException(exception);
        }

        this.order = order;
        this.parentController = controller;

        addInfo();
    }

    // Runs when an order is clicked with the mouse
    // Calls the controller and populates the detail view
    @FXML
    protected void onClick(Event event){
        System.out.println("Loading Previous Order...");
        parentController.updateHistoryListDetail(order);
    }

    // Adds all order information to the image and text components
    private void addInfo(){
        historyOrderDateText.setText(order.getDate().toString());
        historyOrderCountText.setText(order.getItems().size() + "st");
        historyOrderPriceText.setText(getOrderPrice(order) + "kr");
    }

    // Returns the total price of an order
    private int getOrderPrice(Order order){
        List<ShoppingItem> items = order.getItems();

        int totalCost = 0;

        for (ShoppingItem item : items){
            totalCost += item.getTotal();
        }

        return totalCost;
    }
}
