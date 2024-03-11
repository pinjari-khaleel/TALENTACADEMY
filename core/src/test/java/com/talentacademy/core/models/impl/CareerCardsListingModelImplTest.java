package com.talentacademy.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.wcm.api.Page;
import com.talentacademy.core.beans.GridListing;
import com.talentacademy.core.models.FilterHelper;
import com.talentacademy.core.services.GridListingService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class CareerCardsListingModelImplTest {

	CareerCardsListingModelImpl careerCardsListingModelImpl = new CareerCardsListingModelImpl();

	private final AemContext aemContext = new AemContext();

	private GridListingService gridListingService = Mockito.mock(GridListingService.class);

	private Resource filterTagHelper;

	@Mock
	private SlingHttpServletRequest request;

	@BeforeEach
	void setUp() {

		Page page = aemContext.create().page("/content/talentacademy/sa/en/testpage");

		String[] filterTags = {
				"talent-academy:career-listing/sector/design-construction,talent-academy:career-listing/recruitment-status/future-position,talent-academy:career-listing/experience-level/3-4-years-experience" };

		aemContext.addModelsForClasses(CareerCardsListingModelImpl.class);
		aemContext.load().json("/com/talentacademy/core/models/impl/CareerCardsListingModelImpl.json", "/component");
		aemContext.currentResource("/component/careercardslisting");
		careerCardsListingModelImpl = aemContext.request().adaptTo(CareerCardsListingModelImpl.class);

		filterTagHelper = aemContext.create().resource(page, "careerdetails", "sling:resourceType", filterTags);

	}

	/**
	 * Test method for
	 * {@link CareerCardsListingModelImpl#getRootDetailsPath()}.
	 */
	@Test
	void testGetRootDetailsPath() {
		assertEquals("/content/talentacademy/sa/future-career", careerCardsListingModelImpl.getRootDetailsPath());
	}

	/**
	 * Test method for
	 * {@link CareerCardsListingModelImpl#getBadgesEarned()}.
	 */
	@Test
	void testGetBadgesEarned() {
		assertEquals("Earned badges", careerCardsListingModelImpl.getBadgesEarned());
	}

	/**
	 * Test method for
	 * {@link CareerCardsListingModelImpl#getKnowMoreCtaLabel()}.
	 */
	@Test
	void testGetKnowMoreCtaLabel() {
		assertEquals("Know More", careerCardsListingModelImpl.getKnowMoreCtaLabel());
	}

	/**
	 * Test method for
	 * {@link CareerCardsListingModelImpl#getFiltersLabel()}.
	 */
	@Test
	void testGetFiltersLabel() {
		assertEquals("Filters", careerCardsListingModelImpl.getFiltersLabel());
	}

	/**
	 * Test method for
	 * {@link CareerCardsListingModelImpl#getApplyFilterLabel()}.
	 */
	@Test
	void testGetApplyFilterLabel() {
		assertEquals("Apply Filters", careerCardsListingModelImpl.getApplyFilterLabel());
	}

	/**
	 * Test method for
	 * {@link CareerCardsListingModelImpl#getClearFilterLabel()}.
	 */
	@Test
	void testGetClearFilterLabel() {
		assertEquals("Clear Filters", careerCardsListingModelImpl.getClearFilterLabel());
	}

	/**
	 * Test method for
	 * {@link CareerCardsListingModelImpl#getLoadMoreCtaLabel()}.
	 */
	@Test
	void testGetLoadMoreCtaLabel() {
		assertEquals("Load More", careerCardsListingModelImpl.getLoadMoreCtaLabel());
	}

	/**
	 * Test method for
	 * {@link CareerCardsListingModelImpl#getLoadMoreLimit()}.
	 */
	@Test
	void testGetLoadMoreLimit() {
		assertEquals(12, careerCardsListingModelImpl.getLoadMoreLimit());
	}

	/**
	 * Test method for
	 * {@link CareerCardsListingModelImpl#getCareerDetailsdata()}.
	 */
	@Test
	void testGetCareerDetailsdata() throws Exception {

		String rootPath = "/content/talentacademy/sa/en/overview/future-careers";
		Field gridListingServiceValues = careerCardsListingModelImpl.getClass().getDeclaredField("gridListingService");
		gridListingServiceValues.setAccessible(true);
		gridListingServiceValues.set(careerCardsListingModelImpl, gridListingService);

		List<GridListing> gridListingList = new ArrayList<>();
		GridListing gridListing = new GridListing();
		gridListing.setJobTitle("Travel Advisor");
		gridListing.setSector("Tourism");
		gridListing.setExperienceLevel("1-2 years");
		gridListing.setRecruitmentStatus("Actively recruiting");
		gridListing.setPagePath("/content/talentacademy/sa/en/careers-in-neom/travel-advisor");
		gridListingList.add(gridListing);

		when(gridListingService.getCareerDetailsdata(request.getResourceResolver(), rootPath))
				.thenReturn(gridListingList);
		careerCardsListingModelImpl.getCareerDetailsdata();
		assertTrue(true);

	}

	/**
	 * Test method for
	 * {@link CareerCardsListingModelImpl#getData()}.
	 */
	@Test
	void testGetData() throws Exception {

		List<FilterHelper> tagFields = new ArrayList<>();
		FilterHelper filterHelper = filterTagHelper.adaptTo(FilterHelper.class);

		filterHelper.setTagName("Sector");
		filterHelper.setTagLink("talent-academy:career-listing/sector");
		tagFields.add(filterHelper);

		Field filterTagVal = careerCardsListingModelImpl.getClass().getDeclaredField("multifieldvalues");
		filterTagVal.setAccessible(true);
		filterTagVal.set(careerCardsListingModelImpl, tagFields);

		careerCardsListingModelImpl.getData(tagFields);
		careerCardsListingModelImpl.init();
		careerCardsListingModelImpl.getIdTagData();

		assertTrue(true);
	}

}
