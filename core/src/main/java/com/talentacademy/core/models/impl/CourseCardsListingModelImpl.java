package com.talentacademy.core.models.impl;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.wcm.api.Page;
import com.talentacademy.core.beans.CourseDetails;
import com.talentacademy.core.beans.GridListing;
import com.talentacademy.core.constants.ALMConstants;
import com.talentacademy.core.models.CourseCardsListingModel;
import com.talentacademy.core.services.CourseDetailsService;
import com.talentacademy.core.services.GridListingService;
import com.talentacademy.core.services.InvokeAPIService;
import com.talentacademy.core.utils.ApplicationUtil;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, adapters = CourseCardsListingModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class CourseCardsListingModelImpl implements CourseCardsListingModel {

	@ValueMapValue
	private String title;

	@ValueMapValue
	private String description;

	@ValueMapValue
	private String listingType;

	@ValueMapValue
	private String cursorIdValue;

	@ValueMapValue
	private String catalogId;

	@ValueMapValue
	private String rootDetailsPath;

	@ValueMapValue
	private int loadMoreLimit;

	@ValueMapValue
	private String badgesEarned;

	@ValueMapValue
	private String loadMoreCtaLabel;

	@ValueMapValue
	private String primaryButtonLabel;

	@ValueMapValue
	private String primaryButtonLink;

	@ValueMapValue
	private String primaryButtonTarget;

	@ValueMapValue
	private String secondaryButtonLabel;

	@ValueMapValue
	private String secondaryButtonLink;

	@ValueMapValue
	private String secondaryButtonTarget;

	@OSGiService
	private GridListingService gridListingService;

	@SlingObject
	private ResourceResolver resourceResolver;

	@OSGiService
	CourseDetailsService courseDetailsService;

	@OSGiService
	InvokeAPIService invokeAPIService;

	@Inject
	Page currentPage;

	/**
	 * @return String of the course listing component title
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/**
	 * @return String of the course listing component description
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * @return The root details path of the course details pages
	 */
	@Override
	public String getRootDetailsPath() {
		rootDetailsPath = rootDetailsPath == null ? currentPage.getPath() : rootDetailsPath;
		return resourceResolver.map(rootDetailsPath);
	}

	/**
	 * @return The load more limit number which is confugured in the dialog
	 */
	@Override
	public int getLoadMoreLimit() {
		return loadMoreLimit;
	}

	/**
	 * @return The badge earned label to be show in the cards
	 */
	@Override
	public String getBadgesEarned() {
		return badgesEarned;
	}

	/**
	 * @return The load more CTA labels
	 */
	@Override
	public String getLoadMoreCtaLabel() {
		return loadMoreCtaLabel;
	}

	/**
	 * @return The primary CTA button label
	 */
	@Override
	public String getPrimaryButtonLabel() {
		return primaryButtonLabel;
	}

	/**
	 * @return The seconday CTA button label
	 */
	@Override
	public String getSecondaryButtonLabel() {
		return secondaryButtonLabel;
	}

	/**
	 * @return The secondaryButtonLink
	 */
	@Override
	public String getSecondaryButtonLink() {
		return ApplicationUtil.getValidURL(secondaryButtonLink, resourceResolver);
	}

	/**
	 * @return The secondaryButtonTarget
	 */
	@Override
	public String getSecondaryButtonTarget() {
		return secondaryButtonTarget;
	}

	/**
	 * @returns The list of GridListing data which is formed in grid listing service
	 */
	public List<GridListing> getCourseDetailsdata() {
		return Collections.unmodifiableList(gridListingService.getCourseDetailsdata(resourceResolver, rootDetailsPath));
	}

	/**
	 * @return The link for primary button
	 */
	@Override
	public String getPrimaryButtonLink() {
		return ApplicationUtil.getValidURL(primaryButtonLink, resourceResolver);
	}

	/**
	 * @return The primaryButtonTarget
	 */
	@Override
	public String getPrimaryButtonTarget() {
		return primaryButtonTarget;
	}

	/**
	 * @return The listingType
	 */
	@Override
	public String getListingType() {
		return listingType;
	}

	/**
	 * @return The catalogId
	 */
	@Override
	public String getCatalogId() {
		return catalogId;
	}

	/**
	 * get course details from ALM.
	 * 
	 * @return courseDetails List
	 */
	@Override
	public List<CourseDetails> getCourseDetails() throws URISyntaxException {

		String locale = ApplicationUtil.getCurrentPageLocale(currentPage.getPath());
		List<NameValuePair> nvps = new ArrayList<>();
		nvps.add(new BasicNameValuePair(ALMConstants.PAGE_LIMIT, Integer.toString(loadMoreLimit)));
		nvps.add(new BasicNameValuePair(ALMConstants.SORT, ALMConstants.DATE));
		nvps.add(new BasicNameValuePair(ALMConstants.CATALOG_ID, catalogId));
		nvps.add(new BasicNameValuePair(ALMConstants.LO_TYPE_FILTER, ALMConstants.LO_TYPE_FILTER_PARAM));
		nvps.add(new BasicNameValuePair(ALMConstants.INCLUDE, ALMConstants.INCLUDED_SKILLS_INSTANCES_BADGE));

		String responseStr = invokeAPIService.getMultipleCourseResponse(nvps);
		cursorIdValue = courseDetailsService.getCursorId(responseStr);

		return courseDetailsService.getCourseDetails(responseStr, false, locale);
	}

	/**
	 * Get current page locale.
	 *
	 * @return locale
	 */
	public String getCurrentPageLocale() {
		return ApplicationUtil.getCurrentPageLocale(currentPage.getPath());
	}

	/**
	 * Get cursor Value
	 * @return cursorIdValue
	 */
	public String getCursorIdValue() {
		return cursorIdValue;
	}

	/**
	 * Set cursor Value
	 * 
	 * @param cursorIdValue
	 */
	public void setCursorIdValue(String cursorIdValue) {
		this.cursorIdValue = cursorIdValue;
	}

}