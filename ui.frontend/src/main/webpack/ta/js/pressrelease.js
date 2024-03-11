import { fetchData } from "../../utility/fetchall"
import DOMPurify from "dompurify";

const elementDataSets = document.querySelector(".newscardlistingdetails");
const rootDetailsPath = elementDataSets?.dataset?.rootdetailspath; // Actual API endpoint URL
const cardPerLoad = elementDataSets?.dataset?.loadmorelimit;
const knowMoreCta = elementDataSets?.dataset?.knowmorectalabel;

const cardsContainer = document.querySelector(".ta-press-release__container");
const loadMoreButton = document.querySelector("#press-release-loadmore");
const dataOffset = loadMoreButton?.dataset?.offset;
let offset = dataOffset || 2;

// Function to load career cards
function loadNewsCards() {
	const apiEndpoint = `${rootDetailsPath}.newscardlisting.html?offset=${offset}&cardsPerLoad=${cardPerLoad}`;

	fetchData(apiEndpoint).then((data) => {
		const newsCardListDetails = data.cardList;

		// Clear existing content before inserting new cards
		cardsContainer.innerHTML = '';

		// Render career cards using ES6 template
		newsCardListDetails.forEach((cardData) => {
			const cardHtml =
				`<li class = "ta-press-release__list">
      <article class="ta-press-release__grid-item">
       					<picture class="ta-press-release__image">
       						<source
       								media="(min-width: 901px)"
       								srcset="${cardData.cardImage}"
       						/>
       						<source
       								media="(min-width: 768px)"
       								srcset="${cardData.cardImage}"
       						/>
       						<source
       								media="(min-width: 320px)"
       								srcset="${cardData.cardImage}"
       						/>
       						<img
       								src="${cardData.cardImage}"
       								alt="${cardData.cardImageAltText}"
       						/>
       					</picture>
       					<div class="ta-press-release__content">
       						<p class="body6">${cardData.date}</p>
       						<h3 class="captionL">${cardData.heading}</h3>
       						<p class="body5">${cardData.shortDescription}</p>
       					</div>
       					<a href="${cardData.pagePath}"
       					   class="button--small ta-press-release__link has-arrow">
       						<span class="cta-link__text">${knowMoreCta}</span>
       					</a>
       				</article>
       				</li>`;

			const sanitisedHtml = DOMPurify.sanitize(cardHtml);
			// Append the card to the container
			cardsContainer.insertAdjacentHTML("beforeend", sanitisedHtml);
		});


		// Update offset for the next batch of cards
		offset++;

		// Disable the "Load More" button if all cards are loaded
		if (newsCardListDetails.length === parseInt(data.totalCards)) {
			loadMoreButton.style.display = "none";
		}
	});
}

// Load more button click event
loadMoreButton?.addEventListener("click", function () {
	loadNewsCards();
});
