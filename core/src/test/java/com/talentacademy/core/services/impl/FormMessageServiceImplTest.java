package com.talentacademy.core.services.impl;

import com.day.cq.wcm.webservicesupport.Configuration;
import com.day.cq.wcm.webservicesupport.ConfigurationManager;
import com.talentacademy.core.beans.FormConnectionConfiguration;
import com.talentacademy.core.configs.CloudServiceConfiguration;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class})
class FormMessageServiceImplTest {

    private final AemContext aemContext = new AemContext();

    FormMessageServiceImpl service;

    Resource resource;

    @Mock
    FormConnectionConfiguration formConnectionConfiguration;

    @Mock
    ConfigurationManager configurationManager;

    @Mock
    private Configuration configuration;

    @Mock
    StatusLine statusLine;

    private void registerConfigurationManager(final AemContext aemContext) {
        aemContext.registerAdapter(ResourceResolver.class,
                ConfigurationManager.class, configurationManager);
    }

    @BeforeEach
    void setUp() {
        service = new FormMessageServiceImpl();
        aemContext.load().json("/com/talentacademy/core/models/impl/MediaMonks.json", "/etc/cloudservices");
        resource = aemContext.resourceResolver().getResource( "/etc/cloudservices/form-collector/jcr:content");
        aemContext.registerService(FormMessageServiceImpl.class, service);

        registerConfigurationManager(aemContext);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.services.impl.FormMessageServiceImpl#sendMessageAction(List, Resource)}.
     */
    @Test
    void shouldReturnFalseIfResponseIsNotSuccess() {
        registerConfiguration();
        when(configuration.getContentResource()).thenReturn(resource);
        List<NameValuePair> params = new ArrayList<>();
        assertFalse(service.sendMessageAction(params, resource));
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.services.impl.FormMessageServiceImpl#sendMessageAction(List, Resource)}.
     */
    @Test
    void shouldReturnTrueIfResponseIsSuccess() throws IOException {
        registerConfiguration();
        Resource configResource = mock(Resource.class);

        when(configuration.getContentResource()).thenReturn(configResource);
        when(configResource.adaptTo(FormConnectionConfiguration.class)).thenReturn(formConnectionConfiguration);
        when(formConnectionConfiguration.getApiKey()).thenReturn("");
        when(formConnectionConfiguration.getUrl()).thenReturn("");

        CloseableHttpClient httpClient = mock(CloseableHttpClient.class);
        CloseableHttpResponse mockHttpResponse = mock(CloseableHttpResponse.class);
        try (MockedStatic<HttpClients> mockedHttpClient = Mockito.mockStatic(HttpClients.class)) {
            mockedHttpClient.when(HttpClients::createDefault)
                    .thenReturn(httpClient);

            when(httpClient.execute(any(HttpPost.class))).thenReturn(mockHttpResponse);
            when(mockHttpResponse.getStatusLine()).thenReturn(statusLine);
            when(statusLine.getStatusCode()).thenReturn(200);
            List<NameValuePair> params = new ArrayList<>();
            assertTrue(service.sendMessageAction(params, resource));
        }
    }

    @Test
    void shouldReturnFalseIfNoCloudConfiguration() {
        List<NameValuePair> params = new ArrayList<>();
        when(configurationManager.getConfiguration(eq(CloudServiceConfiguration.FORM_COLLECTOR.getCode()), any())).thenThrow(new IllegalArgumentException());

        boolean result = service.sendMessageAction(params,resource);

        assertFalse(result);

    }

    private void registerConfiguration() {
        when(configurationManager.getConfiguration(eq(CloudServiceConfiguration.FORM_COLLECTOR.getCode()), any()))
                .thenReturn(configuration);
    }
}
