/*引用theme.js*/
$(document).ready(function () {
    findyhlx();
});

function findyhlx() {
    /*定义列id和名称*/
    var aoColumns = [{
        mDataProp: "id",
        sTitle: "ID"
    }, {
        mDataProp: "userType",
        sTitle: "用户类型"
    }, {
        mDataProp: "tate",
        sTitle: "电费费率"
    }, {
        mDataProp: "id",
        sTitle: "操作"
    }

    ];

    /*给操作列设置填充 */
    var aoColumnDefs = [{
        "bSortable": false,
        "aTargets": [0, 1, 2, 3]
    }, {
        "sDefaultContent": '',
        "aTargets": ['_all']
    }, {
        "aTargets": [3],
        "mRender": function (data, type, row) {
            return " <div class='text-left'><a class='btn btn-success btn-mini' data-toggle='modal' href='#modal-addYhlx' role='button' style='background-color:#00BB00' onclick=modifyyhlx(" + data + ")><i class='icon-pencil'></i>修改</a></div>";
        }
    }, {
        "aTargets": [0],
        "mRender": function (data, type, full) {

            return "<span class='label label-number'>" + data + "</span>";
        }
    }


    ];

    var new_filter_url = "tbYhlx/getYhlxList"; //表#plist数据获取url

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

    $("#yhlxId").val(sday);

});



/*删除确认按钮onclick*/
/*$('#deleterow').click(function () {

    var yhlxId = $("#yhlxId").val();
    var params = {
        id: yhlxId
    }
    var url = "tbYhlx/deleteYhlx";

    $.post(url, params, function (result) {
        $('#modal-deleteyhlx').modal('hide');
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


/*添加、修改保存按钮onclick*/
function addYhlx() {
    var id = $("#yhlxId").val();
    var userType = $("#userType").val();
    var tate = $("#tate").val();

    var params = {
        id: id,
        userType: userType,
        tate: tate,
    }

    var url = "";
    if (Number(id) > 0) {
        url = "tbYhlx/updateYhlx";
    } else {
        url = "tbYhlx/addYhlx";
    }

    $.post(url, params, function (result) {
        if (result.state == 1) {
            $('#modal-addYhlx').modal('hide');
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
    $("#yhlxId").val("0");
    $("#userType").val("");
    $("#tate").val("");
}

/*修改用户弹出层*/
function modifyyhlx(id) {
    initform();
    $("#yhlxId").val(id);
    var url = "tbYhlx/getYhlx";
    var params = {
        id: id
    };
    $.post(url, params, function (result) {
        if (result.state == 1) {
            $("#userType").val(result.data.userType);
            $("#tate").val(result.data.tate);
        }
    })
}

