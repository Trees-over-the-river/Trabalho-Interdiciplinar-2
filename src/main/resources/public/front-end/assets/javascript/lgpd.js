const lgpdContainer = document.querySelector('.lgpd-cookies');
const lgpdButton = document.querySelector('.cookie_ok');
const lgpdPref = window.localStorage.getItem('cookie');

if(lgpdPref !== 'true') {
    lgpdContainer.classList.add('lgpd-visible');

    lgpdButton.addEventListener('click', () => {
        const lgpdVisible = lgpdButton.getAttribute('aria-expanded');
        if(lgpdVisible === 'true') {
            lgpdButton.setAttribute('aria-expanded', false);
            lgpdContainer.classList.remove('lgpd-visible');
            window.localStorage.setItem('cookie', true);
        } 
    });
}
