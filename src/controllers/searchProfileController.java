package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class searchProfileController {
	@FXML
    public void Back(ActionEvent event) throws IOException {
        Main.getInstance().changeScene("MainPage.fxml", "Main Page", 700, 500);
    }

}
