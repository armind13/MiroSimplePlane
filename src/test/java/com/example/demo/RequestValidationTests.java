package com.example.demo;

import com.example.demo.http.errorHandling.ResponseError;
import org.jeasy.random.EasyRandom;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RequestValidationTests {

    private static HttpHeaders headers;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    public static void runBeforeAllTestMethods() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void create_WhenEmptyRequest_ShouldRespondWithNotNullRestriction() {
        var obj = new JSONObject();
        var request = new HttpEntity<>(obj.toString(), headers);

        var url = UrlHelper.getCreateUrl(port);
        var responseEntity = restTemplate.postForEntity(url, request, ResponseError.class);
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

        var url = UrlHelper.getCreateUrl(port);
        var responseEntity = restTemplate.postForEntity(url, request, ResponseError.class);
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

        var url = UrlHelper.getUpdateUrl(port);
        var responseEntity = restTemplate.postForEntity(url, request, ResponseError.class);
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

        var url = UrlHelper.getUpdateUrl(port);
        var responseEntity = restTemplate.postForEntity(url, request, ResponseError.class);
        var errors = responseEntity.getBody().getErrors();

        assertThat(responseEntity.getStatusCode().value(), is(400));
        assertThat(errors, hasItems(
                hasProperty("message", is("Width should be positive")),
                hasProperty("message", is("Height should be positive"))
        ));
    }

    @Test
    void get_WhenWidgetIdNotSpecified_ShouldRespondWithNotNullRestriction() {
        var url = UrlHelper.getGetUrl(port);
        var responseEntity = restTemplate.getForEntity(url, ResponseError.class);
        var errors = responseEntity.getBody().getErrors();

        assertThat(responseEntity.getStatusCode().value(), is(400));
        assertThat(errors, hasItem(hasProperty("message", containsString("parameter 'id' is not present"))));
        assertThat(errors, hasSize(1));
    }

    @Test
    void delete_WhenWidgetIdNotSpecified_ShouldRespondWithNotNullRestriction() {
        var url = UrlHelper.getDeleteUrl(port);
        var responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, null, ResponseError.class);
        var errors = responseEntity.getBody().getErrors();

        assertThat(responseEntity.getStatusCode().value(), is(400));
        assertThat(errors, hasItem(hasProperty("message", containsString("parameter 'id' is not present"))));
        assertThat(errors, hasSize(1));
    }


}
