function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    console.log(questionId);
    console.log(content);
    comment2target(questionId, 1, content);
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
                if (data.code == 2003) {
                    if (window.confirm(data.message)) {
                        window.open("https://github.com/login/oauth/authorize?client_id=73de90edf12125142f2b&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closeable", "true");
                    }
                } else {
                    alert(data.message);
                }
            }
        },
    })
}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);
}

function collapseComments(e) {
    var id = e.getAttribute("data-id");
    $(e).toggleClass("active");
    var isHas = $("#comment-" + id).toggleClass("in");
    var subCommentContainer = $("#comment-" + id);
    if (isHas) {
        $.getJSON("/comment/" + id, function (data) {
            if(subCommentContainer.children().length == 1){
                $.each(data.data, function (index, comment) {


                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaHeadingElement = $("<h4/>", {
                        "class": "media-heading",
                        "html":comment.user.name
                    });

                    var contentElement = $("<span/>", {
                        "html":comment.content
                    });

                    var iconColorElement = $("<div/>", {
                        "class":"icon-color"
                    }).append($("<span/>", {
                        "class":"pull-right",
                        "html": moment(comment.gmtCreate).format("YYYY-MM-DD")
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append(mediaHeadingElement).append(contentElement).append(iconColorElement);

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);
                    subCommentContainer.prepend(commentElement);
                })
            }
        })
    }
}