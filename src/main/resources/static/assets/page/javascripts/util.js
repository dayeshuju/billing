//获取checkbox的值, 例如<input type="checkbox" value="综合布线" name="buildingsystem"> 综合布线
function getcheckboxvalue(id) {

    var obj = document.getElementsByName("" + id + "");
    var check_val = [];
    for (var k in obj) {
        if (obj[k].checked)
            check_val.push(obj[k].value);
    }
    //alert(check_val)
    var ret = check_val.join(",");
    return ret;

}


function setTheCheckBoxValue(checkboxvaluestr, checkboxname) {
    //alert(checkboxvaluestr);
    //alert(checkboxname);
    var checkboxs = document.getElementsByName(checkboxname); //获取复选框
    var checkboxvalues = checkboxvaluestr.split(",");
    for (var i = 0; i < checkboxvalues.length; i++) { //循环值

        for (var j = 0; j < checkboxs.length; j++) { //循环复选框

            if (checkboxvalues[i] == checkboxs[j].value) //判断是否等于复选框的值
                checkboxs[j].checked = true; //选中复选框

        }

    }

}

function setRadioValue(radiovaluestr, radioname) {

    var radios = document.getElementsByName(radioname); //获取radio


    for (var j = 0; j < radios.length; j++) { //循环radio

        if (radiovaluestr == radios[j].value) //判断是否等于单选框的值
            radios[j].checked = true; //选中单选框

    }

}


function getRadioValue(radioname) {

    var objs = document.getElementsByName("" + radioname + "");
    var ret = 0;
    for (var i = 0; i < objs.length; i++) {
        if (objs[i].checked == true) {
            ret = objs[i].value;
            break;
        }
    }
    return ret;

}

function gettime(expectfinishtime, timex) {



    var oDate1 = new Date(expectfinishtime);
    var oDate2 = new Date(timex);
    if (oDate1.getTime() < oDate2.getTime()) {
        expectfinishtime = timex;
    }
    return expectfinishtime;
}



//非空校验
function saveverify(checkinput, alertinput) {
    var inputv = $("#" + checkinput).val();

    if (inputv.replace(/^ +| +$/g, '') == '') {
        document.getElementById(alertinput).style.display = "";
        return 1;

    } else {
        document.getElementById(alertinput).style.display = "none";
        return 0;
    }

}

$(".box .box-collapse").click(function(e) {
    var box;

    box = $(this).parents(".box").first();
    box.toggleClass("box-collapsed");
    return e.preventDefault();
});


function getProjectApplicationNameList(status) {

    var url = "/ProjectApplicationController/getProjectApplicationNameList?status=" + status;
    //alert(url);
    url = encodeURI(url);

    $.ajax({
        url: url,
        success: function(responseText) {
            var plist = responseText.info;
            //alert(JSON.stringify(plist));

            var projectname = document.getElementById("project_name");
            projectname.innerHTML = "<option></option>";
            for (var i = 0; i < plist.length; i++) {

                projectname.innerHTML += "<option value='" + plist[i].id + "'>" + plist[i].project_name + "</option >"


            }

        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {

        }

    });


}




function getcurrenttime() {
    var date = new Date();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + "-" + month + "-" + strDate;
    return currentdate;

}

//强制输入数字校验
function digitcheck(obj) { // 值允许输入一个小数点和数字 
    obj.value = obj.value.replace(/[^\d.]/g, ""); //先把非数字的都替换掉，除了数字和. 
    obj.value = obj.value.replace(/^\./g, ""); //必须保证第一个为数字而不是. 
    obj.value = obj.value.replace(/\.{2,}/g, "."); //保证只有出现一个.而没有多个. 
    obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", "."); //保证.只出现一次，而不能出现两次以上 

}

//js截取字符串，并加省略号
function showText(text, numSub) {

    if (text == null) {
        return "";
    }
    if (text.length > numSub) {
        return text.substring(0, numSub - 1) + "...";
    }
    return text;
}



//完成时限及完成状态联动（添加）
function setFinishstate() {
 
    var finishdate = document.getElementById("finishdate").value.trim();
 

    var flag = 0;
    
    if ((finishdate.trim().replace(/^ +| +$/g, '') == '') || (finishdate == null)) {

        flag = 0;
    } else {
 

        flag = 1;
    }

    var lang = navigator.language||navigator.userLanguage;
    lang = lang.substr(0, 2);
    if("zh"==lang){
        if (flag == 1) {
            document.getElementById("finishstate").value = "正在推进";
        } else {
            document.getElementById("finishstate").value = "未开始";
        }
    }else{
        if (flag == 1) {
            document.getElementById("finishstate").value = "Avanzando";
        } else {
            document.getElementById("finishstate").value = "No comenzado";
        }
    }
}



function setfinishdate() {
 
    var inputv = $("#finishstate").val();
    var lang = navigator.language||navigator.userLanguage;
    lang = lang.substr(0, 2);
    if("zh"==lang){
        if (inputv == '未开始') {
            document.getElementById("finishdate").value = "";
        }
    }else{
        if (inputv == 'No comenzado') {
            document.getElementById("finishdate").value = "";
        }
    }

}



/*
 未开始、正在推进：亮绿灯；
距离完成时限不足20%时间 至  完成时限，亮黄灯。完成时限可能修改，要动态计算是否亮灯；
已超过完成时限，但状态为 未开始、正在推进，亮红灯告警；
未完成：表示到期还没有完成，亮红灯；此时虽然超期但仍需要推进，超期完成后管理员会置为“逾期完成”状态；
提前完成、按期完成、逾期完成、暂停督查：表示事项已完结，亮灰灯。

 * */
function setlight(conferencedate, finishdate, finishstate) { //红 1   黄2   绿3  灰4
    var ret = 0;
    var time = new Date(finishdate).getTime() - new Date(conferencedate).getTime(); //时间差的毫秒数     

    var now = new Date(); //结束时间


    var time1 = new Date(finishdate).getTime() - now.getTime(); //时间差的毫秒数    

    var inputv = $("#finishstate").val();
    var lang = navigator.language||navigator.userLanguage;
    lang = lang.substr(0, 2);
    if("zh"==lang){
        if ((finishstate == '未开始') || (finishstate == '正在推进')) {

            ret = 3;

            if ((time1 / time * 100 <= 20) && (finishstate == '正在推进')) {


                ret = 2;

            }

            if (time1 < 0) {

                ret = 1;

            }

        } else if (finishstate == '未完成') {

            ret = 1;

        } else { //逾期完成   按期完成  提前完成  暂停督查

            ret = 4;


        }
    }else{
        if ((finishstate == 'No comenzado') || (finishstate == 'Avanzando')) {

            ret = 3;

            if ((time1 / time * 100 <= 20) && (finishstate == 'Avanzando')) {


                ret = 2;

            }

            if (time1 < 0) {

                ret = 1;

            }

        } else if (finishstate == 'No acabado') {

            ret = 1;

        } else { //逾期完成   按期完成  提前完成  暂停督查

            ret = 4;


        }
    }

    return ret;


}

//解决弹出层页面关闭下拉框跑到页面左上角 
$('.modal-add').on('hide.bs.modal', function() {
    
 
    $(".select2-drop").css("display","none");

 
})
