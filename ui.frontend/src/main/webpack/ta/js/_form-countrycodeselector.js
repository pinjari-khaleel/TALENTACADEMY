const taCustomDropdown = (selector) => {
  const BORDER_ERROR_CLASS = "-hasError";
  let isButtonEvent = false;
  const dropdownButton = document.querySelector(selector);
  const dropdownList = dropdownButton.nextElementSibling;
  const hiddenSelect = dropdownButton
    .closest(".cmp-form-options")
    .querySelector(".cmp-form-options--hidden-field");

  const errorMsg = dropdownButton.getAttribute("data-error-message");
  const fieldSet = dropdownButton.parentElement.parentElement;
  const errorBlock = fieldSet.querySelector(".cmp-form-options__error-message");

  const toggleDropdown = () => {
    const isExpanded = dropdownButton.getAttribute("aria-expanded") === "true";
    dropdownButton.setAttribute("aria-expanded", !isExpanded);
    dropdownList.style.display = isExpanded ? "none" : "block";
    dropdownButton.classList.toggle("expanded", !isExpanded);
    isButtonEvent = true;

    if (!isExpanded) {
      setTimeout(() => {
        dropdownList.querySelector(".custom-dropdown__item").focus();
      }, 100);
    }
  };

  const updateDropdownButton = (item) => {
    const { initials: countryInitials, code: countryCode } = item.dataset;
    const flagSrc = item.querySelector("img").src;
    const countryName = item.querySelector("span").textContent;

    let img = dropdownButton.querySelector("img");
    if (!img) {
      img = document.createElement("img");
      dropdownButton.insertBefore(img, dropdownButton.firstChild);
    }
    img.src = flagSrc;
    img.alt = countryName;

    let initialsElement = dropdownButton.querySelector(
      ".custom-dropdown__country-initials"
    );
    if (!initialsElement) {
      initialsElement = document.createElement("span");
      initialsElement.className = "custom-dropdown__country-initials";

      const codeElement = dropdownButton.querySelector(
        ".custom-dropdown__country-code"
      );
      dropdownButton.insertBefore(initialsElement, codeElement);
    }
    initialsElement.textContent = countryInitials;

    const codeElement = dropdownButton.querySelector(
      ".custom-dropdown__country-code"
    );
    codeElement.textContent = countryCode;
  };

  const updateHiddenSelect = (item) => {
    hiddenSelect.value = item.getAttribute("data-code");
  };

  const validateCountryCode = () => {
    if (!hiddenSelect.value) {
      errorBlock.innerHTML = errorMsg;
      errorBlock.style.display = "block";
      dropdownButton.classList.add(BORDER_ERROR_CLASS);
    } else {
      errorBlock.style.display = "none";
      dropdownButton.classList.remove(BORDER_ERROR_CLASS);
    }
  };

  const closeDropdown = () => {
    dropdownList.style.display = "none";
    dropdownButton.setAttribute("aria-expanded", "false");
    dropdownButton.classList.remove("expanded");
    if (isButtonEvent && hiddenSelect.required) {
      validateCountryCode();
      dropdownButton.closest("form").dispatchEvent(new Event("change"));
      isButtonEvent = false;
    }
  };

  const handleDropdownItemClick = (e) => {
    const item = e.target.closest(".custom-dropdown__item");
    if (item) {
      updateDropdownButton(item);
      updateHiddenSelect(item);
      closeDropdown();
    }
  };

  const handleDocumentClick = (e) => {
    if (
      !dropdownButton.contains(e.target) &&
      !dropdownList.contains(e.target)
    ) {
      closeDropdown();
    }
  };

  const handleKeydown = (e) => {
    const items = Array.from(
      dropdownList.querySelectorAll(".custom-dropdown__item")
    );
    const currentIndex = items.indexOf(document.activeElement);

    switch (e.key) {
      case "ArrowDown":
        e.preventDefault();
        const nextIndex =
          currentIndex < items.length - 1 ? currentIndex + 1 : 0;
        items[nextIndex].focus();
        break;
      case "ArrowUp":
        e.preventDefault();
        const prevIndex =
          currentIndex > 0 ? currentIndex - 1 : items.length - 1;
        items[prevIndex].focus();
        break;
      case "Enter":
      case " ":
        e.preventDefault();
        if (currentIndex !== -1) {
          updateDropdownButton(items[currentIndex]);
          updateHiddenSelect(items[currentIndex]);
          closeDropdown();
        }
        break;
      case "Escape":
        closeDropdown();
        dropdownButton.focus();
        break;
    }
  };

  const bindEvents = () => {
    dropdownButton.addEventListener("click", toggleDropdown);
    dropdownList.addEventListener("click", handleDropdownItemClick);
    document.addEventListener("click", handleDocumentClick);
    dropdownButton.addEventListener("keydown", (e) => {
      if (e.key === "ArrowDown" || e.key === "ArrowUp") {
        e.preventDefault();
        if (dropdownButton.getAttribute("aria-expanded") === "false") {
          toggleDropdown();
        }
      }
    });
    dropdownList.addEventListener("keydown", handleKeydown);
  };

  bindEvents();
};

document.addEventListener("DOMContentLoaded", () => {
  taCustomDropdown(".custom-dropdown__button");
});
