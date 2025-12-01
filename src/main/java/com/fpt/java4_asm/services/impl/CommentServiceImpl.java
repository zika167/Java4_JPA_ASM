package com.fpt.java4_asm.services.impl;

import com.fpt.java4_asm.convert.CommentConvert;
import com.fpt.java4_asm.dto.request.CommentRequest;
import com.fpt.java4_asm.dto.response.CommentResponse;
import com.fpt.java4_asm.dto.response.PaginatedResponse;
import com.fpt.java4_asm.exception.AppException;
import com.fpt.java4_asm.exception.Error;
import com.fpt.java4_asm.models.entities.Comment;
import com.fpt.java4_asm.repositories.CommentRepo;
import com.fpt.java4_asm.repositories.impl.CommentRepoImpl;
import com.fpt.java4_asm.services.CommentService;
import com.fpt.java4_asm.utils.helpers.CommentValidation;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Triển khai cụ thể của CommentService
 * 
 * Lớp này cung cấp các phương thức xử lý nghiệp vụ liên quan đến Comment
 * Bao gồm: thêm, sửa, xóa, tìm kiếm và các thao tác khác với đối tượng Comment
 */
public class CommentServiceImpl implements CommentService {
    // Repository để tương tác với database
    private final CommentRepo commentRepo = new CommentRepoImpl();
    // Converter để chuyển đổi giữa Entity và DTO
    private final CommentConvert commentConvert = new CommentConvert();

    /**
     * Tạo mới một comment
     * 
     * @param request Đối tượng chứa thông tin comment cần tạo
     * @return Đối tượng CommentResponse chứa thông tin đã tạo
     * @throws AppException Nếu có lỗi xảy ra trong quá trình tạo
     */
    @Override
    public CommentResponse create(CommentRequest request) {
        // Validate request
        CommentValidation.validateCommentRequest(request);

        try {
            // Chuyển đổi từ DTO sang Entity
            Comment comment = commentConvert.toEntity(request);
            
            // Lưu vào database
            Comment savedComment = commentRepo.save(comment);
            
            // Chuyển đổi và trả về response
            return commentConvert.toResponse(savedComment);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR,
                    "Lỗi khi tạo comment: " + e.getMessage());
        }
    }

    /**
     * Cập nhật thông tin một comment
     * 
     * @param id ID của comment cần cập nhật
     * @param request Đối tượng chứa thông tin cập nhật
     * @return Optional chứa thông tin đã cập nhật nếu thành công
     * @throws AppException Nếu không tìm thấy hoặc có lỗi khi cập nhật
     */
    @Override
    public Optional<CommentResponse> update(Long id, CommentRequest request) {
        // Validate input
        CommentValidation.validateCommentId(id);
        CommentValidation.validateCommentRequest(request);

        // Kiểm tra comment có tồn tại không
        if (!commentRepo.existsById(id)) {
            throw new AppException(Error.NOT_FOUND,
                    "Không tìm thấy comment với ID: " + id);
        }

        try {
            // Lấy đối tượng hiện tại
            Comment existingComment = commentRepo.findById(id)
                    .orElseThrow(() -> new AppException(Error.NOT_FOUND, "Không tìm thấy comment với ID: " + id));
            
            // Cập nhật thông tin từ request (user, video, content, updatedDate)
            Comment updatedCommentData = commentConvert.toEntity(existingComment, request);
            
            // Lưu cập nhật
            Comment updatedComment = commentRepo.update(updatedCommentData)
                    .orElseThrow(() -> new AppException(Error.DATABASE_ERROR, "Cập nhật thất bại"));
                    
            return Optional.of(commentConvert.toResponse(updatedComment));
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR,
                    "Lỗi khi cập nhật comment: " + e.getMessage());
        }
    }

    /**
     * Lấy thông tin một comment theo ID
     * 
     * @param id ID của comment cần lấy
     * @return Optional chứa thông tin comment nếu tìm thấy
     * @throws AppException Nếu có lỗi khi truy vấn
     */
    @Override
    public Optional<CommentResponse> getById(Long id) {
        // Validate ID
        CommentValidation.validateCommentId(id);

        try {
            Optional<Comment> comment = commentRepo.findById(id);

            // Ghi log kết quả tìm kiếm
            if (comment.isPresent()) {
                System.out.println("Đã tìm thấy comment với ID: " + id);
            } else {
                System.out.println("Không tìm thấy comment với ID: " + id);
            }

            return comment.map(commentConvert::toResponse);
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm kiếm comment ID " + id + ": " + e.getMessage());
            throw new AppException(Error.DATABASE_ERROR,
                    "Lỗi khi truy vấn thông tin comment: " + e.getMessage());
        }
    }

    /**
     * Lấy danh sách comment theo ID người dùng
     * 
     * @param userId ID của người dùng
     * @param page Số trang
     * @param size Kích thước trang
     * @return Danh sách các comment của người dùng
     * @throws AppException Nếu có lỗi khi truy vấn
     */
    @Override
    public PaginatedResponse<CommentResponse> getByUserId(String userId, int page, int size) {
        try {
            // Validate input
            if (userId == null || userId.trim().isEmpty()) {
                throw new AppException(Error.INVALID_INPUT, "ID người dùng không được để trống");
            }

            // Tìm comments theo user ID
            List<Comment> comments = commentRepo.findByUserId(userId);
            return commentConvert.toPaginatedResponse(comments);

        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR,
                "Lỗi khi tìm kiếm comment theo người dùng: " + e.getMessage());
        }
    }

    /**
     * Lấy danh sách comment theo ID video
     * 
     * @param videoId ID của video
     * @param page Số trang
     * @param size Kích thước trang
     * @return Danh sách các comment của video
     * @throws AppException Nếu có lỗi khi truy vấn
     */
    @Override
    public PaginatedResponse<CommentResponse> getByVideoId(String videoId, int page, int size) {
        try {
            // Validate input
            if (videoId == null || videoId.trim().isEmpty()) {
                throw new AppException(Error.INVALID_INPUT, "ID video không được để trống");
            }

            // Tìm comments theo video ID
            List<Comment> comments = commentRepo.findByVideoId(videoId);
            return commentConvert.toPaginatedResponse(comments);

        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR,
                "Lỗi khi tìm kiếm comment theo video: " + e.getMessage());
        }
    }

    /**
     * Lấy danh sách tất cả các comment
     * 
     * @return Danh sách các đối tượng CommentResponse
     * @throws AppException Nếu có lỗi khi truy vấn
     */
    @Override
    public List<CommentResponse> getAll() {
        try {
            List<Comment> comments = commentRepo.findAll();

            // Ghi log số lượng bản ghi
            System.out.println("Đã lấy danh sách " + comments.size() + " comment");

            if (comments.isEmpty()) {
                System.out.println("Không có dữ liệu comment nào được tìm thấy");
            }

            return commentConvert.toResponseList(comments);
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách comment: " + e.getMessage());
            throw new AppException(Error.DATABASE_ERROR,
                    "Lỗi khi truy vấn danh sách comment: " + e.getMessage());
        }
    }

    /**
     * Xóa một comment
     * 
     * @param id ID của comment cần xóa
     * @return true nếu xóa thành công, false nếu không
     * @throws AppException Nếu không tìm thấy hoặc có lỗi khi xóa
     */
    @Override
    public boolean delete(Long id) {
        // Validate ID
        CommentValidation.validateCommentId(id);

        try {
            // Kiểm tra comment có tồn tại không
            if (!commentRepo.existsById(id)) {
                throw new AppException(Error.NOT_FOUND,
                        "Không tìm thấy comment với ID: " + id);
            }

            // Thực hiện xóa
            return commentRepo.deleteById(id);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR,
                    "Lỗi khi xóa comment: " + e.getMessage());
        }
    }

    /**
     * Kiểm tra sự tồn tại của một comment
     * 
     * @param id ID của comment cần kiểm tra
     * @return true nếu tồn tại, false nếu không
     * @throws AppException Nếu có lỗi khi kiểm tra
     */
    @Override
    public boolean exists(Long id) {
        // Validate ID
        CommentValidation.validateCommentId(id);

        try {
            boolean exists = commentRepo.existsById(id);
            System.out.println("Kiểm tra tồn tại comment ID " + id + ": " + (exists ? "Có" : "Không"));
            return exists;
        } catch (Exception e) {
            System.err.println("Lỗi khi kiểm tra tồn tại comment ID " + id + ": " + e.getMessage());
            throw new AppException(Error.DATABASE_ERROR,
                    "Lỗi khi kiểm tra sự tồn tại của comment: " + e.getMessage());
        }
    }

    /**
     * Đếm tổng số comment
     * 
     * @return Tổng số comment
     * @throws AppException Nếu có lỗi khi đếm
     */
    @Override
    public long count() {
        try {
            long count = commentRepo.count();
            System.out.println("Tổng số comment hiện có: " + count);
            return count;
        } catch (Exception e) {
            System.err.println("Lỗi khi đếm số lượng comment: " + e.getMessage());
            throw new AppException(Error.DATABASE_ERROR,
                    "Lỗi khi đếm số lượng comment: " + e.getMessage());
        }
    }

    /**
     * Lấy danh sách phân trang các comment
     * 
     * @param page Số trang cần lấy (bắt đầu từ 1)
     * @param size Số lượng bản ghi trên mỗi trang
     * @return Đối tượng PaginatedResponse chứa thông tin phân trang và danh sách kết quả
     * @throws AppException Nếu có lỗi xảy ra trong quá trình xử lý
     */
    @Override
    public PaginatedResponse<CommentResponse> paginate(int page, int size) {
        try {
            // Validate tham số phân trang
            CommentValidation.validatePagination(page, size);
            
            // Điều chỉnh chỉ số trang về 0-based cho JPA
            int pageIndex = page - 1;
            
            // Lấy tổng số bản ghi
            long totalElements = commentRepo.count();
            
            // Nếu không có bản ghi nào, trả về danh sách rỗng
            if (totalElements == 0) {
                return PaginatedResponse.of(Collections.emptyList(), page, size, 0);
            }
            
            // Tính tổng số trang
            int totalPages = (int) Math.ceil((double) totalElements / size);
            
            // Điều chỉnh nếu trang yêu cầu lớn hơn tổng số trang
            if (pageIndex >= totalPages) {
                pageIndex = totalPages - 1;
            }
            
            // Lấy dữ liệu phân trang từ repository
            List<Comment> comments = commentRepo.pages(pageIndex, size);
            
            // Chuyển đổi sang DTO
            List<CommentResponse> content = commentConvert.toResponseList(comments);
            
            // Tạo và trả về đối tượng phân trang
            return PaginatedResponse.of(content, page, size, totalElements);
            
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            String errorMsg = String.format("Lỗi khi lấy danh sách phân trang (trang: %d, kích thước: %d)", page, size);
            System.err.println(errorMsg + ": " + e.getMessage());
            throw new AppException(Error.DATABASE_ERROR, e);
        }
    }
}
