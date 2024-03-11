package com.talentacademy.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import com.talentacademy.core.utils.ApplicationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.talentacademy.core.beans.GridListing;
import com.talentacademy.core.beans.GridListingData;
import com.talentacademy.core.constants.ApplicationConstants;
import com.talentacademy.core.services.GridListingService;

@Component(service = Servlet.class, immediate = true, property = {
        Constants.SERVICE_DESCRIPTION + "=" + CareerCardListingServlet.SERVICE_DESCRIPTION,
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + CareerCardListingServlet.RESOURCE_TYPE,
        ServletResolverConstants.SLING_SERVLET_SELECTORS + "=" + CareerCardListingServlet.SELECTORS })

public class CareerCardListingServlet extends org.apache.sling.api.servlets.SlingSafeMethodsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(CareerCardListingServlet.class);
    private static final long serialVersionUID = 1L;

    protected static final String SERVICE_DESCRIPTION = "Servlet to return the card listing data";
    protected static final String RESOURCE_TYPE = "talentacademy/components/structure/page";
    protected static final String SELECTORS = "careerlisting";
    protected static final String FILTERS = "filters";

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

        String filters = request.getParameter(FILTERS);        
        String cardsPerLoad = request.getParameter(ApplicationConstants.CARDS_PER_LOAD);
        String offset = request.getParameter(ApplicationConstants.OFFSET);

        GridListingData gridListingData = new GridListingData();
        List<GridListing> gridListing = new ArrayList<>();

        int offsetValue = ApplicationUtil.getOffSetValue(cardsPerLoad, offset);
        String pagePath = request.getResource().getPath();

        try {

            gridListing = gridListingService.getCareerDetailsdata(request.getResourceResolver(), pagePath);

            if (StringUtils.isBlank(filters)) {

                gridListingData.setTotalCards(String.valueOf(gridListing.size()));

                int upperIndex = offsetValue <= gridListing.size() ? offsetValue : gridListing.size();
                gridListing = gridListing.subList(0, upperIndex);

                gridListingData.setCardList(gridListing);

            } else {

                List<GridListing> filteredGridList = getCardData(filters, gridListing);

                gridListingData.setTotalCards(String.valueOf(filteredGridList.size()));

                int upperIndex = offsetValue <= filteredGridList.size() ? offsetValue : filteredGridList.size();
                filteredGridList = filteredGridList.subList(0, upperIndex);

                gridListingData.setCardList(filteredGridList);

            }

            response.setCharacterEncoding(ApplicationConstants.UTF_8);
            response.setContentType(ApplicationConstants.APPLICATION_JSON);

            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            response.getWriter().write(gson.toJson(gridListingData));

        } catch (IOException ex) {
            LOGGER.error("Exception is generated from Career Listing Servlet: ", ex);

        } finally {
            response.getWriter().flush();
            response.getWriter().close();

        }
    }

    /**
     * @param filters
     * @param gridListing
     * @return List<GridListing>
     */
    public List<GridListing> getCardData(String filters, List<GridListing> gridListing) {

        List<GridListing> filteredListData = new ArrayList<>();
        List<String> filterTag = Arrays.asList(filters.split(","));
        List<String> uniqueFilters = new ArrayList<>();

        for (String tag : filterTag) {
            uniqueFilters.add(tag.substring(0, tag.lastIndexOf("/")));
        }
        uniqueFilters = new ArrayList<>(new HashSet<>(uniqueFilters));

        for (GridListing listing : gridListing) {
            List<String> tags = Arrays.asList(listing.getFilterTags());
            int count = 0;
            for (String tag : tags) {
                if ((filterTag.contains(tag))) {
                    count += 1;
                }
            }

            if (uniqueFilters.size() == count) {
                filteredListData.add(listing);
            }
        }

        return Collections.unmodifiableList(filteredListData);
    }

}
