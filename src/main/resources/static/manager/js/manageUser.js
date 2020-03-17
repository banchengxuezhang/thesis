var page = 1;
const rows = 5;
var totalPage;
var userIds=new Array();
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
        if (userIds.length <= 0) {
            $.MsgBox.Alert("提示", "请选择一条数据进行操作！");
            return;
        }
        if (userIds.length > 1) {
            $.MsgBox.Alert("提示", "只能选择一条数据进行操作！");
            return;
        }
        let userId = userIds[0];
        location.href = "/thesis/manager/modifyUser.html?userId=" + userId;
    });

    $("#deleteBtn").click(function () {
        if (userIds.length <= 0) {
            $.MsgBox.Alert("提示", "请选择至少一条数据进行操作！");
            return;
        }
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
    $("#noCheckStudentBtn").click(function () {
        if (userIds.length <= 0) {
            $.MsgBox.Alert("提示", "请选择一条数据进行操作！");
            return;
        }
        if (userIds.length > 1) {
            $.MsgBox.Alert("提示", "只能选择一条数据进行操作！");
            return;
        }
        let userId = userIds[0];
        $.ajax({
            type:"post",
            url:"/thesis/replyScore/noCheckStudentStatus?userId="+userId,
            success:function (data) {
                 $.MsgBox.Alert("提示",data.msg);
            },
            error: function () {
             $.MsgBox.Alert("提示","取消验收失败！！！");
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
            let s="";
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
                if(userIds.indexOf(gridData.userId)!=-1){
                    s="<td><input name=\"userId\" checked=\"checked\" onchange='changeIds()' type=\"checkbox\" value=\""+gridData.userId+"\"/></td>"
                }
                else {
                    s="<td><input name=\"userId\" onchange='changeIds()' type=\"checkbox\" value=\""+gridData.userId+"\"/></td>"
                }
                $("#data").append(`
                    <tr>
                       ${s}
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
function changeIds(){
    var oneches=document.getElementsByName("userId");
    for(var i=0;i<oneches.length;i++){
        let n=userIds.indexOf(oneches[i].value);
        if(oneches[i].checked==true){
            //避免重复累计id （不含该id时进行累加）
            if(n==-1){
                userIds.push(oneches[i].value);
            }
        }
        if(oneches[i].checked==false){
            //取消复选框时 含有该id时将id从全局变量中去除
            if(n!=-1){
                userIds.splice(n,1);
            }
        }
    }
}