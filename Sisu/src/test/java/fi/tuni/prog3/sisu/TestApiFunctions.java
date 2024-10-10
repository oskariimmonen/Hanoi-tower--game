package fi.tuni.prog3.sisu;

import org.junit.jupiter.api.Test;
import com.google.gson.JsonObject;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;


public class TestApiFunctions {
    @Test 
    public void testApiFetching() {
        ApiFunctions api = new ApiFunctions();
        String id = "otm-511e7450-0da0-4c45-8395-9c84830d06a0";
        String testUrl = "https://sis-tuni.funidata.fi/kori/api/modules/otm-511e7450-0da0-4c45-8395-9c84830d06a0";
        JsonObject degreeModulesObject = null;
        try {
            degreeModulesObject = api.getJsonObjectFromApi(testUrl);    
        } catch (IOException e) {
            assert false;
        }

        if (degreeModulesObject != null) {
            String newId = degreeModulesObject.get("id").getAsString();
            boolean isSame = newId.equals(id);
            assertFalse(!isSame);
        } else {
            assert false;
        }
    }
}
