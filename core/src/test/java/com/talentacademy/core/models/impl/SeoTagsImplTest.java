package com.talentacademy.core.models.impl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class SeoTagsImplTest {

    private final AemContext aemContext = new AemContext();

    private final String ROOT_PATH = "/content/talentacademy/us/en/home";

    SeoTagsImpl seoTagsImpl;


    /**
     * setUp method for
     * {@link SeoTagsImpl}.
     */
    @BeforeEach
    public void setup() {
        aemContext.addModelsForClasses(SeoTagsImpl.class);
        aemContext.load().json("/com/talentacademy/core/models/impl/SeoTagsTest.json", ROOT_PATH);
    }

    /**
     * Test method for
     * {@link SeoTagsImpl #getTitle(), #getDescription(), #getTwitterCard(), #getTwitterDescription(), #getTwitterImageAltText(),
     * #getTwitterSiteName(), #getTwitterTitle(), #getOgTitle(), #getOgDescription(), #getOgImageAltText(), #getOgType(),
     * #getOgLocale(), #getOgSiteName(), #getCanonicalUrl(), #getOgImage(), #getTwitterImage(), #getOgUrl()}
     */
    @Test
    void validateTags() {
        aemContext.currentResource(ROOT_PATH + "/jcr:content");
        seoTagsImpl = aemContext.request().adaptTo(SeoTagsImpl.class);
        assertEquals("en", seoTagsImpl.getTitle());
        assertEquals("testDesc", seoTagsImpl.getDescription());
        assertNotNull(seoTagsImpl.getRobotTags());
        assertEquals("summary", seoTagsImpl.getTwitterCard());
        assertEquals("Twitter Description", seoTagsImpl.getTwitterDescription());
        assertEquals("Twitter Image Alt Text", seoTagsImpl.getTwitterImageAltText());
        assertEquals("Twitter Site Name", seoTagsImpl.getTwitterSiteName());
        assertEquals("Twitter Title", seoTagsImpl.getTwitterTitle());
        assertEquals("og Title", seoTagsImpl.getOgTitle());
        assertEquals("Og Description", seoTagsImpl.getOgDescription());
        assertEquals("og Image Alt Text", seoTagsImpl.getOgImageAltText());
        assertEquals("product", seoTagsImpl.getOgType());
        assertEquals("en-us", seoTagsImpl.getOgLocale());
        assertEquals("og Site Name", seoTagsImpl.getOgSiteName());
        assertEquals("/content/talentacademy/us/en/home.html", seoTagsImpl.getCanonicalUrl());
        assertEquals("/content/dam/neomtalentacademy/asset.jpg", seoTagsImpl.getOgImage());
        assertEquals("/content/dam/neomtalentacademy/asset.jpg", seoTagsImpl.getTwitterImage());
        assertEquals("/content/talentacademy/us/en/home.html", seoTagsImpl.getOgUrl());
    }

    /**
     * Test method for
     * {@link SeoTagsImpl #getCanonicalUrl()}.
     */
    @Test
    void returnNullIfPageNotFound() {
        seoTagsImpl = aemContext.request().adaptTo(SeoTagsImpl.class);
        assertEquals("", seoTagsImpl.getCanonicalUrl());
    }
}
