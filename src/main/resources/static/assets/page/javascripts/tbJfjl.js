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
        mDataProp: "payTime",
        sTitle: "缴费时间"
    }, {
        mDataProp: "payStatus",
        sTitle: "缴费状态"
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
            return " <div class='text-left'><a class='btn btn-success btn-mini' data-toggle='modal' href='#modal-lookmsg' role='button' style='background-color:#00BB00' onclick=lookJfjlMsg(" + data + ")><i class='icon-search'></i>查看</a><a class='btn btn-success btn-mini' data-toggle='modal' href='#modal-historyjfjl' role='button' style='background-color:#00BB00' onclick=saveJfjlMeterId(" + row.meterId + ")><i class='icon-pencil'></i>历史数据</a> <a class='btn btn-danger btn-mini' data-toggle='modal' href='#modal-deletejfjl' role='button' onclick=saveJfjlId(" + data + ")><i class='icon-remove'></i>删除</a></div>";
        }
    }
    ];

    var new_filter_url = "tbJfjl/getJfjlList"; //表#plist数据获取url

    var oTable = setDataTable_ajax($("#plist"), new_filter_url, aoColumns, aoColumnDefs, true);

    oTable.columnFilter();
    var oSettings = oTable.fnSettings();
    oSettings.sAjaxSource = new_filter_url;
    oTable.fnDraw();
}

//var oSettings = oTable.fnSettings();
//oSettings.sAjaxSource = new_filter_url;
//oTable.fnDraw();


function saveJfjlMeterId(meterId) {
    initform("","");
    $("#meterId").val(meterId);
}
function getHistoryJfjl(meterId) {
    var startTime = $("#starttime").val();
    var endTime = $("#endtime").val();
    initform(startTime,endTime);
    $(".delDiv").remove();
    var meterId = $("#meterId").val();

    if(startTime == "" && endTime != ""){
        layer.msg("请输入开始时间", {
            icon: 2
        });
        return;
    }
    if(startTime != "" && endTime == ""){
        layer.msg("请输入结束", {
            icon: 2
        });
        return;
    }
    if(Date.parse(startTime)>Date.parse(endTime)){
        layer.msg("开始时间不得晚于结束时间", {
            icon: 2
        });
        return;
    }
    $("#historyPlist").dataTable({
        sDom: "<'row-fluid'<'span6'l><'span6 text-right'f>r>t<'row-fluid'<'span6'i><'span6 text-right'p>>",
        iDisplayLength: 12,
        sPaginationType: "bootstrap",
        sAjaxSource: "tbJfjl/getHistoryJfjlList",
        bFilter: false,
        bLengthChange: false,
        oLanguage: {
            "sLengthMenu": "_MENU_ 条/页",
            "sSearch": "搜索:",
            "sZeroRecords": "没有检索到数据",
            "sInfo": "显示 _START_ 至 _END_ 条 &nbsp;&nbsp;共 _TOTAL_ 条",
            "sInfoFiltered": "(筛选自 _MAX_ 条数据)",
            "sInfoEmpty": "当前显示0到0条，共0条记录",
            "sEmptyTable": "没有获取到数据",
            "sProcessing": "正在加载数据...",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "前一页",
                "sNext": "后一页",
                "sLast": "末页"
            }


        },
        //bLengthChange: true,
        bInfo: true,
        bSortL:true,
        bRetrieve:true,
        bDestroy: true,
        bAutoWidth: true,
        bStateSave: false,
        bProcessing: false, //开启读取服务器数据时显示正在加载中……特别是大数据量的时候，开启此功能比较好
        bServerSide: true, //开启服务器模式，使用服务器端处理配置datatable。注意：sAjaxSource参数也必须被给予为了给datatable源代码来获取所需的数据对于每个画。 这个翻译有点别扭。开启此模式后，你对datatables的每个操作 每页显示多少条记录、下一页、上一页、排序（表头）、搜索，这些都会传给服务器相应的值。
        aoColumns: [{
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
            mDataProp: "payTime",
            sTitle: "缴费时间"
        }, {
            mDataProp: "payStatus",
            sTitle: "缴费状态"
        }, {
            mDataProp: "id",
            sTitle: "操作"
        }

        ],
        aoColumnDefs: [{
            "bSortable": false,
            "aTargets": [0, 1, 2, 3, 4, 5, 6]
        }, {
            "sDefaultContent": '',
            "aTargets": ['_all']
        }, {
            "aTargets": [6],
            "mRender": function (data, type, row) {
                return " <div class='text-left'><a class='btn btn-danger btn-mini' data-toggle='modal' href='#modal-deletejfjl' role='button' onclick=saveJfjlId(" + data + ")><i class='icon-remove'></i>删除</a></div>";
            }
        }
        ],

        fnServerData: function(sSource, aoData, fnCallback) {
            $.ajax({
                url: sSource, //这个就是请求地址对应sAjaxSource
                data: {
                    "aoData": JSON.stringify(aoData),
                    "meterId": meterId,
                    "startTime":startTime,
                    "endTime": endTime
                },
                type: 'POST',
                dataType: 'json',
                async: false,
                success: function(result) {
                    //alert("ss");
                    //var obj=JSON.parse(result);
                    //alert(JSON.stringify(result));
                    fnCallback(result); //把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
                }
            });

        }
    })
}

function saveJfjlId(id){
    $("#id").val(id);
}

/*删除确认按钮onclick*/
$('#deleterow').click(function () {

    var id = $("#id").val();

    var params = {
        id: id
    }
    var url = "tbJfjl/deleteJfjl";

    $.post(url, params, function (result) {
        $('#modal-deletejfjl').modal('hide');
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



function initform(starttime,endtime) {
    $("#starttime").val(starttime);
    $("#endtime").val(endtime);
    $("#cellBox").html("<div class=\"span6 delDiv\"></div><div class=\"span6 text-right delDiv\"></div>" +
        "                      <table class='data-table data-table-column-filter table table-bordered table-striped table-hover'\n" +
        "                               style='margin-bottom:0;' id=\"historyPlist\">\n" +
        "                            <thead>\n" +
        "                            <tr>\n" +
        "                                <th>序号</th>\n" +
        "                                <th>姓名</th>\n" +
        "                                <th>身份证号</th>\n" +
        "                                <th>表号</th>\n" +
        "                                <th>抄表时间</th>\n" +
        "                                <th>表示数</th>\n" +
        "                                <th>操作</th>\n" +
        "                            </tr>\n" +
        "                            </thead>\n" +
        "                        </table>");
}

/*弹出层展示抄表记录详细信息*/
function lookJfjlMsg(id) {
    $("#id").val(id);
    var url = "tbJfjl/getJfjl";
    var params = {
        id: id
    };
    $.post(url, params, function (result) {
        if (result.state == 1) {
            $("#name").text(result.data.name);
            $("#idCode").text(result.data.idCode);
            $("#meterNum").text(result.data.meterId);
            $("#payTime").text(result.data.payTime);
            if (result.data.payStatu==0){$("#payStatus").text("未缴费");}
            if (result.data.payStatu==1) {$("#payStatus").text("欠费");}
            if (result.data.payStatu==2) {$("#payStatus").text("已缴费");}
            $("#periodElecNum").text(result.data.periodElecNum);
            $("#amountDue").text(result.data.amountDue);
            $("#actualAmount").text(result.data.actualAmount);
            if(result.data.receiptStatus==0){$("#receiptStatus").text("未打印");}
            if(result.data.receiptStatus==1){$("#receiptStatus").text("已打印");}

            $("#createdTime").text(result.data.createdTime);
            $("#modifiedTime").text(result.data.modifiedTime);
            $("#note").text(result.data.note);
            $("#address").text(result.data.address);
            $("#phoneNum").text(result.data.phoneNum);
            $("#tate").text(result.data.tate);
            if(result.data.valid==0){$("#valid").text("禁用");}
            if(result.data.valid==1){$("#valid").text("启用");}

            $("#createdUserTime").text(result.data.createdUserTime);
            $("#modifiedUserTime").text(result.data.modifiedUserTime);
            $("#userNote").text(result.data.userNote);
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
