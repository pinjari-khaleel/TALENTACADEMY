package com.talentacademy.core.models.impl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class HeaderModelTest {

	private final AemContext aemContext = new AemContext();

	private final String ROOT_PATH = "/content/talentacademy/us/en/test";

	HeaderModelImpl headerModel;

	/**
	 * Setup method for {com.talentacademy.core.models.impl.HeaderModelImpl}
	 */
	@BeforeEach
	public void setup() {
		aemContext.addModelsForClasses(HeaderModelImpl.class);
		aemContext.load().json("/com/talentacademy/core/models/impl/HeaderTest.json", ROOT_PATH);
		aemContext.currentResource(ROOT_PATH);
		headerModel = aemContext.currentResource().adaptTo(HeaderModelImpl.class);
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.HeaderModelImpl#getLogoUrl()}.
	 */
	@Test
	void testGetLogoUrl() {
		assertEquals("Logo Url", headerModel.getLogoUrl());
		assertNotNull(headerModel.getLogoUrl());
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.HeaderModelImpl#getLogoLink()}.
	 */
	@Test
	void testGetLogoLink() {
		assertEquals("Logo Link", headerModel.getLogoLink());
		assertNotNull(headerModel.getLogoLink());
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.HeaderModelImpl#getLogoAltText()}.
	 */
	@Test
	void testGetLogoAltText() {
		assertEquals("Alt text", headerModel.getLogoAltText());
		assertNotNull(headerModel.getLogoAltText());
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.HeaderModelImpl#getLobbyLabel()}.
	 */
	@Test
	void testGetLobbyLabel() {
		assertEquals("Lobby Label", headerModel.getLobbyLabel());
		assertNotNull(headerModel.getLobbyLabel());
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.HeaderModelImpl#getLobbyUrl()}.
	 */
	@Test
	void testGetLobbyUrl() {
		assertEquals("Lobby Url", headerModel.getLobbyUrl());
		assertNotNull(headerModel.getLobbyUrl());
	}

}
