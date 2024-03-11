package com.talentacademy.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.talentacademy.core.models.HeaderModel;
import com.talentacademy.core.utils.ApplicationUtil;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, adapters = HeaderModel.class, resourceType = HeaderModelImpl.RESOURCE_TYPE, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)

public class HeaderModelImpl implements HeaderModel {

	public static final String RESOURCE_TYPE = "talentacademy/components/structure/header";

	@SlingObject
	ResourceResolver resolver;

	@ValueMapValue
	public String logoUrl;

	@ValueMapValue
	public String logoLink;

	@ValueMapValue
	public String logoAltText;

	@ValueMapValue
	public String lobbyLabel;

	@ValueMapValue
	public String lobbyUrl;

	/**
	 * @return the logoUrl
	 */
	@Override
	public String getLogoUrl() {
		return logoUrl;
	}

	/**
	 * @return the logoLink and lobbyUrl
	 */
	@PostConstruct
	public void init() {
		logoLink = ApplicationUtil.getValidURL(logoLink, resolver);
		lobbyUrl = ApplicationUtil.getValidURL(lobbyUrl, resolver);
	}

	/**
	 * @return the logoLink
	 */
	@Override
	public String getLogoLink() {
		return logoLink;
	}

	/**
	 * @return the logoAltText
	 */
	@Override
	public String getLogoAltText() {
		return logoAltText;
	}

	/**
	 * @return the lobbyLabel
	 */
	@Override
	public String getLobbyLabel() {
		return lobbyLabel;
	}

	/**
	 * @return the lobbyUrl
	 */
	@Override
	public String getLobbyUrl() {
		return lobbyUrl;
	}

}