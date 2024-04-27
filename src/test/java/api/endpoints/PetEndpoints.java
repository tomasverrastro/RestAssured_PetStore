package api.endpoints;

import api.payload.Pet;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ResourceBundle;

import static io.restassured.RestAssured.given;

public class PetEndpoints {

    static ResourceBundle getURL(){
        return ResourceBundle.getBundle("routes");
    }

    public static Response addPet(Pet payload){

        String addPet_url = getURL().getString("addPet_url");

        Response response =

            given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
            .when()
                .post(addPet_url);

        return response;
    }

    public static Response getPetById(String petId){

        String getPetById_url = getURL().getString("getPetById_url");

        Response response =

            given()
                .pathParam("petId", petId)
            .when()
                .get(getPetById_url);

        return response;
    }

    public static Response updatePet(Pet payload){

        String updatePet_url = getURL().getString("updatePet_url");

        Response response =

            given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
            .when()
                .put(updatePet_url);

        return response;
    }

    public static Response deletePet(String petId){

        String deletePet_url = getURL().getString("deletePet_url");

        Response response =
            given()
                .pathParam("petId", petId)
                .when()
                .delete(deletePet_url);

        return response;
    }
}
