function sendData() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            callback(xhr.responseText);
        }
    }
    xhr.open("GET", "/api/admin");
    xhr.send();
}

function callback(response) {
    JSON.parse(response).map(file => {
        return {
            name: file.name,
            text: file.phrases.map(phrase => phrase.text),
            usernames: file.user?.username
        }
    }).forEach(dp => {
        let documentTemplate = `
            <div class="card-content">
                
                    <p>${JSON.stringify(dp)}</p>
                
            </div>
        `;
        document.getElementById("result").insertAdjacentHTML("afterbegin", documentTemplate);
    });
}

sendData();
