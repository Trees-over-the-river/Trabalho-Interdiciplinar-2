const evaluationForm = document.querySelector('#evaluationForm');
const confirmationDiv = document.querySelector('.confirmation-div');

evaluationForm.onsubmit = (e) => {
    e.preventDefault();

    evaluationForm.style.display = 'none';
    confirmationDiv.style.display = 'block';
}
