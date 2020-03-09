$(function(){
    $("#loginbtn").click(function () {
        let userType = $("input[name='role']:checked").val();
        let userAccount = $("#userName").val().trim();
        let userPassword = $("#passWord").val().trim();
        let loginCode=$("#loginCode").val().trim();
        //校验验证码是否输入
        if(loginCode ==""){
            $.MsgBox.Alert("提示", "请输入验证码！");
            return;
        }
        // 校验账户和密码是否输入
       else if (userAccount == "" || userPassword == ""||userType=="") {
            $.MsgBox.Alert("提示", "请输入用户名、密码，并选择角色！");
            return;
        }
        let param = {
            'userType':userType,
            'userAccount':userAccount,
            'userPassword':userPassword,
            'loginCode':loginCode
        };
        $.ajax({
            type: 'post',
            url: '/thesis/login',
            data: $.param(param),
            success:function (data) {
                if(data.code == 1){
                    window.location.href = "main.html";
                }else{
                      if((data.msg).indexOf("密码错误")!=-1){
                           $('.forgetpwd').text("忘记密码？点击重置！");
                      }
                        $.MsgBox.Alert("提示", data.msg);
                }
            },
            error:function () {
                // 登录存在错误，弹出提示信息
                $.MsgBox.Alert("错误", "登录出错！");
            }
        })
    })
});