/* 显示遮罩层 */
function showOverlay() {
    $("#overlay").height(pageHeight());
    $("#overlay").width(pageWidth());

    // fadeTo第一个参数为速度，第二个为透明度
    // 多重方式控制透明度，保证兼容性，但也带来修改麻烦的问题
    $("#overlay").fadeTo(200, 0.5);
}

/* 隐藏覆盖层 */
function hideOverlay() {
    $("#overlay").fadeOut(200);
}

/* 当前页面高度 */
function pageHeight() {
    return document.body.scrollHeight;
}

/* 当前页面宽度 */
function pageWidth() {
    return document.body.scrollWidth;
}


/* 定位到页面中心 */
function adjust(id) {
    var w = $(id).width();
    var h = $(id).height();
    
    var t = scrollY() + (windowHeight()/2) - (h/2);
    if(t < 0) t = 0;
    
    var l = scrollX() + (windowWidth()/2) - (w/2);
    if(l < 0) l = 0;
    
    $(id).css({left: l+'px', top: t+'px'});
}

//浏览器窗口的高度
function windowHeight() {
    var de = document.documentElement;

    return self.innerHeight || (de && de.clientHeight) || document.body.clientHeight;
}

//浏览器窗口的宽度
function windowWidth() {
    var de = document.documentElement;

    return self.innerWidth || (de && de.clientWidth) || document.body.clientWidth
}

/* 浏览器垂直滚动位置 */
function scrollY() {
    var de = document.documentElement;

    return self.pageYOffset || (de && de.scrollTop) || document.body.scrollTop;
}

/* 浏览器水平滚动位置 */
function scrollX() {
    var de = document.documentElement;

    return self.pageXOffset || (de && de.scrollLeft) || document.body.scrollLeft;
}

/*自动关闭提示窗口*/
//自动关闭提示框    
function Alert(str,flag) {    //flag:0-success;1-failed
    var msgw,msgh,bordercolor;    
    msgw=350;//提示窗口的宽度    
    msgh=240;//提示窗口的高度    
    titleheight=25 //提示窗口标题高度    
    bordercolor="#D6E9F1";//提示窗口的边框颜色    
    titlecolor="white";//提示窗口的标题颜色    
    var sWidth,sHeight;    
    //获取当前窗口尺寸    
    sWidth = document.body.scrollWidth;    
    sHeight = document.body.scrollHeight;    
//    //背景div    
    var bgObj=document.createElement("div");    
    bgObj.setAttribute('id','alertbgDiv');    
    bgObj.style.position="absolute";    
    bgObj.style.top="0";    
    bgObj.style.background="#000";    
    bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75";    
    bgObj.style.opacity="0.6";    
    bgObj.style.left="0";    
    bgObj.style.width = sWidth + "px";    
    bgObj.style.height = sHeight + "px";    
    bgObj.style.zIndex = "10000";    
    document.body.appendChild(bgObj);    
    //创建提示窗口的div    
    var msgObj = document.createElement("div")    
    msgObj.setAttribute("id","alertmsgDiv");    
    msgObj.setAttribute("align","center");    
    msgObj.style.background="white";    
    msgObj.style.border="12px solid " + bordercolor;    
    msgObj.style.position = "absolute";    
    msgObj.style.left = "50%";    
    msgObj.style.font="16px 微软雅黑";    
    //窗口距离左侧和顶端的距离     
    msgObj.style.marginLeft = "-225px";    
    //窗口被卷去的高+（屏幕可用工作区高/2）-150    
    msgObj.style.top = document.body.scrollTop+(window.screen.availHeight/2)-150 +"px";    
    msgObj.style.width = msgw + "px";    
    msgObj.style.height = msgh + "px";    
    msgObj.style.textAlign = "center";    
    msgObj.style.lineHeight ="25px";    
    msgObj.style.zIndex = "10001";    
    document.body.appendChild(msgObj);    
    //提示信息标题    
    var title=document.createElement("h4");    
    title.setAttribute("id","alertmsgTitle");    
    title.setAttribute("align","left");    
    title.style.margin="15";    
    title.style.padding="15px";      
    title.style.background = "#3B95C8";    
    title.style.filter="progid:DXImageTransform.Microsoft.Alpha(startX=20, startY=20, finishX=100, finishY=100,style=1,opacity=75,finishOpacity=100);";    
    title.style.opacity="0.75";    
    title.style.border="15px solid white";    
    title.style.height="25px";    
    title.style.font="16px 微软雅黑";    
    title.style.color="white";    
    title.innerHTML="提示信息";    
    document.getElementById("alertmsgDiv").appendChild(title);  
    //显示成功或失败的图标
    var tippic = document.createElement("img");
    tippic.setAttribute("id","successFlag");
    
    if(0==flag){
    	tippic.src = "/public/images/success.jpg";
    }  
    else{
    	tippic.src = "/public/images/fail.jpg"
    }
    
    tippic.setAttribute("width","120px");
    tippic.setAttribute("height","80px");
    document.getElementById("alertmsgDiv").appendChild(tippic);
    //提示信息    
    var txt = document.createElement("p");    
    txt.setAttribute("id","msgTxt");    
    txt.style.margin="16px 0";   
    txt.style.font="16px 微软雅黑"; 
    txt.innerHTML = str;    
    document.getElementById("alertmsgDiv").appendChild(txt);    
    //设置关闭时间    
    window.setTimeout("closewin()",2000);     
}   

function closewin() {    
    document.body.removeChild(document.getElementById("alertbgDiv"));    
    document.getElementById("alertmsgDiv").removeChild(document.getElementById("alertmsgTitle"));    
    document.body.removeChild(document.getElementById("alertmsgDiv"));    
}   
