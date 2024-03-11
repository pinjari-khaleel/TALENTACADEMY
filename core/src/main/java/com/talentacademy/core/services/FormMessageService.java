package com.talentacademy.core.services;

import org.apache.http.NameValuePair;
import org.apache.sling.api.resource.Resource;

import java.util.List;

/**
 * Service for external API integration
 */
public interface FormMessageService {

    /**
     * Sends the json object to the backend service
     * @param nvps
     * @return
     */
    boolean sendMessageAction(List<NameValuePair> nvps, Resource resource);
}
