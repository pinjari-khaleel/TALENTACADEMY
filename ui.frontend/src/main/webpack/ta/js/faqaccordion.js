import { fetchData } from "../../utility/fetchall"
import { ntaToggleAccordion } from "./accordion"
import DOMPurify from "dompurify";

const rootPath = document.querySelector(".faq-rootPath")?.dataset?.rootpath; // Replace with your actual API endpoint
const faqContainer = document.querySelector(".faq-container");
const loadMoreButton = document.querySelector("#load-more-faq");
const dataOffset = document.querySelector("#load-more-faq")?.dataset
  ?.offset;
let offset = dataOffset ? dataOffset : 8;

// Function to load career cards
function loadFaqs() {
  const apiEndpoint = `${rootPath}.faq.html?offset=${offset}`;
  fetchData(apiEndpoint).then((data) => {
    const faqListDetails = data.faq;

    // Clear existing content before inserting new cards
    faqContainer.innerHTML = "";

    // Render career cards using ES6 template
    faqListDetails.forEach((faqData) => {
      const faqHtml = `<li>
      <div class="ta-accordion">
      <div class="accordion-item" role="presentation">
        <button class="accordion-item-header" aria-expanded="false">
          <h5 class="captionM">${faqData.question}</h5>
        </button>
        <div
          class="accordion-item-content"
          role="region"
          aria-labelledby="section1-header"
        >
          <p>${faqData.answer}</p>
        </div>
      </div>
    </div></li>
        `;
      
      const sanitisedHtml = DOMPurify.sanitize(faqHtml);
      // Append the card to the container
      faqContainer.insertAdjacentHTML("beforeend", sanitisedHtml);
    });

    // Update offset for the next batch of cards
    offset++;

    // Disable the "Load More" button if all cards are loaded
    if (faqListDetails.length === parseInt(data.totalCount)) {
      loadMoreButton.style.display="none";
    }
    const accordionHeaders = document.querySelectorAll(".accordion-item-header");
    accordionHeaders.forEach(function (header) {
      header.addEventListener("click", function () {
        ntaToggleAccordion(this);
      });
    });
  });
}

// Load more button click event
loadMoreButton?.addEventListener("click", function () {
  loadFaqs();
});
