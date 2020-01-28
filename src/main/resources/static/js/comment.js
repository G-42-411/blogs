function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    console.log(questionId);
    console.log(content);
    comment2target(questionId,1,content);
}

function comment2target(targetId, type, content) {
    $.ajax({
        url: "/comment",
        type: "post",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
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

function comment(e){
    var commentId = e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();
    comment2target(commentId,2,content);
}

function collapseComments(e) {
    var id = e.getAttribute("data-id");
    $(e).toggleClass("active");
    var isHas = $("#comment-"+id).toggleClass("in");
    if(isHas){
        $.getJSON("/comment/"+id, function (data) {
            console.log(data);
        })
    }
    alert(isHas);
    console.log(id);
}