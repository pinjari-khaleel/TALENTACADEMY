package com.talentacademy.core.services.impl;

import com.talentacademy.core.beans.AlmConfigurationBean;
import com.talentacademy.core.configs.ALMAuthenticationConfiguration;
import com.talentacademy.core.services.ALMAuthenticationService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

@Component(service = ALMAuthenticationService.class, immediate = true)
@Designate(ocd = ALMAuthenticationConfiguration.class)
public class ALMAuthenticationServiceImpl implements ALMAuthenticationService {

    private AlmConfigurationBean almConfigurationBean;

    /**
     * Activate.
     *
     * @param almAuthenticationConfiguration
     */
    @Activate
    public void activate(ALMAuthenticationConfiguration almAuthenticationConfiguration) {

        almConfigurationBean = new AlmConfigurationBean();
        almConfigurationBean.setAccessTokenUrl(almAuthenticationConfiguration.accessTokenUrl());
        almConfigurationBean.setClientId(almAuthenticationConfiguration.clientId());
        almConfigurationBean.setClientSecret(almAuthenticationConfiguration.clientSecret());
        almConfigurationBean.setRefreshToken(almAuthenticationConfiguration.refreshToken());
        almConfigurationBean.setAlmBaseUrl(almAuthenticationConfiguration.almBaseUrl());
        almConfigurationBean.setPrefix(almAuthenticationConfiguration.prefix());
        almConfigurationBean.setVersion(almAuthenticationConfiguration.version());
        almConfigurationBean.setLearningObjectsApi(almAuthenticationConfiguration.learningObjectsApi());
    }

    /**
     *
     * @return ALM Configuration Bean
     */
    @Override
    public AlmConfigurationBean getAlmConfigurationBean() {
        return almConfigurationBean;
    }
}