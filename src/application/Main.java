package application;
	
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class main extends Application {
	
	
	private static final Logger logger = Logger.getLogger(Main.class.getName());
	private UserDao userDao = new UserDao();
	
	
	@Override
	public void start(Stage primaryStage) {
		
		
		
		
		primaryStage.setTitle("JavaFX Welcome");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(15);
		grid.setVgap(15);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text scenetitle = new Text("Student registration form");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		 Label fullNameLabel = new Label("Full Name:");
	        grid.add(fullNameLabel, 0, 1);

	        TextField fullNameTextField = new TextField();
	        grid.add(fullNameTextField, 1, 1);

	        Label lastNameLabel = new Label("Last Name:");
	        grid.add(lastNameLabel, 0, 2);

	        TextField lastNameTextField = new TextField();
	        grid.add(lastNameTextField, 1, 2);

	        Label emailLabel = new Label("Email:");
	        grid.add(emailLabel, 0, 3);

	        TextField emailTextField = new TextField();
	        grid.add(emailTextField, 1, 3);

	        Label dobLabel = new Label("Date of Birth:");
	        grid.add(dobLabel, 0, 4);

	        TextField dobTextField = new TextField();
	        grid.add(dobTextField, 1, 4);

	        Label userNameLabel = new Label("Username:");
	        grid.add(userNameLabel, 0, 5);

	        TextField usernameTextField = new TextField();
	        grid.add(usernameTextField, 1, 5);

	        Label passwordLabel = new Label("Password:");
	        grid.add(passwordLabel, 0, 6);

	        PasswordField passwordField = new PasswordField();
	        grid.add(passwordField, 1, 6);

	        Label genderLabel = new Label("Gender:");
	        grid.add(genderLabel, 0, 7);

	        ToggleGroup genderGroup = new ToggleGroup();

	        RadioButton maleRadio = new RadioButton("Male");
	        maleRadio.setToggleGroup(genderGroup);

	        RadioButton femaleRadio = new RadioButton("Female");
	        femaleRadio.setToggleGroup(genderGroup);

	        RadioButton otherRadio = new RadioButton("Other");
	        otherRadio.setToggleGroup(genderGroup);

	        HBox genderBox = new HBox(10);
	        genderBox.getChildren().addAll(maleRadio, femaleRadio, otherRadio);
	        grid.add(genderBox, 1, 7);

	        Label courseLabel = new Label("Course:");
	        grid.add(courseLabel, 0, 8);

	        TextField courseTextField = new TextField();
	        grid.add(courseTextField, 1, 8);

		Button saveButton = new Button("Register");
		HBox hBox = new HBox(10);
		hBox.setAlignment(Pos.BOTTOM_LEFT);
		hBox.getChildren().add(saveButton);
		grid.add(hBox, 1, 9);
		
		
		/*
		 * ADD THE EVENT HANDLING --ONACTION METHOD.. TO DEAL WITH SAVE OPERATION
		 */
		
		saveButton.setOnAction(actionEvent -> {

			String username = usernameTextField.getText().trim();
			String lastName = lastNameTextField.getText().trim();
			String firstName = fullNameTextField.getText().trim();
			String password = passwordField.getText();
			
			if (!StringPool.BLANK.equals(username) && !StringPool.BLANK.equals(lastName)
					&& !StringPool.BLANK.equals(firstName) && !StringPool.BLANK.equals(password)) {
				try {
					if (!userDao.userExists(username)) {
						User user = this.createUserObject(username, lastName, firstName, password);
						int userId = userDao.saveUser(user);
						if (userId > 0) {
							this.alert("Save", "Successful!", AlertType.INFORMATION);
						} else {
							this.alert("Error", "Failed!", AlertType.ERROR);
						}
					} else {
						this.alert("Error", "User already exists!", AlertType.ERROR);
					}
				} catch (Exception exception) {
					logger.log(Level.SEVERE, exception.getMessage());
				}
			} else {
				this.alert("Error", "Please complete fields!", AlertType.ERROR);
			}

			
			
			
		});
		
		
		Scene scene = new Scene(grid, 300, 275);
		primaryStage.setScene(scene);

		primaryStage.show();
		
		
		
	}
	

	public void alert(String title, String message, AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);

		alert.showAndWait();
	}

	public User createUserObject(String username, String lastName, String firstName, String password) {
		User user = new User();
		user.setUsername(username);
		user.setLastName(lastName);
		user.setFirstName(firstName);
		user.setPassword(password);

		return user;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}