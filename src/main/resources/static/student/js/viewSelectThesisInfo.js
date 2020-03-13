var thesisNo="";
$(function () {
    let flag=$("#selectType").val();
    loadTable(flag);
    $("#selectType").change(function () {
    loadTable($("#selectType").val());
    })
})
function loadTable(flag) {
    if(flag=="1") {
        // 加载表格数据
        $("#data").empty();
        loadData();
        $("#dataTable").show();

    }else {
        $("#dataTable").hide();
    }
    if(flag=="2"){
        $("#openReportData").empty();
        loadOpenReportData();
        $("#openReportTable").show();
    }else {
        $("#openReportTable").hide();
    }
    if(flag=="3"){
        $("#reviewData").empty();
        loadReviewData();
        $("#reviewTable").show();
    }else {
        $("#reviewTable").hide();
    }
    if(flag=="4"){
        $("#inspectionData").empty();
        loadInspectionData();
        $("#inspectionTable").show();
    }else {
        $("#inspectionTable").hide();
    }
    if(flag=="5"){
        $("#noReplyData").empty();
        loadNoReplyData();
        $("#noReplyTable").show();
    }else {
        $("#noReplyTable").hide();
    }
    if(flag=="6"){
        $("#replyData").empty();
        loadReplyData();
        $("#replyTable").show();
    }else {
        $("#replyTable").hide();
    }
}
function loadData() {

        $.ajax({
            type: "get",
            url: "/thesis/studentTeacherRelation/getStudentTeacherRelationByStudentNo",
            async: false,
            success: function (data) {
                for (let i = 0; i < data.data.length; i++) {
                    let gridData = (data.data)[i];
                    let status=gridData.opinionFlagStr;
                    if(status=="同意"){
                        status="<td style=\"color:limegreen;\">已同意</td>";
                    }else {
                        status="<td style=\"color:red;\">"+status+"</td>";
                    }
                    $("#data").append(`
                    <tr>
                        <td>${i + 1}</td>
                        <td>${gridData.studentNo}</td>
                        <td>${gridData.studentName}</td>
                        <td>${gridData.studentClass}</td>
                        <td>${gridData.teacherNo}</td>
                        <td>${gridData.teacherName}</td>
                        <td>${gridData.thesisTitle}</td>
                        ${status}
                        <td>${gridData.teacherOpinion}</td>
                    </tr>
                `)
                }
            },
            error: function () {
                $.MsgBox.Alert("错误", "查询信息失败！");
            }
        })

}
function loadOpenReportData() {
    $.ajax({
        type: "get",
        url: "/thesis/replyScore/getOpenReportScore",
        async: false,
        success: function (data) {
            if(data.msg!=null){
                $.MsgBox.Alert("提示",data.msg);
                return;
            }
            if(data.thesisNo==""||data.thesisNo==null){
                $.MsgBox.Alert("提示","并无相关信息!");
                return;
            }
                let gridData = data;
                let status="";
                let score="";
               if(gridData.openReportScore==0){
                    score="待评分";
                }
                else {
                    score=gridData.openReportScore;
                }
                if(gridData.openReportStatus==0){
                    status="<td style=\"color: red\">待审核</td>";
                }
                if(gridData.openReportStatus==1){
                    status="<td style=\"color:limegreen;\">已通过</td>";
                }
                if(gridData.openReportStatus==2){
                    status="<td style=\"color: red\">未通过</td>";
                }
            if(gridData.openReportSummary==""||gridData.openReportWay=="") {
                score = "待提交";
                status="<td style=\"color: red\">待提交</td>";
            }
                $("#openReportData").append(`
                    <tr>
                        <td>${gridData.studentNo}</td>
                        <td>${gridData.studentName}</td>
                        <td>${gridData.studentClass}</td>
                        <td>${gridData.teacherNo}</td>
                        <td>${gridData.teacherName}</td>
                        <td>${gridData.thesisTitle}</td>
                        <td>${gridData.openReportUrl}</td>
                        ${status}
                        <td>${score}</td>
                        <td>${gridData.openReportOpinion}</td>
                    </tr>
                `)
        },
        error: function () {
            $.MsgBox.Alert("错误", "查询信息失败！");
        }
    })
}
function loadReviewData() {
    $.ajax({
        type: "get",
        url: "/thesis/replyScore/getOpenReportScore",
        async: false,
        success: function (data) {
            if(data.msg!=null){
                $.MsgBox.Alert("提示",data.msg);
                return;
            }
            if(data.thesisNo==""||data.thesisNo==null){
                $.MsgBox.Alert("提示","并无相关信息!");
                return;
            }
            let gridData = data;
            let status="";
            if(gridData.reviewStatus==0){
                status="<td style=\"color: red\">待审核</td>";
            }
            if(gridData.reviewStatus==1){
                status="<td style=\"color:limegreen;\">已通过</td>";
            }
            if(gridData.reviewStatus==2){
                status="<td style=\"color: red\">未通过</td>";
            }
            if(gridData.reviewContent==""){
                status="<td style=\"color: red\">待提交</td>";
            }
            $("#reviewData").append(`
                    <tr>
                        <td>${gridData.studentNo}</td>
                        <td>${gridData.studentName}</td>
                        <td>${gridData.studentClass}</td>
                        <td>${gridData.teacherNo}</td>
                        <td>${gridData.teacherName}</td>
                        <td>${gridData.thesisTitle}</td>
                        <td>${gridData.reviewUrl}</td>
                         ${status}
                        <td>${gridData.reviewOpinion}</td>
                    </tr>
                `)
        },
        error: function () {
            $.MsgBox.Alert("错误", "查询信息失败！");
        }
    })
}
function loadInspectionData() {
    $.ajax({
        type: "get",
        url: "/thesis/replyScore/getOpenReportScore",
        async: false,
        success: function (data) {
            if(data.msg!=null){
                $.MsgBox.Alert("提示",data.msg);
                return;
            }
            if(data.thesisNo==""||data.thesisNo==null){
                $.MsgBox.Alert("提示","并无相关信息!");
                return;
            }
            let gridData = data;
            let status="";
            if(gridData.inspectionStatus==0){
                status="<td style=\"color: red\">待审核</td>";
            }
            if(gridData.inspectionStatus==1){
                status="<td style=\"color:limegreen;\">已通过</td>";
            }
            if(gridData.inspectionStatus==2){
                status="<td style=\"color: red\">未通过</td>";
            }
            if(gridData.inspectionPass==""){
                status="<td style=\"color: red\">待提交</td>";
            }
            $("#inspectionData").append(`
                    <tr>
                        <td>${gridData.studentNo}</td>
                        <td>${gridData.studentName}</td>
                        <td>${gridData.studentClass}</td>
                        <td>${gridData.teacherNo}</td>
                        <td>${gridData.teacherName}</td>
                        <td>${gridData.thesisTitle}</td>
                        ${status}
                        <td>${gridData.inspectionOpinion}</td>
                    </tr>
                `)
        },
        error: function () {
            $.MsgBox.Alert("错误", "查询信息失败！");
        }
    })
}
function loadNoReplyData() {
    $.ajax({
        type: "get",
        url: "/thesis/replyScore/getOpenReportScore",
        async: false,
        success: function (data) {
            if(data.msg!=null){
                $.MsgBox.Alert("提示",data.msg);
                return;
            }
            if(data.thesisNo==""||data.thesisNo==null){
                $.MsgBox.Alert("提示","并无相关信息!");
                return;
            }
            let gridData = data;
            let status=""
            if(gridData.status==0){
                status="<td style=\"color: red\">待审核</td>";
            }
            if(gridData.status==1){
                status="<td style=\"color:limegreen;\">已通过</td>";
            }
            if(gridData.status==2){
                status="<td style=\"color: red\">未通过</td>";
            }
            if(gridData.reason==""){
                status="<td style=\"color: red\">未提交申请</td>";
            }
            $("#noReplyData").append(`
                    <tr>
                        <td>${gridData.studentNo}</td>
                        <td>${gridData.studentName}</td>
                        <td>${gridData.studentClass}</td>
                        <td>${gridData.teacherNo}</td>
                        <td>${gridData.teacherName}</td>
                        <td>${gridData.thesisTitle}</td>
                        ${status}
                        <td>${gridData.opinion}</td>
                    </tr>
                `)
        },
        error: function () {
            $.MsgBox.Alert("错误", "查询信息失败！");
        }
    })
}
function loadReplyData() {
    $.ajax({
        type: "get",
        url: "/thesis/replyScore/getOpenReportScore",
        async: false,
        success: function (data) {
            if(data.msg!=null){
                $.MsgBox.Alert("提示",data.msg);
                return;
            }
            if(data.thesisNo==""||data.thesisNo==null){
                $.MsgBox.Alert("提示","并无相关信息!");
                return;
            }
            let gridData = data;
            let status="";
            let score=gridData.thesisScoreList;
            if(gridData.replyStatus==0){
                status="<td style=\"color: red\">未答辩</td>";
                score="未答辩"
            }
            if(gridData.replyStatus==1){
                status="<td style=\"color:limegreen;\">已通过</td>";
            }
            if(gridData.replyStatus==2){
                status="<td style=\"color: red\">未通过</td>";
            }
            let date=gridData.replyDate;
            if(gridData.replyDate==null){
                date="暂无安排！";
            }else {
                date=gridData.replyDate.split(" ")[0];
            }
            $("#replyData").append(`
                    <tr>
                        <td>${gridData.studentNo}</td>
                        <td>${gridData.studentName}</td>
                        <td>${gridData.studentClass}</td>
                        <td>${gridData.teacherNo}</td>
                        <td>${gridData.teacherName}</td>
                        <td>${gridData.thesisTitle}</td>
                        ${status} 
                        <td>${gridData.replyPlace}</td>
                        <td>${date}</td>
                        <td>${gridData.groupName}</td>
                          <td>${gridData.grouperName}</td>
                         <td>${score}</td>
                        <td>${gridData.replyOpinion}</td>
                    </tr>
                `)
        },
        error: function () {
            $.MsgBox.Alert("错误", "查询信息失败！");
        }
    })
}