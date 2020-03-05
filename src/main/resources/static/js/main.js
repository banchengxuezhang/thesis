$(function () {
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
                    if(gridData.menuText=="选定学生"||gridData.menuText=="发布课题"||gridData.menuText=="选题情况"||gridData.menuText=="查看消息"||gridData.menuText=="学生选题"){
                        $("#thesisList").append("<a href=\""+gridData.menuUrl+"\" class=\"link_a\" target=\"iframe_a\"> <i\n" +
                            "class=\"am-icon-angle-right\"></i> <span>"+gridData.menuText+"</span></a>");
                    }
                    if(gridData.menuText=="下达任务"||gridData.menuText=="查看论文"||gridData.menuText=="答辩详情"||gridData.menuText=="系统验收"||gridData.menuText=="下载任务书"||gridData.menuText=="提交论文"){
                        $("#flowList").append("<a href=\""+gridData.menuUrl+"\" class=\"link_a\" target=\"iframe_a\"> <i\n" +
                            "class=\"am-icon-angle-right\"></i> <span>"+gridData.menuText+"</span></a>");
                    }
                    if(gridData.menuText=="添加用户"||gridData.menuText=="设置数据"||gridData.menuText=="数据分析"||gridData.menuText=="控制进程"
                        ||gridData.menuText=="发布公告"){
                        $("#systemManager").css('display','block');
                        $("#managerList").append("<a href=\""+gridData.menuUrl+"\" class=\"link_a\" target=\"iframe_a\"> <i\n" +
                            "class=\"am-icon-angle-right\"></i> <span>"+gridData.menuText+"</span></a>");
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