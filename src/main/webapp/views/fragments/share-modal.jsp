<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%-- 
  File: share-modal.jsp
  Mô tả: Share modal component - Reusable
  Sử dụng Bootstrap 5.3.3 Modal
--%>
<!-- Share Modal -->
<div class="modal fade" id="shareModal" tabindex="-1" aria-labelledby="shareModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header bg-warning">
                <h5 class="modal-title text-white fw-bold" id="shareModalLabel">
                    <i class="fas fa-share-alt me-2"></i>
                    Share Video
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="shareForm">
                    <input type="hidden" id="shareVideoId" name="videoId">
                    
                    <div class="mb-3">
                        <label for="shareEmail" class="form-label fw-semibold">
                            <i class="fas fa-envelope me-1"></i> Friend's Email
                        </label>
                        <input type="email" class="form-control" id="shareEmail" name="email" 
                               placeholder="Enter email address" required>
                    </div>

                    <div class="mb-3">
                        <label for="shareMessage" class="form-label fw-semibold">
                            <i class="fas fa-comment me-1"></i> Message (Optional)
                        </label>
                        <textarea class="form-control" id="shareMessage" name="message" rows="3"
                                  placeholder="Add a personal message..."></textarea>
                    </div>

                    <div class="alert alert-info d-flex align-items-center" role="alert">
                        <i class="fas fa-info-circle me-2"></i>
                        <div>Your friend will receive an email with a link to this video.</div>
                    </div>
                </form>
            </div>
            <div class="modal-footer border-0">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                    <i class="fas fa-times me-1"></i> Cancel
                </button>
                <button type="button" class="btn btn-warning" id="sendShareBtn">
                    <i class="fas fa-paper-plane me-1"></i> Send
                </button>
            </div>
        </div>
    </div>
</div>
