package iMat;

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;


public class CheckoutProductPanel extends AnchorPane {

    @FXML private ImageView imageView;
    @FXML private Label nameLabel;
    @FXML private Text pricePerUnit;
    @FXML private Text totalPrice;
    @FXML private Text amountLabel;

    /*@FXML Label prizeLabel;
    @FXML Label ecoLabel;*/

    private Model model = Model.getInstance();

    private ShoppingItem item;

    private final static double kImageWidth = 100.0;
    private final static double kImageRatio = 0.75;

    public CheckoutProductPanel(ShoppingItem item) {

//        ResourceBundle bundle = java.util.ResourceBundle.getBundle("iMat/resources/iMat");
//        Parent root = FXMLLoader.load(getClass().getResource("imat_about.fxml"), bundle);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("checkout_product.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.item = item;
        nameLabel.setText(item.getProduct().getName());
        pricePerUnit.setText("Pris: " + item.getProduct().getUnit() + ": " + item.getProduct().getPrice() + " kr");
        imageView.setImage(model.getImage(item.getProduct(), kImageWidth, kImageWidth*kImageRatio));
        updateTotalPrice();
        updateAmount();

//        amountLabel.setText(String.valueOf(getAmount(products)));
//        System.out.println(nameLabel.toString());

    }

    private void updateAmount() {
        if (model.getShoppingCart().getItems().size() == 0) amountLabel.setText("0");
        for (ShoppingItem si : model.getShoppingCart().getItems()){
            if(si.getProduct().equals(item.getProduct())){
                double value = (Math.round(si.getAmount() * 10) / 10.0);
                if(si.getProduct().getUnitSuffix().equals("kg")){
                    amountLabel.setText(String.valueOf(value));
                }
                else{
                    amountLabel.setText(String.valueOf((int) value));
                }
            }
        }
    }

    private void updateTotalPrice() {
        totalPrice.setText("Totalt: " + item.getTotal());
    }

    @FXML
    private void handleAddAction(ActionEvent event) {
        System.out.println("Add " + item.getProduct().getName());
        model.addToShoppingCart(item.getProduct());
        for (ShoppingItem si : model.getShoppingCart().getItems()){
            if(si.getProduct().equals(item.getProduct())){
                double value = (Math.round(si.getAmount() * 10) / 10.0);
                if(si.getProduct().getUnitSuffix().equals("kg")){
                    amountLabel.setText(String.valueOf(value));
                }
                else{
                    amountLabel.setText(String.valueOf((int) value));
                }
            }
        }
        updateTotalPrice();
    }

    @FXML
    private void handleSubtractAction(ActionEvent event) {
        System.out.println("Subtract " + item.getProduct().getName());
        model.subtractFromShoppingCart(item.getProduct());
        for (ShoppingItem si : model.getShoppingCart().getItems()){
            if(si.getProduct().equals(item.getProduct())){
                double value = (Math.round(si.getAmount() * 10) / 10.0);
                if(si.getProduct().getUnitSuffix().equals("kg")){
                    amountLabel.setText(String.valueOf(value));
                }
                else{
                    amountLabel.setText(String.valueOf((int) value));
                }
                updateTotalPrice();

                return;
            }
        }
        amountLabel.setText(String.valueOf(0));
        updateTotalPrice();
    }

    public void deleteAll() {
        double end = item.getAmount();
        if (item.getProduct().getUnitSuffix().equals("kg")) {
            for (int n = 0; n < end*10; n++) {
                System.out.println(item.getAmount());
                model.subtractFromShoppingCart(item.getProduct());
                updateTotalPrice();
                updateAmount();
            }

        } else{
            for (int i = 0; i < end + 1; i++) {
                System.out.println(item.getAmount());
                model.subtractFromShoppingCart(item.getProduct());
                updateTotalPrice();
                updateAmount();
            }
        }
    }
}
