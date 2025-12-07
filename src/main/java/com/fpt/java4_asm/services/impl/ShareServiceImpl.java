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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Triển khai cụ thể của ShareService
 */
public class ShareServiceImpl implements ShareService {
    private static final Logger log = LoggerFactory.getLogger(ShareServiceImpl.class);
    
    private final ShareRepo shareRepo = new ShareRepoImpl();
    private final ShareConvert shareConvert = new ShareConvert();

    @Override
    public ShareResponse create(ShareRequest request) {
        log.debug("Tạo share mới");
        ShareValidation.validateShareRequest(request);
        try {
            Share share = shareConvert.toEntity(request);
            Share savedShare = shareRepo.save(share);
            log.info("Tạo share thành công: {}", savedShare.getId());
            return shareConvert.toResponse(savedShare);
        } catch (AppException e) {
            log.warn("Tạo share thất bại: {}", e.getErrorMessage());
            throw e;
        } catch (Exception e) {
            log.error("Lỗi khi tạo share: {}", e.getMessage(), e);
            throw new AppException(Error.SHARE_CREATE_FAILED, "Lỗi khi tạo share: " + e.getMessage());
        }
    }

    @Override
    public Optional<ShareResponse> update(Integer id, ShareRequest request) {
        log.debug("Cập nhật share: {}", id);
        ShareValidation.validateShareId(id);
        ShareValidation.validateShareRequest(request);

        if (!shareRepo.existsById(id)) {
            throw new AppException(Error.SHARE_NOT_FOUND, "Không tìm thấy share với ID: " + id);
        }

        try {
            Share existingShare = shareRepo.findById(id)
                    .orElseThrow(() -> new AppException(Error.SHARE_NOT_FOUND, "Không tìm thấy share với ID: " + id));
            
            Share updatedShare = shareConvert.toEntity(existingShare, request);
            Share result = shareRepo.update(updatedShare)
                    .orElseThrow(() -> new AppException(Error.SHARE_UPDATE_FAILED, "Cập nhật thất bại"));
            
            log.info("Cập nhật share thành công: {}", id);
            return Optional.of(shareConvert.toResponse(result));
        } catch (AppException e) {
            log.warn("Cập nhật share thất bại {}: {}", id, e.getErrorMessage());
            throw e;
        } catch (Exception e) {
            log.error("Lỗi khi cập nhật share: {}", e.getMessage(), e);
            throw new AppException(Error.SHARE_UPDATE_FAILED, "Lỗi khi cập nhật share: " + e.getMessage());
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
        log.debug("Xóa share: {}", id);
        ShareValidation.validateShareId(id);
        try {
            boolean result = shareRepo.deleteById(id);
            log.info("Xóa share thành công: {}", id);
            return result;
        } catch (Exception e) {
            log.error("Lỗi khi xóa share: {}", e.getMessage(), e);
            throw new AppException(Error.SHARE_DELETE_FAILED, "Lỗi khi xóa share: " + e.getMessage());
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
            throw new AppException(Error.DATABASE_ERROR, 
                    "Lỗi khi lấy danh sách phân trang: " + e.getMessage());
        }
    }
}
