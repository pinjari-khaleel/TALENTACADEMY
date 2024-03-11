package com.talentacademy.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.apache.sling.api.resource.Resource;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class CareerDetailsModelImplTest {

	private CareerDetailsModelImpl careerDetailsModelImpl;

	private final AemContext aemContext = new AemContext();

	@BeforeEach
	void setUp() {
		aemContext.addModelsForClasses(CareerDetailsModelImpl.class);
		aemContext.load().json("/com/talentacademy/core/models/impl/CareerDetailsModelImpl.json", "/component");
		aemContext.currentResource("/component/careerdetails");
		careerDetailsModelImpl = aemContext.request().adaptTo(CareerDetailsModelImpl.class);

	}

	/**
	 * Test method for
	 * {@link CareerDetailsModelImpl#getRoleDetails()}.
	 */
	@Test
	void testRoleDetails() {
		Resource res = aemContext.currentResource("/component/careerdetails/roleDetails");
		careerDetailsModelImpl.getRoleDetails();
		assertNotNull(res);
	}

	/**
	 * Test method for
	 * {@link CareerDetailsModelImpl#getEmployerIcons()}.
	 */
	@Test
	void testEmployerIcons() {
		Resource employerResource = aemContext.currentResource("/component/careerdetails/employerIcons");
		careerDetailsModelImpl.getEmployerIcons();
		assertNotNull(employerResource);
	}

	/**
	 * Test method for
	 * {@link CareerDetailsModelImpl#getRegisterInterestCtaLabel()}.
	 */
	@Test
	void testGetRegisterInterestCtaLabel() {
		assertEquals("Register Interest", careerDetailsModelImpl.getRegisterInterestCtaLabel());

	}

	/**
	 * Test method for
	 * {@link CareerDetailsModelImpl#getRegisterInterestCtaLink()}.
	 */
	@Test
	void testGetRegisterInterestCtaLink() {
		assertEquals("externallink", careerDetailsModelImpl.getRegisterInterestCtaLink());
	}

	/**
	 * Test method for
	 * {@link CareerDetailsModelImpl#getRecruitmentStatus()}.
	 */
	@Test
	void testGetRecruitmentStatus() {
		assertEquals("Actively Recruiting", careerDetailsModelImpl.getRecruitmentStatus());
	}

	/**
	 * Test method for
	 * {@link CareerDetailsModelImpl#getJobTitle()}.
	 */
	@Test
	void testGetJobTitle() {
		assertEquals("Travel Advisor", careerDetailsModelImpl.getJobTitle());
	}

	/**
	 * Test method for
	 * {@link CareerDetailsModelImpl#getPotentialEmployersLabel()}.
	 */
	@Test
	void testGetPotentialEmployersLabel() {
		assertEquals("Potential Employers Label", careerDetailsModelImpl.getPotentialEmployersLabel());
	}

	/**
	 * Test method for
	 * {@link CareerDetailsModelImpl#getDemandGrowthLabel()}.
	 */
	@Test
	void testGetDemandGrowthLabel() {
		assertEquals("Demand Growth Label", careerDetailsModelImpl.getDemandGrowthLabel());
	}

	/**
	 * Test method for
	 * {@link CareerDetailsModelImpl#getDemandGrowthValue()}.
	 */
	@Test
	void testGetDemandGrowthValue() {
		assertEquals("Demand Growth Value", careerDetailsModelImpl.getDemandGrowthValue());
	}

	/**
	 * Test method for
	 * {@link CareerDetailsModelImpl#getSalaryRangeLabel()}.
	 */
	@Test
	void testGetSalaryRangeLabel() {
		assertEquals("Salary Range Label", careerDetailsModelImpl.getSalaryRangeLabel());
	}

	/**
	 * Test method for
	 * {@link CareerDetailsModelImpl#getSalaryRangeValue()}.
	 */
	@Test
	void testGetSalaryRangeValue() {
		assertEquals("Salary Range Value", careerDetailsModelImpl.getSalaryRangeValue());
	}

	/**
	 * Test method for
	 * {@link CareerDetailsModelImpl#getOpenInNewTab()}.
	 */
	@Test
	void testGetOpenInNewTab() {
		assertEquals("true", careerDetailsModelImpl.getOpenInNewTab());
	}

}
