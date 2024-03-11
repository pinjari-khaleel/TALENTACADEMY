package com.talentacademy.core.servlets;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.talentacademy.core.models.impl.FAQAccordionModelImpl;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.factory.ModelFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static junit.framework.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
public class FAQAccordionServletTest {
    private final AemContext ctx = new AemContext();
    @Mock
    PageManager pageManager;
    @Mock
    Page page;
    FAQAccordionModelImpl fAQAccordionModelImpl;
    @Mock
    ModelFactory modelFactory;
    Resource resource;
    @Mock
    private SlingHttpServletRequest request;
    @Mock
    private SlingHttpServletResponse response;
    @InjectMocks
    private FAQAccordionServlet fAQAccordionServlet = new FAQAccordionServlet();

    @BeforeEach
    public void setup() throws Exception {
        ctx.load().json("/com/talentacademy/core/models/impl/faqAccordion.json", "/content");
        ctx.requestPathInfo().setSelectorString("faq");
        ctx.registerService(ModelFactory.class);
        resource = ctx.resourceResolver().getResource("/content/faqaccordion");
        fAQAccordionServlet.modelFactory = modelFactory;
        fAQAccordionModelImpl = resource.adaptTo(FAQAccordionModelImpl.class);
    }
    
    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.FAQAccordionServlet#doGet(SlingHttpServletRequest, SlingHttpServletResponse) ()}
     */
    @Test
    public void testDoGet() throws IOException, ServletException {
        when(request.getResourceResolver()).thenReturn(ctx.resourceResolver());
        ctx.registerAdapter(ResourceResolver.class, PageManager.class, pageManager);
        when(request.getResource()).thenReturn(resource);
        when(pageManager.getContainingPage(request.getResource())).thenReturn(page);
        when(modelFactory.getModelFromWrappedRequest(request, null, FAQAccordionModelImpl.class)).thenReturn(fAQAccordionModelImpl);
        StringWriter out = new StringWriter();
        PrintWriter printWriter = new PrintWriter(out);
        when(response.getWriter()).thenReturn(printWriter);
        fAQAccordionServlet.doGet(request, response);
        assertNotNull(printWriter);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.FAQAccordionServlet#getOffSetValue(SlingHttpServletRequest, int) ()}
     */
    @Test
    void testGetOffsetValue() {
        int value = fAQAccordionServlet.getOffSetValue(request, 4);
        assertEquals(4, value);
    }
}
