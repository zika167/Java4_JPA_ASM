
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Modal -->
<div class="modal fade" id="sendVideoModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">
                    <i class="fas fa-share-alt"></i> Chia sẻ video cho bạn bè
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            
            <form id="sendVideoForm" method="POST" action="${pageContext.request.contextPath}/send-video">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="friendEmail" class="form-label">Email của bạn bè</label>
                        <input type="email" class="form-control" id="friendEmail" name="friendEmail" 
                               placeholder="Nhập email bạn bè" required>
                        <div class="error-message">Vui lòng nhập email hợp lệ</div>
                    </div>
                </div>
                
                <div class="modal-footer">
                    <button type="button" class="btn btn-cancel" data-bs-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-paper-plane"></i> Gửi
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    document.getElementById('sendVideoForm').addEventListener('submit', async function(e) {
        e.preventDefault();
        
        const form = this;
        const submitBtn = form.querySelector('button[type="submit"]');
        const originalBtnContent = submitBtn.innerHTML;
        const friendEmail = document.getElementById('friendEmail').value;
        
        try {
            submitBtn.disabled = true;
            submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Đang gửi...';
            
            // Simulate loading delay
            await new Promise(resolve => setTimeout(resolve, 1000));
            
            // For demo: just show success
            alert('Chia sẻ video đến ' + friendEmail + ' thành công!');
            bootstrap.Modal.getInstance(document.getElementById('sendVideoModal')).hide();
            form.reset();
        } catch (error) {
            console.error('Error:', error);
            alert('Có lỗi xảy ra');
        } finally {
            submitBtn.disabled = false;
            submitBtn.innerHTML = originalBtnContent;
        }
    });
    
    // Validate email on blur
    document.getElementById('friendEmail').addEventListener('blur', function() {
        const formGroup = this.closest('.form-group');
        const value = this.value.trim();
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        
        if (value && !emailRegex.test(value)) {
            formGroup.classList.add('error');
        } else {
            formGroup.classList.remove('error');
        }
    });
    
    // Clear error on input
    document.getElementById('friendEmail').addEventListener('input', function() {
        const formGroup = this.closest('.form-group');
        formGroup.classList.remove('error');
    });
</script>
