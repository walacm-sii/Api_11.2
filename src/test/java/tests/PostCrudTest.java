package tests;

import io.restassured.http.ContentType;
import models.Post;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class PostCrudTest extends BaseTest {

    @Test
    public void shouldAddPost() {
        Post post = new Post(1, "new post", "est rerum tempore nihil reprehenderit dolor beatae");

        given()
                .body(post).contentType(ContentType.JSON)
                .when()
                .post(POSTS)
                .then()
                .statusCode(201);
    }

    @Test
    public void shouldReplacePost() {
        Post post = new Post(1, "new post", "est rerum tempore nihil reprehenderit dolor beatae");

        given()
                .pathParam("id", "1")
                .body(post).contentType(ContentType.JSON)
                .when()
                .put(POSTS + "/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldModifyPost() {
        String patchBody = """
                {
                "title": "replaced title"
                }
                """;

        given()
                .pathParam("id", "1")
                .body(patchBody).contentType(ContentType.JSON)
                .when()
                .patch(POSTS + "/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldDeletePost() {
        given()
                .pathParam("id", "1")
                .when()
                .delete(POSTS + "/{id}")
                .then()
                .statusCode(200);
    }


}
