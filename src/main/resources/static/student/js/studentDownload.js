var thesisNo="";
$(function () {
    loadDataGrid();
    $("#taskDownBtn").click(function () {
        window.location.href="/thesis/file/downloadTask?thesisNo="+thesisNo;
        return false;
    })
    $("#thesisDownBtn").click(function () {
        window.location.href="/thesis/file/downloadThesis?thesisNo="+thesisNo;
        return false;
    })
    $("#openReportDownBtn").click(function () {
        window.location.href="/thesis/file/downloadOpenReport?thesisNo="+thesisNo;
        return false;
    })
    $("#ReviewDownBtn").click(function () {
        window.location.href="/thesis/file/downloadReview?thesisNo="+thesisNo;
        return false;
    })
    $("#inspectionDownBtn").click(function () {
       window.location.href="/thesis/file/downloadFile?fileName=中期检查表-模板.docx&path=tableTemplate";
       return false;
    })
    $("#noReplyDownBtn").click(function () {
        window.location.href="/thesis/file/downloadFile?fileName=免答辩申请表-模板.docx&path=tableTemplate";
        return false;
    })
})
function  loadDataGrid(){
    $.ajax({
        type: 'get',
        url: "/thesis/openReport/getThesisForOpenReportAndReview",
        success: function (data) {
            thesisNo=data.thesisNo;
            if(data.taskUrl==""||data.taskUrl==null){
                $("#list1").hide();
            }
            if(data.thesisUrl==""||data.thesisUrl==null){
                $("#list4").hide();
            }
            if(data.openReportUrl==""||data.openReportUrl==null){
                $("#list2").hide();
            }
            if(data.reviewUrl==""||data.reviewUrl==null){
                $("#list3").hide();
            }

        },
        error: function () {
            $.MsgBox.Alert("错误", "查询课题数据失败！");
        }
    })
}