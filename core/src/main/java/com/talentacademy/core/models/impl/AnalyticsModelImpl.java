package com.talentacademy.core.models.impl;

import com.talentacademy.core.models.AnalyticsModel;
import com.talentacademy.core.utils.ApplicationUtil;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, adapters = AnalyticsModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AnalyticsModelImpl implements AnalyticsModel {

	@RequestAttribute(name = "title")
	private String title;
	
	/**
	 * @return component title
	 */
	@Override
	public String getTitle() {
		return ApplicationUtil.getLowerCaseStringWithHyphen(title);
	}
}
