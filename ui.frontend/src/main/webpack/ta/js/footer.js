const footerHeaders = document.querySelectorAll(".footer-accordion__header");
footerHeaders.forEach((footerHeader) => {
	footerHeader.addEventListener("click", function () {
		toggleFooterAccordion(footerHeader);
	});
});
function toggleFooterAccordion(button) {
	const content = button.nextElementSibling;
	const expanded = button.getAttribute("aria-expanded") === "true";
	content.classList.toggle("footer-accordion__open", !expanded);
	button.setAttribute("aria-expanded", !expanded);
	content.setAttribute("aria-hidden", expanded);
}