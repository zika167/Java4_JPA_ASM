<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%-- 
  File: confirmation-modal.jsp
  Mô tả: Confirmation modal component - Reusable
  Sử dụng Bootstrap 5.3.3 Modal
--%>
<!-- Confirmation Modal -->
<div class="modal fade" id="confirmationModal" tabindex="-1" aria-labelledby="confirmationModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header border-0">
                <h5 class="modal-title" id="confirmationModalLabel">
                    <i class="fas fa-question-circle text-warning me-2"></i>
                    Confirm Action
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p id="confirmationMessage">Are you sure you want to proceed?</p>
            </div>
            <div class="modal-footer border-0">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                    <i class="fas fa-times me-1"></i> Cancel
                </button>
                <button type="button" class="btn btn-warning" id="confirmButton">
                    <i class="fas fa-check me-1"></i> Confirm
                </button>
            </div>
        </div>
    </div>
</div>
