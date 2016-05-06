function addDealButton(){
    contactId = document.getElementById("contact").value;
    if (contactId == "-1"){
        alert("Контакт не выбран!");
        return false;
    }
    else {
        var existingNode = document.getElementById(contactId);
        if (existingNode != null){
            alert("Контакт уже в списке!");
            return false;
        }
    }
    console.log("contact id: " + contactId);
    contactName = document.getElementById('contact').options[document.getElementById('contact').selectedIndex].text;
    console.log("contact name:" + contactName );

    var contactsList = document.getElementById("contact");

    var newDiv = document.createElement("div");
    newDiv.setAttribute("class", "form-group");
    newDiv.setAttribute("id", contactId);
    newDiv.setAttribute("style", "margin-top: 8");

    var newLabel = document.createElement("label");
    newLabel.setAttribute("class", "control-label col-sm-6");
    newLabel.innerText = contactName;
    newDiv.appendChild(newLabel);

    var inputDiv = document.createElement("div");
    inputDiv.setAttribute("class", "col-sm-2");

    var newInput = document.createElement("input");
    newInput.setAttribute("type", "text");
    newInput.setAttribute("class", "form-control");
    newInput.setAttribute("name", "addContact" + contactId);
    newInput.value = contactId;
    newInput.style.visibility = "hidden";
    inputDiv.appendChild(newInput);
    newDiv.appendChild(inputDiv);

    var newButton = document.createElement("button");
    newButton.setAttribute("type", "button");
    newButton.setAttribute("class", "btn control-button col-sm-4");
    newButton.setAttribute("onclick", "return deleteContactFromList(" + contactId + ")");
    newButton.innerText = "Удалить";
    newDiv.appendChild(newButton);

    console.log("newDiv inner html: " + newDiv.innerHTML);
    contactsList.parentNode.insertBefore(newDiv, contactsList.nextSibling);
    return false;
}

function deleteContactFromList(id){
    var element = document.getElementById(id);
    element.parentNode.removeChild(element);
    return false;
}

