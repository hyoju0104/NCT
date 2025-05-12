$(function(){

	// [추가] 버튼 누르면 첨부파일 추가 가능
	let i = 0;
	$("#btnAdd").click(function() {

		// input 태그의 name 은 유일해야 함 -> "upfile${i}" 로 작성해서 이름을 유일하게 지정함
		$("#files").append(`
			<div class="input-group mb-2">
			   <input class="form-control col-xs-3" type="file" name="upfile${i}"/>
			   <button type="button" class="btn btn-outline-danger" onclick="$(this).parent().remove()">삭제</button>
			</div>
		`);

		i++;

	});

	// Summernote 추가
	$("#content").summernote({
		height: 300,
	});

});