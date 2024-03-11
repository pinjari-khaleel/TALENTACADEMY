package com.talentacademy.core.models.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.talentacademy.core.beans.GridListing;
import com.talentacademy.core.models.CareerCardsListingModel;
import com.talentacademy.core.models.FilterHelper;
import com.talentacademy.core.services.GridListingService;

@Model(adaptables = { Resource.class, SlingHttpServletRequest.class },
		adapters = CareerCardsListingModel.class,
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
		resourceType = CareerCardsListingModelImpl.RESOURCE_TYPE)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class CareerCardsListingModelImpl implements CareerCardsListingModel {

	public static final String RESOURCE_TYPE = "talentacademy/components/content/careercardslisting";

	@ValueMapValue
	private int loadMoreLimit;

	@ValueMapValue
	private String rootDetailsPath;

	@ValueMapValue
	private String badgesEarned;

	@ValueMapValue
	private String knowMoreCtaLabel;

	@ValueMapValue
	private String loadMoreCtaLabel;

	@ChildResource
	private List<FilterHelper> multifieldvalues;

	@ValueMapValue
	private String applyFilterLabel;

	@ValueMapValue
	private String clearFilterLabel;

	@ValueMapValue
	private String filtersLabel;

	@SlingObject
	private ResourceResolver resourceResolver;

	@ScriptVariable
	private Page currentPage;

	@OSGiService
	private GridListingService gridListingService;

	private Map<String[], Map<String, String>> idTagData = new LinkedHashMap<>();

	private Map<String, Map<String, String>> tagData = new LinkedHashMap<>();

	/**
	 * Initiate the calls the getData method by passing the dialog value of filters
	 */
	@PostConstruct
	public void init() {

		if (multifieldvalues != null && !multifieldvalues.isEmpty()) {
			getData(multifieldvalues);
		}
	}

	/**
	 * @return The load more limit number which is confugured in the dialog
	 */
	@Override
	public int getLoadMoreLimit() {
		return loadMoreLimit;
	}

	/**
	 * @return The root career details page path which is confugured in the dialog
	 */
	@Override
	public String getRootDetailsPath() {
		return rootDetailsPath;
	}

	/**
	 * @return The badge earned label to be show in the cards
	 */
	@Override
	public String getBadgesEarned() {
		return badgesEarned;
	}

	/**
	 * @return The know more CTA label
	 */
	@Override
	public String getKnowMoreCtaLabel() {
		return knowMoreCtaLabel;
	}

	/**
	 * @return The load more CTA label
	 */
	@Override
	public String getLoadMoreCtaLabel() {
		return loadMoreCtaLabel;
	}

	/**
	 * @return The Apply filter label to show the button text
	 */
	@Override
	public String getApplyFilterLabel() {
		return applyFilterLabel;
	}

	/**
	 * @return The clear filter label to show the button text
	 */
	@Override
	public String getClearFilterLabel() {
		return clearFilterLabel;
	}

	/**
	 * @return The filters label wwhich will be visible on mobile device
	 */
	@Override
	public String getFiltersLabel() {
		return filtersLabel;
	}

	/**
	 * Creates a Map of String array and Map of Strings for the filter categery and
	 * their filters
	 */
	public void getData(List<FilterHelper> tagFields) {

		TagManager tagManager = resourceResolver.adaptTo(TagManager.class);

		for (int i = 0; i < tagFields.size(); i++) {

			Map<String, String> tagMap = new LinkedHashMap<>();
			if (tagManager != null) {

				Tag namespace = tagManager.resolve(tagFields.get(i).getTagLink());
				if (namespace != null) {

					Iterator<Tag> tags = namespace.listChildren();
					if (tags != null) {

						while (tags.hasNext()) {

							Tag tag = tags.next();
							tagMap.put(tag.getTitle(currentPage.getLanguage()), tag.getTagID());

						}
						String[] tagIdName = new String[2];
						tagIdName[0] = tagFields.get(i).getTagName();
						tagIdName[1] = tagFields.get(i).getTagName().replace(" ", "").trim();
						tagData.put(tagFields.get(i).getTagName(), tagMap);
						idTagData.put(tagIdName, tagMap);
					}
				}
			}
		}
	}

	/**
	 * @returns The Map of String array and Map of Strings for the filter categery
	 *          and their filters
	 */
	public Map<String[], Map<String, String>> getIdTagData() {
		return idTagData;
	}

	/**
	 * @returns The list of GridListing data which is formed in grid listing service
	 */
	public List<GridListing> getCareerDetailsdata() {
		return Collections.unmodifiableList(gridListingService.getCareerDetailsdata(resourceResolver, rootDetailsPath));
	}

}
