 function checkDealForm(){

    var dealName = document.getElementById("dealName").value;
     console.log("deal name =" + dealName);
    if(dealName == ""){
        setDivError("dealNameBlock", dealNameWarning);
        return false;
    }
     else{
        setDivOk("dealNameBlock");
    }

    var dealBudget = document.getElementById("dealBudget").value;
     console.log("budget = " + dealBudget);
     if(dealBudget == ""){
         setDivError("dealBudgetBlock", dealBudgetWarning);
         return false;
     }
     else {
        setDivOk("dealBudgetBlock");
     }

     var dealResponsibleId = document.getElementById("dealResponsible").value;
     console.log("deal responsible id =" + dealResponsibleId);
     if(dealResponsibleId == -1) {
         setDivError("dealResponsibleBlock", dealResponsibleWarning);
         return false;
     }
     else {
         setDivOk("dealResponsibleBlock");

     }

     var dealStage = document.getElementById("dealStage").value;
     console.log("deal stage=" + dealStage);
     if(dealStage == -1){
         setDivError("dealStageBlock", dealStageWarning);
         return false;
     }
     else {
         setDivOk("dealStageBlock");
     }

     var dealNote = document.getElementById("dealNote").value;
     console.log("deal note=" + dealNote);
     if(dealNote == ""){
         setDivError("dealNoteBlock", dealNoteWarning);
         return false;
     }
     else {
         setDivOk("dealNoteBlock");
     }

     var dealDate = document.getElementById("dealDate").value;
     console.log("deal date=" + dealDate);
     if(dealDate == ""){
         setDivError("dealDateBlock", dealDateWarning);
         return false;
     }
     else {
         setDivOk("dealDateBlock");
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
     return true;
}

 function checkContact(){
     var contactName = document.getElementById("contactName").value;
     var contactPosition = document.getElementById("contactPosition").value;
     var contactPhoneType = document.getElementById("contactPhoneType").value;
     var contactPhone = document.getElementById("contactPhone").value;
     var contactSkype = document.getElementById("contactSkype").value;
     var contactCompany = document.getElementById("contactCompany").value;
     var contactEmail = document.getElementById("contactEmail").value;
     console.log("contactName: " + contactName);
     console.log("contactPosition: " + contactPosition);
     console.log("contactPhoneType: " + contactPhoneType);
     console.log("contactPhone: " + contactPhone);
     console.log("contactSkype: " + contactSkype);
     console.log("contactCompany: " + contactCompany);
     console.log("contactEmail: " + contactEmail);

     if (contactName != "" || contactPosition != "" || contactPhoneType != "-1" || contactPhone != "" || contactSkype != "" || contactCompany != ""){
         if (contactName == ""){
             setDivError("contactNameBlock", contactNameWarning);
             return false;
         }
         else {
             setDivOk("contactNameBlock");
         }

         if ( contactPosition == ""){
             setDivError("contactPositionBlock", contactPostWarning);
             return false;
         }
         else {
             setDivOk("contactPositionBlock");
         }

         if (contactPhoneType == "-1" || contactPhone == ""){
             setDivError("contactPhoneTypeBlock", contactPhoneWarning);
             return false;
         }
         else {
             setDivOk("contactPhoneTypeBlock");
         }

         if (contactSkype == ""){
             setDivError("contactSkypeBlock", contactSkypeWarning);
             return false;
         }
         else {
             setDivOk("contactSkypeBlock");
         }

         if (contactCompany == ""){
             setDivError("contactCompanyBlock", contactCompanyWarning);
             return false;
         }
         else {
             setDivOk("contactCompanyBlock");
         }

         if (contactEmail == ""){
             setDivError("contactEmailBlock", contactEmailWarning);
             return false;
         }
         else {
             setDivOk("contactEmailBlock");
         }
     }
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
         if (taskPeriod == "-1"){
             if (taskDate == "" || taskTime == ""){
                 setDivError("taskPeriodBlock", taskDateWarning);
                 setDivError("taskDateTimeBlock", "");
                 return false;
             }
             else {
                 setDivOk("taskDateTimeBlock");
             }
         }
         else {
             setDivOk("taskPeriodBlock");
         }

         if (taskName == ""){
             setDivError("taskNameBlock", taskNameWarning);
             return false;
         }
         else {
            setDivOk("taskNameBlock");
         }

         if (taskResponsible == "-1"){
             setDivError("taskResponsibleBlock",taskResponsibleWarning);
             return false;
         }
         else {
             setDivOk("taskResponsibleBlock");
         }

         if (taskType == "-1"){
             setDivError("taskTypeBlock", taskTypeWarning);
             return false;
         }
         else {
             setDivOk("taskTypeBlock");
         }

         if (taskText == ""){
             setDivError("taskTextBlock", taskTextWarning);
             return false;
         }
         else {
             setDivOk("taskTextBlock");
         }

         console.log("Задача будет добавлена!");
         return true;
     }
     else {
         console.log("Задача пуста");
         return true;
     }
 }

 function checkCompany(){
     var selectCompanyNameStatus = document.getElementById("companyName").getAttribute("disabled");
     console.log("select company name status = " + selectCompanyNameStatus);
     if (selectCompanyNameStatus != null){
         setDivOk("companyBlock");
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
             setDivError("companyNameBlock", companyNameWarning);
             return false;
         }
         else {
             setDivOk("companyNameBlock");
         }

         if (companyPhone == ""){
             setDivError("companyPhoneBlock", companyPhoneWarning);
             return false;
         }
         else {
             setDivOk("companyPhoneBlock");
         }

         if (companyEmail == ""){
             setDivError("companyEmailBlock", companyEmailWarning);
             return false;
         }
         else {
             setDivOk("companyEmailBlock");
         }

         if (companyAdress == ""){
             setDivError("companyAdressBlock", companyAddressWarning);
             return false;
         }
         else {
             setDivOk("companyAdressBlock");
         }

         if (companyWeb == ""){
             setDivError("companyWebBlock", companyWebWarning);
             return false;
         }
         else {
             setDivOk("companyWebBlock");
         }

         return true;
     }
 }

 function addCompanyButtonFunction(){
     var selectCompanyNameStatus = document.getElementById("companyName").getAttribute("disabled");
     console.log("selectCompanyNameStatus = " + selectCompanyNameStatus);
     var selectedCompanyId = document.getElementById("company").value;
     console.log("selectedCompanyId = " + selectedCompanyId);
     if (selectCompanyNameStatus == null){
         console.log("select element is active");
         if (selectedCompanyId == "-1"){
             return false;
         }
         else{
             console.log("selected company id = " + selectedCompanyId);
             document.getElementById("addCompany").innerHTML = addNewCompany;
             document.getElementById("companyName").setAttribute("disabled", "disabled");
             document.getElementById("companyPhone").setAttribute("disabled", "disabled");
             document.getElementById("companyWeb").setAttribute("disabled", "disabled");
             document.getElementById("companyEmail").setAttribute("disabled", "disabled");
             document.getElementById("companyAdress").setAttribute("disabled", "disabled");
         }
     }
     else{
         console.log("select company list disabled");
         document.getElementById("addCompany").innerHTML = addCompany;
         document.getElementById("company").value = "-1";
         document.getElementById("companyName").removeAttribute("disabled");
         document.getElementById("companyPhone").removeAttribute("disabled");
         document.getElementById("companyWeb").removeAttribute("disabled");
         document.getElementById("companyEmail").removeAttribute("disabled");
         document.getElementById("companyAdress").removeAttribute("disabled");
     }
     return false;
 }

 function setDivError (divId, message ){
     var div = document.getElementById(divId);
     if (div != null){
         div.setAttribute("class", "form-group has-error");
         var messageSpan = document.createElement("span");
         messageSpan.setAttribute("id", divId + "span");
         messageSpan.setAttribute("class", "help-block");
         messageSpan.innerText = message;
         div.appendChild(messageSpan);
     }
 }

/*
 function setDivWarning (divid ){
     var div = document.getElementById(divId);
     div.setAttribute("class", "form-group has-warning");

 }*/

 function setDivOk (divId){
     var div = document.getElementById(divId);
     div.setAttribute("class", "form-group has-success");
     var element = document.getElementById(divId + "span");
     if (element != null){
         element.parentNode.removeChild(element);
     }
 }

 function getLocale(){
     var name = "localeCookie" + "=";
     var cookieArray = document.cookie.split(';');
     for(var i = 0; i <cookieArray.length; i++) {
         var cookie = cookieArray[i];
         while (cookie.charAt(0)==' ') {
             cookie = cookie.substring(1);
         }
         if (cookie.indexOf(name) == 0) {
             console.log("locale cookie: " + cookie.substring(name.length,cookie.length));
             return cookie.substring(name.length,cookie.length);
         }
     }
     return "";
 }

 var dealNameWarning;
 var dealBudgetWarning;
 var dealResponsibleWarning;
 var dealStageWarning;
 var dealNoteWarning;
 var dealDateWarning;

 var contactNameWarning;
 var contactPostWarning;
 var contactPhoneWarning;
 var contactSkypeWarning;
 var contactCompanyWarning;
 var contactEmailWarning;

 var companyNameWarning;
 var companyPhoneWarning;
 var companyEmailWarning;
 var companyAddressWarning;
 var companyWebWarning;

 var taskDateWarning;
 var taskNameWarning;
 var taskResponsibleWarning;
 var taskTypeWarning;
 var taskTextWarning;

 var addNewCompany;
 var addCompany;

 function initWarnings(){
     var locale = getLocale();
     if (locale == "ru_RU"){
        dealNameWarning = "Имя сделки должно быть заполнено!";
         console.log("dealNameWarning: " + dealNameWarning);
         dealBudgetWarning = "Бюджет сделки не указан";
         dealResponsibleWarning = "Ответственный за сделку не выбран!";
         dealStageWarning = "Этап сделки не выбран!";
         dealNoteWarning = "Примечание к сделке не добавлено!";
         dealDateWarning = "Дата не указана!";

         contactNameWarning = "Имя и фамилия нового контакта не указаны!";
         contactPostWarning = "Должность нового контакта не указана!";
         contactPhoneWarning = "Телефрн нового контакта не указан!";
         contactSkypeWarning = "Skype нового контакта не указан!";
         contactCompanyWarning = "Компания нового контакта не указана!";
         contactEmailWarning = "Email нового контакта не указан!";

         companyNameWarning = "Имя компании не заполнено!";
         companyPhoneWarning = "Номер телефона компании не заполнен!";
         companyEmailWarning = "email компании не заполнен!";
         companyAddressWarning = "Адресс компании не заполнен";
         companyWebWarning = "Вэб адресс компании не заполнен!";

         taskDateWarning = "Необходимо указать период или дату и время задачи";
         taskNameWarning = "Имя задачи не указано!";
         taskResponsibleWarning = "Ответственный за задачу не указан!";
         taskTypeWarning = "Тип задачи не указан!";
         taskTextWarning = "Текст задачи не указан!";

         addNewCompany = "Добавить новую";
         addCompany = "Добавить компанию";
     }
     else {
         dealNameWarning = "Deal name is empty!";
         console.log("dealNameWarning: " + dealNameWarning);
         dealBudgetWarning = "Deal budget must be set!";
         dealResponsibleWarning = "Responsible for deal doesn't set!";
         dealStageWarning = "Deal stage doesn't set!";
         dealNoteWarning = "Deal note doesn't set!";
         dealDateWarning = "Deal date doesn't set!";

         contactNameWarning = "Name of the new contact must be set!!";
         contactPostWarning = "Position of the new contact must be set!";
         contactPhoneWarning = "Phone of the new contact must be set!";
         contactSkypeWarning = "Skype of the new contact must be set!";
         contactCompanyWarning = "Company of the new contact must be set!";
         contactEmailWarning = "Email of the new contact must be set!";

         companyNameWarning = "Company name is empty!";
         companyPhoneWarning = "New company phone number is empty!";
         companyEmailWarning = "Email for new company is empty!";
         companyAddressWarning = "Company address is empty! ";
         companyWebWarning = "Web address must be set!";

         taskDateWarning = "Period or date must be set!";
         taskNameWarning = "Task name must be set!";
         taskResponsibleWarning = "Responsible for task must be set!";
         taskTypeWarning = "Task type must be set!";
         taskTextWarning = "Task text must be set!";

         addNewCompany = "Add new";
         addCompany = "Add company";
     }
 }


