package com.talentacademy.core.models.impl;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.talentacademy.core.constants.ApplicationConstants;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class PageConfigModelImplTest {

	private final String ROOT_PATH = "/content/talentacademy/us/en/home/test";

	@InjectMocks
	PageConfigModelImpl pageConfigModelImpl;

	@Mock
	PageManager pageManager;

	@Mock
	Page currentPage;

	@Mock
	Page parentPage;

	@Mock
	ValueMap headerMap;

	/**
	 * Setup method for {com.talentacademy.core.models.impl.PageConfigModelImpl}
	 */
	@BeforeEach
	public void setup() {
		pageConfigModelImpl.pathReference = ROOT_PATH;
		pageConfigModelImpl.languageDirection = "ltr";
		pageConfigModelImpl.hideInSiteMap = "true";
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.PageConfigModelImpl#getHeaderButtonLabel()}.
	 */
	@Test
	void testGetHeaderButtonLabel() {
		pageConfigModelImpl.init();
		when(pageManager.getPage(ROOT_PATH)).thenReturn(currentPage);
		pageConfigModelImpl.init();
		when(currentPage.getDepth()).thenReturn(5);
		when(currentPage.getProperties()).thenReturn(headerMap);
		when(headerMap.get(ApplicationConstants.HEADER_BUTTON_LABEL, String.class)).thenReturn("Header Button Label");
		when(headerMap.get(ApplicationConstants.HEADER_BUTTON_URL, String.class)).thenReturn("/content/talentaccademy");
		pageConfigModelImpl.init();
		assertEquals("Header Button Label", pageConfigModelImpl.getHeaderButtonLabel());
		assertEquals("/content/talentaccademy", pageConfigModelImpl.getHeaderButtonUrl());
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.PageConfigModelImpl#getHeaderButtonUrl()}.
	 */
	@Test
	void testGetHeaderButtonURL() {
		when(pageManager.getPage(ROOT_PATH)).thenReturn(currentPage);
		when(currentPage.getParent(2)).thenReturn(null);
		when(currentPage.getDepth()).thenReturn(7);
		pageConfigModelImpl.init();
		when(currentPage.getParent(2)).thenReturn(parentPage);
		when(currentPage.getDepth()).thenReturn(7);
		when(parentPage.getProperties()).thenReturn(headerMap);
		when(headerMap.get(ApplicationConstants.HEADER_BUTTON_LABEL, String.class)).thenReturn("Header Button Label");
		when(headerMap.get(ApplicationConstants.HEADER_BUTTON_URL, String.class)).thenReturn("/content/talentaccademy");
		pageConfigModelImpl.init();
		assertEquals("Header Button Label", pageConfigModelImpl.getHeaderButtonLabel());
		assertEquals("/content/talentaccademy", pageConfigModelImpl.getHeaderButtonUrl());
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.PageConfigModelImpl#getLanguageDirection()}.
	 */
	@Test
	void testGetLanguageDirection(){
		when(currentPage.getAbsoluteParent(3)).thenReturn(parentPage);
		when(parentPage.getProperties()).thenReturn(headerMap);
		when(headerMap.get(ApplicationConstants.LANGUAGE_DIRECTION, String.class)).thenReturn("ltr");
		assertEquals("ltr", pageConfigModelImpl.getLanguageDirection());
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.PageConfigModelImpl#getHideInSitemap()}.
	 */
	@Test
	public void testGetHideInSitemap() {
		assertEquals("true", pageConfigModelImpl.getHideInSitemap());
	}
}
