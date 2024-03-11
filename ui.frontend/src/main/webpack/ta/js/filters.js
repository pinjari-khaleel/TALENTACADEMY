import { loadCareerCards } from "./careercardlisting";
import { closeAllAccordions } from "./accordion";

const filterButton = document.querySelector(".grid-filter-header");
const applyFilterButton = document.querySelector("#applyFilter");
const cancelFilterButton = document.querySelector("#cancelFilter");
const appliedFiltersSection = document.querySelector(".grid-applied-filters");
const FILTER_SESSION_STORAGE_KEY = 'careerCardsFilters';

export let cancelFilterCalled = false;

filterButton?.addEventListener("click", () => {
    if (window.innerWidth > 900) return;
    toggleFilterModal();
});

export let selectedPills = [];

let appliedFilters = [];
let appliedFiltersCalled = false;

const toggleFilterModal = () => {
    const filterModal = document.querySelector(".grid-filters");
    filterModal.classList.toggle("grid-filters-open");
};

const filterPills = document.querySelectorAll(".ta-pill");
filterPills?.forEach((pill) => {
    pill?.addEventListener("click", function () {
        filterSelected(this);
    });

});

const saveFiltersHistory = (filters=[], pills=[])=>{
    sessionStorage.setItem(
        FILTER_SESSION_STORAGE_KEY,
        JSON.stringify({
            appliedFilters: filters,
            selectedPills: pills
        })
    );
}

const clearFiltersHistory = () => {
    sessionStorage.removeItem(FILTER_SESSION_STORAGE_KEY);
}

const getFiltersHistory = () => {
    let filters = sessionStorage.getItem(FILTER_SESSION_STORAGE_KEY);
    filters = filters?JSON.parse(filters):{};

    return ({
        appliedFilters: filters?.appliedFilters || [],
        selectedPills: filters?.selectedPills || []
    })
}

function filterSelected(pill) {

    appliedFiltersCalled = false;
    const filterTag = pill.getAttribute("data-filtertag");
    const filterTagIndex = selectedPills.indexOf(filterTag);

    if (filterTagIndex === -1) {
        // FilterTag not in selectedPills array, so add it and add the class
        selectedPills.push(filterTag);
        pill.classList.add("ta-pill-selected");
        appliedFilters.push({ pillName: pill?.innerHTML, pillTag: filterTag });

    } else {
        // FilterTag is already in selectedPills array, so remove it and remove the class
        selectedPills = selectedPills.filter((tag) => tag !== filterTag);
        let appliedFilterIndex = appliedFilters.findIndex((filter) => filter.pillTag === filterTag);

        if (appliedFilterIndex !== -1) {
            appliedFilters = appliedFilters.filter((filter) => filter.pillTag !== filterTag);
        }

        pill.classList.remove("ta-pill-selected");

        filterPills.forEach((pill) => {
            if (pill.getAttribute("data-filtertag") === filterTag) {
                pill.classList.remove("ta-pill-selected");
            }

        });
    }
    saveFiltersHistory(appliedFilters, selectedPills)
}

const applyFiltersButtonHandler = () => {
    appliedFiltersSection.innerHTML = "";
    appliedFilters.forEach((filter) => {
        const pillHTML = document.createElement("li");
        pillHTML.innerHTML = `<button class="ta-pill ta-pill-selected" data-filtertag="${filter.pillTag}">${filter.pillName}</button>`;
        appliedFiltersSection.appendChild(pillHTML);    
    });
    saveFiltersHistory(appliedFilters, selectedPills)

    if (selectedPills.length > 0 && !appliedFiltersCalled) {
        appliedFiltersCalled = true;
        loadCareerCards(selectedPills);
    }
    closeAllAccordions();
    if (window.innerWidth < 900) {
        toggleFilterModal();
    }
}

applyFilterButton?.addEventListener("click", applyFiltersButtonHandler);

cancelFilterButton?.addEventListener("click", function () {
    if (selectedPills.length > 0) {
        clearSelectedPills();
        clearFiltersHistory()
        loadCareerCards(selectedPills);
    }
    closeAllAccordions();
    if (window.innerWidth < 900) {
        toggleFilterModal();
    }
});

function clearSelectedPills() {
    cancelFilterCalled = true;
    const allPills = document.querySelectorAll(".ta-pill");
    allPills.forEach((pill) => {
        pill.classList.remove("ta-pill-selected");
    });

    selectedPills.length = 0;
    appliedFilters.length = 0;
    appliedFiltersSection.innerHTML = "";
}

const filtersContainer = document.querySelector(".grid-filters");

if (filtersContainer) {
    const gridFilterButtons = document.querySelector(".grid-filter-buttons");
    const accordionHeaders = document.querySelectorAll(".accordion-item-header");

    const observer = new MutationObserver(function () {
        updateGridFilterButtonsDisplay();
    });

    const observerConfig = { attributes: true, subtree: true };
    observer.observe(filtersContainer, observerConfig);

    function updateGridFilterButtonsDisplay() {
        const isAnyAccordionExpanded = Array.from(accordionHeaders).some((header) => header.getAttribute("aria-expanded") === "true");

        gridFilterButtons.style.display = isAnyAccordionExpanded ? "flex" : "none";
    }
}

document.querySelector(".grid-applied-filters")?.addEventListener("click", function (event) {
    const clickedElement = event.target;

    // Check if the clicked element is a button within an li
    if (clickedElement.tagName === "BUTTON" && clickedElement.parentElement.tagName === "LI") {
        // Remove from selectedPills array
        filterSelected(clickedElement);

        // Remove the li element from the DOM
        const liElement = clickedElement.parentElement;
        liElement.parentNode.removeChild(liElement);
        loadCareerCards(selectedPills);
    }
});

const postLoadHandler = ()=>{
    let oldFilters = getFiltersHistory();
    appliedFilters = [...oldFilters?.appliedFilters];
    selectedPills = [...oldFilters?.selectedPills];
    if(appliedFiltersSection){
        applyFiltersButtonHandler();
    }
}

document.addEventListener('DOMContentLoaded', postLoadHandler)