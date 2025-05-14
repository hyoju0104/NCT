$(function(){

	// 글 [삭제] 버튼
	$("#btnDel").click(function(){
		confirm("삭제하시겠습니까?") && $("form[name='frmDelete']").submit();
	});

	// 현재 글의 id 값
	const id = $("#postId").val().trim();

	// 현재 글의 댓글을 불러온다
	loadComment(id);

	// 댓글 작성 버튼 누르면 댓글 등록 하기.
	$("#btn_comment").click(function(){
		const content = $("#input_comment").val().trim();

		// 검증
		if(!content){
			alert("댓글 입력을 하세요");
			$("#input_comment").focus();
			return;
		}

		// 로그인 여부 확인
		if (!logged_id) {
			alert("로그인 후 댓글을 작성할 수 있습니다.");
			return;
		}

		// 전달할 parameter 들 준비
		const data = {
			"post_id": id,
			"user_id": logged_id,
			"content": content
		};

		console.log(data);

		fetch("/comment/write", {
			method: "POST",
			headers: {
				"Content-Type": "application/x-www-form-urlencoded",
			},
			body: `post_id=${id}&user_id=${logged_id}&content=${encodeURIComponent(content)}`,
		}).then(response => response.json())
			.then(data => {
				if(data.status !== "OK"){
					alert(data.status);
					return;
				}
				loadComment(id);

				$("#input_comment").val('');
			})
			.catch(err => console.error("fetch 실패:", err))
		;

	});

});


// 특정 글(post_id) 의 댓글 목록 읽어오기
function loadComment(post_id){

	fetch("/comment/list/" + post_id)
		.then(response => response.json())
		.then(data => {
			if(data.status !== "OK"){
				alert(data.status);
				return;
			}

			buildComment(data);   // 댓글 화면 렌더링.

			// ⭐ 댓글 목록을 불러오고 난 뒤에 삭제에 대한 이벤트 리스너를 등록해야 한다
			addDelete();
		})

}


function buildComment(result){
	$("#cmt_cnt").text(result.count);

	// 댓글이 0개면 별도 메시지 출력 후 함수 종료
	if (result.count === 0) {
		$("#cmt_list").html(`
            <tr class="no-hover">
                <td colspan="3" class="text-center text-secondary">댓글이 없습니다</td>
            </tr>
        `);
		return;
	}

	const out = [];

	result.data.forEach(comment => {
		let id = comment.id;
		let content = comment.content.trim();
		let regdate = comment.regdate;
		let user_id = parseInt(comment.user.id);
		let username = comment.user.username;
		let name = comment.user.name;


		// 삭제 버튼 여부 : 작성자 본인인 경우만 삭제 버튼 보이게 하기.
		// logged_id가 존재하고, 댓글 작성자와 동일한 경우에만 삭제 버튼 표시
		const delBtn = (logged_id && logged_id === user_id) ? `
            <i class="btn fa-solid fa-delete-left text-danger" data-bs-toggle="tooltip"
                data-cmtdel-id="${id}" title="삭제"></i>
        ` : '';

		const row = `
        <tr>
            <td><span><strong>${username}</strong><br><small class="text-secondary">(${name})</small></span></td>
            <td>
                <span>${content}</span>
                ${delBtn}
            </td>
            <td><span><small class="text-secondary">${regdate}</small></span></td>
        </tr>
        `;

		out.push(row);
	});

	$("#cmt_list").html(out.join("\n"));

} // end buildComment();


// 댓글 삭제 버튼이 눌렸을 때. 해당 댓글 삭제하는 이벤트를 삭제 버튼에 등록
function addDelete(){
	// 현재 글의 id (댓글 삭제 후에 다시 댓글 목록 불러와야 하기 때문에 필요하다)
	const id = $("input[name='id']").val().trim();

	$("[data-cmtdel-id]").click(function(){
		if(!confirm("댓글을 삭제하시겠습니까?")) return;

		// 로그인 여부 확인
		if (!logged_id) {
			alert("로그인 후 댓글을 삭제할 수 있습니다.");
			return;
		}

		// 삭제할 댓글의 id
		const comment_id = $(this).attr("data-cmtdel-id");

		fetch("/comment/delete", {
			method: "POST",
			headers: {
				"Content-Type": "application/x-www-form-urlencoded",
			},
			body: `id=${comment_id}`,
		}).then(response => response.json())
			.then(data => {
				if(data.status !== "OK"){
					alert(data.status);
					return;
				}
				// 삭제 후에도 다시 댓글 목록 불러와서 업데이트 해야 함.
				loadComment(id);
			})
	});

}