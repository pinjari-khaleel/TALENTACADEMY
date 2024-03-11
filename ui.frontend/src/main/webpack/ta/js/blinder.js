var accordionTriggers = document.querySelectorAll('.blinder__item');
var accordionContent = document.querySelectorAll('.blinder__description');

for (var i = 0; i < accordionTriggers.length; i++) {
    accordionTriggers[i].addEventListener('click', function() {
        var content = this.getElementsByClassName('blinder__description')[0];

        if (content.classList.contains('isExpanded')) {
            content.classList.remove('isExpanded');
            content.style.maxHeight = 0;
        } else {
            for (var j = 0; j < accordionContent.length; j++) {
                accordionContent[j].classList.remove('isExpanded');
                accordionContent[j].style.maxHeight = 0;
            }
            content.classList.add('isExpanded');
            content.style.maxHeight = content.scrollHeight + 'px';
        }
    });
}