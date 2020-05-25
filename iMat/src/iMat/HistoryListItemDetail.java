package iMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.ShoppingItem;

import javax.swing.text.html.ImageView;
import java.io.IOException;

public class HistoryListItemDetail extends AnchorPane {

    @FXML private ImageView historyProductImage;
    @FXML private Text historyProductText;
    @FXML private Text historyProductCountText;
    @FXML private Text historyProductCostText;

    ShoppingItem item;
    iMatController parentController;

    public HistoryListItemDetail(ShoppingItem item, iMatController controller){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("history_list_item_v.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        }catch (IOException exception){
            throw new RuntimeException(exception);
        }

        this.item = item;
        this.parentController = controller;

        addInfo();
    }

    private void addInfo(){
        historyProductText.setText(item.getProduct().getName());
        historyProductCostText.setText(item.getTotal() + "kr");
        historyProductCountText.setText(item.getAmount() + "st");
    }
}
