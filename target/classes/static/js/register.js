$(document).ready(function () {
    $("#register").click(function () {
        register();
    })
});
function register()
{
    var username=$("#username").val();
    var email=$("#email").val();
    var password=$("#password").val();
    $.ajax({
        type:"POST",
        url:"/register",
        dataType:'json',
        data:{username:username,email:email,password:password},
        cache:false,
        async:true,
        success:function (res) {
            alert(res.res);
            if (res.res == "注册失败") {
            }else{
                window.location.href="/login";
            }

        },
        error:function (res) {
            alert("注册失败，请检查网络");
        }
    })
}