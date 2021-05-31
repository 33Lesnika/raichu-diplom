async function loadUsers() {
    let response = await fetch('/api/user');

    if (response.ok) { // if HTTP-status is 200-299
        // get the response body (the method explained below)
        let json = await response.json();
        json.forEach(addCard);
    } else {
        alert("HTTP-Error: " + response.status);
    }
}

function addCard(user) {
    const card = `
        <div class="card" id="${user.id}">
            <div class="card-content">
                <div class="text id">${user.id}</div>
                <div class="text username">${user.username}</div>
                <div class="text status">
                    ${user.enabled ? 'Enabled' : 'Disabled'}
                    <div class="status-circle ${user.enabled ? 'green' : 'red'}"></div>
                </div>
            </div>
            <div class="card-controls">
                <button type="button" class="toggle-button" onclick="toggle(${user.id})">${!user.enabled ? 'Enable' : 'Disable'}</button>
            </div>
        </div>
    `
    document.getElementById('content').insertAdjacentHTML('beforeend', card);
}

function toggle(id){
    sendToggle(id).then();
}

async function sendToggle(id){
    let response = await fetch(`/api/user/${id}`, {method: 'POST'});

    if (response.ok) { // if HTTP-status is 200-299
        // get the response body (the method explained below)
        let user = await response.json();
        document.getElementById(id).innerHTML = `
             <div class="card-content">
                <div class="text id">${user.id}</div>
                <div class="text username">${user.username}</div>
                <div class="text status">
                    ${user.enabled ? 'Enabled' : 'Disabled'}
                    <div class="status-circle ${user.enabled ? 'green' : 'red'}"></div>
                </div>
            </div>
            <div class="card-controls">
                <button type="button" class="toggle-button" onclick="toggle(${user.id})">${!user.enabled ? 'Enable' : 'Disable'}</button>
            </div>
        `
    } else {
        alert("HTTP-Error: " + response.status);
    }
}

loadUsers().then();
