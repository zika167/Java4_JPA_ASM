<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="p-3">
    <!-- Filter Section -->
    <div class="mb-4 p-3 border border-warning rounded">
        <label for="videoTitleFilterShared" class="form-label fw-bold mb-2">VIDEO TITLE?</label>
        <div class="input-group">
            <input type="text" 
                   class="form-control border-warning" 
                   id="videoTitleFilterShared" 
                   name="videoTitle" 
                   value="Lâu ghê mới gặp" 
                   placeholder="Nhập tiêu đề video...">
            <button class="btn btn-outline-warning dropdown-toggle" 
                    type="button" 
                    data-bs-toggle="dropdown" 
                    aria-expanded="false">
                <span class="visually-hidden">Dropdown</span>
            </button>
            <ul class="dropdown-menu dropdown-menu-end">
                <li><a class="dropdown-item" href="#" onclick="clearFilterShared()">Clear</a></li>
            </ul>
        </div>
    </div>

    <!-- Table Section -->
    <div class="table-responsive p-3 rounded shadow-sm">
        <table class="table table-striped table-hover table-bordered align-middle text-center">
            <thead>
                <tr>
                    <th scope="col" class="text-uppercase text-danger fw-bold">SENDER NAME</th>
                    <th scope="col" class="text-uppercase text-danger fw-bold">SENDER EMAIL</th>
                    <th scope="col" class="text-uppercase text-danger fw-bold">RECEIVER EMAIL</th>
                    <th scope="col" class="text-uppercase text-danger fw-bold">SENT DATE</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Nguyễn Văn Tèo</td>
                    <td>teonv@gmail.com</td>
                    <td>poly@gmail.com</td>
                    <td>01/01/2020</td>
                </tr>
            </tbody>
        </table>
    </div>
</div>

<script>
function clearFilterShared() {
    document.getElementById('videoTitleFilterShared').value = '';
}
</script>
