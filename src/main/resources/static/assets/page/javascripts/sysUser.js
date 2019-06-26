/*引用theme.js*/
$(document).ready(function() {
    getAuthoritylist();
    finduser();
});

function finduser() {
    /*定义列id和名称*/
    var aoColumns = [{
            mDataProp: "id",
            sTitle: "ID"
        }, {
            mDataProp: "nickname",
            sTitle: "登录账号"
        }, {
            mDataProp: "name",
            sTitle: "真实姓名"
        }, {
            mDataProp: "mobile",
            sTitle: "手机号码"
        }, {
            mDataProp: "email",
            sTitle: "email"
        },{
            mDataProp: "note",
            sTitle: "用户身份"
        }, {
            mDataProp: "id",
            sTitle: "操作"
        }

    ];

    /*给操作列设置填充 */
    var aoColumnDefs = [{
            "bSortable": false,
            "aTargets": [0, 1, 2, 3, 4, 5, 6,]
        }, {
            "sDefaultContent": '',
            "aTargets": ['_all']
        }, {
            "aTargets": [6],
            "mRender": function(data, type, row) {
                return " <div class='text-left'><a class='btn btn-primary btn-mini' data-toggle='modal' href='#modal-userdetail' role='button' onclick='getuser(" + data + ")'><i class='icon-search'></i>查看</a> <a class='btn btn-success btn-mini' data-toggle='modal' href='#modal-adduser' role='button' style='background-color:#00BB00' onclick=modifyuser1(" + data + ")><i class='icon-pencil'></i>修改</a> <a class='btn btn-danger btn-mini' data-toggle='modal' href='#modal-deleteuser' role='button'><i class='icon-remove'></i>删除</a> <a class='btn btn-danger btn-mini' data-toggle='modal' href='#modal-resetpassword' role='button'><i class='icon-refresh'></i>重置密码</a> <a class='btn btn-danger btn-mini' data-toggle='modal' href='#modal-lockuser' role='button' id='aclock" + data + "'><i class='icon-lock'></i><span id='clock" + data + "'>" + (row.valid==1 ? "锁定" : "解锁") + "</span></a></div>";
            }
        }, {
            "aTargets": [0],
            "mRender": function(data, type, full) {

                return "<span class='label label-number'>" + data + "</span>";
            }
        }


    ];

    var new_filter_url = "sysUsers/getUserList"; //表#plist数据获取url

    var oTable = setDataTable_ajax($("#plist"), new_filter_url, aoColumns, aoColumnDefs, true);

    oTable.columnFilter();
    var oSettings = oTable.fnSettings();
    oSettings.sAjaxSource = new_filter_url;
    oTable.fnDraw();
}

//var oSettings = oTable.fnSettings();
//oSettings.sAjaxSource = new_filter_url;
//oTable.fnDraw();


/*行点击*/
$('#plist tbody tr').live('click', function() {

    if ($(this).hasClass('selected')) {
        $(this).removeClass('selected');
    } else {
        oTable.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
    }

    var sTitle;
    var nTds = $('td', this);
    var sday = $(nTds[0]).text(); //得到第1列的值------uid

    document.getElementById("uid").value = sday;

});
//重置密码
function resetPassword() {
    var uid = document.getElementById("uid").value;
    var url = "/UserController/resetPassword?id=" + uid;
    url = encodeURI(url);

    $.get(url, function(data) {
        if (data.result == "success") {
            $('#modal-resetpassword').modal('hide');

            layer.msg('重置成功！', {
                icon: 1
            });

        } else {

            layer.msg('重置失败！', {
                icon: 2
            });
        }
    });
}

function lockuser() {
    var uid = document.getElementById("uid").value;
    var url = "/UserController/resetStatus?id=" + uid;
    url = encodeURI(url);

    $.get(url, function(data) {
        if (data.result == "success") {

            if (document.getElementById("clock" + uid).innerHTML == "锁定") {
                $('#modal-lockuser').modal('hide')
                document.getElementById("clock" + uid).innerHTML = "解锁";
                document.getElementById("aclock" + uid).href = "#modal-unlockuser";
                layer.msg('锁定成功！', {
                    icon: 1
                });
            } else {
                $('#modal-unlockuser').modal('hide')
                document.getElementById("clock" + uid).innerHTML = "锁定";
                document.getElementById("aclock" + uid).href = "#modal-lockuser";
                layer.msg('解锁成功！', {
                    icon: 1
                });
            }


        } else {

            layer.msg('锁定失败！', {
                icon: 2
            });
        }
    });
}

/*删除确认按钮onclick*/
$('#deleterow').click(function() {

    var id = document.getElementById("uid").value;

    $.ajax('/UserController/deleteUser', {
        dataType: 'json',
        data: {
            id: id
        },
        success: function(data) {


            if (data.result == 'success') {
                //  $.modal.alert('删除成功!');

                $('#modal-deleteuser').modal('hide')


                start = oTable.fnSettings()._iDisplayStart;
                total = oTable.fnSettings().fnRecordsDisplay();

                if ((total - start) == 1) {
                    if (start > 0) {
                        oTable.fnPageChange('previous', true);
                    }
                }

                oTable.fnDraw();
                layer.msg('删除成功！', {
                    icon: 1
                });
            } else {
                layer.msg('删除失败！', {
                    icon: 2
                });
            }
        },
        error: function() {
            layer.msg('删除失败！', {
                icon: 2
            });
        }
    });


});


/*添加、修改保存按钮onclick*/
function adduser() {

    var uid = document.getElementById("uid").value;
    var login_name = document.getElementById("login_name").value;
    var name = document.getElementById("name").value;
    var tel = document.getElementById("tel").value;
    var officetel = document.getElementById("officetel").value;

    var gender = $("#gender").val();
    var email = document.getElementById("email").value;
    var autName = document.getElementById("autName").value;
    var department = document.getElementById("department").value;

    var flag = 0;

    flag += saveverify("login_name", "login_name_alert");
    flag += saveverify("name", "name_alert");
    flag += saveverify("autName", "autName_alert");
    if (autName == "员工") {
        flag += saveverify("department", "department_alert");
    }

    if (autName != "员工") {
        department = 0;
    }


    if (flag > 0) {

        return 1;
    } else {

        var url = "";

        if (Number(uid) > 0) {

            url = "/UserController/updateUser?login_name=" + login_name + "&autname=" + autName + "&name=" + name + "&gender=" + gender + "&tel=" + tel + "&officetel=" + officetel + "&email=" + email + "&id=" + uid + "&department=" + department;


        } else {
            url = "/UserController/addUser?login_name=" + login_name + "&autname=" + autName + "&name=" + name + "&gender=" + gender + "&tel=" + tel + "&officetel=" + officetel + "&email=" + email + "&department=" + department;

        }

        //alert(url);
        url = encodeURI(url);

        $.ajax({
            url: url,
            success: function(responseText) {

                if (responseText.result == "success") {
                    $('#modal-adduser').modal('hide')


                    oTable.fnDraw(false);//重新加载当前页


                    layer.msg('保存成功！', {
                        icon: 1
                    });
                } else {
                    layer.msg("用户名重复", {
                        icon: 2
                    });
                }

            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {

            }
        });




    }

}

/*用户详情弹出层*/
function getuser(uid) {
    var url = "/UserController/getuser?id=" + uid;
    url = encodeURI(url);
    $.ajax({
        url: url,
        success: function(responseText) {
            var jinfo = responseText.info;
            var str = JSON.stringify(jinfo);

            $("#dlogin_name").val(jinfo.login_name);
            $("#dname").val(jinfo.name);
            $("#dtel").val(jinfo.tel);
            $("#dofficetel").val(jinfo.officetel);
            $("#demail").val(jinfo.email);
            $("#dgender").val(jinfo.gender);

            $("#dautName").val(jinfo.autName);
            $("#ddepartment").val(jinfo.department);
            if (jinfo.autName == "员工") {

                document.getElementById("ddepartment").disabled = "";

            } else {

                document.getElementById("ddepartment").disabled = "disabled";

            }

        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {

        }
    });



}

function initform() {
    document.getElementById("uid").value = "0";
    document.getElementById("login_name").value = "";
    document.getElementById("name").value = "";
    document.getElementById("gender").value = "男";
    document.getElementById("email").value = "";
    document.getElementById("tel").value = "";
    document.getElementById("officetel").value = "";

    document.getElementById("autName").value = "";
    document.getElementById("department").value = "";
}


/*添加用户弹出层*/
function modifyuser(id) {
    initform();
    $("#uid").val(id);
    var url = "/UserController/getuser?id=" + id;
    url = encodeURI(url);
    $.ajax({
        url: url,
        success: function(responseText) {
            var jinfo = responseText.info;
            var str = JSON.stringify(jinfo);
            //  alert(jinfo.login_name);

            $("#login_name").val(jinfo.login_name);
            $("#name").val(jinfo.name);
            $("#tel").val(jinfo.tel);
            $("#officetel").val(jinfo.officetel);
            $("#email").val(jinfo.email);
            $("#gender").val(jinfo.gender);

            $("#autName").val(jinfo.autName);
            $("#department").val(jinfo.department);
            if (jinfo.autName == "员工") {

                document.getElementById("department").disabled = "";

            } else {

                document.getElementById("department").disabled = "disabled";

            }


        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {

        }
    });

}

/*修改用户弹出层*/
function modifyuser1(id) {
    $("#uid").val(id);
    var url = "/UserController/getuser?id=" + id;
    url = encodeURI(url);
    $.ajax({
        url: url,
        success: function(responseText) {
            var jinfo = responseText.info;
            var str = JSON.stringify(jinfo);


            $("#login_name").val(jinfo.login_name);
            $("#name").val(jinfo.name);
            $("#tel").val(jinfo.tel);
            $("#officetel").val(jinfo.officetel);
            $("#email").val(jinfo.email);
            $("#gender").val(jinfo.gender);

            $("#autName").val(jinfo.autName);
            $("#department").val(jinfo.department);


            if (jinfo.autName == "员工") {

                document.getElementById("department").disabled = "";

            } else {

                document.getElementById("department").disabled = "disabled";

            }

        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {

        }
    });

}


function updateauthority() {
    var userid = document.getElementById("uid").value;

    var aaf = getCheckboxvalue("aaf");
    var abf = getCheckboxvalue("abf");

    var faf = getCheckboxvalue("faf");
    var faf = getCheckboxvalue("fbf");


    var url = "/AuthorityController/updateAuthority?userid=" + userid;

    $.post(url, {
        aafunction: aaf,
        abfunction: abf,

        fafunction: faf,
        fafunction: fbf


    }, function(data) {
        if (data.result == "success") {

            $("#modal-setpermissions").modal("hide");

            layer.msg('保存成功！', {
                icon: 1
            });




        } else {
            layer.msg('保存失败！', {
                icon: 2
            });

        }
    });



}

function getCheckboxvalue(suferfix) {

    var fvalue = document.getElementsByName(suferfix);
    var sb = [];
    for (var i = 00; i < fvalue.length; i++) {
        var epfix = i + 1;
        var eid = suferfix + epfix;
        if (document.getElementById(eid).checked) {
            sb.push('1');
        } else {

            sb.push('2');
        }


    }



    return sb.join("");


}

function setauth(userid) {
    /*var userid = document.getElementById("uid").value;*/
    var url = "/AuthorityController/getAuthority?userid=" + userid;

    url = encodeURI(url);

    $.ajax({
        url: url,
        success: function(responseText) {
            var jinfo = responseText.info;
            var str = JSON.stringify(jinfo);


            document.getElementById("aaf").value = jinfo.aafunction;
            document.getElementById("abf").value = jinfo.abfunction;

            document.getElementById("faf").value = jinfo.fafunction;
            document.getElementById("fbf").value = jinfo.fafunction;


            setCheckbox("faf");
            setCheckbox("fbf");



        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {

        }

    });




}



function setCheckbox(suferfix) {

    var fvalue = document.getElementById(suferfix).value;

    var items = fvalue.split("");
    for (var i = 0; i < fvalue.length; i++) {
        var epfix = i + 1;
        var eid = suferfix + epfix;

        if (items[i] == '1') {

            document.getElementById(eid).checked = true;
        } else {

            document.getElementById(eid).checked = false;

        }
    }
}




$("select#department").change(function() {


    getdutylist($("#department").val());


});



function getAuthoritylist() {

    var url = "sysRoles/getRolelist";
    var astr = "<option></option>";
    url = encodeURI(url);

    $.ajax({
        url: url,
        success: function(result) {
            var roles = result.data;

            for (var i = 0; i < roles.length; i++) {

                astr += "<option>" + roles[i].name + "</option>"


            }

            document.getElementById("autName").innerHTML = astr;
            document.getElementById("dautName").innerHTML = astr;

        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {

        }

    });



}




/*function getDepartmentlist() {

    var url = "/DepartmentController/getallDepartments";
    var astr = "<option></option>";
    url = encodeURI(url);

    $.ajax({
        url: url,
        success: function(responseText) {
            var departments = responseText.info;

            for (var i = 0; i < departments.length; i++) {

                astr += "<option value=" + departments[i].id + ">" + departments[i].text + "</option>"

            }

            document.getElementById("department").innerHTML = astr;
            document.getElementById("ddepartment").innerHTML = astr;

        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {

        }

    });
}*/



function autNamechange(obj) {

    var autName = obj.value;
    if (autName == "员工") {

        document.getElementById("department").disabled = "";

    } else {

        document.getElementById("department").disabled = "disabled";
        document.getElementById("department").value = "";

    }






}
