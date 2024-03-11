package com.talentacademy.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;

import com.talentacademy.core.constants.ApplicationConstants;

import static com.talentacademy.core.constants.ApplicationConstants.HTML_EXTENSION;


/**
 * <p>
 * Utility file for the common methods of application
 * </p>
 * 
 */
public final class ApplicationUtil {

    /**
     * Private Constructor.
     */
    private ApplicationUtil() {
    }

    /**
     * Gets the Valid URL
     *
     * @param url
     * @param resolver resource resolver
     * @return valid URL with extension
     */
    public static String getValidURL(String url, ResourceResolver resolver) {
        if (url == null || StringUtils.isBlank(url) || resolver == null) {
            return url;
        }
        if (!url.startsWith(ApplicationConstants.DAM_ROOT_URI)
                && url.startsWith(ApplicationConstants.CONTENT_ROOT_URI)) {
            if (url.contains(ApplicationConstants.HTML_EXTENSION)) {
                url = resolver.map(url);
            } else {
                url = addExtension(url, resolver);
            }
        }
        return url;
    }

    /**
     * Adds extension to the URL
     *
     * @param url url
     * @param resolver resource resolver
     * @return URL with extension
     */
    private static String addExtension(final String url, final ResourceResolver resolver) {
        String newURL;
        if (url.contains(ApplicationConstants.HTML_UNICODE_QUESTION)) {
            String[] urlSplit = url.split("\\?");
            newURL = urlSplit[0] + ".html?" + urlSplit[1];
        } else if (url.contains(ApplicationConstants.HASH_TAG)) {
            String[] urlSplit = url.split(ApplicationConstants.HASH_TAG);
            newURL = urlSplit[0] + ".html#" + urlSplit[1];
        } else {
            newURL = url + ApplicationConstants.HTML_EXTENSION;
        }
        return resolver.map(newURL);
    }

    /**
     * @param pathStr
     * @return
     */
    public static String getCountryFromPath(String pathStr) {
        String[] path = pathStr.split(ApplicationConstants.SLASH);
        String country = path[3];
        if (country.equals(ApplicationConstants.LANGUAGE_MASTERS))
            country = ApplicationConstants.COUNTRY_US;
        return country;
    }

    /**
     * @param pathStr
     * @return
     */
    public static String getLanguageFromPath(String pathStr) {
        String[] path = pathStr.split(ApplicationConstants.SLASH);
        return path[4];
    }

    /**
     * @param pathStr
     * @return
     */
    public static String getCurrentPageLocale(String currentPagePath) {
    		String countryCode = getCountryFromPath(currentPagePath);
    		String languageCode = getLanguageFromPath(currentPagePath);
    		String currentPageLocale = languageCode.toLowerCase() + "-" + countryCode.toUpperCase();
    		
    		return currentPageLocale;
    }
 
    
    /**
     * @param strNum
     * @return boolean
     */
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * @param request
     * @return int
     */
    public static int getOffSetValue(String cardsPerLoad, String  offset) {

        int cardSize = ApplicationConstants.CARD_SIZE;
        if (isNumeric(cardsPerLoad) && StringUtils.isNotEmpty(cardsPerLoad)) {
            cardSize = Integer.parseInt(cardsPerLoad);
        }

        int offsetValue = 1;
        if (!isNumeric(offset)) {
            offset = "1";
        }
        offsetValue = Integer.parseInt(offset) >= 1 ? Integer.parseInt(offset) : 1;
        offsetValue = offsetValue * cardSize;

        return offsetValue;
    }

    /**
     * Returns a string from a string array
     * @param array
     * @param concatChar concatenating character
     * @param arrayIndex position from where the concatenation starts
     * @return
     */
    public static String getStringFromArrayConcatenatingChar(String[] array, String concatChar, int arrayIndex){
        StringBuilder builder = new StringBuilder();

        for(int i = arrayIndex; i < array.length; i++){
            builder.append(array[i]);
            builder.append(concatChar);
        }

        return StringUtils.removeEnd(builder.toString(),concatChar);
    }

    /**
     * Returns the first string of an array after performing a split
     * @param str to be split
     * @param splitChar
     * @return
     */
    public static String getFirstStringFromArraySplit(String str, String splitChar){
        if(!str.contains(splitChar))
            return str;

        String[] strArray = str.split(splitChar);

        return strArray[0];
    }

    /**
     * Returns the second position of the split array for the given requestUri
     * @param requestUri
     * @return
     */
    public static String getSecondElementFromSplitUri(String requestUri) {
        if(requestUri == null)
            return null;

        String[] requestUriArray = requestUri.split(HTML_EXTENSION);
        if(requestUriArray.length > 1){
            return requestUriArray[1];
        }

        return null;
    }
    /**
     * Returns the lowercase string separated by hyphen
     * @param input
     * @return
     */
    public static String getLowerCaseStringWithHyphen(String input) {
    	if(StringUtils.isNotBlank(input)) {
    		String lowercaseString = input.toLowerCase();
    		return lowercaseString.replace(' ', '-');
    	}
		return input;
    }

    /**
     * replace the + in country code with 00
     * @param input
     * @return
     */
    public static String createStandardPhoneNumberCode(String input) {
        String standardCountryCode = input;
    	if(StringUtils.isNotBlank(input) && StringUtils.startsWith(input, "+")) {
    		standardCountryCode = input.replaceFirst("\\+", "00"); 
    	}
		return standardCountryCode;
    }
}
