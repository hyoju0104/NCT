function previewImage() {
    const preview = document.getElementById('preview');
    const placeholder = document.querySelector('.image-preview-placeholder');
    const file = document.getElementById('file').files[0];
    const reader = new FileReader();

    reader.onloadend = function() {
        preview.src = reader.result;
        preview.style.display = 'block';
        placeholder.style.display = 'none';
    }

    if (file) {
        reader.readAsDataURL(file);
    } else {
        preview.src = '';
        preview.style.display = 'none';
        placeholder.style.display = 'flex';
    }
}