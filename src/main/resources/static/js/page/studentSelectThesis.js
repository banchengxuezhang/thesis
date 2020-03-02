var page = 1;
const rows = 5;
var totalPage;
var thesisTitle="";
var teacherNo="";
var teacherName="";
var selectNum;
$(function () {
    // 加载表格数据
    loadDataGrid();
    $("#queryBtn").click(function () {
        page=1;
        thesisTitle=$("#thesisTitle").val();
        teacherNo=$("#teacherNo").val();
        teacherName=$("#teacherName").val();
        selectNum=$("#selectNum").val();
        $("#data").empty();
        loadDataGrid();
    })
    $("#selectBtn").click(function () {
        let checkedObj = $("input[name='thesis']:checked");
        if (checkedObj.length <= 0) {
            $.MsgBox.Alert("提示", "请选择一条数据进行操作！");
            return;
        }
        if (checkedObj.length > 1) {
            $.MsgBox.Alert("提示", "只能选择一条数据进行操作！");
            return;
        }
        $.MsgBox.Confirm("提示", "确定选择该选题吗？", function () {
            let thesisId = $(checkedObj[0]).val();
            $.ajax({
                type: "post",
                url: "/thesis/studentTeacherRelation/getStudentSelectThesisResult?thesisId=" + thesisId,
                async: false,
                success: function (data) {
                    $.MsgBox.Alert("提示", data.msg, function () {
                        location.href = "/thesis/studentSelectThesis.html";
                    });
                },
                error: function () {
                    $.MsgBox.Alert("错误", "选题异常！");
                }
            });
        })
    });

    $("#firstPage").click(function () {
        if(totalPage==0){
            $.MsgBox.Alert("提示", "没有符合条件的论文！");
        }else {
            if (page == 1) {
                $.MsgBox.Alert("提示", "当前已经是第一页！");
            } else {
                page = 1;
                // 清除之前表格中的数据
                $("#data").empty();
                loadDataGrid();
            }
        }
    });

    $("#prePage").click(function () {
        if(totalPage==0){
            $.MsgBox.Alert("提示", "没有符合条件的论文！");
        }else {
            if (page == 1) {
                $.MsgBox.Alert("提示", "无上一页！");
            } else {
                page -= 1;
                // 清除之前表格中的数据
                $("#data").empty();
                loadDataGrid();
            }
        }
    });

    $("#nextPage").click(function () {
        if(totalPage==0){
            $.MsgBox.Alert("提示", "没有符合条件的论文！");
        }else {
            if (page == totalPage) {
                $.MsgBox.Alert("提示", "无下一页！");
            } else {
                page += 1;
                // 清除之前表格中的数据
                $("#data").empty();
                loadDataGrid();
            }
        }
    });

    $("#lastPage").click(function () {
        if(totalPage==0){
            $.MsgBox.Alert("提示", "没有符合条件的论文！");
        }else {
            if (page == totalPage) {
                $.MsgBox.Alert("提示", "当前已经是最后一页！");
            } else {
                page = totalPage;
                // 清除之前表格中的数据
                $("#data").empty();
                loadDataGrid();
            }
        }
    });
})

function loadDataGrid() {
    let pageInfo = {
        page: page,
        rows: rows,
        thesisTitle:thesisTitle,
        teacherName:teacherName,
        teacherNo:teacherNo,
        selectNum:selectNum

    }
    $.ajax({
        type: "get",
        url: "/thesis/getThesisInfo?" + $.param(pageInfo),
        async: false,
        success: function (data) {
            totalPage = Math.ceil(data.total / 5);
            $("#totalData").html(data.total);
            $("#currentPage").html(page + "/" + totalPage);
            for (let i = 0; i < data.rows.length; i++) {
                let gridData = (data.rows)[i];
                $("#data").append(`
                    <tr>
                        <td><input name="thesis" type="checkbox" value="${gridData.thesisId}"/></td>
                        <td>${gridData.thesisId}</td>
                        <td>${gridData.thesisTitle}</td>
                        <td>${gridData.teacherNo}</td>
                        <td>${gridData.teacherName}</td>
                        <td>${gridData.teacherTitle}</td>
                        <td>${gridData.teacherPhone}</td>
                        <td>${gridData.teacherEmail}</td>
                        <td>${gridData.noticeInfo}</td>
                        <td>${gridData.selectNum}</td>
                    </tr>
                `)
            }
        },
        error: function () {
            $.MsgBox.Alert("错误", "查询论文数据失败！");
        }
    })
}