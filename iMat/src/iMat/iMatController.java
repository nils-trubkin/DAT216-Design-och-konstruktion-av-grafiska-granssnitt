
package iMat;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.scene.text.Text;
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
    @FXML
    private Button historyButtonMain;

    // History pane
    @FXML
    private AnchorPane historyViewPane;
    @FXML
    private FlowPane historyFlowPaneMain;
    @FXML
    private FlowPane historyFlowPaneDetail;
    @FXML
    private Text historyDetailDateText;
    @FXML
    private Text historyTotalPriceText;


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

    @FXML private Text warningMessage1;
    @FXML private Text warningMessage2;
    @FXML private Text warningMessage3;

    @FXML private HBox mainViewOne;
    @FXML private StackPane mainViewTwo;

    private boolean cardPaymentOpen = false;
    private boolean bankPaymentOpen = false;

    private String monthValue = "";
    private Integer dayValue  = 0;
    private String timeValue  = "";
    private String addressValue = "";


    // Other variables
    private final Model model = Model.getInstance();
    List<Product> currentProductList;

    private Customer customer = model.getCustomer();
    private CreditCard card = model.getCreditCard();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        model.getShoppingCart().addShoppingCartListener(this);

        updateProductList(model.getProducts());
        updateCart();

        // historyPaneInit();

        // Checkout

        checkoutMonthComboBox.getItems().addAll("Januari", "Februari", "Mars", "April", "Maj", "Juni", "Juli", "Augusti", "Septempber", "Oktober", "November", "December");
        checkoutMonthComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                monthValue = newValue;
                updateDeliveryTime();
            }
        });

        dayComboBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31);
        dayComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                dayValue = newValue;
                updateDeliveryTime();
            }
        });
        addTime();
        timeComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                timeValue = newValue;
                updateDeliveryTime();
            }
        });
        model.getShoppingCart().addShoppingCartListener(this);
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
            Label totalLabel = new Label(String.valueOf(Math.round(item.getTotal() * 10) / 10.0));
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
            double width3 = 50d;
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

    // TODO
    // Pane switching methods

    @FXML
    protected void focusCheckoutPane (ActionEvent event){
        System.out.println("Loading Checkout Pane...");
        updateCheckoutProductList();
        mainViewOne.setVisible(false);
        mainViewTwo.setVisible(false);
        checkoutViewPane.setVisible(true);
        checkoutViewPane.toFront();
    }

    @FXML
    protected void focusHistoryPane (ActionEvent event){
        System.out.println("Loading History Pane...");

        historyPaneInit();
        historyViewPane.toFront();
    }

    @FXML
    protected void focusMainPane (ActionEvent event){
        System.out.println("Loading Main Pane...");

        mainViewOne.setVisible(true);
        mainViewTwo.setVisible(true);
        checkoutViewPane.toBack();
        checkoutViewPane.setVisible(false);
        mainViewPane.toFront();
        mainViewPane.toFront();
    }

    @FXML
    protected void focusAccountPane (ActionEvent event){
        System.out.println("Loading Account Pane...");

        updateAccountPanel();
        myAccountPane.toFront();
    }

    // History pane methods

    private Order currentOrderHistory;

    @FXML
    protected void addHistoryToCart(Event event){
        System.out.println("Adding Previous Order to the Cart...");

        IMatDataHandler handler = IMatDataHandler.getInstance();
        ShoppingCart cart = handler.getShoppingCart();

        List<ShoppingItem> items = currentOrderHistory.getItems();
        for (ShoppingItem item : items){
            cart.addItem(item);
        }
    }

    @FXML
    protected void saveHistoryAsFavorite(Event event){
        System.out.println("(NYI) Saving History as Favourite");
    }

    // Initializes the history pane
    // Should be run every time it is switched to
    public void historyPaneInit(){
        List<Order> orders = IMatDataHandler.getInstance().getOrders();
        historyDetailDateText.setText("Ingen Beställning Vald");

        //orders.add(createTestOrder());

        updateHistoryListMain(orders);

        historyTotalPriceText.setText("");

        currentOrderHistory = null;
    }

    // Updates the product detail view with the given products
    public void updateHistoryListMain(List<Order> orders){
        historyFlowPaneMain.getChildren().clear();

        for (Order order : orders){
            historyFlowPaneMain.getChildren().add(new HistoryListItemMain(order, this));
        }
    }

    // Updates the product detail view with the given products
    public void updateHistoryListDetail(Order order){
        System.out.println("Sorting Detail View...");

        List<ShoppingItem> items = order.getItems();
        insertionSort(items);
        System.out.println("Sorting Finished");

        historyFlowPaneDetail.getChildren().clear();

        historyDetailDateText.setText(order.getDate().toString());

        int totalPrice = 0;

        for (ShoppingItem shoppingItem : items){
            historyFlowPaneDetail.getChildren().add(new HistoryListItemDetail(shoppingItem, this));
            totalPrice += shoppingItem.getTotal();
        }

        historyTotalPriceText.setText(totalPrice + " kr");

        currentOrderHistory = order;
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
        addressPane.toFront();
    }

    public void openMyInformationView(ActionEvent actionEvent) {
        myInformationPane.toFront();
    }


    public void openPaymentView(ActionEvent actionEvent) {
        paymentMethodPane.toFront();
    }

    @FXML
    private void handleSavePaymentInformation(ActionEvent event){
        updatePaymentInformation();
    }


    @FXML
    private void handleSaveAddressInformation(ActionEvent event){
        updateAddressInformation();
    }

    @FXML
    private void handleSavePersonalInformation(ActionEvent event){
        updatePersonalInformation();
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
        c.setValidMonth(Integer.parseInt(selectedValue));

        selectedValue = (String) yearComboBox.getSelectionModel().getSelectedItem();
        c.setValidYear(Integer.parseInt(selectedValue));
    }
    //Checkout Methods:
    private void updateCardInformation() {
        deliveryAddress.setText(customer.getAddress());
        cardHolderNameField.setText(card.getHoldersName());
        cardNumberField.setText(card.getCardNumber());
        monthField.setText(String.valueOf(card.getValidMonth()));
        yearField.setText(String.valueOf(card.getValidYear()));
        verificationCodeField.setText(String.valueOf(card.getVerificationCode()));
    }

    private void finishPurchase() {
        customer.setAddress(deliveryAddress.getText());
        card.setHoldersName(cardHolderNameField.getText());
        card.setCardNumber(cardNumberField.getText());
        if (!monthField.getText().equals("MM") && !monthField.getText().equals("")) {
            card.setValidMonth(Integer.parseInt(monthField.getText()));
        }
        if (!yearField.getText().equals("YYYY") && !yearField.getText().equals("")) {
            card.setValidYear(Integer.parseInt(yearField.getText()));
        }
        if (!verificationCodeField.getText().equals("")) {
            card.setVerificationCode(Integer.parseInt(verificationCodeField.getText()));
        }
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

    public void monthFieldClear() {
        if (monthField.getText().equals("MM")) monthField.setText("");
    }
    public void yearFieldClear() {
        if (yearField.getText().equals("YYYYF")) yearField.setText("");
    }

    private void updateCheckoutProductList() {
        totalPrice.setText(String.valueOf(model.getShoppingCart().getTotal()));
        totalPriceConfirmation.setText(String.valueOf(model.getShoppingCart().getTotal()));
        VBox vbox = new VBox();
        checkoutScrollPane.setFitToHeight(true);
        checkoutScrollPane.setFitToWidth(true);

        vbox.getChildren();
        checkoutScrollPane.setContent(vbox);


        for (ShoppingItem item : model.getShoppingCart().getItems()) {
            vbox.getChildren().add(new CheckoutProductPanel(item));
//            vbox.setAlignment(Pos.CENTER);
//            vbox.setPadding(new Insets(30d,0d,0d,0d));
//            System.out.println("Adding: " + product.toString());
        }
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
        bankPayment.setLayoutY(528);
    }
    public void closeCardPayment() {
        cardPaymentPopUp.toBack();
        cardPaymentOpen = false;
        bankPayment.setLayoutY(301);

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
        continueShopping.setVisible(true);
    }

    public void mainView() {
        System.out.println("Loading Checkout Pane...");
        mainViewOne.setVisible(false);
        mainViewTwo.setVisible(false);
        checkoutViewPane.setVisible(true);
        checkoutViewPane.toFront();
    }
    public void checkOut() {
        stepOnePane.toFront();
        updateCheckoutProductList();
        warningMessage1.setVisible(false);
        warningMessage2.setVisible(false);
    }
    public void stepTwo() {
        warningMessage1.setVisible(false);
        warningMessage2.setVisible(false);
        stepTwoPane.toFront();
    }
    public void stepThree() {
        if (deliveryInformationNotValid()) {
            warningMessage1.setText("Steg 2 ej ifylld!");
            warningMessage1.setVisible(true);
            warningMessage2.setText("Steg 2 ej ifylld!");
            warningMessage2.setVisible(true);
        } else {
            warningMessage1.setVisible(false);
            warningMessage2.setVisible(false);
            stepThreePane.toFront();
        }
    }
    public void stepFour() {
        setDeliveryLocation();
        if (deliveryInformationNotValid() && cardInformationNotValid()) {
            warningMessage1.setText("Steg 2 och Steg 3 ej ifyllda!");
            warningMessage1.setVisible(true);
            warningMessage2.setText("Steg 2 och Steg 3 ej ifyllda!");
            warningMessage2.setVisible(true);
            warningMessage3.setText("Steg 2 och Steg 3 ej ifyllda!");
            warningMessage3.setVisible(true);
        } else if (deliveryInformationNotValid()) {
            warningMessage1.setText("Steg 2 ej ifylld!");
            warningMessage1.setVisible(true);
            warningMessage2.setText("Steg 2 ej ifylld!");
            warningMessage2.setVisible(true);
        } else if (cardInformationNotValid()) {
            warningMessage1.setText("Steg 3 ej ifylld!");
            warningMessage1.setVisible(true);
            warningMessage2.setText("Steg 3 ej ifylld!");
            warningMessage2.setVisible(true);
            warningMessage3.setText("Steg 3 ej ifylld!");
            warningMessage3.setVisible(true);
        } else{
            warningMessage1.setVisible(false);
            warningMessage2.setVisible(false);
            warningMessage3.setVisible(false);
            stepFourPane.toFront();
        }
    }

    private boolean deliveryInformationNotValid() {
        return customer.getAddress().equals("") || monthValue.equals("") || dayValue == 0 || timeValue.equals("");
    }

    private boolean cardInformationNotValid() {
        return cardHolderNameField.getText().equals("") || cardNumberField.getText().equals("")
                || monthField.getText().equals("") || monthField.getText().equals("MM")
                || yearField.getText().equals("") || yearField.getText().equals("YYYYF")
                || verificationCodeField.getText().equals("");
    }
}
