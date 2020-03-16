$(function () {
loadData();
$("#returnFirstBtn").click(function () {
    let data1={
        firstDate:new Date("2099-12-31")
    }
$.ajax({
    type: "post",
    url:"/thesis/init/updateControllDate?"+$.param(data1),
    success:function (data) {
          $.MsgBox.Alert("提示",data.msg);
    },
    error:function () {
        $.MsgBox.Alert("提示","更新出错！！！");
    }
})
})
$("#returnSecondBtn").click(function () {
    let data1={
        firstDate:new Date("1970-01-01"),
        secondDate:new Date("2099-12-31"),
    }
    $.ajax({
        type: "post",
        url:"/thesis/init/updateControllDate?"+$.param(data1),
        success:function (data) {
            $.MsgBox.Alert("提示",data.msg);
        },
        error:function () {
            $.MsgBox.Alert("提示","更新出错！！！");
        }
    })
})
$("#updateDateBtn").click(function () {
    let firstDate=$("#firstDate").val();
    let secondDate=$("#secondDate").val();
    if(firstDate==""||secondDate==""){
        $.MsgBox.Alert("提示","第一阶段和第二阶段结束时间为必填！！！");
    }
    let data1={
        firstDate:new Date(firstDate),
        secondDate:new Date(secondDate),
    }
    $.ajax({
        type: "post",
        url:"/thesis/init/updateControllDate?"+$.param(data1),
        success:function (data) {
            $.MsgBox.Alert("提示",data.msg,function () {
                location.href="task.html";
            });

        },
        error:function () {
            $.MsgBox.Alert("提示","更新出错！！！");
        }
    })
})
$("#returnBtn").click(function () {
location.href="../content.html";
})

})
function loadData(){
   $.ajax({
       type:"get",
       url:"/thesis/init/getInitInfo",
       success:function(data){
               let firstDate=data.data.firstDate;
               let secondDate=data.data.secondDate;
                   $("#firstDate").val(firstDate.split(" ")[0]);
                   $("#secondDate").val(secondDate.split(" ")[0]);
       },
       error:function () {
           $.MsgBox.Alert("提示","初始化日期出错！！！");
       }
   })
}