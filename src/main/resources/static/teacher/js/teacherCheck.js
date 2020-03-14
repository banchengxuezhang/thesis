var page = 1;
const rows = 5;
var totalPage;
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
        let checkStatus=$(checkedObj[0]).attr("checkStatus");
        if(checkStatus==1){
            $.MsgBox.Alert("提示","该生已经被系统验收，您无法进行该操作！！！");
            return;
        }
        let thesisNo = $(checkedObj[0]).val();
        $.MsgBox.Confirm("提示","一旦验收，就无法再对学生成绩进行评定修改，是否确定验收?",function () {
          $.ajax({
               type:"post",
              url:"/thesis/replyScore/updateCheckStatus?thesisNo="+thesisNo,
              success:function (data) {
                $.MsgBox.Alert("提示",data.msg,function () {
                    location.href="teacherCheck.html";
                });

              },
              error:function () {
                  $.MsgBox.Alert("提示","验收异常！！!");
              }
          })
        })
    });

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
                let score="";
                if(gridData.thesisScoreList!=""&&gridData.thesisScoreList!=null){
                    if(gridData.thesisScoreList.split(" ")[3]<=75){
                        score=`  
                        <td style="color: red">${gridData.thesisScoreList.split(" ")[3]}</td>
`;
                    }else {
                        score=`  
                        <td style="color: limegreen">${gridData.thesisScoreList.split(" ")[3]}</td>
`;
                    }


                }else {
                    score=`<td></td>
                    `
                }
                let reviewStatus=`<td style="color: red">未通过</td>`;
                let inspectionStatus=`<td style="color: red">未通过</td>`;
                let status=`<td>未通过</td>`;
                let checkStatus=`<td style="color: red">未验收</td>`;
                let openReportScore="";
                if(gridData.reviewStatus==1){
                    reviewStatus="<td style=\"color:limegreen;\">通过</td>"
                }
                if(gridData.inspectionStatus==1){
                    inspectionStatus="<td style=\"color:limegreen;\">通过</td>"
                }
                if(gridData.status==1){
                    status="<td style=\"color:limegreen;\">通过</td>";
                }
                if(gridData.checkStatus==1){
                    checkStatus="<td style=\"color:limegreen;\">已验收</td>";
                }
                if(gridData.openReportScore<70){
                   openReportScore= `<td style="color: red">${gridData.openReportScore}</td>`
                }else {
                    openReportScore= `<td style="color: limegreen">${gridData.openReportScore}</td>`
                }
                $("#data").append(`
                    <tr>
                        <td><input name="thesis"  checkStatus="${gridData.checkStatus}" type="checkbox" value="${gridData.thesisNo}"/></td>
                        <td>${(page-1)*rows+i + 1}</td>
                        <td>${gridData.studentNo}</td>
                        <td>${gridData.studentName}</td>
                        <td>${gridData.studentMajor}</td>
                        <td>${gridData.studentClass}</td>
                        <td>${gridData.thesisTitle}</td>
                        ${openReportScore}
                       ${reviewStatus}
                       ${inspectionStatus}
                        ${status}
                        ${score}
                        ${checkStatus}
                    </tr>
                `)
            }
        },
        error: function () {
            $.MsgBox.Alert("错误", "加载信息错误！");
        }
    })
}