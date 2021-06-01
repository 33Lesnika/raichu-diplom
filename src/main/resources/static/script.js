let i = 0;
let file = {};

function callback(response) {
    file = JSON.parse(response);
    file.phrases.forEach(phrase => {
        phrase.codes.map(c => c.code).map(JSON.parse).forEach(code => {
            let documentTemplate = `
            <div class="row p-3 my-1 mx-auto shadow bg-white rounded" id=${i}>
                <div class="w-75">
                    <p>${code.word} ${fixedNum(code.page)} ${fixedNum(code.line)} ${fixedNum(code.wordPos)}</p>
                </div>
                <button type="button" class="btn btn-warning w-25" onclick='save(${JSON.stringify(code)})'>Save</button>
            </div>
        `;
            document.getElementById("result").insertAdjacentHTML("beforeend", documentTemplate);
            i++;
        })
    });
}

function fixedNum(num) {
    return num.toString().padStart(3, "0")
}

async function save(code) {
    console.log(code);
    if (!file.id) {
        file = await saveFile({name: file.name});
    }
    if (!file.phrases.some(p => p.text === code.word)) {
        file.phrases.push({
            text: code.word,
            codes: [{code: JSON.stringify(code)}]
        })
    } else {
        file.phrases.find(p => p.text === code.word).codes.push({code: JSON.stringify(code)})
    }
    file = await saveFile(file)
}

async function saveFile(fileToSave) {
    let response = await fetch(`/api/file`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(fileToSave)
    });

    if (response.ok) { // if HTTP-status is 200-299
        // get the response body (the method explained below)
        return await response.json();
    } else {
        alert("HTTP-Error: " + response.status);
    }
}

function clearAll() {
    document.getElementById("result").innerHTML = "";
}

function sendData() {
    var formData = new FormData();
    formData.append("file", document.getElementById("fileInput").files[0]);
    formData.append("word", document.getElementById("txt").value);

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            callback(xhr.responseText);
        }
    }
    xhr.open("POST", "/word");
    xhr.send(formData);
}


function getCurrentUser() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            currentUserCallback(xhr.responseText);
        }
    }
    xhr.open("GET", "/api/user/me");
    xhr.send();
}

function currentUserCallback(response) {
    const user = JSON.parse(response);
    if (user.username === 'admin') {
        document.getElementById("nav").insertAdjacentHTML("beforeend", `<a href='/admin/users.html'>Admin panel</a>`);
    }
}

getCurrentUser();
