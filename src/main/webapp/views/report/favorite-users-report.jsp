<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="p-3">
    <!-- Filter Section -->
    <div class="mb-4 p-3 border border-warning rounded">
        <label for="videoTitleFilter" class="form-label fw-bold mb-2">VIDEO TITLE?</label>
        <div class="input-group">
            <input type="text" 
                   class="form-control border-warning" 
                   id="videoTitleFilter" 
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
                <li><a class="dropdown-item" href="#" onclick="clearFilter()">Clear</a></li>
            </ul>
        </div>
    </div>

    <!-- Table Section -->
    <div class="table-responsive p-3 rounded shadow-sm">
        <table class="table table-striped table-hover table-bordered align-middle text-center">
            <thead>
                <tr>
                    <th scope="col" class="text-uppercase text-danger fw-bold">USERNAME</th>
                    <th scope="col" class="text-uppercase text-danger fw-bold">FULLNAME</th>
                    <th scope="col" class="text-uppercase text-danger fw-bold">EMAIL</th>
                    <th scope="col" class="text-uppercase text-danger fw-bold">FAVORITE DATE</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>TeoNV</td>
                    <td>Nguyễn Văn Tèo</td>
                    <td>teonv@gmail.com</td>
                    <td>01/01/2020</td>
                </tr>
            </tbody>
        </table>
    </div>
</div>

<script>
function clearFilter() {
    document.getElementById('videoTitleFilter').value = '';
}
</script>
