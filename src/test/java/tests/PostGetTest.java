package tests;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;

import static io.restassured.RestAssured.*;

public class PostGetTest extends BaseTest {

    @Test
    public void getAllPosts() {
        when().get(POSTS)
                .then().statusCode(200);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "3"})
    public void getPostWithId(String id) {
        Response response =
                given().pathParam("id", id)
                        .when().get(POSTS + "/{id}")
                        .then().statusCode(200)
                        .body(JsonSchemaValidator.matchesJsonSchema(new File("src/main/resources/postSchema.json")))
                        .extract().response();

        JsonPath jsonPath = response.jsonPath();
        Assertions.assertThat(jsonPath.get("id").toString()).isEqualTo(id);
    }

    @Test
    public void getPostOfUser() {
        Response response =
        given().queryParam("userId", "3")
                .when().get(POSTS)
                .then().statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        Assertions.assertThat(jsonPath.getList("userId")).allMatch(e -> e.equals(3));
    }
}
