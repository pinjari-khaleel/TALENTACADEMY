package com.talentacademy.core.services;

import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.NameValuePair;

public interface InvokeAPIService {

	String getMultipleCourseResponse(List<NameValuePair> nvps) throws URISyntaxException;

	/**
	 * get APIResponse from ALM.
	 * 
	 * @return responseStr String
	 */
	String getSingleCourseResponse(String courseid, List<NameValuePair> nvps) throws URISyntaxException;
}