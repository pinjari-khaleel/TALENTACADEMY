function analyticsScriptInclusion(){
  const form = document.getElementsByTagName("form")[0];
  if(form){
     const divElements = form.querySelectorAll("div");
     if(divElements.length!=0){
      const formId = form.elements["form_id"].value;
      const error = form.querySelector(".cmp-form-error");
      const leadType = form.elements["lead_type"]?.value?.replace(/ /g, "_")?.toLowerCase();
      if(error){
          const errorMessage = form.querySelector(".cmp-form-error__item").textContent;
           window.dataLayer = window.datalayer || [];
            window.dataLayer.push({
                       'event': 'form_error',
                       'form_id': formId,
                       'form_name': `${formId}_${leadType}`,
                       'form_intent': undefined,
                       'form_step': undefined,
                       'form_error': true,
                       'form_error_message': errorMessage
                       });
      }
      else{
           window.dataLayer = window.datalayer || [];
                   window.dataLayer.push({
                       'event': 'form_impression',
                       'form_id': formId,
                       'form_name': `${formId}_${leadType}`,
                       'form_intent': undefined,
                       'form_step': undefined
                       });
      }
  }
  }

}
analyticsScriptInclusion();