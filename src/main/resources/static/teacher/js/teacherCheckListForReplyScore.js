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
        let thesisNo = $(checkedObj[0]).val();
        location.href = "./teacherOperate.html?thesisNo=" + thesisNo;
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
                let status="";
                let score=gridData.thesisScoreList;
                if(gridData.replyStatus==0){
                    status="<td>未答辩</td>";
                    score="未答辩"
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
                }
                $("#data").append(`
                    <tr>
                        <td><input name="thesis" type="checkbox" value="${gridData.thesisNo}"/></td>
                        <td>${(page-1)*rows+i + 1}</td>
                        <td>${gridData.studentNo}</td>
                        <td>${gridData.studentName}</td>
                        <td>${gridData.studentMajor}</td>
                        <td>${gridData.studentClass}</td>
                        <td>${gridData.thesisTitle}</td>
                            ${status} 
                        <td>${date}</td>
                        <td>${gridData.replyPlace}</td>
                        <td>${gridData.teacherList}</td>
                        <td>${score}</td>
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