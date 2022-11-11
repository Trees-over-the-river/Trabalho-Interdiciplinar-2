
fetch('http://localhost:25565/api/usuario/', {method: 'GET', credentials: 'include'})
    .then(res => res.json())
    .then(res => testAv(res))
    .catch(err => err);

function testAv(res) {
    if(res.status == 'SUCCESS') {
        const evCont = document.querySelector('#evaluationContainer');
        evCont.classList.remove('evaluation-container-disabled');
        evCont.classList.add('evaluation-container-enabled');

        const urldetails = new URLSearchParams(window.location.search);
        let mID = urldetails.get('movie');

        const API_MOVIE = `${baseUrl}/movie/${movieId}?api_key=${API_KEY}`;

        fetchMovie(API_MOVIE, categIA, 'genres');

        const evaluationForm = document.querySelector('#evaluationForm');
        const confirmationDiv = document.querySelector('.confirmation-div');

        evaluationForm.onsubmit = (e) => {
            e.preventDefault();

            const watch = document.querySelector('#watched');
            if(watch.value == 'on') {
                fetch(`/api/usuario/filmes/assistidos/${mID}`, {method: 'POST', credentials: 'include'})
                    .then(res => res.json())
                    .then(res => console.log(res));
            }

            const txtA = evaluationForm.querySelector('#evaluation-description-input');
            const like = evaluationForm.querySelector('#liked');
            const rate = evaluationForm.querySelector('#rating');

            const obj = {
                filme_id: mID,
                gostou: (like.value == "on"),
                pontuacao: rate.value,
                feedback: txtA.value
            };

            fetch(`/api/usuario/filmes/avaliados/${mID}/`, {
                method: 'POST', 
                credentials: 'include', 
                body: JSON.stringify(obj)
            })
                .then(res => res.json())
                .then(res => console.log(res));

            evaluationForm.style.display = 'none';
            confirmationDiv.style.display = 'block';
        }
    } 
}

function categIA(res) {
    console.log(res);
    let ids = [];
    res.forEach(e => {
        ids.push(e.id);
    });

    let preferGenres = JSON.parse(localStorage.getItem('genres'));
    if(preferGenres == null) {
        localStorage.setItem('preferido', JSON.stringify(countThis(ids)));
        localStorage.setItem('genres', JSON.stringify(countingSort(ids)));
    } else {
        ids.forEach(e => preferGenres.push(e));
        ids = countingSort(preferGenres);
        localStorage.setItem('preferido', JSON.stringify(countThis(ids)));
        localStorage.setItem('genres', JSON.stringify(ids));
    }
}

function countThis(ids) {
    let m = 1;
    let c = 1;
    let id = -1;
    
    for(let i = 0; i < ids.length - 1; i++) {
        if(ids[i] === ids[i + 1]) c++;
        else {
            if(c >= m) {
                m = c;
                id = ids[i];
            } 
            c = 1;
        }
    }

    return (ids.length == 1) ? ids[0] : id;
}

function countingSort(arr) {
    let n = arr.length;
    count = new Array(Math.max(...arr) + 1).fill(0);
    out = new Array(n);

    for(let i = 0; i < n; i++) count[arr[i]]++;

    for(let i = 0; i < count.length - 1; i++) count[i + 1] += count[i];

    for(let i = 0; i < n; i++) {
        out[count[arr[i]] - 1] = arr[i];
        count[arr[i]]--;
    }

    return out;
}
