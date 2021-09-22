// 提交回复-问题，调用封装方法 comment2Target
function post() {
    var questionId = $("#question_id").val();
    console.log('questionId = ' + questionId);

    var commentContent = $("#comment_content").val();
    console.log("commentContent = " + commentContent);

    comment2Target(questionId, 1, commentContent);
    console.log("SUCCESS");
}

// 新封装方法
function comment2Target(targetId, type, content) {

    if (!content) {
        alert("请填写回复内容");
        return;
    }

    $.ajax({
        type: 'POST',
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        success: function (response) {
            if(response.code == 200) {
                window.location.reload();
            } else {
                if(response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if(isAccepted) {
                        window.open("https://gitee.com/oauth/authorize?client_id=fda0775a88442646fad277561047a203c0a673207cd704e65b9fd6de4f9fb2b5&redirect_uri=http://127.0.0.1:8887/callback&response_type=code");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
    console.log("SUCCESS");
}

// 提交回复-评论，调用封装方法 comment2Target
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $('#input-' + commentId).val();
    comment2Target(commentId, 2, content);
}

// 展开二级评论
function collapseComments(e) {
    var dataId = e.getAttribute("data-id");

    // 获取二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    var collapseIn = $("#comment-" + dataId).hasClass("collapse in");
    if(collapseIn) {
        // 折叠二级评论
        console.log("msg", "折叠二级评论");
        $("#comment-" + dataId).removeClass("in");
        // e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {

        var subCommentContainer = $("#comment-" + dataId);
        if(subCommentContainer.children().length != 1) {
            console.log("1")
            // 展开二级评论
            $("#comment-" + dataId).addClass("in");
            // 标记二级评论展开状态
            // e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            console.log(":2");
            // 查询评论-评论
            $.getJSON("/comment/"+dataId, function(data){
                console.log("$getJSON：" + data);

                var items = [];
                $.each(data.data.reverse(), function(index, comment) {
                    console.log("data：" + data);
                    console.log("index：" + index);
                    console.log("comment：" + comment);

                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format("YYYY-MM-DD")
                    })));

                    var mediaElement = $("<div/>", {
                       "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                   var commentElement = $("<div/>", {
                      "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                   }).append(mediaElement);
                   subCommentContainer.prepend(commentElement);// 被选元素的开头（仍位于内部）插入指定内容
                });

                /*var commentBody = $("#comment-body-" + dataId);
                var items = [];

                $.each(data.data, function(comment) {
                    var c = $("<div/>", {
                        "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                        html: comment.content
                    });
                    items.push(c);
                });



                $("<div/>", {
                    "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 sub-comments collapse",
                    "id":"comment-" + dataId,
                    html: items.join("")
                }).appendTo(commentBody);*/

                // 展开二级评论
                $("#comment-" + dataId).addClass("in");
                // 标记二级评论展开状态
                // e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }

}


function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();

    if(previous.indexOf(value) == -1) {
        if(previous) {
            $("#tag").val(previous + "," + value);
        } else {
            $("#tag").val(value);
        }
    }

}

function showSelectTag() {
    $("#select-tag").show();
}
