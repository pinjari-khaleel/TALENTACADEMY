package com.talentacademy.core.services.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.talentacademy.core.beans.CourseBadgeDetails;
import com.talentacademy.core.beans.CourseDetails;
import com.talentacademy.core.beans.CourseLocalizedData;
import com.talentacademy.core.beans.CourseModule;
import com.talentacademy.core.services.CourseDetailsService;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.talentacademy.core.constants.ALMConstants.*;

/**
 * Service implementation for Dynamic Course Details.
 */
@Component(service = CourseDetailsService.class)
public class CourseDetailsServiceImpl implements CourseDetailsService {

	List<CourseDetails> courseDetails;

	private String currentPageLocale;

	private static final Logger LOGGER = LoggerFactory.getLogger(CourseDetailsServiceImpl.class);

	/**
	 * Get Cursor ID.
	 * 
	 * @param responseStr
	 * @return
	 */
	@Override
	public String getCursorId(String responseStr) {
		JsonObject response = new Gson().fromJson(responseStr, JsonObject.class);
		String cursorId = StringUtils.EMPTY;
		if (response.has(LINKS)) {
			JsonObject linksData = response.get(LINKS).getAsJsonObject();
			if (linksData.has(NEXT)) {
				String nextData = linksData.get(NEXT).getAsString();
				if (nextData.contains(PAGE_CURSOR)) {
					String[] cursorIdOfStr = nextData.split("page\\[cursor\\]\\=");
					cursorId = cursorIdOfStr[1];
				}
			}
		}
		return cursorId;
	}

	/**
	 * Get List of course details.
	 *
	 * @param responseStr       ALM response as string
	 * @param isSingleCourse
	 * @param currentPageLocale
	 * @return courseDetails list
	 */
	@Override
	public List<CourseDetails> getCourseDetails(String responseStr, Boolean isSingleCourse, String currentPageLocale) {
		this.currentPageLocale = currentPageLocale;
		JsonObject response = new Gson().fromJson(responseStr, JsonObject.class);
		JsonArray includeDataList = new JsonArray();

		if (response.has(INCLUDED)) {
			includeDataList = response.getAsJsonArray(INCLUDED);
		}
		if (response.has(DATA)) {
			if (Boolean.TRUE.equals(isSingleCourse)) {
				courseDetails = getSingleCourseDetails(includeDataList, response);
			} else {
				courseDetails = getMultipleCourseDetails(includeDataList, response);
			}
			return courseDetails;
		} else {
			LOGGER.error("Invalid Course Details Response: {}", responseStr);
			return Collections.emptyList();
		}
	}

	/**
	 * Get Single course details.
	 *
	 * @param response        ALM response as JsonObject
	 * @param includeDataList includeDataList JsonArray
	 * @return courseDetails list
	 */
	public List<CourseDetails> getSingleCourseDetails(JsonArray includeDataList, JsonObject response) {
		JsonObject course = response.get(DATA).getAsJsonObject();
		courseDetails = new ArrayList<>();
		courseDetails.add(getCourseData(course, includeDataList));
		return courseDetails;
	}

	/**
	 * Get Multiple course details.
	 *
	 * @param response        ALM response as JsonObject
	 * @param includeDataList includeDataList JsonArray
	 * @return courseDetails list
	 */
	public List<CourseDetails> getMultipleCourseDetails(JsonArray includeDataList, JsonObject response) {
		JsonArray courseData = response.getAsJsonArray(DATA);
		courseDetails = new ArrayList<>();
		for (JsonElement course : courseData) {
			courseDetails.add(getCourseData(course, includeDataList));
		}
		return courseDetails;
	}

	/**
	 * Get CourseDetails of course data.
	 *
	 * @param course          as JsonElement
	 * @param includeDataList as JsonArray
	 * @return CourseDetails
	 */
	public CourseDetails getCourseData(JsonElement course, JsonArray includeDataList) {

		List<CourseLocalizedData> courseLocalizedData = new ArrayList<>();
		List<CourseBadgeDetails> courseBadgeDetails = new ArrayList<>();
		List<String> arabicSkills = new ArrayList<>();
		CourseDetails courseDetailsList = new CourseDetails();
		JsonObject details = course.getAsJsonObject();
		courseDetailsList.setId(details.get(ID).getAsString());
		JsonObject attributes = details.get(ATTRIBUTES).getAsJsonObject();
		populateCourseDuration(courseDetailsList, details, attributes);
		populateCourseLoType(courseDetailsList, attributes);
		populateCourseImage(courseDetailsList, attributes);
		populateCourseSubLoCount(courseDetailsList, attributes);
		populateCourseLocalizedData(courseDetailsList, attributes, courseLocalizedData);
		populateArabicSkills(courseDetailsList, attributes, arabicSkills);
		populateCourseBadges(courseDetailsList, details, includeDataList, courseBadgeDetails);
		populateCourseSkills(courseDetailsList, includeDataList);
		populateCourseModules(courseDetailsList, includeDataList);
		return courseDetailsList;

	}

	/**
	 * fetch the course skills.
	 *
	 * @param courseDetailsList list has course details
	 * @param includeDataList   ALM response included list
	 *
	 */
	private void populateCourseSkills(CourseDetails courseDetailsList, JsonArray includeDataList) {
		List<String> skillsList = new ArrayList<>();
		for (JsonElement element : includeDataList) {
			JsonObject obj = element.getAsJsonObject();
			if (StringUtils.equals(SKILL, obj.get(TYPE).getAsString())) {
				JsonObject attributes = obj.get(ATTRIBUTES).getAsJsonObject();
				if (attributes.has(NAME)) {
					skillsList.add(attributes.get(NAME).getAsString());
				}
			}
		}
		courseDetailsList.setSkillsList(skillsList);
	}

	/**
	 * fetch the list of course modules.
	 *
	 * @param courseDetailsList list has course details
	 * @param includeDataList   ALM response included list
	 *
	 */
	private void populateCourseModules(CourseDetails courseDetailsList, JsonArray includeDataList) {
		List<CourseModule> courseModuleList = new ArrayList<>();
		List<String> formatList = new ArrayList<>();
		for (JsonElement element : includeDataList) {
			JsonObject obj = element.getAsJsonObject();
			if (StringUtils.equals(LEARNING_OBJECT_RESOURCE, obj.get(TYPE).getAsString())) {
				JsonObject attributes = obj.get(ATTRIBUTES).getAsJsonObject();
				String format = attributes.has(RESOURCE_TYPE) ? attributes.get(RESOURCE_TYPE).getAsString() : "";
				if (!formatList.contains(format)) {
					formatList.add(format);
				}
				if (attributes.has(LOCALIZED_META_DATA)) {
					JsonArray localizedMetadata = attributes.get(LOCALIZED_META_DATA).getAsJsonArray();
					courseModuleList = readCourseModuleData(courseModuleList, format, localizedMetadata,
							includeDataList, getResourceId(obj));
				}
			}
		}
		courseDetailsList.setFormatsList(formatList);
		courseDetailsList.setModuleList(courseModuleList);
	}

	/**
	 * fetch the resource Id
	 * 
	 * @param obj ALM LOR Response
	 * @return resourceId
	 */
	private String getResourceId(JsonObject obj) {
		if (obj.has(RELATION_SHIPS) && obj.get(RELATION_SHIPS).getAsJsonObject().has(RESOURCES)) {
			JsonObject resources = obj.get(RELATION_SHIPS).getAsJsonObject().get(RESOURCES).getAsJsonObject();
			if (resources.has(DATA)) {
				JsonArray dataArray = resources.get(DATA).getAsJsonArray();
				for (JsonElement dataElement : dataArray) {
					JsonObject dataObj = dataElement.getAsJsonObject();
					if (dataObj.has(ID))
						return dataElement.getAsJsonObject().get(ID).getAsString();
				}
			}
		}
		return currentPageLocale;

	}

	/**
	 * fetch the course module data.
	 *
	 * @param courseDetailsList list has course details
	 * @param format            type of module format
	 * @param localizedMetadata localized metadata of learning object resource
	 * @param includeDataList   ALM response included list
	 *
	 */
	private List<CourseModule> readCourseModuleData(List<CourseModule> courseModuleList, String format,
			JsonArray localizedMetadata, JsonArray includeDataList, String resourceId) {
		for (JsonElement metaElement : localizedMetadata) {
			CourseModule module = new CourseModule();
			JsonObject metaobj = metaElement.getAsJsonObject();
			module.setTitle(metaobj.has(NAME) ? metaobj.get(NAME).getAsString() : "");
			module.setDescription(metaobj.has(DESCRIPTION) ? metaobj.get(DESCRIPTION).getAsString() : "");
			String locale = metaobj.has(LOCALE) ? metaobj.get(LOCALE).getAsString() : "";
			module.setLocale(locale.equals("en-US") ? "en-SA" : locale);
			module.setFormat(format);
			int duration = getModuleDuration(includeDataList, resourceId);
			int hours = convertSecondsToHours(duration);
			int minutes = convertSecondsToMinutes(duration);
			if (hours >= 1) {
				module.setDurationHours(String.valueOf(hours));
			}
			if (minutes >= 1) {
				module.setDurationMinutes(String.valueOf(minutes));
			}
			if (!courseModuleList.contains(module) && module.getLocale().contains(currentPageLocale))
				courseModuleList.add(module);
		}
		return courseModuleList;
	}

	/**
	 * fetch the course module duration.
	 *
	 * @param response ALM included response
	 * @param name     module name
	 *
	 */
	private int getModuleDuration(JsonArray response, String resourceId) {
		for (JsonElement element : response) {
			JsonObject obj = element.getAsJsonObject();
			if (StringUtils.equals(RESOURCE, obj.get(TYPE).getAsString())
					&& obj.get(ID).getAsString().equals(resourceId)) {
				JsonObject attributes = obj.get(ATTRIBUTES).getAsJsonObject();
				if (attributes.has(DESIRED_DURATION)) {
					return attributes.get(DESIRED_DURATION).getAsInt();
				}
			}
		}
		return 0;
	}

	/**
	 * fetch the course badges.
	 *
	 * @param details            ALM response course object
	 * @param courseDetailsList  list has course details
	 * @param includeDataList    ALM response included list
	 * @param courseBadgeDetails list has the course badge details
	 *
	 */
	private void populateCourseBadges(CourseDetails courseDetailsList, JsonObject details, JsonArray includeDataList,
			List<CourseBadgeDetails> courseBadgeDetails) {
		JsonObject relationShip = details.get(RELATION_SHIPS).getAsJsonObject();
		if (relationShip.has(SKILLS)) {
			JsonObject skills = relationShip.get(SKILLS).getAsJsonObject();
			JsonArray skillsArray = skills.get(DATA).getAsJsonArray();
			skillOrInstanceBadgeDetails(false, skillsArray, LEARNING_OBJECT_SKILL, includeDataList, courseBadgeDetails);
		}
		if (relationShip.has(INSTANCES)) {
			JsonObject instances = relationShip.get(INSTANCES).getAsJsonObject();
			JsonArray instancesArray = instances.get(DATA).getAsJsonArray();
			skillOrInstanceBadgeDetails(true, instancesArray, LEARNING_OBJECT_INSTANCE, includeDataList,
					courseBadgeDetails);

		}
		courseDetailsList.setCourseBadgesList(courseBadgeDetails);
	}

	/**
	 * fetch skill or instance level badge details.
	 *
	 * @param isInstanceBadge       check is instance level
	 * @param skillsOrInstanceArray array from ALM of skills or instances
	 * @param loSkillOrInstance     type of id
	 * @param includeDataList       ALM response included list
	 * @param courseBadgeDetails    has the list of course badge details
	 *
	 */
	private void skillOrInstanceBadgeDetails(Boolean isInstanceBadge, JsonArray skillsOrInstanceArray,
			String loSkillOrInstance, JsonArray includeDataList, List<CourseBadgeDetails> courseBadgeDetails) {
		String learningObjectSkillOrInstanceId = null;

		for (JsonElement skillsOrInstance : skillsOrInstanceArray) {
			CourseBadgeDetails courseBadgeDetailsList = new CourseBadgeDetails();
			JsonObject skillOrInstance = skillsOrInstance.getAsJsonObject();
			if (skillOrInstance.get(TYPE).getAsString().equalsIgnoreCase(loSkillOrInstance)) {
				learningObjectSkillOrInstanceId = skillOrInstance.get(ID).getAsString();
			}
			instanceBadgeDetails(isInstanceBadge, learningObjectSkillOrInstanceId, courseBadgeDetailsList,
					includeDataList, courseBadgeDetails);
			skillBadgeDetails(isInstanceBadge, learningObjectSkillOrInstanceId, courseBadgeDetailsList, includeDataList,
					courseBadgeDetails);
		}
	}

	/**
	 * fetch skill level badge details.
	 *
	 * @param isInstanceBadge                 check is instance level
	 * @param learningObjectSkillOrInstanceId loSkill or Instance ID
	 * @param includeDataList                 ALM response included list
	 * @param courseBadgeDetails              has the list of course badge details
	 *
	 */
	private void skillBadgeDetails(Boolean isInstanceBadge, String learningObjectSkillOrInstanceId,
			CourseBadgeDetails courseBadgeDetailsList, JsonArray includeDataList,
			List<CourseBadgeDetails> courseBadgeDetails) {
		String skillId;
		String badgeID;
		if (Boolean.FALSE.equals(isInstanceBadge)) {
			skillId = skillOrBadgeId(false, LEARNING_OBJECT_SKILL, SKILL_LEVEL, includeDataList,
					learningObjectSkillOrInstanceId);
			badgeID = skillId != null ? skillOrBadgeId(false, SKILL_LEVEL, BADGE, includeDataList, skillId) : null;
			JsonObject badgeDetails = badgeID != null ? badgeDetails(includeDataList, badgeID) : null;
			if (badgeDetails != null && badgeDetails.get(STATE).getAsString().equalsIgnoreCase(ACTIVE)) {
				courseBadgeDetailsList.setBadgeImage(badgeDetails.get(IMAGE_URL).getAsString());
				courseBadgeDetails.add(courseBadgeDetailsList);
			}
		}
	}

	/**
	 * fetch instance level badge details.
	 *
	 * @param isInstanceBadge                 check is instance level
	 * @param learningObjectSkillOrInstanceId loSkill or Instance ID
	 * @param includeDataList                 ALM response included list
	 * @param courseBadgeDetails              has the list of course badge details
	 *
	 */
	private void instanceBadgeDetails(Boolean isInstanceBadge, String learningObjectSkillOrInstanceId,
			CourseBadgeDetails courseBadgeDetailsList, JsonArray includeDataList,
			List<CourseBadgeDetails> courseBadgeDetails) {
		String instanceBadgeID;
		if (Boolean.TRUE.equals(isInstanceBadge)) {
			instanceBadgeID = skillOrBadgeId(true, LEARNING_OBJECT_INSTANCE, BADGE, includeDataList,
					learningObjectSkillOrInstanceId);
			JsonObject badgeDetails = instanceBadgeID != null ? badgeDetails(includeDataList, instanceBadgeID) : null;
			if (badgeDetails != null && badgeDetails.get(STATE).getAsString().equalsIgnoreCase(ACTIVE)) {
				courseBadgeDetailsList.setBadgeImage(badgeDetails.get(IMAGE_URL).getAsString());
				courseBadgeDetails.add(courseBadgeDetailsList);
			}
		}
	}

	/**
	 * fetch Skill or badge id.
	 *
	 * @param isInstanceBadge       check is instance level
	 * @param skillType             type of skill
	 * @param relationShipType      type of relation
	 * @param includeDataList       ALM response included list
	 * @param learningObjectSkillId loSkillId
	 *
	 */
	private String skillOrBadgeId(Boolean isInstanceBadge, String skillType, String relationShipType,
			JsonArray includeDataList, String learningObjectSkillId) {
		String skillOrBadgeId = null;
		for (JsonElement includedData : includeDataList) {
			JsonObject includeData = includedData.getAsJsonObject();
			String type = includeData.get(TYPE).getAsString();
			String id = includeData.get(ID).getAsString();
			if (type.equalsIgnoreCase(skillType) && learningObjectSkillId.equalsIgnoreCase(id)
					&& (includeData.has(RELATION_SHIPS))
					&& (Boolean.FALSE.equals(isInstanceBadge)
							|| (Boolean.TRUE.equals(isInstanceBadge) && includeData.get(ATTRIBUTES).getAsJsonObject()
									.get("isDefault").getAsString().equalsIgnoreCase("true")))) {
				JsonObject includedRelationShip = includeData.get(RELATION_SHIPS).getAsJsonObject();
				if (includedRelationShip.has(relationShipType)) {
					JsonObject skillLevel = includedRelationShip.get(relationShipType).getAsJsonObject();
					skillOrBadgeId = skillLevel.get(DATA).getAsJsonObject().get(ID).getAsString();
				}
			}
		}
		return skillOrBadgeId;
	}

	/**
	 * fetch badge details.
	 * 
	 * @param badgeID         badge ID
	 * @param includeDataList ALM response included list
	 *
	 */
	private JsonObject badgeDetails(JsonArray includeDataList, String badgeID) {
		JsonObject badgeAttributes = null;
		for (JsonElement includedData : includeDataList) {
			JsonObject includeData = includedData.getAsJsonObject();
			String type = includeData.get(TYPE).getAsString();
			String id = includeData.get(ID).getAsString();
			if (type.equalsIgnoreCase(BADGE) && badgeID.equalsIgnoreCase(id)) {
				badgeAttributes = includeData.get(ATTRIBUTES).getAsJsonObject();

			}
		}
		return badgeAttributes;
	}

	/**
	 * fetch Duration.
	 * 
	 * @param courseDetailsList list of course details
	 * @param details           ALM response data jsonObject
	 * @param attributes        ALM response attributes jsonObject
	 *
	 */
	private void populateCourseDuration(CourseDetails courseDetailsList, JsonObject details, JsonObject attributes) {
		if (attributes.has(DURATION)) {
			int duration = attributes.get(DURATION).getAsInt();
			int hours = convertSecondsToHours(duration);
			int minutes = convertSecondsToMinutes(duration);
			if (hours >= 1) {
				courseDetailsList.setDurationHours(String.valueOf(hours));
			}
			if (minutes >= 1) {
				courseDetailsList.setDurationMinutes(String.valueOf(minutes));
			}
			if (courseDetailsList.getId().contains(LEARNING_PROGRAM)) {
				setNoOfCourses(courseDetailsList, details);
			}
		}

	}

	/**
	 * Set the no of Courses.
	 * 
	 * @param courseDetailsList list of course details
	 * @param details           ALM data response
	 *
	 */
	private void setNoOfCourses(CourseDetails courseDetailsList, JsonObject details) {
		if (details.has(RELATION_SHIPS)) {
			JsonObject relationShipJson = details.get(RELATION_SHIPS).getAsJsonObject();
			if (relationShipJson.has(SUB_LOS)) {
				JsonObject subLOjson = relationShipJson.get(SUB_LOS).getAsJsonObject();
				if (subLOjson.has(DATA)) {
					JsonArray subLOdata = subLOjson.get(DATA).getAsJsonArray();
					int noOfCourses = subLOdata.size();
					courseDetailsList.setNoOfCourses(String.valueOf(noOfCourses));
				}
			}
		}
	}

	/**
	 * fetch Lo Type of course.
	 * 
	 * @param courseDetailsList list of course details
	 * @param attributes        ALM response attributes jsonObject
	 *
	 */
	private void populateCourseLoType(CourseDetails courseDetailsList, JsonObject attributes) {
		String loType = attributes.get(LO_TYPE).getAsString();
		if (loType.equalsIgnoreCase(LEARNING_PROGRAM)) {
			loType = "Learning Pathway";
		}
		courseDetailsList.setLoType(loType);
	}

	/**
	 * fetch image of course.
	 * 
	 * @param courseDetailsList list of course details
	 * @param attributes        ALM response attributes jsonObject
	 *
	 */
	private void populateCourseImage(CourseDetails courseDetailsList, JsonObject attributes) {
		if (attributes.has(IMAGE_URL)) {
			courseDetailsList.setImageUrl(attributes.get(IMAGE_URL).getAsString());
		}
	}

	/**
	 * fetch sub lo count of learning path.
	 * 
	 * @param courseDetailsList list of course details
	 * @param attributes        ALM response attributes jsonObject
	 *
	 */
	private void populateCourseSubLoCount(CourseDetails courseDetailsList, JsonObject attributes) {
		if (attributes.has(SECTIONS)) {
			JsonArray subLos = attributes.get(SECTIONS).getAsJsonArray();
			courseDetailsList.setSubLosCount(subLos.get(0).getAsJsonObject().get(LO_IDS).getAsJsonArray().size());
		}
	}

	/**
	 * fetch locale specific data.
	 * 
	 * @param courseDetailsList list of course details
	 * @param attributes        ALM response attributes jsonObject
	 *
	 */
	private void populateCourseLocalizedData(CourseDetails courseDetailsList, JsonObject attributes,
			List<CourseLocalizedData> courseLocalizedData) {
		JsonArray localizedCourseDetails = attributes.get(LOCALIZED_META_DATA).getAsJsonArray();
		for (JsonElement localizedCourseDetail : localizedCourseDetails) {
			JsonObject obj = localizedCourseDetail.getAsJsonObject();
			CourseLocalizedData courseLocalizedDataList = new CourseLocalizedData();
			courseLocalizedDataList.setTitle(obj.get(NAME).getAsString());
			String locale = obj.get(LOCALE).getAsString();
			courseLocalizedDataList.setLocale(locale.equals("en-US") ? "en-SA" : locale);
			courseLocalizedDataList
					.setRichTextOverview(obj.has(RICH_TEXT_OVERVIEW) ? obj.get(RICH_TEXT_OVERVIEW).getAsString() : "");
			courseLocalizedData.add(courseLocalizedDataList);
		}
		courseDetailsList.setCourseLocalizedDataList(courseLocalizedData);
	}
	/**
	 * fetch Arabic Skills.
	 *
	 * @param courseDetailsList list of course details
	 * @param attributes        ALM response attributes jsonObject
	 *
	 */
	private void populateArabicSkills(CourseDetails courseDetailsList, JsonObject attributes, List<String> arabicSkills) {
		if(attributes.has(CATALOG_LABELS)) {
			JsonArray catalogLabelsDetails = attributes.get(CATALOG_LABELS).getAsJsonArray();
			for (JsonElement catalogLabelDetails : catalogLabelsDetails) {
				JsonObject obj = catalogLabelDetails.getAsJsonObject();
				if (obj.get(NAME).getAsString().equalsIgnoreCase(ARABIC_SKILLS)) {
					JsonArray skills = obj.get(VALUES).getAsJsonArray();
					for (JsonElement skill : skills) {
						arabicSkills.add(skill.getAsString());
					}
				}
				courseDetailsList.setArabicSkills(arabicSkills);
			}
		}
	}

	/**
	 * convert seconds to hours
	 * 
	 * @param seconds seconds
	 *
	 */
	private int convertSecondsToHours(int seconds) {
		return seconds / 3600;
	}

	/**
	 * convert seconds to minutes
	 * 
	 * @param seconds seconds
	 *
	 */
	private int convertSecondsToMinutes(int seconds) {
		return (seconds % 3600) / 60;
	}
}
