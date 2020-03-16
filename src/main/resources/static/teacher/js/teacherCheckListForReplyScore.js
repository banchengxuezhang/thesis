var page = 1;
const rows = 5;
var totalPage;
var studentNoList=new Array();
$(function () {
    loadDataGrid();

    $("#operateBtn").click(function () {
        let checkedObj = $("input[name='thesis']:checked");
        if (checkedObj.length <= 0) {
            $.MsgBox.Alert("提示", "请选择一条数据进行操作！");
            return;
        }
        if (checkedObj.length > 1) {
            $.MsgBox.Alert("提示", "只能选择一条数据进行操作！");
            return;
        }
        let flag=$(checkedObj[0]).attr("flag");
        //判断是否为日期
        if(flag.length<10) {
            $.MsgBox.Alert("提示", "答辩尚未安排，您无法进行该操作！！！");
            return;
        }
        if(compareDate(getNowFormatDate(),flag.split(" ")[0])){
            $.MsgBox.Alert("提示","答辩时间尚未开始，您无法进行该操作！！！");
            return;
        }
        let checkStatus=$(checkedObj[0]).attr("checkStatus");
        if(checkStatus==1){
            $.MsgBox.Alert("提示","该生已经被系统验收，您无法进行该操作！！！");
            return;
        }
        let thesisNo = $(checkedObj[0]).val();
        location.href = "./teacherGiveScore.html?thesisNo=" + thesisNo;
    });
    $("#remindBtn").click(function () {
          $.ajax({
              type:"post",
              url:"/thesis/remindStudents?studentNoList="+studentNoList,
              success:function (data) {
               $.MsgBox.Alert("提示",data.msg);
               return;
              },
              error:function () {

              }
          })
    })

    $("#firstPage").click(function () {
        if(totalPage==0){
            return;
        }
        if (page == 1) {
            $.MsgBox.Alert("提示", "当前已经是第一页！");
        } else {
            page = 1;
            // 清除之前表格中的数据
            $("#data").empty();
            loadDataGrid();
        }
    });

    $("#prePage").click(function () {
        if(totalPage==0){
            return;
        }
        if (page == 1) {
            $.MsgBox.Alert("提示", "无上一页！");
        } else {
            page -= 1;
            // 清除之前表格中的数据
            $("#data").empty();
            loadDataGrid();
        }
    });

    $("#nextPage").click(function () {
        if(totalPage==0){
            return;
        }
        if (page == totalPage) {
            $.MsgBox.Alert("提示", "无下一页！");
        } else {
            page += 1;
            // 清除之前表格中的数据
            $("#data").empty();
            loadDataGrid();
        }
    });

    $("#lastPage").click(function () {
        if(totalPage==0){
            return;
        }
        if (page == totalPage) {
            $.MsgBox.Alert("提示", "当前已经是最后一页！");
        } else {
            page = totalPage;
            // 清除之前表格中的数据
            $("#data").empty();
            loadDataGrid();
        }
    });
})

function loadDataGrid() {
    let pageInfo = {
        page: page,
        rows: rows
    }
    $.ajax({
        type: "get",
        url: "/thesis/openReport/getThesisForOpenReportAndReviewList?" + $.param(pageInfo),
        success: function (data) {
            totalPage = Math.ceil(data.total / rows);
            $("#totalData").html(data.total);
            $("#currentPage").html(page + "/" + totalPage);
            for (let i = 0; i < data.rows.length; i++) {
                let gridData = (data.rows)[i];
                studentNoList.push(gridData.studentNo);
                let status="";
                let score="";
                if(gridData.thesisScoreList!=""&&gridData.thesisScoreList!=null){
                    score=`  <td>${gridData.thesisScoreList.split(" ")[0]}</td>
                        <td>${gridData.thesisScoreList.split(" ")[1]}</td>
                        <td>${gridData.thesisScoreList.split(" ")[2]}</td>
                        <td style="color: red">${gridData.thesisScoreList.split(" ")[3]}</td>
`;
                }else {
                    score=`<td></td><td></td><td></td><td></td>
                    
                    `
                }
                if(gridData.replyStatus==0){
                    status="<td>未答辩</td>";
                }
                if(gridData.replyStatus==1){
                    status="<td style=\"color:limegreen;\">已通过</td>";
                }
                if(gridData.replyStatus==2){
                    status="<td style=\"color:red;\">未通过</td>";
                }
                let date=gridData.replyDate;
                if(gridData.replyDate==null){
                    date="暂无安排！";
                }else {
                    date=gridData.replyDate.split(" ")[0];
                }
                $("#data").append(`
                    <tr>
                        <td><input name="thesis"  checkStatus="${gridData.checkStatus}" flag="${gridData.replyDate}" type="checkbox" value="${gridData.thesisNo}"/></td>
                        <td>${(page-1)*rows+i + 1}</td>
                        <td>${gridData.studentNo}</td>
                        <td>${gridData.studentName}</td>
                        <td>${gridData.studentMajor}</td>
                        <td>${gridData.studentClass}</td>
                        <td>${gridData.thesisTitle}</td>
                            ${status} 
                        <td>${date}</td>
                        <td>${gridData.replyPlace}</td>
                        <td>${gridData.groupName}</td>
                        <td>${gridData.grouperName}</td>
                        ${score}
                        <td>${gridData.replyOpinion}</td>
                    </tr>
                `)
            }
        },
        error: function () {
            $.MsgBox.Alert("错误", "加载信息错误！");
        }
    })
}
function compareDate(a, b) {
    var arr = a.split("-");
    var starttime = new Date(arr[0], arr[1], arr[2]);
    var starttimes = starttime.getTime();
    var arrs = b.split("-");
    var endTime = new Date(arrs[0], arrs[1], arrs[2]);
    var endTimes = endTime.getTime();
    // 进行日期比较
    if (endTimes > starttimes) {
        return true;
    }else{

        return false;
    }
}
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate;
    return currentdate;
}