$(function(){
	
	/*记住用户名和密码*/
	eachRemember();
	$("#js_remember a").click(function(e) {
		$(this).toggleClass("current");
		eachRemember();
		return false;
	});
	function eachRemember(){
		$("#js_remember a").each(function(index, element) {
			$(this).closest("p").find("input").eq($(this).index()).val($(this).hasClass("current")?1:0);
		});
	}
	
	/*提交表单*/
	$('#js_btn_login').click(function(e) {
		if ($("#js_user_name").val() == "") {
			formErro("用户名不能为空！",$("#js_user_name"));
			return false;
		} else if ($("#js_user_pwd").val() == "") {
			formErro("密码不能为空!",$("#js_user_pwd"));
			return false;
		}
		$("#js_formbox_form").submit();
		return false;
	});
	function formErro(txt,obj){
		$("#js_form_error").text(txt);
		if(obj!=undefined){
			obj.focus();
		}
		console.info(obj);
		console.info(txt);
	}
	
	/*回车提交*/
	$("#js_user_name").keydown(function(e) {
        if (e.which == 13) {
            $("#js_user_pwd").focus();
        }
	});
	$("#js_user_pwd").keydown(function(e) {
        if (e.which == 13) {
            $('#js_btn_login').click();
        }
    });
	
	
	//ajax服务端错误
	//formErro("服务端错误，请稍等！",$("#js_user_name"));
	
});
