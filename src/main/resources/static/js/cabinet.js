function sendData() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            callback(xhr.responseText);
        }
    }
    xhr.open("GET", "/api/file");
    xhr.send();
}

function callback(response) {
    JSON.parse(response).map(file => {
        return {
            id: file.id,
            name: file.name,
            text: file.phrases.map(phrase => phrase.text),
            username: file.user?.username
        }
    }).forEach(dp => {
        let documentTemplate = `
            <div class="card-content">
                ${dp.name} ${dp.username} ${dp.text.join(";")}
            </div>
            <a href="/api/file/download/${dp.id}">Download</a>
        `;
        document.getElementById("result").insertAdjacentHTML("afterbegin", documentTemplate);
    });
}

async function change(){
    let newusername = document.getElementById("newusername").value;
    let pass = document.getElementById("pass").value;
    let passRepeat = document.getElementById("passRepeat").value;
    let response = await fetch(`/api/user/change`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            newusername: newusername,
            pass: pass,
            passRepeat: passRepeat
        })
    });

    if (response.ok) { // if HTTP-status is 200-299
        // get the response body (the method explained below)
        return await response.json();
    } else {
        alert("HTTP-Error: " + response.status);
    }
}

sendData();
