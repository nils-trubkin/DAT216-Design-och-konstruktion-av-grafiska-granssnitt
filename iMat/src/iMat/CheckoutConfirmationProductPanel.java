package iMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.ShoppingItem;


import java.io.IOException;

public class CheckoutConfirmationProductPanel extends AnchorPane {

    @FXML private Text name;
    @FXML private Text amount;
    @FXML private Text pricePerUnit;
    @FXML private Text totalPrice;

    private ShoppingItem item;

    public CheckoutConfirmationProductPanel(ShoppingItem item) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("checkout_confirmation_product.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.item = item;

        name.setText(item.getProduct().getName());
        amount.setText(String.valueOf(item.getAmount()));
        pricePerUnit.setText(item.getProduct().getPrice() + "kr /" + item.getProduct().getUnit());
        totalPrice.setText(String.valueOf(item.getTotal()));
    }
}
