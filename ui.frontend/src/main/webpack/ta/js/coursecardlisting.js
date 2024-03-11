import { fetchData } from "../../utility/fetchall"
import DOMPurify from 'dompurify';

const elementDataSets = document.querySelector(".courselistingdetails");
const rootDetailsPath = elementDataSets?.dataset?.rootdetailspath; // Actual API endpoint URL
const cardPerLoad = elementDataSets?.dataset?.loadmorelimit;
const primaryCTALabel = elementDataSets?.dataset?.primaryctalabel;
const primaryCTALink = elementDataSets?.dataset?.primaryctalink;
const target = elementDataSets?.dataset?.openinnewtab;
const secondaryCTALabel = elementDataSets?.dataset?.secondaryctalabel;
const secondaryCTALink = elementDataSets?.dataset?.secondaryctalink;
const secondaryCTATarget = elementDataSets?.dataset?.secondaryctatarget;
const badgesLabel = elementDataSets?.dataset?.badgesearned;
const listType = elementDataSets?.dataset?.listtype;
const catalogId = elementDataSets?.dataset?.catalogid || "";
const catalogParam = `&catalogId=${catalogId}`;
const pageCursor = document.querySelector(".page-cursor");
let cursor = pageCursor?.dataset?.cursorid || "";

const cardsContainer = document.querySelector(".grid-listing");
const locale = document.querySelector(".grid-listing.dynamiccourse-listing")?.dataset?.locale;
const loadMoreButton = document.querySelector(".course-listing-container .load-more-button");
const dataOffset = document.querySelector(".course-listing-container .load-more-button")?.dataset?.offset;
let offset = dataOffset || 2;

const hours = elementDataSets?.dataset?.hours;
const minutes = elementDataSets?.dataset?.minutes;
const courses = elementDataSets?.dataset?.courses;

// Function to load course cards for static data
function loadCourseCards() {
  const apiEndpoint = `${rootDetailsPath}.courselisting?offset=${offset}&cardsPerLoad=${cardPerLoad}`;

  fetchData(apiEndpoint).then((data) => {
    const cardListDetails = data.cardList;

    // Clear existing content before inserting new cards      
    cardsContainer.innerHTML = '';

    // Render career cards using ES6 template
    cardListDetails.forEach((cardData) => {
      const cardHtml =
          `<li>
            <div class="course-card">
                <div class="course-card__image">
                    <picture>
                        <source media="(min-width: 1080px)" srcset="${cardData.cardImage}">
                        <source media="(min-width: 768px)" srcset="${cardData.cardImage}">
                        <img src="${cardData.cardImage}" alt="${cardData.cardImageAltText}" />
					</picture>
                    <div class="ta-tag ta-tag_black ta-tag_size-2 bg-white">
                        ${cardData.courseTag}
                    </div>
                </div>
                <div class="course-card__description bg-cream ${cardData.cardImage ? '' : 'course-card__description--left'}">
                    <div class="ta-tag ta-tag_black ta-tag_size-2 bg-white">
                        ${cardData.courseTag}
                    </div>
                    <div class="course-card__title">
                        <h3 class="captionL">${cardData.courseTitle}</h3>
                    </div>
                    <div class="course-card__titles">
                        <h5 class="captionM">${cardData.numberOfCourses}</h5>
                        ${badgesLabel ? 
                            `<div class="badges-section">
                                <h6 class="captionS">${badgesLabel}</h6>
                                <ul class="course-badges">
                                    ${cardData.badges
                                    .map((badge, index) =>
                                        `<li>
                                            <img src="${badge}" alt="badge-${index + 1}" />
                                        </li>`)
                                    .join("")}
                                </ul>
                            </div>`
                            : ""
                        }
                    </div>
                    <div class="course-card__buttons">
                        ${primaryCTALabel ?
                            `<a class="button button--primary_darkgold has-no-arrow button--small cta-link" href="${primaryCTALink}"  target="${target}"
                                data-learningPathwayID="${cardData.learningPathwayID.includes("learningProgram") ? cardData.learningPathwayID : ''}"
                                data-courseID="${cardData.learningPathwayID.includes("course") ? cardData.learningPathwayID : ''}">
                                <span class="cta-link__text">${primaryCTALabel}</span>
                            </a>`
                            : ""
                        }

                        ${secondaryCTALabel ?
                            `<a class="button button--secondary has-no-arrow button--small cta-link" href="${cardData.pagePath}">
                                <span class="cta-link__text">${secondaryCTALabel}</span>
                            </a>`
                            : ""
                        }
                    </div>
                </div>
            </div>
        </li>`;

    const sanitisedHtml = DOMPurify.sanitize(cardHtml);
      // Append the card to the container
      cardsContainer.insertAdjacentHTML("beforeend", sanitisedHtml);
    });


    // Update offset for the next batch of cards
    offset++;

    // Disable the "Load More" button if all cards are loaded
    if (cardListDetails.length === parseInt(data.totalCards)) {
        loadMoreButton.style.display="none";
    }
  });
}

// Function to load course cards for static data
function loadDynamicCourses() {

    const cursorParam = `&pageCursor=${cursor}`;
    const apiEndpoint = `${rootDetailsPath}.courselisting?cardsPerLoad=${cardPerLoad}&listType=${listType}${catalogId ? catalogParam : ""}${cursor ? cursorParam : ""}`;
  
    fetchData(apiEndpoint).then((data) => {
      const dynamicCardList = data.dynamicCardList;
  
      // Render career cards using ES6 template
      dynamicCardList.forEach((cardData, index) => {
        const cardHtml =
            `<li>
              <div class="course-card">
                ${cardData.imageUrl ? 
                    `<div class="course-card__image">
                        <picture>
                            <source media="(min-width: 1080px)" srcset="${cardData.imageUrl}">
                            <source media="(min-width: 768px)" srcset="${cardData.imageUrl}">
                            <img src="${cardData.imageUrl}" alt="Course-image-${index + 1}" />
                        </picture>
                        <div class="ta-tag ta-tag_black ta-tag_size-2 bg-white">
                            ${cardData.loType}
                        </div>
                    </div>` : ""
                    }
                    
                  <div class="course-card__description bg-cream ${cardData.imageUrl ? '' : 'course-card__description--left'}">
                      <div class="ta-tag ta-tag_black ta-tag_size-2 bg-white">
                          ${cardData.loType}
                      </div>
                      <div class="course-card__title">
                        ${cardData?.courseLocalizedDataList ? cardData.courseLocalizedDataList.filter(localizedData => 
                                localizedData.locale === locale && localizedData.title)
                                .map(localizedData => `<h3 class="captionL">${localizedData.title}</h3>`)
                                .join("") : ""
                        }
                      </div>
                      <div class="course-card__titles">
                        ${cardData.subLosCount ? `<h5 class="captionM">${cardData.subLosCount} ${courses}</h5>` : ""}

                        ${cardData.durationHours && cardData.durationMinutes 
                            ? `<h5 class="captionM">${cardData.durationHours} ${hours} ${cardData.durationMinutes} ${minutes}</h5>` 
                            : ""
                        }

                        ${cardData.durationHours && !cardData.durationMinutes 
                            ? `<h5 class="captionM">${cardData.durationHours} ${hours}</h5>` 
                            : ""
                        }

                        ${!cardData.durationHours && cardData.durationMinutes 
                            ? `<h5 class="captionM">${cardData.durationMinutes} ${minutes}</h5>` 
                            : ""
                        }

                        ${badgesLabel ? 
                            `<div class="badges-section">
                                <h6 class="captionS">${badgesLabel}</h6>
                                <ul class="course-badges">
                                    ${cardData?.courseBadgesList ? cardData.courseBadgesList
                                        .map((badges, index) =>
                                            `<li>
                                                <img src="${badges.badgeImage}" alt="badge-${index + 1}" />
                                            </li>`)
                                        .join("") : ""
                                    }
                                </ul>
                            </div>`
                            : ""
                        }
                      </div>
                      <div class="course-card__buttons">
                            ${primaryCTALabel ?
                                `<a class="button button--primary_darkgold has-no-arrow button--small cta-link" href="${primaryCTALink}"  target="${target}"
                                    data-learningPathwayID="${cardData?.id?.includes("learningProgram") ? cardData.id : ''}"
                                    data-courseID="${cardData?.id?.includes("course") ? cardData.id : ''}">
                                    <span class="cta-link__text">${primaryCTALabel}</span>
                                </a>`
                                : ""
                            }
  
                          ${secondaryCTALabel ?
                              `<a class="button button--secondary has-no-arrow button--small cta-link" href="${secondaryCTALink}?loid=${cardData?.id}" target="${secondaryCTATarget}">
                                  <span class="cta-link__text">${secondaryCTALabel}</span>
                              </a>`
                              : ""
                          }
                      </div>
                  </div>
              </div>
          </li>`;

          const sanitisedHtml = DOMPurify.sanitize(cardHtml);
        // Append the card to the container
        cardsContainer.insertAdjacentHTML("beforeend", sanitisedHtml);
      });

      cursor = data?.cursorIdValue;
  
    });
  }

// Load more button click event
loadMoreButton?.addEventListener("click", function () {
    if (listType == "dynamic") {
        loadDynamicCourses();

    } else {
        loadCourseCards();

    }
});
