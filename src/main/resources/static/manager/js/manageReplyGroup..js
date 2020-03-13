var page = 1;
const rows = 5;
var totalPage;
var userIds=new Array();
var groupIds=new Array();
$(function () {
    var url = location.search; //获取url中"?"符后的字串 ('?modFlag=business&role=1')
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1); //substr()方法返回从参数值开始到结束的字符串；
        var strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = (strs[i].split("=")[1]);
        }
    }
    var flag = theRequest.flag;
    if(flag=="2"){
        $("#deleteGroupBtn").show();
        $("#modifyGroupBtn").show();
        $("#groupTable").show();
        $("#teacherListBtn").show();
        $("#addGroupBtn").hide();
        $("#teacherSelect").hide();
        $("#randomGroupBtn").hide();
        $("#teacherTable").hide();
        $("#viewGroupBtn").hide();
        loadDataGroup();
    }else {
        loadDataGrid();
    }
    $("#addGroupBtn").click(function () {
        if (userIds.length <= 0) {
            $.MsgBox.Alert("提示", "请选择至少一条数据进行操作！");
            return;
        }
        location.href="/thesis/manager/addGroup.html?userIds=" + userIds;
    })
    $('#randomGroupBtn').on('click', function() {
        $('#my-prompt').modal({
            relatedTarget: this,
            onConfirm: function(e) {
                if(!(/(^[1-9]\d*$)/.test(e.data))){
                   $.MsgBox.Alert("提示","请输入合理数字！")
                    return;
                }
                $.ajax({
                 type:"post",
                    url:"/thesis/group/randomGroup?num="+e.data,
                    success:function (data) {
                    if(data.code==1){
                        $.MsgBox.Alert("提示",data.msg,function () {
                         location.href="manageReplyGroup.html"
                        });
                    }
                    },
                    error:function () {

                    }
                })
            },
            onCancel: function(e) {
               return;
            }
        });
    });
    $("#modifyGroupBtn").click(function () {
        if(groupIds.length>1){
            $.MsgBox.Alert("提示", "只能选择一条数据进行操作！");
            return;
        }
        if(groupIds.length<=0){
            $.MsgBox.Alert("提示", "请选择一条数据进行操作！");
            return;
        }
        location.href="modifyGroup.html?groupId="+groupIds[0];
    })
    $("#queryBtn").click(function () {
        page=1;
        $("#data").empty();
        loadDataGrid();
    })

    $("#addBtn").click(function () {
        location.href="./addUser.html"
    });


    $("#deleteGroupBtn").click(function () {
        if (groupIds.length <= 0) {
            $.MsgBox.Alert("提示", "请选择至少一条数据进行操作！");
            return;
        }
        $.MsgBox.Alert("提示","确定删除所选择的分组？",function () {
            $.ajax({
                type: "delete",
                url: "/thesis/group/deleteGroupsByIds?groupIds=" + groupIds,
                success: function (data) {
                    $.MsgBox.Alert("提示", data.msg,function () {
                        location.href="manageReplyGroup.html?flag="+"2";
                    });
                },
                error: function () {
                    $.MsgBox.Alert("错误", "删除分组信息失败！");
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
            if(flag=="2"){
                $("#dataGroup").empty();
                loadDataGroup();
            }else {
                $("#data").empty();
                loadDataGrid();
            }
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
            if(flag=="2"){
                $("#dataGroup").empty();
                loadDataGroup();
            }else {
                $("#data").empty();
                loadDataGrid();
            }
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
            if(flag=="2"){
                $("#dataGroup").empty();
                loadDataGroup();
            }else {
                $("#data").empty();
                loadDataGrid();
            }
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
            if(flag=="2"){
                $("#dataGroup").empty();
                loadDataGroup();
            }else {
                $("#data").empty();
                loadDataGrid();
            }
        }
    });
    $("#viewGroupBtn").click(function () {
        flag="2";
        $("#dataGroup").empty();
        loadDataGroup();
    })
    $("#teacherListBtn").click(function () {
        flag="1";
        $("#data").empty();
        loadDataGrid();
    })
})

function loadDataGrid() {
    let pageInfo = {
        page: page,
        rows: rows,
       teacherNo:$("#teacherNo").val(),
       teacherName:$("#teacherName").val(),
       groupName:$("#groupName").val(),
    }
    $.ajax({
        type: "get",
        url: "/thesis/user/getTeacherListForManager?" + $.param(pageInfo),
        success: function (data) {
            totalPage = Math.ceil(data.total / rows);
            $("#totalData").html(data.total);
            $("#currentPage").html(page + "/" + totalPage);
            let s="";
            $("#groupName").empty();
            $("#groupName").append(`
                    <option value="" selected="selected">全部小组</option>
                `)
            if(pageInfo.groupName=="-1"){
                $("#groupName").append(`
                    <option value="-1" selected="selected">未分组教师</option>
                `)
            }else {
                $("#groupName").append(`
                    <option value="-1">未分组教师</option>
                `)
            }

            for(let i=0;i<data.groupNames.length;i++){
                let groupName=data.groupNames[i];
                if(groupName==pageInfo.groupName){
                    $("#groupName").append(`
                    <option value="${groupName}" selected="selected">${groupName}</option>
                `)
                }else {
                    $("#groupName").append(`
                    <option value="${groupName}">${groupName}</option>
                `)
                }

            }
            for (let i = 0; i < data.rows.length; i++) {
                let gridData = (data.rows)[i];
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
                        <td>${gridData.teacherNo}</td>
                        <td>${gridData.teacherName}</td>
                        <td>教师</td>
                        <td>${gridData.teacherTitle}</td>
                        <td>${gridData.teacherEducation}</td>
                        <td>${gridData.teacherPhone}</td>
                        <td>${gridData.teacherEmail}</td>
                        <td>${gridData.groupName}</td>
                        <td>${data[gridData.teacherNo]}</td>
                    </tr>
                `)
            }
        },
        error: function () {
            $.MsgBox.Alert("错误", "加载教师信息列表错误！");
        }
    })
}
function loadDataGroup() {
    let pageInfo = {
        page: page,
        rows: rows
    }
    $.ajax({
        type: "get",
        url: "/thesis/group/getGroups?" + $.param(pageInfo),
        success: function (data) {
            totalPage = Math.ceil(data.total / rows);
            $("#totalData").html(data.total);
            $("#currentPage").html(page + "/" + totalPage);
            let s="";
            for (let i = 0; i < data.rows.length; i++) {
                let gridData = (data.rows)[i];
                let status="";
                let date="";
                if(gridData.groupStatus=="1"){
                    status="<td style=\"color:limegreen;\">显示</td>";
                }else {
                    status="<td style=\"color:red;\">隐藏</td>";
                }
                if(groupIds.indexOf(gridData.groupId)!=-1){
                    s="<td><input name=\"groupId\" checked=\"checked\" onchange='changeIds1()' type=\"checkbox\" value=\""+gridData.groupId+"\"/></td>"
                }
                else {
                    s="<td><input name=\"groupId\" onchange='changeIds1()' type=\"checkbox\" value=\""+gridData.groupId+"\"/></td>"
                }
                if(gridData.replyDate!=null) {
                    date = gridData.replyDate.split(" ")[0];
                }
                $("#dataGroup").append(`
                    <tr>
                       ${s}
                        <td>${(page-1)*rows+i + 1}</td>
                        <td>${gridData.groupName}</td>
                        <td>${gridData.grouperName}</td>
                        <td>${date}</td>
                         <td>${gridData.replyPlace}</td>
                         ${status}
                         <td>${data[gridData.groupName+"teacher"]}</td>
                         <td>${data[gridData.groupName+"student"]}</td>
                    </tr>
                `)
            }
        },
        error: function () {
            $.MsgBox.Alert("错误", "加载教师信息列表错误！");
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
function changeIds1(){
    var oneches=document.getElementsByName("groupId");
    for(var i=0;i<oneches.length;i++){
        let n=groupIds.indexOf(oneches[i].value);
        if(oneches[i].checked==true){
            //避免重复累计id （不含该id时进行累加）
            if(n==-1){
                groupIds.push(oneches[i].value);
            }
        }
        if(oneches[i].checked==false){
            //取消复选框时 含有该id时将id从全局变量中去除
            if(n!=-1){
                groupIds.splice(n,1);
            }
        }
    }
}