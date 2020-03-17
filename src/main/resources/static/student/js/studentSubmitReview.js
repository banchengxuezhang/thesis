var thesisNo="";
var reviewStatus=0;
var checkStatus;
$(function () {
    loadDataGrid();
    $("#reviewUrl").click(function () {
        window.location.href="/thesis/file/downloadReview?thesisNo="+thesisNo;
        return false;
    })
    $("#reviewBtn").click(function () {
        if(checkStatus==1){
            $.MsgBox.Alert("提示","很抱歉，由于您的教师已经提交系统验收！您无法再修改！");
            return;
        }
        if(reviewStatus!=0){
            $.MsgBox.Alert("提示","很抱歉，由于您的教师已经审核！您无法再修改！");
            return;
        }
        let  reviewContent=$("#reviewContent").val();
        if(reviewContent==null||reviewContent==""){
            $.MsgBox.Alert("错误","文献综述不能为空！！！");
            return;
        }
        let formData = new FormData();
        formData.append("thesisNo",thesisNo);
        formData.append("reviewContent",reviewContent);
        if($("#file")[0].files[0]!=null){
            formData.append("file",$("#file")[0].files[0]);
        }
        $.ajax({
            type: 'post',
            url:"/thesis/openReport/uploadReview",
            data:formData,
            cache: false,
            processData: false,
            contentType: false,
            success:function (data) {
                $.MsgBox.Alert("提示",data.msg);
                loadDataGrid();
            },
            error:function () {
                $.MsgBox.Alert("错误","提交文献综述失败！！！");
            }

        })
    })
})
function  loadDataGrid(){
    $.ajax({
        type: 'get',
        url: "/thesis/openReport/getThesisForOpenReportAndReview",
        success: function (data) {
            if (data.code==2){
                $.MsgBox.Alert("提示","请先通过课题！！！",function () {
                    location.href="studentSelectThesis.html";
                });
            }
            let gridData=data;
           $("#thesisTitle").text("你的论文题目为："+gridData.thesisTitle);
           if(gridData.reviewContent!=""){
               $("#reviewContent").text(gridData.reviewContent);
           }
           if(gridData.reviewUrl!=""){
               $("#reviewUrl").text("附件："+gridData.reviewUrl);
           }
            thesisNo=gridData.thesisNo;
            reviewStatus=gridData.reviewStatus;
            checkStatus=gridData.checkStatus;
        },
        error: function () {
            $.MsgBox.Alert("错误", "查询课题数据失败！");
        }
    })
}