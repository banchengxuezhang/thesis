var thesisNo="";
var inspectionStatus=0;
var checkStatus;
$(function () {
    loadDataGrid();
    $("#inspectionBtn").click(function () {
        if(inspectionStatus!=0){
            if(checkStatus==1){
                $.MsgBox.Alert("提示","很抱歉，由于您的教师已经提交系统验收！您无法再修改！");
                return;
            }
            $.MsgBox.Alert("提示","很抱歉，由于您的教师已经审核！您无法再修改！");
            return;
        }
        let inspectionPass=$("#inspectionPass").val();
        let inspectionNoPass=$("#inspectionNoPass").val();
        if(inspectionPass==null||inspectionPass==""||inspectionNoPass==null||inspectionNoPass==""){
            $.MsgBox.Alert("错误","完成及没完成内容不能为空！！！");
            return;
        }
        let formData = new FormData();
        formData.append("thesisNo",thesisNo);
        formData.append("inspectionPass",inspectionPass);
        formData.append("inspectionNoPass",inspectionNoPass);
        $.ajax({
            type: 'post',
            url:"/thesis/openReport/uploadInspection",
            data:formData,
            cache: false,
            processData: false,
            contentType: false,
            success:function (data) {
                $.MsgBox.Alert("提示",data.msg);
                loadDataGrid();
            },
            error:function () {
                $.MsgBox.Alert("错误","提中期检查失败！！！");
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
            thesisNo=gridData.thesisNo;
            inspectionStatus=gridData.inspectionStatus;
            checkStatus=gridData.checkStatus;
        },
        error: function () {
            $.MsgBox.Alert("错误", "查询课题数据失败！");
        }
    })
}