package iMat;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.util.List;

public class HistoryListItemMain extends AnchorPane {

    @FXML private ImageView historyOrderImage;
    @FXML private Text historyOrderDateText;
    @FXML private Text historyOrderCountText;
    @FXML private Text historyOrderCostText;

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

    @FXML
    protected void onClick(Event event){
        parentController.updateHistoryListDetail(order.getItems());
    }

    private void addInfo(){
        historyOrderDateText.setText(order.getDate().toString());
        historyOrderCountText.setText(order.getItems().size() + "st");
        historyOrderCostText.setText(getOrderPrice(order.getItems()) + "kr");
    }

    private int getOrderPrice(List<ShoppingItem> items){
        int totalCost = 0;

        for (ShoppingItem item : items){
            totalCost += item.getTotal();
        }

        return totalCost;
    }
}
