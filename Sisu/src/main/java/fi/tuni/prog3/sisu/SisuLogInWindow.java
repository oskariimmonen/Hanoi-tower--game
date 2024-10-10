/**
 * <p>
 * @author Oskari Immonen
 */
package fi.tuni.prog3.sisu;

import javafx.stage.Stage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.TreeMap;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox; 
import javafx.scene.control.TextField; 
import javafx.scene.text.Font; 
import javafx.scene.text.FontWeight;



public class SisuLogInWindow {
    private final Stage stage;
    private TreeMap<String, DegreeModule> degrees;
    private TreeMap<String, Student> students;
    private Student loggedStudent; 
    private final Label logInLabel;
    private final Label studentNumberLabel;
    private final TextField studentNumberField;  
    private final VBox vbox; 
    private final Button loginButton; 
    private final Button newStudentButton;
    
/**
 * LoginWindow GUI implementation
 * @param stage
 * @param degrees
 * @param students
 */
    SisuLogInWindow(Stage stage, TreeMap<String, DegreeModule> degrees, TreeMap<String, Student> students) {
        //Creating a new BorderPane.
        this.stage = stage;
        this.degrees = degrees;
        this.students = students; 

        GridPane pane = new GridPane(); 
        pane.setAlignment(Pos.CENTER);
        
        logInLabel = new Label("Tervetuloa!"); 
        logInLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        studentNumberLabel = new Label("Opiskelijanumero: "); 
        studentNumberField = new TextField(); 
        
        loginButton = new Button("Kirjaudu sisään"); 
        newStudentButton = new Button("Luo uusi käyttäjä");
        var quitButton = getQuitButton(); 
        
        vbox = new VBox(); 
        vbox.getChildren().add(loginButton);
        vbox.setAlignment(Pos.BOTTOM_RIGHT); 
                
        GridPane bottomLine = new GridPane(); 
        bottomLine.setAlignment(Pos.BOTTOM_RIGHT); 
        bottomLine.add(quitButton, 0, 0); 
        
        pane.add(logInLabel, 0, 0); 
        pane.add(studentNumberLabel, 0, 1); 
        pane.add(newStudentButton, 0, 2);
        pane.add(studentNumberField, 1, 1);  
        pane.add(vbox, 1, 2);  
        pane.add(bottomLine, 1, 3); 
        pane.setStyle("-fx-background-color: #8fc6fd;");
        
        pane.setHgap(10); 
        pane.setVgap(10); 

        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene); 
        stage.setTitle("SisuGUI");
        stage.show();
                
        onLoginButtonClick(); 
        onNewStudentButtonClick();
    }


    
    /**
     * Implements the quit-button. 
     * @returns the button as a var
     */
    private Button getQuitButton() {
        //Creating a button.
        Button button = new Button("Lopeta");
        
        //Adding an event to the button to terminate the application.
        button.setOnAction((ActionEvent event) -> {
            Platform.exit();
        });
        
        return button;
    }
    
    /**
     * This runs when the login-button is clicked
     */
    private void onLoginButtonClick() { 
        loginButton.setOnAction((ActionEvent event) -> {
            
            String studentNumber = studentNumberField.getText(); 
            if(this.students.containsKey(studentNumber)){
                            try {
                new SisuMainWindow(this.stage, this.degrees, students.get(studentNumber), students);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            } else {
                studentNumberField.setText("Opiskelijanumeroa ei löydy"); 
                studentNumberField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
            }
        });
    } 
    
    /**
     * This runs when create a new student button is clicked. Opens a new window where 
     * the user can create a new "account"
     */
    private void onNewStudentButtonClick(){
        newStudentButton.setOnAction(e -> {
            new NewStudentWindow(stage, degrees, students);
        });        
    }
}


