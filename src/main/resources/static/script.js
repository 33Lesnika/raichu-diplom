function callback(response){
    document.getElementById("result").insertAdjacentHTML("beforeend", `<p>${response}</p>`);
}

function sendData() {
    var formData = new FormData();
    formData.append("file", document.getElementById("fileInput").files[0]);
    formData.append("word", document.getElementById("txt").value);

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            callback(xhr.responseText);
        }
    }
    xhr.open("POST", "http://localhost/word");
    xhr.send(formData);
}
