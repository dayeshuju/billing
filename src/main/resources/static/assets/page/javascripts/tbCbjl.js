/*引用theme.js*/
$(document).ready(function () {
    var lang = navigator.language||navigator.userLanguage;
    lang = lang.substr(0, 2);
    if(lang == 'zh'){
        findCbjl();
    }else{
        findCbjlEs();
    }
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
            return " <div class='text-left'><a class='btn btn-success btn-mini' data-toggle='modal' href='#modal-historycbjl' role='button' style='background-color:#00BB00' onclick=saveCbjlMeterId(" + row.meterId + ")><i class='icon-pencil'></i>历史数据</a></div>";
        }
    }
    ];

    var new_filter_url = "tbCbjl/getCbjlList"; //表#plist数据获取url

    var oTable = setDataTable_ajax($("#plist"), new_filter_url, aoColumns, aoColumnDefs, true);

    oTable.columnFilter();
    var oSettings = oTable.fnSettings();
    oSettings.sAjaxSource = new_filter_url;
    oTable.fnDraw();
};

//西班牙语
function findCbjlEs() {
    /*定义列id和名称*/
    var aoColumns = [{
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
        sTitle: "Contador No "
    }, {
        mDataProp: "regisTime",
        sTitle: "Tiempo de lectura del contador"
    }, {
        mDataProp: "meterNum",
        sTitle: "Cifras en contador"
    }, {
        mDataProp: "id",
        sTitle: "Operar "
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
            return " <div class='text-left'><a class='btn btn-success btn-mini' data-toggle='modal' href='#modal-historycbjl' role='button' style='background-color:#00BB00' onclick=saveCbjlMeterId(" + row.meterId + ")><i class='icon-pencil'></i> Datos históricos </a></div>";
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

function saveCbjlMeterId(meterId) {
    $("#starttime").val("");
    $("#endtime").val("");
    $("#meterId").val(meterId);
    getHistoryCbjl();
}

function getHistoryCbjl() {
    var startTime = $("#starttime").val();
    var endTime = $("#endtime").val();
    $("#starttime").val(startTime);
    $("#endtime").val(endTime);
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
                                    </tr>
                                    </thead>
                                </table>`);
    $(".delDiv").remove();
    var meterId = $("#meterId").val();
    var lang = navigator.language||navigator.userLanguage;
    lang = lang.substr(0, 2);
    if(startTime == "" && endTime != ""){
        if("zh"==lang){
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
        if("zh"==lang){
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
        if("zh"==lang){
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
    if("zh"==lang){
        $("#historyPlist").dataTable({
            sDom: "<'row-fluid'<'span6'l><'span6 text-right'f>r>t<'row-fluid'<'span6'i><'span6 text-right'p>>",
            iDisplayLength: 12,
            sPaginationType: "bootstrap",
            sAjaxSource: "tbCbjl/getHistoryCbjlList",
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
                mDataProp: "regisTime",
                sTitle: "抄表时间"
            }, {
                mDataProp: "meterNum",
                sTitle: "表示数"
            }

            ],
            aoColumnDefs: [{
                "bSortable": false,
                "aTargets": [0, 1, 2, 3, 4, 5]
            }, {
                "sDefaultContent": '',
                "aTargets": ['_all']
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
    }else{
        $("#historyPlist").dataTable({
            sDom: "<'row-fluid'<'span6'l><'span6 text-right'f>r>t<'row-fluid'<'span6'i><'span6 text-right'p>>",
            iDisplayLength: 12,
            sPaginationType: "bootstrap",
            sAjaxSource: "tbCbjl/getHistoryCbjlList",
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
                sTitle: "Contador No "
            }, {
                mDataProp: "regisTime",
                sTitle: "Tiempo de lectura del contador"
            }, {
                mDataProp: "meterNum",
                sTitle: "Cifras en contador"
            }
            ],
            aoColumnDefs: [{
                "bSortable": false,
                "aTargets": [0, 1, 2, 3, 4, 5]
            }, {
                "sDefaultContent": '',
                "aTargets": ['_all']
            }/*, {
            "aTargets": [6],
            "mRender": function (data, type, row) {
                return " <div class='text-left'><a class='btn btn-danger btn-mini' data-toggle='modal' href='#modal-deletecbjl' role='button' onclick=saveCbjlId(" + data + ")><i class='icon-remove'></i>删除</a></div>";
            }
        }*/
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

function saveCbjlId(id){
    alert(id);
    $("#cbjlId").val(id);
}

/*删除确认按钮onclick*/
/*$('#deleterow').click(function () {
    var cbjlId = $("#cbjlId").val();
    alert(cbjlId);
    var params = {
        id: cbjlId
    }
    var url = "tbCbjl/deleteCbjl";

    $.post(url, params, function (result) {
        $('#modal-deletecbjl').modal('hide');
        if (result.state == 1) {
            getHistoryCbjl();
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

var refreshid="";

function doUploadFile(){
    clearInterval(refreshid);
    $("#uploadId").html("");
    createUploadFileItem();
    refreshid = setInterval(upload,500);
}

function upload(){
    if($("#file").val() == ""){
        return;
    }
    clearInterval(refreshid);
    var files=$("#file")[0].files;
    var fileName = files[0].name;
    var index = fileName.lastIndexOf(".");
    var suffix = fileName.substr(index+1);
    if(suffix != "csv"){
        var lang = navigator.language||navigator.userLanguage;
        lang = lang.substr(0, 2);
        if("zh"==lang){
            layer.msg("您上传了错误的文件", {
                icon: 2
            });
        }else{
            layer.msg("Ha subido archivo incorrecto", {
                icon: 2
            });
        }
        return;
    }
    $("#ff").ajaxSubmit({
        type: "POST",
        url: "tbCbjl/doUpload",
        success: function(result){
            if(result.state == 1){
                layer.msg(result.message,{
                    icon: 1
                });
                oTable.fnDraw();
            }else if(result.state == 0){
                layer.msg(result.message,{
                    icon: 2
                })
            }
        }
    });
}

/**创建文件上传dom元素*/
function createUploadFileItem(){
    var inputObj=$("<input></input>");
    inputObj.attr('id','file');
    inputObj.attr('type','file');
    inputObj.attr('name','file');
    inputObj.attr("multiple","multiple");
    var formObj=$("<form id='ff' enctype='multipart/form-data' method='post'></form>")
    formObj.append(inputObj);
    formObj.attr("style",'visibility:hidden');
    $("#uploadId").append(formObj);
    inputObj.value;
    inputObj.click();
}
