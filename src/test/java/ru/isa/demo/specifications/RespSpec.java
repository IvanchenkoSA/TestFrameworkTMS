package ru.isa.demo.specifications;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.entity.mime.HttpMultipartMode;

import java.nio.charset.StandardCharsets;

import static io.restassured.config.EncoderConfig.encoderConfig;
import static io.restassured.config.MultiPartConfig.multiPartConfig;
import static org.apache.http.HttpStatus.SC_OK;

public class RespSpec {

    public static ResponseSpecification SIMPLE_OK() {
        return new ResponseSpecBuilder()
                .expectStatusCode(SC_OK)
                .build();
    }

    public static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri("http://localhost:8080")
                .setUrlEncodingEnabled(false)
                .setContentType(ContentType.JSON)
                .setConfig(RestAssuredConfig.config()
                        .httpClient(HttpClientConfig.httpClientConfig().httpMultipartMode(HttpMultipartMode.BROWSER_COMPATIBLE))
                        .multiPartConfig(multiPartConfig().defaultCharset(StandardCharsets.UTF_8))
                        .encoderConfig(encoderConfig().defaultCharsetForContentType(StandardCharsets.UTF_8, "application/json")))
                .addFilter(new AllureRestAssured())
                .build();
    }

}
