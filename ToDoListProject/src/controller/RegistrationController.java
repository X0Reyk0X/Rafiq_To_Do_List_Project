package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;
import repository.UserRepository;

public class RegistrationController {

    @FXML
    private PasswordField confirmpasswordField;

    @FXML
    private Label confirmpasswordRegistrationErrorLabel;

    @FXML
    private TextField nameField;

    @FXML
    private Label nameRegistrationErrorLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label passwordRegistrationErrorLabel;

    @FXML
    private TextField surnameField;

    @FXML
    private Label surnameRegistrationErrorLabel;

    @FXML
    private TextField usernameField;

    @FXML
    private Label usernameRegistrationErrorLabel;


    private Scene scene;
    private Parent parent;
    private Stage stage;
    UserRepository userRepository= new UserRepository();
    
    List<User> users=new ArrayList<>();
    

	@FXML
    void login(MouseEvent event) throws IOException {
    		FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/Login.fxml"));
    		parent= loader.load();
    		LoginController controller = loader.getController();
    		controller.getData();
    		stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
    		scene=new Scene (parent);
    		stage.setScene(scene);
    		stage.setTitle("Login");
    		stage.show();
    }

    @FXML
    void registration(MouseEvent event) throws IOException {
    	if(fieldCheckForEmpty()) {
    		if(findUser()) {
    			if(checkPasswordField()) {
    				userRepository.add(new User(usernameField.getText(),nameField.getText(),surnameField.getText(),passwordField.getText()));
    				login(event);
    			}
    		}else {
    			usernameRegistrationErrorLabel.setText("This username is already used");
    		}
    	}
    	
    }
    public void getData() {
    	clearErrorLogs();
    	users.addAll(userRepository.findAll());
	}
    boolean  fieldCheckForEmpty() {
    	if(nameField.getText().isEmpty()) {
    		nameRegistrationErrorLabel.setText("input the name");
    	} else {
    		nameRegistrationErrorLabel.setText("");
    	}
    	if (surnameField.getText().isEmpty()) {
    		surnameRegistrationErrorLabel.setText("Input the surname");
    	}
    	else {
    		surnameRegistrationErrorLabel.setText("");
    	}
    	if(usernameField.getText().isEmpty()) {
    		usernameRegistrationErrorLabel.setText("Input the username");
    	}
    	else {
    		usernameRegistrationErrorLabel.setText("");
    	}
    	if(passwordField.getText().isEmpty()) {
    		passwordRegistrationErrorLabel.setText("Input the password");
    	}
    	else {
    		passwordRegistrationErrorLabel.setText("");
    	}
    	if(confirmpasswordField.getText().isEmpty()) {
    		confirmpasswordRegistrationErrorLabel.setText("Input the password");
    	}
    	else {
    	 confirmpasswordRegistrationErrorLabel.setText("");
    	}
    	
    	if(!nameField.getText().isEmpty()&&!surnameField.getText().isEmpty() && !usernameField.getText().isEmpty()
    			&&!passwordField.getText().isEmpty() &&!confirmpasswordField.getText().isEmpty()) {
    		return true;
    	}else {
    		return false;
    	}
    }
    	boolean findUser() {
    		boolean result =true;
    		for(User user :users) {
    			if(usernameField.getText().trim().equals(user.getUsername())) {
    				result=false;
    				break;
    				
    			}
    		}
    		return result;
    	}
    	
    boolean checkPasswordField() {
    	
    	if(passwordField.getText().equals(confirmpasswordField.getText())) {
    		if(passwordField.getText().length()>= 8 && passwordField.getText().length()<16) {
    			return true;
    		} else {
    			passwordRegistrationErrorLabel.setText("Password must be at least 8 and no more than 16");
    			return false;
    		}
    		}else {
    			passwordRegistrationErrorLabel.setText("Password is not match");
    			return false;
    		}
    	
    	
    }	
    private void clearErrorLogs() {
    	nameRegistrationErrorLabel.setText("");
    	surnameRegistrationErrorLabel.setText("");
    	usernameRegistrationErrorLabel.setText("");
    	passwordRegistrationErrorLabel.setText("");
    	confirmpasswordRegistrationErrorLabel.setText("");
    }
  

    
}

