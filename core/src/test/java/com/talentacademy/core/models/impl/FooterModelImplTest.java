package com.talentacademy.core.models.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.talentacademy.core.models.FooterGlobalLinks;
import com.talentacademy.core.models.FooterModel;
import com.talentacademy.core.models.Links;
import com.talentacademy.core.models.SocialMediaLinks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
@ExtendWith({ AemContextExtension.class })
class FooterModelImplTest {
	
    private final AemContext aemContext = new AemContext();
    
    private FooterModelImpl footerModel;

	/**
	 * setUp method for
	 * {@link FooterModelImpl}.
	 */
    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(FooterModel.class);
        aemContext.load().json("/com/talentacademy/core/models/impl/FooterModelImpl.json", "/component");
    }

	/**
	 * Test method for
	 * {@link FooterModelImpl #getSocialMediaLinks()}.
	 */
	@Test
	void testSocialMediaLinks() {
		aemContext.currentResource("/component/footer");
		footerModel = aemContext.request().adaptTo(FooterModelImpl.class);
		for(SocialMediaLinks field : footerModel.getSocialMediaLinks()) {
			assertEquals("/content/dam/dummyicon.png", field.getIcon());
			assertEquals("/content/dam/dummylink", field.getLink());
			assertEquals("Alt Text", field.getAltText());
			assertEquals("Youtube", field.getLabel());
		}
	}

	/**
	 * Test method for
	 * {@link FooterModelImpl #getLegalLinks()}.
	 */
	@Test
	void testLegalLinks() {
		aemContext.currentResource("/component/footer");
		footerModel = aemContext.request().adaptTo(FooterModelImpl.class);
		for(Links field : footerModel.getLegalLinks()) {
			assertEquals("Privacy Policy", field.getCtaLabel());
			assertEquals("/content/nta/dummyctaurl", field.getCtaURL());
			assertEquals("_blank", field.getTarget());
		}
	}

	/**
	 * Test method for
	 * {@link FooterModelImpl #getGlobalLinks()}.
	 */
	@Test
	void testGlobalLinks() {
		aemContext.currentResource("/component/footer");
		footerModel = aemContext.request().adaptTo(FooterModelImpl.class);
		for(FooterGlobalLinks field : footerModel.getGlobalLinks()) {
			assertEquals("About", field.getHeader());
			for(Links links :field.getLinks()) {
				assertEquals("Who we are", links.getCtaLabel());
				assertEquals("/content/nta/dummyctaurl", links.getCtaURL());
				assertEquals("_blank", links.getTarget());
			}
		}
	}

	/**
	 * Test method for
	 * {@link FooterModelImpl #getSocialMediaHeader()}.
	 */
	@Test
	void testSocialMediaHeader() {
		aemContext.currentResource("/component/footer");
		footerModel = aemContext.request().adaptTo(FooterModelImpl.class);
		assertEquals("Follow Us", footerModel.getSocialMediaHeader());	
	}

	/**
	 * Test method for
	 * {@link FooterModelImpl #getCopyrightText()}.
	 */
	@Test
	void testCopyRightText() {
		aemContext.currentResource("/component/footer");
		footerModel = aemContext.request().adaptTo(FooterModelImpl.class);
		assertEquals("Copyright", footerModel.getCopyrightText());	
	}

	/**
	 * Test method for
	 * {@link FooterModelImpl#getRecaptchaText()}.
	 */
	@Test
	void testRecaptchaText() {
		aemContext.currentResource("/component/footer");
		footerModel = aemContext.request().adaptTo(FooterModelImpl.class);
		assertEquals("recaptcha text", footerModel.getRecaptchaText());
	}
}
