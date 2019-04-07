package com.pccwglobal.api;

import com.pccwglobal.Application;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@ContextConfiguration
@SpringBootTest(classes = Application.class, properties = "server.port:0", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerTest {
    @Value("${local.server.port:-1}")
    private int port;

    @PostConstruct
    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost:" + port;
        RestAssured.port = port;
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void test() {
        Map map = new HashMap();
        map.put("firstName", "Animesh");
        map.put("lastName", "Mondal");
        map.put("email", "onems1net@gmail.com");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(map)
                .when()
                .post("/user/add")
                .then().statusCode(HttpStatus.OK.value())
                .and()
                .body("firstName", equalTo("Animesh"))
                .body("lastName", equalTo("Mondal"))
                .body("email", equalTo("onems1net@gmail.com"));

        RestAssured.get("/user/find/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .body("firstName", equalTo("Animesh"))
                .body("lastName", equalTo("Mondal"))
                .body("email", equalTo("onems1net@gmail.com"));

        map.put("email", "animesh.mondal@tcs.com");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(map)
                .when()
                .put("/user/update/1")
                .then().statusCode(HttpStatus.OK.value())
                .and()
                .body("firstName", equalTo("Animesh"))
                .body("lastName", equalTo("Mondal"))
                .body("email", equalTo("animesh.mondal@tcs.com"));

        RestAssured.delete("/user/delete/1")
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
