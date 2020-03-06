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
    var userId = theRequest.userId;

    // 进入页面初始化数据
    loadInitData(userId);

    $("#submitStudentInfo").click(function () {
      modifyStudent(userId);
    })
    $("#submitTeacherInfo").click(function () {
      modifyTeacher(userId);
    })
    $("#returnStudent").click(function () {
        location.href = "manageUser.html";
    })
    $("#returnTeacher").click(function () {
        location.href = "manageUser.html";
    })
})

function loadInitData(userId) {
    let param = {
        userId: userId
    }
    $.ajax({
        type: "get",
        url: "/thesis/user/getUserDetailInfoById?" + $.param(param),
        success: function (data) {
            if(data.type=="3"){
                $("#studentInfo").show();
               let student=data.student;
               $("#studentNo").val(student.studentNo);
               $("#studentName").val(student.studentName);
               $("#studentInstructor").val(student.studentInstructor);
               $("#studentClass").val(student.studentClass);
               $("#studentMajor").val(student.studentMajor);
               $("#studentPhone").val(student.studentPhone);
               $("#studentEmail").val(student.studentEmail);

            }else {
                $("#teacherInfo").show();
                let teacher=data.teacher;
                $("#teacherNo").val(teacher.teacherNo);
                $("#teacherName").val(teacher.teacherName);
                $("#teacherTitle").val(teacher.teacherTitle);
                $("#teacherEducation").val(teacher.teacherEducation);
                $("#teacherPhone").val(teacher.teacherPhone);
                $("#teacherEmail").val(teacher.teacherEmail);
            }
        },
        error: function () {
            $.MsgBox.Alert("错误", "初始化用户信息失败！");
        }
    })
}

function modifyStudent(userId) {
    let studentNo=$("#studentNo").val();
    let studentName=$("#studentName").val();
    let studentClass=$("#studentClass").val();
    let studentMajor=$("#studentMajor").val();
    let studentInstructor=$("#studentInstructor").val();
    let studentPhone=$("#studentPhone").val();
    let studentEmail=$("#studentEmail").val();
    if (studentNo == null || studentNo == ""||studentName == null || studentName == ""||studentInstructor == null || studentInstructor == ""||studentClass == null || studentClass == ""||studentMajor == null || studentMajor == ""||studentEmail == null || studentEmail== "") {
        $.MsgBox.Alert("提示", "必填项不能为空！");
        return;
    }
    let data = {
      studentNo:studentNo,
      studentName:studentName,
      studentInstructor:studentInstructor,
      studentEmail:studentEmail,
      studentMajor:studentMajor,
      studentClass:studentClass,
      studentPhone:studentPhone,
        userId:userId
    }
    $.MsgBox.Alert("提示", "确定修改此学生信息？", function () {
        $.ajax({
            type: "post",
            url: "/thesis/user/updateStudentInfo?" + $.param(data),
            success: function (data) {
                $.MsgBox.Alert("提示", data.msg, function () {
                    location.href = "./manageUser.html";
                });
            },
            error: function () {
                $.MsgBox.Alert("错误", "修改学生信息异常！");
            }
        })
    })
}
function modifyTeacher(userId) {
    let teacherNo=$("#teacherNo").val();
    let teacherName=$("#teacherName").val();
    let teacherTitle=$("#teacherTitle").val();
    let teacherEducation=$("#teacherEducation").val();
    let teacherEmail=$("#teacherEmail").val();
    let teacherPhone=$("#teacherPhone").val();
    if (teacherNo == null || teacherNo == ""||teacherName == null || teacherName == ""||teacherTitle == null || teacherTitle == ""||teacherEducation == null || teacherEducation == ""||teacherEmail == null || teacherEmail== "") {
        $.MsgBox.Alert("提示", "必填项不能为空！");
        return;
    }
    let data = {
       teacherNo:teacherNo,
        teacherName:teacherName,
        teacherEmail:teacherEmail,
        teacherEducation:teacherEducation,
        teacherPhone:teacherPhone,
        teacherTitle:teacherTitle,
        userId:userId
    }
    $.MsgBox.Alert("提示", "确定修改此教师信息？", function () {
        $.ajax({
            type: "post",
            url: "/thesis/user/updateTeacherInfo?" + $.param(data),
            success: function (data) {
                $.MsgBox.Alert("提示", data.msg, function () {
                    location.href = "./manageUser.html";
                });
            },
            error: function () {
                $.MsgBox.Alert("错误", "修改教师信息异常！");
            }
        })
    })
}