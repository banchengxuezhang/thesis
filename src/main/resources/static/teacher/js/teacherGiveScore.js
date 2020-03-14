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

    // 进入页面初始化数据
    loadInitData(thesisNo);

    $("#replyScoreBtn").click(function () {
     replyScore(thesisNo);
    })
    $("#returnBtn").click(function () {
        location.href = "teacherCheckListForReplyScore.html";
    })
})

function loadInitData(thesisNo) {
    let param = {
        thesisNo: thesisNo
    }
    $.ajax({
        type: "get",
        url: "/thesis/replyScore/getReplyScore?" + $.param(param),
        success: function (data) {
            if(data.thesisScoreList!=""&&data.thesisScoreList!=null){
                $("#score1").val(data.thesisScoreList.split(" ")[0]);
                $("#score2").val(data.thesisScoreList.split(" ")[1]);
                $("#score3").val(data.thesisScoreList.split(" ")[2]);
                $("#score4").val(data.thesisScoreList.split(" ")[3]);
            }
            if(data.replyOpinion!=""&&data.replyOpinion!=null){
                $("#replyOpinion").val(data.replyOpinion);
            }
        },
        error: function () {
            $.MsgBox.Alert("错误", "初始化分数信息失败！");
        }
    })
}

function replyScore(thesisNo) {
    let thesisScoreList = $("#score1").val()+" "+$("#score2").val()+" "+$("#score3").val()+" "+$("#score4").val()+" ";
    let replyOpinion = $("#replyOpinion").val();
    if ($("#score1").val() == null || $("#score1").val() == ""||$("#score2").val() == null || $("#score2").val() == ""||$("#score3").val() == null || $("#score3").val() == ""||$("#score4").val() == null || $("#score4").val() == "") {
        $.MsgBox.Alert("提示", "分数为必填！");
        return;
    }
    let data = {
        thesisNo:thesisNo,
       thesisScoreList:thesisScoreList,
        replyOpinion:replyOpinion
    }
    $.MsgBox.Confirm("提示", "确定提交分数信息？", function () {
        $.ajax({
            type: "post",
            url: "/thesis/replyScore/updateScoreList?" + $.param(data),
            success: function (data) {
                $.MsgBox.Alert("提示", data.msg, function () {
                    location.href = "teacherCheckListForReplyScore.html";
                });
            },
            error: function () {
                $.MsgBox.Alert("错误", "修改论文信息异常！");
                return;
            }
        })
    })
}