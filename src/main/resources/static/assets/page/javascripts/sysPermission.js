/*引用theme.js*/
var zTree;
var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",  //节点数据中保存唯一标识的属性名称
            pIdKey: "parentId",  //节点数据中保存其父节点唯一标识的属性名称
            rootPId: null  //根节点id
        }
    },
    check: {
        enable: true,
        nocheckInherit: true
    }//此配置提供复选框操作(可查官方zTree)
}

function doLoadSysMenus() {
    var url = "sysMenu/doFindZtreeMenuNodes"
    $.getJSON(url, function (result) {
        if (result.state == 1) {
            zTree = $.fn.zTree.init(
                $("#menuTree"), setting, result.data);
        } else {
            layer.msg(result.message, {
                icon: 2
            });
        }
    });
}

$(document).ready(function () {
    /*定义列id和名称*/
    var aoColumns = [{
        mDataProp: "id",
        sTitle: "ID"
    }, {
        mDataProp: "name",
        sTitle: "权限类型"
    }, {
        mDataProp: "id",
        sTitle: "操作"
    }

    ];

    /*给操作列设置填充 */
    var aoColumnDefs = [{
        "bSortable": false,
        "aTargets": [0, 1, 2]
    }, {
        "sDefaultContent": '',
        "aTargets": ['_all']
    }, {
        "aTargets": [2],
        "mRender": function (data, type, row) {

            return " <div class='text-left'> <a class='btn btn-primary btn-mini' data-toggle='modal' href='#modal-setpermissions' role='button' style='background-color:#00BB00' onclick='setauth(\"" + row.id + "\")'><i class='icon-wrench'></i> 权限设置</a>  <a class='btn btn-danger btn-mini' data-toggle='modal' href='#modal-deleteauthority' role='button'><i class='icon-remove'></i> 删除</a></div>";
        }
    }, {
        "aTargets": [0],
        "mRender": function (data, type, full) {

            return "<span class='label label-number'>" + data + "</span>";
        }
    }


    ];

    var new_filter_url = "sysRoles/getAuthoritylist"; //表#plist数据获取url

    var oTable = setDataTable_ajax($("#plist"), new_filter_url, aoColumns, aoColumnDefs, true);

    oTable.columnFilter();


});
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
    var sday = $(nTds[1]).text(); //得到第1列的值------uid

    document.getElementById("autName").value = sday;

});

/*删除确认按钮onclick*/
$('#deleterow').click(function () {

    var autName = document.getElementById("autName").value;

    $.ajax('/AuthorityController/deleteAuthority', {
        dataType: 'json',
        data: {
            autName: autName
        },
        success: function (data) {


            if (data.result == 'success') {
                //  $.modal.alert('删除成功!');

                $('#modal-deleteauthority').modal('hide')


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
        error: function () {
            layer.msg('删除失败！', {
                icon: 2
            });
        }
    });


});


/*添加、修改保存按钮onclick*/
function addauthority() {

    var autName = document.getElementById("add_autName").value;
    var url = "";


    url = "/AuthorityController/addAuthority?autName=" + autName;


    url = encodeURI(url);

    $.ajax({
        url: url,
        success: function (responseText) {

            if (responseText.result == "success") {
                $('#modal-addauthority').modal('hide')
                document.getElementById("add_autName").value = "";

                oTable.fnDraw();
                layer.msg('保存成功！', {
                    icon: 1
                });
            } else {
                layer.msg("用户名重复", {
                    icon: 2
                });
            }

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });


}


function initform() {
    document.getElementById("autName").value = "";
    document.getElementById("login_name").value = "";
    document.getElementById("name").value = "";
    document.getElementById("gender").value = "男";
    document.getElementById("email").value = "";
    document.getElementById("tel").value = "";
    document.getElementById("officetel").value = "";
    document.getElementById("islawyer").value = "是";
    document.getElementById("lawnum").value = "";
    document.getElementById("duty").value = "";
    document.getElementById("education").value = "";
}

function updateauthority() {
    var autName = document.getElementById("autName").value;
    var aaf = getCheckboxvalue("aaf");
    var abf = getCheckboxvalue("abf");

    var baf = getCheckboxvalue("baf");
    var bbf = getCheckboxvalue("bbf");

    var bcf = getCheckboxvalue("bcf");


    var faf = getCheckboxvalue("faf");
    var fbf = getCheckboxvalue("fbf");
    var fcf = getCheckboxvalue("fcf");

    var url = "/AuthorityController/updateAuthority?autName=" + autName;
    $.post(url, {
        aafunction: aaf,
        abfunction: abf,

        bafunction: baf,
        bbfunction: bbf,
        bcfunction: bcf,


        fafunction: faf,
        fbfunction: fbf,
        fcfunction: fcf,

    }, function (data) {
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

function setauth(autName) {

    var url = "/AuthorityController/getAuthority?autName=" + autName;

    url = encodeURI(url);

    $.ajax({
        url: url,
        success: function (responseText) {
            var jinfo = responseText.info;
            var str = JSON.stringify(jinfo);

            document.getElementById("aaf").value = jinfo.aafunction;
            document.getElementById("abf").value = jinfo.abfunction;
            setCheckbox("aaf");
            setCheckbox("abf");


            document.getElementById("baf").value = jinfo.bafunction;
            document.getElementById("bbf").value = jinfo.bbfunction;
            document.getElementById("bcf").value = jinfo.bcfunction;
            setCheckbox("baf");
            setCheckbox("bbf");
            setCheckbox("bcf");


            document.getElementById("faf").value = jinfo.fafunction;
            document.getElementById("fbf").value = jinfo.fbfunction;
            document.getElementById("fcf").value = jinfo.fcfunction;
            setCheckbox("faf");
            setCheckbox("fbf");
            setCheckbox("fcf");


        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }

    });


}


function setCheckbox(suferfix) {

    var fvalue = document.getElementById(suferfix).value;
    var items = fvalue.split("");
    for (var i = 0; i < fvalue.length; i++) {
        var epfix = i + 1;
        var eid = suferfix + epfix;

        //alert(eid)

        if (items[i] == '1') {

            document.getElementById(eid).checked = true;
        } else {

            document.getElementById(eid).checked = false;

        }
    }
}


$("select#department").change(function () {


    getdutylist($("#department").val());


});
