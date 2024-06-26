package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class registerOptionsController {
	public void exit(ActionEvent event) throws IOException {
        Main.getInstance().changeScene("MainPage.fxml", "Main Page", 700, 500);
    }

    @FXML
    public void registerUser(ActionEvent event) throws IOException {
    	 Main.getInstance().changeScene("customerRegisteration.fxml", "customer Registeration", 700, 500);
        
        }

    public void registerProfessional(ActionEvent event) throws IOException {
   	 Main.getInstance().changeScene("ProfessionalRegisteration.fxml", "Professional Registeration", 700, 500);
       
       }
    public void registerCenter(ActionEvent event) throws IOException {
   	 Main.getInstance().changeScene("centerRegis.fxml", "Center Registeration", 700, 500);
       
       }
}
