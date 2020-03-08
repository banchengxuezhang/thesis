var thesisNo="";
$(function () {
    loadDataGrid();
    $("#reviewBtn").click(function () {
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
            let gridData=data;
           $("#thesisTitle").text("你的论文题目为："+gridData.thesisTitle);
           if(gridData.reviewContent!=""){
               $("#reviewContent").text(gridData.reviewContent);
           }
            thesisNo=gridData.thesisNo;
        },
        error: function () {
            $.MsgBox.Alert("错误", "查询课题数据失败！");
        }
    })
}