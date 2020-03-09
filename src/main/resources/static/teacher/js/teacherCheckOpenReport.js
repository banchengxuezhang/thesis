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
            url: "/thesis/openReport/getThesisOpenReportByThesisNo?" + $.param(param),
            success: function (data) {
                let gridData=data;
                if(gridData.openReportSummary==""||gridData.openReportWay==""){
                    $.MsgBox.Alert("提示","该生未提交开题报告！无法查看！",function () {
                        location.href = "./teacherCheckListForOpenReport.html";
                    });
                    return;
                }
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
            },
            error: function () {
                $.MsgBox.Alert("错误", "初始化开题报告数据失败！");
            }
        })
    }
})