package com.talentacademy.core.models;

import com.talentacademy.core.beans.ContentDetails;
import java.util.List;

public interface ContentBlockModel {

	/**
	 * @return The header
	 */
	String getHeader();

	/**
	 * @return The description
	 */
	String getDescription();

	/**
	 * @return The fileReferencece
	 */
	String getFileReference();

	/**
	 * @return The altText
	 */
	String getAltText();

	/**
	 * @return The contentDetails
	 */
	List<ContentDetails> getContentDetails();

	/**
	 * @return The shortDescription
	 */
	String getShortDescription();

	/**
	 * @return The findOutMoreLabel
	 */
	String getFindOutMoreLabel();

	/**
	 * @return The findOutMoreLink
	 */
	String getFindOutMoreLink();

	/**
	 * @return The pretitle
	 */
	String getPretitle();

	/**
	 * @return The title
	 */
	String getTitle();

	/**
	 * @return The descriptionText
	 */
	String getDescriptionText();
	
}
