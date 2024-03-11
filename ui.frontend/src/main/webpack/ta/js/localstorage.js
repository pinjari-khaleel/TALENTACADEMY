// To set the localStorage key-values for sending the courseID or learninProgramId send to form
const buttons = document.querySelectorAll(".button--primary_darkgold");
//reading the values to set in analytics script
const htmlElement = document.querySelector('html');
const langCode = htmlElement.getAttribute('lang');
buttons?.forEach((btn) => {
    btn?.addEventListener("click", function () {
        let datasets = this?.dataset;
        let courseId = datasets?.courseid || '';
        let learningProgramId = datasets?.learningpathwayid || '';

        if ("courseid" in datasets || "learningpathwayid" in datasets) {
            localStorage.setItem("courseId", courseId);
            localStorage.setItem("learningProgramId", learningProgramId);
        }
        
    });
});

// reading submission attribute from confirmation page component - moving user to confirmation element on same page

const confirmationComponent = document.getElementById('confirmation');
const onSamePageFormConfirmationComponent = document.getElementById('same_page_confirmation');
const formErrorElement = document.querySelector(".cmp-form-error__item");

if(onSamePageFormConfirmationComponent !== null && formErrorElement === null){
    scrollToElement(onSamePageFormConfirmationComponent);
}else if(formErrorElement !== null){
    scrollToElement(formErrorElement.closest("form"));
}

if(confirmationComponent !== null) {
    removeLocalStorageLeads(confirmationComponent);
}

if(onSamePageFormConfirmationComponent !== null) {
    removeLocalStorageLeads(onSamePageFormConfirmationComponent);
}

function removeLocalStorageLeads(confirmationElement) {
    if(confirmationElement !== null && confirmationElement.dataset !== null){
        const formSubmissionState = confirmationElement.dataset.formSubmissionState;
        if (formSubmissionState !== "" && JSON.parse(formSubmissionState) === true){
            localStorage.removeItem("courseId");
            localStorage.removeItem("learningProgramId");
            analyticsScript(confirmationElement);
            return true;
        }
    }

    return false;
}

function scrollToElement(element) {
    if(element === null)
        return;

    const headerElement = document.querySelector(".ta-header");
    const headerWrapper = document.querySelector(".ta-header-wrapper");

    let headerBottomPos = 0;

    if(headerElement !== null)
        headerBottomPos = headerBottomPos + headerElement.offsetHeight;
    if(headerWrapper !== null)
        headerBottomPos = headerBottomPos + headerWrapper.offsetHeight;

    const offsetPosition = element.parentElement.getBoundingClientRect().top - headerBottomPos;
    window.scrollTo({top: offsetPosition, behavior: "smooth"});
}

function analyticsScript(confirmationElement){
   if(confirmationElement !== null){
   const formId = sessionStorage.getItem("formId");
   const formName = sessionStorage.getItem("formName");
   const email = confirmationElement.dataset.email;
   const recaptchaResponse = confirmationElement.dataset.recaptcha;
   const consent = sessionStorage.getItem("consent");
         window.dataLayer = window.datalayer || [];
                    window.dataLayer.push({
                               'event': 'generate_lead',
                               'form': {
                                   'form_id': formId,
                                   'form_name': formName,
                                   'form_intent': undefined,
                                   'form_error_message': undefined
                               },
                               'response': {
                                    'message': 'form submitted successfully',
                                    'status': 200
                               },
                               'user': {
                                    'form_id': formId,
                                    'lang_code': langCode,
                                    'contact_permissions': consent,
                                    'email': email,
                                    'g-recaptcha-response': recaptchaResponse
                               }
                               });
}
    sessionStorage.removeItem("formId");
    sessionStorage.removeItem("formName");
    sessionStorage.removeItem("consent");
}
