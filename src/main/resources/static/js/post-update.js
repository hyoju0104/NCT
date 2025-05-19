$(function() {

	console.log("post-update.js loaded");

	// 새 파일 추가
	let i = 0;
	$('#btnAdd').click(() => {
		$('#files').append(`
      <div class="input-group mb-2">
        <input class="form-control" type="file" name="upfile${i}"/>
        <button type="button" class="btn btn-outline-danger remove-new-file">
          삭제
        </button>
      </div>`);
		i++;
	});

	// 새로 추가된 파일 삭제
	$('#files').on('click', '.remove-new-file', function () {
		$(this).closest('.input-group').remove();
	});

	// 기존 첨부파일 삭제 (항상 document 에 위임)
	$('#updateForm').on('click', '[data-fileid-del]', function () {
		const fileId = $(this).attr('data-fileid-del');
		console.log('▶ delete clicked:', fileId);
		// 히든 필드 추가

		$('#delFiles').append(
			`<input type="hidden" name="delFile" value="${fileId}">`
		);
		$(this).closest('.input-group').remove();
		console.log('▶ #delFiles now has:', $('#delFiles').html());
	});

	// Summernote 초기화
	$('#content').summernote({
		height: 300
	});

});
