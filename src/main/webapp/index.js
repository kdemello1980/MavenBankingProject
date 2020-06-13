// comment
// console.log('hello world');

// // declare variable
// // var is old way
// let name;
// name = "ken"; // String literal
// let age = 30; // number
// let isApproved = true; // boolean (true/false lowercase)
// let firstName; // undefined
// let foo = undefined;

// age = 'thirty';  // Change type of variable from number to string

// // typeof - returns the type of variable

// // console.log(name);

// function greet(firstName, LastName){
//     console.log("Hello " + firstName + " " + LastName); //can use template listerals to format strings
// }

// function square(number){
//     return number * number;
// }

// let four = square(2);
// console.log(square(3));

// load the login page 
function login_form(form, title){
    // Change the title.
    // document.getElementsByName("title").innerHtml = "Please login";
    title.innerText = "Please Login";

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
    element.type = "submit";
    element.value = "Login";
    cell1.appendChild(element);

//    document.createTextNode(table);

    // Get the user information from the database as json,
    // turn it into an object and store it in the sesisionStorage
    sessionStorage.setItem("user", "user1");

    
    return table;    
}

// Build the home page.

function home_form(form, title){
    title.innerText = 'Welcome to MavenBank';

    // Get the list of accounts for the user.

    return form;
}