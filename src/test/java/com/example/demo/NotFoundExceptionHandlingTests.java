package com.example.demo;

import com.example.demo.http.errorHandling.ResponseError;
import org.jeasy.random.EasyRandom;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotFoundExceptionHandlingTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final EasyRandom random = new EasyRandom();

    @Test
    void get_WhenIdDoesNotExist_ShouldReturnNotFound() {
        var url = UrlHelper.getGetUrl(port, random.nextLong());
        var responseEntity = restTemplate.getForEntity(url, ResponseError.class);
        var errors = responseEntity.getBody().getErrors();

        assertThat(responseEntity.getStatusCode().value(), is(404));
    }

    @Test
    void update_WhenIdDoesNotExist_ShouldReturnNotFound() throws JSONException {
        var url = UrlHelper.getUpdateUrl(port);
        var obj = new JSONObject()
                .put("id", random.nextLong())
                .put("width", 1);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var request = new HttpEntity<>(obj.toString(), headers);

        var responseEntity = restTemplate.postForEntity(url, request, ResponseError.class);

        assertThat(responseEntity.getStatusCode().value(), is(404));
    }

    @Test
    void getAll_WhenRepositoryIsEmpty_ShouldReturnNotFound() {
        var url = UrlHelper.getGetAllUrl(port);
        var responseEntity = restTemplate.getForEntity(url, ResponseError.class);
        var errors = responseEntity.getBody().getErrors();

        assertThat(responseEntity.getStatusCode().value(), is(404));
    }

}
