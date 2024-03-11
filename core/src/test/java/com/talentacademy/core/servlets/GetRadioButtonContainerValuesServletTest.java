package com.talentacademy.core.servlets;

import com.adobe.granite.ui.components.ds.DataSource;
import com.talentacademy.core.utils.ApplicationUtil;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.servlet.ServletException;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.mock;

@ExtendWith({AemContextExtension.class})
class GetRadioButtonContainerValuesServletTest {

    private final String ROOT_PATH = "/content/talentacademy/us/en/test";

    private final AemContext context = new AemContext();
    private GetRadioButtonContainerValuesServlet getRadioButtonContainerValuesServlet;

    /**
     * Setup method for {com.talentacademy.core.servlets.GetRadioButtonContainerValuesServlet}
     */
    @BeforeEach
    public void setUp() {
        context.load().json("/com/talentacademy/core/models/impl/RadioButtonContainerModelImpl.json", ROOT_PATH);

        getRadioButtonContainerValuesServlet = new GetRadioButtonContainerValuesServlet();
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.GetRadioButtonContainerValuesServlet#doGet(SlingHttpServletRequest, SlingHttpServletResponse)}.
     */
    @Test
    void doGetWithNullComponentResource() throws ServletException {
        Resource resource = context.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerDefault");
        context.request().setResource(resource);

        try (MockedStatic mockedApplicationUtil = Mockito.mockStatic(ApplicationUtil.class)) {
            mockedApplicationUtil.when(() -> ApplicationUtil.getSecondElementFromSplitUri(context.request().getRequestURI()))
                    .thenReturn("/invalid");

            getRadioButtonContainerValuesServlet.doGet(context.request(), context.response());

            DataSource ds = (DataSource) context.request().getAttribute(DataSource.class.getName());

            assertNull(ds);
        }
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.GetRadioButtonContainerValuesServlet#doGet(SlingHttpServletRequest, SlingHttpServletResponse)}.
     */
    @Test
    void doGetWithComponentResource() throws ServletException {
        Resource resource = context.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerDefault");
        context.request().setResource(resource);
        context.request().setServletPath("localhost.html"+ROOT_PATH + "/radiobuttoncontainerDefault");

        try (MockedStatic mockedApplicationUtil = Mockito.mockStatic(ApplicationUtil.class)) {
            mockedApplicationUtil.when(() -> ApplicationUtil.getSecondElementFromSplitUri(context.request().getRequestURI()))
                    .thenReturn(ROOT_PATH + "/radiobuttoncontainerDefault");

            getRadioButtonContainerValuesServlet.doGet(context.request(), context.response());

            DataSource ds = (DataSource) context.request().getAttribute(DataSource.class.getName());

            assertNotNull(ds);
            Resource dsResource = ds.iterator().next();
            assertEquals("I want to kickstart my career", dsResource.getValueMap().get("text", String.class));
            assertEquals("kickstart career", dsResource.getValueMap().get("value", String.class));
        }
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.GetRadioButtonContainerValuesServlet#doGet(SlingHttpServletRequest, SlingHttpServletResponse)}.
     */
    @Test
    void doGetWithComponentResourceHavingNoItems() throws ServletException {
        Resource resource = context.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerNoItems");
        context.request().setResource(resource);
        context.request().setServletPath("localhost.html"+ROOT_PATH + "/radiobuttoncontainerNoItems");

        try (MockedStatic mockedApplicationUtil = Mockito.mockStatic(ApplicationUtil.class)) {
            mockedApplicationUtil.when(() -> ApplicationUtil.getSecondElementFromSplitUri(context.request().getRequestURI()))
                    .thenReturn(ROOT_PATH + "/radiobuttoncontainerNoItems");

            getRadioButtonContainerValuesServlet.doGet(context.request(), context.response());

            DataSource ds = (DataSource) context.request().getAttribute(DataSource.class.getName());

            assertNotNull(ds);
            assertFalse(ds.iterator().hasNext());
        }
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.GetRadioButtonContainerValuesServlet#doGet(SlingHttpServletRequest, SlingHttpServletResponse)}.
     */
    @Test
    void doGetWithComponentResourceHavingIncompleteKeys() throws ServletException {
        Resource resource = context.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerItemsNoCompleteKeys");
        context.request().setResource(resource);
        context.request().setServletPath("localhost.html"+ROOT_PATH + "/radiobuttoncontainerItemsNoCompleteKeys");

        try (MockedStatic mockedApplicationUtil = Mockito.mockStatic(ApplicationUtil.class)) {
            mockedApplicationUtil.when(() -> ApplicationUtil.getSecondElementFromSplitUri(context.request().getRequestURI()))
                    .thenReturn(ROOT_PATH + "/radiobuttoncontainerItemsNoCompleteKeys");

            getRadioButtonContainerValuesServlet.doGet(context.request(), context.response());

            DataSource ds = (DataSource) context.request().getAttribute(DataSource.class.getName());

            assertNotNull(ds);
            Resource dsResource = ds.iterator().next();
            assertEquals("I want to accelerate my existing career", dsResource.getValueMap().get("text", String.class));
            assertEquals("accelerate career", dsResource.getValueMap().get("value", String.class));
        }
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.GetRadioButtonContainerValuesServlet#doGet(SlingHttpServletRequest, SlingHttpServletResponse)}.
     */
    @Test
    void testDoGetWithNullRequestUri() throws ServletException {
        AemContext context = mock(AemContext.class);
        MockSlingHttpServletRequest request = mock(MockSlingHttpServletRequest.class);
        ResourceResolver resourceResolver = mock(ResourceResolver.class);

        Mockito.when(context.request()).thenReturn(request);
        Mockito.when(context.request().getResourceResolver()).thenReturn(resourceResolver);
        Mockito.when(context.request().getRequestURI()).thenReturn(null);

        try (MockedStatic mockedApplicationUtil = Mockito.mockStatic(ApplicationUtil.class)) {
            mockedApplicationUtil.when(() -> ApplicationUtil.getSecondElementFromSplitUri(context.request().getRequestURI()))
                    .thenReturn(null);

            getRadioButtonContainerValuesServlet.doGet(context.request(), context.response());

            DataSource ds = (DataSource) context.request().getAttribute(DataSource.class.getName());

            assertNull(ds);
        }
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.GetRadioButtonContainerValuesServlet#doGet(SlingHttpServletRequest, SlingHttpServletResponse)}.
     */
    @Test
    void doGetWithComponentResourceHavingMissingTextOrValueKeys() throws ServletException {
        Resource resource = context.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerItemsNoValueOrNoTextKeys");
        context.request().setResource(resource);
        context.request().setServletPath("localhost.html"+ROOT_PATH + "/radiobuttoncontainerItemsNoValueOrNoTextKeys");

        try (MockedStatic mockedApplicationUtil = Mockito.mockStatic(ApplicationUtil.class)) {
            mockedApplicationUtil.when(() -> ApplicationUtil.getSecondElementFromSplitUri(context.request().getRequestURI()))
                    .thenReturn(ROOT_PATH + "/radiobuttoncontainerItemsNoValueOrNoTextKeys");

            getRadioButtonContainerValuesServlet.doGet(context.request(), context.response());

            DataSource ds = (DataSource) context.request().getAttribute(DataSource.class.getName());

            assertNotNull(ds);
            assertFalse(ds.iterator().hasNext());
        }
    }

}
