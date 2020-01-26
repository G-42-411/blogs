function post() {
    var questionId = $("#question_id").val();
    var comment = $("#comment_content").val();
    $.ajax({
        url:"/comment",
        type:"post",
        data:JSON.stringify({
            "parentId":questionId,
            "content":comment,
            "type":1
        }),
        contentType:"application/json",
        dataType:"json",
        success:function (data) {
            if (data.code == 200){
                $("#comment_content").hide();
            } else{
                alert(data.message);
            }
        },
    })

    console.log(questionId);
    console.log(comment);
}