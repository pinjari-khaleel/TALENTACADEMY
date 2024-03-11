package com.talentacademy.core.models.impl;

import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.talentacademy.core.beans.CourseDetails;
import com.talentacademy.core.constants.ALMConstants;
import com.talentacademy.core.models.DynamicCourseCarouselModel;
import com.talentacademy.core.services.CourseDetailsService;
import com.talentacademy.core.services.InvokeAPIService;
import com.talentacademy.core.utils.ApplicationUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import static com.talentacademy.core.constants.ALMConstants.DATA;
import static com.talentacademy.core.constants.ALMConstants.RELATION_SHIPS;
import static com.talentacademy.core.constants.ALMConstants.SUB_LOS;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.xss.XSSAPI;

import javax.inject.Inject;

/**
 * Sling Model for Dynamic Course Details.
 */
@Model(adaptables = { SlingHttpServletRequest.class, Resource.class }, adapters = {
		DynamicCourseCarouselModel.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DynamicCourseCarouselModelImpl implements DynamicCourseCarouselModel {

	public static final String ZERO = "0";

	public static final String NO_OF_SLIDES = "noofields";

	@ValueMapValue
	String catalogId;

	@Inject
	Page currentPage;

	@Inject
	Resource resource;

	@OSGiService
	CourseDetailsService courseDetailsService;

	@OSGiService
	InvokeAPIService invokeAPIService;
	
	@SlingObject
	SlingHttpServletRequest request;
	
	@Inject
	private XSSAPI xssApi;
	
	private String loId;
	
	/**
	 * get course details from ALM.
	 * 
	 * @return courseDetails List
	 */
	@Override
	public List<CourseDetails> getCourseDetails() throws URISyntaxException {

		ValueMap parentProps = resource.getParent().getValueMap();
		String numberOfSlides = parentProps.containsKey(NO_OF_SLIDES) ? parentProps.get(NO_OF_SLIDES).toString() : ZERO;
		List<NameValuePair> nvps = new ArrayList<>();
		loId = xssApi.filterHTML(request.getParameter(ALMConstants.LO_ID));
		if (StringUtils.isNotBlank(loId)) {
			String responseStr = invokeAPIService.getSingleCourseResponse(loId, nvps);
			return getLearningPathwayCourses(responseStr, nvps);
		}
		nvps.add(new BasicNameValuePair(ALMConstants.PAGE_LIMIT, numberOfSlides));
		nvps.add(new BasicNameValuePair(ALMConstants.SORT, ALMConstants.DATE));
		nvps.add(new BasicNameValuePair(ALMConstants.CATALOG_ID, catalogId));
		nvps.add(new BasicNameValuePair(ALMConstants.LO_TYPE_FILTER, ALMConstants.LO_TYPE_FILTER_PARAM));
		nvps.add(new BasicNameValuePair(ALMConstants.INCLUDE, ALMConstants.INCLUDED_SKILLS_INSTANCES_BADGE));

		return courseDetailsService.getCourseDetails(invokeAPIService.getMultipleCourseResponse(nvps), false, getCurrentPageLocale());
	}
	
	/**
	 * get learning Pathway courses.
	 * 
	 * @return courseDetailsList
	 */
	private List<CourseDetails> getLearningPathwayCourses(String responseStr, List<NameValuePair> nvps)
			throws URISyntaxException {
		nvps.add(new BasicNameValuePair(ALMConstants.INCLUDE, ALMConstants.INCLUDED_SKILLS_INSTANCES_BADGE));
		List<CourseDetails> courseDetailsList = new ArrayList<>();
		JsonObject response = new Gson().fromJson(responseStr, JsonObject.class);
		if (response.has(DATA)) {
			JsonObject course = response.get(DATA).getAsJsonObject();
			JsonObject details = course.getAsJsonObject();
			if(details.has(RELATION_SHIPS)) {
				JsonObject relationShipJson = details.get(RELATION_SHIPS).getAsJsonObject();
				if (relationShipJson.has(SUB_LOS)) {
					JsonObject subLOjson = relationShipJson.get(SUB_LOS).getAsJsonObject();
					if (subLOjson.has(DATA)) {
						JsonArray subLOdata = subLOjson.get(DATA).getAsJsonArray();
						courseDetailsList = getSubLoDetails(subLOdata, courseDetailsList, nvps);
					}
				}
			}
		}
		return courseDetailsList;
	}
	
	/**
	 * get learning Pathway Sub LO Details.
	 * 
	 * @return courseDetailsList
	 */
	private List<CourseDetails> getSubLoDetails(JsonArray subLOdata, List<CourseDetails> courseDetailsList,
			List<NameValuePair> nvps) throws URISyntaxException {
		for (JsonElement subLo : subLOdata) {
			JsonObject obj = subLo.getAsJsonObject();
			String courseId = obj.has(ALMConstants.ID) ? obj.get(ALMConstants.ID).getAsString() : "";
			String courseRes = "";
			if (StringUtils.isNotBlank(courseId) && courseId.contains(ALMConstants.COURSE)) {
				courseRes = invokeAPIService.getSingleCourseResponse(courseId, nvps);
			}
			if (StringUtils.isNotBlank(courseRes)) {
				courseDetailsList
						.addAll(courseDetailsService.getCourseDetails(courseRes, true, getCurrentPageLocale()));
			}
		}
		return courseDetailsList;
	}
	
	/**
	 * get learning object ID
	 * 
	 * @return loId
	 */
	public String getLoId() {
		return loId;
	}

	/**
	 * Get current page locale.
	 *
	 * @return locale
	 */
	public String getCurrentPageLocale() {
		return ApplicationUtil.getCurrentPageLocale(currentPage.getPath());
	}
}