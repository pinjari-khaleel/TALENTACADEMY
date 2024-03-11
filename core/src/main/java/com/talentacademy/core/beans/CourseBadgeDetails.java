package com.talentacademy.core.beans;

/**
 * Course specific badge details Bean.
 */
public class CourseBadgeDetails {

    String badgeName;

    String badgeImage;

    String badgeState;

    /**
     * @return The Badge Name
     */
    public String getBadgeName() {
        return badgeName;
    }

    /**
     * @param badgeName set the Badge Name
     */
    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
    }

    /**
     * @return The Badge Image
     */
    public String getBadgeImage() {
        return badgeImage;
    }

    /**
     * @param badgeImage set the Badge Image
     */
    public void setBadgeImage(String badgeImage) {
        this.badgeImage = badgeImage;
    }

    /**
     * @return The Badge State
     */
    public String getBadgeState() {
        return badgeState;
    }

    /**
     * @param badgeState set the Badge State
     */
    public void setBadgeState(String badgeState) {
        this.badgeState = badgeState;
    }
}