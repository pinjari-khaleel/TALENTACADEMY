package com.talentacademy.core.services;

import com.talentacademy.core.beans.AlmConfigurationBean;

/**
 * Service that reads the osgi ALM configuration
 */
public interface ALMAuthenticationService {

    /**
     * returns the ALM configuration in the Configuration Bean object
     * @return
     */
    AlmConfigurationBean getAlmConfigurationBean();
}