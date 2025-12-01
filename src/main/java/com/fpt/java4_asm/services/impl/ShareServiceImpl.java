package com.fpt.java4_asm.services.impl;

import com.fpt.java4_asm.convert.ShareConvert;
import com.fpt.java4_asm.dto.request.ShareRequest;
import com.fpt.java4_asm.dto.response.ShareResponse;
import com.fpt.java4_asm.dto.response.PaginatedResponse;
import com.fpt.java4_asm.exception.AppException;
import com.fpt.java4_asm.exception.Error;
import com.fpt.java4_asm.models.entities.Share;
import com.fpt.java4_asm.repositories.ShareRepo;
import com.fpt.java4_asm.repositories.impl.ShareRepoImpl;
import com.fpt.java4_asm.services.ShareService;
import com.fpt.java4_asm.utils.helpers.ShareValidation;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Triển khai cụ thể của ShareService
 * 
 * Lớp này cung cấp các phương thức xử lý nghiệp vụ liên quan đến Share
 * Bao gồm: thêm, sửa, xóa, tìm kiếm và các thao tác khác với đối tượng Share
 */
public class ShareServiceImpl implements ShareService {
    // Repository để tương tác với database
    private final ShareRepo shareRepo = new ShareRepoImpl();
    // Converter để chuyển đổi giữa Entity và DTO
    private final ShareConvert shareConvert = new ShareConvert();

    @Override
    public ShareResponse create(ShareRequest request) {
        ShareValidation.validateShareRequest(request);
        try {
            Share share = shareConvert.toEntity(request);
            Share savedShare = shareRepo.save(share);
            return shareConvert.toResponse(savedShare);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi tạo share: " + e.getMessage());
        }
    }

    @Override
    public Optional<ShareResponse> update(Integer id, ShareRequest request) {
        ShareValidation.validateShareId(id);
        ShareValidation.validateShareRequest(request);

        if (!shareRepo.existsById(id)) {
            throw new AppException(Error.NOT_FOUND, "Không tìm thấy share với ID: " + id);
        }

        try {
            Share existingShare = shareRepo.findById(id)
                    .orElseThrow(() -> new AppException(Error.NOT_FOUND, "Không tìm thấy share với ID: " + id));
            
            Share updatedShare = shareConvert.toEntity(existingShare, request);
            Share result = shareRepo.update(updatedShare)
                    .orElseThrow(() -> new AppException(Error.DATABASE_ERROR, "Cập nhật thất bại"));
                    
            return Optional.of(shareConvert.toResponse(result));
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi cập nhật share: " + e.getMessage());
        }
    }

    @Override
    public Optional<ShareResponse> getById(Integer id) {
        ShareValidation.validateShareId(id);
        try {
            return shareRepo.findById(id).map(shareConvert::toResponse);
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi lấy share: " + e.getMessage());
        }
    }

    @Override
    public PaginatedResponse<ShareResponse> getByUserId(String userId, int page, int size) {
        try {
            ShareValidation.validatePagination(page, size);
            int pageIndex = page - 1;
            long totalElements = shareRepo.count();
            
            if (totalElements == 0) {
                return PaginatedResponse.of(Collections.emptyList(), page, size, 0);
            }
            
            int totalPages = (int) Math.ceil((double) totalElements / size);
            if (pageIndex >= totalPages) {
                pageIndex = totalPages - 1;
            }
            
            List<Share> shares = shareRepo.findByUserId(userId);
            List<ShareResponse> content = shareConvert.toResponseList(shares);
            
            return PaginatedResponse.of(content, page, size, totalElements);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi lấy share theo userId: " + e.getMessage());
        }
    }

    @Override
    public PaginatedResponse<ShareResponse> getByVideoId(String videoId, int page, int size) {
        try {
            ShareValidation.validatePagination(page, size);
            int pageIndex = page - 1;
            long totalElements = shareRepo.count();
            
            if (totalElements == 0) {
                return PaginatedResponse.of(Collections.emptyList(), page, size, 0);
            }
            
            int totalPages = (int) Math.ceil((double) totalElements / size);
            if (pageIndex >= totalPages) {
                pageIndex = totalPages - 1;
            }
            
            List<Share> shares = shareRepo.findByVideoId(videoId);
            List<ShareResponse> content = shareConvert.toResponseList(shares);
            
            return PaginatedResponse.of(content, page, size, totalElements);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi lấy share theo videoId: " + e.getMessage());
        }
    }

    @Override
    public List<ShareResponse> getAll() {
        try {
            return shareConvert.toResponseList(shareRepo.findAll());
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi lấy danh sách share: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(Integer id) {
        ShareValidation.validateShareId(id);
        try {
            return shareRepo.deleteById(id);
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi xóa share: " + e.getMessage());
        }
    }

    @Override
    public boolean exists(Integer id) {
        try {
            return shareRepo.existsById(id);
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi kiểm tra share: " + e.getMessage());
        }
    }

    @Override
    public long count() {
        try {
            return shareRepo.count();
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi đếm share: " + e.getMessage());
        }
    }

    @Override
    public PaginatedResponse<ShareResponse> paginate(int page, int size) {
        try {
            ShareValidation.validatePagination(page, size);
            int pageIndex = page - 1;
            long totalElements = shareRepo.count();
            
            if (totalElements == 0) {
                return PaginatedResponse.of(Collections.emptyList(), page, size, 0);
            }
            
            int totalPages = (int) Math.ceil((double) totalElements / size);
            if (pageIndex >= totalPages) {
                pageIndex = totalPages - 1;
            }
            
            List<Share> shares = shareRepo.pages(pageIndex, size);
            List<ShareResponse> content = shareConvert.toResponseList(shares);
            
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
