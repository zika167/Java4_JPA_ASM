// User Management JavaScript

document.addEventListener('DOMContentLoaded', function() {
    // Form validation
    const userForm = document.getElementById('userForm');
    if (userForm) {
        userForm.addEventListener('submit', function(e) {
            const action = e.submitter.value;
            
            if (action === 'delete') {
                if (!confirm('Bạn có chắc chắn muốn xóa user này?')) {
                    e.preventDefault();
                    return false;
                }
            }

            // Validate required fields for update
            if (action === 'update') {
                const username = document.querySelector('input[name="username"]').value.trim();
                const fullname = document.querySelector('input[name="fullname"]').value.trim();
                const email = document.querySelector('input[name="email"]').value.trim();

                if (!username || !fullname || !email) {
                    alert('Vui lòng điền đầy đủ Username, Fullname và Email!');
                    e.preventDefault();
                    return false;
                }
            }
        });
    }
});

// Edit User Function
function editUser(userId) {
    // Switch to Edition tab
    const editionTab = document.getElementById('edition-tab');
    const editionTabPane = new bootstrap.Tab(editionTab);
    editionTabPane.show();

    // Fetch user data and populate form
    fetch(`/admin/users/get?id=${userId}`)
        .then(response => response.json())
        .then(data => {
            document.querySelector('input[name="username"]').value = data.id;
            document.querySelector('input[name="fullname"]').value = data.fullname;
            document.querySelector('input[name="email"]').value = data.email;
            document.querySelector('input[name="password"]').value = '';

            // Scroll to top
            window.scrollTo({ top: 0, behavior: 'smooth' });
        })
        .catch(error => {
            console.error('Error fetching user data:', error);
            alert('Không thể tải thông tin user!');
        });

    return false;
}

// Reset form when switching tabs
document.querySelectorAll('[data-bs-toggle="tab"]').forEach(tab => {
    tab.addEventListener('shown.bs.tab', function(e) {
        if (e.target.id === 'edition-tab') {
            const form = document.getElementById('userForm');
            if (form && !form.dataset.editing) {
                form.reset();
            }
        }
    });
});
