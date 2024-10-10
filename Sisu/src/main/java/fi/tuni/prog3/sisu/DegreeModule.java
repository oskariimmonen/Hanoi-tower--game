package fi.tuni.prog3.sisu;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.TreeMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class DegreeModule {
    private String name;
    private String id;
    private String groupId;
    private int minCredits;
    private String type;
    private String url;
    private String moduleEndpointUrl = "https://sis-tuni.funidata.fi/kori/api/modules/";
    private String description;
    /**
     * A constructor for initializing the member variables.
     * @param name name of the Module or Course.
     * @param id id of the Module or Course.
     * @param groupId group id of the Module or Course.
     * @param minCredits minimum credits of the Module or Course.
     */
    public DegreeModule(String name, String id, String groupId, 
            int minCredits, String type, String url, String description) {
        this.name = name;
        this.id = id;
        this.groupId = groupId;
        this.minCredits = minCredits;
        this.type = type;
        this.url = url;
        this.description = description;
    }
    
    /**
     * Returns the name of the Module or Course.
     * @return name of the Module or Course.
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Returns the id of the Module or Course.
     * @return id of the Module or Course.
     */
    public String getId() {
        return this.id;
    }
    
    /**
     * Returns the group id of the Module or Course.
     * @return group id of the Module or Course.
     */
    public String getGroupId() {
        return this.groupId;
    }
    
    /**
     * Returns the minimum credits of the Module or Course.
     * @return minimum credits of the Module or Course.
     */
    public int getMinCredits() {
        return this.minCredits;
    }

    /**
     * Return the type of the module
     * @return String type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Return url of the module
     * @return String url
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Return description of the module
     * @return String Description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets direct submodules for this module
     * @return TreeMap<String, DegreeModule> containing module id and module data.
     * @throws IOException
     * @throws MalformedURLException
     */
    public TreeMap<String, DegreeModule> getSubModules() throws IOException, MalformedURLException {
        ApiFunctions api = new ApiFunctions();
        String url = moduleEndpointUrl + this.id;
        // Fetch parent module data
        JsonObject thisDegree = api.getJsonObjectFromApi(url);
        // Submodules of selected parent module.
        var subModules = findModuleRules(thisDegree);
        TreeMap<String, DegreeModule> modulesToReturn = new TreeMap<>();
        // Loop submodule ids
        for (var submodule : subModules) {

            // Generate url for the module
            String url_;
            try {
                url_ = generateUrl(submodule);
            } catch (NullPointerException e) {
                continue;
            }

            // Fetch submodule data with id
            JsonObject subModuleData = api.getJsonObjectFromApi(url_);
            String groupId_ = subModuleData.getAsJsonObject().get("groupId").getAsString();
            String id__ = subModuleData.getAsJsonObject().get("id").getAsString();

            // Get module name
            String name_;
            try {
                name_ = subModuleData.getAsJsonObject().get("name").getAsJsonObject().get("fi").getAsString();
            } catch (NullPointerException e) {
                name_ = subModuleData.getAsJsonObject().get("name").getAsJsonObject().get("en").getAsString();
            }

            // Get right type for module
            String type;
            try {
                type = subModuleData.getAsJsonObject().get("type").getAsString();
            } catch (NullPointerException e ) {
                // Courses do not have types
                type = "CourseUnit";
            }

            // Get module description if type == CourseUnit
            String description = "No description";
            try {
                if (type.equals("CourseUnit")) {
                    JsonObject contentObject = subModuleData.getAsJsonObject().get("content").getAsJsonObject();
                    if (contentObject.has("fi")) {
                        description = contentObject.get("fi").getAsString().replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", "\n");
                    } else {
                        description = contentObject.get("en").getAsString().replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", "\n");
                    }
                }
    
            } catch (IllegalStateException e ) {
                ;
            }

            // Submodule might not have target credits since it can be a collection of modules
            int creditEntries_ = 0;
            try {
                creditEntries_ = subModuleData.getAsJsonObject().get("targetCredits").getAsJsonObject().get("min").getAsInt();
            } catch (NullPointerException e) {
                ;
            }

            try {
                creditEntries_ = subModuleData.getAsJsonObject().get("credits").getAsJsonObject().get("min").getAsInt();
            } catch (NullPointerException e) {
                ;
            }

            // Create new module and add to map
            DegreeModule subModule_ = new DegreeModule(name_, id__, groupId_, creditEntries_, type, url_, description);
            modulesToReturn.put(id__, subModule_);
        }
        
        return modulesToReturn;
    }

    /**
     * Generates url for submodule
     * @param submodule submodule sturcture from api
     * @return url as a string
     */
    private String generateUrl(JsonElement submodule) {
        String id_;
        String url_;
        if (submodule.getAsJsonObject().get("type").getAsString().equals("CourseUnitRule")) {
            id_ = submodule.getAsJsonObject().get("courseUnitGroupId").getAsString();
            url_ = "https://sis-tuni.funidata.fi/kori/api/course-units/by-group-id?groupId=" + id_ + "&universityId=tuni-university-root-id";
        } else {
            try {
                id_ = submodule.getAsJsonObject().get("moduleGroupId").getAsString();
            } catch (NullPointerException e) {
                id_ = submodule.getAsJsonObject().get("groupId").getAsString();
            }

            if (id_.contains("otm")) {
                url_ = moduleEndpointUrl + id_;
            } else {
                url_ = "https://sis-tuni.funidata.fi/kori/api/modules/by-group-id?groupId=" + id_ + "&universityId=tuni-university-root-id";
            }
        }

        return url_;
    }

    /**
     * Find right rule from module jsonObject
     * @param degree degrees api strucrure
     * @return Wanted rules as an array
     */
    private JsonArray findModuleRules(JsonObject degree) {
        var subModule = degree.getAsJsonObject();

        if (type.equals("DegreeProgramme")) {
            while (subModule.has("rule")) {
                subModule = subModule.get("rule").getAsJsonObject();
            }
            if (subModule.get("rules").getAsJsonArray().get(0).getAsJsonObject().get("type").getAsString().equals("ModuleRule")) {
                return subModule.get("rules").getAsJsonArray();
            }
            return subModule.get("rules").getAsJsonArray().get(0).getAsJsonObject().get("rules").getAsJsonArray();
        } 
        
        else if (type.equals("GroupingModule")) {
            JsonObject test = subModule.get("rule").getAsJsonObject();
            if (test.has("rule")) {
                return test.get("rule").getAsJsonObject().get("rules").getAsJsonArray();
            }
            return test.get("rules").getAsJsonArray();
        } 
        
        else if (type.equals("StudyModule")) {

            // Different study module structures
            try {
                return subModule.get("rule").getAsJsonObject()
                    .get("rule").getAsJsonObject()
                    .get("rules").getAsJsonArray().get(0).getAsJsonObject()
                    .get("rules").getAsJsonArray();

            } catch (NullPointerException e) {
                ;
            }

            try {
                return subModule.get("rule").getAsJsonObject().get("rules").getAsJsonArray();
            } catch (NullPointerException e) {
                ;
            }

            try {
                return subModule.get("rule").getAsJsonObject().get("rule").getAsJsonObject().get("rules").getAsJsonArray();
            } catch (NullPointerException e) {
                ;
            }

            JsonArray test = new JsonArray();
            test.add(degree);
            return test;
        }

        return new JsonArray();
    }
    
}
