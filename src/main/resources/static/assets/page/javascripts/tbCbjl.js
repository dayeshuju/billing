/*引用theme.js*/
$(document).ready(function () {
    findCbjl();
});

function findCbjl() {
    /*定义列id和名称*/
    var aoColumns = [{
        mDataProp: "sort",
        sTile: "序号"
    }, {
        mDataProp: "name",
        sTitle: "姓名"
    }, {
        mDataProp: "idCode",
        sTitle: "身份证号"
    }, {
        mDataProp: "meterId",
        sTitle: "表号"
    }, {
        mDataProp: "regisTime",
        sTitle: "抄表时间"
    }, {
        mDataProp: "meterNum",
        sTitle: "表示数"
    }, {
        mDataProp: "id",
        sTitle: "操作"
    }

    ];

    /*给操作列设置填充 */
    var aoColumnDefs = [{
        "bSortable": false,
        "aTargets": [0, 1, 2, 3, 4, 5, 6]
    }, {
        "sDefaultContent": '',
        "aTargets": ['_all']
    }, {
        "aTargets": [6],
        "mRender": function (data, type, row) {
            return " <div class='text-left'><a class='btn btn-success btn-mini' data-toggle='modal' href='#modal-historycbjl' role='button' style='background-color:#00BB00' onclick=getHistoryCbjl(" + row.meterId + ")><i class='icon-pencil'></i>历史数据</a> <a class='btn btn-danger btn-mini' data-toggle='modal' href='#modal-deletecbjl' role='button' onclick=saveCbjlId(" + data + ")><i class='icon-remove'></i>删除</a></div>";
        }
    }
    ];

    var new_filter_url = "tbCbjl/getCbjlList"; //表#plist数据获取url

    var oTable = setDataTable_ajax($("#plist"), new_filter_url, aoColumns, aoColumnDefs, true);

    oTable.columnFilter();
    var oSettings = oTable.fnSettings();
    oSettings.sAjaxSource = new_filter_url;
    oTable.fnDraw();
}

//var oSettings = oTable.fnSettings();
//oSettings.sAjaxSource = new_filter_url;
//oTable.fnDraw();

function getHistoryCbjl(meterId) {
    /*定义列id和名称*/
    var aoColumns = [{
        mDataProp: "sort",
        sTile: "序号"
    }, {
        mDataProp: "name",
        sTitle: "姓名"
    }, {
        mDataProp: "idCode",
        sTitle: "身份证号"
    }, {
        mDataProp: "meterId",
        sTitle: "表号"
    }, {
        mDataProp: "regisTime",
        sTitle: "抄表时间"
    }, {
        mDataProp: "meterNum",
        sTitle: "表示数"
    }, {
        mDataProp: "id",
        sTitle: "操作"
    }

    ];

    /*给操作列设置填充 */
    var aoColumnDefs = [{
        "bSortable": false,
        "aTargets": [0, 1, 2, 3, 4, 5, 6]
    }, {
        "sDefaultContent": '',
        "aTargets": ['_all']
    }, {
        "aTargets": [6],
        "mRender": function (data, type, row) {
            return " <div class='text-left'><a class='btn btn-success btn-mini' data-toggle='modal' href='#modal-historycbjl' role='button' style='background-color:#00BB00' onclick=getHistoryCbjl(" + row.meterId + ")><i class='icon-pencil'></i>历史数据</a> <a class='btn btn-danger btn-mini' data-toggle='modal' href='#modal-deletecbjl' role='button' onclick=saveCbjlId(" + data + ")><i class='icon-remove'></i>删除</a></div>";
        }
    }
    ];

    var new_filter_url = "tbCbjl/getHistoryCbjlList?meterId"+meterId; //表#plist数据获取url

    var oTable = setDataTable_ajax_5($("#historyPlist"), new_filter_url, aoColumns, aoColumnDefs, false);

    oTable.columnFilter();
    var oSettings = oTable.fnSettings();
    oSettings.sAjaxSource = new_filter_url;
    oTable.fnDraw();
}

function saveCbjlId(id){
    $("#id").val(id);
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
    var name = $("#name").val();
    var idCode = $("#idCode").val();
    var phoneNum = $("#phoneNum").val();
    var address = $("#address").val();
    var yhlxId = $("#yhlxId").val();

    var params = {
        id: id,
        name: name,
        idCode: idCode,
        phoneNum: phoneNum,
        address: address,
        userTypeId: yhlxId
    }

    var url = "";
    if (Number(id) > 0) {
        url = "tbYdyh/updateYdyh";
    } else {
        url = "tbYdyh/addYdyh";
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
    $("#name").val("");
    $("#idCode").val("");
    $("#phoneNum").val("");
    $("#yhlxId").val("");
    $("#address").val("");
}

/*修改用户弹出层*/
function modifyuser(id) {
    initform();
    $("#id").val(id);
    var url = "tbYdyh/getYdyh";
    var params = {
        id: id
    };
    $.post(url, params, function (result) {
        if (result.state == 1) {
            $("#name").val(result.data.name);
            $("#idCode").val(result.data.idCode);
            $("#phoneNum").val(result.data.phoneNum);
            $("#yhlxId").val(result.data.userTypeId);
            $("#address").val(result.data.address);
        }
    })
}

function getYhlxlist() {

    var url = "tbYdyh/getYhlxlist";
    var astr = "<option></option>";
    url = encodeURI(url);

    $.ajax({
        url: url,
        success: function (result) {
            var yhlxs = result.data;

            for (var i = 0; i < yhlxs.length; i++) {

                astr += "<option value='" + yhlxs[i].id + "'>" + yhlxs[i].userType + "</option>"


            }

            document.getElementById("yhlxId").innerHTML = astr;

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }

    });


}
