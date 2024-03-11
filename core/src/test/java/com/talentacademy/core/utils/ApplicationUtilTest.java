package com.talentacademy.core.utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ApplicationUtilTest {

	@Mock
	ResourceResolver resourceResolver;

	@Mock
	private SlingHttpServletRequest request;

	String url = "/content/talentacademy/language-masters/en/individual";

	String expectedHtmlExtension = "/content/talentacademy/language-masters/en/individual.html";

	String expectedHtmlQuestion = "/content/talentacademy/language-masters/en/individual.html?wcmmode=disabled";

	String expectedHashTag = "/content/talentacademy/language-masters/en/individual.html#cta";

	String htmlQuestion = "/content/talentacademy/language-masters/en/individual?wcmmode=disabled";

	String hashTag = "/content/talentacademy/language-masters/en/individual#cta";

	String assetUrl = "/content/dam/talentacademy/icons.youtube.svg";

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		lenient().when(resourceResolver.map(expectedHtmlExtension)).thenReturn(expectedHtmlExtension);
		lenient().when(resourceResolver.map(expectedHtmlQuestion)).thenReturn(expectedHtmlQuestion);
		lenient().when(resourceResolver.map(expectedHashTag)).thenReturn(expectedHashTag);

	}

	/**
     * Test method for
     * {@link ApplicationUtil#getValidURL()}.
     */
	@Test
	void testGetValidURL() {
		assertEquals(expectedHtmlExtension, ApplicationUtil.getValidURL(url, resourceResolver));
		assertEquals(expectedHtmlExtension, ApplicationUtil.getValidURL(expectedHtmlExtension, resourceResolver));
		assertEquals(expectedHtmlQuestion, ApplicationUtil.getValidURL(expectedHtmlQuestion, resourceResolver));
		assertEquals(expectedHashTag, ApplicationUtil.getValidURL(expectedHashTag, resourceResolver));
		assertEquals(expectedHtmlQuestion, ApplicationUtil.getValidURL(htmlQuestion, resourceResolver));
		assertEquals(expectedHashTag, ApplicationUtil.getValidURL(hashTag, resourceResolver));
		assertEquals("", ApplicationUtil.getValidURL("", resourceResolver));
		assertEquals(null, ApplicationUtil.getValidURL(null, resourceResolver));
		assertEquals(url, ApplicationUtil.getValidURL(url, null));
		assertEquals(assetUrl, ApplicationUtil.getValidURL(assetUrl, resourceResolver));

	}

	/**
     * Test method for
     * {@link ApplicationUtil#getCountryFromPath()}.
     */
	@Test
	void testGetCountryFromPath() {
		assertEquals("us", ApplicationUtil.getCountryFromPath(url));
	}

	/**
     * Test method for
     * {@link ApplicationUtil#doGet()}.
     */
	@Test
	void testGetLanguageFromPath() {
		assertEquals("en", ApplicationUtil.getLanguageFromPath(url));
	}

	/**
     * Test method for
     * {@link ApplicationUtil#isNumeric()}.
     */
	@Test
	void testIsNumber() throws ServletException, NumberFormatException {

		assertEquals(false, ApplicationUtil.isNumeric(null));
		assertEquals(true, ApplicationUtil.isNumeric("12"));
		assertEquals(false, ApplicationUtil.isNumeric("abc"));

	}

	/**
     * Test method for
     * {@link ApplicationUtil#getOffSetValue()}.
     */
	@Test
	void testGetOffsetValue() throws NumberFormatException {

		assertEquals(12, ApplicationUtil.getOffSetValue("12","0"));
		assertEquals(12, ApplicationUtil.getOffSetValue("12","1"));
		assertEquals(36, ApplicationUtil.getOffSetValue("12","3"));

	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.utils.ApplicationUtil#getStringFromArrayConcatenatingChar()}.
	 */
	@Test
	void testGetStringFromArrayConcatenatingCharWithIndex0(){
		String[] array = {"string1","string2","string3"};
		String concatenatingString = "/";
		int index = 0;

		String expectedString = "string1/string2/string3";

		String str = ApplicationUtil.getStringFromArrayConcatenatingChar(array,concatenatingString,index);

		assertEquals(expectedString,str);

	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.utils.ApplicationUtil#getStringFromArrayConcatenatingChar()}.
	 */
	@Test
	void testGetStringFromArrayConcatenatingCharWithIndexGreaterThan0(){
		String[] array = {"string1","string2","string3"};
		String concatenatingString = "/";
		int index = 1;

		String expectedString = "string2/string3";

		String str = ApplicationUtil.getStringFromArrayConcatenatingChar(array,concatenatingString,index);

		assertEquals(expectedString,str);

	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.utils.ApplicationUtil#getFirstStringFromArraySplit()}.
	 */
	@Test
	void testGetFirstStringFromArraySplitWithSplitCharacter(){
		String str = "string1/string2/string3";
		String concatenatingString = "/";

		String expectedString = "string1";

		String result = ApplicationUtil.getFirstStringFromArraySplit(str,concatenatingString);

		assertEquals(expectedString,result);
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.utils.ApplicationUtil#getFirstStringFromArraySplit()}.
	 */
	@Test
	void testGetFirstStringFromArraySplitWithNoSplitCharacter(){
		String str = "string3";
		String concatenatingString = "/";

		String expectedString = "string3";

		String result = ApplicationUtil.getFirstStringFromArraySplit(str,concatenatingString);

		assertEquals(expectedString,result);
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.utils.ApplicationUtil#getSecondElementFromSplitUri()}.
	 */
	@Test
	void testGetComponentResourceWithNullRequestUri() {
		String requestUri = null;


		assertNull(ApplicationUtil.getSecondElementFromSplitUri(requestUri));
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.utils.ApplicationUtil#getSecondElementFromSplitUri()}.
	 */
	@Test
	void testGetComponentResourceWithNoSeparator() {
		String requestUri = "";


		assertNull(ApplicationUtil.getSecondElementFromSplitUri(requestUri));
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.utils.ApplicationUtil#getSecondElementFromSplitUri()}.
	 */
	@Test
	void testGetComponentResource() {
		String requestUri = "firstPart.html/secondPart/path";


		assertEquals("/secondPart/path",ApplicationUtil.getSecondElementFromSplitUri(requestUri));
	}
	
	/**
	 * Test method for {@link ApplicationUtil #getLowerCaseStringWithHyphen()}.
	 */
	@Test
	void testGetLowerCaseStringWithHyphen() {
		String input = "Promotional Banner";
		assertEquals("promotional-banner", ApplicationUtil.getLowerCaseStringWithHyphen(input));
		assertNull(ApplicationUtil.getLowerCaseStringWithHyphen(null));
	}

}
