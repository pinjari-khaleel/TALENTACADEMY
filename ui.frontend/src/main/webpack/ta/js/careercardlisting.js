import { fetchData } from "../../utility/fetchall"
import { selectedPills, cancelFilterCalled } from "./filters";
import DOMPurify from 'dompurify';

const elementDataSets = document.querySelector(".joblistingdetails");
const rootDetailsPath = elementDataSets?.dataset?.rootdetailspath; // Actual API endpoint URL
const cardPerLoad = elementDataSets?.dataset?.loadmorelimit;
const knowMoreCta = elementDataSets?.dataset?.knowmorectalabel;
const badgesLabel = elementDataSets?.dataset?.badgesearned;

const futureposition = elementDataSets?.dataset?.futureposition;
const activelyrecruiting = elementDataSets?.dataset?.activelyrecruiting;
const rolescount = elementDataSets?.dataset?.rolescount;

const careerListingHeader = document.querySelector(".grid-listing-header h4");

const cardsContainer = document.querySelector(".grid-listing");
const loadMoreButton = document.querySelector(".career-listing-container .load-more-button");
const dataOffset = document.querySelector(".career-listing-container .load-more-button")?.dataset?.offset;
let offset = dataOffset || 1;
let loadMoreClicked = 0;

// Function to load career cards
export function loadCareerCards(selectedPills=[]) {
  if (cancelFilterCalled && loadMoreClicked == 1) {
    offset = 1;
  }
  const apiEndpoint = `${rootDetailsPath}.careerlisting.html?offset=${offset}&cardsPerLoad=${cardPerLoad}${
    selectedPills?.length ? `&filters=${selectedPills.join(",")}` : ""
  }`;

  fetchData(apiEndpoint).then((data) => {
    const cardListDetails = data.cardList;

    // Clear existing content before inserting new cards      
    cardsContainer.innerHTML = '';

    // Render career cards using ES6 template
    cardListDetails.forEach((cardData) => {
      const cardHtml = 
      `<li>
        <div class="ta-career-cardlisting">
          <!-- Include your handlebars here based on the provided template -->
          <div class="ta-tag ta-tag_small ta-tag_with-border ${cardData.recruitmentStatus === "futureposition"
          ? "ta-tag_blue"
          : "ta-tag_orange"
        }">${cardData.recruitmentStatus === "futureposition" ? futureposition : activelyrecruiting}</div>
          <div class="career-card-content">
            <h4 class="title6">${cardData.jobTitle}</h4>
            <p class="captionS">${cardData.sector}</p>
            <p class="captionL">${cardData.experienceLevel}</p>
            <div class="badges-section">
              <p class="captionS">${badgesLabel}:</p>
              <div class="course-images-wrapper">
                ${cardData.badges
                    .map((badge, index) => `<img src="${badge}" alt="Badge-${index+1}"/>`)
                    .join("")}
              </div>
            </div>
          </div>

          <a class="button button--primary_darkgold has-no-arrow button--small cta-link" href="${cardData.pagePath}">
              <span class="cta-link__text">${knowMoreCta}</span>
          </a>
          
        </div>
      </li>`;
      
      const sanitisedHtml = DOMPurify.sanitize(cardHtml);

      // Append the card to the container
      cardsContainer.insertAdjacentHTML("beforeend", sanitisedHtml);
    });

    careerListingHeader.innerHTML = `${data.totalCards} ${rolescount}`;

    // Disable the "Load More" button if all cards are loaded
    if(loadMoreButton != null || loadMoreButton != undefined) {

      if (cardListDetails.length === parseInt(data.totalCards)) {
        loadMoreButton.style.display="none";

      } else {
        loadMoreButton.style.display="block";

      }
    }
  });
}

// Load more button click event
loadMoreButton?.addEventListener("click", function () {

  // Update offset for the next batch of cards
  offset++;
  loadMoreClicked++;

  loadCareerCards(selectedPills);
});
