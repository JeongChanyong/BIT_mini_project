// (1) 회원정보 수정
function update(userid, event) {
    event.preventDefault() // 폼태그 액션 진행 되지 않는다

    let data = $("#profileUpdate").serialize(); // 폼 태그에 있는 내용을 key=value 로 뽑아낼때

    $.ajax({
        type: "PUT",
        url: `http://localhost:8080/api/user/${userid}`,
        data: data,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json",

    }).done(res => { // HTTP Status 상태코드가 200번대 일 때, done 이 실행된다.
        console.log("성공", res);
        location.href = `/user/${userid}`;
    }).fail(error => { // HTTP Status 상태코드가 200번대가 아닐 때, fail 이 실행된다.
        if (error.data == null) { // message만 응답
            alert(error.responseJSON.message);
        } else { // errorMap이 포함된 응답
            alert("회원정보 수정에 실패하였습니다. 원인 : " +
                JSON.stringify((error.responseJSON.data)));
        }
    });
}