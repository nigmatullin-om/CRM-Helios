 function checkDealForm(){
    var dealName = document.getElementById("dealName").value;
    if(dealName == ""){
        alert("Имя сделки должно быть заполнено");
        return false;
    }

    var dealBudget = document.getElementById("dealName").value;
     if(dealBudget == ""){
         alert("Бюджет должен быть заполнен");
         return false;
     }

     var dealResponsibleId = document.getElementById("dealResponsible").value;
     if(dealResponsibleId == -1){
         alert("Ответственный не выбран");
         return false;
     }

     alert("completed!!!");
     return false
}
