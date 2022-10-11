const userBTNName = document.querySelector('#user-btn-name');
const userBTNUser = document.querySelector('#user-btn-user');
const userBTNEmail = document.querySelector('#user-btn-email');
const userBTNPassword = document.querySelector('#user-btn-password');
const userBTNImg = document.querySelector('#userImgChange');

const place = document.querySelector('#form-place');
const placePass = document.querySelector('#divChangePassword');
const placeImg = document.querySelector('#formImgChange');

userBTNImg.onclick = (e) => {
    cancel();
    cancelPass();
    const str = `
        <div> 
            <form action="" class="form-update form-update-img" id="form-update-img">
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
        <form action="" class="form-update" id="form-update">
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
                    <button type="submit" id="btnNameChange">Confirmar</button>
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
        <form action="" class="form-update" id="form-update">
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
        <form action="" class="form-update" id="form-update">
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
        <form action="" class="form-update" id="formUpdate">
            <fieldset>
                <legend>Mudar Senha</legend>
                <div>
                    <label for="changePass">Nova Senha</label>
                    <input type="password" name="change-Pass" id="changePass" oninput="passwordCheck(formUpdate)" required>
                </div>
                <div>
                    <label for="changeConfSenha">Confirmar Senha</label>
                    <input type="password" name="change-ConfSenha" id="changeConfSenha" oninput="passwordCheck(formUpdate)" required>
                </div>
                <div>
                    <button type="submit" id="btnDescriptionChange" disabled>Confirmar</button>
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

function passwordCheck(id) {
    const formPassword = id;
    const in1 = formPassword.querySelector('#changePass');
    const in2 = formPassword.querySelector('#changeConfSenha');
    const btnpassword = formPassword.querySelector('#btnDescriptionChange');
    console.log(in1.value.length);
    if(in1.value === in2.value && in1.value.length != 0 && in2.value.length != 0) 
        btnpassword.disabled = false;
    else btnpassword.disabled = true;
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
