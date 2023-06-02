package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;
import repository.UserRepository;

public class LoginController implements Initializable {

    @FXML
    private Label passwordErrorLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label usernameErrorLabel;

    @FXML
    private TextField usernameField;
    
    private Scene scene;
    private Parent parent;
    private Stage stage;
    private User user;
    
    UserRepository userRepository= new UserRepository();

    List <User> users=new ArrayList<>();
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		users.addAll(userRepository.findAll());
		clearLabel();
	}	
    
    @FXML
    void login(MouseEvent event) throws IOException {
    	if(checkField()) {
    		if (checkUser()) {
    			FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/Home.fxml"));
    			parent= loader.load();
    			HomeController cont = loader.getController();
    			cont.login(user);
    			stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
    			scene=new Scene (parent);
    			stage.setScene(scene);
    			stage.setTitle("Login");
    			stage.show();
    		} else {
    			usernameErrorLabel.setText("This username is not exist");
    			passwordErrorLabel.setText("This password is not exist");
    		}
    	}
    }

    @FXML
    void registration(MouseEvent event) throws IOException {
    	FXMLLoader loader=new FXMLLoader(getClass().getResource("/Views/Registration.fxml"));
		parent= loader.load();
		RegistrationController controller = loader.getController();
		controller.getData();
		stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
		scene=new Scene (parent);
		stage.setScene(scene);
		stage.setTitle("Login");
		stage.show();
    }

	public void getData() {
		users.clear();
		users.addAll(userRepository.findAll());
		
	}
	
	boolean checkField() {
		boolean result=true;
		if(usernameField.getText().isEmpty()) {
			usernameErrorLabel.setText("Please, input your username");
			result=false;
		}else {
			usernameErrorLabel.setText("");
		}
		if(passwordField.getText().isEmpty()) {
			passwordErrorLabel.setText("Please, input you password");
			result=false;
		}else {
			passwordErrorLabel.setText("");
		}
		return result;
	}
	
	boolean checkUser() {
		for(User user : users) {
			if(usernameField.getText().equals(user.getUsername()) && passwordField.getText().equals(user.getPassword())) {
				this.user= user;
				return true;
			}
		}
		return false;
	}
	
	void clearLabel(){
		usernameErrorLabel.setText("");
		passwordErrorLabel.setText("");
	}

}
