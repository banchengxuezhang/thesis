$(function () {
    $.ajax({
        type: 'get',
        url: "/thesis/studentTeacherRelation/getThesisForOpenReport",
        success: function (data) {
            let gridData=data.rows[0];
            $("#studentName").text(gridData.studentName);
            $("#studentNo").text(gridData.studentNo);
            $("#studentMajor").text(gridData.studentMajor);
            $("#studentClass").text(gridData.studentClass);
            $("#thesisTitle").text(gridData.thesisTitle);
        },
        error: function () {
            $.MsgBox.Alert("错误", "查询课题数据失败！");
        }
    })

})