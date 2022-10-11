const logonForm = document.querySelector('#formLogin');

const btnTemp = document.querySelector('#btn-temp');
if(btnTemp != null) {
    btnTemp.onclick = (e) => {
        window.location.href = `logon.html`;
    }
}

logonForm.onsubmit = (e) => {
    e.preventDefault();
    const user = {
        email: e.srcElement.querySelector('#User').value, 
        senha: e.srcElement.querySelector('#Pass').value, 
    };

    const options = {method: 'POST', mode: 'no-cors', include: 'same-origin', body: JSON.stringify(user)};
    fetch('http://localhost:25565/api/login', options)
        .then(res => verifyRes(res))
        .catch(err => showConfirmation('Erro no servidor.'));
}

function verifyRes(res) {
    if(res.status == 200) redirect(res);
    else if(res.status == 404) showConfirmation('E-mail ou senha incorretos.');
    else showConfirmation(`Erro de cadastro, status: ${res.status}`);
}

function showConfirmation(msg) {
    const con = document.querySelector('#confirmationSec');
    const conP = con.querySelector('p');
    con.classList.add('confirmation-enabled');
    conP.innerText = msg;
    setTimeout(() => { 
        con.classList.remove('confirmation-enabled');
    }
    , 1700);
}

function redirect(res) {
    window.location.href = 'user.html';
}
