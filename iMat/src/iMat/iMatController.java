
package iMat;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import se.chalmers.cse.dat216.project.*;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;
import static javafx.scene.control.PopupControl.USE_PREF_SIZE;
import static se.chalmers.cse.dat216.project.ProductCategory.*;

public class iMatController implements Initializable, ShoppingCartListener {

    @FXML private MenuBar menuBar;

    // Shopping Pane
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private HBox subcategoryHbox;
    @FXML
    private AnchorPane subcategoryAnchorPane;
    @FXML
    private TextField searchField;
    @FXML
    private ScrollPane cartScrollPane;
    @FXML
    private Label totalLabel;

    // History pane
    @FXML
    private FlowPane historyFlowPaneMain;

    @FXML
    private FlowPane historyFlowPaneDetail;

    // Other variables
    private final Model model = Model.getInstance();
    List<Product> currentProductList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        model.getShoppingCart().addShoppingCartListener(this);

        updateProductList(model.getProducts());
        updateCart();
    }

    private void updateProductList() {
        updateProductList(currentProductList);
    }

    private void updateProductList(List<Product> products) {
        currentProductList = products;
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
            hbox.setSpacing(30.0);
            vbox.setAlignment(Pos.CENTER);
            vbox.setSpacing(30.0);
            vbox.setPadding(new Insets(30d,0d,0d,0d));
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
    protected void handleChangeCategory(ActionEvent event){
        String s = event.getSource().toString();

        s = s.substring(s.indexOf("id=") + 3);
        s = s.substring(0, s.indexOf(","));
        System.out.println("Category " + s + " selected");

        List<Product> products = new ArrayList<Product>();
        subcategoryAnchorPane.setMinHeight(USE_PREF_SIZE);

        switch (s){
            case "categoryBtn1":
                subcategoryHbox.getChildren().clear();
                subcategoryAnchorPane.setMaxHeight(0d);
                subcategoryAnchorPane.setMinHeight(0d);
                products.addAll(model.getProducts(BREAD));
                break;
            case "categoryBtn2":
                subcategoryHbox.getChildren().clear();
                createSubcategoryBtn(COLD_DRINKS);
                createSubcategoryBtn(HOT_DRINKS);
                products.addAll(model.getProducts(COLD_DRINKS));
                products.addAll(model.getProducts(HOT_DRINKS));
                break;
            case "categoryBtn3":
                subcategoryHbox.getChildren().clear();
                subcategoryAnchorPane.setMaxHeight(0d);
                subcategoryAnchorPane.setMinHeight(0d);
                products.addAll(model.getProducts(FISH));
                break;
            case "categoryBtn4":
                subcategoryHbox.getChildren().clear();
                createSubcategoryBtn(BERRY);
                createSubcategoryBtn(CITRUS_FRUIT);
                createSubcategoryBtn(EXOTIC_FRUIT);
                createSubcategoryBtn(MELONS);
                createSubcategoryBtn(FRUIT);
                products.addAll(model.getProducts(BERRY));
                products.addAll(model.getProducts(CITRUS_FRUIT));
                products.addAll(model.getProducts(EXOTIC_FRUIT));
                products.addAll(model.getProducts(MELONS));
                products.addAll(model.getProducts(FRUIT));
                break;
            case "categoryBtn5":
                subcategoryHbox.getChildren().clear();
                createSubcategoryBtn(POD);
                createSubcategoryBtn(VEGETABLE_FRUIT);
                createSubcategoryBtn(CABBAGE);
                createSubcategoryBtn(ROOT_VEGETABLE);
                createSubcategoryBtn(HERB);
                products.addAll(model.getProducts(POD));
                products.addAll(model.getProducts(VEGETABLE_FRUIT));
                products.addAll(model.getProducts(CABBAGE));
                products.addAll(model.getProducts(ROOT_VEGETABLE));
                products.addAll(model.getProducts(HERB));
                break;
            case "categoryBtn6":
                subcategoryHbox.getChildren().clear();
                subcategoryAnchorPane.setMaxHeight(0d);
                subcategoryAnchorPane.setMinHeight(0d);
                products.addAll(model.getProducts(MEAT));
                break;
            case "categoryBtn7":
                subcategoryHbox.getChildren().clear();
                subcategoryAnchorPane.setMaxHeight(0d);
                subcategoryAnchorPane.setMinHeight(0d);
                products.addAll(model.getProducts(DAIRIES));
                break;
            case "categoryBtn8":
                subcategoryHbox.getChildren().clear();
                subcategoryAnchorPane.setMaxHeight(0d);
                subcategoryAnchorPane.setMinHeight(0d);
                products.addAll(model.getProducts(SWEET));
                break;
            case "categoryBtn9":
                subcategoryHbox.getChildren().clear();
                createSubcategoryBtn(FLOUR_SUGAR_SALT);
                createSubcategoryBtn(NUTS_AND_SEEDS);
                createSubcategoryBtn(PASTA);
                createSubcategoryBtn(POTATO_RICE);
                products.addAll(model.getProducts(FLOUR_SUGAR_SALT));
                products.addAll(model.getProducts(NUTS_AND_SEEDS));
                products.addAll(model.getProducts(PASTA));
                products.addAll(model.getProducts(POTATO_RICE));
                break;
            default:
                System.out.println("Unknown category! Returning");
                return;
        }
        updateProductList(products);
    }

    private void createSubcategoryBtn(ProductCategory subcategory){
        CheckBox btn = new CheckBox();
        btn.setId(subcategory.toString());
        switch (subcategory){
            case BERRY:
                btn.setText("Bär");
                break;
            case BREAD:
                btn.setText("Bröd");
                break;
            case CABBAGE:
                btn.setText("Kål");
                break;
            case CITRUS_FRUIT:
                btn.setText("Citrusfrukter");
                break;
            case COLD_DRINKS:
                btn.setText("Kalla drycker");
                break;
            case DAIRIES:
                btn.setText("Mejeri");
                break;
            case EXOTIC_FRUIT:
                btn.setText("Exotiska frukter");
                break;
            case FISH:
                btn.setText("Fisk");
                break;
            case FLOUR_SUGAR_SALT:
                btn.setText("Mlöj, socker, salt");
                break;
            case FRUIT:
                btn.setText("Stenfrukter");
                break;
            case HERB:
                btn.setText("Örtkryddor");
                break;
            case HOT_DRINKS:
                btn.setText("Varma drycker");
                break;
            case MEAT:
                btn.setText("Kött");
                break;
            case MELONS:
                btn.setText("Meloner");
                break;
            case NUTS_AND_SEEDS:
                btn.setText("Nötter och frön");
                break;
            case PASTA:
                btn.setText("Pasta");
                break;
            case POD:
                btn.setText("Baljväxter");
                break;
            case POTATO_RICE:
                btn.setText("Potatis och ris");
                break;
            case ROOT_VEGETABLE:
                btn.setText("Rotfrukter");
                break;
            case SWEET:
                btn.setText("Sötsaker");
                break;
            case VEGETABLE_FRUIT:
                btn.setText("Grönsaksfrukter");
                break;
        }
        btn.setFont(new Font(20d));
        btn.setSelected(true);
        btn.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                System.out.println(btn.getId() + " set to " + newValue);
                List<Product> products = new ArrayList<Product>();
                boolean noneSelected = true;
                for (Node child : subcategoryHbox.getChildren()) {
                    CheckBox ch = (CheckBox) child;
                    if(ch.isSelected()){
                        noneSelected = false;
                        break;
                    }
                }
                if (noneSelected){
                    System.out.println("None selected, adding all");
                    for (Node child : subcategoryHbox.getChildren()) {
                        CheckBox ch = (CheckBox) child;
                        products.addAll(model.getProducts(ProductCategory.valueOf(ch.getId())));
                    }
                }
                else {
                    for (Node child : subcategoryHbox.getChildren()) {
                        CheckBox ch = (CheckBox) child;
                        System.out.println("State of: " + child.getId() + " is " + ch.isSelected());
                        if (ch.isSelected()) {
                            System.out.println("Adding " + ch.getId());
                            products.addAll(model.getProducts(ProductCategory.valueOf(ch.getId())));
                        }
                    }
                }
                updateProductList(products);
            }
        });
        subcategoryHbox.getChildren().add(btn);
    }
    
    @FXML 
    protected void closeApplicationActionPerformed(ActionEvent event) throws IOException{
        
        Stage iMatStage = (Stage) menuBar.getScene().getWindow();
        iMatStage.hide();
    }

    @Override
    public void shoppingCartChanged(CartEvent evt) {
        System.out.println("Shopping cart changed");
        updateCart();
    }

    @FXML
    private void handleSearchAction(ActionEvent event) {
        List<Product> matches = model.findProducts(searchField.getText());
        updateProductList(matches);
        System.out.println("# matching products: " + matches.size());
    }

    @FXML
    private void handleAllProducts(ActionEvent event) {
        List<Product> matches = model.getProducts();
        updateProductList(matches);
        System.out.println("# matching products: " + matches.size());
    }

    private void updateCart(){
        totalLabel.setText("Totalt: " + Math.round(model.getShoppingCart().getTotal() * 100) / 100.0 + "kr");
        double kImageWidth = 50.0;
        double kImageRatio = 0.75;
        VBox vbox = new VBox();
        cartScrollPane.setContent(vbox);
        for (ShoppingItem item : model.getShoppingCart().getItems()){
            HBox hbox = new HBox();
            vbox.getChildren().add(hbox);
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.setSpacing(20d);
            hbox.setPadding(new Insets(10d,0,0,10d));
            vbox.setSpacing(10d);

            hbox.getChildren().add(new ImageView(model.getImage(item.getProduct(), kImageWidth, kImageWidth*kImageRatio)));
            Label nameLabel = new Label(item.getProduct().getName() + " ");
            Label antalLabel = null;
            Label totalLabel = new Label(String.valueOf(Math.round(item.getTotal() * 10) / 10.0) + "kr");
            if (item.getProduct().getUnit().equals("kr/kg")){
                antalLabel = new Label(Math.round(item.getAmount() * 10) / 10.0 + " "
                        + item.getProduct().getUnitSuffix() + " ");
            }
            else {
                antalLabel = new Label(Math.round(item.getAmount()) + " "
                        + item.getProduct().getUnitSuffix() + " ");
            }
            nameLabel.setFont(new Font(20d));
            antalLabel.setFont(new Font(20d));
            totalLabel.setFont(new Font(20d));

            double width1 = 120d;
            double width2 = 80d;
            double width3 = 80d;
            nameLabel.setMinWidth(width1);
            nameLabel.setMaxWidth(width1);
            nameLabel.setPrefWidth(width1);

            antalLabel.setMinWidth(width2);
            antalLabel.setMaxWidth(width2);
            antalLabel.setPrefWidth(width2);

            totalLabel.setPrefWidth(width3);
            totalLabel.setMaxWidth(width3);
            totalLabel.setMinWidth(width3);

            hbox.getChildren().addAll(nameLabel, antalLabel, totalLabel);
        }
    }

    @FXML
    private void clearCart(){
        System.out.println("Clearing cart");
        model.getShoppingCart().clear();
        updateCart();
        updateProductList();
    }

    // History pane methods

    // TODO: Run when switching to the history pane
    // Initializes the history pane
    // Should be run every time it is switched to
    public void historyPaneInit(){
        List<Order> orders = IMatDataHandler.getInstance().getOrders();
        updateHistoryListMain(orders);
    }

    // Updates the product detail view with the given products
    public void updateHistoryListMain(List<Order> orders){
        historyFlowPaneMain.getChildren().clear();

        for (Order order : orders){
            historyFlowPaneMain.getChildren().add(new HistoryListItemMain(order, this));
        }
    }

    // Updates the product detail view with the given products
    public void updateHistoryListDetail(List<ShoppingItem> items){
        historyFlowPaneDetail.getChildren().clear();

        for (ShoppingItem item : items){
            historyFlowPaneDetail.getChildren().add(new HistoryListItemDetail(item, this));
        }
    }
}