const DpopUp = document.querySelector('#doacoesPopup'); 
const btnD = document.querySelector('#popuoBtnDoacoes');
const btnX = document.querySelector('#btnXPopup');

const showPopup = sessionStorage.getItem("popUpDoacao");

if(showPopup === "true") DpopUp.classList.add("sr-only");
else {
    btnX.onclick = () => {
        DpopUp.classList.add("sr-only");
        sessionStorage.setItem("popUpDoacao", "true");
    }
    
    btnD.onclick = () => {
        let url = 'doacoes.html';
    
        if(window.location.pathname == '/src/main/resources/public/front-end/index.html') url = 'assets/pages/doacoes.html';
    
        window.location.href = url;
    }
}
