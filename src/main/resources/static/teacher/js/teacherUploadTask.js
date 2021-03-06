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
        type: "get",
        url: "/thesis/studentTeacherRelation/getAgreeThesisByTeacherNo?" + $.param(pageInfo),
        success: function (data) {
            totalPage = Math.ceil(data.total / 5);
            $("#totalData").html(data.total);
            $("#currentPage").html(page + "/" + totalPage);
            for (let i = 0; i < data.rows.length; i++) {
                let gridData = (data.rows)[i];
                let status = (gridData.taskStatus == 2 ?  `<td style="color: red">未给</td>` :`<td style=\"color:limegreen;\">已给</td>`);
                let fileName=gridData.taskUrl;
                if(fileName==null){
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
                        <td style="color: red">${status}</td>
                        <td><a style="text-decoration: underline;cursor: pointer" onclick="uploadTask('${gridData.thesisTitle}','${gridData.thesisNo}')">给任务书</a></td>   
                         <td style="text-decoration:underline" onclick="downloadTask('${gridData.thesisNo}')">${fileName}</td>
                    </tr>
                `)
            }
        },
        error: function () {
            $.MsgBox.Alert("错误", "加载论文题目信息错误！");
        }
    })
}
function downloadTask(thesisNo) {
    window.location.href="/thesis/file/downloadTask?thesisNo="+thesisNo;
    return false;
}
function uploadTask(thesisTitle, thesisNo) {
    Dialog.init(`<form action="/thesis/file/uploadTask" method="post" enctype="multipart/form-data">
        <input type="hidden" name="thesisNo" id="thsisNo" value="${thesisNo}"/>
        <input type="hidden" name="thesisTitle" id="thesisTitle" value="${thesisTitle}"/>
        <p>选择文件: <input type="file" name="file"/></p>
    </form>`, {
        title: '上传任务书',
        button: {
            确定: function () {
                // 使用jq的form插件
                $("form").ajaxSubmit({
                    success: function (responseText, statusText) {
                        //alert('状态: ' + statusText + '\n 返回的内容是: \n' + responseText);
                        $.MsgBox.Alert("提示",responseText.msg);
                        // 清除表格数据
                        $("#data").empty();
                        // 重新加载数据
                        loadDataGrid();
                    },
                    resetForm: true
                });
                Dialog.close(this);
            },
            点击关闭: function () {
                Dialog.close(this);
            }
        }
    })
}