document.addEventListener("DOMContentLoaded", function() {
	const headerSubnav = document.querySelector(".ta-subheader-subsection");
	const headerLogo = document.querySelector(".ta-header-wrapper");
	const headerLinks = document.querySelector(".ta-navigation");
	const menuIcon = document.querySelector("#navigationToggle");
	const tabularNavigation = document.querySelector(".ta-navigation");
	const body = document.querySelector("body");
	const header = document.querySelector(".ta-header");

	let prevScrollPos = window.pageYOffset;

	const spotlight = document.querySelector(".spotlight");
	if (spotlight) {
		body.classList.add("has-spotlight");
	}
	if (!spotlight) {
		headerSubnav.style.backgroundColor = "#1D1F22";
	}
	function handleScroll() {
		const isMenuOpen = headerLinks.classList.contains("ta-header-links-open");
		const currentScrollPos = window.pageYOffset;
		if (currentScrollPos > prevScrollPos) {
			// Scrolling down
			if (window.innerWidth > 1439) {
				headerSubnav.style.transform = "translateY(-50%)";
				headerLogo.style.transform = "translateY(-50%)";
				tabularNavigation.style.transform = "translateY(-200%)";
				headerSubnav.style.backgroundColor = "#1D1F22";
				header.style.height = "120px";
			} else {
				headerLogo.style.backgroundColor = "#1D1F22";
			}
		} else {
			// Scrolling up
			if (window.innerWidth > 1439) {
				headerSubnav.style.transform = "translateY(0)";
				tabularNavigation.style.transform = "translateY(0)";
				headerLogo.style.transform = "translateY(0)";
				headerSubnav.style.backgroundColor = isMenuOpen ? "#000" : "#1D1F22";
				header.style.height = "fit-content";
			} else {
				headerLogo.style.backgroundColor = isMenuOpen ? "#000" : "#1D1F22";
			}
		}

		// Check if the user is back on top of the screen
		if (currentScrollPos === 0 && spotlight && !isMenuOpen) {
			headerSubnav.style.backgroundColor = "transparent";
			headerLogo.style.backgroundColor = "transparent";
		}

		prevScrollPos = currentScrollPos;
	}

	function toggleHeaderLinks() {
		headerLogo.classList.toggle("ta-header-wrapper-open");
		headerLinks.classList.toggle("ta-header-links-open");
		const isMenuOpen = headerLinks.classList.contains("ta-header-links-open");
		headerLogo.style.backgroundColor = isMenuOpen ? "#000" : "#1D1F22";
		if (isMenuOpen) menuIcon.classList.add("menu-icon-close");
		else menuIcon.classList.remove("menu-icon-close");

		document.body.style.overflow = headerLinks.classList.contains(
			"ta-header-links-open"
		)
			? "hidden"
			: "auto";
	}

	menuIcon?.addEventListener?.("click", toggleHeaderLinks);

	// Add a resize event listener to handle changes in screen size
	window.addEventListener("resize", function() {
		// Recalculate on resize
		handleScroll();
	});

	window.addEventListener("scroll", handleScroll);
	handleScroll();
});

// Function to update header-sublinks based on clicked header-link
function updateSublinks(linkText) {
	const activeSublink = document.querySelector(".ta-header-sublinks-active");
	const subHeaderLinks = document.querySelectorAll(".ta-header-sublinks");
	if (linkText) {
		subHeaderLinks.forEach((subHeaderLink) => {
			if (
				subHeaderLink.children[0].innerHTML.toLowerCase() ===
				linkText.toLowerCase()
			) {
				activeSublink.classList.add("ta-header-sublinks-hover-inactive");
				subHeaderLink.classList.add("ta-header-sublinks-hover-active");
			} else {
				subHeaderLink.classList.remove("ta-header-sublinks-hover-active");
				activeSublink.classList.add("ta-header-sublinks-hover-inactive");
			}
		});
	} else {
		activeSublink.classList.remove("ta-header-sublinks-hover-inactive");
		subHeaderLinks.forEach((subHeaderLink) => {
			subHeaderLink.classList.remove("ta-header-sublinks-hover-active");
		});
	}
}

/* 
Uncomment in future if sublinks to be showed on hover 
-----------------------------------------------------
// Event listener for header-link clicks
const headerLinks = document.querySelectorAll(".ta-header-link");
const nav = document.querySelector("header");

headerLinks.forEach((link) => {
  const anchor = link.querySelector("a");

  function handleMouseEnter() {
    if (!anchor.getAttribute("lang")) {
      const linkText = anchor.innerText.trim();
      updateSublinks(linkText);
    }
  }

  function handleMouseOut() {
    const isMouseOutsideHeader = !nav.contains(event.relatedTarget);
    if (isMouseOutsideHeader) {
      updateSublinks();
    }
  }

  link.addEventListener("mouseenter", handleMouseEnter);
  // link.addEventListener("mouseout", handleMouseOut);
  anchor.addEventListener("mouseenter", handleMouseEnter);  
  nav.addEventListener("mouseout", handleMouseOut);
});
*/

// Find all elements with data-cmp-is="headerAccordian"
const headerAccordionElements = document.querySelectorAll(
	'[data-cmp-is="headerAccordian"]'
);

// Attach click event listener to each element
headerAccordionElements.forEach((element) => {
	element.addEventListener("click", function() {
		toggleHeaderAccordion(this); // 'this' refers to the clicked element
	});
});

// Toggle Accordion in menu
function toggleHeaderAccordion(button) {
	const content = button.nextElementSibling;
	const expanded = button.getAttribute("aria-expanded") === "true";

	const allAccordions = document.querySelectorAll(".ta-mobile-nav-link");
	allAccordions.forEach((accordion) => {
		const otherContent = accordion.nextElementSibling;
		if(!otherContent)return;
		otherContent.classList.remove("ta-accordion-open");
		accordion.setAttribute("aria-expanded", "false");
		accordion.classList.remove("list-item-opened");
		otherContent.setAttribute("aria-hidden", "true");
	});

	content.classList.toggle("ta-accordion-open", !expanded);
	button.classList.toggle("list-item-opened", !expanded);
	button.setAttribute("aria-expanded", !expanded);
	content.setAttribute("aria-hidden", expanded);
}