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
})
function  loadDataGrid(){
    $.ajax({
        type: 'get',
        url: "/thesis/openReport/getThesisForOpenReportAndReview",
        success: function (data) {
            thesisNo=data.thesisNo;

        },
        error: function () {
            $.MsgBox.Alert("错误", "查询课题数据失败！");
        }
    })
}