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
   var groupId = theRequest.groupId;

    // 进入页面初始化数据
    loadInitData(groupId);
    $("#returnGroupBtn").click(function () {
        location.href="manageReplyGroup.html?flag="+"2";
    })
    $("#modifyGroupBtn").click(function () {
        let groupName=$("#groupName").val();
        let grouperNo=$("#grouperNo").val();
        let groupStatus=$("#groupStatus").val();
        let replyDate=$("#replyDate").val();
        let replyPlace=$("#replyPlace").val();
        if(grouperNo==null||grouperNo==""||replyDate==null||replyDate==""||replyPlace==""||replyPlace==null){
            $.MsgBox.Alert("提示","组长编号、答辩时间、答辩地点不能为空！！！");
            return;
        }
        let data={
            groupId:groupId,
            groupName:groupName,
            groupStatus:groupStatus,
            grouperNo:grouperNo,
            replyPlace:replyPlace,
            replyDate:new Date(replyDate)
        }
        $.ajax({
            type: "post",
            url:"/thesis/group/modifyGroup?"+$.param(data),
            success:function (data) {
                $.MsgBox.Alert("提示",data.msg,function () {
                    if(data.code!=1){
                        return;
                    }
                    location.href="manageReplyGroup.html?flag="+"2";
                });

            },
            error:function () {
                $.MsgBox.Alert("提示", "修改分组异常！！！", function () {
                    location.href="manageReplyGroup.html?flag="+"2";
                });
            }
        })
    })
    function  loadInitData(groupId) {
        $.ajax({
            type:"get",
            url:"/thesis/group/getGroupById?groupId="+groupId,
            success:function (data) {
                $("#groupName").val(data.groupName);
                $("#grouperNo").val(data.grouperNo);
                $("#groupStatus").val(data.groupStatus);
               if(data.replyPlace!=""&&data.replyPlace!=null){
                   $("#replyPlace").val(data.replyPlace);
               }
               if(data.replyDate!=null){
                   $("#replyDate").val(data.replyDate.split(" ")[0]);
               }
            },
            error:function () {
                $.MsgBox.Alert("提示","初始化数据失败！");
            }
        })

    }
})
