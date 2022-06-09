$(function() {
	// 登录验证的controller url
	var loginUrl = '/o2o/local/logincheck';
	// 从地址栏的URL里获取usertype
	// usertype=1则为customer,其余为shopowner
	var usertype = getQueryString('usertype');
	// 登录次数，累积登录三次失败之后自动弹出验证码要求输入
	var loginCount = 0;

	$('#submit').click(function() {
		// 获取输入的帐号
		var userName = $('#username').val();
		// 获取输入的密码
		var password = $('#psw').val();
		// 获取验证码信息
		var verifyCodeActual = $('#j_captcha').val();
		// 是否需要验证码验证，默认为false,即不需要
		var needVerify = false;
		// 如果登录三次都失败
		if (loginCount >= 3) {
			// 那么就需要验证码校验了
			if (!verifyCodeActual) {
				$.toast('请输入验证码！');
				return;
			} else {
				needVerify = true;
			}
		}
		// 访问后台进行登录验证
		$.ajax({
			url : loginUrl,
			async : false,
			cache : false,
			type : "post",
			dataType : 'json',
			beforeSend : function(request){
				request.setRequestHeader("token",localStorage.getItem("token"));
			},
			data : {
				userName : userName,
				password : password,
				verifyCodeActual : verifyCodeActual,
				//是否需要做验证码校验
				needVerify : needVerify
			},
			success : function(data) {
				if (data.success) {
					$.toast('登录成功！');
					localStorage.setItem("token",data.token);
					if (usertype == 1) {
						// 若用户在前端展示系统页面则自动链接到前端展示系统首页

						window.location.href = '/o2o/frontend/index';
					} else {

						//若用户是在店家管理系统页面则自动链接到店铺列表页中
						// let url = '/o2o/shopadmin/shoplist';
						// var xhr = new XMLHttpRequest();
						// xhr.open("get", url, true); // get、post都可
						// xhr.responseType = "blob";  // 转换流
						// xhr.setRequestHeader("token",localStorage.getItem("token")); // token键值对
						// xhr.onload = function() {
						// 	if (this.status == 200) {
						// 		var blob = this.response;
						// 		var a = document.createElement("a")
						// 		var url = window.URL.createObjectURL(blob)
						// 		a.href = url
						// 		//a.download = ".xls"  // 文件名
						// 	}
						// 	a.click()
						// 	window.URL.revokeObjectURL(url)
						// }
					 // xhr.send();
						$.ajax({
							type:"GET",
							url:'/o2o/shopadmin/shoplist',
							beforeSend : function(request){
								request.setRequestHeader("token",localStorage.getItem("token"));
							},
							success : function(req){
								document.close();
								document.write(req);
								document.close();
								history.pushState(null, null, '/o2o/shopadmin/shoplist');
								//document.getElementById("list").click()
							}
						});
						//window.location.href = '/o2o/shopadmin/shoplist';
					}
				} else {
					$.toast('登录失败！' + data.errMsg);
					loginCount++;
					if (loginCount >= 3) {
						// 登录失败三次，需要做验证码校验
						$('#verifyPart').show();
					}
				}
			}
		});
	});
});
