
function checkUserRepeat(url){
	$("form").attr("action",url);
	$("form").submit();
}
function checkCode(url){
	$("form").attr("action",url);
	$("form").submit();
}
function checkUsername(thisobj){
	var username = $(thisobj).val();
	var reg = new RegExp(/^[-a-z_A-Z0-9\u4e00-\u9fa5]{3,20}$/);
	if(username.length()>2 && username.length()<21){
		if (!reg.test(username)){
			$("#err_msg1").html("只支持中文、字母、数字、-、_的组合");
			$("#err_msg1").css("background","url('imgs/alert_2.jpg') 17px 7px no-repeat");
			$("#err_msg1").css("color","red");
		}else{
			$("#err_msg1").html("支持中文、字母、数字、-、_的组合，3-20个字母");
			$("#err_msg1").css("background","url('imgs/alert_1.jpg') 17px 7px no-repeat");
			$("#err_msg1").css("color","#c3c3c3");
		}
	}else if(username.length<3 && username.length>0){
		$("#err_msg1").html("请不要少于3个字符");
		$("#err_msg1").css("background","url('imgs/alert_2.jpg') 17px 7px no-repeat");
		$("#err_msg1").css("color","red");
	}else if(username.length>20){
		$("#err_msg1").html("请不要超过20个字符");
		$("#err_msg1").css("background","url('imgs/alert_2.jpg') 17px 7px no-repeat");
		$("#err_msg1").css("color","red");
	}else if(username.length == 0){
		$("#err_msg1").html("支持中文、字母、数字、-、_的组合，3-20个字母");
		$("#err_msg1").css("background","url('imgs/alert_1.jpg') 17px 7px no-repeat");
		$("#err_msg1").css("color","#c3c3c3");
	}
	return reg.test(username);
}
function checkPassword(thisobj) {
	var password = $(thisobj).attr("value");
	var reg = new RegExp(/^(?![^a-zA-Z]+$)(?!\D+$)/);
	var flag = false;
	if(password.length>5 && password.length<21){
		if (!reg.test(password)){
			$("#err_msg2").html("请使用数字、字母和符号两种以上的组合");
			$("#err_msg2").css("background","url('imgs/alert_2.jpg') 17px 7px no-repeat");
			$("#err_msg2").css("color","red");
		}else{
			flag = true;
			$("#err_msg2").html("使用数字、字母和符号两种以上的组合，6-20个字符");
			$("#err_msg2").css("background","url('imgs/alert_1.jpg') 17px 7px no-repeat");
			$("#err_msg2").css("color","#c3c3c3");
		}
	}else if(password.length<6 && password.length>0){
		$("#err_msg2").html("请不要少于6个字符");
		$("#err_msg2").css("background","url('imgs/alert_2.jpg') 17px 7px no-repeat");
		$("#err_msg2").css("color","red");
	}else if(password.length>20){
		$("#err_msg2").html("请不要超过20个字符");
		$("#err_msg2").css("background","url('imgs/alert_2.jpg') 17px 7px no-repeat");
		("#err_msg2").css("color","red");
	}else if(password.length == 0){
		$("#err_msg2").html("使用数字、字母和符号两种以上的组合，6-20个字符");
		$("#err_msg2").css("background","url('imgs/alert_1.jpg') 17px 7px no-repeat");
		$("#err_msg2").css("color","#c3c3c3");
	}
	return flag;
}
function reCheckPassword(thisobj){
	var password = $("input[name='password']").attr("value");
	var repassword = $(thisobj).attr("value");
	var flag = false;
	if(password !== repassword){
		$("#err_msg3").css("visibility","visible");
	}else{
		$("#err_msg3").css("visibility","hidden");
		flag = true;
	}
	return flag;
}
function checkEmail(thisobj){
	var email = $(thisobj).attr("value");
	var reg = new RegExp(/^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/);
	var flag = false;
	if(!reg.test(email)){
		$("#err_msg4").css("visibility","visible");
		if(email.length == 0){
			$("#err_msg4").css("visibility","hidden");
		}
	}else{
		$("#err_msg4").css("visibility","hidden");
		flag = true;
	}
	return flag;
}
function sendcode(url){
	var email = $("input[name='email']")[0];
	if($(email).attr("value").length == 0){
		$("#err_msg4").html("请输入邮箱")
		$("#err_msg4").css("visibility","visible");
	}else if(checkEmail(email)){
		$("form").attr("action",url);
		$("form").submit();
	}
}
function submit1( url ) {
	var codeFlag = $("input[name='code']").attr("data-flag");
	var username = $("input[name='username']")[0];
	var password = $("input[name='password']")[0];
	var repassword = $("input[name='repassword']")[0];
	var email = $("input[name='email']")[0];
	if(codeFlag=="true" && checkUsername(username) && checkPassword(password) && reCheckPassword(repassword) && checkEmail(email)){
		$("form").attr("action",url);
		$("form").submit();
	}
	
}