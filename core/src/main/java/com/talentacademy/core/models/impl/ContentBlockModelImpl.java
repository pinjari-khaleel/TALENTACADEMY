package com.talentacademy.core.models.impl;

import com.talentacademy.core.beans.ContentDetails;
import com.talentacademy.core.models.ContentBlockModel;
import com.talentacademy.core.utils.ApplicationUtil;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sairam Implementation class of ContentBlockModel
 */
@Model(adaptables = { SlingHttpServletRequest.class,
        Resource.class }, adapters = ContentBlockModel.class, resourceType = ContentBlockModelImpl.RESOURCE_TYPE, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContentBlockModelImpl implements ContentBlockModel {

    protected static final String RESOURCE_TYPE = "talentacademy/components/content/contentblock";

    @ValueMapValue
    private String header;

    @ValueMapValue
    private String description;

    @ValueMapValue
    private String shortDescription;

    @ValueMapValue
    private String fileReference;

    @ValueMapValue
    private String altText;

    @ValueMapValue
    private String findOutMoreLabel;

    @ValueMapValue
    private String findOutMoreLink;

    @ChildResource
    private List<ContentDetails> contentDetails;

    @SlingObject
    private ResourceResolver resolver;
    
    //Without Image variation Props
    @ValueMapValue
    private String pretitle;

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String descriptionText;

    /**
     * @return The header
     */
    @Override
    public String getHeader() {
        return header;
    }

    /**
     * @return The description
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * @return The fileReferencece
     */
    @Override
    public String getFileReference() {
        return fileReference;
    }

    /**
     * @return The altText
     */
    @Override
    public String getAltText() {
        return altText;
    }

    /**
     * @return The shortDescription
     */
    @Override
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * @return The contentDetails
     */
    @Override
    public List<ContentDetails> getContentDetails() {
        return new ArrayList<>(contentDetails);
    }

    /**
     * @return The findOutMoreLabel
     */
    @Override
    public String getFindOutMoreLabel() {
        return findOutMoreLabel;
    }

    /**
     * @return The findOutMoreLink
     */
    @Override
    public String getFindOutMoreLink() {
        return ApplicationUtil.getValidURL(findOutMoreLink, resolver);
    }

    /**
     * @return The pretitle
     */
    @Override
    public String getPretitle() {
		return pretitle;
	}

    /**
     * @return The title
     */
    @Override
	public String getTitle() {
		return title;
	}

    /**
     * @return The descriptionText
     */
    @Override
	public String getDescriptionText() {
		return descriptionText;
	}

    /**
     * @param header set the header
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * @param description set the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param findOutMoreLink set the findOutMoreLink
     */
    public void setFindOutMoreLink(String findOutMoreLink) {
        this.findOutMoreLink = findOutMoreLink;
    }
}