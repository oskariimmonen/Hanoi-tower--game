package fi.tuni.prog3.sisu;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestDegreeModule {
    @Test
    public void testCreateDegreeModule() {
        
        new DegreeModule(
            "Tietojenkäsittelyopin maisteriohjelma", 
            "otm-511e7450-0da0-4c45-8395-9c84830d06a0",
            "uta-tohjelma-1707",
            120,
            "DegreeProgramme",
            "https://sis-tuni.funidata.fi/kori/api/modules/otm-511e7450-0da0-4c45-8395-9c84830d06a0",
            "");
        
        assert true;
    }

    @Test
    public void testSubModuleFunction() {
        DegreeModule testModule = new DegreeModule(
            "Tietojenkäsittelyopin maisteriohjelma", 
            "otm-511e7450-0da0-4c45-8395-9c84830d06a0",
            "uta-tohjelma-1707",
            120,
            "DegreeProgramme",
            "https://sis-tuni.funidata.fi/kori/api/modules/otm-511e7450-0da0-4c45-8395-9c84830d06a0",
            "");
    
        TreeMap<String, DegreeModule> testSubModules = null;
        try {
            testSubModules = testModule.getSubModules();
        } catch (IOException e) {
            ;
        }
        
        if (testSubModules != null) {
            boolean modulesLength = testSubModules.keySet().size() == 4;
            assertFalse(!modulesLength);
            assert true;
        } else {
            assert false;
        }
    }
}
