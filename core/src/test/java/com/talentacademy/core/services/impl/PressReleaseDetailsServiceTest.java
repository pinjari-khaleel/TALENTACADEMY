package com.talentacademy.core.services.impl;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.talentacademy.core.beans.GridListing;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.when;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class PressReleaseDetailsServiceTest {

    @InjectMocks
    GridListingServiceImpl pressReleaseDetails;

    @Mock
    Page page, childPage;

    @Mock
    Resource resource, componentContent;

    @Mock
    ResourceResolver resourceResolver;

    @Mock
    PageManager pageManager;

    @Mock
    Iterator<Page> childPages;

    @Mock
    ValueMap value;

    private static final String ROOT_DETAILS_PAGE = "/content/talentacademy/sa/en/press-release";
    private static final String DETAILS_PAGE_PATH = "/content/talentacademy/sa/en/press-release/press-release-detail";

    @BeforeEach
    void setUp() {

    }

    /**
     * Test method for
     * {@link GridListingServiceImpl#getPressReleaseDetaildata}.
     *
     */
    @Test
    void getPressReleaseDetaildata() throws LoginException {
        when(resourceResolver.getResource(ROOT_DETAILS_PAGE)).thenReturn(resource);
        when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
        when(pageManager.getContainingPage(resource)).thenReturn(page);
        when(page.listChildren()).thenReturn(childPages);
        when(childPages.hasNext()).thenReturn(true, false);
        when(childPages.next()).thenReturn(childPage);
        when(childPage.getPath()).thenReturn(DETAILS_PAGE_PATH);
        when(resourceResolver.getResource(DETAILS_PAGE_PATH+"/jcr:content/root/container/pressreleasedetails")).thenReturn(componentContent);
        when(componentContent.getValueMap()).thenReturn(value);
        when(childPage.getPath()).thenReturn(DETAILS_PAGE_PATH);
        validatePressDetailProperties(value);
        assertNotNull(pressReleaseDetails.getPressReleaseDetaildata(resourceResolver,ROOT_DETAILS_PAGE));
    }

    private void validatePressDetailProperties(ValueMap value) {
        when(value.get("date", String.class)).thenReturn("September 02, 2023");
        when(value.get("cardImage", String.class)).thenReturn("image.png");
        when(value.get("cardImageAltText", String.class)).thenReturn("alt text");
        when(value.get("heading", String.class)).thenReturn("NEOM ANNOUNCES EPICON – ITS LUXURY COASTAL TOURISM DESTINATION ON THE GULF OF AQABA");
        when(value.get("shortDescription", String.class)).thenReturn("Siranna is a space where elegant and innovative living meet to facilitate a lifestyle without compromise.");
    }

}