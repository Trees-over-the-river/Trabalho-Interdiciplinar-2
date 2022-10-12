function updateName(e) {
    e.preventDefault();
    const f = document.querySelector('#formUpdate');
    const name = f.querySelector('#changeName');
    const sobrenome = f.querySelector('#changeLastName');

    const options = {
        method: 'PUT',
        credentials: 'include',
        body: JSON.stringify({nome: name.value, sobrenome: sobrenome.value})
    }

    fetch('/api/usuario/nome', options)
        .then(res => res.json())
        .then(res => {
            alert(res.message);
            if(res.status == 'SUCCESS') window.location.href = '';
        });
}

function updateUsername(e) {
    e.preventDefault();
    const f = document.querySelector('#formUpdate');
    const user = f.querySelector('#changeUser');

    const options = {
        method: 'PUT',
        credentials: 'include',
        body: JSON.stringify({username: user.value})
    }

    fetch('/api/usuario/username', options)
        .then(res => res.json())
        .then(res => {
            alert(res.message);
            if(res.status == 'SUCCESS') window.location.href = '';
        });
}

function updateEmail(e) {
    e.preventDefault();
    const f = document.querySelector('#formUpdate');
    const email = f.querySelector('#changeEmail');

    const options = {
        method: 'PUT',
        credentials: 'include',
        body: JSON.stringify({email: email.value})
    }

    fetch('/api/usuario/email', options)
        .then(res => res.json())
        .then(res => {
            alert(res.message);
            if(res.status == 'SUCCESS') window.location.href = '';
        });
}

function updatePassword(e) {
    e.preventDefault();
    const f = document.querySelector('#formUpdate');
    const pass = f.querySelector('#changePass');
    const passCon = f.querySelector('#changeConfSenha');

    if(pass.value != 0 && pass.value == passCon.value) {
        const options = {
            method: 'PUT',
            credentials: 'include',
            body: JSON.stringify({senha: pass.value})
        }

        fetch('/api/usuario/senha', options)
            .then(res => res.json())
            .then(res => {
                alert(res.message);
                if(res.status == 'SUCCESS') window.location.href = '';
            });
    } else alert('Senhas diferentes.');
}

function updateImg(e) {
    e.preventDefault();
    const f = document.querySelector('#formUpdate');
    const img = f.querySelector('#changeImg')['files'][0];

    const imgt = String(img.type);
    if(imgt.includes('image')) {
        const toBase64 = file => new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = () => resolve(reader.result);
            reader.onerror = err => reject(err);
        });
        toBase64(img)
            .then(res1 => {
                const options = {
                    method: 'PUT',
                    credentials: 'include',
                    body: JSON.stringify({avatar: btoa(res1)})
                }
        
                fetch('/api/usuario/avatar', options)
                    .then(res => res.json())
                    .then(res => {
                        alert(res.message);
                        if(res.status == 'SUCCESS') window.location.href = '';
                    });
            });

        
    } else alert('Tipo errado de arquivo.');
    
}

