/*引用theme.js*/
$(document).ready(function () {
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
        mDataProp: "officePhone",
        sTitle: "办工电话"
    }, {
        mDataProp: "email",
        sTitle: "email"
    }, {
        mDataProp: "note",
        sTitle: "权限类型"
    }, {
        mDataProp: "id",
        sTitle: "操作"
    }

    ];

    /*给操作列设置填充 */
    var aoColumnDefs = [{
        "bSortable": false,
        "aTargets": [0, 1, 2, 3, 4, 5, 6, 7]
    }, {
        "sDefaultContent": '',
        "aTargets": ['_all']
    }, {
        "aTargets": [7],
        "mRender": function (data, type, row) {
            return " <div class='text-left'><a class='btn btn-success btn-mini' data-toggle='modal' href='#modal-adduser' role='button' style='background-color:#00BB00' onclick=modifyuser(" + data + ")><i class='icon-pencil'></i>修改</a> <a class='btn btn-danger btn-mini' data-toggle='modal' href='#modal-deleteuser' role='button'><i class='icon-remove'></i>删除</a> <a class='btn btn-danger btn-mini' data-toggle='modal' href='#modal-resetpassword' role='button'><i class='icon-refresh'></i>重置密码</a> <a class='btn btn-danger btn-mini' data-toggle='modal' href=" + (row.valid == 1 ? "#modal-lockuser" : "#modal-unslockuser") + " role='button' id='aclock" + data + "'><i class='icon-lock'></i><span id='clock" + data + "'>" + (row.valid == 1 ? "锁定" : "解锁") + "</span></a></div>";
        }
    }, {
        "aTargets": [0],
        "mRender": function (data, type, full) {

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
$('#plist tbody tr').live('click', function () {

    if ($(this).hasClass('selected')) {
        $(this).removeClass('selected');
    } else {
        oTable.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
    }

    var sTitle;
    var nTds = $('td', this);
    var sday = $(nTds[0]).text(); //得到第1列的值------uid

    $("#id").val(sday);

});

//重置密码
function resetPassword() {
    var id = $("#id").val();
    var params = {
        id: id
    }
    var url = "sysUsers/resetPassword";

    $.post(url, params, function (result) {
        $('#modal-resetpassword').modal('hide');
        if (result.state == 1) {
            layer.msg(result.message, {
                icon: 1
            });
        } else if (result.state == 0) {
            layer.msg(result.message, {
                icon: 2
            });
        }
    })
}

function lockuser() {
    var id = $("#id").val();
    var url = "sysUsers/resetStatus";
    var params = {
        id: id
    }
    $.post(url, params, function (result) {
        if (result.state == 1) {
            if (result.data.valid == 1) {
                $('#modal-unlockuser').modal('hide');
                $("#clock" + id).html("锁定");
                $("#aclock" + id).attr("href", "#modal-lockuser");
                layer.msg(result.data.message, {
                    icon: 1
                });
            } else if (result.data.valid == 0) {
                $('#modal-lockuser').modal('hide');
                $("#clock" + id).html("解锁");
                $("#aclock" + id).attr("href", "#modal-unlockuser");
                layer.msg(result.data.message, {
                    icon: 1
                });
            }
        } else if (result.state == 0) {
            $('#modal-lockuser').modal('hide');
            $('#modal-unlockuser').modal('hide');
            layer.msg(result.message, {
                icon: 2
            });
        }
    });
}

/*删除确认按钮onclick*/
$('#deleterow').click(function () {

    var id = $("#id").val();
    var params = {
        id: id
    }
    var url = "sysUsers/deleteUser";

    $.post(url, params, function (result) {
        $('#modal-deleteuser').modal('hide');
        if (result.state == 1) {

            start = oTable.fnSettings()._iDisplayStart;
            total = oTable.fnSettings().fnRecordsDisplay();

            if ((total - start) == 1) {
                if (start > 0) {
                    oTable.fnPageChange('previous', true);
                }
            }

            oTable.fnDraw();
            layer.msg(result.message, {
                icon: 1
            });
        } else if (result.state == 0) {
            layer.msg(result.message, {
                icon: 2
            });
        }
    })
});


/*添加、修改保存按钮onclick*/
function adduser() {
    var id = $("#id").val();
    var nickname = $("#nickname").val();
    var name = $("#name").val();
    var mobile = $("#mobile").val();
    var officePhone = $("#officePhone").val();
    var email = $("#email").val();
    var roleId = $("#roleId").val();

    if (Number(id) == 2) {
        roleId = 1;
    }

    var params = {
        id: id,
        nickname: nickname,
        name: name,
        mobile: mobile,
        officePhone: officePhone,
        email: email,
        roleId: roleId
    }

    var url = "";
    if (Number(id) > 0) {
        url = "sysUsers/updateUser";
    } else {
        url = "sysUsers/addUser";
    }

    $.post(url, params, function (result) {
        if (result.state == 1) {
            $('#modal-adduser').modal('hide');
            initform();
            oTable.fnDraw(false);//重新加载当前页
            layer.msg(result.message, {
                icon: 1
            });
        }
        ;
        if (result.state == 0) {
            layer.msg(result.message, {
                icon: 2
            });
        }
    })

}

function initform() {
    $("#id").val("0");
    $("#nickname").val("");
    $("#name").val("");
    $("#mobile").val("");
    $("#officePhone").val("");
    $("#email").val("");
    $("#roleId").val(0);
    $("#dnickname").val("");
    $("#dname").val("");
    $("#dmobile").val("");
    $("#dofficePhone").val("");
    $("#demail").val("");
    $("#droleId").val(0);
}

/*修改用户弹出层*/
function modifyuser(id) {
    initform();
    $("#id").val(id);
    var url = "sysUsers/getUser";
    var params = {
        id: id
    };
    $.post(url, params, function (result) {
        if (result.state == 1) {
            $("#nickname").val(result.data.nickname);
            $("#name").val(result.data.name);
            $("#mobile").val(result.data.mobile);
            $("#officePhone").val(result.data.officePhone);
            $("#email").val(result.data.email);
            $("#roleId").val(result.data.roleId);
        }
    })
}

function getAuthoritylist() {

    var url = "sysRoles/getRolelist";
    var astr = "<option></option>";
    url = encodeURI(url);

    $.ajax({
        url: url,
        success: function (result) {
            var roles = result.data;

            for (var i = 0; i < roles.length; i++) {

                astr += "<option value='" + roles[i].id + "'>" + roles[i].name + "</option>"


            }

            document.getElementById("roleId").innerHTML = astr;
            document.getElementById("droleId").innerHTML = astr;

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }

    });


}
