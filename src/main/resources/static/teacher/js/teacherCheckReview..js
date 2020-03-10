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
    $("#reviewUrl").click(function () {
        window.location.href="/thesis/file/downloadReview?thesisNo="+thesisNo;
        return false;
    })
    $("#reviewCheckBtn").click(function () {
      let  reviewStatus=$("#reviewStatus").val();
       let reviewOpinion=$("#reviewOpinion").val();
        let params={
            thesisNo:thesisNo,
           reviewStatus:reviewStatus,
           reviewOpinion:reviewOpinion
        }
        $.ajax({
            type: "post",
            url:"/thesis/openReport/teacherCheckReview?"+$.param(params),
            success:function (data) {
                $.MsgBox.Alert("提示",data.msg,function () {
                    window.location.href="./teacherCheckListForReview.html";
                });
            },
            error:function () {
                $.MsgBox.Alert("错误","提交文献综述审核失败！");
            }
        })


    })
    $("#returnBtn").click(function () {
        location.href = "./teacherCheckListForReview.html";
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
                $.MsgBox.Alert("提示","该生未提交文献综述！无法查看！",function () {
                    location.href = "./teacherCheckListForReview.html";
                });
                return;
            }
            $("#thesisTitle").text("论文题目为："+gridData.thesisTitle);
            if(gridData.reviewContent!=""){
                $("#reviewContent").text(gridData.reviewContent);
            }
            if(gridData.reviewUrl!=""){
                $("#reviewUrl").text("附件："+gridData.reviewUrl);
            }
            if(gridData.reviewOpinion!=""){
                $("#reviewOpinion").text(gridData.reviewOpinion);
            }
            if(gridData.reviewStatus!=0){
                $("#reviewStatus").val(gridData.reviewStatus);
            }
        },
        error: function () {
            $.MsgBox.Alert("错误", "初始化文献综述数据失败！");
        }
    })
}