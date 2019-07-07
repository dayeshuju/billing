/*引用theme.js*/
$(document).ready(function () {
    findJfjl();
    // $('#plist').DataTable( {
    //     initComplete: function () {
    //         var api = this.api();
    //         api.columns().indexes().flatten().each( function ( i ) {
    //             var column = api.column( i );
    //             var select = $('<select><option value=""></option></select>')
    //                 .appendTo( $(column.footer()).empty() )
    //                 .on( 'change', function () {
    //                     var val = $.fn.dataTable.util.escapeRegex(
    //                         $(this).val()
    //                     );
    //                     column
    //                         .search( val ? '^'+val+'$' : '', true, false )
    //                         .draw();
    //                 } );
    //             column.data().unique().sort().each( function ( d, j ) {
    //                 select.append( '<option value="'+d+'">'+d+'</option>' )
    //             } );
    //         } );
    //     }
    // } );
});

function findJfjl() {
    /*定义列id和名称*/
    var aoColumns = [{
        mDataProp: "sort",
        sTile: "序号",
        sWidth: "50px"
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
        mDataProp: "amountDue",
        sTitle: "应缴电费"
    }, {
        mDataProp: "regisTime",
        sTitle: "抄表时间"
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
            return " <div class='text-left'><a class='btn btn-success btn-mini' data-toggle='modal' href='#modal-lookmsg' role='button' style='background-color:#00BB00' onclick=lookJfyhMsg(" + data + ")><i class='icon-search'></i>收费</a></div>";
        }
    }
    ];

    var new_filter_url = "syt/getJfyhList"; //表#plist数据获取url

    var oTable = setDataTable_ajax($("#plist"), new_filter_url, aoColumns, aoColumnDefs, true);

    oTable.columnFilter();
    var oSettings = oTable.fnSettings();
    oSettings.sAjaxSource = new_filter_url;
    oTable.fnDraw();
}

//var oSettings = oTable.fnSettings();
//oSettings.sAjaxSource = new_filter_url;
//oTable.fnDraw();

function saveJfjlId(id){
    $("#id").val(id);
}

function initform(starttime,endtime) {
    $("#name").text("");
    $("#idCode").text("");
    $("#meterNum").text("");
    $("#address").text("");
    $("#periodElecNum").text("");
    $("#tate").text("");
    $("#amountDue").text("");
    $("#actualAmount").val("");
    $("#note").val("");
}

/*弹出层展示抄表记录详细信息*/
function lookJfyhMsg(id) {
    saveJfjlId(id);
    var url = "syt/getJfyh";
    var params = {
        id: id
    };
    $.post(url, params, function (result) {
        if (result.state == 1) {
            $("#name").text(result.data.name);
            $("#idCode").text(result.data.idCode);
            $("#meterNum").text(result.data.meterId);
            $("#address").text(result.data.address);
            $("#periodElecNum").text(result.data.periodElecNum);
            $("#tate").text(result.data.tate);
            $("#amountDue").text(result.data.amountDue);
        }
    })
}

function printFactura() {
    var id = $("#id").val();
    var actualAmount = $("#actualAmount").val();
    var note = $("#note").val();
    var params = {
        id: id,
        actualAmount: actualAmount,
        note: note,
    }
    var url="syt/saveJfyh";
    $.post(url,params,function (result) {
        if(result.state == 1){
            $("#modal-lookmsg").modal('hide');
            oTable.fnDraw(false);
        }else{
            layer.msg(result.message,{
                icon:2
            })
        }
    })
}