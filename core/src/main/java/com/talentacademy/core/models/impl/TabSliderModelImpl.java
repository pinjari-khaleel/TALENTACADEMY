package com.talentacademy.core.models.impl;

import com.talentacademy.core.beans.TabSliderDetails;
import com.talentacademy.core.models.TabSliderModel;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation class of Tab Slider Model
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, adapters = TabSliderModel.class, resourceType = TabSliderModelImpl.RESOURCE_TYPE, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TabSliderModelImpl implements TabSliderModel {

	public static final String RESOURCE_TYPE = "talentacademy/components/content/tabslider";

	@ValueMapValue
	private String header;

	@ChildResource
	private List<TabSliderDetails> tabSliderDetails;

	/**
	 * @return Header of the Tab Slider.
	 */
	@Override
	public String getHeader() {
		return header;
	}

	/**
	 * @return List of Tab Slider Details.
	 */
	@Override
	public List<TabSliderDetails> getTabSliderDetails() {
		return new ArrayList<>(tabSliderDetails);
	}
}