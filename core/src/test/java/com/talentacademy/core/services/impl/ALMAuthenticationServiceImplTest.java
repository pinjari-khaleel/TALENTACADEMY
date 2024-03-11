package com.talentacademy.core.services.impl;

import com.talentacademy.core.beans.AlmConfigurationBean;
import com.talentacademy.core.configs.ALMAuthenticationConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ALMAuthenticationServiceImplTest {

    @Mock
    private ALMAuthenticationConfiguration almAuthenticationConfiguration;

    private ALMAuthenticationServiceImpl almAuthenticationService;

    /**
     * setUp to load before test method
     */
    @BeforeEach
    public void setUp() {
        almAuthenticationService = new ALMAuthenticationServiceImpl();
        almAuthenticationService.activate(almAuthenticationConfiguration);
    }

    /**
     * Test to Fetch the ALM Configuration Bean
     */
    @Test
    void activateShouldSetAlmConfigurationBean() {
        Mockito.when(almAuthenticationConfiguration.almBaseUrl()).thenReturn("https://alm.site.com");
        Mockito.when(almAuthenticationConfiguration.clientId()).thenReturn("clientId");
        Mockito.when(almAuthenticationConfiguration.clientSecret()).thenReturn("clientSecret");
        Mockito.when(almAuthenticationConfiguration.accessTokenUrl()).thenReturn("https://alm.site.com/accesstoken");
        Mockito.when(almAuthenticationConfiguration.refreshToken()).thenReturn("token");

        almAuthenticationService.activate(almAuthenticationConfiguration);

        AlmConfigurationBean almConfigurationBean = almAuthenticationService.getAlmConfigurationBean();
        assertNotNull(almConfigurationBean);
        assertEquals("https://alm.site.com", almConfigurationBean.getAlmBaseUrl());
        assertEquals("clientId", almConfigurationBean.getClientId());
        assertEquals("clientSecret", almConfigurationBean.getClientSecret());
        assertEquals("https://alm.site.com/accesstoken", almConfigurationBean.getAccessTokenUrl());
        assertEquals("token", almConfigurationBean.getRefreshToken());

    }
}