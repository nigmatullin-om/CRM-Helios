function testAlert(){
    alert("Test Alert")
}

function checkAddCompanyForm() {
    var companyName = document.getElementById("companyName").value;
    var errorMessage = document.getElementById("CompanyErrorMessage");
    var result = true;

    errorMessage.innerText = "";
    console.log("companyName = " + companyName);
    if(companyName == "") {
        errorMessage.innerText = errorMessage.innerText + "Company name should not be empty\n";
        result = false;
    }

    if(document.getElementById("contactName").value == ""
    && (document.getElementById("contactPosition").value != ""
        || document.getElementById("contactPhone").value != ""
        || document.getElementById("contactEmail").value != ""
        || document.getElementById("contactSkype").value != ""
        ) ) {
        errorMessage.innerText = errorMessage.innerText + "Contact name should not be empty\n";
        result = false;
    }

    if(document.getElementById("dealName").value == ""
        && document.getElementById("dealBudget").value != "") {
        errorMessage.innerText = errorMessage.innerText + "Deal name should not be empty\n";
        result = false;
    }

    if(document.getElementById("dealName").value != ""
        && document.getElementById("dealBudget").value == "") {
        errorMessage.innerText = errorMessage.innerText + "Deal budget should not be empty\n";
        result = false;
    }

    return result;
}