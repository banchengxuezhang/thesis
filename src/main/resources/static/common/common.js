/**
 * 弹出层插件，调用方式如下
 * $.MsgBox.Alert("消息", "哈哈，添加成功！");
 * $.MsgBox.Confirm("温馨提示", "确定要进行修改吗？", test);
 */
(function () {
    $.MsgBox = {
        Alert: function (title, msg, callback) {
            GenerateHtml("alert", title, msg);
            btnOk(callback); //alert只是弹出消息，因此没必要用到回调函数callback
            btnNo();
        },
        Confirm: function (title, msg, callback) {
            GenerateHtml("confirm", title, msg);
            btnOk(callback);
            btnNo();
        }
    }
    //生成Html
    var GenerateHtml = function (type, title, msg) {
        var _html = "";
        _html += '<div  class="am-panel am-panel-default" id="mb_box"></div><div id="mb_con"><span  id="mb_tit"><div class="am-animation-slide-bottom">' + title + '</div></span>';
        _html += '<a  class="am-close am-close-alt am-close-spin am-icon-times " id="mb_ico"></a><div class="am-animation-slide-top" id="mb_msg">' + msg + '</div><div id="mb_btnbox">';
        if (type == "alert") {
            _html += '<input class="am-btn am-btn-default am-round" id="mb_btn_ok" type="button" value="确定" />';
        }
        if (type == "confirm") {
            _html += '<input id="mb_btn_ok" class="am-btn am-btn-default am-round" type="button" value="确定" />';
            _html += '<input id="mb_btn_no" class="am-btn am-btn-danger am-round" type="button" value="取消" />';
        }
        _html += '</div></div>';
        //必须先将_html添加到body，再设置Css样式
        $("body").append(_html);
        //生成Css
        GenerateCss();
    }

    //生成Css
    var GenerateCss = function () {
        $("#mb_box").css({
            width: '100%',
            height: '100%',
            zIndex: '99999',
            position: 'fixed',
            filter: 'Alpha(opacity=60)',
            backgroundColor: 'black',
            top: '0',
            left: '0',
            opacity: '0.6'
        });
        $("#mb_con").css({
            zIndex: '999999',
            width: '400px',
            position: 'fixed',
            backgroundColor: 'White',
            borderRadius: '10px'
        });
        $("#mb_tit").css({
            display: 'block',
            fontSize: '14px',
            color: '#444',
            padding: '10px 15px',
            backgroundColor: '#DDD',
            borderRadius: '10px 10px 0 0',
            borderBottom: '3px solid black',
            fontWeight: 'bold'
        });
        $("#mb_msg").css({
            padding: '20px',
            lineHeight: '20px',
            fontSize: '13px',
        });
        $("#mb_ico").css({
            display: 'block',
            position: 'absolute',
            right: '10px',
            top: '9px',
            border: '1px solid Gray',
            width: '20px',
            height: '20px',
            textAlign: 'center',
            lineHeight: '16px',
            cursor: 'pointer',
            borderRadius: '12px',
            fontFamily: '微软雅黑'
        });
        $("#mb_btnbox").css({
            margin: '15px 0 10px 0',
            textAlign: 'center'
        });
        $("#mb_btn_ok,#mb_btn_no").css({
            width: '85px',
            height: '40px',
        });
        // $("#mb_btn_ok").css({
        //     backgroundColor: '#168bbb'
        // });
        $("#mb_btn_no").css({
           // backgroundColor: 'gray',
            marginLeft: '20px'
        });
        var _widht = document.documentElement.clientWidth; //屏幕宽
        var _height = document.documentElement.clientHeight; //屏幕高
        var boxWidth = $("#mb_con").width();
        var boxHeight = $("#mb_con").height();
        //让提示框居中
        $("#mb_con").css({
            top: (_height - boxHeight) / 2+ "px",
            left: (_widht - boxWidth) / 2 + "px"
        });
    }
    //确定按钮事件
    var btnOk = function (callback) {
        $("#mb_btn_ok").click(function () {
            $("#mb_box,#mb_con").remove();
            if (typeof (callback) == 'function') {
                callback();
            }
        });
    }
    //取消按钮事件
    var btnNo = function () {
        $("#mb_btn_no,#mb_ico").click(function () {
            $("#mb_box,#mb_con").remove();
        });
    }
})();