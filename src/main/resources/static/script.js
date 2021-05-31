let i = 0;
let file = {};

function callback(response) {
    let file = JSON.parse(response);
    file.phrases.forEach(p => {
        p.id = i++;

    });
}

function phraseTemplate(model) {
    return `
     <div class="accordion-item">
            <h2 class="accordion-header" id="item-${model.id}">
                <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapse-${model.id}"
                        aria-expanded="true" aria-controls="collapseOne">
                    ${model.text}
                </button>
            </h2>
            <div id="collapse-${model.id}" class="accordion-collapse collapse" aria-labelledby="#item-${model.id}"
                 data-bs-parent="#accordionExample">
                <div class="accordion-body" id="accordeon-body-${model.id}">
                ${insertCodes(model.id)}
                </div>
            </div>
        </div>
    `
}

function insertCodes(id) {
    let j = 0;
    return file.phrases.find(p => p.id === id).codes.map(c => {
        c.id = j++;
        return `
            <div class="row p-3 my-1 mx-auto shadow bg-white rounded">
                <div class="w-75">
                    <p id="phrase-${id}-code-${j}">${JSON.stringify(c)}</p>
                </div>
<!--                <button type="button" class="btn btn-warning w-25" onclick="save(${j},${id})">Delete</button>-->
                <div>
                    <input type="checkbox" id="phrase-${id}-code-${j}-checkbox" name="codeCheckbox" checked>
<!--                    <label for="scales">Scales</label>-->
                </div>
            </div>
        `
    });
}

function save() {
    const fileToSend = {phrases: []};
    document.getElementsByName("codeCheckbox").forEach(v => {
        if (v.checked){
            let phrase = file.phrases.find(p => p.id === v.id.split("-")[1]);
            fileToSend.phrases.push(phrase);
            JSON.parse(v.parentElement.parentElement.getElementsByTagName("p").item(0).innerHTML);
        }
    })
    const code = JSON.parse(document.getElementById(`phrase-${id}-code-${j++}`).innerHTML);
    const phrase = file.phrases.find(p => p.id === id);

}

function del(id) {
    document.getElementById(id).remove();
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
        document.getElementById("nav").insertAdjacentHTML("beforeend", `<a href='/admin/index.html'>Admin panel</a>`);
    }
}

getCurrentUser();
