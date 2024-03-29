function updatepwd() {
    var lang = navigator.language||navigator.userLanguage;
    lang = lang.substr(0, 2);
    var oldpwd = document.getElementById("oldpwd").value;
    var newpwd = document.getElementById("newpwd").value;
    var newpwd2 = document.getElementById("newpwd2").value;
    var flag = 0;
    flag += saveverify("oldpwd", "oldpwdalert");

    var alertstr = "";
    if (flag > 0) {
        document.getElementById("oldpwdalert").style.display = "";
        if("zh"==lang){
            alertstr = "原密码不能为空！";
        }else{//es
            alertstr = "La contraseña original no pueda estar vacía！";
        }
        layer.msg(alertstr, {
            icon: 2
        });
        return;
    }
    flag += saveverify("newpwd", "newpwdalert");
    if (flag > 0) {
        document.getElementById("newpwdalert").style.display = "";
        if("zh"==lang){
            alertstr = "新密码不能为空！";
        }else{//es
            alertstr = "La nueva contraseña no pueda estar vacía！";
        }
        layer.msg(alertstr, {
            icon: 2
        });
        
        return;
    }
    flag += saveverify("newpwd2", "newpwd2alert");
    if (flag > 0) {
        if("zh"==lang){
            alertstr = "请再输入一遍新密码！";
        }else{//es
            alertstr = "Ingrese nueva contraseña de otra vez！";
        }
        layer.msg(alertstr, {
            icon: 2
        });

        
        return;
    }
    if (newpwd != newpwd2) {
        if("zh"==lang){
            alertstr = "两次输入的密码不一致！";
        }else{//es
            alertstr = "No coinciden contraseñas introducida por dos veces！";
        }
        layer.msg(alertstr, {
            icon: 2
        });

        return;
    }
    var url = "sysUsers/updatePwd";
    var params={
        oldpwd:oldpwd,
        newpwd:newpwd,
    }
    url = encodeURI(url);
    $.ajax({
        url: url,
        data: params,
        type: "POST",
        success: function (result) {
            if(result.state == 1){
                layer.msg(result.message,{
                    icon: 1
                });
                window.location.href = "logout";
            }else if(result.state == 0){
                layer.msg(result.message,{
                    icon:2
                })
            }
        },
        error: function (result) {
            if("zh"==lang){
                alertstr = "原密码错误！";
            }else{//es
                alertstr = "La contraseña original es incorrecta！";
            }
            layer.msg(alertstr,{
                icon: 2
            });
        }
    })
}
/*输入为空，标红提示*/
function verify(obj, tipinput, tipinfo, tipinfo2) {
    var inputv = obj.value;
    if (inputv.replace(/^ +| +$/g, '') == '') {
        document.getElementById(tipinput).value = tipinfo;
        obj.focus();
        return;
    } else {
        document.getElementById(tipinput).value = "";
    }
    /*此处读数据库中密码，并与用户输入的“原密码”进行比较，不符合则标红提示。待补充*/
    if (inputv != password) {
        document.getElementById(tipinput).value = tipinfo2;
        obj.focus();
        return;
    } else {
        document.getElementById(tipinput).value = "";
    }
}
/*比较两次输入的新密码是否一致*/
function verifysame(obj, newpwd, tipinput, tipinfo) {
    var inputv = obj.value;
    /*比较两次输入的新密码是否一致*/
    if (inputv != document.getElementById(newpwd).value) {
        document.getElementById(tipinput).value = tipinfo;
        obj.focus();
        return;
    } else {
        document.getElementById(tipinput).value = "";
    }
}

function saveverify(checkinput, alertinput) {
    var inputv = $("#" + checkinput).val();
    if (inputv.replace(/^ +| +$/g, '') == '') {
        var lan = navigator.language||navigator.userLanguage;
        lan = lan.substr(0, 2);
        if("zh"==lan){
            document.getElementById(alertinput).value = "必填";
        }else{
            document.getElementById(alertinput).value = "Requerido";
        }
        return 1;
    } else {
        document.getElementById(alertinput).value = "";
        return 0;
    }
}

function cancelvalue() {
    document.getElementById("oldpwd").value = "";
    document.getElementById("newpwd").value = "";
    document.getElementById("newpwd2").value = "";
}
