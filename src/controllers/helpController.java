package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class helpController {
	@FXML
	private TextArea Feild;
	@FXML
	private void createAccount(ActionEvent event)
	{
		StringBuilder str = new StringBuilder();
		str.append("Navigate to the \"Register\" page.").append("\n");
		str.append("Fill in the required fields, including personal information and contact details.").append("\n");
		str.append("Click \"Register\" to create your account.").append("\n");
		
		Feild.setText(str.toString());
	}
	@FXML
	private void updateProfile(ActionEvent event)
	{
		StringBuilder str = new StringBuilder();
		str.append("Login to your account.").append("\n");
		str.append("Go to the \"Profile\" or \"Update Profile\" section.").append("\n");
		str.append("Update relevant information and save changes.").append("\n");
		
		Feild.setText(str.toString());
	}
	@FXML
	private void BookingAppointment(ActionEvent event)
	{
		StringBuilder str = new StringBuilder();
		str.append("Login to your account.").append("\n");
		str.append("Go to the \"Manage Appointment\" section.").append("\n");
		str.append("Go to the \"Book Appointment\" section.").append("\n");
		str.append("Choose a suitable date and time.").append("\n");
		str.append("click on \"Book\"").append("\n");
		str.append("Fill the Payment Details.").append("\n");
		str.append("click on \"Pay\"").append("\n");
		Feild.setText(str.toString());
	}
	@FXML
	private void customerSupport(ActionEvent event)
	{
		StringBuilder str = new StringBuilder();
		str.append("Reach out to our support team via phone, email, or live chat.").append("\n");
		str.append("Operating hours and response times are available on the \"Contact Us\" page.").append("\n");
		str.append("Ph: 0342-9674001 , 0330-5926891").append("\n");
		str.append("Email: i210888@nu.edu.pk , i210886@nu.edu.pk").append("\n");
		Feild.setText(str.toString());
	}
	@FXML
	private void privacyPolicy(ActionEvent event)
	{
		StringBuilder str = new StringBuilder();
		str.append("Privacy Policy\r\n"
				+ "\r\n"
				+ "Effective Date: 12/2/2023\r\n"
				+ "\r\n"
				+ "Thank you for choosing DOC CONNECT. This Privacy Policy is designed to help you understand how we collect, use, disclose, and safeguard your personal information.\r\n"
				+ "\r\n"
				+ "Information We Collect\r\n"
				+ "Personal Information\r\n"
				+ "In the course of providing our healthcare services, we may collect the following types of personal information:\r\n"
				+ "\r\n"
				+ "Contact Information:\r\n"
				+ "\r\n"
				+ "Full name\r\n"
				+ "Address\r\n"
				+ "Email address\r\n"
				+ "Phone number\r\n"
				+ "Medical Information:\r\n"
				+ "\r\n"
				+ "Health history\r\n"
				+ "Medications\r\n"
				+ "Allergies\r\n"
				+ "Treatment plans\r\n"
				+ "Payment Information:\r\n"
				+ "\r\n"
				+ "Credit card details\r\n"
				+ "Billing information\r\n"
				+ "Usage Information\r\n"
				+ "We may also collect non-personal information about how you interact with our website, including:\r\n"
				+ "\r\n"
				+ "Log Data:\r\n"
				+ "\r\n"
				+ "Internet Protocol (IP) addresses\r\n"
				+ "Browser type\r\n"
				+ "Pages visited\r\n"
				+ "Device Information:\r\n"
				+ "\r\n"
				+ "Device type\r\n"
				+ "Operating system\r\n"
				+ "Unique device identifiers\r\n"
				+ "How We Use Your Information\r\n"
				+ "We use the collected information for various purposes, including:\r\n"
				+ "\r\n"
				+ "Providing healthcare services\r\n"
				+ "Processing payments\r\n"
				+ "Improving and personalizing our services\r\n"
				+ "Sending updates and promotional materials\r\n"
				+ "Data Security\r\n"
				+ "We prioritize the security of your information and have implemented industry-standard measures to protect it from unauthorized access, disclosure, alteration, and destruction.\r\n"
				+ "\r\n"
				+ "Sharing Your Information\r\n"
				+ "We may share your information with:\r\n"
				+ "\r\n"
				+ "Healthcare professionals involved in your treatment\r\n"
				+ "Payment processors for billing purposes\r\n"
				+ "Legal authorities when required by law\r\n"
				+ "Your Choices\r\n"
				+ "You have the right to:\r\n"
				+ "\r\n"
				+ "Access, correct, or delete your personal information\r\n"
				+ "Opt-out of marketing communications\r\n"
				+ "Third-Party Links\r\n"
				+ "Our website may contain links to third-party websites. We are not responsible for their privacy practices and encourage you to review their policies.\r\n"
				+ "\r\n"
				+ "Changes to This Privacy Policy\r\n"
				+ "We may update our Privacy Policy periodically. We will notify you of any changes by posting the new policy on this page.\r\n"
				+ "\r\n"
				+ "Contact Us\r\n"
				+ "If you have any questions or concerns about our Privacy Policy, please contact us at i210888@nu.edu.pk.").append("\n");

		Feild.setText(str.toString());
	}
	@FXML
	private void contactUs(ActionEvent event)
	{
		StringBuilder str = new StringBuilder();
		str.append("Contact Us at:").append("\n");
		str.append("\n");
		str.append("Syed Ata ul Muhaimen Ahmad").append("\n");
		str.append("Ph: 0342-9674001 ").append("\n");
		str.append("Email: i210888@nu.edu.pk ").append("\n").append("\n");
		str.append("Syed Ahmad Mustafa Jala").append("\n");	
		str.append("Ph: 0330-5926891").append("\n");
		str.append("Email: i210886@nu.edu.pk").append("\n");
		Feild.setText(str.toString());
	}
	  @FXML
	    public void Back(ActionEvent event) throws IOException {
	        Main.getInstance().changeScene("MainPage.fxml", "Main Page", 700, 500);
	    }
}
