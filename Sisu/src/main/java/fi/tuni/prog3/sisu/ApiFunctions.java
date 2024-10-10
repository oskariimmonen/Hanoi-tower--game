package fi.tuni.prog3.sisu;

import com.google.gson.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Handles all Sisu API calls by full URL.
 */
public class ApiFunctions {

    public ApiFunctions() {
        ;
    }

    /**
     * Get module data from api
     * @param stringURL Module's url
     * @return JsonObject of the module
     * @throws IOException
     * @throws MalformedURLException
     */
    public JsonObject getJsonObjectFromApi(String stringURL) throws IOException, 
            MalformedURLException {
        URL url = new URL(stringURL);
        try {
            URLConnection request = url.openConnection();
            JsonElement element = JsonParser.parseReader(
                    new InputStreamReader((InputStream) request.getContent()));
            try {
                JsonObject elementObject = element.getAsJsonObject();
                return elementObject;
            }  catch (IllegalStateException e) {
                JsonArray elementObject = element.getAsJsonArray();
                return elementObject.get(0).getAsJsonObject();
            }
        } catch (java.io.IOException e) {
            try {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Virhe");
                alert.setHeaderText("Ei internetyhteyttä!");
                alert.setContentText("Ohjelman suoritusta ei voida jatkaa "
                        + "ilman toimivaa internetyhteyttä. Yhdistä laite "
                        + "internettiin ja kokeile uudelleen.");
                alert.showAndWait();
                System.exit(0);
            } catch (NoClassDefFoundError error) {
                System.out.println("Ei internetyhteyttä!");
                System.exit(0);
            }
            return new JsonObject();
        }
    }
}
