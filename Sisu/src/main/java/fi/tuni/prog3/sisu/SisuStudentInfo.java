package fi.tuni.prog3.sisu;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Year;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.util.StringConverter;

/**
 * Renders student information so it can be edited.
 */
public class SisuStudentInfo {
    public SisuStudentInfo() { }
    
    /**
     * Renders student information in full VBox
     * @param loggedStudent
     * @param allDegrees
     * @return student information VBox
     * @throws IOException
     * @throws MalformedURLException
     */
    public VBox getFullVBox(Student loggedStudent, TreeMap<String, DegreeModule> 
            allDegrees) throws IOException, MalformedURLException {

        // Creating a VBox for the left side.
        VBox dataVBox = new VBox();
        dataVBox.setPrefWidth(720);
        dataVBox.setPrefHeight(400);
        dataVBox.setStyle("-fx-background-color: #FFFFFF;");
        dataVBox.getChildren().add(new Label("Opiskelijan tiedot"));
        dataVBox.setSpacing(20);
        // Set padding
        Insets insets = new Insets(10);
        dataVBox.setPadding(insets);
        dataVBox.setAlignment(Pos.CENTER_LEFT);

        // All the inputfields
        var inputs = new VBox(5);

        // Name
        inputs.getChildren().add(new Label("Nimi:"));
        TextField inputName = new TextField();
        inputName.setText(loggedStudent.getName());
        inputs.getChildren().add(inputName);

        // Studentnumber
        inputs.getChildren().add(new Label("Opiskelijanumero:"));
        TextField inputStudentno = new TextField();
        inputStudentno.setText(loggedStudent.getStudentNumber());
        inputStudentno.setEditable(false);
        inputs.getChildren().add(inputStudentno);

        // Degree
        inputs.getChildren().add(new Label("Opintosuunta:"));
        ComboBox<DegreeModule> degreeBox = new ComboBox<DegreeModule>();
        degreeBox.setMaxWidth(Double.MAX_VALUE);
        ObservableList<DegreeModule> degrees = FXCollections.observableArrayList();
        for (String programmeId : allDegrees.keySet()){
            degrees.add(allDegrees.get(programmeId));
        }     
        degreeBox.setItems(degrees); 
        degreeBox.setConverter(new StringConverter<DegreeModule>() {
            @Override
            public String toString(DegreeModule object) {
                return object.getName();
            }
            @Override
            public DegreeModule fromString(String string) {
                return degreeBox.getItems().stream().filter(ap -> 
                    ap.getName().equals(string)).findFirst().orElse(null);
            }
        });        
        degreeBox.setValue(allDegrees
                .get(loggedStudent
                        .getSelectedDegreeProgramme()));
    
        inputs.getChildren().add(degreeBox);
        
        // Start and end year
        HBox hbYearSelector = new HBox(10);
        hbYearSelector.setAlignment(Pos.CENTER);
        Insets inset = new Insets(20);
        
        hbYearSelector.setPadding(inset);
        
        VBox yearVBox1 = new VBox();
        VBox yearVBox2 = new VBox();
        
        int year = Year.now().getValue();
        
        yearVBox1.getChildren().add(new Label("Aloitusvuosi:"));
        ComboBox<Integer> startYearSelector = new ComboBox<Integer>();
        startYearSelector.setMaxWidth(Double.MAX_VALUE);

        for (int i = year-8; i < year+8; i++ ){
            startYearSelector.getItems().add(i);
        }

        startYearSelector.setValue(loggedStudent.getStartYear());
        yearVBox1.getChildren().add(startYearSelector);
        hbYearSelector.getChildren().add(yearVBox1);
        
        
        yearVBox2.getChildren().add(new Label("Lopetusvuosi:"));
        ComboBox<Integer> endYearSelector = new ComboBox<Integer>();
        endYearSelector.setMaxWidth(Double.MAX_VALUE);
        for (int i = year-8; i < year+8; i++ ){
            endYearSelector.getItems().add(i);
        }
        endYearSelector.setValue(loggedStudent.getEndYear());
        yearVBox2.getChildren().add(endYearSelector);
        hbYearSelector.getChildren().add(yearVBox2);
        
        inputs.getChildren().add(hbYearSelector);
        
        dataVBox.getChildren().add(inputs);
        
        HBox hbBtn = new HBox(10);
        
        Insets insets2 = new Insets(20, 0, 20, 20);
        
        hbBtn.setPadding(insets2);
        hbBtn.setAlignment(Pos.CENTER_RIGHT);
        Button btnReset = new Button("Eiku");
        
        btnReset.setOnAction((ActionEvent event) -> {
            inputName.setText(loggedStudent.getName());
            degreeBox.setValue(allDegrees
                    .get(loggedStudent
                            .getSelectedDegreeProgramme()));
        });
        
        Button btnSave = new Button("Tallenna");
        
        // On button click set all the values to the current object.
        btnSave.setOnAction((ActionEvent event) -> {
            loggedStudent.setName(inputName.getText());
            loggedStudent.setSelectedDegreeProgramme(degreeBox.getValue().getId());
            loggedStudent.setStartYear(startYearSelector.getValue());
            loggedStudent.setEndYear(endYearSelector.getValue());
        });
        
        hbBtn.getChildren().add(btnReset);
        hbBtn.getChildren().add(btnSave);
        inputs.getChildren().add(hbBtn);
        
        return dataVBox;
    }
    
}