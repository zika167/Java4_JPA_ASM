<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%-- 
  File: video-preview-modal.jsp
  Mô tả: Video preview modal component - Reusable
  Sử dụng Bootstrap 5.3.3 Modal
--%>
<!-- Video Preview Modal -->
<div class="modal fade" id="videoPreviewModal" tabindex="-1" aria-labelledby="videoPreviewModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-xl modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header bg-dark text-white">
                <h5 class="modal-title" id="videoPreviewModalLabel">
                    <i class="fas fa-play-circle me-2"></i>
                    <span id="videoPreviewTitle">Video Preview</span>
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body p-0">
                <div class="ratio ratio-16x9">
                    <iframe id="videoPreviewFrame" src="" title="Video player" allowfullscreen></iframe>
                </div>
            </div>
            <div class="modal-footer">
                <div class="d-flex justify-content-between w-100">
                    <div>
                        <button type="button" class="btn btn-primary" id="videoLikeBtn">
                            <i class="fas fa-thumbs-up me-1"></i> Like
                        </button>
                        <button type="button" class="btn btn-warning" id="videoShareBtn">
                            <i class="fas fa-share me-1"></i> Share
                        </button>
                    </div>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        <i class="fas fa-times me-1"></i> Close
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
