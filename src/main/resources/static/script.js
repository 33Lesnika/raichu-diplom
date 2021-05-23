let i = 0;

function callback(response) {
    JSON.parse(response).forEach(dp => {
        let documentTemplate = `
            <div class="row p-3 my-1 mx-auto shadow bg-white rounded" id=${i}>
                <div class="w-75">
                    <p>${JSON.stringify(dp)}</p>
                </div>
                <button type="button" class="btn btn-warning w-25" onclick="del(${i})">Delete</button>
            </div>
        `;
        document.getElementById("result").insertAdjacentHTML("beforeend", documentTemplate);
        i++;
    });
}

function del(id){
    document.getElementById(id).remove();
}

function clearAll(){
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
    xhr.open("POST", "http://localhost/word");
    xhr.send(formData);
}
