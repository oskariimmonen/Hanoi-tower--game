package fi.tuni.prog3.sisu;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestStudent
{
    @ParameterizedTest
    @MethodSource("studentArguments")

    public void testCreateNewStudent(String name, String studentNo, String degree, int start, int end){

        Student testObject = new Student(name, studentNo, degree, start, end);

        assertEquals(name, testObject.getName());
        assertEquals(studentNo,testObject.getStudentNumber());
        assertEquals(degree,testObject.getSelectedDegreeProgramme());
        assertEquals(start, testObject.getStartYear());
        assertEquals(end, testObject.getEndYear());
    }

    @ParameterizedTest
    @MethodSource("studentArguments")

    public void testCreateStudentFromJson(String name, String studentNo, String degree, int start, int end){

        Student testObject = new Student();

        testObject.setStudentNumber(studentNo);
        testObject.setName(name);
        testObject.setSelectedDegreeProgramme(degree);
        testObject.setStartYear(start);
        testObject.setEndYear(end);

        assertEquals(name, testObject.getName());
        assertEquals(studentNo,testObject.getStudentNumber());
        assertEquals(degree,testObject.getSelectedDegreeProgramme());
        assertEquals(start, testObject.getStartYear());
        assertEquals(end, testObject.getEndYear());
    }

    @Test
    
    public void testSetGetCourse(){
        ArrayList<String> expectedCourses = new ArrayList<>();
        ArrayList<String> result = new ArrayList<>();

        Student testObject = new Student("Testi Testattava", "H123456", "otm-511e7450-0da0-4c45-8395-9c84830d06a0", 2020, 2025);

        ArrayList<String> testCourses = new ArrayList<>();
        Collections.addAll(testCourses, "otm-ba43a0dc-9a2b-4ffb-8aa7-51d12c7e4c4e",
        "otm-e3e68a5d-fda8-47b8-a7e5-ae75fdd279fd","otm-cfb80d37-38d5-4c6f-b0e2-e3f65109f854",
        "otm-9249accb-76bc-4158-83c8-532d7b729fc6","otm-6f42dabd-a65f-4ad4-9d41-5dd16342d07e",
        "otm-3c3132fa-2eaa-4160-acb3-0c2f4b475ceb","otm-ba43a0dc-9a2b-4ffb-8aa7-51d12c7e4c4e",
        "otm-ba43a0dc-9a2b-4ffb-8aa7-51d12c7e4c4e","otm-ba43a0dc-9a2b-4ffb-8aa7-51d12c7e4c4e");

        for (String course : testCourses) {
            if (!expectedCourses.contains(course)) {
                expectedCourses.add(course);
            }
            testObject.setCourses(course);
        }
        result = testObject.getCourses();
        assertEquals(expectedCourses, result);
    }

    @Test
    
    public void testDeleteCourse(){
        ArrayList<String> expectedCourses = new ArrayList<>();
        ArrayList<String> result = new ArrayList<>();

        Student testObject = new Student("Testi Testattava", "H123456", "otm-511e7450-0da0-4c45-8395-9c84830d06a0", 2020, 2025);

        ArrayList<String> testCourses = new ArrayList<>();
        Collections.addAll(testCourses, "otm-ba43a0dc-9a2b-4ffb-8aa7-51d12c7e4c4e",
        "otm-e3e68a5d-fda8-47b8-a7e5-ae75fdd279fd","otm-cfb80d37-38d5-4c6f-b0e2-e3f65109f854",
        "otm-9249accb-76bc-4158-83c8-532d7b729fc6","otm-6f42dabd-a65f-4ad4-9d41-5dd16342d07e",
        "otm-3c3132fa-2eaa-4160-acb3-0c2f4b475ceb","otm-ba43a0dc-9a2b-4ffb-8aa7-51d12c7e4c4e",
        "otm-ba43a0dc-9a2b-4ffb-8aa7-51d12c7e4c4e","otm-ba43a0dc-9a2b-4ffb-8aa7-51d12c7e4c4e");

        for (String course : testCourses) {
            if (!expectedCourses.contains(course)) {
                expectedCourses.add(course);
            }
            testObject.setCourses(course);
        }
        testObject.deleteCourse("otm-ba43a0dc-9a2b-4ffb-8aa7-51d12c7e4c4e");
        expectedCourses.remove("otm-ba43a0dc-9a2b-4ffb-8aa7-51d12c7e4c4e");
        result = testObject.getCourses();
        assertEquals(expectedCourses, result);
    }


    static Stream<Arguments> studentArguments(){
        return Stream.of(
                arguments("Testi Testattava", "H123456", "otm-511e7450-0da0-4c45-8395-9c84830d06a0", 2020, 2025),
                arguments("CAPS LOCK NIMI", "12h1234KK", "otm-7469b38b-dd7c-4d09-a025-4726d7e05b9c", 2018, 2022),
                arguments("kaikkiyhteenpienellä", "mErkkij0n0", "otm-8dd0ff78-dc4e-433f-bd02-091f87fc2715", 2017, 2028)
        );
    }

}
