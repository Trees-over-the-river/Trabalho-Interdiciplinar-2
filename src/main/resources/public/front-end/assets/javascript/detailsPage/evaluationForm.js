
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
