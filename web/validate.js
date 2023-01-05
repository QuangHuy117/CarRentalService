/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function validateForm() {

    var error = "";
    var filter = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    var num = /\D+$/;
    var userID = document.getElementById('userID').value;
    var pass = document.getElementById('pass').value;
    var conPass = document.getElementById('conPass').value;
    var name = document.getElementById('name').value;
    var phone = document.getElementById('phone').value;
    var address = document.getElementById('address').value;


    if (userID.trim() === "") {
        error += "Email can't be blank! \n";
    } else {
        if (!userID.match(filter)) {
            error += "Invalid Email address! \n";
        }
    }

    if (pass.trim() === "") {
        error += "Password can't be blank! \n";
    }

    if (conPass.trim() === "") {
        error += "Confirm can't be blank! \n";
    }

    if (!conPass.match(pass) || !pass.match(conPass)) {
        error += "Password not match! \n";
    }

    if (name.trim() === "") {
        error += "Name can't be blank! \n";
    } else {
        if (!name.match(num)) {
            error += "Name can't have a number! \n";
        }
    }

    if (phone.trim() === "") {
        error += "Phone can't be blank! \n";
    } else {
        if (phone.length > 11 || phone.length !== 10) {
            error += "Phone must have 10 number! \n";
        }
    }

    if (address.trim() === "") {
        error += "Address can't be blank! \n";
    }
    if (error === "") {
        alert("Register Success!");
        return true;
    } else {
        alert(error);
        return false;
    }
}

function validateLoginForm() {

    var error = "";
    var userID = document.getElementById('userID').value;
    var pass = document.getElementById('pass').value;

    if (userID.trim() === "") {
        error += "Email can't be blank! \n";
    }
    if (pass.trim() === "") {
        error += "Password can't be blank! \n";
    }
    if (error === "") {
        return true;
    } else {
        alert(error);
        return false;
    }

}

