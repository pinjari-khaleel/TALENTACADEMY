package com.talentacademy.core.models.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.talentacademy.core.models.CourseCardModel;
import com.talentacademy.core.models.SocialMediaLinks;
import com.talentacademy.core.utils.ApplicationUtil;

@Model(adaptables = Resource.class, adapters = CourseCardModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = CourseCardModelImpl.RESOURCE_TYPE)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class CourseCardModelImpl implements CourseCardModel {

	public static final String RESOURCE_TYPE = "talentacademy/components/content/coursecard";

	@SlingObject
	ResourceResolver resolver;

	@ValueMapValue
	private String carouselImage;

	@ValueMapValue
	private String altTextForImage;

	@ValueMapValue
	private String carouselImageLabelText;

	@ValueMapValue
	private String headingText;

	@ValueMapValue
	private String coursesCount;

	@ValueMapValue
	private String badgesTitle;

	@ChildResource
	private List<SocialMediaLinks> badgesList;

	@ValueMapValue
	private String primaryButtonLink;

	@ValueMapValue
	private String primaryButtonLabel;

	@ValueMapValue
	private String primaryButtonTarget;

	@ValueMapValue
	private String secondaryButtonLink;

	@ValueMapValue
	private String secondaryButtonLabel;

	@ValueMapValue
	private String secondaryButtonTarget;

	@ValueMapValue
	private String learningPathwayID;

	/**
	 * initialising primaryButtonLink and secondaryButtonLink using the getValidURL
	 * method
	 */
	@PostConstruct
	public void init() {
		primaryButtonLink = ApplicationUtil.getValidURL(primaryButtonLink, resolver);
		secondaryButtonLink = ApplicationUtil.getValidURL(secondaryButtonLink, resolver);
	}

	/**
	 * @return The carouselImage
	 */
	@Override
	public String getCarouselImage() {
		return carouselImage;
	}

	/**
	 * @return The altTextForImage
	 */
	@Override
	public String getAltTextForImage() {
		return altTextForImage;
	}

	/**
	 * @return The carouselImageLabelText
	 */
	@Override
	public String getCarouselImageLabelText() {
		return carouselImageLabelText;
	}

	/**
	 * @return The headingText
	 */
	@Override
	public String getHeadingText() {
		return headingText;
	}

	/**
	 * @return The coursesCount
	 */
	public String getCoursesCount() {
		return coursesCount;
	}

	/**
	 * @return The badgesTitle
	 */
	@Override
	public String getBadgesTitle() {
		return badgesTitle;
	}

	/**
	 * @return The badgesList
	 */
	@Override
	public List<SocialMediaLinks> getBadgesList() {
		return new ArrayList<>(badgesList);
	}

	/**
	 * @return The primaryButtonLink
	 */
	@Override
	public String getPrimaryButtonLink() {
		return primaryButtonLink;
	}

	/**
	 * @return The primaryButtonLabel
	 */
	@Override
	public String getPrimaryButtonLabel() {
		return primaryButtonLabel;
	}

	/**
	 * @return The secondaryButtonLink
	 */
	@Override
	public String getSecondaryButtonLink() {
		return secondaryButtonLink;
	}

	/**
	 * @return The secondaryButtonLabel
	 */
	@Override
	public String getSecondaryButtonLabel() {
		return secondaryButtonLabel;
	}

	/**
	 * @return The primaryButtonTarget
	 */
	@Override
	public String getPrimaryButtonTarget() {
		return primaryButtonTarget;
	}

	/**
	 * @return The secondaryButtonTarget
	 */
	@Override
	public String getSecondaryButtonTarget() {
		return secondaryButtonTarget;
	}

	/**
	 * @return The learningPathwayID
	 */
	@Override
	public String getLearningPathwayID() {
		return learningPathwayID;
	}

}
