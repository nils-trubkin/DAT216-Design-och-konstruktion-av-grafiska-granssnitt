
package iMat;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import se.chalmers.cse.dat216.project.*;


public class CheckoutController implements Initializable, ShoppingCartListener {

    @FXML private MenuBar menuBar;
    @FXML private AnchorPane mainViewPane;
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
    @FXML private ComboBox<String> monthCheckoutComboBox;
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

    private boolean cardPaymentOpen = false;
    private boolean bankPaymentOpen = false;

    private String monthValue = "";
    private Integer dayValue  = 0;
    private String timeValue  = "";
    private String addressValue = "";

    private final Model model = Model.getInstance();

    private Customer customer = model.getCustomer();
    private CreditCard card = model.getCreditCard();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        deliveryLocation.setText("Levereras till: " + addressValue);
        monthCheckoutComboBox.getItems().addAll("Januari", "Februari", "Mars", "April", "Maj", "Juni", "Juli", "Augusti", "Septempber", "Oktober", "November", "December");
        monthCheckoutComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
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

    private void updateProductList() {
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
    
    @FXML 
    protected void openAboutActionPerformed(ActionEvent event) throws IOException{
    
        ResourceBundle bundle = ResourceBundle.getBundle("iMat/resources/iMat");
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
        mainViewPane.toFront();
    }
    public void checkOut() {
        stepOnePane.toFront();
        updateProductList();
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

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {

    }
}

