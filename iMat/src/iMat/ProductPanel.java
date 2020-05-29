package iMat;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class ProductPanel extends AnchorPane {

    @FXML ImageView imageView;
    @FXML Label nameLabel;
    @FXML Label amountLabel;
    @FXML Label priceLabel;
    @FXML Button subtractButton;
    //@FXML Label ecoLabel;

    private Model model = Model.getInstance();

    private Product product;

    private final static double kImageWidth = 100.0;
    private final static double kImageRatio = 0.75;

    public ProductPanel(Product product) {

        ResourceBundle bundle = java.util.ResourceBundle.getBundle("iMat/resources/iMat");
        //Parent root = FXMLLoader.load(getClass().getResource("imat_about.fxml"), bundle);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("product_panel.fxml"), bundle);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.product = product;
        nameLabel.setText(product.getName());
        priceLabel.setText(product.getPrice() + " " + product.getUnit());
        for (ShoppingItem si : model.getShoppingCart().getItems()){
            if(si.getProduct().equals(product)){
                double value = (Math.round(si.getAmount() * 10) / 10.0);
                if(value != 0)
                    subtractButton.setDisable(false);
                if(si.getProduct().getUnitSuffix().equals("kg")){
                    amountLabel.setText(String.valueOf(value));
                }
                else{
                    amountLabel.setText(String.valueOf((int) value));
                }
            }
        }
        /*prizeLabel.setText(String.format("%.2f", product.getPrice()) + " " + product.getUnit());*/
        imageView.setImage(model.getImage(product, kImageWidth, kImageWidth*kImageRatio));
        /*if (!product.isEcological()) {
            ecoLabel.setText("");
        }*/
    }

    @FXML
    private void handleAddAction(ActionEvent event) {
        System.out.println("Add " + product.getName());
        model.addToShoppingCart(product);
        for (ShoppingItem si : model.getShoppingCart().getItems()){
            if(si.getProduct().equals(product)){
                double value = (Math.round(si.getAmount() * 10) / 10.0);
                if(si.getProduct().getUnitSuffix().equals("kg")){
                    amountLabel.setText(String.valueOf(value));
                }
                else{
                    amountLabel.setText(String.valueOf((int) value));
                }
            }
        }
        subtractButton.setDisable(false);
    }

    @FXML
    private void handleSubtractAction(ActionEvent event) {

        double value = 0;
        System.out.println("Subtract " + product.getName());
        model.subtractFromShoppingCart(product);
        for (ShoppingItem si : model.getShoppingCart().getItems()){
            if(si.getProduct().equals(product)){
                value = (Math.round(si.getAmount() * 10) / 10.0);
                System.out.println(value);
                if(si.getProduct().getUnitSuffix().equals("kg")){
                    amountLabel.setText(String.valueOf(value));
                }
                else{
                    amountLabel.setText(String.valueOf((int) value));
                }
                return;
            }
        }
        amountLabel.setText(String.valueOf(0));
        subtractButton.setDisable(true);
    }
}
