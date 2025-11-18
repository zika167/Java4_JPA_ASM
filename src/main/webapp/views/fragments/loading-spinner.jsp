<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%-- 
  File: loading-spinner.jsp
  Mô tả: Loading spinner component - Reusable
  Sử dụng Bootstrap 5.3.3 Spinner
--%>
<!-- Full Page Loading Overlay -->
<div id="loadingOverlay" class="loading-overlay" style="display: none;">
    <div class="spinner-container">
        <div class="spinner-border text-warning" role="status" style="width: 3rem; height: 3rem;">
            <span class="visually-hidden">Loading...</span>
        </div>
        <p class="mt-3 text-white fw-bold" id="loadingMessage">Loading...</p>
    </div>
</div>

<style>
.loading-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.7);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 9999;
}

.spinner-container {
    text-align: center;
}
</style>
