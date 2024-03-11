package com.talentacademy.core.services.impl;

import com.talentacademy.core.beans.AlmConfigurationBean;
import com.talentacademy.core.utils.RestApiHttpClientUtil;
import org.apache.http.message.BasicNameValuePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;
import org.mockito.MockedStatic;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import static com.talentacademy.core.constants.ApplicationConstants.CLIENT_ID;
import static com.talentacademy.core.constants.ApplicationConstants.CLIENT_SECRET;
import static com.talentacademy.core.constants.ApplicationConstants.REFRESH_TOKEN;

class CPTokenServiceImplTest {

    @InjectMocks
    private CPTokenServiceImpl cpTokenService;

    @Mock
    private ALMAuthenticationServiceImpl almAuthenticationService;

    private AlmConfigurationBean almConfigurationBean;

    /**
     * setUp to load before test method
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        almConfigurationBean = new AlmConfigurationBean();
        almConfigurationBean.setClientId("clientId");
        almConfigurationBean.setClientSecret("clientSecret");
        almConfigurationBean.setRefreshToken("validCode");
        almConfigurationBean.setAccessTokenUrl("https://alm.example.com/token");
    }

    /**
     * Test to Fetch the Access Token
     */
    @ParameterizedTest
    @ValueSource(strings = {"{\"access_token\":\"accessToken\",\"expires_in\":3600}", "{\"access_token\":\"accessToken\"}", "{\"access_token\":\"accessToken\", \"expires_in\":40}"})
    void generateAccessToken(String arg) {
        Mockito.when(almAuthenticationService.getAlmConfigurationBean()).thenReturn(almConfigurationBean);

        try (MockedStatic<RestApiHttpClientUtil> mockedRestApiHttpClientUtil = Mockito.mockStatic(RestApiHttpClientUtil.class)) {
            mockedRestApiHttpClientUtil.when(() -> RestApiHttpClientUtil.doUrlEncodedPostCall(almConfigurationBean.getAccessTokenUrl(),
                            Arrays.asList(new BasicNameValuePair(CLIENT_ID, almConfigurationBean.getClientId()),
                                    new BasicNameValuePair(CLIENT_SECRET, almConfigurationBean.getClientSecret()),
                                    new BasicNameValuePair(REFRESH_TOKEN, almConfigurationBean.getRefreshToken())
                            )))
                    .thenReturn(arg);

            cpTokenService.activate();
            cpTokenService.getAccessTokenFromCode(cpTokenService.getAccessToken());
            assertEquals("accessToken", cpTokenService.getAccessToken());
        }
    }

    /**
     * Test to Generate the Access Token If it's Not Generated
     */
    @Test
    void generateAccessTokenIfAccessTokenIsNull() {
        String jsonAlmTokenResponse = "{\"expires_in\":3600}";

        Mockito.when(almAuthenticationService.getAlmConfigurationBean()).thenReturn(almConfigurationBean);

        try (MockedStatic<RestApiHttpClientUtil> mockedRestApiHttpClientUtil = Mockito.mockStatic(RestApiHttpClientUtil.class)) {
            mockedRestApiHttpClientUtil.when(() -> RestApiHttpClientUtil.doUrlEncodedPostCall(almConfigurationBean.getAccessTokenUrl(),
                            Arrays.asList(new BasicNameValuePair(CLIENT_ID, almConfigurationBean.getClientId()),
                                    new BasicNameValuePair(CLIENT_SECRET, almConfigurationBean.getClientSecret()),
                                    new BasicNameValuePair(REFRESH_TOKEN, almConfigurationBean.getRefreshToken())
                            )))
                    .thenReturn(jsonAlmTokenResponse);

            cpTokenService.getAccessTokenFromCode(cpTokenService.getAccessToken());
            assertNull(cpTokenService.getAccessToken());
        }
    }

    /**
     * Test to Get the Access Token
     */
    @Test
    void getAccessToken() {
        String jsonAlmTokenResponse = "{\"access_token\":\"accessToken\",\"expires_in\":3600}";

        Mockito.when(almAuthenticationService.getAlmConfigurationBean()).thenReturn(almConfigurationBean);

        try (MockedStatic<RestApiHttpClientUtil> mockedRestApiHttpClientUtil = Mockito.mockStatic(RestApiHttpClientUtil.class)) {
            mockedRestApiHttpClientUtil.when(() -> RestApiHttpClientUtil.doUrlEncodedPostCall(almConfigurationBean.getAccessTokenUrl(),
                            Arrays.asList(new BasicNameValuePair(CLIENT_ID, almConfigurationBean.getClientId()),
                                    new BasicNameValuePair(CLIENT_SECRET, almConfigurationBean.getClientSecret()),
                                    new BasicNameValuePair(REFRESH_TOKEN, almConfigurationBean.getRefreshToken())
                            )))
                    .thenReturn(jsonAlmTokenResponse);

            cpTokenService.getAccessTokenFromCode(cpTokenService.getAccessToken());
            assertEquals("accessToken", cpTokenService.getAccessToken());
        }
    }

    /**
     * Test to Return Null If Response Is Null
     */
    @Test
    void shouldReturnNullIfResponseIfNull() {

        Mockito.when(almAuthenticationService.getAlmConfigurationBean()).thenReturn(almConfigurationBean);

        try (MockedStatic<RestApiHttpClientUtil> mockedRestApiHttpClientUtil = Mockito.mockStatic(RestApiHttpClientUtil.class)) {
            mockedRestApiHttpClientUtil.when(() -> RestApiHttpClientUtil.doUrlEncodedPostCall(almConfigurationBean.getAccessTokenUrl(),
                            Arrays.asList(new BasicNameValuePair(CLIENT_ID, almConfigurationBean.getClientId()),
                                    new BasicNameValuePair(CLIENT_SECRET, almConfigurationBean.getClientSecret()),
                                    new BasicNameValuePair(REFRESH_TOKEN, almConfigurationBean.getRefreshToken())
                            )))
                    .thenReturn(null);
            cpTokenService.getAccessTokenFromCode(cpTokenService.getAccessToken());
            assertNull(cpTokenService.getAccessToken());
        }
    }
}
