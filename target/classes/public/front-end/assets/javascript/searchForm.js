const form  = document.querySelector('.header-form-search');
const searchBtn = form.querySelector('#headerSearchBtn');
const searchInput = form.querySelector('#search-bar');

form.onsubmit = (e) => {
    e.preventDefault();
    let url;

    if(window.location.pathname == "/src/main/resources/public/front-end/assets/pages/login.html" || window.location.pathname == "/src/main/resources/public/front-end/assets/pages/logon.html" || window.location.pathname == "/src/main/resources/public/front-end/assets/pages/user.html")
        url = `pesquisa.html?search=${searchInput.value}`;
    else url = `assets/pages/pesquisa.html?search=${searchInput.value}`;
    window.location.href = url;
}