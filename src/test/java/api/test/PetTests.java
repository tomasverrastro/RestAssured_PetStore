package api.test;

import api.endpoints.PetEndpoints;
import api.payload.Category;
import api.payload.Pet;
import api.payload.Tag;
import api.util.JSONSchemaValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PetTests {

    static Pet petPayload;
    static Category category;
    static Tag tag;

    @BeforeClass
    public void setUp() throws JsonProcessingException {

        //Creating the payload
        petPayload = new Pet();
        category = new Category();
        tag = new Tag();

        category.setId(13);
        category.setName("Dragon");

        tag.setId(5);
        tag.setName("Hello");
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);

        List<String> photoUrls = new ArrayList<>();
        photoUrls.add("www.happybirthday.com");;

        petPayload.setId(50);
        petPayload.setName("Monsio");
        petPayload.setStatus("available");
        petPayload.setCategory(category);
        petPayload.setTags(tags);
        petPayload.setPhotoUrls(photoUrls);

        //Code for printing the payload in the console
        ObjectMapper objMapper = new ObjectMapper();
        String jsonData = objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(petPayload);
        System.out.println(jsonData);
    }

    @Test(priority = 1, description = "PET_001 - Add a new pet to the store")
    public void testAddPet() throws IOException, ProcessingException {

        System.out.println("PET_001 - Add a new pet to the store");

        Response response = PetEndpoints.addPet(petPayload);
        response.then().log().all();

        //Response code validation
        Assert.assertEquals(response.getStatusCode(), 200);

        //JSON Schema validation
        String jsonResponse = response.getBody().asString();
        String schemaFilePath = "src/test/resources/petJSONSchema.json";
        JSONSchemaValidator.validateJSONSchema(jsonResponse, schemaFilePath);

        //Body validations
        String id = response.jsonPath().getString("id");
        String name = response.jsonPath().getString("name");
        String status = response.jsonPath().getString("status");
        String categoryID = response.jsonPath().getString("category.id");
        String categoryName = response.jsonPath().getString("category.name");
        String photoURL = response.jsonPath().getString("photoUrls[0]");
        String tagID = response.jsonPath().getString("tags[0].id");
        String tagName = response.jsonPath().getString("tags[0].name");

        Assert.assertEquals(Integer.parseInt(id), petPayload.getId());
        Assert.assertEquals(name, petPayload.getName());
        Assert.assertEquals(status, petPayload.getStatus());
        Assert.assertEquals(Integer.parseInt(categoryID), category.getId());
        Assert.assertEquals(categoryName, category.getName());
        Assert.assertEquals(photoURL, petPayload.getPhotoUrls().get(0));
        Assert.assertEquals(Integer.parseInt(tagID), tag.getId());
        Assert.assertEquals(tagName, tag.getName());

        //Headers validations
        String contentType = response.getHeader("Content-Type");
        Assert.assertEquals(contentType, "application/json");

        System.out.println("PET_001 - Add a new pet to the store OK ");
    }

    @Test(priority = 2, description = "PET_002 - Find a pet by ID")
    public void testGetPetByID() throws IOException, ProcessingException {

        System.out.println("PET_002 - Find a pet by ID");

        Response response = PetEndpoints.getPetById(String.valueOf(petPayload.getId()));
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);

        //JSON Schema validation
        String jsonResponse = response.getBody().asString();
        String schemaFilePath = "src/test/resources/petJSONSchema.json";
        JSONSchemaValidator.validateJSONSchema(jsonResponse, schemaFilePath);

        //Body validations
        String id = response.jsonPath().getString("id");
        String name = response.jsonPath().getString("name");
        String status = response.jsonPath().getString("status");
        String categoryID = response.jsonPath().getString("category.id");
        String categoryName = response.jsonPath().getString("category.name");
        String photoURL = response.jsonPath().getString("photoUrls[0]");
        String tagID = response.jsonPath().getString("tags[0].id");
        String tagName = response.jsonPath().getString("tags[0].name");

        Assert.assertEquals(Integer.parseInt(id), petPayload.getId());
        Assert.assertEquals(name, petPayload.getName());
        Assert.assertEquals(status, petPayload.getStatus());
        Assert.assertEquals(Integer.parseInt(categoryID), category.getId());
        Assert.assertEquals(categoryName, category.getName());
        Assert.assertEquals(photoURL, petPayload.getPhotoUrls().get(0));
        Assert.assertEquals(Integer.parseInt(tagID), tag.getId());
        Assert.assertEquals(tagName, tag.getName());

        //Headers validations
        String contentType = response.getHeader("Content-Type");
        Assert.assertEquals(contentType, "application/json");

        System.out.println("PET_002 - Find a pet by ID OK");
    }

    @Test(priority = 3, description = "PET_003 - Update an existing pet")
    public static void updatePet() throws IOException, ProcessingException {

        System.out.println("PET_003 - Update an existing pet");

        //Update pet data
        category.setId(50);
        category.setName("Alien");

        petPayload.setName("Sauron");
        petPayload.setCategory(category);

        Response response = PetEndpoints.updatePet(petPayload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);

        //JSON Schema validation
        String jsonResponse = response.getBody().asString();
        String schemaFilePath = "src/test/resources/petJSONSchema.json";
        JSONSchemaValidator.validateJSONSchema(jsonResponse, schemaFilePath);

        //Body validations
        String id = response.jsonPath().getString("id");
        String name = response.jsonPath().getString("name");
        String status = response.jsonPath().getString("status");
        String categoryID = response.jsonPath().getString("category.id");
        String categoryName = response.jsonPath().getString("category.name");
        String photoURL = response.jsonPath().getString("photoUrls[0]");
        String tagID = response.jsonPath().getString("tags[0].id");
        String tagName = response.jsonPath().getString("tags[0].name");

        Assert.assertEquals(Integer.parseInt(id), petPayload.getId());
        Assert.assertEquals(name, petPayload.getName());
        Assert.assertEquals(status, petPayload.getStatus());
        Assert.assertEquals(Integer.parseInt(categoryID), category.getId());
        Assert.assertEquals(categoryName, category.getName());
        Assert.assertEquals(photoURL, petPayload.getPhotoUrls().get(0));
        Assert.assertEquals(Integer.parseInt(tagID), tag.getId());
        Assert.assertEquals(tagName, tag.getName());

        //Headers validations
        String contentType = response.getHeader("Content-Type");
        Assert.assertEquals(contentType, "application/json");

        System.out.println("PET_003 - Update an existing pet OK");
    }

    @Test(priority = 4, description = "PET_004 - Delete a pet")
    public static void deletePet() {

        System.out.println("PET_004 - Delete a pet");

        Response response = PetEndpoints.deletePet(String.valueOf(petPayload.getId()));
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);

        //Headers validations
        String contentType = response.getHeader("Content-Type");
        Assert.assertEquals(contentType, "application/json");

        System.out.println("PET_004 - Delete a pet OK");

    }
}
