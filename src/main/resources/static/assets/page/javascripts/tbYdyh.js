/*引用theme.js*/
$(document).ready(function () {
    var lang = navigator.language||navigator.userLanguage;
    lang = lang.substr(0, 2);
    getYhlxlist();
    if(lang == 'zh'){
        finduser();
    }else{
        finduserEs();
    }
});

function finduser() {
    /*定义列id和名称*/
    var aoColumns = [{
        mDataProp: "id",
        sTitle: "ID"
    }, {
        mDataProp: "name",
        sTitle: "姓名"
    }, {
        mDataProp: "idCode",
        sTitle: "身份证号"
    }, {
        mDataProp: "address",
        sTitle: "用户地址"
    }, {
        mDataProp: "phoneNum",
        sTitle: "联系方式"
    }, {
        mDataProp: "note",
        sTitle: "用户类型"
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
            return " <div class='text-left'><a class='btn btn-success btn-mini' data-toggle='modal' href='#modal-adduser' role='button' style='background-color:#00BB00' onclick=modifyuser(" + data + ")><i class='icon-pencil'></i>修改</a></div>";
        }
    }, {
        "aTargets": [0],
        "mRender": function (data, type, full) {

            return "<span class='label label-number'>" + data + "</span>";
        }
    }


    ];

    var new_filter_url = "tbYdyh/getYdyhList"; //表#plist数据获取url

    var oTable = setDataTable_ajax($("#plist"), new_filter_url, aoColumns, aoColumnDefs, true);

    oTable.columnFilter();
    var oSettings = oTable.fnSettings();
    oSettings.sAjaxSource = new_filter_url;
    oTable.fnDraw();
}
//西班牙语
function finduserEs() {
    /*定义列id和名称*/
    var aoColumns = [{
        mDataProp: "id",
        sTitle: "ID"
    }, {
        mDataProp: "name",
        sTitle: "Nombre y apellido"
    }, {
        mDataProp: "idCode",
        sTitle: "DNI No"
    }, {
        mDataProp: "address",
        sTitle: "Direccion de usuario"
    }, {
        mDataProp: "phoneNum",
        sTitle: "Contacto"
    }, {
        mDataProp: "note",
        sTitle: "Tipo de usuario"
    }, {
        mDataProp: "id",
        sTitle: "Operar"
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
            return " <div class='text-left'><a class='btn btn-success btn-mini' data-toggle='modal' href='#modal-adduser' role='button' style='background-color:#00BB00' onclick=modifyuser(" + data + ")><i class='icon-pencil'></i>Modificar</a></div>";
        }
    }, {
        "aTargets": [0],
        "mRender": function (data, type, full) {

            return "<span class='label label-number'>" + data + "</span>";
        }
    }


    ];

    var new_filter_url = "tbYdyh/getYdyhList"; //表#plist数据获取url

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

    $("#ydyhId").val(sday);

});

/*删除确认按钮onclick*/
/*$('#deleterow').click(function () {

    var id = $("#ydyhId").val();
    var params = {
        id: id
    }
    var url = "tbYdyh/deleteYdyh";

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
});*/


function adduser() {
    /*添加、修改保存按钮onclick*/
    var name = $("#name").val();
    var patt = /^(?=.*\d.*\b)/;
    if (!patt.test(name)){
        var id = $("#ydyhId").val();
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
    }else{
        var lang = navigator.language||navigator.userLanguage;
        lang = lang.substr(0, 2);
        if("zh"==lang){
            layer.msg("姓名不能包含数字！", {
                icon: 2
            })
        }else {//西班牙语
            layer.msg("姓名不能包含数字！", {
                icon: 2
            })
        }
    }

}

function initform() {
    $("#ydyhId").val("0");
    $("#name").val("");
    $("#idCode").val("");
    $("#phoneNum").val("");
    $("#yhlxId").val("");
    $("#address").val("");
}

/*修改用户弹出层*/
function modifyuser(id) {
    initform();
    $("#ydyhId").val(id);
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
