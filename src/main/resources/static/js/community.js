function post() {
    var questionId = $("#question_id").val();
    console.log('questionId = ' + questionId);

    var commentContent = $("#comment_content").val();
    console.log("commentContent = " + commentContent);

    if (!commentContent) {
        alert("请填写回复内容");
        return;
    }

    $.ajax({
        type: 'POST',
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId":questionId,
            "content":commentContent,
            "type":1
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