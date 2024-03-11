package com.talentacademy.core.models;

import javax.annotation.PostConstruct;
import com.day.cq.dam.api.Asset;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import static java.util.Objects.isNull;

/**
 * Dynamic Media Sling Model.
 */
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DynamicMediaModel {

	/**
	 * The default scheme for all Dynamic Media request "https".
	 */
	public static final String DEFAULT_SCHEME = "https";

	/**
	 * The width param.
	 */
	public static final String WIDTH = "?wid=";

	/**
	 * the height param.
	 */
	public static final String HEIGHT = "&hei=";

	/**
	 * Source url.
	 */
	private String src;

	@SlingObject
	ResourceResolver resourceResolver;

	@RequestAttribute(name = "assetPath")
	String assetPath;

	@RequestAttribute(name = "mobileWidth")
	String mobileWidth;

	@RequestAttribute(name = "desktopWidth")
	String desktopWidth;

	private String desktopImage;

	private String mobileImage;

	/**
	 * init method.
	 *
	 */
	@PostConstruct
	private void init() {
		src = assetPath;
		Resource assetResource = resourceResolver.getResource(assetPath);
		if (isNull(assetResource)) {
			return;
		}
		Asset asset = assetResource.adaptTo(Asset.class);
		if (isNull(asset)) {
			return;
		}

		DynamicMediaPropsModel props = new DynamicMediaPropsModel(asset);
		if (!props.isValid()) {
			return;
		}
		long maxWidth = props.getS7width();
		long maxHeight = props.getS7height();
		if (StringUtils.isNotBlank(desktopWidth) && Integer.valueOf(desktopWidth) < maxWidth) {
			long height = maxHeight * Integer.valueOf(desktopWidth) / maxWidth;
			desktopImage = props.getDmBareUrl() + WIDTH + desktopWidth + HEIGHT + height;
		} else {
			desktopImage = props.getDmBareUrl() + WIDTH + maxWidth;
		}
		if (StringUtils.isNotBlank(mobileWidth) && Integer.valueOf(mobileWidth) < maxWidth) {
			long height = maxHeight * Integer.valueOf(mobileWidth) / maxWidth;
			mobileImage = props.getDmBareUrl() + WIDTH + mobileWidth + HEIGHT + height;
		} else {
			mobileImage = props.getDmBareUrl() + WIDTH + maxWidth;
		}
		src = mobileImage;
	}

	/**
	 * get image src
	 * 
	 * @return src
	 */
	public String getSrc() {
		return src;
	}
	
	/**
	 * get dynamic media desktop width image
	 * 
	 * @return desktopImage
	 */
	public String getDesktopImage() {
		return desktopImage;
	}
	
	/**
	 * get dynamic media mobile width image
	 * 
	 * @return mobileImage
	 */
	public String getMobileImage() {
		return mobileImage;
	}

}
