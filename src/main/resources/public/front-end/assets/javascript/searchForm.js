const form  = document.querySelector('.header-form-search');
const searchBtn = form.querySelector('#headerSearchBtn');
const searchInput = form.querySelector('#search-bar');
const btnTemp = document.querySelector('#btn-temp');
console.log(window.location.pathname); 

if(btnTemp != null) {
    btnTemp.onclick = (e) => {
        window.location.href = `user.html`;
    }
}


form.onsubmit = (e) => {
    e.preventDefault();
    let url;

    if(window.location.pathname == "/assets/pages/login.html")
        url = `pesquisa.html?search=${searchInput.value}`;

    else if(window.location.pathname == "/index.html")
        url = `assets/pages/pesquisa.html?search=${searchInput.value}`;

    else if(window.location.pathname == "/assets/pages/logon.html")
        url = `pesquisa.html?search=${searchInput.value}`;

    else if(window.location.pathname == "/assets/pages/privacy-policy.html")
        url = `pesquisa.html?search=${searchInput.value}`;

    else if(window.location.pathname == "/assets/pages/user.html")
        url = `pesquisa.html?search=${searchInput.value}`;

    else url = `assets/pages/pesquisa.html?search=${searchInput.value}`;
    window.location.href = url;
}
