package com.talentacademy.core.configs;

import org.apache.sling.caconfig.annotation.Configuration;
import org.apache.sling.caconfig.annotation.Property;

@Configuration(label = "Generic site-specific context configurations [NTA]",
        description = "Generic site-specific context configurations")
public @interface GenericSiteConfiguration {

    /**
     * GTM Id.
     *
     * @return GTM Id.
     */
    @Property(label = "GTM Id",
            description = "Set GTM Id")
    String gtmId();
}
