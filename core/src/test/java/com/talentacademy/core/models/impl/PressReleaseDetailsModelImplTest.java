package com.talentacademy.core.models.impl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class PressReleaseDetailsModelImplTest {

    private final AemContext aemContext = new AemContext();
    PressReleaseDetailsModelImpl pressReleaseDetailsModel;

    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(PressReleaseDetailsModelImpl.class);
        aemContext.load().json("/com/talentacademy/core/models/impl/PressReleaseDetailsModelTest.json","/content");
        aemContext.currentResource("/content/pressreleasedetails");
        pressReleaseDetailsModel = aemContext.request().adaptTo(PressReleaseDetailsModelImpl.class);
    }

    /**
     * Test method for
     * {@link PressReleaseDetailsModelImpl#getDate()}.
     */
    @Test
    void getDate() {
        assertEquals("September 02, 2023", pressReleaseDetailsModel.getDate());
    }

    /**
     * Test method for
     * {@link PressReleaseDetailsModelImpl#getHeroImage()}.
     */
    @Test
    void getHeroImage() {
        assertEquals("/content/dam/talentacademy/press-detail-image1.jpg", pressReleaseDetailsModel.getHeroImage());
    }

    /**
     * Test method for
     * {@link PressReleaseDetailsModelImpl#getImageAlt()}.
     */
    @Test
    void getImageAlt() {
        assertEquals("image alt", pressReleaseDetailsModel.getImageAlt());
    }

    /**
     * Test method for
     * {@link PressReleaseDetailsModelImpl#getHeading()}.
     */
    @Test
    void getHeading() {
        assertEquals("NEOM ANNOUNCES EPICON – ITS LUXURY COASTAL TOURISM DESTINATION ON THE GULF OF AQABA", pressReleaseDetailsModel.getHeading());
    }

    /**
     * Test method for
     * {@link PressReleaseDetailsModelImpl#getShortDescription()}.
     */
    @Test
    void getShortDescription() {
        assertEquals("Siranna is a space where elegant and innovative living meet to facilitate a lifestyle without compromise.", pressReleaseDetailsModel.getShortDescription());
    }

    /**
     * Test method for
     * {@link PressReleaseDetailsModelImpl#getLongDescription()}.
     */
    @Test
    void getLongDescription() {
        assertEquals("The destination offers uninterrupted views of the Red Sea, with its tiered design offering diverse views and perspectives.", pressReleaseDetailsModel.getLongDescription());
    }

    /**
     * Test method for
     * {@link PressReleaseDetailsModelImpl#getCTALabel()}.
     */
    @Test
    void getCTALabel() {
        assertEquals("Back TO PRESS RELEASES", pressReleaseDetailsModel.getCTALabel());
    }

    /**
     * Test method for
     * {@link PressReleaseDetailsModelImpl#getCTAUrl()}.
     */
    @Test
    void getCTAUrl() {
        assertEquals("/content/talentacademy/sa/en/press-release.html", pressReleaseDetailsModel.getCTAUrl());
    }

    /**
     * Test method for
     * {@link PressReleaseDetailsModelImpl#getOpenInNewTab()}.
     */
    @Test
    void getOpenInNewTab() {
        assertEquals("_blank", pressReleaseDetailsModel.getOpenInNewTab());
    }
}