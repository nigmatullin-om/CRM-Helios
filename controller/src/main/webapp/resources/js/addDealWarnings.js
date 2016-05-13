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
