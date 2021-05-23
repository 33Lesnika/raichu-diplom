function sendData() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            callback(xhr.responseText);
        }
    }
    xhr.open("GET", "http://localhost/api/admin");
    xhr.send();
}

function callback(response) {
    JSON.parse(response).forEach(dp => {
        let documentTemplate = `
            <div class="row p-3 my-1 mx-auto shadow bg-white rounded">
                <div class="w-75">
                    <p>${JSON.stringify(dp)}</p>
                </div>
            </div>
        `;
        document.getElementById("result").insertAdjacentHTML("afterbegin", documentTemplate);
    });
}

sendData();
