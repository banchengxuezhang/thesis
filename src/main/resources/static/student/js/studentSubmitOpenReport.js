var thesisNo="";
var openReportStatus=0;
$(function () {
   loadDataGrid();
    $("#openReportUrl").click(function () {
        window.location.href="/thesis/file/downloadOpenReport?thesisNo="+thesisNo;
        return false;
    })
   $("#openReportbtn").click(function () {
       if(openReportStatus!=0){
           $.MsgBox.Alert("提示","很抱歉，由于您的教师已经审核！您无法再修改！");
           return;
       }
       let openReportSummary=$("#openReportSummary").val();
       let  openReportWay=$("#openReportWay").val();
       if(openReportSummary==null||openReportSummary==""||openReportWay==null||openReportWay==""){
           $.MsgBox.Alert("错误","概述和方式不能为空！！！");
           return;
       }
       let formData = new FormData();
       formData.append("thesisNo",thesisNo);
       formData.append("openReportSummary",openReportSummary);
       formData.append("openReportWay",openReportWay);
       if($("#file")[0].files[0]!=null){
           formData.append("file",$("#file")[0].files[0]);
       }
       $.ajax({
           type: 'post',
           url:"/thesis/openReport/uploadOpenReport",
           data:formData,
           cache: false,
           processData: false,
           contentType: false,
           success:function (data) {
            $.MsgBox.Alert("提示",data.msg);
            loadDataGrid();
           },
           error:function () {
               $.MsgBox.Alert("错误","提交开题报告失败！！！");
           }

       })
   })
})
function  loadDataGrid(){
    $.ajax({
        type: 'get',
        url: "/thesis/openReport/getThesisForOpenReportAndReview",
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
            thesisNo=gridData.thesisNo;
            openReportStatus=gridData.openReportStatus;
        },
        error: function () {
            $.MsgBox.Alert("错误", "查询课题数据失败！");
        }
    })
}