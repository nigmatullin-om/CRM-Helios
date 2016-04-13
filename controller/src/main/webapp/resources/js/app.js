function testAlert(){
    alert("Test Alert")
}

var form = document.getElementById('main').children[0].children[0].children[0];
form.onclick = function (event) {
        var target = event.target;
        if (target.tagName != 'INPUT' || !target.getAttribute('value')) return;
        var val = target.getAttribute('value');
        if (val == 'create'){
            document.getElementsByName('select_company')[0].setAttribute('disabled',true);
            document.getElementsByName('name-company')[0].removeAttribute('disabled');
            document.getElementById('phone-company').removeAttribute('disabled');
            document.getElementById('email-company').removeAttribute('disabled');
            document.getElementById('web-company').removeAttribute('disabled');
            document.getElementById('note-company').removeAttribute('disabled');
            document.getElementsByName('select_company')[0].firstElementChild.setAttribute('selected',true);
        }
        else if (val == 'choose') {
            document.getElementsByName('select_company')[0].removeAttribute('disabled');
            document.getElementsByName('name-company')[0].setAttribute('disabled',true);
            document.getElementById('phone-company').setAttribute('disabled',true);
            document.getElementById('email-company').setAttribute('disabled',true);
            document.getElementById('web-company').setAttribute('disabled',true);
            document.getElementById('note-company').setAttribute('disabled',true);
            document.getElementsByName('name-company')[0].value = '';
            document.getElementById('phone-company').value = '';
            document.getElementById('email-company').value = '';
            document.getElementById('web-company').value = '';
            document.getElementById('note-company').value = '';
            document.getElementsByName('select_company')[0].firstElementChild.removeAttribute('selected');
        }
    }
