package com.talentacademy.core.utils;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public final class RestApiHttpClientUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestApiHttpClientUtil.class);

    private RestApiHttpClientUtil(){}

    /**
     * Processes POST requests.
     */
    public static String doUrlEncodedPostCall(String url, List<NameValuePair> params)
    {
        HttpPost post = new HttpPost(url);
        post.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post))
        {
            return EntityUtils.toString(response.getEntity());
        } catch (ParseException | IOException e)
        {
            LOGGER.error("Exception in url encoded post http call", e);
        }
        return null;
    }

    /**
     *
     * @param url
     * @param params
     * @return CloseableHttpResponse
     */
    public static CloseableHttpResponse getPostCallResponse(String url, List<NameValuePair> params)
    {
        HttpPost post = new HttpPost(url);
        post.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post))
        {
            return response;
        } catch (ParseException | IOException e)
        {
            LOGGER.error("Exception in post http call {}", e.getMessage());
        }
        return null;
    }


    /**
     * Processes Get requests.
     */
    public static String doUrlEncodedGetCall(String url, List<NameValuePair> params, String accessToken) throws URISyntaxException {
        LOGGER.debug("Inside the HTTP get call");
        HttpGet httpGet = new HttpGet(url);
        URI uri = new URIBuilder(httpGet.getURI()).addParameters(params).build();
        httpGet.setURI(uri);
        httpGet.setHeader( "Accept", "application/vnd.api+json" );
        httpGet.setHeader( "Authorization", accessToken );
        try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(httpGet))
        {
            LOGGER.debug("HTTP Get Response {}", response);
            return EntityUtils.toString(response.getEntity());
        } catch (ParseException | IOException e)
        {
            LOGGER.error("Exception in url encoded get http call", e);
        }
        return null;
    }
}