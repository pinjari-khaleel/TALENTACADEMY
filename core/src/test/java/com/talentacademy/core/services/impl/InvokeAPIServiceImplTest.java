package com.talentacademy.core.services.impl;

import com.talentacademy.core.beans.AlmConfigurationBean;
import com.talentacademy.core.constants.ALMConstants;
import com.talentacademy.core.services.ALMAuthenticationService;
import com.talentacademy.core.services.CPTokenService;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InvokeAPIServiceImplTest {

	@InjectMocks
	InvokeAPIServiceImpl invokeAPIService;

	@Mock
	AlmConfigurationBean almConfigurationBean;

	@Mock
	ALMAuthenticationService almAuthenticationService;

	@Mock
	CPTokenService cpTokenService;

	/**
     * setUp to load before test method
     */
	@BeforeEach
	void setUp() {
		when(almAuthenticationService.getAlmConfigurationBean()).thenReturn(almConfigurationBean);
		when(almConfigurationBean.getLearningObjectsApi()).thenReturn("learningObjectsAPI");
		when(almConfigurationBean.getAlmBaseUrl()).thenReturn("https://alm.site.com");
		when(almConfigurationBean.getPrefix()).thenReturn("prefix");
		when(almConfigurationBean.getVersion()).thenReturn("1.0");
		when(cpTokenService.getAccessToken()).thenReturn("accessToken");
	}

	/**
	 * Test method for {@link InvokeAPIServiceImpl#getMultipleCourseResponse()}.
	 */
	@Test
	void getMultipleCourseResponse() throws URISyntaxException {
		List<NameValuePair> nvps = new ArrayList<>();
		nvps.add(new BasicNameValuePair(ALMConstants.PAGE_LIMIT, "12"));
		nvps.add(new BasicNameValuePair(ALMConstants.SORT, ALMConstants.DATE));
		nvps.add(new BasicNameValuePair(ALMConstants.CATALOG_ID, "12345"));
		nvps.add(new BasicNameValuePair(ALMConstants.LO_TYPE_FILTER, ALMConstants.LO_TYPE_FILTER_PARAM));
		nvps.add(new BasicNameValuePair(ALMConstants.INCLUDE, ALMConstants.INCLUDED_SKILLS_INSTANCES_BADGE));
		invokeAPIService.getMultipleCourseResponse(nvps);
	}

	/**
	 * Test method for {@link InvokeAPIServiceImpl#getSingleCourseResponse()}.
	 */
	@Test
	void getSingleCourseResponse() throws URISyntaxException {
		String courseId = "course:12345";
		List<NameValuePair> nvps = new ArrayList<>();
		nvps.add(new BasicNameValuePair(ALMConstants.INCLUDE, ALMConstants.INCLUDED_SKILLS_INSTANCES_BADGE));
		invokeAPIService.getSingleCourseResponse(courseId, nvps);
	}

}