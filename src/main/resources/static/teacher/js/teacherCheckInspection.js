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
    $("#inspectionCheckBtn").click(function () {
        let  inspectionStatus=$("#inspectionStatus").val();
        let inspectionOpinion=$("#inspectionOpinion").val();
        let params={
            thesisNo:thesisNo,
            inspectionStatus:inspectionStatus,
            inspectionOpinion:inspectionOpinion
        }
        $.ajax({
            type: "post",
            url:"/thesis/openReport/teacherCheckInspection?"+$.param(params),
            success:function (data) {
                $.MsgBox.Alert("提示",data.msg,function () {
                    window.location.href="./teacherCheckListForInspection.html";
                });
            },
            error:function () {
                $.MsgBox.Alert("错误","提交中期检查审核失败！");
            }
        })


    })
    $("#returnBtn").click(function () {
        location.href = "./teacherCheckListForInspection.html";
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
                $.MsgBox.Alert("提示","该生未提交中期检查！无法查看！",function () {
                    location.href = "./teacherCheckListForInspection.html";
                });
                return;
            }
            $("#studentMajor").text(gridData.studentMajor);
            $("#studentClass").text(gridData.studentClass);
            $("#thesisTitle").text(gridData.thesisTitle);
            $("#studentNo").text(gridData.studentNo);
            $("#studentName").text(gridData.studentName);
            $("#teacherName").text(gridData.teacherName);
            $("#teacherTitle").text(gridData.teacherTitle);
            if(gridData.inspectionPass!=""){
                $("#inspectionPass").text(gridData.inspectionPass);
            }
            if(gridData.inspectionNoPass!=""){
                $("#inspectionNoPass").text(gridData.inspectionNoPass);
            }
            if(gridData.inspectionStatus!=0){
                $("#inspectionStatus").val(gridData.inspectionStatus);
            }
            if(gridData.inspectionOpinion!=""){
                $("#inspectionOpinion").text(gridData.inspectionOpinion);
            }

        },
        error: function () {
            $.MsgBox.Alert("错误", "初始化中期检查数据失败！");
        }
    })
}