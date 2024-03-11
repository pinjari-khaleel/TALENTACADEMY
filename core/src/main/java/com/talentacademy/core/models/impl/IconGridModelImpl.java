package com.talentacademy.core.models.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.talentacademy.core.models.IconGridList;
import com.talentacademy.core.models.IconGridModel;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, adapters = IconGridModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = IconGridModelImpl.RESOURCE_TYPE)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class IconGridModelImpl implements IconGridModel {

	public static final String RESOURCE_TYPE = "talentacademy/components/content/icongrid";

	@ValueMapValue
	private String gridType;

	@ValueMapValue
	private String caption;

	@ValueMapValue
	private String iconGridTitle;

	@ValueMapValue
	private String iconGridDescription;

	@ChildResource
	private List<IconGridList> gridIconsList;

	/**
	 * @return the gridType
	 */
	@Override
	public String getGridType() {
		return gridType;
	}

	/**
	 * @return the caption
	 */
	@Override
	public String getCaption() {
		return caption;
	}

	/**
	 * @return the iconGridTitle
	 */
	@Override
	public String getIconGridTitle() {
		return iconGridTitle;
	}

	/**
	 * @return the iconGridDescription
	 */
	@Override
	public String getIconGridDescription() {
		return iconGridDescription;
	}

	/**
	 * @return the gridIconsList
	 */
	@Override
	public List<IconGridList> getGridIconsList() {
		return new ArrayList<>(gridIconsList);
	}
}
