/*引用theme.js*/
$(document).ready(function () {
    var lang = navigator.language||navigator.userLanguage;
    lang = lang.substr(0, 2);
    if("zh"==lang){
        findJfjl();
    }else{
        findJfjlEs();
    }
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
            return " <div class='text-left'><a class='btn btn-success btn-mini' data-toggle='modal' href='#modal-lookmsg' role='button' style='background-color:#00BB00' onclick=lookJfjlMsg(" + data + ")><i class='icon-search'></i>查看</a> <a class='btn btn-success btn-mini' data-toggle='modal' href='#modal-historyjfjl' role='button' style='background-color:#00BB00' onclick=saveJfjlMeterId(" + row.meterId + ")><i class='icon-pencil'></i>历史数据</a> </div>";
        }
    }
    ];

    var new_filter_url = "tbJfjl/getJfjlList"; //表#plist数据获取url

    var oTable = setDataTable_ajax($("#plist"), new_filter_url, aoColumns, aoColumnDefs, true);

    oTable.columnFilter();
    var oSettings = oTable.fnSettings();
    oSettings.sAjaxSource = new_filter_url;
    oTable.fnDraw();
};
//西班牙语
function findJfjlEs() {
    /*定义列id和名称*/
    var aoColumns = [{
        mDataProp: "sort",
        sTile: "Serie",
        sWidth: "50px"
    }, {
        mDataProp: "name",
        sTitle: "Nombre y apellido"
    }, {
        mDataProp: "idCode",
        sTitle: "DNI No"
    }, {
        mDataProp: "meterId",
        sTitle: "Contador No"
    }, {
        mDataProp: "payTime",
        sTitle: "Hora de pago"
    }, {
        mDataProp: "payStatus",
        sTitle: "Estado de pago"
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
            return " <div class='text-left'><a class='btn btn-success btn-mini' data-toggle='modal' href='#modal-lookmsg' role='button' style='background-color:#00BB00' onclick=lookJfjlMsg(" + data + ")><i class='icon-search'></i>Ver</a> <a class='btn btn-success btn-mini' data-toggle='modal' href='#modal-historyjfjl' role='button' style='background-color:#00BB00' onclick=saveJfjlMeterId(" + row.meterId + ")><i class='icon-pencil'></i>\n" +
                "Datos históricos</a> </div>";
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
    //initform("","");
    $("#starttime").val("");
    $("#endtime").val("");
    $("#meterId").val(meterId);
    getHistoryJfjl();

}
function getHistoryJfjl() {
    var startTime = $("#starttime").val();
    var endTime = $("#endtime").val();
    initform(startTime,endTime);
    $(".delDiv").remove();
    var meterId = $("#meterId").val();
    var lan = navigator.language||navigator.userLanguage;
    lan = lan.substr(0, 2);
    if(startTime == "" && endTime != ""){
        if("zh"==lan){
            layer.msg("请输入开始时间", {
                icon: 2
            });
        }else{
            layer.msg("Ingrese la hora de inicio", {
                icon: 2
            });
        }
        return;
    }
    if(startTime != "" && endTime == ""){
        if("zh"==lan){
            layer.msg("请输入结束时间", {
                icon: 2
            });
        }else{
            layer.msg("Introduzca la hora de finalización", {
                icon: 2
            });
        }
        return;
    }
    if(Date.parse(startTime)>Date.parse(endTime)){
        if("zh"==lan){
            layer.msg("开始时间不得晚于结束时间", {
                icon: 2
            });
        }else{
            layer.msg("Hora de inicio no pueda ser más tarde que la hora de termino", {
                icon: 2
            });
        }
        return;
    }
    if("zh"==lan){
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
                    debugger;
                    var str;
                    if(row.payStatu == 2){
                        str = "<div class='text-left'><a class='btn btn-success btn-mini' data-toggle='modal' href='#modal-print' role='button' style='background-color:#00BB00' onclick=print(" + data + ")><i class='icon-print'></i>打印收据</a></div>";
                    }
                    return str;
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
    }else{//西班牙语
        $("#historyPlist").dataTable({
            sDom: "<'row-fluid'<'span6'l><'span6 text-right'f>r>t<'row-fluid'<'span6'i><'span6 text-right'p>>",
            iDisplayLength: 12,
            sPaginationType: "bootstrap",
            sAjaxSource: "tbJfjl/getHistoryJfjlList",
            bFilter: false,
            bLengthChange: false,
            oLanguage: {
                "sLengthMenu": "_MENU_ Artículo / página",
                "sSearch": "Búsqueda:",
                "sZeroRecords": "No encontrado con datos",
                "sInfo": "Exhibir _START_ A _END_ Registro &nbsp;&nbsp;Total _TOTAL_ Registro",
                "sInfoFiltered": "(Filtrar desde _MAX_ Datos)",
                "sInfoEmpty": "Actualmente mostrando 0 a 0, un total de 0 registros",
                "sEmptyTable": "No han obtenido datos ",
                "sProcessing": "Cargando datos...",
                "oPaginate": {
                    "sFirst": "Inicio",
                    "sPrevious": "Pagina anterior",
                    "sNext": "Siguiente pagina",
                    "sLast": "Ultima pagina"
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
                sTile: "Serie"
            }, {
                mDataProp: "name",
                sTitle: "Nombre y apellido"
            }, {
                mDataProp: "idCode",
                sTitle: "DNI No"
            }, {
                mDataProp: "meterId",
                sTitle: "Contador No"
            }, {
                mDataProp: "payTime",
                sTitle: "Hora de pago"
            }, {
                mDataProp: "payStatus",
                sTitle: "Estado de pago"
            }, {
                mDataProp: "id",
                sTitle: "Operar"
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
                    debugger;
                    var str;
                    if(row.payStatu == 2){
                        str = "<div class='text-left'><a class='btn btn-success btn-mini' data-toggle='modal' href='#modal-print' role='button' style='background-color:#00BB00' onclick=print(" + data + ")><i class='icon-print'></i>Imprimir recibo</a></div>";
                    }
                    return str;
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
}
function print(sytId) {
    var newWindow = window.open("syt/printFactura?id="+sytId, "_blank");
    setTimeout(function () {
        newWindow.print();
    },2000);

}
/*function saveJfjlId(id){
    $("#jfjlId").val(id);
}*/

/*删除确认按钮onclick*/
/*$('#deleterow').click(function () {

    var jfjlId = $("#jfjlId").val();

    var params = {
        id: jfjlId
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
});*/



function initform(starttime,endtime) {
    $("#starttime").val(starttime);
    $("#endtime").val(endtime);
    $("#cellBox").html(`<div class=\"span6 delDiv\"></div><div class=\"span6 text-right delDiv\"></div>
                              <table class='data-table data-table-column-filter table table-bordered table-striped table-hover'
                                       style='margin-bottom:0;' id=\"historyPlist\">
                                    <thead>
                                    <tr>
                                        <th th:text="#{serialNumber}">序号</th>
                                        <th th:text="#{username}">姓名</th>
                                        <th th:text="#{IDCardNumber}">身份证号</th>
                                        <th th:text="#{meterNumber}">表号</th>
                                        <th th:text="#{cbjl.recordTime}">抄表时间</th>
                                        <th th:text="#{cbjl.meterValue}">表示数</th>
                                        <th th:text="#{operation}">操作</th>
                                    </tr>
                                    </thead>
                                </table>`);
}

/*弹出层展示抄表记录详细信息*/
function lookJfjlMsg(id) {
    $("#jfjlId").val(id);
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
            var la = navigator.language||navigator.userLanguage;
            la = la.substr(0, 2);
            if("zh"==la){
                if (result.data.payStatu==0){$("#payStatus").text("未缴费");}
                if (result.data.payStatu==1) {$("#payStatus").text("欠费");}
                if (result.data.payStatu==2) {$("#payStatus").text("已缴费");}
                if(result.data.receiptStatus==0){$("#receiptStatus").text("未打印");}
                if(result.data.receiptStatus==1){$("#receiptStatus").text("已打印");}
            }else{
                if (result.data.payStatu==0){$("#payStatus").text("Impago");}
                if (result.data.payStatu==1) {$("#payStatus").text("Pago pendiente");}
                if (result.data.payStatu==2) {$("#payStatus").text("Pagado");}
                if(result.data.receiptStatus==0){$("#receiptStatus").text("No imprimido");}
                if(result.data.receiptStatus==1){$("#receiptStatus").text("Imprimir");}
            }
            $("#periodElecNum").text(result.data.periodElecNum);
            $("#amountDue").text(result.data.amountDue);
            $("#actualAmount").text(result.data.actualAmount);
            $("#createdTime").text(result.data.createdTime);
/*            $("#modifiedTime").text(result.data.modifiedTime);
            $("#note").text(result.data.note);*/
            $("#address").text(result.data.address);
            $("#phoneNum").text(result.data.phoneNum);
            $("#tate").text(result.data.tate);
/*
            if(result.data.valid==0){$("#valid").text("禁用");}
            if(result.data.valid==1){$("#valid").text("启用");}
*/

            $("#createdUserTime").text(result.data.createdUserTime);
           $("#printTime").text(result.data.printTime);
/*           $("#userNote").text(result.data.userNote);*/
        }
    })
}


/*function getYhlxlist() {

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


}*/

function clearExportConditions(){
    $("#payStatus_export option:selected").removeAttrs("selected");
    $("#meterId_export").val(null);
    $("#idCode_export").val(null);
    $("#starttime_export").val(null);
    $("#endtime_export").val(null);
    endtime_export.max = laydate.now();
    endtime_export.min = '1900-01-01';
    endtime_export.start = starttime_export.max;
    starttime_export.max = laydate.now();
    starttime_export.min = '1900-01-01';
    starttime_export.start = starttime_export.max;
}

function changeTimeType(){
    var language = navigator.language||navigator.userLanguage;
    language = language.substr(0, 2);
    if("zh"==language){
        if($("#payStatus_export").val()=='2'){
            $(".timeTagStart").html("缴费起始时间：<font color=\"red\">*</font>");
            $(".timeTagEnd").html("缴费结束时间：<font color=\"red\">*</font>");
        }else{
            $(".timeTagStart").html("创建起始时间：<font color=\"red\">*</font>");
            $(".timeTagEnd").html("创建结束时间：<font color=\"red\">*</font>");
        }
    }else{
        if($("#payStatus_export").val()=='2'){
            $(".timeTagStart").html("Hora de inicio del pago：<font color=\"red\">*</font>");
            $(".timeTagEnd").html("Hora de termino del pago：<font color=\"red\">*</font>");
        }else{
            $(".timeTagStart").html("Crear hora de inicio：<font color=\"red\">*</font>");
            $(".timeTagEnd").html("Hora de termino del pago：<font color=\"red\">*</font>");
        }
    }
}

Date.prototype.format = function(format)
{
    var o = {
        "M+" : this.getMonth()+1, //month
        "d+" : this.getDate(),    //day
        "h+" : this.getHours(),   //hour
        "m+" : this.getMinutes(), //minute
        "s+" : this.getSeconds(), //second
        "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
        "S" : this.getMilliseconds() //millisecond
    }
    if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
        (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)if(new RegExp("("+ k +")").test(format))
        format = format.replace(RegExp.$1,
            RegExp.$1.length==1 ? o[k] :
                ("00"+ o[k]).substr((""+ o[k]).length));
    return format;
}

var starttime_export = {
    elem: '#starttime_export',
    format: 'YYYY-MM-DD',
    min: '1900-01-01', //设定最小日期为当前日期
    max: laydate.now(), //最大日期
    istime: false,
    istoday: false,
    choose: function(datas){console.log(datas);
        var slectedDate = new Date(datas);
        slectedDate.setFullYear(slectedDate.getFullYear()+1);
        slectedDate.setDate(slectedDate.getDate()-1);
        if(slectedDate.getTime()>Date.parse(laydate.now())){
            endtime_export.max = laydate.now();
        }else{
            endtime_export.max = slectedDate.format("yyyy-MM-dd");
        }
        endtime_export.min = datas; //开始日选好后，重置结束日的最小日期
        endtime_export.start = endtime_export.max //将结束日的初始值设定为开始日
    }
};
var endtime_export = {
    elem: '#endtime_export',
    format: 'YYYY-MM-DD',
    min: '1900-01-01',
    max: laydate.now(),
    istime: false,
    istoday: false,
    choose: function(datas){
        var slectedEndDate = new Date(datas);
        slectedEndDate.setFullYear(slectedEndDate.getFullYear()-1);
        slectedEndDate.setDate(slectedEndDate.getDate()+1);
        starttime_export.min = slectedEndDate.format("yyyy-MM-dd");console.log(slectedEndDate.format("yyyy-MM-dd"));
        starttime_export.max = datas; //结束日选好后，重置开始日的最大日期
        starttime_export.start = starttime_export.min //将结束日的初始值设定为开始日
    }
};

function showDate(val){
    if(val==0){
        laydate(starttime_export);
    }
    if(val==1){
        laydate(endtime_export);
    }
    $("#laydate_clear").click(function () {
        $("#starttime_export").val(null);
        $("#endtime_export").val(null);
        endtime_export.max = laydate.now();
        endtime_export.min = '1900-01-01';
        endtime_export.start = starttime_export.max;
        starttime_export.max = laydate.now();
        starttime_export.min = '1900-01-01';
        starttime_export.start = starttime_export.max;
    })
}

function exportJfjl(){
    var startTime = $("#starttime_export").val();
    var endTime = $("#endtime_export").val();
    var languages = navigator.language||navigator.userLanguage;
    languages = languages.substr(0, 2);
    if(startTime==''||endTime==''){
        if("zh"==languages){
            layer.msg('时间为必填项，不能为空！', {
                icon: 2
            });
        }else{
            layer.msg('Hora es del campo requerido, no pueda estar vacío！', {
                icon: 2
            });
        }
        return false;
    }

    var payStatus = $("#payStatus_export").val();
    var meterId = $("#meterId_export").val();
    var idCode = $("#idCode_export").val();

    var form = $("#downLoadJfjl");
    form.submit();
    /*$.ajax({
        url: "tbJfjl/exportJfjl", //这个就是请求地址对应sAjaxSource
        data: {
            "payStatus": payStatus,
            "meterId": meterId,
            "idCode":idCode,
            "startTime":startTime,
            "endTime": endTime
        },
        type: 'POST',
        async: false,
        success: function(result) {
            if (result.state == 1) {
                layer.msg(result.message, {
                    icon: 1
                });
            }else if(result.state == 0){
                layer.msg('导出缴费记录出错！', {
                    icon: 2
                });
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg('导出缴费记录出错！', {
                icon: 2
            });
        }
    });*/
}