package com.talentacademy.core.models.impl;

import com.day.cq.wcm.api.Page;

import com.talentacademy.core.constants.ApplicationConstants;
import com.talentacademy.core.models.PageConfigModel;
import com.talentacademy.core.utils.ApplicationUtil;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.day.cq.wcm.api.PageManager;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Model(adaptables = SlingHttpServletRequest.class, adapters = {
		PageConfigModel.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PageConfigModelImpl implements PageConfigModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(PageConfigModel.class);

	@Inject
	PageManager pageManager;

	@Inject
	Page currentPage;

	@SlingObject
	ResourceResolver resourceResolver;

	@Inject
	protected String pathReference;

	@Inject
	private String headerButtonLabel;

	@Inject
	private String headerButtonUrl;

	@Inject
	protected String languageDirection;

	@Inject
	protected String hideInSiteMap;

	@RequestAttribute(name = "id")
	private String id;

	/**
	 * @return the headerButtonLabel
	 */
	@Override
	public String getHeaderButtonLabel() {
		return headerButtonLabel;
	}

	/**
	 * @return the headerButtonLabel
	 */
	@Override
	public String getId() {
		return ApplicationUtil.getLowerCaseStringWithHyphen(id);
	}

	/**
	 * @return the headerButtonUrl
	 */
	@Override
	public String getHeaderButtonUrl() { return headerButtonUrl; }

	/**
	 * @return the hideInSiteMap
	 */
	@Override
	public String getHideInSitemap(){ return hideInSiteMap; }

	/**
	 * @return the languageDirection
	 */
	@Override
	public String getLanguageDirection() {
		languageDirection = currentPage.getAbsoluteParent(3).getProperties()
				.get(ApplicationConstants.LANGUAGE_DIRECTION, String.class);
		return languageDirection;
	}

	/**
	 * initialising the headerButtonLabel and headerButtonUrl
	 */
	@PostConstruct
	protected void init() {
		LOGGER.debug("PageConfigModel - Checking current Page Depth : Start");
		Page currentPage = pageManager.getPage(pathReference);

		if (currentPage != null && currentPage.getDepth() == 5) {
			headerButtonLabel = currentPage.getProperties().get(ApplicationConstants.HEADER_BUTTON_LABEL, String.class);
			headerButtonUrl = ApplicationUtil.getValidURL(
					currentPage.getProperties().get(ApplicationConstants.HEADER_BUTTON_URL, String.class),
					resourceResolver);
		} else if (currentPage != null && currentPage.getDepth() > 5) {
			int parentDepthLevel = currentPage.getDepth() - 5;
			if (currentPage.getParent(parentDepthLevel) != null) {
				headerButtonLabel = currentPage.getParent(parentDepthLevel).getProperties()
						.get(ApplicationConstants.HEADER_BUTTON_LABEL, String.class);
				headerButtonUrl = ApplicationUtil.getValidURL(currentPage.getParent(parentDepthLevel).getProperties()
						.get(ApplicationConstants.HEADER_BUTTON_URL, String.class), resourceResolver);
			}
		}
		LOGGER.debug("PageConfigModel - Checking current Page Depth : End");
	}

}
