package com.talentacademy.core.beans;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PageModel {

    @ValueMapValue
    private String pagePath;

    /**
     * @return The pagePath
     */
    public String getPagePath() {
        return pagePath;
    }

    /**
     * @param pagePath set the page path
     */
    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }
}
