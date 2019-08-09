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
    initform();
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
    var lang = navigator.language||navigator.userLanguage;
    lang = lang.substr(0, 2);
    if(lang == 'zh'){
        permissionList();
    }else{
        permissionListEs();
    }
});
function permissionList() {
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

            return " <div class='text-left'> <a class='btn btn-primary btn-mini' data-toggle='modal' href='#modal-addauthority' role='button' style='background-color:#00BB00' onclick='setauth(\"" + row.id + "\")'><i class='icon-wrench'></i> 权限设置</a></div>";
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


};
//西班牙语
function permissionListEs() {
    /*定义列id和名称*/
    var aoColumns = [{
        mDataProp: "id",
        sTitle: "ID"
    }, {
        mDataProp: "name",
        sTitle: "Tipo de permiso"
    }, {
        mDataProp: "id",
        sTitle: "Operar"
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

            return " <div class='text-left'> <a class='btn btn-primary btn-mini' data-toggle='modal' href='#modal-addauthority' role='button' style='background-color:#00BB00' onclick='setauth(\"" + row.id + "\")'><i class='icon-wrench'></i> Tipo de autorización</a></div>";
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


};
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

    document.getElementById("autId").value = sday;

});

/*删除确认按钮onclick*/
/*$('#deleterow').click(function () {

    var id = $("#autId").val();

    var params = {
        id: id
    }
    var url = "sysRoles/deleteAuth";
    $.post(url,params,function (result) {
        if(result.state == 1){
            $('#modal-deleteauthority').modal('hide');
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
        }else if(result.state == 0){
            $('#modal-deleteauthority').modal('hide');
            layer.msg(result.message, {
                icon: 2
            });
        }
    });

});*/

/*添加、修改保存按钮onclick*/
function addauthority() {
    var id = $("#autId").val();
    var autName = $("#add_autName").val();
    var add_autNote = $("#add_autNote").val();
//获取选中节点的信息
    var menuIds=[];
    var checkedNodes=
        zTree.getCheckedNodes(true);
    for(var i in checkedNodes){
        menuIds.push(checkedNodes[i].id);
    }
    var params={
        id: id,
        name: autName,
        note: add_autNote,
        menuIds: menuIds.toString()
    }
    var url = "";

    if(id>0){
        url = "sysRoles/updateObject"
    }else{
        url = "sysRoles/addAuthority";
    }

    $.post(url, params, function (result) {
        if (result.state == 1) {
            $('#modal-addauthority').modal('hide');
            initform();
            oTable.fnDraw(false);//重新加载当前页
            layer.msg(result.message, {
                icon: 1
            });
        }else if (result.state == 0) {
            layer.msg(result.message, {
                icon: 2
            });
        }
    })


}


function initform() {
    $("#autId").val("");
    $("#add_autName").val("");
    $("#add_autNote").val("");
    $("#menuTree").html("");
}

/*function updateauthority() {
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


}*/

function setauth(roleId) {
    initform();
    doLoadSysMenus();

    var url = "sysRoles/findOne";
    var params={
        id:roleId
    }

    $.post(url, params, function (result) {
        if (result.state == 1) {

            $("#add_autName").val(result.data.name);
            $("#add_autNote").val(result.data.note);
            getrolemenus(roleId);
        }
        ;
        if (result.state == 0) {
            layer.msg(result.message, {
                icon: 2
            });
        }
    })


}

function getrolemenus(roleId) {

    var url = "sysRoles/getrolemenus";
    var params={
        id:roleId
    }

    $.post(url, params, function (result) {
        if (result.state == 1) {
            //展开ztree树
            zTree.expandAll(true);
            //2.2获取角色对应的菜单id
            var menuIds=result.data;
            //2.3迭代所有菜单id
            for(var i in menuIds){
                //基于菜单id获取ztree中的node节点
                var node=
                    zTree.getNodeByParam("id",menuIds[i]);
                //让节点选中
                zTree.checkNode(node,true,false);
            }
        }
        ;
        if (result.state == 0) {
            layer.msg(result.message, {
                icon: 2
            });
        }
    })

}

