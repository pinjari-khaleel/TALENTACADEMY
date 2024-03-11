package com.talentacademy.core.models.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class CountryCodeSelectorModelImplTest {
	private final AemContext aemContext = new AemContext();

	private CountryCodeSelectorModelImpl countrySelectorModelImpl;

	private final String ROOT_PATH = "/content/talentacademy";

	@Mock
	private ResourceResolver resolver;

	/**
	 * setUp method for {@link CountryCodeSelectorModelImpl}.
	 */
	@BeforeEach
	void setUp() {
		aemContext.addModelsForClasses(CountryCodeSelectorModelImpl.class);
		aemContext.load().json("/com/talentacademy/core/models/impl/CountryCodeSelectorModelImpl.json", ROOT_PATH);
	}

	/**
	 * Test method for {@link CountryCodeSelectorModelImpl
	 * #getCountrySelectorList()}.
	 */
	@Test
	void testCountrySelectorList() {
		aemContext.currentResource(ROOT_PATH + "/jcr:content");
		countrySelectorModelImpl = aemContext.request().adaptTo(CountryCodeSelectorModelImpl.class);
		assertNotNull(countrySelectorModelImpl.getCountrySelectorList());
		assertEquals("SA", countrySelectorModelImpl.getCountrySelectorList().get(0).getCountryCode());
	}

	/**
	 * Test method for {@link CountryCodeSelectorModelImpl #getTitle()}.
	 */
	@Test
	void testTitle() {
		aemContext.currentResource(ROOT_PATH + "/jcr:content");
		countrySelectorModelImpl = aemContext.request().adaptTo(CountryCodeSelectorModelImpl.class);
		assertEquals("Mobile", countrySelectorModelImpl.getTitle());
	}

	/**
	 * Test method for {@link CountryCodeSelectorModelImpl #getName()}.
	 */
	@Test
	void testName() {
		aemContext.currentResource(ROOT_PATH + "/jcr:content");
		countrySelectorModelImpl = aemContext.request().adaptTo(CountryCodeSelectorModelImpl.class);
		assertEquals("phoneCode", countrySelectorModelImpl.getName());
	}

	/**
	 * Test method for {@link CountryCodeSelectorModelImpl #getFlagsPath()}.
	 */
	@Test
	void testFlagsPath() {
		aemContext.currentResource(ROOT_PATH + "/jcr:content");
		countrySelectorModelImpl = aemContext.request().adaptTo(CountryCodeSelectorModelImpl.class);
		assertEquals("/content/dam/flags", countrySelectorModelImpl.getFlagsPath());
	}

	/**
	 * Test method for {@link CountryCodeSelectorModelImpl #getRequired()}.
	 */
	@Test
	void testRequired() {
		aemContext.currentResource(ROOT_PATH + "/jcr:content");
		countrySelectorModelImpl = aemContext.request().adaptTo(CountryCodeSelectorModelImpl.class);
		assertEquals("true", countrySelectorModelImpl.getRequired());
	}

	/**
	 * Test method for {@link CountryCodeSelectorModelImpl #getRequired()}.
	 */
	@Test
	void testErrorMessage() {
		aemContext.currentResource(ROOT_PATH + "/jcr:content");
		countrySelectorModelImpl = aemContext.request().adaptTo(CountryCodeSelectorModelImpl.class);
		assertEquals("Select the phone country", countrySelectorModelImpl.getErrorMessage());
	}

}
