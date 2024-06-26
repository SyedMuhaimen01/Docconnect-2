module SDA {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires mysql.connector.j;
	requires javafx.graphics;
	
	opens controllers to javafx.graphics, javafx.fxml;
}
