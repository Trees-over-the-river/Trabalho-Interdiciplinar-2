const avaliacoes = document.querySelectorAll('.avaliacao-div');

fetch('/api/filme/melhores-avaliacoes', {method: 'GET', credentials: 'include'})
    .then(res => res.json())
    .then(res => showAvaliacoes(res))
    .catch(err => err);

function showAvaliacoes(data) {
    const j = avaliacoes.length > data.message.length ? data.message.length : avaliacoes.length;

    for(let i = 0; i < j; i++) {
        fetch(`/api/usuarios/${data.message[i].usuarioID}`, {method: 'GET', credentials: 'include'})
            .then(res => res.json())
            .then(res => updateAvaliacao(data, res, i));
        
    }
}

function updateAvaliacao(feed, user, idx) {
    const text = avaliacoes[idx].querySelector('.avaliacao-user');
    
    if(user.message.avatar != null) {
        let s = '';
        for(let i = 0; i < user.message.avatar.length; i++) 
                s += String.fromCharCode(user.message.avatar[i]);
        
        const img = avaliacoes[idx].querySelector('.avaliacao-pessoa-icon');

        img.style.backgroundImage = `url(${s})`;
    }

    text.innerHTML = `
        <h3>${user.message.nome} ${user.message.sobrenome}</h3>
        <p><strong>Avaliação: </strong>${feed.message[idx].feedback.substr(0, 147)}${feed.message[idx].feedback.length > 147 ? '...' : ''}</p>
        <div class="avaliacao-star">
            <ul class="avaliacao-star-list">
                ${stars(feed.message[idx].pontuacao)}
            </ul>
        </div>
    `;
}

function stars(rate) {
    let s = '';
    for(let i = 0; i < rate; i++)
        s += `<li><i class='bx bxs-star'></i></li>`;

    for(let j = rate; j < 5; j++)
        s += `<li><i class='bx bx-star' ></i></li>`;
    return s; 
}
