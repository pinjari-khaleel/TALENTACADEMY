package com.talentacademy.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.talentacademy.core.beans.GridListing;
import com.talentacademy.core.models.PressReleaseModel;
import com.talentacademy.core.services.GridListingService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = { Resource.class, SlingHttpServletRequest.class },
        adapters = PressReleaseModel.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
        resourceType = PressReleaseModelImpl.RESOURCE_TYPE)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class PressReleaseModelImpl implements PressReleaseModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(PressReleaseModelImpl.class);

    public static final String RESOURCE_TYPE = "talentacademy/components/content/gridlisting";

    @OSGiService
    private GridListingService gridListingService;

    @SlingObject
    private ResourceResolver resourceResolver;

    @ValueMapValue
    private int loadMoreLimit;

    @ValueMapValue
    private String rootDetailsPath;

    @ValueMapValue
    private String knowMoreCtaLabel;

    @ValueMapValue
    private String loadMoreCtaLabel;

    /**
     * @return The load more limit number which is confugured in the dialog
     */
    @Override
    public int getLoadMoreLimit() {
        return loadMoreLimit;
    }

    /**
     * @return The root career details page path which is confugured in the dialog
     */
    @Override
    public String getRootDetailsPath() {
        return rootDetailsPath;
    }

    /**
     * @return The know more CTA label
     */
    @Override
    public String getKnowMoreCtaLabel() {
        return knowMoreCtaLabel;
    }

    /**
     * @return The know more CTA label
     */
    @Override
    public String getLoadMoreCtaLabel() {
        return loadMoreCtaLabel;
    }

    /**
     * @returns The list of GridListing data which is formed in grid listing service
     */
    public List<GridListing> getPressReleaseDetails() {
        return Collections.unmodifiableList(gridListingService.getPressReleaseDetaildata(resourceResolver, rootDetailsPath));
    }

}
