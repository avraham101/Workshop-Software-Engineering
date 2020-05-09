var name=undefined;
var password=undefined;
function send(url, method, msg, update) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            alert(xhr.response);
        }
    };
    let send = JSON.stringify(msg);
    xhr.open(method,url);
    xhr.send(send);
}
//The function return a response object
function receive(buffer){
    //let out = Object.getOwnPropertyNames(buffer)
    let out = buffer.toString();
    return JSON.parse(out);
}
//the function is the handle of submit click
function handleSubmit() {
    let promise = (buffer) => {
        if (buffer && buffer.value) {
            alert("Admin Registered");
            document.getElementById("name").innerHTML = "";
            document.getElementById("password").innerHTML = "";
        }
        else
            alert("Server Crashed. Cant Register Admin.")
    }
    if(name==undefined) {
        alert("Please insert name of admin");
        return
    }
    if(password==undefined) {
        alert("Please insert password");
        return
    }
    let msg = {name: name, password: password};
    send('/admin/init', 'POST', msg, promise)
}
function handleChangeName() {
    name = document.getElementById("name").value;
}
function handleChangePassword(event) {
    password = document.getElementById("password").value;
}