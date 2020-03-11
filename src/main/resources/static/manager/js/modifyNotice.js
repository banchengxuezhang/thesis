var noticeId="";
var noticeUrl="";
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
   noticeId = theRequest.noticeId;

    // 进入页面初始化数据
    loadInitData(noticeId);
    $("#noticeUrl").click(function () {
        window.location.href="/thesis/file/downloadFile?fileName="+noticeUrl+"&path=notice/"+noticeId;
        return false;
    })
    $("#returnNoticeBtn").click(function () {
        location.href="manageNotice.html";
    })
    $("#modifyNoticeBtn").click(function () {
        let noticeBelong=$("#noticeBelong").val();
        let noticeStatus=$("#noticeStatus").val();
        let noticeTitle=$("#noticeTitle").val();
        let file=$("#file")[0].files[0];
        if(noticeTitle==null||noticeTitle==""){
            $.MsgBox.Alert("提示","标题为必填项,不能为空！");
            return;
        }
        let formData=new FormData();
        if(file!=null){
            formData.append("file",file);
        }
        formData.append("noticeId",noticeId);
        formData.append("noticeBelong",noticeBelong);
        formData.append("noticeStatus",noticeStatus);
        formData.append("noticeTitle",noticeTitle);
       $.ajax({
           type: "post",
           url:"/thesis/notice/updateNotice",
           data:formData,
           cache: false,
           processData: false,
           contentType: false,
           success:function (data) {
               $.MsgBox.Alert("提示",data.msg,function () {
                   location.href="manageNotice.html";
               });

           },
           error:function () {
               $.MsgBox.Alert("提示", "修改公告异常！！！", function () {
                   location.href = "manageNotice.html";
               });
           }
       })
    })
    function  loadInitData(noticeId) {
        $.ajax({
            type:"get",
            url:"/thesis/notice/getNoticeByNoticeId?noticeId="+noticeId,
            success:function (data) {
                $("#noticeBelong").val(data.noticeBelong);
                $("#noticeStatus").val(data.noticeStatus);
                $("#noticeUrl").text(data.noticeUrl);
                $("#noticeTitle").val(data.noticeTitle);
                noticeId=data.noticeId;
                noticeUrl=data.noticeUrl;
            },
            error:function () {
                $.MsgBox.Alert("提示","初始化数据失败！");
            }
        })

    }
})
