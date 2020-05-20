
package iMat;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingCartListener;

public class iMatController implements Initializable, ShoppingCartListener {
    
    @FXML private MenuBar menuBar;

    // Shopping Pane
    @FXML
    private AnchorPane shopPane;
    @FXML
    private TextField searchField;
    @FXML
    private Label itemsLabel;
    @FXML
    private Label costLabel;
    @FXML
    private GridPane productsGridPane;
    @FXML
    private ScrollPane scrollPane;

    // Account Pane
    @FXML
    private AnchorPane accountPane;
    @FXML
    ComboBox cardTypeCombo;
    @FXML
    private TextField numberTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private ComboBox monthCombo;
    @FXML
    private ComboBox yearCombo;
    @FXML
    private TextField cvcField;
    @FXML
    private Label purchasesLabel;

    // Other variables
    private final Model model = Model.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        model.getShoppingCart().addShoppingCartListener(this);

        updateProductList(model.getProducts());
        /*updateBottomPanel();

        setupAccountPane();*/

    }

    private void updateProductList(List<Product> products) {
        VBox vbox = new VBox();
        HBox hbox = new HBox();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        vbox.getChildren().add(hbox);
        scrollPane.setContent(vbox);

        int col = 0;
        for (Product product : products) {
            if(col > 2){
                hbox = new HBox();
                vbox.getChildren().add(hbox);
                col = 0;
            }

            hbox.getChildren().add(new ProductPanel(product));
            col++;
            hbox.setAlignment(Pos.CENTER);
            hbox.setSpacing(10.0);
            hbox.setAlignment(Pos.CENTER);
            vbox.setSpacing(10.0);
            //System.out.println("Adding: " + product.toString());
        }
    }
    
    @FXML 
    protected void openAboutActionPerformed(ActionEvent event) throws IOException{
    
        ResourceBundle bundle = java.util.ResourceBundle.getBundle("iMat/resources/iMat");
        Parent root = FXMLLoader.load(getClass().getResource("imat_about.fxml"), bundle);
        Stage aboutStage = new Stage();
        aboutStage.setScene(new Scene(root));
        aboutStage.setTitle(bundle.getString("about.title.text"));
        aboutStage.initModality(Modality.APPLICATION_MODAL);
        aboutStage.setResizable(false);
        aboutStage.showAndWait();
    }
    
    @FXML 
    protected void closeApplicationActionPerformed(ActionEvent event) throws IOException{
        
        Stage iMatStage = (Stage) menuBar.getScene().getWindow();
        iMatStage.hide();
    }

    // Shope pane methods
    @Override
    public void shoppingCartChanged(CartEvent evt) {
        /*updateBottomPanel();*/
    }
}
