
package iMat;

import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class iMat extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {

        ResourceBundle bundle = java.util.ResourceBundle.getBundle("iMat/resources/iMat");

        Parent rootMain = FXMLLoader.load(getClass().getResource("iMat.fxml"), bundle);

        Scene main = new Scene(rootMain, 1500, 800);
        
        stage.setTitle(bundle.getString("application.name"));
        stage.setScene(main);
        stage.setMinWidth(1500);
        stage.setMinHeight(800);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                Model.getInstance().shutDown();
            }
        }));
    }
    
}
