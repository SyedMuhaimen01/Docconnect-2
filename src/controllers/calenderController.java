package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class calenderController {
	@FXML
    public void Back(ActionEvent event) throws IOException {
        Main.getInstance().changeScene("book.fxml", "Booking ", 292, 198);
    }

}
