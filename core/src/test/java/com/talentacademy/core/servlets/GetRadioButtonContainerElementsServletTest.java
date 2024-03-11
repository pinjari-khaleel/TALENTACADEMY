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
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith({AemContextExtension.class})
class GetRadioButtonContainerElementsServletTest {

    private final String ROOT_PATH = "/content/talentacademy/us/en/test";

    private final AemContext context = new AemContext();

    private GetRadioButtonContainerElementsServlet getRadioButtonContainerElementsServlet;

    /**
     * Setup method for {com.talentacademy.core.servlets.GetRadioButtonContainerElementsServlet}
     */
    @BeforeEach
    public void setUp() {
        context.load().json("/com/talentacademy/core/models/impl/RadioButtonContainerModelImpl.json", ROOT_PATH);

        getRadioButtonContainerElementsServlet = new GetRadioButtonContainerElementsServlet();
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.GetRadioButtonContainerElementsServlet#doGet(SlingHttpServletRequest, SlingHttpServletResponse)}.
     */
    @Test
    void testDoGetWithNullRadioButtonContainerResource() throws ServletException {
        Resource resource = context.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerDefault");
        context.request().setResource(resource);

        try (MockedStatic mockedApplicationUtil = Mockito.mockStatic(ApplicationUtil.class)) {
            mockedApplicationUtil.when(() -> ApplicationUtil.getSecondElementFromSplitUri("/"))
                    .thenReturn("/invalid");

            getRadioButtonContainerElementsServlet.doGet(context.request(),context.response());

            DataSource ds = (DataSource) context.request().getAttribute(DataSource.class.getName());

            assertNull(ds);
        }
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.GetRadioButtonContainerElementsServlet#doGet(SlingHttpServletRequest, SlingHttpServletResponse)}.
     */
    @Test
    void testDoGetWithRadioButtonContainerResource() throws ServletException {
        Resource resource = context.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerDefault");
        context.request().setResource(resource);
        context.request().setServletPath("localhost.html"+ROOT_PATH + "/radiobuttoncontainerDefault");

        try (MockedStatic mockedApplicationUtil = Mockito.mockStatic(ApplicationUtil.class)) {
            mockedApplicationUtil.when(() -> ApplicationUtil.getSecondElementFromSplitUri(context.request().getRequestURI()))
                    .thenReturn(ROOT_PATH + "/radiobuttoncontainerDefault");

            getRadioButtonContainerElementsServlet.doGet(context.request(), context.response());

            DataSource ds = (DataSource) context.request().getAttribute(DataSource.class.getName());

            assertNotNull(ds);
            Resource dsResource = ds.iterator().next();
            assertEquals("Tell me more", dsResource.getValueMap().get("text", String.class));
            assertEquals("text/Tell me more", dsResource.getValueMap().get("value", String.class));
        }
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.GetRadioButtonContainerElementsServlet#doGet(SlingHttpServletRequest, SlingHttpServletResponse)}.
     */
    @Test
    void testDoGetWithRadioButtonContainerHavingColumnContainerResource() throws ServletException {
        Resource resource = context.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerDefaultWithColumnContainer");
        context.request().setResource(resource);

        try (MockedStatic mockedApplicationUtil = Mockito.mockStatic(ApplicationUtil.class)) {
            mockedApplicationUtil.when(() -> ApplicationUtil.getSecondElementFromSplitUri(context.request().getRequestURI()))
                    .thenReturn(ROOT_PATH + "/radiobuttoncontainerDefaultWithColumnContainer");

            getRadioButtonContainerElementsServlet.doGet(context.request(), context.response());

            DataSource ds = (DataSource) context.request().getAttribute(DataSource.class.getName());

            assertNotNull(ds);
            Resource dsResource = ds.iterator().next();
            assertEquals("Employer", dsResource.getValueMap().get("text", String.class));
            assertEquals("text/Employer", dsResource.getValueMap().get("value", String.class));
        }
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.GetRadioButtonContainerElementsServlet#doGet(SlingHttpServletRequest, SlingHttpServletResponse)}.
     */
    @Test
    void testDoGetWithRadioButtonContainerHavingColumnContainerWithOptionsResource() throws ServletException {
        Resource resource = context.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerDefaultWithColumnContainerOptions");
        context.request().setResource(resource);

        try (MockedStatic mockedApplicationUtil = Mockito.mockStatic(ApplicationUtil.class)) {
            mockedApplicationUtil.when(() -> ApplicationUtil.getSecondElementFromSplitUri(context.request().getRequestURI()))
                    .thenReturn(ROOT_PATH + "/radiobuttoncontainerDefaultWithColumnContainerOptions");

            getRadioButtonContainerElementsServlet.doGet(context.request(), context.response());

            DataSource ds = (DataSource) context.request().getAttribute(DataSource.class.getName());

            assertNotNull(ds);
            Resource dsResource = ds.iterator().next();
            assertEquals("Company Size", dsResource.getValueMap().get("text", String.class));
            assertEquals("options/Company Size", dsResource.getValueMap().get("value", String.class));
        }
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.GetRadioButtonContainerElementsServlet#doGet(SlingHttpServletRequest, SlingHttpServletResponse)}.
     */
    @Test
    void testDoGetWithRadioButtonContainerHavingColumnContainerNoFormelements() throws ServletException {
        Resource resource = context.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerDefaultWithColumnContainerNoFormElements");
        context.request().setResource(resource);

        try (MockedStatic mockedApplicationUtil = Mockito.mockStatic(ApplicationUtil.class)) {
            mockedApplicationUtil.when(() -> ApplicationUtil.getSecondElementFromSplitUri(context.request().getRequestURI()))
                    .thenReturn(ROOT_PATH + "/radiobuttoncontainerDefaultWithColumnContainerNoFormElements");

            getRadioButtonContainerElementsServlet.doGet(context.request(), context.response());

            DataSource ds = (DataSource) context.request().getAttribute(DataSource.class.getName());

            assertNotNull(ds);
            assertFalse(ds.iterator().hasNext());
        }
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.GetRadioButtonContainerElementsServlet#doGet(SlingHttpServletRequest, SlingHttpServletResponse)}.
     */
    @Test
    void testDoGetWithRadioButtonContainerHavingNoChildrenElements() throws ServletException {
        Resource resource = context.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerNoChildrenElements");
        context.request().setResource(resource);
        context.request().setServletPath("localhost.html"+ROOT_PATH + "/radiobuttoncontainerNoChildrenElements");

        try (MockedStatic mockedApplicationUtil = Mockito.mockStatic(ApplicationUtil.class)) {
            mockedApplicationUtil.when(() -> ApplicationUtil.getSecondElementFromSplitUri(context.request().getRequestURI()))
                    .thenReturn(ROOT_PATH + "/radiobuttoncontainerNoChildrenElements");

            getRadioButtonContainerElementsServlet.doGet(context.request(), context.response());

            DataSource ds = (DataSource) context.request().getAttribute(DataSource.class.getName());

            assertNotNull(ds);
            assertFalse(ds.iterator().hasNext());
        }
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.GetRadioButtonContainerElementsServlet#doGet(SlingHttpServletRequest, SlingHttpServletResponse)}.
     */
    @Test
    void testDoGetWithRadioButtonContainerHavingOtherChildrenElements() throws ServletException {
        Resource resource = context.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerOtherChildrenElements");
        context.request().setResource(resource);
        context.request().setServletPath("localhost.html"+ROOT_PATH + "/radiobuttoncontainerOtherChildrenElements");

        try (MockedStatic mockedApplicationUtil = Mockito.mockStatic(ApplicationUtil.class)) {
            mockedApplicationUtil.when(() -> ApplicationUtil.getSecondElementFromSplitUri(context.request().getRequestURI()))
                    .thenReturn(ROOT_PATH + "/radiobuttoncontainerOtherChildrenElements");

            getRadioButtonContainerElementsServlet.doGet(context.request(), context.response());

            DataSource ds = (DataSource) context.request().getAttribute(DataSource.class.getName());

            assertNotNull(ds);
            assertFalse(ds.iterator().hasNext());
        }
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.GetRadioButtonContainerElementsServlet#doGet(SlingHttpServletRequest, SlingHttpServletResponse)}.
     */
    @Test
    void testDoGetWithNoRadioButTextAndCheckBox() throws ServletException {
        Resource resource = context.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerWithTextAndOptionsNoRadio");
        context.request().setResource(resource);
        context.request().setServletPath("localhost.html"+ROOT_PATH + "/radiobuttoncontainerWithTextAndOptionsNoRadio");

        try (MockedStatic mockedApplicationUtil = Mockito.mockStatic(ApplicationUtil.class)) {
            mockedApplicationUtil.when(() -> ApplicationUtil.getSecondElementFromSplitUri(context.request().getRequestURI()))
                    .thenReturn(ROOT_PATH + "/radiobuttoncontainerWithTextAndOptionsNoRadio");

            getRadioButtonContainerElementsServlet.doGet(context.request(), context.response());

            DataSource ds = (DataSource) context.request().getAttribute(DataSource.class.getName());

            assertNotNull(ds);
            Resource dsResource = ds.iterator().next();
            assertEquals("CURRENT STATUS", dsResource.getValueMap().get("text", String.class));
            assertEquals("options/CURRENT STATUS", dsResource.getValueMap().get("value", String.class));
        }
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.GetRadioButtonContainerElementsServlet#doGet(SlingHttpServletRequest, SlingHttpServletResponse)}.
     */
    @Test
    void testDoGetWithNoRadioButTextAndOtherElements() throws ServletException {
        Resource resource = context.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerTextAndOtherChildrenElements");
        context.request().setResource(resource);
        context.request().setServletPath("localhost.html"+ROOT_PATH + "/radiobuttoncontainerTextAndOtherChildrenElements");

        try (MockedStatic mockedApplicationUtil = Mockito.mockStatic(ApplicationUtil.class)) {
            mockedApplicationUtil.when(() -> ApplicationUtil.getSecondElementFromSplitUri(context.request().getRequestURI()))
                    .thenReturn(ROOT_PATH + "/radiobuttoncontainerTextAndOtherChildrenElements");

            getRadioButtonContainerElementsServlet.doGet(context.request(), context.response());

            DataSource ds = (DataSource) context.request().getAttribute(DataSource.class.getName());

            assertNotNull(ds);
            assertTrue(ds.iterator().hasNext());
        }
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.GetRadioButtonContainerElementsServlet#doGet(SlingHttpServletRequest, SlingHttpServletResponse)}.
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

            getRadioButtonContainerElementsServlet.doGet(context.request(), context.response());

            DataSource ds = (DataSource) context.request().getAttribute(DataSource.class.getName());

            assertNull(ds);
        }
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.GetRadioButtonContainerElementsServlet#doGet(SlingHttpServletRequest, SlingHttpServletResponse)}.
     */
    @Test
    void testDoGetWithOnlyRadioOption() throws ServletException {
        Resource resource = context.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerWithOnlyRadioOption");
        context.request().setResource(resource);
        context.request().setServletPath("localhost.html"+ROOT_PATH + "/radiobuttoncontainerWithOnlyRadioOption");

        try (MockedStatic mockedApplicationUtil = Mockito.mockStatic(ApplicationUtil.class)) {
            mockedApplicationUtil.when(() -> ApplicationUtil.getSecondElementFromSplitUri(context.request().getRequestURI()))
                    .thenReturn(ROOT_PATH + "/radiobuttoncontainerWithOnlyRadioOption");

            getRadioButtonContainerElementsServlet.doGet(context.request(), context.response());

            DataSource ds = (DataSource) context.request().getAttribute(DataSource.class.getName());

            assertNotNull(ds);
            Resource dsResource = ds.iterator().next();
            assertEquals("High school student", dsResource.getValueMap().get("text", String.class));
            assertEquals("options/radio/High school student", dsResource.getValueMap().get("value", String.class));
        }
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.GetRadioButtonContainerElementsServlet#doGet(SlingHttpServletRequest, SlingHttpServletResponse)}.
     */
    @Test
    void testDoGetWithWrongOnlyRadioOption() throws ServletException {
        Resource resource = context.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerWithWrongRadioOption");
        context.request().setResource(resource);
        context.request().setServletPath("localhost.html"+ROOT_PATH + "/radiobuttoncontainerWithWrongRadioOption");

        try (MockedStatic mockedApplicationUtil = Mockito.mockStatic(ApplicationUtil.class)) {
            mockedApplicationUtil.when(() -> ApplicationUtil.getSecondElementFromSplitUri(context.request().getRequestURI()))
                    .thenReturn(ROOT_PATH + "/radiobuttoncontainerWithWrongRadioOption");

            getRadioButtonContainerElementsServlet.doGet(context.request(), context.response());

            DataSource ds = (DataSource) context.request().getAttribute(DataSource.class.getName());

            assertNotNull(ds);
            assertTrue(ds.iterator().hasNext());
        }
    }
}
