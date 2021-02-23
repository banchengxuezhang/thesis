$(function () {
    $.ajax({
        type: 'get',
        url: "/thesis/getLoginUserInfo",
        success: function (data) {
            if (data.userType == 3) {
                $("#loginUser").text("学生，" + data.userAccount);
            }
            if (data.userType == 2) {
                $("#loginUser").text("教师，" + data.userAccount);
            }
            if (data.userType == 1) {
                $("#loginUser").text("管理员，" + data.userAccount);

            }
        }
    })
    $("#loginOut").click(function () {
        $.ajax({
            type: 'post',
            url:"/thesis/loginOut",
            success:function (data) {
            }
        })

    })
    $("#loginOut1").click(function () {
        $.ajax({
            type: 'post',
            url:"/thesis/loginOut",
            success:function (data) {
            }
        })

    })

    $.ajax({
        type: 'get',
        url: "/thesis/menu/getMenuByMenuBelong",
        success:function (data) {
            if(data.code == 1){
                for (let i = 0; i <data.data.length ; i++) {
                    let gridData = data.data[i];
                    if(gridData.menuText=="个人信息" || gridData.menuText=="修改密码"){
                        $("#personInfoList").append("<a href=\""+gridData.menuUrl+"\" class=\"link_a\" target=\"iframe_a\"> <i\n" +
                            "class=\"am-icon-angle-right\"></i> <span>"+gridData.menuText+"</span></a>");
                    }
                    if(gridData.menuText=="选定学生"||gridData.menuText=="发布课题"||gridData.menuText=="选题情况"||gridData.menuText=="学生选题"||gridData.menuText=="下载任务书"||gridData.menuText=="下达任务"){
                        $("#thesisList").append("<a href=\""+gridData.menuUrl+"\" class=\"link_a\" target=\"iframe_a\"> <i\n" +
                            "class=\"am-icon-angle-right\"></i> <span>"+gridData.menuText+"</span></a>");
                    }
                    if(gridData.menuText=="提交免答辩申请"||gridData.menuText=="提交文献综述"||gridData.menuText=="提交开题报告"||gridData.menuText=="查看免答辩申请"||gridData.menuText=="查看文献综述"||gridData.menuText=="查看开题报告"||gridData.menuText=="查看论文"||gridData.menuText=="答辩详情"||gridData.menuText=="答辩分组"||gridData.menuText=="系统验收"||gridData.menuText=="提交论文"||gridData.menuText=="提交中期检查"||gridData.menuText=="查看中期检查"||gridData.menuText=="阶段控制"||gridData.menuText=="权限控制"
                        ||gridData.menuText=="发布公告"){
                        $("#flowList").append("<a href=\""+gridData.menuUrl+"\" class=\"link_a\" target=\"iframe_a\"> <i\n" +
                            "class=\"am-icon-angle-right\"></i> <span>"+gridData.menuText+"</span></a>");
                    }
                    if(gridData.menuText=="用户管理"||gridData.menuText=="设置数据"||gridData.menuText=="数据分析"){
                        $("#systemManager").css('display','block');
                        $("#managerList").append("<a href=\""+gridData.menuUrl+"\" class=\"link_a\" target=\"iframe_a\"> <i\n" +
                            "class=\"am-icon-angle-right\"></i> <span>"+gridData.menuText+"</span></a>");
                    }
                    if(gridData.menuText=="查看消息"){
                        $("#list").append(`<li class="tpl-left-nav-item"><a href="${gridData.menuUrl}" class="nav-link tpl-left-nav-link-list link_a" target="iframe_a"> <i class="am-icon-th-list"></i> <span>${gridData.menuText}</span></a></li>`);
                    }
                    if(gridData.menuText=="学生材料下载"){
                        $("#list").append(`<li class="tpl-left-nav-item"><a href="${gridData.menuUrl}" class="nav-link tpl-left-nav-link-list link_a" target="iframe_a"> <i class="am-icon-download"></i> <span>${gridData.menuText}</span></a></li>`);
                    }
                }
            }
        },
        error:function () {
            // 登录存在错误，弹出提示信息
           $.MsgBox.Alert("错误", "登录出错！");
        }
    })

})
