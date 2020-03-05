$(function(){
    $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
    $(window).resize(function(){
        $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
    })
    $('.forgetpwd').click(function () {
        let userType = $("input[name='role']:checked").val();
        let userAccount = $(".loginuser").val().trim();
        if( $('.forgetpwd').text().indexOf("忘记")!=-1){
            if(userAccount==""||userType==""){
                $.MsgBox.Alert("提示","未输入待重置账号或未选择角色！！！")
            }else {
                $.MsgBox.Alert("提示","请确认您重置的账号为："+userAccount+"\n，取消请关闭！！！",function () {
                    let param = {
                        'userType':userType,
                        'userAccount':userAccount,
                    };
                    $.ajax({
                        type: 'post',
                        url: '/thesis/forgetPassword',
                        data: $.param(param),
                        success:function (data) {
                            if(data.code == 1){
                                $.MsgBox.Alert("提示", data.msg);
                            }else{
                                $.MsgBox.Alert("提示", data.msg);
                            }
                        },
                        error:function () {
                            // 登录存在错误，弹出提示信息
                            $.MsgBox.Alert("错误", "登录出错！");
                        }
                    })
                });

            }
        }

    })
    $('.loginbtn').click(function () {
        let userType = $("input[name='role']:checked").val();
        let userAccount = $(".loginuser").val().trim();
        let userPassword = $(".loginpwd").val().trim();
        let loginCode=$(".logincode").val().trim();
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