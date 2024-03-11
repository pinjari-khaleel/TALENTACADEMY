package com.talentacademy.core.beans;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import java.util.Collections;
import java.util.List;

@Model(adaptables = Resource.class)
public class GridListingData {

	private String totalCards;
	private String offsetValue;
	private String cursorIdValue;
	List<GridListing> cardList;
	List<CourseDetails> dynamicCardList;

	/**
	 * @return the total cards
	 */
	public String getTotalCards() {
		return totalCards;
	}

	/**
	 * @param totalCards set the total cards
	 */
	public void setTotalCards(String totalCards) {
		this.totalCards = totalCards;
	}

	/**
	 * @return the offset value
	 */
	public String getOffsetValue() {
		return offsetValue;
	}

	/**
	 * @param offsetValue set the offset value
	 */
	public void setOffsetValue(String offsetValue) {
		this.offsetValue = offsetValue;
	}

	/**
	 * @return the List<GridListing>
	 */
	public List<GridListing> getCardList() {
		return Collections.unmodifiableList(cardList);
	}

	/**
	 * @param cardList set the card list
	 */
	public void setCardList(List<GridListing> cardList) {
		this.cardList = Collections.unmodifiableList(cardList);
	}

		/**
	 * @return the List<CourseDetails>
	 */
	public List<CourseDetails> getDynamicCardList() {
		return Collections.unmodifiableList(dynamicCardList);
	}

	/**
	 * @param dynamicCardList set the card list
	 */
	public void setDynamicCardList(List<CourseDetails> dynamicCardList) {
		this.dynamicCardList = Collections.unmodifiableList(dynamicCardList);
	}

	/**
	 * @return the cursor ID
	 */
	public String getCursorIdValue() {
		return cursorIdValue;
	}

	/**
	 * @param cursorIdValue set the the cursor ID
	 */
	public void setCursorIdValue(String cursorIdValue) {
		this.cursorIdValue = cursorIdValue;
	}

}