 function checkDealForm(){
    var dealName = document.getElementById("dealName").value;
     console.log("deal name =" + dealName);
    if(dealName == ""){
        alert("Имя сделки должно быть заполнено");
        return false;
    }

    var dealBudget = document.getElementById("dealBudget").value;
     console.log("budget = " + dealBudget);
     if(dealBudget == ""){
         alert("Бюджет должен быть заполнен");
         return false;
     }

     var dealResponsibleId = document.getElementById("dealResponsible").value;
     console.log("deal responsible id =" + dealResponsibleId);
     if(dealResponsibleId == -1){
         alert("Ответственный не выбран");
         return false;
     }

     if(!checkContact()){
         return false;
     }

     alert("completed!");
     return true;
     /*return false;*/
}

 function checkContact(){
     var contactName = document.getElementById("contactName").value;
     var contactPosition = document.getElementById("contactPosition").value;
     var contactPhoneType = document.getElementById("contactPhoneType").value;
     var contactPhone = document.getElementById("contactPhone").value;
     var contactSkype = document.getElementById("contactSkype").value;
     console.log("contactName: " + contactName);
     console.log("contactPosition: " + contactPosition);
     console.log("contactPhoneType: " + contactPhoneType);
     console.log("contactPhone: " + contactPhone);
     console.log("contactSkype: " + contactSkype);

     if (contactName != ""){
         alert("Контакт будет добавлен")
         return true;
     }


     if (contactPosition != "" || contactPhoneType != "-1" || contactPhone != "" || contactSkype != ""){
         if (contactName == ""){
            alert("Имя контакта не заполнено");
             return false;
         }
         /*else {
             alert("Контакт будет добавлен");
             return true;
         }*/
     }

     alert("Контакт пуст");
     return true;
 }

 function addContact(){
     alert("add contact");
     return false;
 }

 function deleteContact(){
     alert("delete contact");
     return false;
 }
