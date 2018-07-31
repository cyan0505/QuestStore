function formValidation() {
    var userName = document.myForm.firstname;
    var email = document.myForm.email;
    var message = document.myForm.message;
    
    
    if (checkIfFullfilled(userName, email, message)) {
        if (validateName(userName)){
            if (validateEmail(email)){
                alert("Message has been sent!");   
                document.getElementById("myForm").reset();
            }   
        }    
    }        
    
    return false;
    
}
      
           
function checkIfFullfilled(userName, email, message) {
    if(userName.value == "") {
        document.getElementById('nameError').innerHTML="*This field is required";
        userName.focus();
        return false;
    }
            
    if(email.value == "") {
        document.getElementById('emailError').innerHTML="*This field is required";
        email.focus();
        return false;
    }
            
    if(message.value == "") {
        document.getElementById('messageError').innerHTML="*Please provide message";
        message.focus();
        return false;
    }
            
    return(true);
}

           

function validateName(userName) {
    var letters = /^[A-Z]\w+/;
    var fullName =/[A-Z]\w+\s[A-Z]\w+/;
    if(userName.value.match(letters)) {
        return true;
    }
    if(userName.value.match(fullName)) {
        return true;
    }
    else {
        alert("User must start with upper case and must contains only letters!");
        userName.focus();
        return false;
    }
    
}



function validateEmail(email) {
    var validEmailAdress =  /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    var validateWhiteSpaces = /^[A-Za-z]+$/;
    
    if(email.value.match(validEmailAdress)) {
        return true;
    }
    if(email.value.match(validateWhiteSpaces)) {
        return false;
    }
    else {
        alert("You have entered an invalid email address!");
        email.focus();
        return false;
    }
    
    
}


function myFunction() {
    var x = document.getElementById("myNavbar");
    if (x.className === "navbar") {
        x.className += " responsive";
    } else {
        x.className = "navbar";
    }
}




var counter = 0;

function addQuest() {
    
    if (counter == 0 || counter % 3 == 0) {
        var flexContainer = document.createElement("div");
        flexContainer.className = "flex-container perspective";
        flexContainer.id = "new-quests";
        
        container_div = document.getElementById('first-container');
        document.body.insertBefore(flexContainer, container_div);
        
        counter++;
           
    }
    
    var cardContainer = document.createElement("div");
    cardContainer.setAttribute("class", "card-container");
    cardContainer.innerHTML = "<div class='card-title'>" + 
                "<p contenteditable='true'>Card title</p>" +
            "</div>" + 
            "<div class='card-value'>" + 
                "<p contenteditable='true'>10</p>" + 
            "</div>" + 
            "<img src='assets/stylesheets/Card_quest.jpg'>" + 
            "<div class='card-description'>" + 
                "<p contenteditable='true'>Card description here</p>" + 
            "</div>";
    
    document.getElementById('new-quests').append(cardContainer);
    
}


function addArtefact() {

    
    var cardContainer = document.createElement("div");
    cardContainer.setAttribute("class", "card-container");
    cardContainer.innerHTML = "<div class='card-title'>" + 
                "<p contenteditable='true'>Card title</p>" +
            "</div>" + 
            "<div class='card-value'>" + 
                "<p contenteditable='true'>10</p>" + 
            "</div>" + 
            "<img src='assets/stylesheets/Card_artefact.jpg'>" + 
            "<div class='card-description'>" + 
                "<p contenteditable='true'>Card description here</p>" + 
            "</div>";
    
    
    document.getElementById('new-quests').append(cardContainer);
    
}










