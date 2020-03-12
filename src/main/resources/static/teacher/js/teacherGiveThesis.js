var page = 1;
const rows = 5;
var totalPage;
var thesisIds=new Array();
$(function () {
    loadDataGrid();

    $("#addBtn").click(function () {
        $.ajax({
            type:"get",
            url:"/thesis/getPowerGiveThesis",
            success:function(data){
                if(data.code==1){
                    location.href = "./addThesis.html";
                }else {
                    $.MsgBox.Alert("提示", data.msg);
                }
            },
            error:function () {
                $.MsgBox.Alert("提示", "加载页面失败！");
            }
        })

    });

    $("#modifyBtn").click(function () {
        if (thesisIds.length <= 0) {
            $.MsgBox.Alert("提示", "请选择一条数据进行操作！");
            return;
        }
        if (thesisIds.length > 1) {
            $.MsgBox.Alert("提示", "只能选择一条数据进行操作！");
            return;
        }
        let thesisId = thesisIds[0];
        location.href = "/thesis/teacher/modifyThesis.html?thesisId=" + thesisId;
    });

    $("#deleteBtn").click(function () {
        if (thesisIds.length <= 0) {
            $.MsgBox.Alert("提示", "请选择至少一条数据进行操作！");
            return;
        }
        $.MsgBox.Alert("提示","确定删除所选择的论文信息？",function () {
            $.ajax({
                type: "delete",
                url: "/thesis/deleteThesisInfoByThesisIds?thesisIds=" + thesisIds,
                success: function (data) {
                    $.MsgBox.Alert("提示", data.msg,function () {
                        location.href="./teacherGiveThesis.html";
                    });
                },
                error: function () {
                    $.MsgBox.Alert("错误", "删除论文信息失败！");
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
        url: "/thesis/getThesisInfoByTeacherNo?" + $.param(pageInfo),
        success: function (data) {
            let s="";
            totalPage = Math.ceil(data.total / 5);
            $("#totalData").html(data.total);
            $("#currentPage").html(page + "/" + totalPage);
            for (let i = 0; i < data.rows.length; i++) {
                let gridData = (data.rows)[i];
                if(thesisIds.indexOf(gridData.thesisId)!=-1){
                    s="<td><input name=\"thesisId\" checked=\"checked\" onchange='changeIds()' type=\"checkbox\" value=\""+gridData.thesisId+"\"/></td>"
                }
                else {
                    s="<td><input name=\"thesisId\" onchange='changeIds()' type=\"checkbox\" value=\""+gridData.thesisId+"\"/></td>"
                }
                $("#data").append(`
                    <tr>
                        ${s}
                        <td>${(page-1)*rows+i + 1}</td>
                        <td>${gridData.thesisTitle}</td>
                        <td>${gridData.noticeInfo}</td>
                    </tr>
                `)
            }
        },
        error: function () {
            $.MsgBox.Alert("错误", "加载论文题目信息错误！");
        }
    })
}
function changeIds(){
    var oneches=document.getElementsByName("thesisId");
    for(var i=0;i<oneches.length;i++){
        let n=thesisIds.indexOf(oneches[i].value);
        if(oneches[i].checked==true){
            //避免重复累计id （不含该id时进行累加）
            if(n==-1){
                thesisIds.push(oneches[i].value);
            }
        }
        if(oneches[i].checked==false){
            //取消复选框时 含有该id时将id从全局变量中去除
            if(n!=-1){
                thesisIds.splice(n,1);
            }
        }
    }
}