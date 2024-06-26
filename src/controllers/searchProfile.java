package controllers;

import java.io.IOException;

public class searchProfile implements SearchStrategy {
	  @Override
	    public void search(String userInput) throws IOException {
	        
	        System.out.println("Invalid input or general search for: " + userInput);
	        Main.getInstance().changeScene("searchProfile.fxml", "Search Profile", 700, 500);
	    }

}
