package fi.tuni.prog3.sisu;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.TreeMap;
import com.google.gson.*;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * JavaFX Sisu Login
 */
public class Sisu extends Application {
    private ApiFunctions api;
    
    /**
     * Start function for the app. 
     * Starts by fetching student and degree programme data.
     * Open login window.
     */
    @Override
    public void start(Stage stage) throws IOException, MalformedURLException {
        this.api = new ApiFunctions();
        var fileRead = new FileReadWriteFunctions();
        TreeMap<String, DegreeModule> degrees = getPrograms();
        TreeMap<String, Student> students = fileRead.getStudents();
        new SisuLogInWindow(stage, degrees, students);
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Finds degree programmes from api
     * @return TreeMap<String, DegreeModule> where string is module id
     * @throws IOException
     * @throws MalformedURLException
     */
    private TreeMap<String, DegreeModule> getPrograms() throws IOException, MalformedURLException {
        String url = "https://sis-tuni.funidata.fi/kori/api/module-search?curriculumPeriodId=uta-lvv-2021&universityId=tuni-university-root-id&moduleType=DegreeProgramme&limit=1000";
        JsonObject degreeModulesObject = api.getJsonObjectFromApi(url);
        JsonArray degreeModules = degreeModulesObject.get("searchResults").getAsJsonArray();

        TreeMap<String, DegreeModule> degrees = new TreeMap<>();

        for (var degree : degreeModules) {
            
            var thisDegree = degree.getAsJsonObject();
            var groupId = thisDegree.get("groupId").getAsString();
            var id = thisDegree.get("id").getAsString();
            var name = thisDegree.get("name").getAsString();
            var creditEntries = thisDegree.get("credits").getAsJsonObject().get("min").getAsInt();
            var type = "DegreeProgramme";
            var newDegree = new DegreeModule(name, id, groupId, creditEntries, type, "", "");
            degrees.put(id, newDegree);
        }

        return degrees;
    }

}