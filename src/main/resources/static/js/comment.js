function post() {
    var questionId = $("#question_id").val();
    var comment = $("#comment_content").val();
    console.log(questionId);
    console.log(comment);
    $.ajax({
        url: "/comment",
        type: "post",
        data: JSON.stringify({
            "parentId": questionId,
            "content": comment,
            "type": 1
        }),
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            if (data.code == 200) {
                window.location.reload();
            } else {
                if(data.code == 2003){
                    if(window.confirm(data.message)){
                        window.open("https://github.com/login/oauth/authorize?client_id=73de90edf12125142f2b&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closeable","true");
                    }
                }else{
                    alert(data.message);
                }
            }
        },
    })
}