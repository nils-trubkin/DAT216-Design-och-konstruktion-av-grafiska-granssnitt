package iMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class HistoryListItemDetail extends AnchorPane {

    @FXML private ImageView historyProductImage;
    @FXML private Text historyProductText;
    @FXML private Text historyProductCountText;
    @FXML private Text historyProductPriceText;

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

    // Adds all order information to the image and text components
    private void addInfo(){
        historyProductImage.setImage(Model.getInstance().getImage(item.getProduct()));
        historyProductText.setText(item.getProduct().getName());
        historyProductPriceText.setText(Math.round(item.getTotal() * 100) / 100.0 + " kr");
        if (item.getProduct().getUnitSuffix().equals("kg"))
            historyProductCountText.setText(Math.round(item.getAmount() * 10) / 10.0 + " " + item.getProduct().getUnitSuffix());
        else
            historyProductCountText.setText(Math.round(item.getAmount()) + " " + item.getProduct().getUnitSuffix());
    }
}
