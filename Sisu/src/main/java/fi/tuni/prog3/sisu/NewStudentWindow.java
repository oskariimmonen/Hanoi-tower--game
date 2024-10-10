/**
 * <p>
 * @author Oskari Immonen
 */

package fi.tuni.prog3.sisu;

import java.util.TreeMap;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage; 
import javafx.scene.control.TextField; 
import javafx.scene.text.Font; 
import javafx.scene.text.FontWeight; 
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections; 
import javafx.collections.ObservableList;  
import javafx.util.StringConverter; 
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.MalformedURLException;

    /**
     * 
     * @param loggedStudent
     * @param allDegrees
     * @return
     * @throws IOException
     * @throws MalformedURLException 
     */

public class NewStudentWindow {
    private TreeMap<String, DegreeModule> degrees;
    private TreeMap<String, Student> students; 
    private final Stage stage; 
    private final Label createUserLabel; 
    private final Label nameLabel;
    private final TextField nameField; 
    private final Label studentNumberLabel;
    private final TextField studentNumberField; 
    private final Label startYearLabel;
    private final TextField startYearField; 
    private final Label endYearLabel; 
    private final TextField endYearField; 
    private final Label fieldOfStudyLabel; 
    private final Button returnButton; 
    private final Button continueButton; 
    private final VBox startYearBox; 
    private final VBox endYearBox; 
    private final ComboBox<Integer> startYearSelector; 
    private final ComboBox<Integer> endYearSelector;  
    private final ComboBox<DegreeModule> comboBox;
     

    /**
     * NewStudentWindow GUI implementation
     * @param stage
     * @param degrees
     * @param students
     */
    NewStudentWindow(Stage stage,TreeMap<String, DegreeModule> degrees, TreeMap<String, Student> students) {
        this.degrees = degrees;
        this.students = students; 
        this.stage = stage;
        
        GridPane pane = new GridPane(); 
        pane.setAlignment(Pos.CENTER); 
        
        createUserLabel = new Label("Luo käyttäjä: "); 
        createUserLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15)); 
       
        nameLabel = new Label("Nimi: ");
        nameField = new TextField(); 
        
        studentNumberLabel = new Label("Opiskelijanumero: "); 
        studentNumberField = new TextField(); 
        
        startYearLabel = new Label("Aloitusvuosi: "); 
        startYearField = new TextField(); 
        
        endYearLabel = new Label("Arvioitu valmistumisvuosi: ");
        endYearField = new TextField(); 
        
        fieldOfStudyLabel = new Label("Opintosuunta: ");  
        
        comboBox = new ComboBox();
        
        comboBox.setMaxWidth(Double.MAX_VALUE);
        ObservableList<DegreeModule> degrees_ = FXCollections.observableArrayList();
        
        for (String programmeId : this.degrees.keySet()){
            degrees_.add(this.degrees.get(programmeId));
        }
        
        comboBox.setItems(degrees_); 
        
       
      
        comboBox.setConverter(new StringConverter<DegreeModule>() {

            @Override
            public String toString(DegreeModule object) {
                return object.getName();
            }

            @Override
            public DegreeModule fromString(String string) {
                return comboBox.getItems().stream().filter(ap -> 
                    ap.getName().equals(string)).findFirst().orElse(null);
            }
        });
        

        comboBox.setValue(degrees.get(degrees.firstKey()));

        returnButton = new Button("Eiku"); 
        continueButton = new Button("Luo käyttäjä"); 
        
        startYearBox = new VBox(); 
        endYearBox = new VBox();  
        
            
        int year = 2022;
      
        startYearSelector = new ComboBox<Integer>();
        startYearSelector.setMaxWidth(Double.MAX_VALUE);
        for (int i = year-8; i < year+8; i++ ){
            startYearSelector.getItems().add(i);
        }
        startYearSelector.setValue(year);
        startYearBox.getChildren().add(startYearSelector);  
        
        endYearSelector = new ComboBox<Integer>();
        endYearSelector.setMaxWidth(Double.MAX_VALUE);
        for (int i = year-8; i < year+8; i++ ){
            endYearSelector.getItems().add(i);
        }
        endYearSelector.setValue(year);
        endYearBox.getChildren().add(endYearSelector);
  
        

        
        
        
        pane.add(createUserLabel, 0, 0);
        pane.add(nameLabel, 0, 1); 
        pane.add(nameField, 1, 1); 
        pane.add(studentNumberLabel, 0, 2); 
        pane.add(studentNumberField, 1,2);
        pane.add(startYearLabel, 0, 3); 
        //pane.add(startYearField, 1, 3); 
        
        pane.add(startYearBox, 1,3);
        pane.add(endYearLabel, 0, 4); 
        //pane.add(endYearField, 1, 4); 
        
        pane.add(endYearBox,1 , 4);
        pane.add(fieldOfStudyLabel, 0, 5); 
        pane.add(comboBox, 1, 5); 
        pane.add(returnButton, 0, 6); 
        pane.add(continueButton, 1, 6);
        
                
        pane.setStyle("-fx-background-color: #8fc6fd;");
        pane.setHgap(10); 
        pane.setVgap(10);
        
        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene); 
        stage.setTitle("SisuGUI");
        stage.show(); 
        
        onReturnButtonClick(); 
        onContinueButtonClick();
    }
    
    /**
     * This runs when return button is clicked. User is taken to LoginWindow.
     */
    private void onReturnButtonClick(){
        returnButton.setOnAction((var e) -> {
            new SisuLogInWindow(stage, degrees, students);
        });       
    } 
    
    /**
     * Checks if user has written down a name and a student number. If they are ok, stores a new student account with given inputs and 
     * user is taken to MainWindow. Othwerwise prints error messages to studentNumberField and nameField.
     */
    private void onContinueButtonClick() { 
        
        continueButton.setOnAction((ActionEvent event) -> { 
        
        // User must give a name and a studentnumber. Checks for empty fields.
        if(studentNumberField.getText().isEmpty()|| nameField.getText().isEmpty() ) {
          
           studentNumberField.setText("Syötä opiskelijanumero"); 
           studentNumberField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
           
           nameField.setText("Syötä nimi"); 
           nameField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
           
        } else {
            
        //Storing the new "account"
        Student newStudent = new Student(); 
        newStudent.setStudentNumber(studentNumberField.getText()); 
        newStudent.setName(nameField.getText()); 
        newStudent.setSelectedDegreeProgramme(comboBox.getValue().getId());  
        newStudent.setStartYear(startYearSelector.getValue()); 
        newStudent.setEndYear(endYearSelector.getValue()); 
        
        students.put(studentNumberField.getText(), newStudent);  
        }
        
            if(this.students.containsKey(studentNumberField.getText())){
             try {
            new SisuMainWindow(this.stage, this.degrees, students.get(studentNumberField.getText()), students);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            } else {
                studentNumberField.setText("Syötä opiskelijanumero"); 
                studentNumberField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
            }

        });

    }
}