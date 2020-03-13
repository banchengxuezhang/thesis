$(function () {
    var url = location.search; //获取url中"?"符后的字串 ('?modFlag=business&role=1')
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1); //substr()方法返回从参数值开始到结束的字符串；
        var strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = (strs[i].split("=")[1]);
        }
    }
    userIds = theRequest.userIds;
    $("#returnGroupBtn").click(function () {
        location.href="manageReplyGroup.html";
    })
    $("#addGroupBtn").click(function () {
        let grouperName=$("#grouperName").val();
        let grouperNo=$("#grouperNo").val();
        let groupName=$("#groupName").val();
        let groupStatus=$("#groupStatus").val();
        let replyDate=$("#replyDate").val();
        let replyPlace=$("#replyPlace").val();
        if(groupName==""||groupName==null||grouperNo==null||grouperNo==""||replyDate==null||replyDate==""||replyPlace==""||replyPlace==null){
            $.MsgBox.Alert("提示","必填项不能为空！除组长名字外均为必填！！！");
            return;
        }
        let data={
            userIds:userIds,
            grouperName:grouperName,
            groupStatus:groupStatus,
            grouperNo:grouperNo,
            groupName:groupName,
            replyPlace:replyPlace,
            replyDate:new Date(replyDate)
        }
        $.ajax({
            type: "post",
            url:"/thesis/group/addGroup?"+$.param(data),
            success:function (data) {
                $.MsgBox.Alert("提示",data.msg,function () {
                   if(data.code!=1){
                       if(data.msg.indexOf("很抱歉，您选了其它分组的教师!!!")!=-1){
                           location.href="manageReplyGroup.html";
                       }
                       return;
                   }
                    location.href="manageReplyGroup.html";
                });

            },
            error:function () {
                $.MsgBox.Alert("提示", "添加分组异常！！！", function () {
                    location.href="manageReplyGroup.html";
                });
            }
        })
    })
})
