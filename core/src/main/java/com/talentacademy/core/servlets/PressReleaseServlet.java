package com.talentacademy.core.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.talentacademy.core.beans.GridListing;
import com.talentacademy.core.beans.GridListingData;
import com.talentacademy.core.constants.ApplicationConstants;
import com.talentacademy.core.services.GridListingService;
import com.talentacademy.core.utils.ApplicationUtil;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component(service = Servlet.class, immediate = true, property = {
        Constants.SERVICE_DESCRIPTION + "=" + PressReleaseServlet.SERVICE_DESCRIPTION,
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + PressReleaseServlet.RESOURCE_TYPE,
        ServletResolverConstants.SLING_SERVLET_SELECTORS + "=" + PressReleaseServlet.SELECTORS })
public class PressReleaseServlet extends org.apache.sling.api.servlets.SlingSafeMethodsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(PressReleaseServlet.class);
    private static final long serialVersionUID = 1L;
    protected static final String SERVICE_DESCRIPTION = "Servlet to return the news card listing data";
    protected static final String RESOURCE_TYPE = "talentacademy/components/structure/page";
    protected static final String SELECTORS = "newscardlisting";
    @Reference
    private transient GridListingService gridListingService;

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

        String cardsPerLoad = request.getParameter(ApplicationConstants.CARDS_PER_LOAD);
        String offset = request.getParameter(ApplicationConstants.OFFSET);

        try {
            GridListingData gridListingData = new GridListingData();
            List<GridListing> gridListing = new ArrayList<>();
            int offsetValue = ApplicationUtil.getOffSetValue(cardsPerLoad, offset);
            String pagePath = request.getResource().getPath();
            gridListing = gridListingService.getPressReleaseDetaildata(request.getResourceResolver(), pagePath);
            gridListingData.setTotalCards(String.valueOf(gridListing.size()));
            int upperIndex = offsetValue <= gridListing.size() ? offsetValue : gridListing.size();
            gridListing = gridListing.subList(0, upperIndex);
            gridListingData.setCardList(gridListing);

            response.setCharacterEncoding(ApplicationConstants.UTF_8);
            response.setContentType(ApplicationConstants.APPLICATION_JSON);
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            response.getWriter().write(gson.toJson(gridListingData));
        }

        catch(IOException e){
            LOGGER.error("Exception is generated from News Card Listing Servlet: ", e);
        }

        finally {
            response.getWriter().flush();
            response.getWriter().close();
        }

    }
}
