package com.talentacademy.core.models.impl;

import static com.talentacademy.core.constants.ALMConstants.INCLUDE;
import static com.talentacademy.core.constants.ALMConstants.INCLUDED_SKILLS_INSTANCES_BADGE;
import static com.talentacademy.core.constants.ALMConstants.INC_RESOURCES;
import static com.talentacademy.core.constants.ALMConstants.INC_SKILLS;
import static com.talentacademy.core.constants.ApplicationConstants.COMMA;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.xss.XSSAPI;
import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.wcm.api.Page;
import com.talentacademy.core.beans.CourseBadgeDetails;
import com.talentacademy.core.beans.CourseDetails;
import com.talentacademy.core.beans.CourseModule;
import com.talentacademy.core.models.CourseDetailsModel;
import com.talentacademy.core.services.CourseDetailsService;
import com.talentacademy.core.services.InvokeAPIService;
import com.talentacademy.core.utils.ApplicationUtil;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, adapters = CourseDetailsModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class CourseDetailsModelImpl implements CourseDetailsModel {

	@SlingObject
	private ResourceResolver resolver;
	
	@SlingObject
	SlingHttpServletRequest request;
	
	@Inject
	private XSSAPI xssApi;
	
	@OSGiService
	InvokeAPIService invokeAPIService;
	
	@OSGiService
	CourseDetailsService courseDetailsService;
	
	@RequestAttribute(name="type")
	private String type;
	
	@ValueMapValue
	private String courseId;
	
	@Inject
	Page currentPage;
	
	// Course details text
	
	@ValueMapValue
	private String overviewRichText;

	// Info card
	@ChildResource
	private Resource skills;

	@ChildResource
	private Resource badges;

	@ChildResource
	private Resource formats;

	@ValueMapValue
	private String skillsLabel;

	@ValueMapValue
	private String badgesLabel;

	@ValueMapValue
	private String formatLabel;

	@ValueMapValue
	private String durationLabel;

	@ValueMapValue
	private String duration;

	@ValueMapValue
	private String infoctaLabel;

	@ValueMapValue
	private String infoctaURL;

	@ValueMapValue
	private String infotarget;

	// Accreditations
	@ValueMapValue
	private String accHeader;

	@ChildResource
	private Resource accreditations;

	// Course Curriculum
	@ValueMapValue
	private String header;

	@ChildResource
	private Resource curriculum;

	@ValueMapValue
	private String learningPathwayID;
	
	private String loId;

	public List<CourseDetails> getCourseDetails() throws URISyntaxException {
		if ("dynamic".equals(type)) {
			loId = StringUtils.isNotBlank(xssApi.filterHTML(request.getParameter("loid")))
					? xssApi.filterHTML(request.getParameter("loid")) : courseId;
			List<NameValuePair> nvps = new ArrayList<>();
			nvps.add(new BasicNameValuePair(INCLUDE,
					INC_SKILLS + COMMA + INCLUDED_SKILLS_INSTANCES_BADGE + COMMA + INC_RESOURCES));
			String responseStr = invokeAPIService.getSingleCourseResponse(loId, nvps);
			return courseDetailsService.getCourseDetails(responseStr, true, getCurrentPageLocale());
		} else {
			List<CourseDetails> courseDetailsList = new ArrayList<>();
			CourseDetails courseDetails = new CourseDetails();
			courseDetails.setSkillsList(getResourceValues(skills, "skill"));
			courseDetails.setFormatsList(getResourceValues(formats, "format"));
			courseDetails.setModuleList(getModules(curriculum));
			courseDetails.setCourseBadgesList(getBadges(badges));
			courseDetailsList.add(courseDetails);
			return courseDetailsList;
		}
	}
		
	private List<CourseModule> getModules(Resource resource) {
		List<CourseModule> courseModuleList = new ArrayList<>();
		if (resource != null) {
			Iterator<Resource> itrRes = resource.listChildren();
			while (itrRes.hasNext()) {
				CourseModule courseModule = new CourseModule();
				ValueMap valueMap = itrRes.next().getValueMap();
				String title = valueMap.get("title") != null
						? valueMap.get("title").toString() : "";
				String subtitle = valueMap.get("subtitle") != null
						? valueMap.get("subtitle").toString() : "";
				String description = valueMap.get("description") != null
						? valueMap.get("description").toString() : "";
				courseModule.setTitle(title);
				courseModule.setSubtitle(subtitle);
				courseModule.setDescription(description);
				courseModuleList.add(courseModule);
			}
		}
		return courseModuleList;
	}
	
	private List<CourseBadgeDetails> getBadges(Resource resource){
		List<CourseBadgeDetails> courseBadgesList = new ArrayList<>();
		if (resource != null) {
			Iterator<Resource> itrRes = resource.listChildren();
			while (itrRes.hasNext()) {
				CourseBadgeDetails courseBadges = new CourseBadgeDetails();
				ValueMap valueMap = itrRes.next().getValueMap();
				String badge = valueMap.get("badge") != null
						? valueMap.get("badge").toString() : "";
				courseBadges.setBadgeImage(badge);
				courseBadgesList.add(courseBadges);
			}
		}
		return courseBadgesList;
		
	}

	private List<String> getResourceValues(Resource resource, String property) {
		List<String> list = new ArrayList<>();
		if (resource != null) {
			Iterator<Resource> itrRes = resource.listChildren();
			while (itrRes.hasNext()) {
				ValueMap valueMap = itrRes.next().getValueMap();
				String skill = valueMap.get(property) !=null 
						? valueMap.get(property).toString() : "";
				list.add(skill);
			}
		}
		return list;
	}
	
	/**
	 * Get current page locale.
	 *
	 * @return locale
	 */
	public String getCurrentPageLocale() {
		return ApplicationUtil.getCurrentPageLocale(currentPage.getPath());
	}


	/**
	 * @return The skillsLabel
	 */
	@Override
	public String getSkillsLabel() {
		return skillsLabel;
	}

	/**
	 * @return The badgesLabel
	 */
	@Override
	public String getBadgesLabel() {
		return badgesLabel;
	}

	/**
	 * @return The formatLabel
	 */
	@Override
	public String getFormatLabel() {
		return formatLabel;
	}

	/**
	 * @return The durationLabel
	 */
	@Override
	public String getDurationLabel() {
		if(StringUtils.isNotBlank(loId) && loId.contains("course"))
			return "Duration";
		else if(StringUtils.isNotBlank(loId) && loId.contains("learningProgram"))
			return "Courses";
		return durationLabel;
	}
	
	/**
	 * @return The duration
	 */
	@Override
	public String getDuration() {
		return duration;
	}
	
	/**
	 * @return the infoctaLabel
	 */
	@Override
	public String getInfoctaLabel() {
		return infoctaLabel;
	}

	/**
	 * @return the infoctaURL
	 */
	@Override
	public String getInfoctaURL() {
		return ApplicationUtil.getValidURL(infoctaURL, resolver);
	}

	/**
	 * @return The infotarget
	 */
	@Override
	public String getInfotarget() {
		return infotarget;
	}

	/**
	 * @return The accHeader
	 */
	@Override
	public String getAccHeader() {
		return accHeader;
	}

	/**
	 * @return The accreditations
	 */
	@Override
	public Resource getAccreditations() {
		return accreditations;
	}

	/**
	 * @return The header
	 */
	@Override
	public String getHeader() {
		return header;
	}

	/**
	 * @return The learningPathwayID
	 */
	@Override
	public String getLearningPathwayID() {
		return learningPathwayID;
	}
	
	/**
	 * @return The overviewRichText
	 */
	@Override
	public String getOverviewRichText() {
		return overviewRichText;
	}

	/**
	 * @return The CurrentPageLanguage
	 */
	public String getCurrentPageLanguage() {
		return ApplicationUtil.getLanguageFromPath(currentPage.getPath());
	}
}
