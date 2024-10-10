package fi.tuni.prog3.sisu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.TreeMap;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Reads and writes data from the JSON file (database).
 */
public class FileReadWriteFunctions {
    GsonBuilder gsonBuild = new GsonBuilder().registerTypeAdapter(
            Student.class, new StudentAdapter());
    Gson gson = gsonBuild.create();
    
    public FileReadWriteFunctions() {}

    /**
     * Takes the student datastructure and updates JSON database.
     * @param studentMap
     */
    public void writeFile(TreeMap<String, Student> studentMap) {
        try {
            Writer writer = Files.newBufferedWriter(
                    Paths.get("Sisu_user_data.json"));
            gson.toJson(studentMap.values(),writer);
            writer.close();
        } catch (IOException e) { ; }
    }
    
    /**
     * Reads data of all students saved into JSON database.
     * @return students
     * @throws IOException
     * @throws JsonSyntaxException
     * @throws JsonIOException
     */
    public TreeMap<String, Student> getStudents() throws IOException, 
            JsonSyntaxException, JsonIOException  {
        Alert alert = new Alert(AlertType.ERROR);
        TreeMap<String, Student> students = new TreeMap<>();
        Reader reader;
        try {
            reader = Files.newBufferedReader(Paths.get("Sisu_user_data.json"));
            Student[] studentList = gson.fromJson(reader, Student[].class);
            // Loop student and insert to map
            for ( Student student : studentList) {
                students.put(student.getStudentNumber(), student);
            }
            return students;
        } catch (IOException e) {
            return students;
        } catch (JsonSyntaxException | JsonIOException e) {
            alert.setTitle("Virhe");
            alert.setHeaderText("Tiedoston luvussa tapahtui virhe!");
            alert.setContentText("Tarkistathan tietokannan virheiden "
                    + "varalta, jatkamalla tietoja saattaa kadota.");
            alert.showAndWait();
            return students;
        }
    }
}
