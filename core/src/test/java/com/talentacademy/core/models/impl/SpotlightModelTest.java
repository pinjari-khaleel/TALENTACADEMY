package com.talentacademy.core.models.impl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class SpotlightModelTest {

    private final AemContext aemContext = new AemContext();

    private final String ROOT_PATH = "/content/talentacademy/us/en/test";

    SpotlightModelImpl spotlightModel;

    @BeforeEach
    public void setup() {
        aemContext.addModelsForClasses(SpotlightModelImpl.class);
        aemContext.load().json("/com/talentacademy/core/models/impl/SpotlightTest.json", ROOT_PATH);
        aemContext.currentResource(ROOT_PATH);
        spotlightModel = aemContext.currentResource().adaptTo(SpotlightModelImpl.class);
    }

    /**
     * Test method for
     * {@link SpotlightModelImpl#getCaption()}.
     */
    @Test
    void fetchSpotlightCaption() {
        assertEquals("test Caption", spotlightModel.getCaption());
    }

    /**
     * Test method for
     * {@link SpotlightModelImpl#getLearningPathway()}.
     */
    @Test
    void fetchSpotlightLearningPathway() {
        assertEquals("Learning Pathway", spotlightModel.getLearningPathway());
    }

    /**
     * Test method for
     * {@link SpotlightModelImpl#getTitle()}.
     */
    @Test
    void fetchSpotlightTitle() {
        assertEquals("test Title", spotlightModel.getTitle());
    }

    /**
     * Test method for
     * {@link SpotlightModelImpl#getDescription()}.
     */
    @Test
    void fetchSpotlightDescription() {
        assertEquals("test description", spotlightModel.getDescription());
    }

    /**
     * Test method for
     * {@link SpotlightModelImpl#getBackgroundVideo()}.
     */
    @Test
    void fetchSpotlightBackgroundVideo() {
        assertEquals("/content/dam/testVideo.mp4", spotlightModel.getBackgroundVideo());
    }

    /**
     * Test method for
     * {@link SpotlightModelImpl#getBackgroundVideoPoster()}.
     */
    @Test
    void fetchSpotlightBackgroundVideoPoster() {
        assertEquals("/content/dam/testVideo/poster.mp4", spotlightModel.getBackgroundVideoPoster());
    }

    /**
     * Test method for
     * {@link SpotlightModelImpl#getDesktopBackgroundImage()}.
     */
    @Test
    void fetchSpotlightDesktopBackgroundImage() {
        assertEquals("/content/dam/test1.jpg", spotlightModel.getDesktopBackgroundImage());
    }

    /**
     * Test method for
     * {@link SpotlightModelImpl#getMobileBackgroundImage()}.
     */
    @Test
    void fetchSpotlightMobileBackgroundImage() {
        assertEquals("/content/dam/test2.jpg", spotlightModel.getMobileBackgroundImage());
    }

    /**
     * Test method for
     * {@link SpotlightModelImpl#getImageAltText()}.
     */
    @Test
    void fetchSpotlightImageAltText() {
        assertEquals("Image Alt Text", spotlightModel.getImageAltText());
    }
}