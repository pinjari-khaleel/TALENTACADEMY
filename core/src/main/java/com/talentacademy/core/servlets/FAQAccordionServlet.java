package com.talentacademy.core.servlets;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.talentacademy.core.constants.ApplicationConstants;
import com.talentacademy.core.models.impl.FAQAccordionModelImpl;
import com.talentacademy.core.utils.ApplicationUtil;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.models.factory.ModelFactory;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Represent Filter and Load more functionality for FAQ Accordion
 */
@Component(service = {Servlet.class})
@SlingServletResourceTypes(resourceTypes = FAQAccordionServlet.RESOURCE_TYPE, methods = HttpConstants.METHOD_GET, selectors = FAQAccordionServlet.SELECTORS)
@ServiceDescription(FAQAccordionServlet.SERVICE_DESCRIPTION)
public class FAQAccordionServlet extends SlingSafeMethodsServlet {
    public static final String SERVICE_DESCRIPTION = "This Servlet is responsible for FAQ Accordion's Filter and Load more functionality.";

    public static final String RESOURCE_TYPE = "talentacademy/components/structure/page";
    protected static final String SELECTORS = "faq";
    protected static final String ROOT_PATH = "/jcr:content/root/container/faqaccordion";
    private static final Logger LOGGER = LoggerFactory.getLogger(FAQAccordionServlet.class);

    @Reference
    public transient ModelFactory modelFactory;

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            ResourceResolver resourceResolver = request.getResourceResolver();
            PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
            Page page = pageManager.getContainingPage(request.getResource());
            String rootPath = page.getPath() + ROOT_PATH;
            Resource resource = resourceResolver.getResource(rootPath);
            FAQAccordionModelImpl faqData = modelFactory.getModelFromWrappedRequest(request, resource, FAQAccordionModelImpl.class);
            int loadMoreLimit = faqData.getLoadMoreLimit() == 0 ? 1 : (faqData.getLoadMoreLimit());
            int offsetValue = getOffSetValue(request, loadMoreLimit);
            int upperIndex = Math.min(offsetValue, faqData.getFaq().size());
            faqData.setTotalCount(faqData.getFaq().size());
            faqData.setFaq(faqData.getFaq().subList(0, upperIndex));

            response.setCharacterEncoding(ApplicationConstants.UTF_8);
            response.setContentType(ApplicationConstants.APPLICATION_JSON);

            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            response.getWriter().write(gson.toJson(faqData));

        } catch (IOException ex) {
            LOGGER.error("Exception is generated from FAQ Accordion Servlet: ", ex);
        } finally {
            response.getWriter().flush();
            response.getWriter().close();
        }
    }

    /**
     * @param request
     * @param loadMoreLimit
     * @return offsetvalue
     */
    public int getOffSetValue(SlingHttpServletRequest request, int loadMoreLimit) {
        String offset = request.getParameter(ApplicationConstants.OFFSET);
        if (!ApplicationUtil.isNumeric(offset) || Integer.parseInt(offset) <= 1) {
            offset = "1";
        }
        int offsetValue = Math.max(Integer.parseInt(offset), 1);
        offsetValue = offsetValue * loadMoreLimit;
        return offsetValue;
    }
}
