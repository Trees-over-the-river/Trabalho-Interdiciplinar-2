

fetch('http://localhost:25565/api/usuario/', {method: 'GET', credentials: 'include'})
    .then(res => res.json())
    .then(res => testLog(res))
    .catch(err => console.log(err));

function testLog(res) {
    if(res.status == 'SUCCESS') {
        const userDiv = document.querySelector('#secundary-navigation');
        userDiv.innerHTML = `
            <div class="user-min-container" onclick="redirectUser()">
                <div class="user-min-username">
                    <p></p>
                </div>
                <div class="user-min-img">
                </div>
            </div>
        `;

        const userDivUsername =  userDiv.querySelector('.user-min-username p');
        const userDivImg = userDiv.querySelector('.user-min-img');

        userDivUsername.innerText = res.message.username;

        let s = "";
        for(let i = 0; i < res.message.avatar.length; i++) 
            s += String.fromCharCode(res.message.avatar[i]);

        userDivImg.style.backgroundImage = `url(${s})`;
    }
}

function redirectUser() {
    if(window.location.pathname == '/front-end/index.html') window.location.href = 'assets/pages/user.html';
    else window.location.href = 'user.html';
}
