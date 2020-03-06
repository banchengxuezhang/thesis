var page = 1;
const rows = 5;
var totalPage;
$(function () {
    loadDataGrid();
    $("#queryBtn").click(function () {
        page=1;
        $("#data").empty();
        loadDataGrid();
    })

    $("#addBtn").click(function () {
        location.href="./addUser.html"
    });

    $("#modifyBtn").click(function () {
        let checkedObj = $("input[name='userId']:checked");
        if (checkedObj.length <= 0) {
            $.MsgBox.Alert("提示", "请选择一条数据进行操作！");
            return;
        }
        if (checkedObj.length > 1) {
            $.MsgBox.Alert("提示", "只能选择一条数据进行操作！");
            return;
        }
        let userId = $(checkedObj[0]).val();
        location.href = "/thesis/manager/modifyUser.html?userId=" + userId;
    });

    $("#deleteBtn").click(function () {
        let checkedObj = $("input[name='userId']:checked");
        if (checkedObj.length <= 0) {
            $.MsgBox.Alert("提示", "请选择至少一条数据进行操作！");
            return;
        }
        let userIds = new Array();
        checkedObj.each(function (i, item) {
            userIds.push(item.value);
        });
        $.MsgBox.Alert("提示","确定删除所选择的用户？",function () {
            $.ajax({
                type: "delete",
                url: "/thesis/user/deleteUsersByIds?userIds=" + userIds,
                success: function (data) {
                    $.MsgBox.Alert("提示", data.msg,function () {
                        location.href="./manageUser.html";
                    });
                },
                error: function () {
                    $.MsgBox.Alert("错误", "删除用户信息失败！");
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
        rows: rows,
        userAccount:$("#userAccount").val(),
        userName:$("#userName").val(),
        typeFlag:$("#userType").val(),
        studentMajor:$("#studentMajor").val(),
        studentClass:$("#studentClass").val()
    }
    $.ajax({
        type: "get",
        url: "/thesis/user/getUserList?" + $.param(pageInfo),
        success: function (data) {
            totalPage = Math.ceil(data.total / rows);
            $("#totalData").html(data.total);
            $("#currentPage").html(page + "/" + totalPage);
            for (let i = 0; i < data.rows.length; i++) {
                let gridData = (data.rows)[i];
                var type=""
                if(gridData.userType<=2){
                  type="教师";
                }else {
                  type="学生";
                }
                $("#data").append(`
                    <tr>
                        <td><input name="userId" type="checkbox" value="${gridData.userId}"/></td>
                        <td>${(page-1)*rows+i + 1}</td>
                        <td>${gridData.userAccount}</td>
                        <td>${gridData.userName}</td>
                        <td>${type}</td>
                        <td>${gridData.teacherTitle}</td>
                        <td>${gridData.teacherEducation}</td>
                        <td>${gridData.studentMajor}</td>
                        <td>${gridData.studentInstructor}</td>
                        <td>${gridData.studentClass}</td>
                        <td>${gridData.phone}</td>
                        <td>${gridData.email}</td>
                    </tr>
                `)
            }
        },
        error: function () {
            $.MsgBox.Alert("错误", "加载用户信息列表错误！");
        }
    })
}