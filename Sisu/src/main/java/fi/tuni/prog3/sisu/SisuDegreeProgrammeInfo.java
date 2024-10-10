package fi.tuni.prog3.sisu;

import java.io.IOException;
import java.util.Collection;
import java.util.TreeMap;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


public class SisuDegreeProgrammeInfo {
    private VBox rightVBox;
    private TreeView<String> leftTreeView;
    private TreeMap<String, DegreeModule> subModulesByName;
    private Student student;
    public SisuDegreeProgrammeInfo() {
        ;
    }

    /**
     * Creates Opintojen rakenne window
     * @param loggedStudent Currently logged student
     * @param selectedDegreeProgramme Degree programme student has selected
     * @return Hbox containing opintojen rakenne content
     */
    public HBox showSelectedProgrammeStructure(Student loggedStudent, DegreeModule selectedDegreeProgramme) {
        try {
            this.student = loggedStudent;
            //Creating an HBox.
            HBox centerHBox = new HBox(10);
            
            TreeMap<String, DegreeModule> subModules = selectedDegreeProgramme.getSubModules();
            subModulesByName = new TreeMap<>();
            for (DegreeModule module : subModules.values()) {
                subModulesByName.put(module.getName(), module);
            }
            this.rightVBox = getRightVBox();
            this.leftTreeView = getLeftTreeView(selectedDegreeProgramme.getName(),subModules);
            centerHBox.getChildren().addAll(leftTreeView, rightVBox);
            
            return centerHBox;
        } catch (IOException e) {
            HBox centerHBox = new HBox(10);
            return centerHBox;
        }
    }

    /**
     * Craetes Vbox for the right side of the winodw
     * @return Vbox with a label
     */
    private VBox getRightVBox() {
        //Creating a VBox for the right side.

        VBox rightVBox = new VBox();
        rightVBox.setPrefWidth(450);
        rightVBox.setStyle("-fx-background-color: #b1c2d4;");
        
        rightVBox.getChildren().add(new Label("Valitun opintokokonaisuuden kurssit:"));
        
        return rightVBox;
    }

    /**
     * Create TreeView for the left window
     * @param mainProgramme Selected programme's name
     * @param subModules Sub modules of the selected programme
     * @return TreeView<String> containing right submodules for the tree
     */
    private TreeView<String> getLeftTreeView(String mainProgramme, TreeMap<String, DegreeModule> subModules) {
        TreeView<String> treeView = new TreeView<>();
        TreeItem<String> degreeProgrammes = new TreeItem<String> (mainProgramme);

        for (String programmeId : subModules.keySet()){
            TreeItem<String> row = new TreeItem<String> (subModules.get(programmeId).getName());
            try {
                TreeMap<String, DegreeModule> test_ = subModules.get(programmeId).getSubModules();
                for (String innerId : test_.keySet()) {
                    if (!test_.get(innerId).getType().equals("CourseUnit")){
                        subModulesByName.put(test_.get(innerId).getName(), test_.get(innerId));
                        TreeItem<String> innerModule = new TreeItem<String> (test_.get(innerId).getName());
                        row.getChildren().add(innerModule);
                    }
                }
                
            } catch (IOException e){
                ;
            } catch (NullPointerException e) {
                ;
            }
            degreeProgrammes.getChildren().add(row);
        }
        treeView.setRoot(degreeProgrammes);
        treeView.setPrefWidth(450);
        treeView.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    showCourses(subModulesByName.get(newValue.getValue()), observable.getValue());
                });;
        return treeView;
    }

    /**
     * Add sub element to tree and add courses to right side window
     * @param module Selected parent module
     * @param parentItem Selected parent element
     */
    private void showCourses(DegreeModule module, TreeItem<String> parentItem) {
        
        boolean addModules = false;
        if (parentItem.isLeaf()){
            addModules = true;
        } else {
            addModules = false;
        }

        this.rightVBox.getChildren().clear();
        this.rightVBox.getChildren().add(new Label("Valitun opintokokonaisuuden kurssit:"));
        try {
            Collection<DegreeModule> coursesAndModules = module.getSubModules().values();
            for (DegreeModule subModule : coursesAndModules) {
                subModulesByName.put(subModule.getName(), subModule);
                // chekc if module is a course
                if (subModule.getType().equals("CourseUnit")) {
                    
                    String id = subModule.getId();
                    CheckBox course = new CheckBox(subModule.getName());
                    if (student.getCourses().contains(id)) {
                        course.setSelected(true);
                    }
                    course.setOnAction( e -> {
                        if (course.isSelected()) {
                            student.setCourses(id);
                        } else {
                            student.deleteCourse(id);
                        }
                    });
                    Tooltip tip = new Tooltip();
                    tip.setShowDelay(Duration.seconds(0.5));
                    tip.setText(subModule.getDescription());
                    course.setTooltip(tip);
                    this.rightVBox.getChildren().add(course);
                } else if (addModules) {
                    // Extend tree if module is not a course
                    TreeItem<String> innerModule = new TreeItem<String> (subModule.getName());
                    parentItem.getChildren().add(innerModule);
                }
            }
        } catch (IOException e) {
            ;
        } catch (NullPointerException e) {
            ;
        }
    }
}
