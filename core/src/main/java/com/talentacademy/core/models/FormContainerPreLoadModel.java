package com.talentacademy.core.models;

/**
 * Model to load the request param to check if confirmation message of the form should be handled in same page
 */
public interface FormContainerPreLoadModel {

    /**
     * indicates if request param is present to display confirmation message in same page
     * @return
     */
    Boolean isSameConfirmationPage();

    /**
     * indicates if form was successfully submitted
     * @return
     */
    Boolean isSuccessfulPageSubmission();

    /**
     * returns the shorten form action path
     * @return
     */
    String getFormActionPath();

    /**
     * returns the form email
     * @return
     */
    String getEmail();

    /**
     * returns the form recaptcha token
     * @return
     */
    String getRecaptchaToken();

}
