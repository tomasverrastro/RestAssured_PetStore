package api.test;

import api.endpoints.PetEndpoints;
import api.payload.Category;
import api.payload.Pet;
import api.payload.Tag;
import api.util.JSONSchemaValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
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

    @Test(priority = 5, description = "PET_005 - Find a pet by status")
    public static void getPetByStatus() throws IOException, ProcessingException {

        System.out.println("PET_005 - Find a pet by status");

        Response response = PetEndpoints.getPetByStatus("pending");
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);

        //Headers validations
        String contentType = response.getHeader("Content-Type");
        Assert.assertEquals(contentType, "application/json");

        //JSON Schema validation
        String jsonResponse = response.getBody().asString();
        String schemaFilePath = "src/test/resources/petListJSONSchema.json";
        JSONSchemaValidator.validateJSONSchema(jsonResponse, schemaFilePath);

        System.out.println("PET_005 - Find a pet by status OK");
    }

    @Test(priority = 6, description = "PET_006 - Find a pet by tag")
    public static void getPetByTags() throws IOException, ProcessingException {

        System.out.println("PET_006 - Find a pet by tag");

        Response response = PetEndpoints.getPetByTag("tag1");
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);

        //Headers validations
        String contentType = response.getHeader("Content-Type");
        Assert.assertEquals(contentType, "application/json");

        //JSON Schema validation
        String jsonResponse = response.getBody().asString();
        String schemaFilePath = "src/test/resources/petListJSONSchema.json";
        JSONSchemaValidator.validateJSONSchema(jsonResponse, schemaFilePath);

        System.out.println("PET_006 - Find a pet by tag OK");
    }

    @Test(priority = 7, description = "PET_007 - Update a pet in the store with form data")
    public static void updatePetWithFormData(){

        System.out.println("PET_007 - Update a pet in the store with form data");

        Response response = PetEndpoints.updatePetWithFormData(12, "Ramoncito", "available");
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);

        //Headers validations
        String contentType = response.getHeader("Content-Type");
        Assert.assertEquals(contentType, "application/json");

        System.out.println("PET_007 - Update a pet in the store with form data OK");
    }
}
