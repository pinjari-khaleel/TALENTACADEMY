package com.talentacademy.core.utils;

import com.talentacademy.core.constants.ApplicationConstants;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static com.talentacademy.core.constants.ALMConstants.PAGE_LIMIT;
import static com.talentacademy.core.constants.ALMConstants.SORT;
import static com.talentacademy.core.constants.ApplicationConstants.CLIENT_SECRET;
import static com.talentacademy.core.constants.ApplicationConstants.CLIENT_ID;
import static com.talentacademy.core.constants.ApplicationConstants.REFRESH_TOKEN;
import static com.talentacademy.core.utils.RestApiHttpClientUtil.getPostCallResponse;
import static junit.framework.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RestApiHttpClientUtilTest {

    /**
     * Test For the Post Call With Valid Params
     */
    @Test
    void doUrlEncodedPostCallWithValidUrlAndParamsShouldReturnResponse() throws IOException {
        String url = "https://externalapi.com/api/endpoint";
        List<NameValuePair> params = Arrays.asList(
            new BasicNameValuePair(CLIENT_ID, "value1"),
            new BasicNameValuePair(CLIENT_SECRET, "value2"),
            new BasicNameValuePair(REFRESH_TOKEN, "value3")
        );
        String mockResponse = "{'message': 'Success'}";
        CloseableHttpClient httpClient = mock(CloseableHttpClient.class);
        CloseableHttpResponse mockHttpResponse = mock(CloseableHttpResponse.class);
        HttpEntity entity = mock(HttpEntity.class);

        try (MockedStatic<HttpClients> mockedHttpClient = Mockito.mockStatic(HttpClients.class)) {
            mockedHttpClient.when(HttpClients::createDefault)
                    .thenReturn(httpClient);

            when(httpClient.execute(any(HttpPost.class))).thenReturn(mockHttpResponse);

            when(mockHttpResponse.getEntity()).thenReturn(entity);
            when(entity.getContent()).thenReturn(new ByteArrayInputStream(mockResponse.getBytes(StandardCharsets.UTF_8)));

            String response = RestApiHttpClientUtil.doUrlEncodedPostCall(url, params);

            assertEquals(mockResponse, response);
        }
    }

    /**
     * Test For the Get Call With Valid Params
     */
    @Test
    void doUrlEncodedGetCallWithValidUrlAndParamsShouldReturnResponse() throws IOException, URISyntaxException {
        String url = "https://externalapi.com/api/endpoint";
        String accessToken = "test-token";
        List<NameValuePair> params = Arrays.asList(
                new BasicNameValuePair(PAGE_LIMIT, "value1"),
                new BasicNameValuePair(SORT, "value2")
        );
        String mockResponse = "{'message': 'Success'}";
        CloseableHttpClient httpClient = mock(CloseableHttpClient.class);
        CloseableHttpResponse mockHttpResponse = mock(CloseableHttpResponse.class);
        HttpEntity entity = mock(HttpEntity.class);

        try (MockedStatic<HttpClients> mockedHttpClient = Mockito.mockStatic(HttpClients.class)) {
            mockedHttpClient.when(HttpClients::createDefault)
                    .thenReturn(httpClient);

            when(httpClient.execute(any(HttpGet.class))).thenReturn(mockHttpResponse);

            when(mockHttpResponse.getEntity()).thenReturn(entity);
            when(entity.getContent()).thenReturn(new ByteArrayInputStream(mockResponse.getBytes(StandardCharsets.UTF_8)));

            String response = RestApiHttpClientUtil.doUrlEncodedGetCall(url, params, accessToken);

            assertEquals(mockResponse, response);
        }
    }


    /**
     * Test For the Get Call With InValid URL
     */
    @Test
    void doUrlEncodedGetCallWithInValidUrlAndParamsShouldReturnResponse() throws IOException, URISyntaxException {
        String url = "https://externalapi.com/api/endpoint";
        String accessToken = "test-token";
        List<NameValuePair> params = Arrays.asList(
                new BasicNameValuePair(PAGE_LIMIT, "value1"),
                new BasicNameValuePair(SORT, "value2")
        );
        CloseableHttpClient httpClient = mock(CloseableHttpClient.class);

        try (MockedStatic<HttpClients> mockedHttpClient = Mockito.mockStatic(HttpClients.class)) {
            mockedHttpClient.when(HttpClients::createDefault)
                    .thenReturn(httpClient);

            when(httpClient.execute(any(HttpGet.class))).thenThrow(new IOException());

            String response = RestApiHttpClientUtil.doUrlEncodedGetCall(url, params, accessToken);

            assertNull(response);
        }
    }

    /**
     * Test For the Post Call With InValid URL
     */
    @Test
    void doInvalidUrlEncodedPostCallTest() throws IOException, URISyntaxException {
        String url = "https://externalapi.com/api/endpoint";
        List<NameValuePair> params = Arrays.asList(
                new BasicNameValuePair(PAGE_LIMIT, "value1"),
                new BasicNameValuePair(SORT, "value2")
        );
        CloseableHttpClient httpClient = mock(CloseableHttpClient.class);

        try (MockedStatic<HttpClients> mockedHttpClient = Mockito.mockStatic(HttpClients.class)) {
            mockedHttpClient.when(HttpClients::createDefault)
                    .thenReturn(httpClient);

            when(httpClient.execute(any(HttpPost.class))).thenThrow(new IOException());

            String response = RestApiHttpClientUtil.doUrlEncodedPostCall(url, params);
            assertEquals(null,response);
        }
    }

    /**
     * Test method for Post call with valid URL
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    void doGetPostCallResponse() throws IOException, URISyntaxException {
        String url = "https://www.google.com/recaptcha/api/siteverify";
        List<NameValuePair> params = Arrays.asList(
                new BasicNameValuePair(ApplicationConstants.SECRET_PARAMETER, "value1"),
                new BasicNameValuePair(ApplicationConstants.RESPONSE_PARAMETER, "value2")
        );
        String mockResponse = "{'Success': 'true'}";
        CloseableHttpClient httpClient = mock(CloseableHttpClient.class);
        CloseableHttpResponse mockHttpResponse = mock(CloseableHttpResponse.class);
        HttpEntity entity = mock(HttpEntity.class);

        try (MockedStatic<HttpClients> mockedHttpClient = Mockito.mockStatic(HttpClients.class)) {
            mockedHttpClient.when(HttpClients::createDefault)
                    .thenReturn(httpClient);

            when(httpClient.execute(any(HttpPost.class))).thenReturn(mockHttpResponse);
            when(mockHttpResponse.getEntity()).thenReturn(entity);
            when(entity.getContent()).thenReturn(new ByteArrayInputStream(mockResponse.getBytes(StandardCharsets.UTF_8)));
            CloseableHttpResponse response = getPostCallResponse(url, params);
            assertNotNull(mockResponse, response);
        }
    }
    /**
     * Test method for Post call with invalid details
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    void doGetPostCallErrorResponse() throws IOException, URISyntaxException {
        String url = "https://www.google.com/recaptcha/api/siteverify";
        List<NameValuePair> params = Arrays.asList(
                new BasicNameValuePair(ApplicationConstants.SECRET_PARAMETER, "value1"),
                new BasicNameValuePair(ApplicationConstants.RESPONSE_PARAMETER, "value2")
        );
        String mockResponse = "{'Success': 'true'}";
        CloseableHttpClient httpClient = mock(CloseableHttpClient.class);
        try (MockedStatic<HttpClients> mockedHttpClient = Mockito.mockStatic(HttpClients.class)) {
            mockedHttpClient.when(HttpClients::createDefault)
                    .thenReturn(httpClient);
            when(httpClient.execute(any(HttpPost.class))).thenThrow(new IOException());
            CloseableHttpResponse response = getPostCallResponse(url, params);
            assertNull(mockResponse, response);
        }
    }
}