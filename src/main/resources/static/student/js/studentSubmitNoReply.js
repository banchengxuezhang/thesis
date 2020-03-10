var thesisNo="";
var studentName="";
var status=0;
$(function () {
    loadDataGrid();
    $("#noReplyBtn").click(function () {
        if(status!=0){
            $.MsgBox.Alert("提示","很抱歉，由于您的教师已经审核！您无法再修改！");
            return;
        }
        let signName=$("#signName").val();
        let mobilePhone=$("#mobilePhone").val();
        let reason=$("#reason").val();
        if(signName==null||signName==""||mobilePhone==null||mobilePhone==""||reason==null||reason==""){
            $.MsgBox.Alert("错误","签名、手机号、理由都不能为空！！！");
            return;
        }
        if(signName!=studentName){
            $.MsgBox.Alert("错误","签名和姓名不一致！！！");
            return;
        }
        if(!isPhone(mobilePhone)){
            $.MsgBox.Alert("错误","手机号码格式不对！");
            return;
        }
        let formData = new FormData();
        formData.append("thesisNo",thesisNo);
        formData.append("signName",signName);
        formData.append("mobilePhone",mobilePhone);
        formData.append("reason",reason);
        $.ajax({
            type: 'post',
            url:"/thesis/openReport/uploadNoReply",
            data:formData,
            cache: false,
            processData: false,
            contentType: false,
            success:function (data) {
                $.MsgBox.Alert("提示",data.msg);
                loadDataGrid();
            },
            error:function () {
                $.MsgBox.Alert("错误","提交免答辩申请失败！！！");
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
            if(gridData.signName!=""){
                $("#signName").val(gridData.signName);
            }
            if(gridData.mobilePhone!=""){
                $("#mobilePhone").val(gridData.mobilePhone);
            }
            if(gridData.reason!=""){
                $("#reason").text(gridData.reason);
            }
            studentName=gridData.studentName;
            thesisNo=gridData.thesisNo;
            status=gridData.status;
        },
        error: function () {
            $.MsgBox.Alert("错误", "查询课题数据失败！");
        }
    })
}
function isPhone(phone) {
    return /^1(3\d|4\d|5\d|6\d|7\d|8\d|9\d)\d{8}$/g.test(phone);
}