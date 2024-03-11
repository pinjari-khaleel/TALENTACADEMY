package com.talentacademy.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.wcm.api.Page;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ColumnControlModelImplTest {

	@InjectMocks
	ColumnControlModelImpl columnControlModelImpl;

	private Page page;
	private Resource resource;
	private AemContext context = new AemContext();
	private static final String COLUMN_CONTROL_NODE = "columncontrol";
	private static final String NUMBER_OF_COLUMNS = "columnNumbers";
	private static final String SECTION_NAME = "sectionName";
	private static final String IS_CONTAINER_FLUID = "isContainerFluid";

	/**
	 * setUp method for
	 * {@link ColumnControlModelImpl}.
	 */
	@BeforeEach
	public void setup() throws Exception {
		page = context.create().page("/content/talentacademy");
	}

	/**
	 * Test method for
	 * {@link ColumnControlModelImpl #getColumnCount(), #getList()}.
	 */
	@Test
	void testColumns() throws Exception {

		resource = context.create().resource(page, COLUMN_CONTROL_NODE,
				JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, ColumnControlModelImpl.RESOURCE_TYPE,
				NUMBER_OF_COLUMNS, "2");
		columnControlModelImpl = resource.adaptTo(ColumnControlModelImpl.class);
		assertEquals(6, columnControlModelImpl.getColumnCount());
		assertEquals(2, columnControlModelImpl.getList().size());
	}

	/**
	 * Test method for
	 * {@link ColumnControlModelImpl #getSectionName()}.
	 */
	@Test
	void testSectionName() throws Exception {
		resource = context.create().resource(page, COLUMN_CONTROL_NODE,
				JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, ColumnControlModelImpl.RESOURCE_TYPE,
				SECTION_NAME, "Column Control section name");
		columnControlModelImpl = resource.adaptTo(ColumnControlModelImpl.class);
		assertEquals("Column Control section name", columnControlModelImpl.getSectionName());
	}

	/**
	 * Test method for
	 * {@link ColumnControlModelImpl #getIsContainerFluid()}.
	 */
	@Test
	void testIsContainerFluid() throws Exception {
		resource = context.create().resource(page, COLUMN_CONTROL_NODE,
				JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, ColumnControlModelImpl.RESOURCE_TYPE,
				IS_CONTAINER_FLUID, "true");
		columnControlModelImpl = resource.adaptTo(ColumnControlModelImpl.class);
		assertEquals(true, columnControlModelImpl.getIsContainerFluid());
	}

}
