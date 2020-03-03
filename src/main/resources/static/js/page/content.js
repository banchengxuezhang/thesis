$(function () {
    $.ajax({
        type: 'get',
        url: '/thesis/notice/getNoticeInfo',
        success:function (data) {
            if(data.code == 1){
               $('#nowStage').text(data.data.nowStage);
               if(data.data.userType=="3"){
                   $('#text1').append("剩余课题数<a style=\"color:white;font-weight:bold;font-size: 30px;padding-left: 20px;padding-right: 30px\">" + data.data.text1 + "</a>");
                   $('#text2').append("当前状态<a style=\"color:white;font-weight:bold;font-size: 30px;padding-left: 20px;padding-right: 30px\">"+data.data.text2+"</a>");
               }
               if(data.data.userType=="2"){
                   $('#text1').append("被选课题数<a style=\"color:white;font-weight:bold;font-size: 30px;padding-left: 20px;padding-right: 30px\">" + data.data.text1 + "</a>");
                   $('#text2').append("待确认人数<a style=\"color:white;font-weight:bold;font-size: 30px;padding-left: 20px;padding-right: 30px\">"+data.data.text2+"</a>");
               }
               if(data.data.userType=="1"){
                   $('#text1').append("未选题学生数<a style=\"color:white;font-weight:bold;font-size: 30px;padding-left: 20px;padding-right: 30px\">" + data.data.text1 + "</a>");
                   $('#text2').append("未带学生教师数<a style=\"color:white;font-weight:bold;font-size: 30px;padding-left: 20px;padding-right: 30px\">"+data.data.text2+"</a>");
               }
            }
        },
        error:function () {
            // 登录存在错误，弹出提示信息
            $.MsgBox.Alert("错误", "登录出错！");
        }
    })

})