package com.talentacademy.core.models.impl;

import com.day.cq.wcm.api.Page;
import com.talentacademy.core.models.SeoTags;
import com.talentacademy.core.utils.ApplicationUtil;
import com.talentacademy.core.constants.ApplicationConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Model(adaptables = SlingHttpServletRequest.class,
        adapters = {SeoTags.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SeoTagsImpl implements SeoTags {
    @Inject
    Page currentPage;

    @SlingObject
    ResourceResolver resourceResolver;

    private String currentPagePath = "";

    private String countryCode;

    private String languageCode;


    /**
     * initialising the currentPagePath, countryCode and languageCode
     */
    @PostConstruct
    public void init() {
        if(currentPage != null) {
            currentPagePath = currentPage.getPath();
            countryCode = ApplicationUtil.getCountryFromPath(currentPagePath);
            languageCode = ApplicationUtil.getLanguageFromPath(currentPagePath);
        }
    }

    /**
     * @return the title of the page
     */
    @Override
    public String getTitle() {
        return currentPage.getTitle();
    }

    /**
     * @return the description of the page
     */
    @Override
    public String getDescription() {
        return currentPage.getDescription();
    }

    /**
     * @return the templateName
     */
    @Override
    public String getTemplateName() {
        return currentPage.getTemplate().getName();
    }

    /**
     * @return the robotTags
     */
    @Override
    public String[] getRobotTags() {
        return currentPage.getProperties().get(ApplicationConstants.ROBOTS_TAGS, String[].class);
    }

    /**
     * @return the twitterCard
     */
    @Override
    public String getTwitterCard() {
        return currentPage.getProperties().get(ApplicationConstants.TWITTER_CARD, String.class);
    }

    /**
     * @return the twitterDescription
     */
    @Override
    public String getTwitterDescription() {
        return currentPage.getProperties().get(ApplicationConstants.TWITTER_DESCRIPTION, String.class);
    }

    /**
     * @return the twitterImage
     */
    @Override
    public String getTwitterImage() {
        return getAbsolutePath(currentPage.getProperties().get(ApplicationConstants.TWITTER_IMAGE, String.class));
    }

    /**
     * @return the fileReference
     */
    @Override
    public String getTwitterImageAltText() {
        return currentPage.getProperties().get(ApplicationConstants.TWITTER_IMAGE_ALT_TEXT, String.class);
    }

    /**
     * @return the twitterSite
     */
    @Override
    public String getTwitterSiteName() {
        return currentPage.getProperties().get(ApplicationConstants.TWITTER_SITE, String.class);
    }

    /**
     * @return the twitterTitle
     */
    @Override
    public String getTwitterTitle() {
        return currentPage.getProperties().get(ApplicationConstants.TWITTER_TITLE, String.class);
    }

    /**
     * @return the canonical URL
     */
    @Override
    public String getCanonicalUrl() {
        return getAbsolutePath(currentPagePath);
    }

    /**
     * @return the ogTitle
     */
    @Override
    public String getOgTitle() {
        return currentPage.getProperties().get(ApplicationConstants.OG_TITLE, String.class);
    }

    /**
     * @return the ogDescription
     */
    @Override
    public String getOgDescription() {
        return currentPage.getProperties().get(ApplicationConstants.OG_DESCRIPTION, String.class);
    }

    /**
     * @return the ogImage
     */
    @Override
    public String getOgImage() {
        return getAbsolutePath(currentPage.getProperties().get(ApplicationConstants.OG_IMAGE, String.class));
    }

    /**
     * @return the ogImageText
     */
    @Override
    public String getOgImageAltText() {
        return currentPage.getProperties().get(ApplicationConstants.OG_IMAGE_ALT_TEXT, String.class);
    }

    /**
     * @return the ogURL
     */
    @Override
    public String getOgUrl() {
        return getAbsolutePath(currentPagePath);
    }

    /**
     * @return the ogType
     */
    @Override
    public String getOgType() {
        return currentPage.getProperties().get(ApplicationConstants.OG_TYPE, String.class);
    }

    /**
     * @return the ogLocale
     */
    @Override
    public String getOgLocale() {
        return languageCode+"-"+countryCode;
    }

    /**
     * @return the ogSite
     */
    @Override
    public String getOgSiteName() {
        return currentPage.getProperties().get(ApplicationConstants.OG_SITE, String.class);
    }

    /**
     * @return the absolute path
     */
    private String getAbsolutePath(String path) {
       return ApplicationUtil.getValidURL(path, resourceResolver);
    }
}
