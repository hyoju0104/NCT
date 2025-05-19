// File: src/main/resources/static/js/PostUpdate.js

$(function(){
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
	$('#files').on('click', '.remove-new-file', function(){
		$(this).closest('.input-group').remove();
	});

	// 기존 첨부파일 삭제 (id 있는 것들)
	$("form[name='frm']").on('click', '[data-fileid-del]', function(){
		const fileId = $(this).data('fileid-del');
		deleteFiles(fileId);
		$(this).closest('.input-group').remove();
	});

	// Summernote 초기화
	$('#content').summernote({
		height: 300
	});
});

function deleteFiles(fileId){
	$('#delFiles').append(
		`<input type="hidden" name="delFile" value="${fileId}">`
	);
}
