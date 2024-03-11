package com.talentacademy.core.services.impl;

import com.talentacademy.core.beans.FormConnectionConfiguration;
import com.talentacademy.core.configs.CloudServiceConfiguration;
import com.talentacademy.core.services.FormMessageService;
import com.talentacademy.core.utils.RestApiHttpClientUtil;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.talentacademy.core.constants.ApplicationConstants.API_KEY;
import static com.talentacademy.core.constants.ApplicationConstants.NON_COMMERCIAL;
import static com.talentacademy.core.constants.ApplicationConstants.LEAD_USAGE_TYPE;

@Component(service = FormMessageService.class)
public class FormMessageServiceImpl implements FormMessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FormMessageServiceImpl.class);

    @Override
    public boolean sendMessageAction(List<NameValuePair> nvps, Resource resource) {

        try{
            CloudConfigServiceImpl cloudConfigServiceImpl = new CloudConfigServiceImpl(resource);
            FormConnectionConfiguration formConnectionConfiguration =
                    cloudConfigServiceImpl.getConfiguration(CloudServiceConfiguration.FORM_COLLECTOR);

            final String url = formConnectionConfiguration.getUrl();

            final String apiKey = formConnectionConfiguration.getApiKey();
            nvps.add(new BasicNameValuePair(API_KEY, apiKey));
            nvps.add(new BasicNameValuePair(LEAD_USAGE_TYPE, NON_COMMERCIAL));
            CloseableHttpResponse response = RestApiHttpClientUtil.getPostCallResponse(url, nvps);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                return Boolean.TRUE;
            }
        }catch(IllegalArgumentException exception){
            LOGGER.error("Exception in form service action: {} ", exception.getMessage());
        }

        return Boolean.FALSE;
    }
}
