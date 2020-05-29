$(document).ready(function () {
    layui.use(['form']),function(){
        var form = layui.form;
        form.render('remember');
    };

    $("#login").click(function () {
        login();
    });
});
function login(){
    var username=$("#username").val();
    var password = $("#password").val();
    var remember = $("#remember").siblings('div.layui-form-switch').hasClass('layui-form-onswitch');
    // console.log(remember);
    $.ajax({
        type:"POST",
        url:"/shirologin",
        dataType:'json',
        data:{username:username,password:password,remember:remember},
        cache:false,
        async:false,
        success:function (res) {
            alert(res.res);
            if (res.res == "登录成功") {
                window.location.href="/back_notes";
            }else{
                window.location.reload(true);
            }
        },
        error:function (res) {
            alert("登录失败，请检查网络");
        }
    })
}