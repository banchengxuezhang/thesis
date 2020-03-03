$(function () {
    //顶部导航切换
    $(".nav li a").click(function () {
        $(".nav li a.selected").removeClass("selected")
        $(this).addClass("selected");
    })

    // 获取登录session值
    $.ajax({
        type: 'get',
        url: "/thesis/getLoginUserInfo",
        success: function (data) {
            if (data.userType == 3) {
                $("#loginUser").html("学生，" + data.userAccount);
            }
            if (data.userType == 2) {
                $("#loginUser").html("教师，" + data.userAccount);
            }
            if (data.userType == 1) {
                $("#loginUser").html("管理员，" + data.userAccount);

            }
        }
    })
})