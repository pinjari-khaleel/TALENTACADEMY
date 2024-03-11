package com.talentacademy.core.models.impl;

import com.talentacademy.core.beans.GridListing;
import com.talentacademy.core.services.GridListingService;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class PressReleaseModelImplTest {

    private final AemContext aemContext = new AemContext(ResourceResolverType.JCR_MOCK);

    @Mock
    GridListingService gridListingService;

    PressReleaseModelImpl pressReleaseModel = mock(PressReleaseModelImpl.class);

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        aemContext.addModelsForClasses(PressReleaseModelImpl.class);
        aemContext.load().json("/com/talentacademy/core/models/impl/PressReleaseModelTest.json", "/content");
        aemContext.currentResource("/content/pressrelease");
        pressReleaseModel = aemContext.request().adaptTo(PressReleaseModelImpl.class);
    }

    /**
     * Test method for
     * {@link PressReleaseModelImpl#getLoadMoreLimit()}.
     */
    @Test
    void getLoadMoreLimit() {
        assertEquals(6,pressReleaseModel.getLoadMoreLimit());
    }

    /**
     * Test method for
     * {@link PressReleaseModelImpl#getRootDetailsPath()}.
     */
    @Test
    void getRootDetailsPath() {
        assertEquals("/content/talentacademy/sa/en/press-release",pressReleaseModel.getRootDetailsPath());
    }

    /**
     * Test method for
     * {@link PressReleaseModelImpl#getKnowMoreCtaLabel()}.
     */
    @Test
    void getKnowMoreCtaLabel() {
        assertEquals("know more",pressReleaseModel.getKnowMoreCtaLabel());
    }

    /**
     * Test method for
     * {@link PressReleaseModelImpl#getLoadMoreCtaLabel()}.
     */
    @Test
    void getLoadMoreCtaLabel() {
        assertEquals("Load more",pressReleaseModel.getLoadMoreCtaLabel());
    }

    /**
     * Test method for
     * {@link PressReleaseModelImpl#getPressReleaseDetails()}.
     */
    @Test
    void getPressReleaseDetails() throws NoSuchFieldException, IllegalAccessException {
        PrivateAccessor.setField(pressReleaseModel, "gridListingService", gridListingService);
        String rootPath = "/content/talentacademy/sa/en/press-release";
        GridListing gridListing = new GridListing();
        List<GridListing> gridListingList = new ArrayList<>();
        gridListing.setCreatedDate("20 September 2023");
        gridListing.setCardImage("/content/dam/talentacademy/press-detail-image1.jpg");
        gridListing.setHeading("NEOM ANNOUNCES EPICON – ITS LUXURY COASTAL TOURISM DESTINATION ON THE GULF OF AQABA");
        gridListing.setShortDescription("Siranna is a space where elegant and innovative living meet to facilitate a lifestyle without compromise.");
        gridListingList.add(gridListing);
        Mockito.when(gridListingService.getPressReleaseDetaildata(Mockito.any(), Mockito.eq(rootPath)))
                .thenReturn(gridListingList);
        assertEquals(gridListingList,pressReleaseModel.getPressReleaseDetails());
    }
}