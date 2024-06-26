package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ProfessionalRegisterationController {
    @FXML
    private ComboBox<String> affiliationComboBox;

    @FXML
    private ComboBox<String> designationComboBox;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField cnicField;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField phField;

    @FXML
    private TextField MedicalIdField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField reEnterPasswordField;

    public void initialize() {
        
        populateAffiliationComboBox();
        populateDesignationComboBox();
    }

    private void populateAffiliationComboBox() {
        // JDBC connection parameters
        String url = "jdbc:mysql://localhost:3306/SDA";
        String username = "root";
        String password = "123456";

        
        String sql = "SELECT centerName FROM centers";

        try (Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            ObservableList<String> medicalCenters = FXCollections.observableArrayList();

            while (resultSet.next()) {
                String centerName = resultSet.getString("centerName");
                medicalCenters.add(centerName);
            }

            affiliationComboBox.setItems(medicalCenters);

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    private void populateDesignationComboBox() {
        
        ObservableList<String> Designation = FXCollections.observableArrayList();
        Designation.add("Physician");
        Designation.add("Nurse");
        Designation.add("Nurse Practitioner");
        Designation.add("Physician Assistant");
        Designation.add("Pharmacist");
        Designation.add("Dentist");
        Designation.add("Optometrist");
        Designation.add("Physical Therapist");
        Designation.add("Occupational Therapist");
        Designation.add("Speech-Language Pathologist");
        Designation.add("Radiologist");
        Designation.add("Respiratory Therapist");
        Designation.add("Chiropractor");

        designationComboBox.setItems(Designation);
    }

    @FXML
    public void registerOptions(ActionEvent event) throws IOException {
        Main.getInstance().changeScene("registerOptions.fxml", "Register Options", 292, 188);
    }

    private boolean flag = false;

    private void saveToDatabase(String username, String firstName, String lastName, String cnic, String ph,
            String medicalId, String affiliation, String designation, String affiliationStatus, String email,
            String password) {

        initialize();

        String url = "jdbc:mysql://localhost:3306/SDA";
        String username1 = "root";
        String password1 = "123456";

        Connection connection = null;
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            connection = DriverManager.getConnection(url, username1, password1);

            if (connection != null && !connection.isClosed()) {
                System.out.println("Connected to the database!");
                if (isUserNameRegistered(username) == false && isMedicalIdValid(medicalId) == false) {
                   
                    String sql = "INSERT INTO professional (username, firstName, lastName, cnic, ph,medicalId,affiliation,designation,affiliationStatus, email, password) VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?,?)";
                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setString(1, username);
                        statement.setString(2, firstName);
                        statement.setString(3, lastName);
                        statement.setString(4, cnic);
                        statement.setString(5, ph);
                        statement.setString(6, medicalId);
                        statement.setString(7, affiliation);
                        statement.setString(8, designation);
                        statement.setString(9, affiliationStatus);
                        statement.setString(10, email);
                        statement.setString(11, password);

                       
                        statement.executeUpdate();
                    }
                } else {
                    if (isUserNameRegistered(username)) {
                        errorLabel.setText("Username already registered!");
                    } else if (isMedicalIdValid(medicalId)) {
                        errorLabel.setText("Medical ID already registered!");
                    }
                    flag = true;
                }
            } else {
                System.out.println("Failed to connect to the database!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        } finally {
            // Close the connection in the finally block
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception appropriately
            }
        }
    }

    @FXML
    private void handleRegisterButton(ActionEvent event) throws IOException {
       
        String username = usernameField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String cnic = cnicField.getText();
        String ph = phField.getText();
        String medicalId = MedicalIdField.getText();
        String affiliation = affiliationComboBox.getValue();
        String designation = designationComboBox.getValue();
        String affiliationStatus = "Pending";
        String email = emailField.getText();
        String password = passwordField.getText();
        String reEnterPassword = reEnterPasswordField.getText();

        if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || cnic.isEmpty() || ph.isEmpty()
                || email.isEmpty() || password.isEmpty() || reEnterPassword.isEmpty() || medicalId.isEmpty()
                || affiliation.isEmpty() || designation.isEmpty() || affiliationStatus.isEmpty()) {
            errorLabel.setText("Please fill in all fields!");
            return; 
        }

        
        saveToDatabase(username, firstName, lastName, cnic, ph, medicalId, affiliation, designation,
                affiliationStatus, email, password);

        
        clearFields();
        if (flag == false) {
            Main.getInstance().changeScene("professionalProfile.fxml", " Professional Profile", 700, 500);
        }
    }

    private void clearFields() {
        
        usernameField.clear();
        firstNameField.clear();
        lastNameField.clear();
        cnicField.clear();
        phField.clear();
        MedicalIdField.clear();

        emailField.clear();
        passwordField.clear();
        reEnterPasswordField.clear();
    }

    private boolean isUserNameRegistered(String username) {
        String url = "jdbc:mysql://localhost:3306/SDA";
        String username1 = "root";
        String password1 = "123456";

        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username1, password1);

            if (connection != null && !connection.isClosed()) {
                String sql = "SELECT * FROM professional WHERE username = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, username);

                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0;
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception appropriately
            }
        }
        return false;
    }

    private boolean isMedicalIdValid(String medicalId) {
        String url = "jdbc:mysql://localhost:3306/SDA";
        String username1 = "root";
        String password1 = "123456";

        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username1, password1);

            if (connection != null && !connection.isClosed()) {
                String sql = "SELECT * FROM professional WHERE medicalId = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, medicalId);

                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0; 
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception appropriately
            }
        }
        return false;
    }
}
