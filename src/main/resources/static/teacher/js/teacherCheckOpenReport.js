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
    $("#openReportCheckBtn").click(function () {
     let openReportScore=$("#openReportScore").val();
     let openReportOpinion=$("#openReportOpinion").val();
     if(!(/(^[1-9]\d*$)/.test(openReportScore))){
         $.MsgBox.Alert("错误","请输入合法分数！");
         return;
     }
     if(openReportScore>100||openReportScore<=0){
         $.MsgBox.Alert("错误","请输入合法分数！");
         return;
     }

     let params={
         thesisNo:thesisNo,
         openReportScore:openReportScore,
         openReportOpinion:openReportOpinion
     }
        $.ajax({
            type: "post",
            url:"/thesis/openReport/teacherCheckOpenReport?"+$.param(params),
            success:function (data) {
                $.MsgBox.Alert("提示",data.msg,function () {
                    window.location.href="./teacherCheckListForOpenReport.html";
                });
            },
            error:function () {
                $.MsgBox.Alert("错误","提交开题报告分数失败！");
            }
        })
    })
    $("#openReportUrl").click(function () {
        window.location.href="/thesis/file/downloadOpenReport?thesisNo="+thesisNo;
        return false;
    })
    $("#returnBtn").click(function () {
        location.href = "./teacherCheckListForOpenReport.html";
    });
    function loadInitData(thesisNo) {
        let param = {
            thesisNo: thesisNo
        }
        $.ajax({
            type: "get",
            url: "/thesis/openReport/getThesisOpenReportAndOtherByThesisNo?" + $.param(param),
            success: function (data) {
                let gridData=data;
                $("#studentName").text(gridData.studentName);
                $("#studentNo").text(gridData.studentNo);
                $("#studentMajor").text(gridData.studentMajor);
                $("#studentClass").text(gridData.studentClass);
                $("#thesisTitle").text(gridData.thesisTitle);
                $("#openReportSummary").text(gridData.openReportSummary);
                $("#openReportWay").text(gridData.openReportWay);
                if(gridData.openReportUrl!=""){
                    $("#openReportUrl").text("附件："+gridData.openReportUrl);
                }
                if(gridData.openReportScore!=0)
                $("#openReportScore").val(gridData.openReportScore);
                if(gridData.openReportOpinion!="")
                $("#openReportOpinion").text(gridData.openReportOpinion);
            },
            error: function () {
                $.MsgBox.Alert("错误", "初始化开题报告数据失败！");
            }
        })
    }
})