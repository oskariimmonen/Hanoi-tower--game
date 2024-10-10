package fi.tuni.prog3.sisu;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class that represents student information.
 */
public class Student implements Comparable<Student>{
    private String name;
    private String studentNumber;
    private String selectedDegreeProgramme;
    private int startYear;
    private int endYear;
    private ArrayList<String> courses = new ArrayList<>();
    
    /**
     * Full builder for new registeration.
     * @param name
     * @param studentNumber
     * @param selectedDegreeProgramme
     * @param startYear
     * @param endYear
     */
    public Student(String name, String studentNumber, 
            String selectedDegreeProgramme, int startYear, int endYear){
        this.name = name;
        this.studentNumber = studentNumber;
        this.selectedDegreeProgramme = selectedDegreeProgramme;
        this.startYear = startYear;
        this.endYear = endYear;
    }

    /**
     * Empty builder for GSON typeadapter
     */
    public Student(){
    }

    /**
     * Compare students by name.
     */
    @Override
    public int compareTo(Student e) {
        return this.getName().compareTo(e.getName());
    }
    
    /**
     * Get name of student
     * @return name
     */
    public String getName(){
        return name;
    }
    
    /**
     * Set name of student
     * @param name 
     */
    public void setName(String name ) {
        this.name = name;
    }

    /**
     * Get the studentnumber of student
     * @return studentNumber
     */
    public String getStudentNumber(){
        return studentNumber;
    }
    
    /**
     * Set studentnumber of student
     * @param studentNumber 
     */
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
    
    /**
     * Get the start year of student
     * @return startYear
     */
    public int getStartYear(){
        return startYear;
    }
    
    /**
     * Set the start year of student
     * @param startYear 
     */
    public void setStartYear(int startYear){
        this.startYear = startYear;
    }
    
    /**
     * Get the end year of student
     * @return endYear
     */
    public int getEndYear(){
        return endYear;
    }
    
    /**
     * Set the end year of student
     * @param endYear 
     */
    public void setEndYear(int endYear){
        this.endYear = endYear;
    }
    
    /**
     * Set the degree program of student
     * @param id 
     */
    public void setSelectedDegreeProgramme(String id ) {
        selectedDegreeProgramme = id;
    }

    /**
     * Get the degree program of student
     * @return selectedDegreeProgramme
     */
    public String getSelectedDegreeProgramme() {
        return selectedDegreeProgramme;
    }
    
    /**
     * Set course for student.
     * @param course
     */
    public void setCourses(String course){
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    /**
     * Delete courses from student.
     * @param course
     */
    public void deleteCourse(String course) {
        courses.remove(course);
    }
    
    /**
     * Returns all courses done by the student.
     * @return courses
     */
    public ArrayList<String> getCourses() {
        return courses;
    }
}

/**
 * StudentAdapter for GSON reader and writer.
 */
class StudentAdapter extends TypeAdapter<Student> { 
   @Override 
   public Student read(JsonReader reader) throws IOException {
      Student student = new Student(); 
      reader.beginObject(); 
      String fieldname = null;
         
      while (reader.hasNext()) { 
         JsonToken token = reader.peek();            
         
         if (token.equals(JsonToken.NAME)) {     
            fieldname = reader.nextName(); 
         } 
         
         if ("studentNumber".equals(fieldname)) {        
            student.setStudentNumber(reader.nextString()); 
         } 
         
         if ("name".equals(fieldname)) {        
            student.setName(reader.nextString()); 
         } 
         
         if("startYear".equals(fieldname)) { 
            student.setStartYear(reader.nextInt()); 
         }
         
         if("endYear".equals(fieldname)) { 
            student.setEndYear(reader.nextInt());
         }
         
         if("selectedDegreeProgramme".equals(fieldname)) { 
            student.setSelectedDegreeProgramme(reader.nextString()); 
         }
         
         if("courses".equals(fieldname)) { 
            reader.beginArray();
            while (reader.peek() != JsonToken.END_ARRAY) {
                student.setCourses(reader.nextString());
            }
            reader.endArray(); 
         }
         
      } 
      reader.endObject(); 
      return student; 
   }  

   
   @Override 
   public void write(JsonWriter writer, Student student) throws IOException { 
      writer.beginObject(); 
      writer.name("studentNumber"); 
      writer.value(student.getStudentNumber()); 
      writer.name("name"); 
      writer.value(student.getName());
      writer.name("startYear"); 
      writer.value(student.getStartYear());
      writer.name("endYear"); 
      writer.value(student.getEndYear());
      writer.name("selectedDegreeProgramme"); 
      writer.value(student.getSelectedDegreeProgramme());
      writer.name("courses");
      writer.beginArray();
      for (String s : student.getCourses()) {
        writer.value(s);
      }
      writer.endArray();
      writer.endObject(); 
   } 
}

