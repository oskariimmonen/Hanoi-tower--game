package fi.tuni.prog3.sisu;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.TreeMap;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SisuMainWindow {
    private TreeMap<String, DegreeModule> degrees;
    private Student loggedStudent;
    /**
     * Main window of the program.
     * @param stage 
     * @param degrees all degree programmes
     * @param loggedStudent Currently logged student
     * @param students all students
     * @throws IOException
     * @throws MalformedURLException
     */
    SisuMainWindow(Stage stage, TreeMap<String, DegreeModule> degrees, Student loggedStudent, TreeMap<String, Student> students) throws IOException, MalformedURLException {
        this.degrees = degrees;
        this.loggedStudent = loggedStudent;
        
        var fileWrite = new FileReadWriteFunctions();
        
        SisuDegreeProgrammeInfo degreeProgrammesHandler = new SisuDegreeProgrammeInfo();
        SisuStudentInfo studentInfoHandler = new SisuStudentInfo();
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));
        
        TabPane tabPane = new TabPane();
        
        // Opintojen rakenne tab
        Tab tab0 = new Tab("Opintojen rakenne");
        DegreeModule selectedDegree = degrees.get(loggedStudent.getSelectedDegreeProgramme());
        tab0.setContent(degreeProgrammesHandler.showSelectedProgrammeStructure(loggedStudent, selectedDegree)); 
        
        tab0.setClosable(false);

        // Opiskelijan tiedot tab 
        Tab tab1 = new Tab("Opiskelijan tiedot");
        tab1.setContent(studentInfoHandler.getFullVBox(loggedStudent, degrees)); 
        tab1.setClosable(false);
        
        // Add tabs together and setTop root
        tabPane.getTabs().addAll(tab0, tab1); 
        root.setTop(tabPane);

        // Always re-render tab0 when its selected
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
                if(newTab == tab0) {
                    DegreeModule newDegree = degrees.get(loggedStudent.getSelectedDegreeProgramme());
                    tab0.setContent(degreeProgrammesHandler.showSelectedProgrammeStructure(loggedStudent, newDegree));
                }
            }
        });

        //Adding button to the BorderPane and aligning it to the right.
        //var quitButton = getQuitButton();
        
        Button quitButton = new Button("Lopeta");
        
        //Adding an event to the button to terminate the application.
        quitButton.setOnAction((ActionEvent event) -> {
            students.replace(loggedStudent.getStudentNumber(), loggedStudent);
            fileWrite.writeFile(students);
            Platform.exit();
        });
        
        BorderPane.setMargin(quitButton, new Insets(10, 10, 0, 10));
        root.setBottom(quitButton);
        BorderPane.setAlignment(quitButton, Pos.TOP_RIGHT);
        
        Scene scene = new Scene(root, 800, 500);                      
        stage.setScene(scene);
        stage.setTitle("SisuGUI");
        stage.show();
    }
}
