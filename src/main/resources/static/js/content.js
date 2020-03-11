$(function () {
    $.ajax({
        type: 'get',
        url: '/thesis/notice/getNoticeInfo',
        success: function (data) {
            if (data.code == 1) {
                let noticeList = data.data.noticeList;
                if (noticeList.length > 0) {
                    $("#noticeParent").show();
                }
                for (let i = 0; i < noticeList.length; i++) {
                    if (i == 0) {
                        $("#noticeList").append(`
                    <li>
<i class="am-icon-chevron-circle-right"></i> ${noticeList[i].noticeTitle}
<div style="float: right"><i class="am-icon-download"></i> <a  onclick="downloadNoticeFile('${noticeList[i].noticeId}','${noticeList[i].noticeUrl}')" style="font-weight: normal;color: black">下载附件</a></div>
</li>
                    
                    `);
                    } else {
                        $("#noticeList").append(`
                       <li style="padding-top: 10px">
<i class="am-icon-chevron-circle-right"></i> ${noticeList[i].noticeTitle}
<div style="float: right"><i class="am-icon-download"></i> <a  onclick="downloadNoticeFile('${noticeList[i].noticeId}','${noticeList[i].noticeUrl}')" style="font-weight: normal;color: black">下载附件</a></div>
</li>
                    
                    `);
                    }

                }
                for (let i = 0; i < data.data.noteList.length; i++) {
                    let gridData = (data.data.noteList)[i];
                    $('#userNotes').append("<li><i class=\"am-icon-pencil am-icon-exclamation-circle\"></i>\n" + gridData + "</li>")
                }
                $('#nowStage').text(data.data.nowStage);
                if (data.data.userType == "3") {
                    $('#text1').append("剩余课题数<a style=\"color:white;font-weight:bold;font-size: 30px;padding-left: 20px;padding-right: 30px\">" + data.data.text1 + "</a>");
                    $('#text2').append("当前状态<a style=\"color:white;font-weight:bold;font-size: 30px;padding-left: 20px;padding-right: 30px\">" + data.data.text2 + "</a>");
                }
                if (data.data.userType == "2") {
                    $('#text1').append("被选课题数<a style=\"color:white;font-weight:bold;font-size: 30px;padding-left: 20px;padding-right: 30px\">" + data.data.text1 + "</a>");
                    $('#text2').append("待确认人数<a style=\"color:white;font-weight:bold;font-size: 30px;padding-left: 20px;padding-right: 30px\">" + data.data.text2 + "</a>");
                }
                if (data.data.userType == "1") {
                    $('#text1').append("未选题学生数<a style=\"color:white;font-weight:bold;font-size: 30px;padding-left: 20px;padding-right: 30px\">" + data.data.text1 + "</a>");
                    $('#text2').append("未带学生教师数<a style=\"color:white;font-weight:bold;font-size: 30px;padding-left: 20px;padding-right: 30px\">" + data.data.text2 + "</a>");
                }
            }
        },
        error: function () {
            // 登录存在错误，弹出提示信息
            $.MsgBox.Alert("错误", "加载出错！");
        }
    })

})
function  downloadNoticeFile(noticeId,fileName){
    window.location.href="/thesis/file/downloadFile?fileName="+fileName+"&path=notice/"+noticeId;
    return false;
}