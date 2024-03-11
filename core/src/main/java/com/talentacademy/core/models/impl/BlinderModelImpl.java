package com.talentacademy.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.wcm.api.Page;
import com.talentacademy.core.beans.PageModel;
import com.talentacademy.core.models.BlinderModel;
import com.talentacademy.core.utils.ApplicationUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Model(adaptables = Resource.class, adapters = {BlinderModel.class},
        resourceType = "talentacademy/components/blinder",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class BlinderModelImpl implements BlinderModel {
	private static final Logger LOG = LoggerFactory.getLogger(BlinderModelImpl.class);

    private static final String DYNAMIC = "dynamic";
    private static final String JCR_CONTENT_CONTENT_BLOCK = "/jcr:content/root/container/contentblock";

    @ValueMapValue
    private String header;

    @ValueMapValue
    private String variation;

    @ChildResource
    private List<ContentBlockModelImpl> blinderDetails;

    @ChildResource
    private List<PageModel> pages;

    @SlingObject
    private ResourceResolver resolver;

    @PostConstruct
    private void init() {
        if(DYNAMIC.equals(variation) && pages != null) {
            blinderDetails = new ArrayList<>();
            pages.forEach(page -> {
                String pagePath = page.getPagePath();
                if (StringUtils.isNotEmpty(pagePath)) {
                    updateDynamicDetails(pagePath);
                }
            });
        }
    }

    /**
     * Updates the blinder details from the authored pages
     *
     * @param pagePath pagePath.
     */
    private void updateDynamicDetails(String pagePath) {
    	LOG.debug("*** NTA BlinderModelImpl updateDynamicDetails() starts ***");
        Resource contentBlockResource = resolver.getResource(pagePath + JCR_CONTENT_CONTENT_BLOCK);
        if(contentBlockResource != null) {
            ContentBlockModelImpl contentBlockModel = contentBlockResource.adaptTo(ContentBlockModelImpl.class);
            contentBlockModel.setFindOutMoreLink(ApplicationUtil.getValidURL(pagePath, resolver));
            Resource pageResource = resolver.getResource(pagePath);
            if (pageResource != null) {
                Page pageObj = pageResource.adaptTo(Page.class);
                if (pageObj != null) {
                    contentBlockModel.setHeader(pageObj.getTitle().toUpperCase(Locale.ROOT));
                    contentBlockModel.setDescription(contentBlockModel.getShortDescription());
                }
            }
            blinderDetails.add(contentBlockModel);
        }
    	LOG.debug("*** NTA BlinderModelImpl updateDynamicDetails() ends ***");
    }

    /**
     * @return The Blinder details
     */
    @Override
    public List<ContentBlockModelImpl> getBlinderDetails() {
        return new ArrayList<>(blinderDetails);
    }

    /**
     * @return The header
     */
    @Override
    public String getHeader() {
        return header;
    }

    /**
     * @return The variation
     */
    @Override
    public String getVariation() {
        return variation;
    }

    /**
     * @return The pages
     */
    @Override
    public List<PageModel> getPages() {
        return new ArrayList<>(pages);
    }
}
