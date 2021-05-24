function register() {
    let username = document.getElementById("username").value;
    let password = document.getElementById("pass").value;
    if (!valid()) {
        disable();
        return;
    }
    const user = {
        username: username,
        password: password
    }
    send(user);
}

function valid() {
    if (document.getElementById('pass').value.length === 0) {
        return false;
    } else {
        return document.getElementById('pass').value ===
            document.getElementById('passRepeat').value;
    }

}

function check() {
    if (valid()) {
        enable();
    } else {
        disable();
    }
}

function disable() {
    document.getElementById("register").disabled = true;
    document.getElementById("matchPasswords").style.display = "block";
}

function enable() {
    document.getElementById("register").disabled = false;
    document.getElementById("matchPasswords").style.display = "none";
}

function send(user) {
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4)
            if (xhr.status >= 200 && xhr.status < 300) {
                successHandler(xhr.responseText);
            } else if (xhr.status >= 400) {
                errorHandler(xhr.responseText);
            }
    }
    xhr.open("POST", "/api/registration");
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhr.send(JSON.stringify(user));
    setStatus("Sending registration request")
}

function successHandler(response) {
    const user = JSON.parse(response);
    const redirectSec = 3;
    setStatus(`Registration successful, ${user.username}! You will be redirected to login page in ${redirectSec} sec`);
    setTimeout(() => {
        window.location = "/"
    }, redirectSec * 1000);
}

function errorHandler(errorText) {
    const error = JSON.parse(errorText);
    setStatus(`Something went completely wrong, we cannot register you at this time. Server response: ${error.message}`);
}

function setStatus(text) {
    document.getElementById("status").innerHTML = text;
}
