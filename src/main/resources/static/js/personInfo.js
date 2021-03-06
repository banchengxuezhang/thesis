$(function () {
    $("#modifyBtn").hide();
    // 加载信息
    loadPersonInfo();
    $("#modifyInfo").change(function () {
        let status = $("#modifyInfo").is(":checked");
        if (status) {
            // 允许用户修改电话和邮箱
            $("#phone").attr("readonly", false);
            $("#email").attr("readonly", false);
            // 修改radio被选中，展示确认修改按钮给用户
            $("#modifyBtn").show();
        } else {
            // 不选中，修改无效，重新刷新页面
          location.href = "./personInfo.html";
        }
    });

    $("#modifyBtn").click(function () {
        $.MsgBox.Confirm("提示", "确定提交修改后的信息吗？", commitInfo);
    })
})

function commitInfo() {
    // 获取修改后的用户电话和邮箱
    let phone = $("#phone").val();
    let email = $("#email").val();
    if((!isPhone(phone))&&phone!=""){
        $.MsgBox.Alert("错误", "手机号格式错误！");
        return;
    }
    if((!isEmail(email))&&email!=""){
        $.MsgBox.Alert("错误", "邮箱格式错误！");
        return;
    }
    let data = {phone: phone, email: email};
    ``
    $.ajax({
        type: "post",
        url: "/thesis/user/updateInfo?" + $.param(data),
        success: function (data) {
            $.MsgBox.Alert(data.code == 1 ? "提示" : "错误", data.msg, function () {
               location.href = "./personInfo.html";
            });
        },
        error: function () {
            $.MsgBox.Alert("错误", "修改用户信息错误！");
        }
    })
}
function isPhone(phone){
    return /^1(3\d|4\d|5\d|6\d|7\d|8\d|9\d)\d{8}$/g.test(phone);
}
function isEmail(email){
    return /^\w+@[a-z0-9]+\.[a-z]+$/i.test(email);
}

function loadPersonInfo() {
    $.ajax({
        type: "get",
        url: "/thesis/user/getInfo",
        async: false,
        success: function (data) {
            let u = data.data.user;
            // 如果是学生信息
            if (3 == u.userType) {
                let studentPhone = data.data.personInfo.studentPhone == null ? '' : data.data.personInfo.studentPhone;
                let studentEmail = data.data.personInfo.studentEmail == null ? '' : data.data.personInfo.studentEmail;
                $("#forminfo").html(`
                    <div class="am-form-group"> <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">姓名</label> <div class="am-u-sm-10"><input  readonly="readonly" type="text" id="doc-ipt-3"value="${data.data.personInfo.studentName}"></div></div>
                    <div class="am-form-group"> <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">学号</label> <div class="am-u-sm-10"><input readonly="readonly" type="text" id="doc-ipt-3"value="${data.data.personInfo.studentNo}"></div></div>
                    <div class="am-form-group"> <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">专业</label> <div class="am-u-sm-10"><input readonly="readonly" type="text" id="doc-ipt-3"value="${data.data.personInfo.studentMajor}"></div></div>
                    <div class="am-form-group"> <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">班级</label> <div class="am-u-sm-10"><input readonly="readonly" type="text" id="doc-ipt-3"value="${data.data.personInfo.studentClass}"></div></div>
                    <div class="am-form-group"> <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">辅导员</label> <div class="am-u-sm-10"><input readonly="readonly" type="text" id="doc-ipt-3"value="${data.data.personInfo.studentInstructor}"></div></div>
                    <div class="am-form-group"> <label for="phone" class="am-u-sm-2 am-form-label">电话</label> <div class="am-u-sm-10"><input readonly="readonly" type="text" id="phone"value="${studentPhone}"></div></div>
                    <div class="am-form-group"> <label for="email" class="am-u-sm-2 am-form-label">邮箱</label> <div class="am-u-sm-10"><input readonly="readonly" type="text"id="email" value="${studentEmail}"></div></div>
               
               `)
            } else {
                let teacherPhone = data.data.personInfo.teacherPhone == null ? '' : data.data.personInfo.teacherPhone;
                let teacherEmail = data.data.personInfo.teacherEmail == null ? '' : data.data.personInfo.teacherEmail;
                // 如果是老师信息或管理员信息
                $("#forminfo").html(`
                    <div class="am-form-group"> <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">姓名</label> <div class="am-u-sm-10"><input readonly="readonly" type="text"id="doc-ipt-3" value="${data.data.personInfo.teacherName}"></div></div>
                    <div class="am-form-group"> <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">教师编号</label> <div class="am-u-sm-10"><input readonly="readonly" type="text" id="doc-ipt-3" value="${data.data.personInfo.teacherNo}" ></div></div>
                    <div class="am-form-group"> <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">职称</label> <div class="am-u-sm-10"><input readonly="readonly" type="text"id="doc-ipt-3" value="${data.data.personInfo.teacherTitle}"></div></div>
                    <div class="am-form-group"> <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">学历</label> <div class="am-u-sm-10"><input  readonly="readonly" type="text" id="doc-ipt-3"value="${data.data.personInfo.teacherEducation}" ></div></div>
                    <div class="am-form-group"> <label for="phone" class="am-u-sm-2 am-form-label">电话</label> <div class="am-u-sm-10"><input  readonly="readonly" type="text" id="phone" value="${teacherPhone}" ></div></div>
                    <div class="am-form-group"> <label for="email" class="am-u-sm-2 am-form-label">邮箱</label> <div class="am-u-sm-10"><input readonly="readonly" type="text" id="email" value="${teacherEmail}"></div></div>
                   `)
            }
        },
        error: function () {
            $.MsgBox.Alert("错误", "加载个人信息数据失败");
        }
    })
}