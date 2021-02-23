package com.example.demo;

import com.example.demo.http.responses.*;
import org.jeasy.random.EasyRandom;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CrudOperationsTests {

    private static HttpHeaders headers;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final EasyRandom random = new EasyRandom();

    @BeforeAll
    public static void runBeforeAllTestMethods() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void get_WhenWidgetAlreadyCreated_ShouldReturnWidget() throws JSONException {
        JSONObject obj = getJsonForCreateResponse();
        var request = new HttpEntity<>(obj.toString(), headers);

        var createUrl = UrlHelper.getCreateUrl(port);
        var responseEntity = restTemplate.postForEntity(createUrl, request, CreateWidgetResponse.class);
        var createResponseBody= responseEntity.getBody();

        var getUrl = UrlHelper.getGetUrl(port, createResponseBody.getId());
        var getResponse = restTemplate.getForEntity(getUrl, GetWidgetResponse.class);
        var getResponseBody = getResponse.getBody();

        assertThat(getResponse.getStatusCode().value()).isEqualTo(200);
        assertThat(createResponseBody.getId()).isEqualTo(getResponseBody.getId());
        assertThat(createResponseBody.getX()).isEqualTo(getResponseBody.getX());
        assertThat(createResponseBody.getY()).isEqualTo(getResponseBody.getY());
        assertThat(createResponseBody.getWidth()).isEqualTo(getResponseBody.getWidth());
        assertThat(createResponseBody.getHeight()).isEqualTo(getResponseBody.getHeight());
        assertThat(createResponseBody.getZIndex()).isEqualTo(getResponseBody.getZIndex());
        assertThat(createResponseBody.getUpdatedDateTimeUtc()).isEqualTo(getResponseBody.getUpdatedDateTimeUtc());
    }

    @Test
    void getAll_WhenWidgetsExists_ShouldReturnOrdered() throws JSONException {
        var createUrl = UrlHelper.getCreateUrl(port);

        for (var i = 0; i < 50; i++) {
            JSONObject obj = getJsonForCreateResponse();
            if (i % 7 != 0)
                obj = obj.put("zIndex", random.nextInt(200));
            var request = new HttpEntity<>(obj.toString(), headers);
            restTemplate.postForEntity(createUrl, request, CreateWidgetResponse.class);
        }
        var getAllUrl = UrlHelper.getGetAllUrl(port);
        var response = restTemplate.getForEntity(getAllUrl, GetAllWidgetsResponse.class);

        var widgets = response.getBody();

        assertThat(widgets.getWidgets()).isSortedAccordingTo(Comparator.comparingInt(WidgetResponseModel::getZIndex));;
    }

    @Test
    void get_WhenWidgetUpdated_ShouldReturnActualInfo() throws JSONException {
        JSONObject createBody = getJsonForCreateResponse();

        var createRequest = new HttpEntity<>(createBody.toString(), headers);
        var createResponse = restTemplate.postForEntity(
                UrlHelper.getCreateUrl(port),
                createRequest,
                CreateWidgetResponse.class);

        var newZIndex = random.nextInt(200);

        var widgetId = createResponse.getBody().getId();
        var updateBody = new JSONObject()
                .put("id", widgetId)
                .put("zindex", newZIndex);
        var updateRequest = new HttpEntity<>(updateBody.toString(), headers);
        restTemplate.postForEntity(
                UrlHelper.getUpdateUrl(port),
                updateRequest,
                UpdateWidgetResponse.class);

        var getUrl = UrlHelper.getGetUrl(port, widgetId);
        var getResponse = restTemplate.getForEntity(getUrl, GetWidgetResponse.class);
        var getResponseBody = getResponse.getBody();

        assertThat(getResponseBody.getZIndex()).isEqualTo(newZIndex);
    }

    @Test
    void getAll_WhenOneWidgetRemoved_ShouldReturnWidgetsWithoutRemoved() throws JSONException {
        var createUrl = UrlHelper.getCreateUrl(port);
        long lastId = 0;

        for (var i = 0; i < 2; i++) {
            var obj = getJsonForCreateResponse();

            var request = new HttpEntity<>(obj.toString(), headers);
            var response = restTemplate.postForEntity(createUrl, request, CreateWidgetResponse.class);
            lastId = response.getBody().getId();
        }

        var url = UrlHelper.getDeleteUrl(port, lastId);
        restTemplate.delete(url);

        var getAllUrl = UrlHelper.getGetAllUrl(port);
        var response = restTemplate.getForEntity(getAllUrl, GetAllWidgetsResponse.class);

        var widgets = response.getBody();
        long finalLastId = lastId;
        assertThat(widgets.getWidgets()).allMatch(widget -> widget.getId() != finalLastId);
    }

    private JSONObject getJsonForCreateResponse() throws JSONException {
        return new JSONObject()
                .put("x", random.nextInt())
                .put("y", random.nextInt())
                .put("width", random.nextInt(100))
                .put("height", random.nextInt(100));
    }
}
