 function checkDealForm(){
    var dealName = document.getElementById("dealName").value;
     console.log("deal name =" + dealName);
    if(dealName == ""){
        alert("Имя сделки должно быть заполнено!");
        return false;
    }

    var dealBudget = document.getElementById("dealBudget").value;
     console.log("budget = " + dealBudget);
     if(dealBudget == ""){
         alert("Бюджет должен быть заполнен!");
         return false;
     }

     var dealResponsibleId = document.getElementById("dealResponsible").value;
     console.log("deal responsible id =" + dealResponsibleId);
     if(dealResponsibleId == -1){
         alert("Ответственный не выбран!");
         return false;
     }

     var dealStage = document.getElementById("dealStage").value;
     console.log("deal stage=" + dealStage);
     if(dealStage == -1){
         alert("Этап не выбран!");
         return false;
     }

     var dealDate = document.getElementById("dealDate").value;
     console.log("deal date=" + dealDate);
     if(dealDate == ""){
         alert("Дата не установлена!");
         return false;
     }

     var dealNote = document.getElementById("dealNote").value;
     console.log("deal тщеу=" + dealNote);
     if(dealNote == ""){
         alert("Примечание не указано!");
         return false;
     }

     if(!checkContact()){
         return false;
     }

     if (!checkCompany()){
         return false;
     }

     if (!checkTask()){
         return false;
    }

     /*alert("completed!");*/
     return true;
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
         /*alert("Контакт будет добавлен");*/
         return true;
     }

     if (contactPosition != "" || contactPhoneType != "-1" || contactPhone != "" || contactSkype != ""){
         if (contactName == ""){
            alert("Имя контакта не указано");
             return false;
         }
     }
     /*alert("Контакт пуст");*/
     return true;
 }



 function checkTask(){
     var taskName = document.getElementById("taskName").value;
     console.log("task name = " + taskName);
     var taskPeriod = document.getElementById("taskPeriod").value;
     console.log("task period = " + taskPeriod);
     var taskDate = document.getElementById("taskDate").value;
     console.log("task date = " + taskDate);
     var taskTime = document.getElementById("taskTime").value;
     console.log("task time = " + taskTime);
     var taskResponsible = document.getElementById("taskResponsible").value;
     console.log("task responsible = " + taskResponsible);
     var taskType = document.getElementById("taskType").value;
     console.log("task type = " + taskType);
     var taskText = document.getElementById("taskText").value;
     console.log("task text = " + taskText);

     if (taskDate != "" || taskPeriod != "-1" || taskTime != "" || taskResponsible != "-1" || taskType != "-1" || taskText != ""){
         alert("Задача имеет заполненные поля");

         if (taskPeriod == "-1"){
             if (taskDate == "" || taskTime == ""){
                 alert("Необходимо указать период или дату и время задачи");
                 return false;
             }
         }

         if (taskName == ""){
             alert("Имя задачи не указано");
             return false;
         }

         if (taskResponsible == "-1"){
             alert("Ответственный за задачу не указан");
             return false;
         }

         if (taskType == "-1"){
             alert("Тип задачи не указан");
             return false;
         }

         if (taskText == ""){
             alert("Текст задачи не указан");
             return false;
         }

         alert("Задача будет добавлена");
         return true;
     }
     else {
         alert("Задача пуста");
         return true;
     }
 }

 function checkCompany(){
     var selectCompanyNameStatus = document.getElementById("companyName").getAttribute("disabled");
     console.log("select company name status = " + selectCompanyNameStatus);
     /*alert("select company name status = " + selectCompanyNameStatus);*/
     if (selectCompanyNameStatus != null){
         document.getElementById("companyName").value = "";
         return true;
     }
     else{
         var companyName = document.getElementById("companyName").value;
         console.log("company name = " + companyName);
         var companyPhone = document.getElementById("companyPhone").value;
         console.log("company phone = " + companyPhone);
         var companyEmail = document.getElementById("companyEmail").value;
         console.log("company email = " + companyEmail);
         var companyWeb = document.getElementById("companyWeb").value;
         console.log("company web = " + companyWeb);
         var companyAdress = document.getElementById("companyAdress").value;
         console.log("company adress = " + companyAdress);

         if (companyName == ""){
             alert("Имя компании не заполнено!");
             return false;
         }

         if (companyPhone == ""){
             alert("Номер телефона компании не заполнен!");
             return false;
         }

         if (companyEmail == ""){
             alert("email компании не заполнен!");
             return false;
         }

         if (companyAdress == ""){
             alert("Адресс компании не заполнен!");
             return false;
         }

         if (companyWeb == ""){
             alert("Вэб адресс компании не заполнен!");
             return false;
         }

         return true;
     }
 }

 function addCompanyButtonFunction(){
     /*alert("add company button");*/
     var selectCompanyNameStatus = document.getElementById("companyName").getAttribute("disabled");
     console.log("selectCompanyNameStatus = " + selectCompanyNameStatus);
     var selectedCompanyId = document.getElementById("company").value;
     console.log("selectedCompanyId = " + selectedCompanyId);
     if (selectCompanyNameStatus == null){
         console.log("select element is active");
         if (selectedCompanyId == "-1"){
             alert("Выберите компанию из списка");
             return false;
         }
         else{
             console.log("selected company id = " + selectedCompanyId);
             document.getElementById("addCompany").innerHTML = "Добавить новую";
             /*document.getElementById("company").setAttribute("disabled", "disabled");*/
             document.getElementById("companyName").setAttribute("disabled", "disabled");
             /*document.getElementById("companyName").value = "";*/
             document.getElementById("companyPhone").setAttribute("disabled", "disabled");
             /*document.getElementById("companyPhone").value = "";*/
             document.getElementById("companyWeb").setAttribute("disabled", "disabled");
             /*document.getElementById("companyWeb").value = "";*/
             document.getElementById("companyEmail").setAttribute("disabled", "disabled");
             /*document.getElementById("companyEmail").value = "";*/
             document.getElementById("companyAdress").setAttribute("disabled", "disabled");
             /*document.getElementById("companyAdress").value = "";*/
         }
     }
     else{
         console.log("select company list disabled");
         document.getElementById("addCompany").innerHTML = "Добавить компанию";
         /*document.getElementById("company").removeAttribute("disabled");*/
         document.getElementById("company").value = "-1";
         document.getElementById("companyName").removeAttribute("disabled");
         document.getElementById("companyPhone").removeAttribute("disabled");
         document.getElementById("companyWeb").removeAttribute("disabled");
         document.getElementById("companyEmail").removeAttribute("disabled");
         document.getElementById("companyAdress").removeAttribute("disabled");
     }
     return false;
 }
