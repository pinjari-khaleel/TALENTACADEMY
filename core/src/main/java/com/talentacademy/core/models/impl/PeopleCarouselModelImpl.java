package com.talentacademy.core.models.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.talentacademy.core.models.PeopleCarouselList;
import com.talentacademy.core.models.PeopleCarouselModel;

@Model(adaptables = Resource.class, adapters = PeopleCarouselModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = PeopleCarouselModelImpl.RESOURCE_TYPE)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class PeopleCarouselModelImpl implements PeopleCarouselModel {

	public static final String RESOURCE_TYPE = "talentacademy/components/content/peoplecarousel";

	@ValueMapValue
	private String caption;

	@ValueMapValue
	private String description;

	@ChildResource
	private List<PeopleCarouselList> peopleCarouselList;

	/**
	 * @return the caption
	 */
	@Override
	public String getCaption() {
		return caption;
	}

	/**
	 * @return the description
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * @return the peopleCarouselList
	 */
	@Override
	public List<PeopleCarouselList> getPeopleCarouselList() {
		return new ArrayList<>(peopleCarouselList);
	}
}
