package com.example.demo;

import com.example.demo.http.errorHandling.ResponseError;
import com.example.demo.http.responses.DeleteWidgetResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RequestValidationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static HttpHeaders headers;

    @BeforeAll
    public static void runBeforeAllTestMethods() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void create_WhenEmptyRequest_ShouldRespondWithNotNullRestriction() {
        var obj = new JSONObject();
        var request = new HttpEntity<>(obj.toString(), headers);

        var responseEntity = restTemplate.postForEntity(getCreateUrl(), request, ResponseError.class);
        var errors = responseEntity.getBody().getErrors();

        assertThat(responseEntity.getStatusCode().value(), is(400));
        assertThat(errors, hasItems(
                hasProperty("field", is("x")),
                hasProperty("field", is("y")),
                hasProperty("field", is("width")),
                hasProperty("field", is("height"))
        ));
        assertThat(errors, everyItem(hasProperty("message", is("must not be null"))));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void create_WhenWidgetSizeIsNotPositive_ShouldRespondWithAcceptedRangeRestriction(int size) throws JSONException {
        var obj = new JSONObject()
                .put("x", 1)
                .put("y", 1)
                .put("width", size)
                .put("height", size);
        var request = new HttpEntity<>(obj.toString(), headers);

        var responseEntity = restTemplate.postForEntity(getCreateUrl(), request, ResponseError.class);
        var errors = responseEntity.getBody().getErrors();

        assertThat(responseEntity.getStatusCode().value(), is(400));
        assertThat(errors, hasItems(
                hasProperty("message", is("Width should be positive")),
                hasProperty("message", is("Height should be positive"))
        ));
    }

    @Test
    void update_WhenEmptyRequest_ShouldRespondWithNotNullRestriction() {
        var obj = new JSONObject();
        var request = new HttpEntity<>(obj.toString(), headers);

        var responseEntity = restTemplate.postForEntity(getUpdateUrl(), request, ResponseError.class);
        var errors = responseEntity.getBody().getErrors();

        assertThat(responseEntity.getStatusCode().value(), is(400));
        assertThat(errors, hasItem(hasProperty("field", is("id"))));
        assertThat(errors, hasItem(hasProperty("message", is("must not be null"))));
        assertThat(errors, hasSize(1));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void update_WhenWidgetSizeIsNotPositive_ShouldRespondWithAcceptedRangeRestriction(int size) throws JSONException {
        var obj = new JSONObject()
                .put("id", 1)
                .put("width", size)
                .put("height", size);
        var request = new HttpEntity<>(obj.toString(), headers);

        var responseEntity = restTemplate.postForEntity(getUpdateUrl(), request, ResponseError.class);
        var errors = responseEntity.getBody().getErrors();

        assertThat(responseEntity.getStatusCode().value(), is(400));
        assertThat(errors, hasItems(
                hasProperty("message", is("Width should be positive")),
                hasProperty("message", is("Height should be positive"))
        ));
    }

    @Test
    void get_WhenWidgetIdNotSpecified_ShouldRespondWithNotNullRestriction() {
        var responseEntity = restTemplate.getForEntity(getGetUrl(), ResponseError.class);
        var errors = responseEntity.getBody().getErrors();

        assertThat(responseEntity.getStatusCode().value(), is(400));
        assertThat(errors, hasItem(hasProperty("message", containsString("parameter 'id' is not present"))));
        assertThat(errors, hasSize(1));
    }

    @Test
    void delete_WhenWidgetIdNotSpecified_ShouldRespondWithNotNullRestriction() {
        var responseEntity = restTemplate.exchange(getDeleteUrl(), HttpMethod.DELETE, null, ResponseError.class);
        var errors = responseEntity.getBody().getErrors();

        assertThat(responseEntity.getStatusCode().value(), is(400));
        assertThat(errors, hasItem(hasProperty("message", containsString("parameter 'id' is not present"))));
        assertThat(errors, hasSize(1));
    }

    private String getCreateUrl() {
        return "http://localhost:" + port + "/widget/create";
    }

    private String getUpdateUrl() {
        return "http://localhost:" + port + "/widget/update";
    }

    private String getGetUrl() {
        return "http://localhost:" + port + "/widget/get";
    }

    private String getDeleteUrl() {
        return "http://localhost:" + port + "/widget/delete";
    }
}
