# 📘 Hướng Dẫn Thực Hiện CRUD User - Chi Tiết Từng Bước

## 📚 Mục Lục
1. [Kiến Trúc Dự Án](#kiến-trúc-dự-án)
2. [Bước 1: Entity User](#bước-1-entity-user)
3. [Bước 2: DTO Request & Response](#bước-2-dto-request--response)
4. [Bước 3: UserRepo Interface](#bước-3-userrepo-interface)
5. [Bước 4: UserRepoImpl](#bước-4-userrepoimpl)
6. [Bước 5: UserValidation Helper](#bước-5-uservalidation-helper)
7. [Bước 6: UserService Interface](#bước-6-userservice-interface)
8. [Bước 7: UserServiceImpl](#bước-7-userserviceimpl)
9. [Bước 8: UserAPI Servlet](#bước-8-userapi-servlet) 
10. [Checklist Hoàn Thành](#-checklist-hoàn-thành)

---

## Kiến Trúc Dự Án

```
Request từ Frontend
    ↓
UserAPI Servlet (doGet/doPost/doPut/doDelete)
    ↓
UserService (xử lý logic nghiệp vụ)
    ↓
UserRepository (truy vấn database)
    ↓
Hibernate/JPA → MariaDB
    ↓
Response (ApiResponse<UserResponse>)
    ↓
Frontend nhận JSON
```

---

## Bước 1: Entity User

**File:** `src/main/java/com/fpt/java4_asm/models/entities/User.java`

### Yêu Cầu:
- ✅ Entity với `@Table(name = "User")`
- ✅ Field `id` (String, Primary Key)
- ✅ Field `username` (String, unique, not null)
- ✅ Field `email` (String, unique, not null)
- ✅ Field `password` (String, not null)
- ✅ Field `fullName` (String, optional)
- ✅ Field `createdDate` (Date)
- ✅ Field `updatedDate` (Date)
- ✅ **Field `confirmPassword` (@Transient)** - chỉ dùng cho validation, không lưu DB

### Ví Dụ Code:

```java
package com.fpt.java4_asm.models.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User")
public class User {
    @Id
    @Column(name = "Id")
    private String id;

    @Column(name = "Username", unique = true, nullable = false)
    private String username;

    @Column(name = "Email", unique = true, nullable = false)
    private String email;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "FullName")
    private String fullName;

    @Column(name = "CreatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "UpdatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    // Không lưu vào database, chỉ dùng cho validation
    @Transient
    private String confirmPassword;
}
```

### 📝 Hướng Dẫn Thực Hiện:
1. Mở file `User.java`
2. Thêm các field như trên
3. Sử dụng Lombok `@Data`, `@AllArgsConstructor`, `@NoArgsConstructor` để tự động generate getter/setter
4. **Lưu ý:** `@Transient` cho `confirmPassword` - điều này rất quan trọng!

---

## Bước 2: DTO Request & Response

### 2.1 CreateUserRequest

**File:** `src/main/java/com/fpt/java4_asm/dto/request/UserRequest.java`

```java
package com.fpt.java4_asm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    private String username;      // Required
    private String email;         // Required
    private String password;      // Required
    private String confirmPassword; // Required - phải giống password
    private String fullName;      // Optional
}
```

### 2.2 UserResponse

**File:** `src/main/java/com/fpt/java4_asm/dto/response/UserResponse.java`

```java
package com.fpt.java4_asm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private String fullName;
    private Date createdDate;
    // Không trả về password cho client!
}
```

### 📝 Hướng Dẫn Thực Hiện:
1. **CreateUserRequest**: Chứa `username`, `email`, `password`, `confirmPassword`, `fullName`
2. **UserResponse**: Trả về `id`, `username`, `email`, `fullName`, `createdDate` (không trả về password)
3. Cả hai đều sử dụng Lombok

---

## Bước 3: UserRepo Interface

**File:** `src/main/java/com/fpt/java4_asm/repositories/UserRepo.java`

```java
package com.fpt.java4_asm.repositories;

import com.fpt.java4_asm.models.entities.User;
import java.util.Optional;

/**
 * Repository interface cho User entity
 * Kế thừa các method CRUD từ BaseRepository
 * Thêm các method tìm kiếm riêng cho User
 */
public interface UserRepo extends BaseRepository<User, String> {
    
    /**
     * Tìm User theo username
     * @param username Username cần tìm
     * @return Optional chứa User nếu tìm thấy
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Tìm User theo email
     * @param email Email cần tìm
     * @return Optional chứa User nếu tìm thấy
     */
    Optional<User> findByEmail(String email);
}
```

### 📝 Hướng Dẫn Thực Hiện:
1. Tạo interface `UserRepo` kế thừa `BaseRepository<User, String>`
2. Thêm hai method: `findByUsername()` và `findByEmail()`
3. Cả hai trả về `Optional<User>`

---

## Bước 4: UserRepoImpl

**File:** `src/main/java/com/fpt/java4_asm/repositories/impl/UserRepoImpl.java`

```java
package com.fpt.java4_asm.repositories.impl;

import com.fpt.java4_asm.config.HibernateUtil;
import com.fpt.java4_asm.models.entities.User;
import com.fpt.java4_asm.repositories.UserRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class UserRepoImpl implements UserRepo {
    private EntityManager em = HibernateUtil.getEntityManager();

    @Override
    public User save(User entity) {
        if (entity == null) {
            throw new IllegalArgumentException("User không được để trống");
        }
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Lỗi khi lưu User: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<User> update(User entity) {
        if (entity == null || entity.getId() == null) {
            return Optional.empty();
        }
        try {
            em.getTransaction().begin();
            User updatedEntity = em.merge(entity);
            em.getTransaction().commit();
            return Optional.of(updatedEntity);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Lỗi khi cập nhật User: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<User> findById(String id) {
        if (id == null) {
            return Optional.empty();
        }
        try {
            User entity = em.find(User.class, id);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm User với ID: " + id, e);
        }
    }

    @Override
    public List<User> findAll() {
        try {
            String jpql = "SELECT u FROM User u";
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách User", e);
        }
    }

    @Override
    public boolean deleteById(String id) {
        if (id == null) {
            return false;
        }
        try {
            em.getTransaction().begin();
            User entity = em.find(User.class, id);
            if (entity != null) {
                em.remove(entity);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().rollback();
            return false;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Lỗi khi xóa User với ID: " + id, e);
        }
    }

    @Override
    public boolean existsById(String id) {
        if (id == null) {
            return false;
        }
        try {
            String jpql = "SELECT COUNT(u) > 0 FROM User u WHERE u.id = :id";
            return em.createQuery(jpql, Boolean.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi kiểm tra tồn tại User với ID: " + id, e);
        }
    }

    @Override
    public long count() {
        try {
            String jpql = "SELECT COUNT(u) FROM User u";
            return em.createQuery(jpql, Long.class)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi đếm số lượng User", e);
        }
    }

    /**
     * Tìm User theo username
     */
    @Override
    public Optional<User> findByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return Optional.empty();
        }
        try {
            String jpql = "SELECT u FROM User u WHERE u.username = :username";
            TypedQuery<User> query = em.createQuery(jpql, User.class)
                    .setParameter("username", username);
            List<User> results = query.getResultList();
            return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm User theo username: " + username, e);
        }
    }

    /**
     * Tìm User theo email
     */
    @Override
    public Optional<User> findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return Optional.empty();
        }
        try {
            String jpql = "SELECT u FROM User u WHERE u.email = :email";
            TypedQuery<User> query = em.createQuery(jpql, User.class)
                    .setParameter("email", email);
            List<User> results = query.getResultList();
            return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm User theo email: " + email, e);
        }
    }
}
```

### 📝 Hướng Dẫn Thực Hiện:
1. Implement `UserRepo` interface
2. Implement tất cả method từ `BaseRepository<User, String>`
3. Implement `findByUsername()` và `findByEmail()` - sử dụng JPQL query
4. Sử dụng `EntityManager` từ `HibernateUtil`

---

## Bước 5: UserValidation Helper

**File:** `src/main/java/com/fpt/java4_asm/utils/helpers/UserValidation.java`

```java
package com.fpt.java4_asm.utils.helpers;

import com.fpt.java4_asm.dto.request.CreateUserRequest;
import com.fpt.java4_asm.exception.AppException;
import com.fpt.java4_asm.exception.Error;
import com.fpt.java4_asm.repositories.UserRepo;
import com.fpt.java4_asm.repositories.impl.UserRepoImpl;

/**
 * Lớp helper để kiểm tra tính hợp lệ của dữ liệu User
 */
public class UserValidation {
    private static final UserRepo userRepo = new UserRepoImpl();
    
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    /**
     * Constructor riêng tư để ngăn chặn việc tạo thể hiện
     */
    private UserValidation() {
        throw new UnsupportedOperationException("Không thể tạo thể hiện của lớp tiện ích");
    }

    /**
     * Kiểm tra tính hợp lệ của CreateUserRequest
     * 
     * @param request Request cần kiểm tra
     * @throws AppException nếu có lỗi
     */
    public static void validateCreateUserRequest(CreateUserRequest request) {
        if (request == null) {
            throw new AppException(Error.INVALID_DATA, "Thông tin người dùng không được để trống");
        }

        // Validate username
        validateNotEmpty(request.getUsername(), "Username");
        validateDuplicateUsername(request.getUsername());

        // Validate email
        validateNotEmpty(request.getEmail(), "Email");
        validateEmailFormat(request.getEmail());
        validateDuplicateEmail(request.getEmail());

        // Validate password
        validateNotEmpty(request.getPassword(), "Password");
        validatePasswordLength(request.getPassword());

        // Validate confirmPassword
        validateNotEmpty(request.getConfirmPassword(), "Confirm Password");
        validatePasswordMatch(request.getPassword(), request.getConfirmPassword());
    }

    /**
     * Kiểm tra field không được trống
     */
    public static void validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new AppException(Error.INVALID_DATA, fieldName + " không được để trống");
        }
    }

    /**
     * Kiểm tra độ dài password >= 6 ký tự
     */
    public static void validatePasswordLength(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new AppException(Error.INVALID_DATA, 
                "Password phải có ít nhất " + MIN_PASSWORD_LENGTH + " ký tự");
        }
    }

    /**
     * Kiểm tra password và confirmPassword có giống nhau không
     */
    public static void validatePasswordMatch(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new AppException(Error.INVALID_DATA, "Password và Confirm Password không khớp");
        }
    }

    /**
     * Kiểm tra email đã tồn tại chưa
     */
    public static void validateDuplicateEmail(String email) {
        if (userRepo.findByEmail(email).isPresent()) {
            throw new AppException(Error.INVALID_DATA, "Email đã tồn tại trong hệ thống");
        }
    }

    /**
     * Kiểm tra username đã tồn tại chưa
     */
    public static void validateDuplicateUsername(String username) {
        if (userRepo.findByUsername(username).isPresent()) {
            throw new AppException(Error.INVALID_DATA, "Username đã tồn tại trong hệ thống");
        }
    }

    /**
     * Kiểm tra format email
     */
    public static void validateEmailFormat(String email) {
        if (!email.matches(EMAIL_REGEX)) {
            throw new AppException(Error.INVALID_DATA, "Email không hợp lệ");
        }
    }
}
```

### 📝 Hướng Dẫn Thực Hiện:
1. Tạo class `UserValidation` trong package `utils/helpers/`
2. Tất cả method đều là `static`
3. Validate:
   - ✅ Username không null/empty
   - ✅ Email không null/empty và format hợp lệ
   - ✅ Password không null/empty
   - ✅ Password >= 6 ký tự
   - ✅ Confirm password giống password
   - ✅ Check duplicate username bằng Repository
   - ✅ Check duplicate email bằng Repository

---

## Bước 6: UserService Interface

**File:** `src/main/java/com/fpt/java4_asm/services/UserService.java`

```java
package com.fpt.java4_asm.services;

import com.fpt.java4_asm.dto.request.CreateUserRequest;
import com.fpt.java4_asm.dto.response.UserResponse;
import java.util.List;
import java.util.Optional;

/**
 * Service interface cho User
 * Kế thừa các method CRUD từ BaseService
 */
public interface UserService {
    
    /**
     * Tạo mới User
     */
    UserResponse create(CreateUserRequest request);
    
    /**
     * Cập nhật User
     */
    Optional<UserResponse> update(String id, CreateUserRequest request);
    
    /**
     * Lấy User theo ID
     */
    Optional<UserResponse> getById(String id);
    
    /**
     * Lấy tất cả User
     */
    List<UserResponse> getAll();
    
    /**
     * Xóa User theo ID
     */
    boolean delete(String id);
    
    /**
     * Kiểm tra User có tồn tại không
     */
    boolean exists(String id);
    
    /**
     * Đếm tổng số User
     */
    long count();
}
```

### 📝 Hướng Dẫn Thực Hiện:
1. Tạo interface `UserService`
2. Định nghĩa các method CRUD cơ bản

---

## Bước 7: UserServiceImpl

**File:** `src/main/java/com/fpt/java4_asm/services/impl/UserServiceImpl.java`

```java
package com.fpt.java4_asm.services.impl;

import com.fpt.java4_asm.dto.request.CreateUserRequest;
import com.fpt.java4_asm.dto.response.UserResponse;
import com.fpt.java4_asm.exception.AppException;
import com.fpt.java4_asm.exception.Error;
import com.fpt.java4_asm.models.entities.User;
import com.fpt.java4_asm.repositories.UserRepo;
import com.fpt.java4_asm.repositories.impl.UserRepoImpl;
import com.fpt.java4_asm.services.UserService;
import com.fpt.java4_asm.utils.helpers.UserValidation;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Triển khai cụ thể của UserService
 */
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo = new UserRepoImpl();

    /**
     * Tạo mới User
     */
    @Override
    public UserResponse create(CreateUserRequest request) {
        // Validate request
        UserValidation.validateCreateUserRequest(request);

        try {
            // Tạo entity User mới
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword()); // TODO: Hash password bằng BCrypt
            user.setFullName(request.getFullName());
            user.setCreatedDate(new Date());
            user.setUpdatedDate(new Date());

            // Lưu vào database
            User savedUser = userRepo.save(user);
            return toUserResponse(savedUser);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, 
                "Lỗi khi tạo mới User: " + e.getMessage());
        }
    }

    /**
     * Cập nhật User
     */
    @Override
    public Optional<UserResponse> update(String id, CreateUserRequest request) {
        // Validate input
        UserValidation.validateNotEmpty(id, "User ID");
        UserValidation.validateCreateUserRequest(request);

        try {
            // Kiểm tra User có tồn tại không
            Optional<User> existingUser = userRepo.findById(id);
            if (existingUser.isEmpty()) {
                throw new AppException(Error.NOT_FOUND, "Không tìm thấy User với ID: " + id);
            }

            User user = existingUser.get();
            
            // Kiểm tra username và email đã tồn tại ở user khác không
            if (!user.getUsername().equals(request.getUsername()) && 
                userRepo.findByUsername(request.getUsername()).isPresent()) {
                throw new AppException(Error.INVALID_DATA, "Username đã tồn tại");
            }
            
            if (!user.getEmail().equals(request.getEmail()) && 
                userRepo.findByEmail(request.getEmail()).isPresent()) {
                throw new AppException(Error.INVALID_DATA, "Email đã tồn tại");
            }

            // Cập nhật thông tin
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword()); // TODO: Hash password
            user.setFullName(request.getFullName());
            user.setUpdatedDate(new Date());

            // Lưu cập nhật
            Optional<User> updatedUser = userRepo.update(user);
            return updatedUser.map(this::toUserResponse);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, 
                "Lỗi khi cập nhật User: " + e.getMessage());
        }
    }

    /**
     * Lấy User theo ID
     */
    @Override
    public Optional<UserResponse> getById(String id) {
        UserValidation.validateNotEmpty(id, "User ID");

        try {
            Optional<User> user = userRepo.findById(id);
            return user.map(this::toUserResponse);
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, 
                "Lỗi khi lấy User: " + e.getMessage());
        }
    }

    /**
     * Lấy tất cả User
     */
    @Override
    public List<UserResponse> getAll() {
        try {
            List<User> users = userRepo.findAll();
            return users.stream()
                    .map(this::toUserResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, 
                "Lỗi khi lấy danh sách User: " + e.getMessage());
        }
    }

    /**
     * Xóa User theo ID
     */
    @Override
    public boolean delete(String id) {
        UserValidation.validateNotEmpty(id, "User ID");

        try {
            // Kiểm tra User có tồn tại không
            if (!userRepo.existsById(id)) {
                throw new AppException(Error.NOT_FOUND, "Không tìm thấy User với ID: " + id);
            }
            return userRepo.deleteById(id);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, 
                "Lỗi khi xóa User: " + e.getMessage());
        }
    }

    /**
     * Kiểm tra User có tồn tại không
     */
    @Override
    public boolean exists(String id) {
        UserValidation.validateNotEmpty(id, "User ID");

        try {
            return userRepo.existsById(id);
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, 
                "Lỗi khi kiểm tra User: " + e.getMessage());
        }
    }

    /**
     * Đếm tổng số User
     */
    @Override
    public long count() {
        try {
            return userRepo.count();
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, 
                "Lỗi khi đếm User: " + e.getMessage());
        }
    }

    /**
     * Convert User Entity sang UserResponse DTO
     */
    private UserResponse toUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setCreatedDate(user.getCreatedDate());
        return response;
    }
}
```

### 📝 Hướng Dẫn Thực Hiện:
1. Implement `UserService` interface
2. Implement `create()`: 
   - ✅ Validate dữ liệu
   - ✅ Tạo User entity (UUID cho ID)
   - ✅ Lưu vào DB
   - ✅ Trả về UserResponse
3. Implement `update()`:
   - ✅ Validate dữ liệu
   - ✅ Kiểm tra User có tồn tại
   - ✅ Kiểm tra username/email duplicate (trừ user hiện tại)
   - ✅ Cập nhật thông tin
   - ✅ Trả về UserResponse
4. Implement `getById()`, `getAll()`, `delete()`, `exists()`, `count()`
5. Tạo method `toUserResponse()` để convert Entity → DTO

---

## Bước 8: UserAPI Servlet

**File:** `src/main/java/com/fpt/java4_asm/controllers/api/UserAPI.java`

```java
package com.fpt.java4_asm.controllers.api;

import com.fpt.java4_asm.dto.request.CreateUserRequest;
import com.fpt.java4_asm.dto.response.UserResponse;
import com.fpt.java4_asm.exception.AppException;
import com.fpt.java4_asm.exception.Error;
import com.fpt.java4_asm.services.UserService;
import com.fpt.java4_asm.services.impl.UserServiceImpl;
import com.fpt.java4_asm.utils.constants.ApiConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * API Controller xử lý các request liên quan đến User
 * 
 * Các endpoint hỗ trợ:
 * - GET /api/users - Lấy tất cả User
 * - GET /api/users/{id} - Lấy User theo ID
 * - POST /api/users - Tạo User mới
 * - PUT /api/users/{id} - Cập nhật User
 * - DELETE /api/users/{id} - Xóa User
 */
@WebServlet(ApiConstants.API_USERS + "/*")
public class UserAPI extends BaseApiServlet {
    
    private final UserService userService = new UserServiceImpl();

    /**
     * Xử lý GET request
     * - GET /api/users: Lấy tất cả
     * - GET /api/users/{id}: Lấy theo ID
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String pathInfo = req.getPathInfo();

            // GET /api/users - Lấy tất cả User
            if (pathInfo == null || pathInfo.equals("/")) {
                List<UserResponse> response = userService.getAll();
                sendSuccessResponse(resp, response, ApiConstants.MSG_SUCCESS);
                return;
            }

            // GET /api/users/{id} - Lấy User theo ID
            String[] pathParts = pathInfo.split("/");
            if (pathParts.length == 2) {
                String id = pathParts[1];
                Optional<UserResponse> response = userService.getById(id);
                if (response.isPresent()) {
                    sendSuccessResponse(resp, response.get(), ApiConstants.MSG_SUCCESS);
                } else {
                    throw new AppException(Error.NOT_FOUND, "Không tìm thấy User với ID: " + id);
                }
                return;
            }

            throw new AppException(Error.NOT_FOUND);

        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.INTERNAL_SERVER_ERROR, e);
        }
    }

    /**
     * Xử lý POST request
     * - POST /api/users: Tạo User mới
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CreateUserRequest request = parseRequestBody(req, CreateUserRequest.class);
            UserResponse response = userService.create(request);
            
            resp.setStatus(HttpServletResponse.SC_CREATED);
            sendSuccessResponse(resp, response, ApiConstants.MSG_CREATED);
            
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.INTERNAL_SERVER_ERROR, "Lỗi khi tạo User");
        }
    }

    /**
     * Xử lý PUT request
     * - PUT /api/users/{id}: Cập nhật User
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String pathInfo = req.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                throw new AppException(Error.INVALID_INPUT, "User ID là bắt buộc");
            }

            String[] pathParts = pathInfo.split("/");
            if (pathParts.length != 2) {
                throw new AppException(Error.INVALID_INPUT, "URL không hợp lệ");
            }

            String id = pathParts[1];
            CreateUserRequest request = parseRequestBody(req, CreateUserRequest.class);
            Optional<UserResponse> response = userService.update(id, request);
            
            sendSuccessResponse(resp, response, ApiConstants.MSG_UPDATED);
            
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.INTERNAL_SERVER_ERROR, "Lỗi khi cập nhật User");
        }
    }

    /**
     * Xử lý DELETE request
     * - DELETE /api/users/{id}: Xóa User
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String pathInfo = req.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                throw new AppException(Error.INVALID_INPUT, "User ID là bắt buộc");
            }

            String[] pathParts = pathInfo.split("/");
            if (pathParts.length != 2) {
                throw new AppException(Error.INVALID_INPUT, "URL không hợp lệ");
            }

            String id = pathParts[1];
            userService.delete(id);
            
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.INTERNAL_SERVER_ERROR, "Lỗi khi xóa User");
        }
    }
}
```

### 📝 Hướng Dẫn Thực Hiện:
1. Tạo class `UserAPI` extends `BaseApiServlet`
2. Annotate với `@WebServlet(ApiConstants.API_USERS + "/*")`
3. Implement `doGet()`:
   - ✅ GET /api/users - trả về danh sách
   - ✅ GET /api/users/{id} - trả về user theo ID
4. Implement `doPost()`:
   - ✅ POST /api/users - tạo user mới
   - ✅ Response status: 201 CREATED
5. Implement `doPut()`:
   - ✅ PUT /api/users/{id} - cập nhật user
6. Implement `doDelete()`:
   - ✅ DELETE /api/users/{id} - xóa user
   - ✅ Response status: 204 NO_CONTENT

---

## ✅ Checklist Hoàn Thành

Sau khi hoàn thành tất cả các bước, hãy kiểm tra:

### Entity User
- [ ] ✅ Entity `User.java` có tất cả field (username, email, password, fullName, createdDate, updatedDate)
- [ ] ✅ Field `confirmPassword` có `@Transient`
- [ ] ✅ Field `username`, `email` có `unique = true`
- [ ] ✅ Field `password` có `nullable = false`

### DTO
- [ ] ✅ `CreateUserRequest` có: username, email, password, confirmPassword, fullName
- [ ] ✅ `UserResponse` có: id, username, email, fullName, createdDate (không có password)

### Repository
- [ ] ✅ `UserRepo` interface kế thừa `BaseRepository<User, String>`
- [ ] ✅ `UserRepo` có `findByUsername()` và `findByEmail()`
- [ ] ✅ `UserRepoImpl` implement tất cả method
- [ ] ✅ Sử dụng JPQL queries

### Validation
- [ ] ✅ `UserValidation` class có tất cả method validate
- [ ] ✅ Validate: username, email, password, confirmPassword
- [ ] ✅ Validate độ dài password >= 6 ký tự
- [ ] ✅ Validate password == confirmPassword
- [ ] ✅ Check duplicate username và email bằng Repository

### Service
- [ ] ✅ `UserService` interface có tất cả method CRUD
- [ ] ✅ `UserServiceImpl` implement tất cả method
- [ ] ✅ Gọi UserValidation để validate
- [ ] ✅ Gọi UserRepo để lưu/lấy dữ liệu

### API Servlet
- [ ] ✅ `UserAPI` extends `BaseApiServlet`
- [ ] ✅ Implement `doGet/doPost/doPut/doDelete`
- [ ] ✅ Xử lý URL: /api/users và /api/users/{id}
- [ ] ✅ Trả về `ApiResponse<UserResponse>`
- [ ] ✅ Đặt status code đúng: 201 for POST, 204 for DELETE

### Testing
- [ ] ✅ Build project: `mvn clean install`
- [ ] ✅ Test GET /api/users
- [ ] ✅ Test GET /api/users/{id}
- [ ] ✅ Test POST /api/users (create)
- [ ] ✅ Test PUT /api/users/{id} (update)
- [ ] ✅ Test DELETE /api/users/{id}
- [ ] ✅ Test validation: empty field, password < 6, password != confirmPassword, duplicate

---

## 📝 Ghi Chú Quan Trọng

1. **UUID cho ID**: Sử dụng `UUID.randomUUID().toString()` để tạo ID user
2. **@Transient cho confirmPassword**: Rất quan trọng! Không lưu vào DB
3. **Password hashing**: Hiện tại để plain text (TODO: sử dụng BCrypt/Spring Security)
4. **Unique constraint**: username và email phải unique
5. **Validation trước**: Validate dữ liệu ở Service trước khi lưu DB
6. **Error handling**: Sử dụng `AppException` để xử lý lỗi

---

## 🚀 Bắt Đầu Thực Hiện

Bây giờ bạn có thể bắt đầu thực hiện từng bước. Hãy thực hiện theo thứ tự:

1. ✅ Bước 1: Entity User
2. ✅ Bước 2: DTO Request & Response
3. ✅ Bước 3: UserRepo Interface
4. ✅ Bước 4: UserRepoImpl
5. ✅ Bước 5: UserValidation Helper
6. ✅ Bước 6: UserService Interface
7. ✅ Bước 7: UserServiceImpl
8. ✅ Bước 8: UserAPI Servlet

**Chúc bạn thành công!** 🎉
