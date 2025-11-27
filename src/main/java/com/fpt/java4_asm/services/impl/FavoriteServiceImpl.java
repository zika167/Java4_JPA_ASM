package com.fpt.java4_asm.services.impl;

import com.fpt.java4_asm.convert.FavoriteConvert;
import com.fpt.java4_asm.dto.request.FavoriteRequest;
import com.fpt.java4_asm.dto.response.FavoriteResponse;
import com.fpt.java4_asm.dto.response.PaginatedResponse;
import com.fpt.java4_asm.exception.AppException;
import com.fpt.java4_asm.exception.Error;
import com.fpt.java4_asm.models.entities.Favorite;
import com.fpt.java4_asm.repositories.FavoriteRepo;
import com.fpt.java4_asm.repositories.impl.FavoriteRepoImpl;
import com.fpt.java4_asm.services.FavoriteService;
import com.fpt.java4_asm.utils.helpers.FavoriteValidation;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Triển khai cụ thể của FavoriteService
 * 
 * Lớp này cung cấp các phương thức xử lý nghiệp vụ liên quan đến Favorite
 * Bao gồm: thêm, sửa, xóa, tìm kiếm và các thao tác khác với đối tượng Favorite
 */
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepo favoriteRepo = new FavoriteRepoImpl();
    private final FavoriteConvert favoriteConvert = new FavoriteConvert();
    /**
     * Tạo mới một mục yêu thích
     * 
     * @param request Đối tượng chứa thông tin yêu thích cần tạo
     * @return Đối tượng FavoriteResponse chứa thông tin đã tạo
     * @throws AppException Nếu có lỗi xảy ra trong quá trình tạo
     * 
     * Mô tả:
     * - Kiểm tra tính hợp lệ của dữ liệu đầu vào
     * - Kiểm tra xem yêu thích đã tồn tại chưa
     * - Tạo mới và lưu vào cơ sở dữ liệu
     * - Trả về thông tin đã tạo
     */
    @Override
    public FavoriteResponse create(FavoriteRequest request) {
        // Validate request
        FavoriteValidation.validateFavoriteRequest(request);

        try {
            // Kiểm tra xem đã tồn tại favorite này chưa
            if (favoriteRepo.existsByUserAndVideo(request.getUser().getId(), request.getVideo().getId())) {
                throw new AppException(Error.FAVORITE_ALREADY_EXISTS, 
                    "Video đã được thêm vào danh sách yêu thích trước đó");
            }

            // Thực hiện lưu
            Favorite favorite = favoriteConvert.toEntity(request);
            Favorite savedFavorite = favoriteRepo.save(favorite);
            return favoriteConvert.toResponse(savedFavorite);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR,
                    "Lỗi khi thêm vào danh sách yêu thích: " + e.getMessage());
        }
    }

    /**
     * Cập nhật thông tin một mục yêu thích
     * 
     * @param id ID của mục yêu thích cần cập nhật
     * @param request Đối tượng chứa thông tin cập nhật
     * @return Optional chứa thông tin đã cập nhật nếu thành công
     * @throws AppException Nếu không tìm thấy hoặc có lỗi khi cập nhật
     * 
     * Mô tả:
     * - Kiểm tra tính hợp lệ của ID và dữ liệu đầu vào
     * - Kiểm tra sự tồn tại của mục yêu thích
     * - Cập nhật thông tin và lưu vào cơ sở dữ liệu
     * - Trả về thông tin đã cập nhật
     */
    @Override
    public Optional<FavoriteResponse> update(Integer id, FavoriteRequest request) {
        // Validate input
        FavoriteValidation.validateFavoriteId(id);
        FavoriteValidation.validateFavoriteRequest(request);

        // Check if favorite exists
        if (!favoriteRepo.existsById(id)) {
            throw new AppException(Error.FAVORITE_NOT_FOUND,
                    "Không tìm thấy yêu thích với ID: " + id);
        }

        try {
            // Cập nhật thông tin
            request.setLikeDate(new Date()); // Cập nhật lại thời gian yêu thích
            
            // Lấy đối tượng hiện tại
            Favorite existingFavorite = favoriteRepo.findById(id)
                    .orElseThrow(() -> new AppException(Error.FAVORITE_NOT_FOUND, "Không tìm thấy yêu thích với ID: " + id));
            
            // Cập nhật thông tin
            favoriteConvert.toEntity(existingFavorite, request);
            
            // Lưu cập nhật
            Favorite updatedFavorite = favoriteRepo.update(existingFavorite)
                    .orElseThrow(() -> new AppException(Error.DATABASE_ERROR, "Cập nhật thất bại"));
                    
            return Optional.of(favoriteConvert.toResponse(updatedFavorite));
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR,
                    "Lỗi khi cập nhật thông tin yêu thích: " + e.getMessage());
        }
    }

    /**
     * Lấy thông tin một mục yêu thích theo ID
     * 
     * @param id ID của mục yêu thích cần lấy
     * @return Optional chứa thông tin mục yêu thích nếu tìm thấy
     * @throws AppException Nếu có lỗi khi truy vấn
     * 
     * Mô tả:
     * - Kiểm tra tính hợp lệ của ID
     * - Tìm kiếm trong cơ sở dữ liệu
     * - Ghi log kết quả tìm kiếm
     * - Trả về thông tin nếu tìm thấy
     */
    @Override
    public Optional<FavoriteResponse> getById(Integer id) {
        // Validate ID
        FavoriteValidation.validateFavoriteId(id);

        try {
            Optional<Favorite> favorite = favoriteRepo.findById(id);

            // Ghi log kết quả tìm kiếm
            if (favorite.isPresent()) {
                System.out.println("Đã tìm thấy yêu thích với ID: " + id);
            } else {
                System.out.println("Không tìm thấy yêu thích với ID: " + id);
            }

            return favorite.map(favoriteConvert::toResponse);
        } catch (Exception e) {
            // Ghi log lỗi nếu có ngoại lệ
            System.err.println("Lỗi khi tìm kiếm yêu thích ID " + id + ": " + e.getMessage());
            throw new AppException(Error.DATABASE_ERROR,
                    "Lỗi khi truy vấn thông tin yêu thích: " + e.getMessage());
        }
    }

    /**
     * Lấy danh sách tất cả các mục yêu thích
     * 
     * @return Danh sách các đối tượng FavoriteResponse
     * @throws AppException Nếu có lỗi khi truy vấn
     * 
     * Mô tả:
     * - Lấy toàn bộ danh sách từ cơ sở dữ liệu
     * - Ghi log số lượng bản ghi
     * - Xử lý trường hợp danh sách rỗng
     * - Trả về danh sách đã được chuyển đổi
     */
    @Override
    public List<FavoriteResponse> getAll() {
        try {
            List<Favorite> favorites = favoriteRepo.findAll();

            // Ghi log số lượng bản ghi
            System.out.println("Đã lấy danh sách " + favorites.size() + " yêu thích");

            // Có thể thêm xử lý nếu danh sách rỗng
            if (favorites.isEmpty()) {
                System.out.println("Không có dữ liệu yêu thích nào được tìm thấy");
            }

            return favoriteConvert.toResponseList(favorites);
        } catch (Exception e) {
            // Ghi log lỗi
            System.err.println("Lỗi khi lấy danh sách yêu thích: " + e.getMessage());
            throw new AppException(Error.DATABASE_ERROR,
                    "Lỗi khi truy vấn danh sách yêu thích: " + e.getMessage());
        }
    }

    /**
     * Xóa một mục yêu thích
     * 
     * @param id ID của mục yêu thích cần xóa
     * @return true nếu xóa thành công, false nếu không
     * @throws AppException Nếu không tìm thấy hoặc có lỗi khi xóa
     * 
     * Mô tả:
     * - Kiểm tra tính hợp lệ của ID
     * - Kiểm tra sự tồn tại của mục yêu thích
     * - Thực hiện xóa và trả về kết quả
     */
    @Override
    public boolean delete(Integer id) {
        // Validate ID
        FavoriteValidation.validateFavoriteId(id);

        try {
            // Check if favorite exists
            if (!favoriteRepo.existsById(id)) {
                throw new AppException(Error.FAVORITE_NOT_FOUND,
                        "Không tìm thấy yêu thích với ID: " + id);
            }

            // Thực hiện xóa
            return favoriteRepo.deleteById(id);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR,
                    "Lỗi khi xóa khỏi danh sách yêu thích: " + e.getMessage());
        }
    }

    /**
     * Kiểm tra sự tồn tại của một mục yêu thích
     * 
     * @param id ID của mục yêu thích cần kiểm tra
     * @return true nếu tồn tại, false nếu không
     * @throws AppException Nếu có lỗi khi kiểm tra
     * 
     * Mô tả:
     * - Kiểm tra tính hợp lệ của ID
     * - Thực hiện truy vấn kiểm tra
     * - Ghi log kết quả kiểm tra
     */
    @Override
    public boolean exists(Integer id) {
        // Validate ID
        FavoriteValidation.validateFavoriteId(id);

        try {
            boolean exists = favoriteRepo.existsById(id);
            // Ghi log kết quả kiểm tra
            System.out.println("Kiểm tra tồn tại yêu thích ID " + id + ": " + (exists ? "Có" : "Không"));
            return exists;
        } catch (Exception e) {
            // Ghi log lỗi
            System.err.println("Lỗi khi kiểm tra tồn tại yêu thích ID " + id + ": " + e.getMessage());
            throw new AppException(Error.DATABASE_ERROR,
                    "Lỗi khi kiểm tra sự tồn tại của yêu thích: " + e.getMessage());
        }
    }

    /**
     * Đếm tổng số mục yêu thích
     * 
     * @return Tổng số mục yêu thích
     * @throws AppException Nếu có lỗi khi đếm
     * 
     * Mô tả:
     * - Thực hiện đếm tổng số bản ghi
     * - Ghi log số lượng
     * - Trả về kết quả đếm được
     */
    @Override
    public long count() {
        try {
            long count = favoriteRepo.count();
            // Ghi log số lượng bản ghi
            System.out.println("Tổng số yêu thích hiện có: " + count);
            return count;
        } catch (Exception e) {
            // Ghi log lỗi
            System.err.println("Lỗi khi đếm số lượng yêu thích: " + e.getMessage());
            throw new AppException(Error.DATABASE_ERROR,
                    "Lỗi khi đếm số lượng yêu thích: " + e.getMessage());
        }
    }

    /**
     * Lấy danh sách yêu thích theo ID người dùng
     * 
     * @param userId ID của người dùng
     * @return Danh sách các mục yêu thích của người dùng
     * @throws AppException Nếu có lỗi khi truy vấn
     * 
     * Mô tả:
     * - Kiểm tra tính hợp lệ của userId
     * - Tìm kiếm các mục yêu thích theo userId
     * - Chuyển đổi và trả về danh sách kết quả
     */
    @Override
    public PaginatedResponse<FavoriteResponse> getByUserId(String userId, int page, int size) {
        try {
            // Validate input
            if (userId == null || userId.trim().isEmpty()) {
                throw new AppException(Error.INVALID_INPUT, "ID người dùng không được để trống");
            }

            // Find favorites by user ID and convert to response DTOs
            List<Favorite> favorites = favoriteRepo.findByUserId(userId);
            return favoriteConvert.toPaginatedResponse(favorites);

        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR,
                "Lỗi khi tìm kiếm yêu thích theo người dùng: " + e.getMessage());
        }
    }

    /**
     * Lấy danh sách phân trang các mục yêu thích
     * 
     * @param page Số trang cần lấy (bắt đầu từ 1)
     * @param size Số lượng bản ghi trên mỗi trang
     * @return Đối tượng PaginatedResponse chứa thông tin phân trang và danh sách kết quả
     * @throws AppException Nếu có lỗi xảy ra trong quá trình xử lý
     */
    @Override
    public PaginatedResponse<FavoriteResponse> paginate(int page, int size) {
        try {
            // Validate tham số phân trang
            FavoriteValidation.validatePagination(page, size);
            
            // Điều chỉnh chỉ số trang về 0-based cho JPA
            int pageIndex = page - 1;
            
            // Lấy tổng số bản ghi
            long totalElements = favoriteRepo.count();
            
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
            List<Favorite> favorites = favoriteRepo.pages(pageIndex, size);
            
            // Chuyển đổi sang DTO
            List<FavoriteResponse> content = favoriteConvert.toResponseList(favorites);
            
            // Tạo và trả về đối tượng phân trang
            return PaginatedResponse.of(content, page, size, totalElements);
            
        } catch (AppException e) {
            // Nếu là lỗi do validate, ném tiếp
            throw e;
        } catch (Exception e) {
            // Ghi log lỗi và ném ngoại lệ
            String errorMsg = String.format("Lỗi khi lấy danh sách phân trang (trang: %d, kích thước: %d)", page, size);
            System.err.println(errorMsg + ": " + e.getMessage());
            throw new AppException(Error.DATABASE_ERROR, e);
        }
    }

}
