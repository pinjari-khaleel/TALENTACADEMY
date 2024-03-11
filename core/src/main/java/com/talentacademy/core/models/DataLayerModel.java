package com.talentacademy.core.models;

import com.talentacademy.core.configs.GenericSiteConfiguration;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.caconfig.ConfigurationBuilder;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;

@Model(adaptables = { SlingHttpServletRequest.class,
        Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DataLayerModel {

    private String gtmId;

    @Inject
    Resource resource;

    /**
     * get GTM ID
     *
     * @return GTMID
     */
    public String getGtmId() {
        ConfigurationBuilder builder = resource.adaptTo(ConfigurationBuilder.class);
        if(builder !=null) {
            GenericSiteConfiguration configuration = builder.as(GenericSiteConfiguration.class);
            gtmId = configuration.gtmId();
        }
        return gtmId;
    }
}
