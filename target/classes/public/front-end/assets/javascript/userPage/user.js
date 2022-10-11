const userBTNName = document.querySelector('#user-btn-name');
const userBTNUser = document.querySelector('#user-btn-user');
const userBTNEmail = document.querySelector('#user-btn-email');
const userBTNPassword = document.querySelector('#user-btn-password');
const userBTNImg = document.querySelector('#userImgChange');
const userBTNExc = document.querySelector('#btnExcluirConta');
const userBTNLogout = document.querySelector('#btnLogout');

const place = document.querySelector('#form-place');
const placePass = document.querySelector('#divChangePassword');
const placeImg = document.querySelector('#formImgChange');


fetch('http://localhost:25565/api/usuario/', {method: 'GET', credentials: 'include'})
    .then(res => res.json())
    .then(res => buildPage(res.message));

function buildPage(res) {
    const divPN = document.querySelector('#divName p');
    const divPUN = document.querySelector('#divUsername p');
    const divPE = document.querySelector('#divEmail p');
    divPN.innerText = res.nome + " " + res.sobrenome;
    divPUN.innerText = res.username;
    divPE.innerText = res.email;
    
    const divImg = document.querySelector('#userImgChange');
    if(res.avatar == null) {
        let i = Math.random();
        if(i > 0.5) divImg.style.backgroundImage = `url('../img/pessoas/Pessoas_01.jpg')`;
        else if(i < 0.5) divImg.style.backgroundImage = `url('../img/pessoas/Pessoas_02.jpg')`;
    } else {
        let s = "";
        for(let i = 0; i < res.avatar.length; i++) 
            s += String.fromCharCode(res.avatar[i]);

        divImg.style.backgroundImage = `url(${s})`;
    }
}

userBTNExc.onclick = (e) => {
    if(confirm('Tem certeza que deseja excluir sua conta?')) {
        fetch('http://localhost:25565/api/usuario/', {method: 'DELETE', credentials: 'include'})
        .then(res => {
            if(res.status == 200) alert('Conta excluida com sucesso');
            else alert(`Erro ao excluir conta, status: ${res.status}`);
        });

        setTimeout(() => {window.location.href = '../../index.html';}, 1000);
    }
}

userBTNImg.onclick = (e) => {
    cancel();
    cancelPass();
    const str = `
        <div> 
            <form action="" class="form-update form-update-img" id="formUpdate" onsubmit="updateImg(event)">
                <fieldset>
                    <legend>Mudar Imagem</legend>
                    <div>
                        <label for="changeImg">Nova Imagem</label>
                        <input type="file" name="change-Img" id="changeImg" required>
                    </div>
                    <div>
                        <button type="submit" id="btnImgChange">Confirmar</button>
                    </div>
                    <div>
                        <button type="button" id="btnImgChangeCancel" onclick="cancelImg()">Cancelar</button>
                    </div>
                </fieldset>
            </form>
        </div>
    `;
    placeImg.innerHTML = str;
}

userBTNName.onclick = (e) => {
    cancelPass();
    cancelImg();
    const str = `
    <div>
        <form action="" class="form-update" id="formUpdate" onsubmit="updateName(event)">
            <fieldset>
                <legend>Mudar Nome</legend>
                <div>
                    <label for="changeName">Novo Nome</label>
                    <input type="text" name="change-Name" id="changeName" required>
                </div>
                <div>
                    <label for="changeLastName">Novo Sobrenome</label>
                    <input type="text" name="change-LastName" id="changeLastName" required>
                </div>
                <div>
                    <button type="submit" id="btnNameChange" onclick="updateName()">Confirmar</button>
                </div>
                <div>
                    <button type="button" id="btnChangeCancel" onclick="cancel()">Cancelar</button>
                </div>
            </fieldset>
        </form>
    </div>
    `;

    place.innerHTML = str;
}

userBTNEmail.onclick = (e) => {
    cancelPass();
    cancelImg();
    const str = `
    <div>
        <form action="" class="form-update" id="formUpdate" onsubmit="updateEmail(event)">
            <fieldset>
                <legend>Mudar Email</legend>
                <div>
                    <label for="changeEmail">Novo Email</label>
                    <input type="email" name="change-Email" id="changeEmail" required>
                </div>
                <div>
                    <button type="submit" id="btnEmailChange">Confirmar</button>
                </div>
                <div>
                    <button type="button" id="btnChangeCancel" onclick="cancel()">Cancelar</button>
                </div>
            </fieldset>
        </form>
    </div>
    `;

    place.innerHTML = str;
}

userBTNUser.onclick = (e) => {
    cancelPass();
    cancelImg();
    const str = `
    <div>
        <form action="" class="form-update" id="formUpdate" onsubmit="updateUsername(event)">
            <fieldset>
                <legend>Mudar Usuário</legend>
                <div>
                    <label for="changeUser">Novo Usuário</label>
                    <input type="text" name="change-user" id="changeUser" required>
                </div>
                <div>
                    <button type="submit" id="btnUserChange">Confirmar</button>
                </div>
                <div>
                    <button type="button" id="btnChangeCancel" onclick="cancel()">Cancelar</button>
                </div>
            </fieldset>
        </form>
    </div>
    `;

    place.innerHTML = str;
}

userBTNPassword.onclick = (e) => {
    cancel();
    cancelImg();
    const str = `
    <div>
        <form action="" class="form-update" id="formUpdate" onsubmit="updatePassword(event)">
            <fieldset>
                <legend>Mudar Senha</legend>
                <div>
                    <label for="changePass">Nova Senha</label>
                    <input type="password" name="change-Pass" id="changePass" required>
                </div>
                <div>
                    <label for="changeConfSenha">Confirmar Senha</label>
                    <input type="password" name="change-ConfSenha" id="changeConfSenha" required>
                </div>
                <div>
                    <button type="submit" id="btnDescriptionChange">Confirmar</button>
                </div>
                <div>
                    <button type="button" id="btnChangeCancel" onclick="cancelPass()">Cancelar</button>
                </div>
            </fieldset>
        </form>
    </div>
    `;

    placePass.innerHTML = str;
}

userBTNLogout.onclick = () => {
    const options = {
        method: 'POST',
        credentials: 'include',
    }
    fetch('http://localhost:25565/api/logout', options) 
        .then(res => res.json())
        .then(res => {
            alert(res.message);
            window.location.href = '../../index.html'
        });
}

function cancel() {
    place.innerHTML = "";
}

function cancelPass() {
    placePass.innerHTML = "";
}

function cancelImg() {
    placeImg.innerHTML = "";
}
