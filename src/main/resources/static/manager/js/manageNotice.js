var page = 1;
const rows = 5;
var totalPage;
var noticeIds=new Array();
$(function () {
    loadDataGrid();
    $("#addBtn").click(function () {
        location.href="./addNotice.html"
    });

    $("#modifyBtn").click(function () {
        if(noticeIds.length>1){
            $.MsgBox.Alert("提示", "只能选择一条数据进行操作！");
            return;
        }
        if(noticeIds.length<=0){
            $.MsgBox.Alert("提示", "请选择一条数据进行操作！");
            return;
        }
        let noticeId = noticeIds[0];
        location.href = "/thesis/manager/modifyNotice.html?noticeId=" + noticeId;
    });

    $("#deleteBtn").click(function () {
        if (noticeIds.length <= 0) {
            $.MsgBox.Alert("提示", "请选择至少一条数据进行操作！");
            return;
        }
        $.MsgBox.Alert("提示","确定删除所选择的公告？",function () {
            $.ajax({
                type: "delete",
                url: "/thesis/notice/deleteNoticesByIds?noticeIds=" +noticeIds,
                success: function (data) {
                    $.MsgBox.Alert("提示", data.msg,function () {
                        location.href="./manageNotice.html";
                    });
                },
                error: function () {
                    $.MsgBox.Alert("错误", "删除公告信息失败！");
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
    }
    $.ajax({
        type: "get",
        url: "/thesis/notice/getAllNoticeList?" + $.param(pageInfo),
        success: function (data) {
            let s="";
            totalPage = Math.ceil(data.total / rows);
            $("#totalData").html(data.total);
            $("#currentPage").html(page + "/" + totalPage);
            for (let i = 0; i < data.rows.length; i++) {
                let gridData = (data.rows)[i];
                let belong=gridData.noticeBelong;
                let status=gridData.noticeStatus;
                let updateTime=gridData.updateTime;
                if(updateTime==null||updateTime==""){
                   updateTime="未更新过";
                }
                if(belong=="1"){
                    belong="全部";
                }
                if(belong=="2"){
                    belong="教师";
                }
                if(belong=="3"){
                    belong="学生";
                }
                if(status==1){
                    status="<td style=\"color:limegreen;\">显示</td>";
                }else {
                    status="<td style=\"color:red;\">隐藏</td>";
                }
                if(noticeIds.indexOf(gridData.noticeId)!=-1){
                   s="<td><input name=\"noticeId\" checked=\"checked\" onchange='changeIds()' type=\"checkbox\" value=\""+gridData.noticeId+"\"/></td>"
                }
                else {
                   s="<td><input name=\"noticeId\" onchange='changeIds()' type=\"checkbox\" value=\""+gridData.noticeId+"\"/></td>"
                }
                $("#data").append(`
                    <tr>
                        ${s}
                        <td>${(page-1)*rows+i + 1}</td>
                         <td>${gridData.noticeId}</td>
                         <td>${gridData.noticeTitle}</td>
                         <td><a style="text-decoration: underline;cursor: pointer" onclick="downloadNoticeFile('${gridData.noticeId}','${gridData.noticeUrl}')">${gridData.noticeUrl}</a></td>
                         <td>${belong}</td>
                         <td>${gridData.createTime}</td>
                         <td>${updateTime}</td>
                         ${status}
                    </tr>
                `)
            }
        },
        error: function () {
            $.MsgBox.Alert("错误", "加载公告信息列表错误！");
        }
    })
}
function  downloadNoticeFile(noticeId,fileName){
   window.location.href="/thesis/file/downloadFile?fileName="+fileName+"&path=notice/"+noticeId;
   return false;
}

function changeIds(){
    var oneches=document.getElementsByName("noticeId");
    for(var i=0;i<oneches.length;i++){
        let n=noticeIds.indexOf(oneches[i].value);
        if(oneches[i].checked==true){
            //避免重复累计id （不含该id时进行累加）
            if(n==-1){
               noticeIds.push(oneches[i].value);
            }
        }
        if(oneches[i].checked==false){
            //取消复选框时 含有该id时将id从全局变量中去除
            if(n!=-1){
                noticeIds.splice(n,1);
            }
        }
    }
}

