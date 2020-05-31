
package iMat;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import se.chalmers.cse.dat216.project.*;

import static javafx.scene.control.PopupControl.USE_PREF_SIZE;
import static se.chalmers.cse.dat216.project.ProductCategory.*;

public class iMatController implements Initializable, ShoppingCartListener {

    @FXML private MenuBar menuBar;

    // Shopping Pane
    @FXML
    private SplitPane mainViewPane;
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

    // History & Saved Lists pane
    // Both of these pages share the same pane
    @FXML
    private AnchorPane listsViewPane;
    @FXML
    private FlowPane historyFlowPaneMain;
    @FXML
    private FlowPane historyFlowPaneDetail;
    @FXML
    private Text historyDetailDescriptionText;
    @FXML
    private Text historyDetailDateText;
    @FXML
    private Text historyTotalPriceText;
    @FXML
    private Text largeInfoText1;
    @FXML
    private Button saveAsListButton;

    private List<SavedList> savedLists = new ArrayList<>();


    // My account pane
    @FXML
    private SplitPane myAccountPane;
    @FXML
    private Button myInformationButton;
    @FXML
    private Button addressButton;
    @FXML
    private Button paymentMethodButton;
    @FXML
    private AnchorPane paymentMethodPane;
    @FXML
    private TextField cardNumberTextField;
    @FXML
    private ComboBox monthComboBox;
    @FXML
    private ComboBox yearComboBox;
    @FXML
    private TextField cardNameTextField;
    @FXML
    private Button savCardInfoButton;
    @FXML
    private AnchorPane addressPane;
    @FXML
    private TextField streetAddressTextField;
    @FXML
    private TextField postcodeTextField;
    @FXML
    private TextField cityTextField;
    @FXML
    private Button saveAddressButton;
    @FXML
    private AnchorPane myInformationPane;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField telephoneTextField;
    @FXML
    private Button saveInfoButton;
    @FXML
    private Label myInformationLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label cardLabel;
    @FXML
    private Button myAccountButton;
    @FXML private Text savedMessageText;
    @FXML private Text savedMessageText2;
    @FXML private Text savedMessageText3;


    // Checkout Pane
    @FXML private AnchorPane checkoutViewPane;
    @FXML private AnchorPane stepOnePane;
    @FXML private AnchorPane stepTwoPane;
    @FXML private AnchorPane stepThreePane;
    @FXML private AnchorPane stepFourPane;
    @FXML private AnchorPane purchaseCompleted;

    @FXML private ScrollPane checkoutScrollPane;
    @FXML private ScrollPane confirmationScrollPane;

    @FXML private AnchorPane cardPaymentPopUp;
    @FXML private AnchorPane bankPaymentPopUp;
    @FXML private AnchorPane bankPayment;

    @FXML private RadioButton altOne;
    @FXML private RadioButton altTwo;

    @FXML private Button continueShopping;
    @FXML private Text totalPrice;
    @FXML private Text totalPriceConfirmation;

    @FXML private TextField deliveryAddress;
    @FXML private ComboBox<String> checkoutMonthComboBox;
    @FXML private ComboBox<Integer> dayComboBox;
    @FXML private ComboBox<String> timeComboBox;

    @FXML private TextField cardHolderNameField;
    @FXML private TextField cardNumberField;
    @FXML private TextField verificationCodeField;
    @FXML private TextField monthField;
    @FXML private TextField yearField;

    @FXML private Text deliveryTime;
    @FXML private Text deliveryLocation;

    @FXML private Text warningMessage;

    private boolean cardPaymentOpen = false;
    private boolean bankPaymentOpen = false;

    private String monthValue = "";
    private Integer dayValue  = 0;
    private String timeValue  = "";
    private String addressValue = "";


    // Other variables
    private final Model model = Model.getInstance();
    List<Product> currentProductList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        model.getShoppingCart().addShoppingCartListener(this);

        updateProductList(model.getProducts());
        updateCart();

        // historyPaneInit();


        //My account pane Text Fields
        //limit input size in card number TextField
        Pattern pattern = Pattern.compile(".{0,16}");
        TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        cardNumberTextField.setTextFormatter(formatter);
        //limit input to integers only
        cardNumberTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    cardNumberTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        Pattern phonePattern = Pattern.compile(".{0,12}");
        TextFormatter phoneFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return phonePattern.matcher(change.getControlNewText()).matches() ? change : null;
        });

        telephoneTextField.setTextFormatter(phoneFormatter);
        telephoneTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    telephoneTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        Pattern postcodePattern = Pattern.compile(".{0,5}");
        TextFormatter postcodeFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return postcodePattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        postcodeTextField.setTextFormatter(postcodeFormatter);

        postcodeTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    postcodeTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        // Checkout
        checkoutViewPane.setVisible(false);


        checkoutMonthComboBox.getItems().addAll("Januari", "Februari", "Mars", "April", "Maj", "Juni", "Juli", "Augusti", "Septempber", "Oktober", "November", "December");
        checkoutMonthComboBox.setStyle("-fx-font: 20px \"System\";");
        checkoutMonthComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                monthValue = newValue;
                updateDeliveryTime();
            }
        });

        dayComboBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31);
        dayComboBox.setStyle("-fx-font: 20px \"System\";");
        dayComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                dayValue = newValue;
                updateDeliveryTime();
            }
        });
        addTime();
        timeComboBox.setStyle("-fx-font: 20px \"System\";");
        timeComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                timeValue = newValue;
                updateDeliveryTime();
            }
        });
        model.getShoppingCart().addShoppingCartListener(this);


        Pattern cardNumberPattern = Pattern.compile(".{0,16}");
        TextFormatter cardNumberFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return cardNumberPattern.matcher(change.getControlNewText()).matches() ? change : null;
        });

        cardNumberField.setTextFormatter(cardNumberFormatter);
        cardNumberField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    cardNumberField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });


            Pattern verificationCodePattern = Pattern.compile(".{0,3}");
        TextFormatter verificationCodeFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return verificationCodePattern.matcher(change.getControlNewText()).matches() ? change : null;
        });

        verificationCodeField.setTextFormatter(verificationCodeFormatter);
        verificationCodeField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    verificationCodeField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        
        Pattern monthPattern = Pattern.compile(".{0,2}");
        TextFormatter monthFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return monthPattern.matcher(change.getControlNewText()).matches() ? change : null;
        });

        monthField.setTextFormatter(monthFormatter);
        monthField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!newValue.matches("\\d*")) {
                        monthField.setText(newValue.replaceAll("[^\\d]", ""));
                    }
            }
        });
        
        Pattern yearPattern = Pattern.compile(".{0,4}");
        TextFormatter yearFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return yearPattern.matcher(change.getControlNewText()).matches() ? change : null;
        });

        yearField.setTextFormatter(yearFormatter);
        yearField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!newValue.matches("\\d*")) {
                        yearField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        updateProductList();
        updateConfirmationTable();

        setDeliveryLocation();
        updateCardInformation();
        finishPurchase();

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
        searchField.setText("");
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
        subcategoryAnchorPane.setMaxHeight(0d);
        subcategoryAnchorPane.setMinHeight(0d);
        List<Product> matches = model.findProducts(searchField.getText());
        updateProductList(matches);
        System.out.println("# matching products: " + matches.size());
    }

    @FXML
    private void handleAllProducts(ActionEvent event) {
        searchField.setText("");
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
        int i = 0;
        for (ShoppingItem item : model.getShoppingCart().getItems()){
            i++;
            HBox hbox = new HBox();
            vbox.getChildren().add(hbox);
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.setSpacing(20d);
            hbox.setPadding(new Insets(10d,0,10d,10d));
            vbox.setSpacing(10d);
            if(i % 2 == 0) {
                hbox.getStyleClass().add("grey_background");
            }

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

            double width1 = 100d;
            double width2 = 60d;
            double width3 = 90d;
            nameLabel.setMinWidth(width1);
            nameLabel.setMaxWidth(width1);
            nameLabel.setPrefWidth(width1);

            antalLabel.setMinWidth(width2);
            antalLabel.setMaxWidth(width2);
            antalLabel.setPrefWidth(width2);

            totalLabel.setPrefWidth(width3);
            totalLabel.setMaxWidth(width3);
            totalLabel.setMinWidth(width3);

            Button plus = new Button("+");
            plus.getStyleClass().add("extra_small_button");
            plus.setPrefSize(27d, 27d);
            plus.setMinSize(27d, 27d);
            plus.setMaxSize(27d, 27d);
            plus.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    model.addToShoppingCart(item.getProduct());
                    updateCart();
                    updateProductList();
                }
            });

            Button minus = new Button("-");
            minus.getStyleClass().add("extra_small_button");
            minus.setPrefSize(27d, 27d);
            minus.setMinSize(27d, 27d);
            minus.setMaxSize(27d, 27d);
            minus.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    model.subtractFromShoppingCart(item.getProduct());
                    updateCart();
                    updateProductList();
                }
            });

            VBox buttonVBox = new VBox();
            buttonVBox.getChildren().addAll(plus, minus);

            hbox.getChildren().addAll(nameLabel, buttonVBox, antalLabel, totalLabel);
        }
    }

    @FXML
    private void clearCart(){
        System.out.println("Clearing cart");
        model.getShoppingCart().clear();
        updateCart();
        updateProductList();
    }

    // TODO
    // Pane switching methods

    @FXML
    protected void focusCheckoutPane (ActionEvent event){
        System.out.println("Loading Checkout Pane...");
        updateCardInformation();
        updateCheckoutProductList();
        checkoutViewPane.setVisible(true);
        checkoutViewPane.toFront();
        stepOnePane.toFront();
    }

    @FXML
    protected void focusHistoryPane (ActionEvent event){
        System.out.println("Loading History Pane...");

        historyPaneInit();
        listsViewPane.toFront();
    }

    @FXML
    protected void focusSavedListsPane (ActionEvent event){
        System.out.println("Loading Saved Lists Pane...");

        savedListsPaneInit();
        listsViewPane.toFront();
    }

    @FXML
    protected void focusMainPane (ActionEvent event){
        System.out.println("Loading Main Pane...");
        checkoutViewPane.toBack();
        checkoutViewPane.setVisible(false);
        mainViewPane.toFront();
    }

    @FXML
    protected void focusAccountPane (ActionEvent event){
        System.out.println("Loading Account Pane...");

        updateAccountPanel();
        savedMessageText.setVisible(false);
        savedMessageText2.setVisible(false);
        savedMessageText3.setVisible(false);
        myAccountPane.toFront();
    }


    // Saved Lists Pane Methods

    // Initializes the Saved Lists pane
    // Should be run every time it is switched to
    public void savedListsPaneInit(){
        //List<Order> orders = IMatDataHandler.getInstance().;
        historyDetailDateText.setText("Ingen Lista Vald");
        historyDetailDescriptionText.setText("Lista");
        saveAsListButton.setDisable(true);

        historyFlowPaneMain.getChildren().clear();
        historyFlowPaneDetail.getChildren().clear();

        updateHistoryListMain(savedLists, "");

        historyTotalPriceText.setText("");

        largeInfoText1.setText("Namn");

        currentList = null;
    }

    @FXML
    protected void saveCartAsFavorite(Event event){
        System.out.println("Saving History as Favourite...");

        if (IMatDataHandler.getInstance().getShoppingCart().getItems().size() == 0) {
            throw new NullPointerException("No Items in Cart");
        }

        savedLists.add(new SavedList(IMatDataHandler.getInstance().getShoppingCart().getItems(), "Saved List " + (savedLists.size() + 1)));
    }

    // History pane methods

    private List<ShoppingItem> currentList;

    @FXML
    protected void addHistoryToCart(Event event){
        System.out.println("Adding Previous Order to the Cart...");

        IMatDataHandler handler = IMatDataHandler.getInstance();
        ShoppingCart cart = handler.getShoppingCart();

        List<ShoppingItem> items = List.copyOf(currentList);
        for (ShoppingItem item : items){
            cart.addItem(item);
        }
    }

    @FXML
    protected void saveHistoryAsFavorite(Event event){
        System.out.println("Saving History as Favourite...");

        if (currentList == null) {
            throw new NullPointerException("No list selected");
        }

        savedLists.add(new SavedList(currentList, "Saved List " + (savedLists.size() + 1)));
    }

    // Initializes the history pane
    // Should be run every time it is switched to
    public void historyPaneInit(){
        List<Order> orders = IMatDataHandler.getInstance().getOrders();
        historyDetailDateText.setText("Ingen Beställning Vald");
        historyDetailDescriptionText.setText("Beställning");
        saveAsListButton.setDisable(false);

        historyFlowPaneMain.getChildren().clear();
        historyFlowPaneDetail.getChildren().clear();

        //orders.add(createTestOrder());

        updateHistoryListMain(orders);

        historyTotalPriceText.setText("");

        largeInfoText1.setText("Beställning");

        currentList = null;
    }

    // Updates the product detail view with the given products
    public void updateHistoryListMain(List<Order> orders){
        historyFlowPaneMain.getChildren().clear();

        for (Order order : orders){
            historyFlowPaneMain.getChildren().add(new OrderOverviewListItem(order.getItems(), order.getDate().toString(), this));
        }
    }

    public void updateHistoryListMain(List<SavedList> savedLists, String t){
        historyFlowPaneMain.getChildren().clear();

        for (SavedList list : savedLists){
            historyFlowPaneMain.getChildren().add(new OrderOverviewListItem(list.items, list.description, this));
        }
    }

    // Updates the product detail view with the given products
    public void updateDetailList(List<ShoppingItem> items, String description){
        System.out.println("Sorting Detail View...");

        insertionSort(items);
        System.out.println("Sorting Finished");

        historyFlowPaneDetail.getChildren().clear();

        historyDetailDateText.setText(description);

        int totalPrice = 0;

        for (ShoppingItem shoppingItem : items){
            historyFlowPaneDetail.getChildren().add(new OrderDetailListItem(shoppingItem, this));
            totalPrice += shoppingItem.getTotal();
        }

        historyTotalPriceText.setText(totalPrice + " kr");

        currentList = items;
    }

    // Insertion sort.

    private static void insertionSort(List<ShoppingItem> array) {

        int length = array.size();

        // Loops through the unsorted array
        for (int i = 1; i < length; i++){
            // First unsorted variable in the array from the left
            ShoppingItem current = array.get(i);

            // Loops through the remaining unsorted array backwards
            // until current is larger than the next element (to the left) in the sorted portion
            int j = i - 1;
            while (j >= 0 && array.get(j).getProduct().getName().compareTo(current.getProduct().getName()) > 0){
                // Moves the insertion gap one step to the right
                array.set(j+1, array.get(j));
                j-=1;
            }
            // Places the unsorted element into the correct position in the sorted array
            array.set(j+1, current);
        }
    }

    private Order createTestOrder(){
        Order order = new Order();

        List<ShoppingItem> items = new ArrayList<>();
        List<Product> allProducts = IMatDataHandler.getInstance().getProducts();

        for (Product product : allProducts){
            items.add(new ShoppingItem(product, 1));
        }

        order.setItems(items);

        Date date = new Date();
        date.setTime(100);
        order.setDate(date);

        order.setOrderNumber(1);

        return order;
    }

    //My account methods

    public void openAddressView(ActionEvent actionEvent) {
        savedMessageText.setVisible(false);
        savedMessageText3.setVisible(false);
        savedMessageText2.setVisible(false);
        addressPane.toFront();
    }

    public void openMyInformationView(ActionEvent actionEvent) {
        myInformationPane.toFront();
        savedMessageText2.setVisible(false);
        savedMessageText3.setVisible(false);
        savedMessageText.setVisible(false);
    }


    public void openPaymentView(ActionEvent actionEvent) {
        paymentMethodPane.toFront();
        savedMessageText.setVisible(false);
        savedMessageText2.setVisible(false);
        savedMessageText3.setVisible(false);
    }

    @FXML
    private void handleSavePaymentInformation(ActionEvent event){
        if(cardNumberTextField.getText().equals("") || cardNameTextField.getText().equals("") ||monthComboBox.getSelectionModel().isEmpty()|| yearComboBox.getSelectionModel().isEmpty()){
            savedMessageText3.setVisible(false);
            showAlertWithoutHeaderText();
        }else {
            updatePaymentInformation();
            savedMessageText3.setVisible(true);
            savedMessageText3.setText("Dina uppgifter har sparats!");
        }
    }


    @FXML
    private void handleSaveAddressInformation(ActionEvent event){
        if(streetAddressTextField.getText().equals("") || postcodeTextField.getText().equals("") || cityTextField.getText().equals("")){
            savedMessageText2.setVisible(false);
            showAlertWithoutHeaderText();
        }else {
            updateAddressInformation();
            savedMessageText2.setVisible(true);
            savedMessageText2.setText("Dina uppgifter har sparats!");
        }
    }


    @FXML
    private void handleSavePersonalInformation(ActionEvent event){
        if(firstNameTextField.getText().equals("") || lastNameTextField.getText().equals("") || emailTextField.getText().equals("") || telephoneTextField.getText().equals("")){
            savedMessageText.setVisible(false);
            showAlertWithoutHeaderText();
        }else {
            updatePersonalInformation();
            savedMessageText.setVisible(true);
            savedMessageText.setText("Dina uppgifter har sparats!");
        }
    }

    private void showAlertWithoutHeaderText() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ofullständiga uppgifter");

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText("Hoppsan! Du har missat att fylla i något.");
        alert.setX(500);
        alert.setY(300);
        alert.showAndWait();
    }


    private void updateAccountPanel(){
        Customer c = model.getCustomer();
        CreditCard card = model.getCreditCard();

        firstNameTextField.setText(c.getFirstName());
        lastNameTextField.setText(c.getLastName());
        emailTextField.setText(c.getEmail());
        streetAddressTextField.setText(c.getAddress());
        telephoneTextField.setText(c.getPhoneNumber());
        postcodeTextField.setText(c.getPostCode());
        cityTextField.setText(c.getPostAddress());
        cardNumberTextField.setText(card.getCardNumber());
        cardNameTextField.setText(card.getHoldersName());
        monthComboBox.getSelectionModel().select(""+card.getValidMonth());
        yearComboBox.getSelectionModel().select(""+card.getValidYear());
        yearComboBox.getItems().addAll(model.getYears());
        monthComboBox.getItems().addAll(model.getMonths());
    }


    private void updatePersonalInformation(){
        Customer customer = model.getCustomer();

        customer.setFirstName(firstNameTextField.getText());
        customer.setLastName(lastNameTextField.getText());
        customer.setEmail(emailTextField.getText());
        customer.setPhoneNumber(telephoneTextField.getText());
    }

    private void updateAddressInformation(){
        Customer customer = model.getCustomer();

        customer.setAddress(streetAddressTextField.getText());
        customer.setPostCode(postcodeTextField.getText());
        customer.setPostAddress(cityTextField.getText());
    }

    private void updatePaymentInformation(){
        CreditCard c = model.getCreditCard();

        c.setCardNumber(cardNumberTextField.getText());
        c.setHoldersName(cardNameTextField.getText());

        String selectedValue = (String) monthComboBox.getSelectionModel().getSelectedItem();
        try{
            c.setValidMonth(Integer.parseInt(selectedValue));
        } catch(NumberFormatException ex){ // handle your exception
            System.out.println(ex);
        }

        selectedValue = (String) yearComboBox.getSelectionModel().getSelectedItem();
        try{
            c.setValidYear(Integer.parseInt(selectedValue));
        } catch(NumberFormatException ex){ // handle your exception
            System.out.println(ex);
        }
    }
    //Checkout Methods:
    private void updateCardInformation() {
        Customer customer = model.getCustomer();
        CreditCard card = model.getCreditCard();
        deliveryAddress.setText(customer.getAddress());

        cardHolderNameField.setText(card.getHoldersName());
        cardNumberField.setText(String.valueOf(card.getCardNumber()));
        monthField.setText(String.valueOf(card.getValidMonth()));
        yearField.setText(String.valueOf(card.getValidYear()));
        verificationCodeField.setText(String.valueOf(card.getVerificationCode()));
    }

    private void finishPurchase() {
        Customer customer = model.getCustomer();
        CreditCard card = model.getCreditCard();
        customer.setAddress(deliveryAddress.getText());
        card.setHoldersName(cardHolderNameField.getText());
        if (!cardNumberField.getText().isEmpty())
            card.setCardNumber(cardNumberField.getText());
        if (!monthField.getText().isEmpty()) {
            try {
                card.setValidMonth(Integer.parseInt(monthField.getText()));
            } catch (NumberFormatException ex) {
                System.out.println(ex);
            }
        }
        if (!yearField.getText().isEmpty()){
            try{
                card.setValidYear(Integer.parseInt(yearField.getText()));
            }
            catch (NumberFormatException ex){
                System.out.println(ex);
            }
        }
        if (!verificationCodeField.getText().isEmpty()){
            try{
                card.setVerificationCode(Integer.parseInt(verificationCodeField.getText()));
            }
            catch (NumberFormatException ex){
                System.out.println(ex);
            }
        }
        model.placeOrder();
    }

    private void setDeliveryLocation() {
        deliveryLocation.setText("Levereras till: " + deliveryAddress.getText());
    }


    private void updateDeliveryTime() {
        deliveryTime.setText("Leveranstid: " + dayValue + " " + monthValue + ". Klockan: " + timeValue);
    }

    private void addTime() {
        for (int i = 12; i <= 20; i++) {
            for (int j = 0; j <= 3; j++) {
                if (j == 0) timeComboBox.getItems().addAll(i +":00");
                else timeComboBox.getItems().addAll(i +":" + j*15);
            }
        }
    }


    public void updateCheckoutProductList() {
        updateTotalPrice();
        VBox vbox = new VBox();
        checkoutScrollPane.setFitToHeight(true);
        checkoutScrollPane.setFitToWidth(true);

        vbox.getChildren();
        checkoutScrollPane.setContent(vbox);


        for (ShoppingItem item : model.getShoppingCart().getItems()) {
            vbox.getChildren().add(new CheckoutProductPanel(item, this));
        }
    }

    public void updateTotalPrice() {
        double total = Math.round(model.getShoppingCart().getTotal() * 100) / 100.0;
        totalPrice.setText("Totalt: " + total + " kr");
        totalPriceConfirmation.setText("Totalt: " + total + " kr");
    }

    private void updateConfirmationTable() {
        VBox vbox = new VBox();
        confirmationScrollPane.setFitToHeight(true);
        confirmationScrollPane.setFitToWidth(true);

        vbox.getChildren();
        confirmationScrollPane.setContent(vbox);


        for (ShoppingItem item : model.getShoppingCart().getItems()) {
            vbox.getChildren().add(new CheckoutConfirmationProductPanel(item));
        }
    }

    public void cardPaymentOptionPressed() {
        if (cardPaymentOpen) {
            closeCardPayment();
        } else {
            openCardPayment();
        }
    }
    public void openCardPayment() {
        altOne.setSelected(true);
        cardPaymentPopUp.toFront();
        cardPaymentOpen = true;
        closeBankPayment();
        bankPayment.setLayoutY(602);
    }
    public void closeCardPayment() {
        cardPaymentPopUp.toBack();
        cardPaymentOpen = false;
        bankPayment.setLayoutY(372);

    }

    public void bankPaymentOptionPressed() {
        if (bankPaymentOpen) {
            closeBankPayment();
        } else {
            openBankPayment();
        }
    }
    public void openBankPayment() {
        altTwo.setSelected(true);
        bankPaymentPopUp.toFront();
        bankPaymentOpen = true;
        closeCardPayment();

    }
    public void closeBankPayment() {
        bankPaymentPopUp.toBack();
        bankPaymentOpen = false;

    }


    public void completePurchase() {
        finishPurchase();
        updateCardInformation();

        continueShopping.setVisible(false);
        purchaseCompleted.toFront();
    }
    public void restartShopping() {
        model.getShoppingCart().clear();
        mainView();
        purchaseCompleted.toBack();
        checkoutViewPane.setVisible(false);
        checkoutViewPane.toBack();
        continueShopping.setVisible(true);
    }

    public void mainView() {
        System.out.println("Loading Checkout Pane...");
        checkoutViewPane.setVisible(true);
        checkoutViewPane.toFront();
    }
    public void checkOut() {
        updateCardInformation();
        stepOnePane.toFront();
        updateCheckoutProductList();
        warningMessage.setVisible(false);

    }
    public void stepTwo() {
        warningMessage.setVisible(false);
        stepTwoPane.toFront();
    }
    public void stepThree() {

        if (deliveryTimeNotValid() || deliveryTimeNotValid())
            warningMessagePopUpStepThree();
        else {
            stepThreePane.toFront();
            warningMessage.setVisible(false);
        }
    }
    public void stepFour() {
        if (deliveryTimeNotValid() || deliveryTimeNotValid() || cardInformationNotValid())
            warningMessagePopUpStepFour();
        else {
            stepFourPane.toFront();
            setDeliveryLocation();
            updateConfirmationTable();
            warningMessage.setVisible(false);
        }
    }

    private void warningMessagePopUpStepThree() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fyll i");
        int warnings = 0;
        if (deliveryAddressNotValid()) warnings++;
        if (deliveryTimeNotValid()) warnings++;

        if (deliveryAddressNotValid()) sb.append(" leveransadress");
        if (warnings == 2) sb.append(" och");

        if (deliveryTimeNotValid()) sb.append(" leveranstid");

        sb.append(" för att fortsätta.");
        warningMessage.setText(sb.toString());
        warningMessage.setVisible(true);
        warningMessage.toFront();
    }

    private void warningMessagePopUpStepFour() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fyll i");
        int warnings = 0;

        if (deliveryAddressNotValid()) warnings++;
        if (deliveryTimeNotValid()) warnings++;
        if (cardInformationNotValid()) warnings++;

        if (warnings == 1) {
            if (deliveryAddressNotValid()) sb.append(" leveransadress");
            else if (deliveryTimeNotValid()) sb.append(" leveranstid");
            else if (cardInformationNotValid()) sb.append(" kortuppgifter");
        }
        else if (warnings == 2) {
            if (deliveryTimeNotValid() && deliveryAddressNotValid())  sb.append(" leveranstid och leveransadress");
            else if (deliveryAddressNotValid() && cardInformationNotValid()) sb.append(" leveransadress och kortuppgifter");
            else if (cardInformationNotValid() && deliveryTimeNotValid()) sb.append(" leveranstid och kortuppgifter");
        }
        else if (warnings == 3) sb.append(" leveranstid, leveransadress och kortuppgifter");

        sb.append(" för att fortsätta.");
        warningMessage.setText(sb.toString());
        warningMessage.setVisible(true);
        warningMessage.toFront();
    }

    private boolean deliveryAddressNotValid() {
        return deliveryAddress.getText().isEmpty();
    }

    private boolean deliveryTimeNotValid() {
        return monthValue.isEmpty() || dayValue == 0 || timeValue.isEmpty() ;
    }

    private boolean cardInformationNotValid() {
        return cardHolderNameField.getText().isEmpty() || cardNumberField.getText().isEmpty()
                || monthField.getText().isEmpty() || yearField.getText().isEmpty()
                || verificationCodeField.getText().isEmpty();
    }
}
