document.addEventListener("DOMContentLoaded", function () {
  const accordionHeaders = document.querySelectorAll(".accordion-item-header");

  accordionHeaders.forEach(function (header) {
    header.addEventListener("click", function () {
      ntaToggleAccordion(this);
    });
  });
});

export function ntaToggleAccordion(button) {
  const content = button.nextElementSibling;
  const isExpanded = button.getAttribute("aria-expanded") === "true";

  // Close all accordions
  closeAllAccordions();

  button.setAttribute("aria-expanded", !isExpanded);

  // Toggle the "open" class to control visibility
  if (!isExpanded) {
    content.classList.add("accordion-item-content-open");
  } else {
    content.classList.remove("accordion-item-content-open");
  }
}

export function closeAllAccordions() {
  const accordionHeaders = document.querySelectorAll(".accordion-item-header");

  accordionHeaders.forEach(function (header) {
    const content = header.nextElementSibling;
    header.setAttribute("aria-expanded", "false");
    content.classList.remove("accordion-item-content-open");
  });
}