// Video Management JavaScript

// Poster Upload Preview
document.addEventListener('DOMContentLoaded', function() {
    const posterFile = document.getElementById('posterFile');
    const posterImage = document.getElementById('posterImage');
    const posterPlaceholder = document.getElementById('posterPlaceholder');
    const posterInput = document.getElementById('posterInput');

    if (posterFile) {
        posterFile.addEventListener('change', function(e) {
            const file = e.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function(event) {
                    posterImage.src = event.target.result;
                    posterImage.style.display = 'block';
                    posterPlaceholder.style.display = 'none';
                    posterInput.value = file.name;
                };
                reader.readAsDataURL(file);
            }
        });
    }

    // Click on poster preview to trigger file input
    const posterPreview = document.getElementById('posterPreview');
    if (posterPreview) {
        posterPreview.addEventListener('click', function() {
            posterFile.click();
        });
    }

    // Form validation
    const videoForm = document.getElementById('videoForm');
    if (videoForm) {
        videoForm.addEventListener('submit', function(e) {
            const action = e.submitter.value;
            
            if (action === 'delete') {
                if (!confirm('Bạn có chắc chắn muốn xóa video này?')) {
                    e.preventDefault();
                    return false;
                }
            }

            // Validate required fields for create/update
            if (action === 'create' || action === 'update') {
                const youtubeId = document.querySelector('input[name="id"]').value.trim();
                const title = document.querySelector('input[name="title"]').value.trim();

                if (!youtubeId || !title) {
                    alert('Vui lòng điền đầy đủ YouTube ID và Video Title!');
                    e.preventDefault();
                    return false;
                }
            }
        });
    }
});

// Edit Video Function
function editVideo(videoId) {
    // Switch to Edition tab
    const editionTab = document.getElementById('edition-tab');
    const editionTabPane = new bootstrap.Tab(editionTab);
    editionTabPane.show();

    // Fetch video data and populate form
    // This should be implemented with AJAX call to backend
    fetch(`/admin/videos/get?id=${videoId}`)
        .then(response => response.json())
        .then(data => {
            document.querySelector('input[name="id"]').value = data.id;
            document.querySelector('input[name="title"]').value = data.title;
            document.querySelector('input[name="views"]').value = data.views;
            document.querySelector('textarea[name="description"]').value = data.description || '';
            
            if (data.poster) {
                document.getElementById('posterImage').src = data.poster;
                document.getElementById('posterImage').style.display = 'block';
                document.getElementById('posterPlaceholder').style.display = 'none';
            }

            if (data.active) {
                document.getElementById('activeTrue').checked = true;
            } else {
                document.getElementById('activeFalse').checked = true;
            }

            // Scroll to top
            window.scrollTo({ top: 0, behavior: 'smooth' });
        })
        .catch(error => {
            console.error('Error fetching video data:', error);
            alert('Không thể tải thông tin video!');
        });

    return false;
}

// Reset form when switching tabs
document.querySelectorAll('[data-bs-toggle="tab"]').forEach(tab => {
    tab.addEventListener('shown.bs.tab', function(e) {
        if (e.target.id === 'edition-tab') {
            // Reset form when switching to edition tab
            const form = document.getElementById('videoForm');
            if (form && !form.dataset.editing) {
                form.reset();
                document.getElementById('posterImage').style.display = 'none';
                document.getElementById('posterPlaceholder').style.display = 'block';
            }
        }
    });
});
