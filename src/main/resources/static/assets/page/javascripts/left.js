function getshouye() {
    getpersonalCenterleft();
    var titlestr = "";
    var url = "/Home/homepage";
    url = encodeURI(url);
    $.ajax({
        url: url,
        success: function(responseText) {
            $("#content").html(responseText);
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {}
    });
}

function setVisiable(suferfix) {
     if (document.getElementById(suferfix).value == "1") {
    document.getElementById(suferfix).style.display = "";
     } else {
        document.getElementById(suferfix).style.display = "none";
    }
}
//个人中心
function getpersonalCenterleft() {

    setVisiable("personalCenter1");
    setVisiable("personalCenter2");

    document.getElementById("projectCenter1").style.display = "none";
    document.getElementById("projectCenter2").style.display = "none";
    document.getElementById("projectCenter3").style.display = "none";


    document.getElementById("systemManagement1").style.display = "none";
    document.getElementById("systemManagement2").style.display = "none";
    document.getElementById("systemManagement3").style.display = "none";
}
//项目中心
function getprojectCenterleft() {
    setVisiable("projectCenter1");
    setVisiable("projectCenter2");
    setVisiable("projectCenter3");


    document.getElementById("personalCenter1").style.display = "none";
    document.getElementById("personalCenter2").style.display = "none";


    document.getElementById("systemManagement1").style.display = "none";
    document.getElementById("systemManagement2").style.display = "none";
    document.getElementById("systemManagement3").style.display = "none";
}



//系统管理
function getSystemManagementLeft() {

    document.getElementById("personalCenter1").style.display = "none";
    document.getElementById("personalCenter2").style.display = "none";


    document.getElementById("projectCenter1").style.display = "none";
    document.getElementById("projectCenter2").style.display = "none";
    document.getElementById("projectCenter3").style.display = "none";


    setVisiable("systemManagement1");
    setVisiable("systemManagement2");
    setVisiable("systemManagement3");
}


//个人中心
$("#personalCenter").click(function() {
    getpersonalCenterleft();
});


//项目中心
$("#projectCenter").click(function() {
    getprojectCenterleft();
});


//系统管理
$("#systemManagement").click(function() {
    getSystemManagementLeft();
});

//点击左侧列表获取相应页面
$("#main-nav .navigation > .nav > li > .nav > li > a").click(function() {
    getpagebyid($(this).attr('id'));
});

//设置top的bottom颜色
$(".navbar .nav>li").click(function() {
    $("a", this).parent().siblings().children().css("border-bottom", "0px solid  ");
    $("a", this).css("border-bottom", "3px solid #FF9900");
    $(".dropdown-menu > li > a").css("border-bottom", "0px solid ");
    $(".dropdown-toggle").css("border-bottom", "0px solid");
});

function getpagebyid(id) {
    var url = "/Home/" + id;
    url = encodeURI(url);
    $.ajax({
        url: url,
        success: function(responseText) {
            $("#content").html(responseText);
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {}
    });
}

function setAuthority(fstr, suferfix) {
    var flag = 0;
    var Sf = new String(fstr);
    for (var i = 0; i < Sf.length; i++) {
        var epfix = Number(i) + 1;
        var eid = suferfix + String(epfix);
         
        if (Sf[i] == "1") {
            flag = 1;
            document.getElementById(eid).style.display = "";
        } else {
            document.getElementById(eid).style.display = "none";
        }
    }
 
    if (flag == 0) {
        document.getElementById(suferfix).value = "0";
        document.getElementById(suferfix).style.display = "none";

       
    } else {
        document.getElementById(suferfix).value = "1";

        document.getElementById(suferfix).style.display = "";
    }

    return flag;
}


function getleft() {
    url = "/AuthorityController/getpersonalAuthority";
    url = encodeURI(url);
    $.ajax({
        url: url,
        async: false,
        success: function(responseText) {
            var jinfo = responseText.info;
            var str = JSON.stringify(jinfo);

            document.getElementById("personalCenter1").style.display = "none";
            document.getElementById("personalCenter2").style.display = "none";


            document.getElementById("projectCenter1").style.display = "none";
            document.getElementById("projectCenter2").style.display = "none";
            document.getElementById("projectCenter3").style.display = "none";
 
            document.getElementById("systemManagement1").style.display = "none";
            document.getElementById("systemManagement2").style.display = "none";
            document.getElementById("systemManagement3").style.display = "none";

            var aflag = 0;

            aflag += setAuthority(jinfo.aafunction, "personalCenter1");
            aflag += setAuthority(jinfo.abfunction, "personalCenter2");


            //top
            if (aflag == 0) {
                document.getElementById("personalCenter").style.display = "none";
            } else {
                document.getElementById("personalCenter").style.display = "";

            }


            var bflag = 0;
            bflag += setAuthority(jinfo.bafunction, "projectCenter1");

            bflag += setAuthority(jinfo.bbfunction, "projectCenter2");
            bflag += setAuthority(jinfo.bcfunction, "projectCenter3");


            if (bflag == 0) {
                document.getElementById("projectCenter").style.display = "none";
            } else {
                document.getElementById("projectCenter").style.display = "";

            }
            var fflag = 0;

            fflag += setAuthority(jinfo.fafunction, "systemManagement1");
            fflag += setAuthority(jinfo.fbfunction, "systemManagement2");
            fflag += setAuthority(jinfo.fcfunction, "systemManagement3");

            if (fflag == 0) {
                document.getElementById("systemManagement").style.display = "none";
            } else {
                document.getElementById("systemManagement").style.display = "";

            }
 
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {

        }

    });
}
