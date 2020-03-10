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
    var thesisNo = theRequest.thesisNo;
    loadInitData(thesisNo);
    $("#noReplyCheckBtn").click(function () {
        let  status=$("#status").val();
        let  opinion=$("#opinion").val();
        let params={
            thesisNo:thesisNo,
            status:status,
            opinion:opinion
        }
        $.ajax({
            type: "post",
            url:"/thesis/openReport/teacherCheckNoReply?"+$.param(params),
            success:function (data) {
                $.MsgBox.Alert("提示",data.msg,function () {
                    window.location.href="./teacherCheckListForNoReply.html";
                });
            },
            error:function () {
                $.MsgBox.Alert("错误","提交免答辩申请审核失败！");
            }
        })


    })
    $("#returnBtn").click(function () {
        location.href = "./teacherCheckListForNoReply.html";
    });
})
function loadInitData(thesisNo) {
    let param = {
        thesisNo: thesisNo
    }
    $.ajax({
        type: "get",
        url: "/thesis/openReport/getThesisOpenReportAndOtherByThesisNo?" + $.param(param),
        success: function (data) {
            let gridData=data;
            if(gridData.reviewContent==""){
                $.MsgBox.Alert("提示","该生未提交免答辩申请！无法查看！",function () {
                    location.href = "./teacherCheckListForNoReply.html";
                });
                return;
            }
            $("#studentMajor").text(gridData.studentMajor);
            $("#studentClass").text(gridData.studentClass);
            $("#thesisTitle").text(gridData.thesisTitle);
            $("#studentNo").text(gridData.studentNo);
            $("#studentName").text(gridData.studentName);
            $("#teacherName").text(gridData.teacherName);
            if(gridData.signName!=""){
                $("#signName").val(gridData.signName);
            }
            if(gridData.mobilePhone!=""){
                $("#mobilePhone").val(gridData.mobilePhone);
            }
            if(gridData.reason!=""){
                $("#reason").text(gridData.reason);
            }
            if(gridData.status!=0){
                $("#status").val(gridData.status);
            }
            if(gridData.opinion!=""){
                $("#opinion").text(gridData.opinion);
            }

        },
        error: function () {
            $.MsgBox.Alert("错误", "初始化免答辩申请数据失败！");
        }
    })
}