package com.talentacademy.core.models.impl;

import com.day.cq.wcm.api.Page;
import com.talentacademy.core.beans.CourseDetails;
import com.talentacademy.core.constants.ALMConstants;
import com.talentacademy.core.models.SingleCardWrapperModel;
import com.talentacademy.core.services.CourseDetailsService;
import com.talentacademy.core.services.InvokeAPIService;
import com.talentacademy.core.utils.ApplicationUtil;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Single Card Wrapper Model Implementation.
 **/
@Model(adaptables = { SlingHttpServletRequest.class, Resource.class }, adapters = {
		SingleCardWrapperModel.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SingleCardWrapperModelImpl implements SingleCardWrapperModel {

	public static final String RESOURCE_TYPE = "talentacademy/components/content/singlecardwrapper";

	@Inject
	Page currentPage;

	@ValueMapValue
	@Default(values = "staticCard")
	public String singleCardVariation;

	@ValueMapValue
	public String courseid;

	@OSGiService
	InvokeAPIService invokeAPIService;

	@OSGiService
	CourseDetailsService courseDetailsService;

	/**
	 * get course details from ALM.
	 * 	
	 * @return courseDetails List
	 */
	@Override
	public List<CourseDetails> getSingleCardWrapperDetails() throws URISyntaxException {
		List<NameValuePair> nvps = new ArrayList<>();
		nvps.add(new BasicNameValuePair(ALMConstants.INCLUDE, ALMConstants.INCLUDED_SKILLS_INSTANCES_BADGE));
		return courseDetailsService.getCourseDetails(invokeAPIService.getSingleCourseResponse(courseid, nvps), true,
				getCurrentPageLocale());
	}

	/**
	 * @return courseid of the card.
	 */
	@Override
	public String getCourseid() {
		return courseid;
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
	 * @return singleCardVariation
	 */
	public String getSingleCardVariation() {
		return singleCardVariation;
	}

}
