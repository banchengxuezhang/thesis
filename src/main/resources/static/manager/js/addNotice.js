$(function () {
    $("#addNoticeBtn").click(function () {
        let noticeBelong=$("#noticeBelong").val();
        let noticeStatus=$("#noticeStatus").val();
        let noticeTitle=$("#noticeTitle").val();
        let file=$("#file")[0].files[0];
        if(noticeTitle==null||noticeTitle==""||file==null){
            $.MsgBox.Alert("提示","标题和附件为必填项,不能为空！");
            return;
        }
        let formData=new FormData();
        formData.append("file",file);
        formData.append("noticeBelong",noticeBelong);
        formData.append("noticeStatus",noticeStatus);
        formData.append("noticeTitle",noticeTitle);
        $.ajax({
            type:"post",
            url:"/thesis/notice/publishNotice",
            data:formData,
            cache: false,
            processData: false,
            contentType: false,
            success:function (data) {
               $.MsgBox.Alert("提示",data.msg);
            },
            error:function () {
                $.MsgBox.Alert("提示","添加公告异常！！！");
            }

        })
    })
    $("#returnNoticeBtn").click(function () {
        location.href = "manageNotice.html";
    })
})
