package com.talentacademy.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.wcm.api.Page;
import com.talentacademy.core.models.SiteMapModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.ExporterOption;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

@Model(adaptables = { SlingHttpServletRequest.class, Resource.class }, adapters = {
		SiteMapModel.class }, resourceType = "talentacademy/components/content/sitemap", defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, selector = ExporterConstants.SLING_MODEL_SELECTOR, extensions = ExporterConstants.SLING_MODEL_EXTENSION, options = {
		@ExporterOption(name = "MapperFeature.SORT_PROPERTIES_ALPHABETICALLY", value = "true"),
		@ExporterOption(name = "SerializationFeature.WRITE_DATES_AS_TIMESTAMPS", value = "true") })
public class SiteMapModelImpl implements SiteMapModel {

	@Self
	private SlingHttpServletRequest request;

	@ScriptVariable
	private ValueMap properties;

	@ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
	private String rootPage;

	@ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
	private String heading;

	private Page pagePath;

	private static final Logger LOGGER = LoggerFactory.getLogger(SiteMapModelImpl.class);

	@PostConstruct
	private void initModel() {

		LOGGER.debug("Inside Site Map init method");
		if (StringUtils.isNotBlank(getRootPage())) {
			pagePath = request.getResourceResolver().getResource(getRootPage()).adaptTo(Page.class);
		}
		LOGGER.debug("Exiting Site Map initModel method");
	}

	@Override
	public Page getPagePath() {
		return pagePath;
	}

	@Override
	public String getRootPage() {
		return rootPage;
	}

	@Override
	public String getHeading() {
		return heading;
	}
}
