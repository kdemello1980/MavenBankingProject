
function submit_form(form, endpoint){
	const formData = new FormData(form);
	let request = new XHttpRequest();
	request.open("POST", endpoint);
	request.send(formData);
}

function login_form(form, title){
    // Change the title.
    // document.getElementsByName("title").innerHtml = "Please login";
    title.innerText = "Welcome to MavenBank! Please Login Below:";

    let table = form.appendChild(document.createElement("table"));
    let row = table.insertRow();

    // Insert the username fields.
    let cell1 = row.insertCell();
    cell1.innerText = "Username";
    let cell2 = row.insertCell();
    let element = document.createElement("input");
    element.type = "text";
    element.id = "username";
    cell2.appendChild(element);

    // Insert the password elements.
    row = table.insertRow();
    cell1 = row.insertCell();
    cell1.innerText = "Password";
    row.appendChild(cell1);
    cell2 = row.insertCell();
    element = document.createElement("input");
    element.type = "text";
    element.id = "password";
    cell2.appendChild(element);

    // Insert the login button
    row = table.insertRow();
    cell1 = row.insertCell();
    cell1.colSpan = 2;
    cell1.align = "center";
    element = document.createElement("input");
    element.type = "button";
    element.value = "Login";
//    element.onclick = submit_form(form, "/MavenBankingProject/login");
    cell1.appendChild(element);

//    document.createTextNode(table);

    // Get the user information from the database as json,
    // turn it into an object and store it in the sesisionStorage
    sessionStorage.setItem("user", "user1");

    
    return table;    
}

// Build the home page.

function home_form(form, title){
    title.innerText = 'MavenBank Home';

    // Get the list of accounts for the user.

    return form;
}