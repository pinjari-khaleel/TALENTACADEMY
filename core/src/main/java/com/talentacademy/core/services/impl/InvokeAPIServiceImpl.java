package com.talentacademy.core.services.impl;

import com.talentacademy.core.beans.AlmConfigurationBean;
import com.talentacademy.core.constants.ALMConstants;
import com.talentacademy.core.services.ALMAuthenticationService;
import com.talentacademy.core.services.CPTokenService;
import com.talentacademy.core.services.InvokeAPIService;
import com.talentacademy.core.utils.RestApiHttpClientUtil;

import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = InvokeAPIService.class)
public class InvokeAPIServiceImpl implements InvokeAPIService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InvokeAPIServiceImpl.class);

	@Reference
    private CPTokenService cpTokenService;

	@Reference
    private ALMAuthenticationService almAuthenticationService;

	/**
	 * get APIResponse from ALM.
	 * 
	 * @return responseStr String
	 */
	@Override
	public String getMultipleCourseResponse(List<NameValuePair> nvps) throws URISyntaxException {
		return getResponse(null, nvps);
	}

	/**
	 * get APIResponse from ALM.
	 * 
	 * @return responseStr String
	 */
	@Override
	public String getSingleCourseResponse(String courseid, List<NameValuePair> nvps) throws URISyntaxException {
		return getResponse(courseid, nvps);
	}

	/**
	 * get APIResponse from ALM.
	 * 
	 * @return responseStr String
	 */
	public String getResponse(String courseid, List<NameValuePair> nvps) throws URISyntaxException {
		
		LOGGER.debug("Inside InvokeAPIServiceImpl GetResponse :");
		AlmConfigurationBean almConfigurationBean = almAuthenticationService.getAlmConfigurationBean();
		String learningObjectsEndPoint = almConfigurationBean.getLearningObjectsApi();
		String url = learningObjectsEndPoint.replace("{almUrl}", almConfigurationBean.getAlmBaseUrl())
				.replace("{prefix}", almConfigurationBean.getPrefix())
				.replace("{version}", almConfigurationBean.getVersion());
		if (StringUtils.isNotBlank(courseid)) {
			url += "/" + courseid;
		}
		String accessToken = cpTokenService.getAccessTokenFromCode(cpTokenService.getAccessToken());
		accessToken = ALMConstants.OAUTH + " " + accessToken;
		return RestApiHttpClientUtil.doUrlEncodedGetCall(url, nvps, accessToken);
	}
}
