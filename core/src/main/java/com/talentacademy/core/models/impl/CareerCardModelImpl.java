package com.talentacademy.core.models.impl;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.talentacademy.core.models.CareerCardModel;

@Model(adaptables = { Resource.class, SlingHttpServletRequest.class }, adapters = CareerCardModel.class, 
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
		resourceType = CareerCardModelImpl.RESOURCE_TYPE)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME,
       extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class CareerCardModelImpl implements CareerCardModel {

	public static final String RESOURCE_TYPE = "talentacademy/components/content/careercard";

	@ValueMapValue
	private String jobTitle;

	@ValueMapValue
	private String careerLabel;

	@ValueMapValue
	private String tileImage;

	@ValueMapValue
	private String altTextForImage;

	@ValueMapValue
	private String recruitmentStatus;

	@ValueMapValue
	private String jobRoleType;

	@ValueMapValue
	private String experienceLevel;

	@ValueMapValue
	private String jobOpening;

	@ValueMapValue
	private String expectedDemand;

	@ValueMapValue
	private String badgesWeRecommend;

	@ChildResource
	private Resource badgeList;

	@ValueMapValue
	private String knowMoreCTALabel;

	@ValueMapValue
	private String knowMoreCTALink;

	@ValueMapValue
	private String openInNewTab;

	/**
	 * @return The jobTitle
	 */
	@Override
	public String getJobTitle() {
		return jobTitle;
	}

	/**
	 * @return The careerLabel
	 */
	@Override
	public String getCareerLabel() {
		return careerLabel;
	}

	/**
	 * @return The tileImage
	 */
	@Override
	public String getTileImage() {
		return tileImage;
	}

	/**
	 * @return The altTextForImage
	 */
	@Override
	public String getAltTextForImage() {
		return altTextForImage;
	}

	/**
	 * @return The recruitmentStatus
	 */
	@Override
	public String getRecruitmentStatus() {
		return recruitmentStatus;
	}

	/**
	 * @return The jobRoleType
	 */
	@Override
	public String getJobRoleType() {
		return jobRoleType;
	}

	/**
	 * @return The experienceLevel
	 */
	@Override
	public String getExperienceLevel() {
		return experienceLevel;
	}

	/**
	 * @return The jobOpening
	 */
	@Override
	public String getJobOpening() {
		return jobOpening;
	}

	/**
	 * @return The expectedDemand
	 */
	@Override
	public String getExpectedDemand() {
		return expectedDemand;
	}

	/**
	 * @return badgesWeRecommend
	 */
	@Override
	public String getBadgesWeRecommend() {
		return badgesWeRecommend;
	}

	/**
	 * @return The badgeList
	 */
	@Override
	public Resource getBadgeList() {
		return badgeList;
	}

	/**
	 * @return The knowMoreCTALabel
	 */
	@Override
	public String getKnowMoreCTALabel() {
		return knowMoreCTALabel;
	}

	/**
	 * @return The knowMoreCTALink
	 */
	@Override
	public String getKnowMoreCTALink() {
		return knowMoreCTALink;
	}

	/**
	 * @return The openInNewTab
	 */
	@Override
	public String getOpenInNewTab() {
		return openInNewTab;
	}

}
