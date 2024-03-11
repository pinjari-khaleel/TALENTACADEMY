package com.talentacademy.core.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.talentacademy.core.utils.ApplicationUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.talentacademy.core.beans.GridListing;
import com.talentacademy.core.services.GridListingService;

@Component(service = GridListingService.class)
public class GridListingServiceImpl implements GridListingService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GridListingServiceImpl.class);

	private static final String CAREER_DETAILS_PATH = "/jcr:content/root/container/careerdetails";
	private static final String COURSE_DETAILS_PATH = "/jcr:content/root/container/coursedetails";
	private static final String PRESS_RELEASE_DETAILS_PATH = "/jcr:content/root/container/pressreleasedetails";

	private static final String JOB_TITLE = "jobTitle";
	private static final String SECTOR = "sector";
	private static final String RECRUITMENT_STATUS = "recruitmentStatus";
	private static final String EXPERIENCE_LEVEL = "experienceLevel";
	private static final String FILTER_TAGS = "filterTags";
	private static final String BADGELIST = "/badgeList";
	private static final String CARD_IMAGE = "cardImage";
	private static final String COURSE_TITLE = "courseTitle";
	private static final String COURSE_TAG = "courseTag";
	private static final String NUMBER_OF_COURSES = "numberOfCourses";
	private static final String LEARNING_PATHWAY_ID = "learningPathwayID";
	private static final String BADGES = "/badges";
	private static final String BADGE = "badge";
	private static final String COURSE_IMAGE_ALT_TEXT = "cardImageAltText";
	private static final String CREATED_DATE = "date";
	private static final String HEADING = "heading";
	private static final String DESCRIPTION = "shortDescription";

	/**
	 * This method is getting called from CareerCardListingServlet and
	 * CareerCardListingModel.
	 * 
	 * @return The list of component properties and child nodes of each career
	 *         details page
	 */
	@Override
	public List<GridListing> getCareerDetailsdata(ResourceResolver resolver, String rootDetailsPath) {

		LOGGER.debug("Get Career details data method call started.");

		List<GridListing> careerCardsList = new ArrayList<>();
		Resource resource = resolver.getResource(rootDetailsPath);
		PageManager pageManager = resolver.adaptTo(PageManager.class);

		if (resource != null) {
			Page page = pageManager.getContainingPage(resource);
			Iterator<Page> childPages = page.listChildren();

			while (childPages.hasNext()) {
				Page childPage = childPages.next();
				String childPagePath = childPage.getPath() + CAREER_DETAILS_PATH;
				String childPageUrl = ApplicationUtil.getValidURL(childPage.getPath(), resolver);
				Resource componentContent = resolver.getResource(childPagePath);

				careerCardsList.addAll(getCareerDetailsProperties(componentContent, childPagePath, childPage, resolver, childPageUrl));

			}
		}

		LOGGER.debug("Get Career details data method call ended.");

		return careerCardsList;
	}

	/**
	 * @return The list of component properties of career details page
	 */
	public List<GridListing> getCareerDetailsProperties(Resource componentContent, String childPagePath, Page childPage,
			ResourceResolver resolver, String childPageUrl) {

		LOGGER.debug("Get Career properties method call started.");

		List<GridListing> careerCardListingData = new ArrayList<>();
		GridListing careerDetailsProperties = new GridListing();

		if (componentContent != null) {
			ValueMap value = componentContent.getValueMap();
			careerDetailsProperties.setJobTitle(value.get(JOB_TITLE, String.class));
			careerDetailsProperties.setSector(value.get(SECTOR, String.class));
			careerDetailsProperties.setRecruitmentStatus(value.get(RECRUITMENT_STATUS, String.class));
			careerDetailsProperties.setExperienceLevel(value.get(EXPERIENCE_LEVEL, String.class));
			careerDetailsProperties.setPagePath(childPageUrl);
			careerDetailsProperties.setFilterTags(value.get(FILTER_TAGS, String[].class) == null ? 
						ArrayUtils.EMPTY_STRING_ARRAY : value.get(FILTER_TAGS, String[].class));

			Resource componentContentChild = resolver.getResource(childPagePath + BADGELIST);

			if (componentContentChild != null) {
				careerDetailsProperties.setBadges(getBadges(componentContentChild));

			}

			careerCardListingData.add(careerDetailsProperties);
		}

		LOGGER.debug("Get Career properties method call ended.");

		return careerCardListingData;
	}

	/**
	 * This method is getting called from CourseCardListingServlet and
	 * CourseCardListingModel.
	 * 
	 * @return The list of component properties and child nodes of each course
	 *         details page
	 */
	@Override
	public List<GridListing> getCourseDetailsdata(ResourceResolver resourceResolver, String rootDetailsPath) {

		LOGGER.debug("Get Course details data method call started.");

		List<GridListing> courseCardsList = new ArrayList<>();
		Resource resource = resourceResolver.getResource(rootDetailsPath);
		PageManager pageManager = resourceResolver.adaptTo(PageManager.class);

		if (resource != null) {
			Page page = pageManager.getContainingPage(resource);
			Iterator<Page> childPages = page.listChildren();

			while (childPages.hasNext()) {
				Page childPage = childPages.next();
				String childPagePath = childPage.getPath() + COURSE_DETAILS_PATH;
				String childPageUrl = ApplicationUtil.getValidURL(childPage.getPath(), resourceResolver);
				Resource componentContent = resourceResolver.getResource(childPagePath);

				courseCardsList.addAll(
						getCourseDetailsProperties(componentContent, childPagePath, childPage, resourceResolver, childPageUrl));
			}
		}

		LOGGER.debug("Get Course details data method call ended.");

		return courseCardsList;

	}

	/**
	 * @return The list of component properties of career details page
	 */
	public List<GridListing> getCourseDetailsProperties(Resource componentContent, String childPagePath, Page childPage,
			ResourceResolver resolver, String childPageUrl) {

		LOGGER.debug("Get Course details properties method call started.");

		List<GridListing> courseCardListingData = new ArrayList<>();
		GridListing courseDetailsProperties = new GridListing();

		if (componentContent != null) {
			ValueMap value = componentContent.getValueMap();
			courseDetailsProperties.setCardImage(value.get(CARD_IMAGE, String.class));
			courseDetailsProperties.setCardImageAltText(value.get(COURSE_IMAGE_ALT_TEXT, String.class));
			courseDetailsProperties.setCourseTag(value.get(COURSE_TAG, String.class));
			courseDetailsProperties.setCourseTitle(value.get(COURSE_TITLE, String.class));
			courseDetailsProperties.setNumberOfCourses(value.get(NUMBER_OF_COURSES, String.class));
			courseDetailsProperties.setPagePath(childPageUrl);
			courseDetailsProperties.setLearningPathwayID(value.get(LEARNING_PATHWAY_ID, String.class));

			Resource componentContentChild = resolver.getResource(childPagePath + BADGES);

			if (componentContentChild != null) {
				courseDetailsProperties.setBadges(getBadges(componentContentChild));

			}

			courseCardListingData.add(courseDetailsProperties);
		}

		LOGGER.debug("Get Course details properties method call ended.");

		return courseCardListingData;
	}

	/**
	 * This method is getting called from PressReleaseListingServlet and
	 * CareerCardListingModel.
	 *
	 * @return The list of component properties and child nodes of each press release detail page
	 */
	@Override
	public List<GridListing> getPressReleaseDetaildata(ResourceResolver resourceResolver, String rootDetailsPath) {

		LOGGER.debug("Get press release details data method call started.");

		List<GridListing> newsCardsList = new ArrayList<>();
		Resource resource = resourceResolver.getResource(rootDetailsPath);
		PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
		if (resource != null) {
			Page page = pageManager.getContainingPage(resource);
			Iterator<Page> childPages = page.listChildren();

			while (childPages.hasNext()) {
				Page childPage = childPages.next();
				String childPagePath = childPage.getPath() + PRESS_RELEASE_DETAILS_PATH;
				String childPageUrl = ApplicationUtil.getValidURL(childPage.getPath(), resourceResolver);
				Resource componentContent = resourceResolver.getResource(childPagePath);
				newsCardsList.addAll(getPressReleaseDetailsProperties(componentContent, childPage, childPageUrl));
			}
		}

		LOGGER.debug("Get Press release details data method call ended.");

		return newsCardsList;
	}

	/**
	 * @return The list of component properties of press release details page
	 */
	private List<GridListing> getPressReleaseDetailsProperties(Resource componentContent, Page childPage, String childPageUrl) {

		LOGGER.debug("Get Career properties method call started.");

		List<GridListing> newsCardListingData = new ArrayList<>();
		GridListing pressReleaseDetailsProperties = new GridListing();

		if (componentContent != null) {
			ValueMap value = componentContent.getValueMap();
			pressReleaseDetailsProperties.setPagePath(childPageUrl);
			pressReleaseDetailsProperties.setCreatedDate(value.get(CREATED_DATE, String.class));
			pressReleaseDetailsProperties.setCardImage(value.get(CARD_IMAGE, String.class));
			pressReleaseDetailsProperties.setCardImageAltText(value.get(COURSE_IMAGE_ALT_TEXT, String.class));
			pressReleaseDetailsProperties.setHeading(value.get(HEADING, String.class));
			pressReleaseDetailsProperties.setShortDescription(value.get(DESCRIPTION, String.class));
		}
		newsCardListingData.add(pressReleaseDetailsProperties);

		LOGGER.debug("Get Press release detail properties method call ended.");

		return newsCardListingData;
	}

	/**
	 * @return The list of badges from the component node based on the Resource
	 */
	public List<String> getBadges(Resource componentContentChild) {

		LOGGER.debug("Get badges method call started.");

		Iterator<Resource> badgeList = componentContentChild.listChildren();
		List<String> badges = new ArrayList<>();

		while (badgeList.hasNext()) {
			Resource badge = badgeList.next();

			if (badge != null) {
				ValueMap valMap = badge.getValueMap();
				badges.add(valMap.get(BADGE, String.class));

			}

		}

		LOGGER.debug("Get badges method call ended.");

		return Collections.unmodifiableList(badges);
	}

}
