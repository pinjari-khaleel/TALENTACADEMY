package com.talentacademy.core.servlets;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.talentacademy.core.beans.CourseDetails;
import com.talentacademy.core.beans.GridListing;
import com.talentacademy.core.beans.GridListingData;
import com.talentacademy.core.constants.ALMConstants;
import com.talentacademy.core.constants.ApplicationConstants;
import com.talentacademy.core.services.CourseDetailsService;
import com.talentacademy.core.services.GridListingService;
import com.talentacademy.core.services.InvokeAPIService;
import com.talentacademy.core.utils.ApplicationUtil;

@Component(service = Servlet.class, immediate = true, property = {
        Constants.SERVICE_DESCRIPTION + "=" + CourseCardListingServlet.SERVICE_DESCRIPTION,
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + CourseCardListingServlet.RESOURCE_TYPE,
        ServletResolverConstants.SLING_SERVLET_SELECTORS + "=" + CourseCardListingServlet.SELECTORS })

public class CourseCardListingServlet extends org.apache.sling.api.servlets.SlingSafeMethodsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseCardListingServlet.class);
    private static final long serialVersionUID = 1L;

    protected static final String SERVICE_DESCRIPTION = "Servlet to return the card listing data";
    protected static final String RESOURCE_TYPE = "talentacademy/components/structure/page";
    protected static final String SELECTORS = "courselisting";

    @Reference
    private transient GridListingService gridListingService;

    @Reference
    private transient CourseDetailsService courseDetailsService;

    @Reference
    private transient InvokeAPIService invokeAPIService;

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

        final String DYNAMIC_CARD_LIST = "dynamic";

        String cardsPerLoad = request.getParameter(ApplicationConstants.CARDS_PER_LOAD);
        String offset = request.getParameter(ApplicationConstants.OFFSET);
        String catalogId = request.getParameter(ApplicationConstants.CATALOG_ID);
        String courseListType = request.getParameter(ApplicationConstants.LIST_TYPE);
        String pageCursor = request.getParameter(ApplicationConstants.PAGECURSOR);

        GridListingData gridListingData = new GridListingData();
        List<GridListing> gridListing = new ArrayList<>();
        List<CourseDetails> dynamicCardsListing = new ArrayList<>();

        int offsetValue = ApplicationUtil.getOffSetValue(cardsPerLoad, offset);
        String pagePath = request.getResource().getPath();

        try {

            if (StringUtils.isNotBlank(courseListType) && StringUtils.equals(courseListType, DYNAMIC_CARD_LIST)) {

                String locale = ApplicationUtil.getCurrentPageLocale(pagePath);
                List<NameValuePair> nvps = new ArrayList<>();
                nvps.add(new BasicNameValuePair(ALMConstants.PAGE_LIMIT, cardsPerLoad));
                nvps.add(new BasicNameValuePair(ALMConstants.PAGE_CURSOR, pageCursor));
                nvps.add(new BasicNameValuePair(ALMConstants.SORT, ALMConstants.DATE));
                nvps.add(new BasicNameValuePair(ALMConstants.CATALOG_ID, catalogId));
                nvps.add(new BasicNameValuePair(ALMConstants.LO_TYPE_FILTER, ALMConstants.LO_TYPE_FILTER_PARAM));
                nvps.add(new BasicNameValuePair(ALMConstants.INCLUDE, ALMConstants.INCLUDED_SKILLS_INSTANCES_BADGE));

                String responseStr = invokeAPIService.getMultipleCourseResponse(nvps);
        		String cursorIdValue = courseDetailsService.getCursorId(responseStr);
                
                dynamicCardsListing = courseDetailsService.getCourseDetails(responseStr, false, locale);

                gridListingData.setCursorIdValue(cursorIdValue);
                gridListingData.setDynamicCardList(dynamicCardsListing);

            } else {

                gridListing = gridListingService.getCourseDetailsdata(request.getResourceResolver(), pagePath);

                gridListingData.setTotalCards(String.valueOf(gridListing.size()));

                int upperIndex = offsetValue <= gridListing.size() ? offsetValue : gridListing.size();
                gridListing = gridListing.subList(0, upperIndex);

                gridListingData.setCardList(gridListing);

            }          

            response.setCharacterEncoding(ApplicationConstants.UTF_8);
            response.setContentType(ApplicationConstants.APPLICATION_JSON);

            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            response.getWriter().write(gson.toJson(gridListingData));

        } catch (IOException ex) {
            LOGGER.error("Exception is generated from Course Listing Servlet for IOException: ", ex);

        } catch (URISyntaxException uriSyntaxException) {
            LOGGER.error("Exception is generated from Course Listing Servlet for URISyntaxException: ", uriSyntaxException);
            
        } finally {
            response.getWriter().flush();
            response.getWriter().close();

        }
    }

}