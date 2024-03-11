package com.talentacademy.core.models.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.talentacademy.core.models.CarouselContainerModel;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, adapters = CarouselContainerModel.class,
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
		resourceType = CarouselContainerModelImpl.RESOURCE_TYPE)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class CarouselContainerModelImpl implements CarouselContainerModel {

	public static final String RESOURCE_TYPE = "talentacademy/components/content/carouselcontainer";

	@ValueMapValue
	private String caption;

	@ValueMapValue
	private String carouselTitle;

	@ValueMapValue
	private String carouselDescription;

	@ValueMapValue
	private String buttonLabel;

	@ValueMapValue
	private String buttonLink;

	@Self
	private SlingHttpServletRequest request;

	@ValueMapValue
	private String carouselType;

	@ValueMapValue
	private int noofields;

	private List<Integer> list;

	@ValueMapValue
	private String openInNewTab;

	/**
	 * Initializes the list
	 */
	@PostConstruct
	private void init() {
		list = new ArrayList<>();
		if (noofields > 0) {
			for (int i = 0; i < noofields; i++) {
				list.add(i);
			}
		}
	}

	/**
	 * @return The list
	 */
	@Override
	public List<Integer> getList() {
		return Collections.unmodifiableList(list);
	}

	/**
	 * @return The caption
	 */
	@Override
	public String getCaption() {
		return caption;
	}

	/**
	 * @return The carouselTitle
	 */
	@Override
	public String getCarouselTitle() {
		return carouselTitle;
	}

	/**
	 * @return The carouselDescription
	 */
	@Override
	public String getCarouselDescription() {
		return carouselDescription;
	}

	/**
	 * @return The buttonLabel
	 */
	@Override
	public String getButtonLabel() {
		return buttonLabel;
	}

	/**
	 * @return The buttonLink
	 */
	@Override
	public String getButtonLink() {
		return request.getResourceResolver().map(buttonLink);
	}

	/**
	 * @return The carouselType
	 */
	@Override
	public String getCarouselType() {
		return carouselType;
	}

	/**
	 * @return The noofields
	 */
	@Override
	public int getNumberOfFields() {
		return noofields;
	}

	/**
	 * @return The openInNewTab
	 */
	@Override
	public String getOpenInNewTab() {
		return openInNewTab;
	}
	
}
