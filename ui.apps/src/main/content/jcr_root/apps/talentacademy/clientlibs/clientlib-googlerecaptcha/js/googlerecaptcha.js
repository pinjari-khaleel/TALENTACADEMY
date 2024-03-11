var s = document.createElement("script");
s.src = "https://www.google.com/recaptcha/api.js";
s.setAttribute("async","");
s.defer = true;
document.getElementsByTagName('head')[0].appendChild(s);

function captchaCallBack(){
    document.getElementsByClassName("g-recaptcha")[0].closest("form").submit();
}