const logonForm = document.querySelector('#formLogon');

const btnTemp = document.querySelector('#btn-temp');
if(btnTemp != null) {
    btnTemp.onclick = (e) => {
        window.location.href = `login.html`;
    }
}

logonForm.onsubmit = (e) => {
    e.preventDefault();
    const senha1 = e.srcElement.querySelector('#pass');
    const senha2 = e.srcElement.querySelector('#passCon');
    if(senha1.value != 0 && senha1.value === senha2.value) {
        const user = {
            email: e.srcElement.querySelector('#Email').value, 
            username: e.srcElement.querySelector('#User').value, 
            senha: e.srcElement.querySelector('#pass').value, 
            nome: e.srcElement.querySelector('#name').value, 
            sobrenome: e.srcElement.querySelector('#sobrenome').value, 
            avatar: null
        };

        const options = {method: 'POST', mode: 'no-cors', include: 'same-origin', body: JSON.stringify(user)};
        fetch('/api/logon', options)
            .then(res => verifyRes(e, res))
            .catch(err => showConfirmation('Cadastro mal sucedido'));
        
    } else {
        showConfirmation('Senha incorreta.');
    }
}

function verifyRes(e, res) {
    if(res.status == 200) {
        showConfirmation('Usuário cadastrado com sucesso!', 'green');
        e.srcElement.querySelector('#Email').value = '';
        e.srcElement.querySelector('#User').value = '';
        e.srcElement.querySelector('#pass').value = '';
        e.srcElement.querySelector('#passCon').value = '';
        e.srcElement.querySelector('#name').value = ''; 
        e.srcElement.querySelector('#sobrenome').value = '';
        setTimeout(() => {window.location.href = 'login.html'}, 2200);
    } else showConfirmation(`Erro de cadastro, status: ${res.status}`);
}

function showConfirmation(msg, color) {
    const con = document.querySelector('#confirmationSec');
    const conP = con.querySelector('p');
    con.classList.add('confirmation-enabled');
    if(color == 'green') con.classList.add('confirmation-green');
    conP.innerText = msg;
    setTimeout(() => { 
        con.classList.remove('confirmation-enabled');
    }
    , 2000);
}