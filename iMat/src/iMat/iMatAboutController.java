
package iMat;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class iMatAboutController implements Initializable {
    
    @FXML private Button closeAboutButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {}
    
    @FXML 
    protected void closeAboutActionPerformed(ActionEvent event) throws IOException{
        Stage aboutStage = (Stage) closeAboutButton.getScene().getWindow();
        aboutStage.hide();
    }    
}
