// (1) 회원정보 수정
function update(userid, event) {
    event.preventDefault() // 폼태그 액션 진행 되지 않는다

    let data = $("#profileUpdate").serialize();

    // function update(userid, event) {
    //     // event.preventDefault(); // form태그의 action경로를 비활성화 시킨다.
    //     let data = $("#profileUpdate").serialize();
    //     console.log(data);
    //
    //     $.ajax({
    //         type: "put",
    //         url: `http://localhost:8080/api/user/${userid}`,
    //         data: data,
    //         contentType: "application/x-www-form-urlencoded; charset=utf-8",
    //         dataType: "json"
    //     }).done(res => { // HTTP Status 상태코드가 200번대 일 때, done이 실행된다.
    //         console.log("update 성공", res);
    //         alert("회원정보가 성공적으로 수정되었습니다.");
    //         // location.href = `/user/${userId}`;
    //     }).fail(error => { // HTTP Status 상태코드가 200번대가 아닐 때, fail이 실행된다.
    //         // if (error.data == null) { // message만 응답
    //             // alert(error.responseJSON.message);
    //         // } else { // errorMap이 포함된 응답
    //         //     alert("회원정보 수정에 실패하였습니다. 원인 : " +
    //                 // JSON.stringify((error.responseJSON.data)));
    //         // }
    //     });
    // }



    $.ajax({
        type: "PUT",
        url: `http://localhost:8080/api/user/${userid}`,
        data: data,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json",

    // $.ajax({
    //     // object 영역
    //     type:"put",
    //     url: `/api/user/${userid}`,
    //     // url: `http://localhost/api/user/${userid}`,
    //     contentType:"application/x-www-form-urlencoded; charset=utf-8",
    //     // contentType:"application/json;charset=UTF-8",
    //     data : data,
    //     // dataType:"json"
    //     dataType:"text/plain"

    }).done(res => { // HTTP Status 상태코드가 200번대 일 때, done 이 실행된다.
        console.log("성공", res);
        location.href = `/user/${userid}`;
    }).fail(error => { // HTTP Status 상태코드가 200번대가 아닐 때, fail 이 실행된다.
        if (error.data == null) {
            alert(error.responseJSON.message)
        } else {
            alert(JSON.stringify(error.responseJSON.data));
        }
    });




}