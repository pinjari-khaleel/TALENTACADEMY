package com.talentacademy.core.models;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.DamConstants;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.day.cq.dam.api.DamConstants.TIFF_IMAGELENGTH;
import static com.day.cq.dam.api.DamConstants.TIFF_IMAGEWIDTH;
import static com.day.cq.dam.api.s7dam.constants.S7damConstants.S7_IMAGE;
import static com.day.cq.dam.scene7.api.constants.Scene7Constants.PN_S7_DOMAIN;
import static com.day.cq.dam.scene7.api.constants.Scene7Constants.PN_S7_FILE;
import static com.day.cq.dam.scene7.api.constants.Scene7Constants.PN_S7_FILE_STATUS;
import static com.day.cq.dam.scene7.api.constants.Scene7Constants.PN_S7_HEIGHT;
import static com.day.cq.dam.scene7.api.constants.Scene7Constants.PN_S7_TYPE;
import static com.day.cq.dam.scene7.api.constants.Scene7Constants.PN_S7_WIDTH;
import static com.talentacademy.core.models.DynamicMediaModel.DEFAULT_SCHEME;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Dynamic Media Asset properties model.
 */
public class DynamicMediaPropsModel {
	/**
	 * logger
	 */
	private static final Logger log = LoggerFactory.getLogger(DynamicMediaPropsModel.class);

	/**
	 * Status of dynamic media cloud rendition process.
	 */
	public static final String PUBLISH_COMPLETE = "PublishComplete";

	/**
	 * SVG vector mime type "image/svg+xml".
	 */
	public static final String MIME_SVG = "image/svg+xml";

	/**
	 * DXF vector mime type "image/vnd.dxf".
	 */
	public static final String MIME_DXF = "image/vnd.dxf";

	/**
	 * AI vector mime type "application/pdf".
	 */
	public static final String MIME_AI = "application/pdf";

	/**
	 * Immutable list of excluded mime types.
	 */
	public static final List<String> EXCLUDED_MIME_TYPES = Collections
			.unmodifiableList(Arrays.asList(MIME_SVG, MIME_DXF, MIME_AI));

	/**
	 * Dynamic Media URL partial for providing images "is/image".
	 */
	public static final String IS_IMAGE = "is/image/";

	/**
	 * Dynamic Media URL partial for providing video and other types of content
	 * "is/content".
	 */
	public static final String IS_CONTENT = "is/content/";

	/**
	 * Asset object.
	 */
	private Asset asset;

	/**
	 * Dynamic media bare original URL without any params.
	 */
	private String dmBareUrl;

	/**
	 * // * Scene7 status.
	 */
	private String s7status;

	/**
	 * Scene7 asset width.
	 */
	private long s7width;

	/**
	 * Scene7 asset width.
	 */
	private long s7height;

	/**
	 * Scene7 domain.
	 */
	private String s7domain;

	/**
	 * Scene7 path.
	 */
	private String s7path;

	/**
	 * Asset MIME type.
	 */
	private String assetMimeType;

	/**
	 * Is valid to work with Dynamic Media.
	 */
	private boolean valid = true;

	/**
	 * DynamicMediaPropsModel Constructor.
	 * 
	 * @param asset asset
	 */
	public DynamicMediaPropsModel(final Asset asset) {
		this.asset = asset;
		s7status = asset.getMetadataValueFromJcr(PN_S7_FILE_STATUS);
		if (!StringUtils.equals(s7status, PUBLISH_COMPLETE)) {
			valid = false;
			return;
		}

		assetMimeType = asset.getMetadataValueFromJcr(DamConstants.DC_FORMAT);
		if (isNull(assetMimeType) || EXCLUDED_MIME_TYPES.contains(assetMimeType)) {
			valid = false;
			return;
		}
		initUrlFields();

		s7width = parseToLong(TIFF_IMAGEWIDTH, PN_S7_WIDTH);
		s7height = parseToLong(TIFF_IMAGELENGTH, PN_S7_HEIGHT);
	}

	/**
	 * Get field value as long.
	 * 
	 * @param firstFieldName  checked first field
	 * @param secondFieldName checked second field
	 * @return long value of the field
	 */
	private long parseToLong(String firstFieldName, String secondFieldName) {
		String assetWidthText = Optional.ofNullable(asset.getMetadataValueFromJcr(firstFieldName))
				.orElse(asset.getMetadataValueFromJcr(secondFieldName));
		if (nonNull(assetWidthText)) {
			try {
				return Long.parseLong(assetWidthText);
			} catch (NumberFormatException e) {
				log.error("Error during parse image size field.");
			}
		}
		valid = false;
		return 0;
	}

	/**
	 * Initialize URL parts.
	 */
	private void initUrlFields() {
		s7domain = asset.getMetadataValueFromJcr(PN_S7_DOMAIN);
		String s7file = asset.getMetadataValueFromJcr(PN_S7_FILE);
		if (StringUtils.isAnyBlank(s7domain, s7file)) {
			valid = false;
			return;
		}
		String s7type = asset.getMetadataValueFromJcr(PN_S7_TYPE);
		if (StringUtils.isAnyBlank(s7file, s7type)) {
			valid = false;
			return;
		}

		// Domain name cleanup (get rid of http:// and last slash)
		s7domain = RegExUtils.removeFirst(s7domain, "^(https?:)?//");
		s7domain = StringUtils.removeEnd(s7domain, "/");
		if (S7_IMAGE.equals(s7type)) {
			s7path = IS_IMAGE + s7file;
		} else {
			s7path = IS_CONTENT + s7file;
		}
		try {
			dmBareUrl = new URIBuilder().setHost(getS7domain()).setScheme(DEFAULT_SCHEME).setPath(getS7path()).build()
					.toString();
		} catch (URISyntaxException e) {
			log.error("Error", e);
		}
	}

	/**
	 * @return dmBaseUrl
	 */
	public String getDmBareUrl() {
		return dmBareUrl;
	}

	/**
	 * @return s7width
	 */
	public long getS7width() {
		return s7width;
	}

	/**
	 * @return s7height
	 */
	public long getS7height() {
		return s7height;
	}

	/**
	 * @return s7domain
	 */
	public String getS7domain() {
		return s7domain;
	}

	/**
	 * @return s7path
	 */
	public String getS7path() {
		return s7path;
	}

	/**
	 * @return assetMimeType
	 */
	public String getAssetMimeType() {
		return assetMimeType;
	}

	/**
	 * @return valid
	 */
	public boolean isValid() {
		return valid;
	}
}
