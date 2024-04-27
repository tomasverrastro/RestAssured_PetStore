package api.test;

import api.endpoints.PetEndpoints;
import api.payload.Category;
import api.payload.Pet;
import api.payload.Tag;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class PetTests {

    Pet petPayload;
    Category category;
    Tag tag;

    @BeforeClass
    public void setUp() throws JsonProcessingException {

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

    @Test(priority = 1)
    public void testAddPet(){

        Response response = PetEndpoints.addPet(petPayload);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
