package com.talentacademy.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.wcm.api.Page;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class PromotionalBannerModelImplTest {

    @InjectMocks
    PromotionalBannerModelImpl promotionalBannerModelImpl;

    private Page page;
    private Resource resource;
    private AemContext context = new AemContext();
    private static final String PROMOTIONALBANNER_NODE = "columncontrol";
    private static final String SECTIONTITLE = "sectionTitle";
    private static final String SECTIONDESCRIPTION = "sectionDescription";
    private static final String FILEREFERENCE = "fileReference";
    private static final String ALTTEXT = "altTextForImage";

    /**
     * setUp method for
     * {@link PromotionalBannerModelImpl}.
     */
    @BeforeEach
    public void setup() throws Exception {
        page = context.create().page("/content/talentacademy");
    }

    /**
     * Test method for
     * {@link PromotionalBannerModelImpl #getSectionDescription()}.
     */
    @Test
    void testGetSectionDescription() {
        resource = context.create().resource(page, PROMOTIONALBANNER_NODE,
                JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, PromotionalBannerModelImpl.RESOURCE_TYPE,
                SECTIONDESCRIPTION, "Section Description");
        promotionalBannerModelImpl = resource.adaptTo(PromotionalBannerModelImpl.class);
        assertEquals("Section Description", promotionalBannerModelImpl.getSectionDescription());
    }

    /**
     * Test method for
     * {@link PromotionalBannerModelImpl #getSectionTitle()}.
     */
    @Test
    void testGetSectionTitle() {
        resource = context.create().resource(page, PROMOTIONALBANNER_NODE,
                JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, PromotionalBannerModelImpl.RESOURCE_TYPE,
                SECTIONTITLE, "Section Title");
        promotionalBannerModelImpl = resource.adaptTo(PromotionalBannerModelImpl.class);
        assertEquals("Section Title", promotionalBannerModelImpl.getSectionTitle());

    }

    /**
     * Test method for
     * {@link PromotionalBannerModelImpl #getFileReference()}.
     */
    @Test
    void testGetFileReference() {
        resource = context.create().resource(page, PROMOTIONALBANNER_NODE,
                JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, PromotionalBannerModelImpl.RESOURCE_TYPE,
                FILEREFERENCE, "/content/dam/talentacademy/bgimage.jpg");
        promotionalBannerModelImpl = resource.adaptTo(PromotionalBannerModelImpl.class);
        assertEquals("/content/dam/talentacademy/bgimage.jpg", promotionalBannerModelImpl.getFileReference());

    }

    /**
     * Test method for
     * {@link PromotionalBannerModelImpl #getAltTextForImage()}.
     */
    @Test
    void testGetAltTextForImage() {
        resource = context.create().resource(page, PROMOTIONALBANNER_NODE,
                JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, PromotionalBannerModelImpl.RESOURCE_TYPE,
                ALTTEXT, "Alt text for image");
        promotionalBannerModelImpl = resource.adaptTo(PromotionalBannerModelImpl.class);
        assertEquals("Alt text for image", promotionalBannerModelImpl.getAltTextForImage());

    }
}
