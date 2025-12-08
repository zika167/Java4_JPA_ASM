package com.fpt.java4_asm.services.impl;

import com.fpt.java4_asm.dto.request.UserRequest;
import com.fpt.java4_asm.dto.response.PaginatedResponse;
import com.fpt.java4_asm.dto.response.UserResponse;
import com.fpt.java4_asm.exception.AppException;
import com.fpt.java4_asm.exception.Error;
import com.fpt.java4_asm.models.entities.User;
import com.fpt.java4_asm.repositories.UserRepo;
import com.fpt.java4_asm.repositories.impl.UserRepoImpl;
import com.fpt.java4_asm.services.UserService;
import com.fpt.java4_asm.convert.UserConvert;
import com.fpt.java4_asm.utils.helpers.UserValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Triển khai cụ thể của UserService
 * Xử lý các nghiệp vụ liên quan đến User: CRUD, tìm kiếm, phân trang
 * 
 * @author Java4 ASM
 */
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    
    // Repository để thao tác với database
    private final UserRepo userRepo = new UserRepoImpl();
    // Converter để chuyển đổi giữa Entity và DTO
    private final UserConvert userConvert = new UserConvert();

    /**
     * Tạo user mới
     * 
     * @param request Thông tin user cần tạo
     * @return UserResponse chứa thông tin user đã tạo
     * @throws AppException nếu có lỗi khi tạo user
     */
    @Override
    public UserResponse create(UserRequest request) {
        UserValidation.validateCreateUserRequest(request);
        log.debug("Tạo user mới: {}", request.getEmail());
        try {
            User user = userConvert.toEntity(request);
            User savedUser = userRepo.save(user);
            log.info("Tạo user thành công: {}", savedUser.getId());
            return userConvert.toResponse(savedUser);
        } catch (AppException e) {
            log.warn("Tạo user thất bại: {}", e.getErrorMessage());
            throw e;
        } catch (Exception e) {
            log.error("Lỗi tạo user: " + e.getMessage(), e);
            throw new AppException(Error.USER_CREATE_FAILED, "Lỗi tạo user: " + e.getMessage());
        }
    }

    /**
     * Cập nhật thông tin user
     * 
     * @param id ID của user cần cập nhật
     * @param request Thông tin mới cần cập nhật
     * @return Optional chứa UserResponse nếu cập nhật thành công
     * @throws AppException nếu user không tồn tại hoặc email đã được sử dụng
     */
    @Override
    public Optional<UserResponse> update(String id, UserRequest request) {
        log.debug("Cập nhật user: {}", id);
        UserValidation.validateUserId(id);
        UserValidation.validateUpdateUserRequest(request);

        try {
            Optional<User> existingUser = userRepo.findById(id);
            if (existingUser.isEmpty()) {
                throw new AppException(Error.USER_NOT_FOUND, "Không tìm thấy User với ID: " + id);
            }

            User user = existingUser.get();

            if (!user.getEmail().equals(request.getEmail()) && 
                userRepo.findByEmail(request.getEmail()).isPresent()) {
                throw new AppException(Error.USER_EMAIL_EXISTS, "Email đã tồn tại");
            }

            userConvert.toEntity(user, request);
            Optional<User> updatedUser = userRepo.update(user);
            log.info("Cập nhật user thành công: {}", id);
            return updatedUser.map(userConvert::toResponse);
        } catch (AppException e) {
            log.warn("Cập nhật user thất bại {}: {}", id, e.getErrorMessage());
            throw e;
        } catch (Exception e) {
            log.error("Lỗi cập nhật user: " + e.getMessage(), e);
            throw new AppException(Error.USER_UPDATE_FAILED, "Lỗi cập nhật user: " + e.getMessage());
        }
    }

    /**
     * Lấy thông tin user theo ID
     * 
     * @param id ID của user cần tìm
     * @return Optional chứa UserResponse nếu tìm thấy
     */
    @Override
    public Optional<UserResponse> getById(String id) {
        UserValidation.validateUserId(id);

        try {
            Optional<User> user = userRepo.findById(id);
            return user.map(userConvert::toResponse);
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi lấy user: " + e.getMessage());
        }
    }

    /**
     * Lấy thông tin user theo email
     * 
     * @param email Email của user cần tìm
     * @return Optional chứa UserResponse nếu tìm thấy
     */
    @Override
    public Optional<UserResponse> getByEmail(String email){
        UserValidation.validateEmailFormat(email);

        try {
            Optional<User> user = userRepo.findByEmail(email);
            return user.map(userConvert::toResponse);
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi lấy user: " + e.getMessage());
        }
    }

    /**
     * Lấy danh sách tất cả user
     * 
     * @return Danh sách UserResponse
     */
    @Override
    public List<UserResponse> getAll() {
        try {
            List<User> users = userRepo.findAll();
            return userConvert.toResponseList(users);
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi lấy danh sách user: " + e.getMessage());
        }
    }

    /**
     * Xóa user theo ID
     * 
     * @param id ID của user cần xóa
     * @return true nếu xóa thành công
     * @throws AppException nếu user không tồn tại
     */
    @Override
    public boolean delete(String id) {
        log.debug("Xóa user: {}", id);
        UserValidation.validateUserId(id);

        try {
            if (!userRepo.existsById(id)) {
                throw new AppException(Error.USER_NOT_FOUND, "Không tìm thấy User với ID: " + id);
            }
            boolean result = userRepo.deleteById(id);
            log.info("Xóa user thành công: {}", id);
            return result;
        } catch (AppException e) {
            log.warn("Xóa user thất bại {}: {}", id, e.getErrorMessage());
            throw e;
        } catch (Exception e) {
            log.error("Lỗi xóa user: " + e.getMessage(), e);
            throw new AppException(Error.USER_DELETE_FAILED, "Lỗi xóa user: " + e.getMessage());
        }
    }

    /**
     * Kiểm tra user có tồn tại theo ID
     * 
     * @param id ID của user cần kiểm tra
     * @return true nếu tồn tại, false nếu không
     */
    @Override
    public boolean exists(String id) {
        UserValidation.validateUserId(id);

        try {
            return userRepo.existsById(id);
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi kiểm tra user: " + e.getMessage());
        }
    }

    /**
     * Đếm tổng số user trong hệ thống
     * 
     * @return Tổng số user
     */
    @Override
    public long count() {
        try {
            return userRepo.count();
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi đếm user: " + e.getMessage());
        }
    }

    /**
     * Lấy danh sách user theo phân trang
     * 
     * @param page Số trang (bắt đầu từ 1)
     * @param size Số lượng bản ghi mỗi trang
     * @return PaginatedResponse chứa danh sách user và thông tin phân trang
     */
    @Override
    public PaginatedResponse<UserResponse> paginate(int page, int size) {
        try {
            UserValidation.validatePagination(page, size);
            
            // Chuyển đổi page từ 1-based sang 0-based cho JPA
            int pageIndex = page - 1;
            long totalElements = userRepo.count();
            
            if (totalElements == 0) {
                return PaginatedResponse.of(Collections.emptyList(), page, size, 0);
            }
            
            int totalPages = (int) Math.ceil((double) totalElements / size);
            
            // Điều chỉnh nếu trang yêu cầu vượt quá tổng số trang
            if (pageIndex >= totalPages) {
                pageIndex = totalPages - 1;
            }
            
            List<User> users = userRepo.pages(pageIndex, size);
            List<UserResponse> content = userConvert.toResponseList(users);
            
            return PaginatedResponse.of(content, page, size, totalElements);
            
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, 
                    "Lỗi khi lấy danh sách phân trang: " + e.getMessage());
        }
    }
}
