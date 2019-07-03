function updatepwd() {
    var oldpwd = document.getElementById("oldpwd").value;
    var newpwd = document.getElementById("newpwd").value;
    var newpwd2 = document.getElementById("newpwd2").value;
    var flag = 0;
    flag += saveverify("oldpwd", "oldpwdalert");

    var alertstr = "";
    if (flag > 0) {
        document.getElementById("oldpwdalert").style.display = "";
        
        alertstr = "原密码不能为空！";
        layer.msg(alertstr, {
            icon: 2
        });
        return;
    }
    flag += saveverify("newpwd", "newpwdalert");
    if (flag > 0) {
        document.getElementById("newpwdalert").style.display = "";

        alertstr = "新密码不能为空！";
        layer.msg(alertstr, {
            icon: 2
        });
        
        return;
    }
    flag += saveverify("newpwd2", "newpwd2alert");
    if (flag > 0) {
        alertstr = "请再输入一遍新密码！";
        layer.msg(alertstr, {
            icon: 2
        });

        
        return;
    }
    if (newpwd != newpwd2) {
        
        alertstr = "两次输入的密码不一致！";
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
                var alertstr = "密码修改成功";
                layer.msg(alertstr,{
                    icon: 1
                });
                window.location.href = "logout";
            }
        },
        error: function (result) {
            var alertstr = "原密码错误";
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
        document.getElementById(alertinput).value = "必填项";
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
