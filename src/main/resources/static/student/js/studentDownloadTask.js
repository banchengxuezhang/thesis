var page = 1;
const rows = 5;
var totalPage;
$(function () {
    loadDataGrid();

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
        type: "post",
        url: "/thesis/studentTeacherRelation/getAgreeThesisByStudentNo?" + $.param(pageInfo),
        success: function (data) {
            totalPage = Math.ceil(data.total / 5);
            $("#totalData").html(data.total);
            $("#currentPage").html(page + "/" + totalPage);
            for (let i = 0; i < data.rows.length; i++) {
                let gridData = (data.rows)[i];
                let status = (gridData.taskStatus == 2 ?  `<td style="color: red">未给</td>` :`<td style=\"color:limegreen;\">已给</td>`);
                let fileName=gridData.taskUrl;
                if(gridData.taskUrl==null){
                    fileName="";
                }
                $("#data").append(`
                    <tr>
                        <td>${(page-1)*rows+i + 1}</td>
                        <td>${gridData.studentNo}</td>
                        <td>${gridData.studentName}</td>
                        <td>${gridData.studentMajor}</td>
                        <td>${gridData.studentClass}</td>
                        <td>${gridData.studentInstructor}</td>
                        <td>${gridData.studentPhone}</td>
                        <td>${gridData.studentEmail}</td>
                        <td>${gridData.thesisTitle}</td>
                        ${status}
                        <!--<td><a href="ftp://ftpuser:724055@192.168.0.105/13/13.jpg" style="text-decoration: underline;cursor: pointer" download="">下载</a></td>-->
                        <td><a style="text-decoration: underline;cursor: pointer" onclick="downloadTask('${gridData.thesisNo}')">${fileName}</a></td>
                    </tr>
                `)
            }
        },
        error: function () {
            $.MsgBox.Alert("错误", "加载论文题目信息错误！");
        }
    })
}