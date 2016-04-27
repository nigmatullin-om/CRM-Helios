 function checkDealForm(){

    var dealName = document.getElementById("dealName").value;
     console.log("deal name =" + dealName);
    if(dealName == ""){
        setDivError("dealNameBlock", "Имя сделки должно быть заполнено!");
        alert("Имя сделки должно быть заполнено!");
        return false;
    }
     else{
        setDivOk("dealNameBlock");
    }

    var dealBudget = document.getElementById("dealBudget").value;
     console.log("budget = " + dealBudget);
     if(dealBudget == ""){
         setDivError("dealBudgetBlock", "Бюджет сделки не указан");
         /*alert("Бюджет должен быть заполнен!");*/
         return false;
     }
     else {
        setDivOk("dealBudgetBlock");
     }

     var dealResponsibleId = document.getElementById("dealResponsible").value;
     console.log("deal responsible id =" + dealResponsibleId);
     if(dealResponsibleId == -1) {
         setDivError("dealResponsibleBlock", "Ответственный за сделку не выбран!");
         /*alert("Ответственный не выбран!");*/
         return false;
     }
     else {
         setDivOk("dealResponsibleBlock");

     }

     var dealStage = document.getElementById("dealStage").value;
     console.log("deal stage=" + dealStage);
     if(dealStage == -1){
         setDivError("dealStageBlock", "Этап сделки не выбран!");
         /*alert("Этап не выбран!");*/
         return false;
     }
     else {
         setDivOk("dealStageBlock");
     }

     var dealNote = document.getElementById("dealNote").value;
     console.log("deal note=" + dealNote);
     if(dealNote == ""){
         setDivError("dealNoteBlock", "Примечание к сделке не добавлено!");
         /*alert("Примечание не указано!");*/
         return false;
     }
     else {
         setDivOk("dealNoteBlock");
     }

     var dealDate = document.getElementById("dealDate").value;
     console.log("deal date=" + dealDate);
     if(dealDate == ""){
         setDivError("dealDateBlock", "Дата не указана!");
         /*alert("Дата не установлена!");*/
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

     /*alert("completed!");*/
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
             setDivError("contactNameBlock", "Имя и фамилия нового контакта не указаны!");
             /*alert("Имя контакта не указано");*/
             return false;
         }
         else {
             setDivOk("contactNameBlock");
         }

         if ( contactPosition == ""){
             setDivError("contactPositionBlock", "Должность нового контакта не указана!");
             return false;
         }
         else {
             setDivOk("contactPositionBlock");
         }

         if (contactPhoneType == "-1" || contactPhone == ""){
             setDivError("contactPhoneTypeBlock", "Телефрн нового контакта не указан!");
             return false;
         }
         else {
             setDivOk("contactPhoneTypeBlock");
         }

         if (contactSkype == ""){
             setDivError("contactSkypeBlock", "Skype нового контакта не указан!");
             return false;
         }
         else {
             setDivOk("contactSkypeBlock");
         }

         if (contactCompany == ""){
             setDivError("contactCompanyBlock", "Компания нового контакта не указана!");
             return false;
         }
         else {
             setDivOk("contactCompanyBlock");
         }

         if (contactEmail == ""){
             setDivError("contactEmailBlock", "Email нового контакта не указан!");
             return false;
         }
         else {
             setDivOk("contactEmailBlock");
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
                 setDivError("taskPeriodBlock", "Необходимо указать период или дату и время задачи");
                 setDivError("taskDateTimeBlock", "");
                 /*alert("Необходимо указать период или дату и время задачи");*/
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
             setDivError("taskNameBlock", "Имя задачи не указано!");
             /*alert("Имя задачи не указано");*/
             return false;
         }
         else {
            setDivOk("taskNameBlock");
         }

         if (taskResponsible == "-1"){
             setDivError("taskResponsibleBlock","Ответственный за задачу не указан!");
             /*alert("Ответственный за задачу не указан");*/
             return false;
         }
         else {
             setDivOk("taskResponsibleBlock");
         }

         if (taskType == "-1"){
             setDivError("taskTypeBlock", "Тип задачи не указан!");
             /*alert("Тип задачи не указан");*/
             return false;
         }
         else {
             setDivOk("taskTypeBlock");
         }

         if (taskText == ""){
             setDivError("taskTextBlock", "Текст задачи не указан!");
             /*alert("Текст задачи не указан");*/
             return false;
         }
         else {
             setDivOk("taskTextBlock");
         }

         console.log("Задача будет добавлена!");
         /*alert("Задача будет добавлена");*/
         return true;
     }
     else {
         console.log("Задача пуста");
         /*alert("Задача пуста");*/
         return true;
     }
 }

 function checkCompany(){
     var selectCompanyNameStatus = document.getElementById("companyName").getAttribute("disabled");
     console.log("select company name status = " + selectCompanyNameStatus);
     /*alert("select company name status = " + selectCompanyNameStatus);*/
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
             setDivError("companyNameBlock", "Имя компании не заполнено!");
             /*alert("Имя компании не заполнено!");*/
             return false;
         }
         else {
             setDivOk("companyNameBlock");
         }

         if (companyPhone == ""){
             setDivError("companyPhoneBlock", "Номер телефона компании не заполнен!");
             /*alert("Номер телефона компании не заполнен!");*/
             return false;
         }
         else {
             setDivOk("companyPhoneBlock");
         }

         if (companyEmail == ""){
             setDivError("companyEmailBlock", "email компании не заполнен!");
             /*alert("email компании не заполнен!");*/
             return false;
         }
         else {
             setDivOk("companyEmailBlock");
         }

         if (companyAdress == ""){
             setDivError("companyAdressBlock", "");
             /*alert("Адресс компании не заполнен!");*/
             return false;
         }
         else {
             setDivOk("companyAdressBlock");
         }

         if (companyWeb == ""){
             setDivError("companyWebBlock", "Вэб адресс компании не заполнен!");
             /*alert("Вэб адресс компании не заполнен!");*/
             return false;
         }
         else {
             setDivOk("companyWebBlock");
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
             document.getElementById("companyName").setAttribute("disabled", "disabled");
             document.getElementById("companyPhone").setAttribute("disabled", "disabled");
             document.getElementById("companyWeb").setAttribute("disabled", "disabled");
             document.getElementById("companyEmail").setAttribute("disabled", "disabled");
             document.getElementById("companyAdress").setAttribute("disabled", "disabled");
         }
     }
     else{
         console.log("select company list disabled");
         document.getElementById("addCompany").innerHTML = "Добавить компанию";
         document.getElementById("company").value = "-1";
         document.getElementById("companyName").removeAttribute("disabled");
         document.getElementById("companyPhone").removeAttribute("disabled");
         document.getElementById("companyWeb").removeAttribute("disabled");
         document.getElementById("companyEmail").removeAttribute("disabled");
         document.getElementById("companyAdress").removeAttribute("disabled");
     }
     return false;
 }

 function testWarnings(){
     var dealNameBlock = document.getElementById("dealNameBlock");
     dealNameBlock.setAttribute("class", "form-group has-error");
 }

 function setDivError (divId, message ){
     var div = document.getElementById(divId);
     if (div != null){
         div.setAttribute("class", "form-group has-error");
         /*var inputField = div.getElementsByName("input");
         if (input != null){
             inputField.setAttribute("aria-describedby", "inputError2Status");
         }*/
         /*<span id="helpBlock2" class="help-block">A block of help text that breaks onto a new line and may extend beyond one line.</span>*/
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

