# 📘 Hướng Dẫn Chuyển Đăng Nhập Từ Username Sang Email

## 📝 Tóm Tắt Tình Hình Hiện Tại

Bạn đã hoàn thành:
- ✅ **Entity User** - có đầy đủ field username, email, password, fullname, createdDate, updatedDate, confirmPassword
- ✅ **UserRequest DTO** - đã có tất cả field cần thiết
- ✅ **UserResponse DTO** - không trả về password (đúng theo yêu cầu)
- ✅ **UserRepo Interface** - có `findByUsername()` và `findByEmail()` 
- ✅ **UserRepoImpl** - đã implement đầy đủ 6 method từ BaseRepository và 2 method từ UserRepo

**Chưa hoàn thành:**
- ❌ **UserValidation** - chưa tạo
- ❌ **UserService Interface** - trống
- ❌ **UserServiceImpl** - trống
- ❌ **UserAPI Servlet** - trống

---

## 🎯 Chiến Lược Chuyển Từ Username Sang Email Login

### Bước 1: Thêm Method Login Vào UserRepo

**File:** `src/main/java/com/fpt/java4_asm/repositories/UserRepo.java`

Thêm method để tìm user bằng email và password để login:

```java
// Thêm vào interface UserRepo
Optional<User> findByEmailAndPassword(String email, String password);
```

### Bước 2: Implement Method Vào UserRepoImpl

**File:** `src/main/java/com/fpt/java4_asm/repositories/impl/UserRepoImpl.java`

Thêm implementation:

```java
@Override
public Optional<User> findByEmailAndPassword(String email, String password) {
    if(email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
        return Optional.empty();
    }
    try{
        String jpql = "SELECT u FROM User u WHERE u.email = :email AND u.password = :password";
        TypedQuery<User> query = em.createQuery(jpql, User.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        List<User> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
    }catch(Exception e){
        throw new RuntimeException("Lỗi khi tìm User theo email và password: " + email, e);
    }
}
```

### Bước 3: Tạo LoginRequest DTO

**File:** `src/main/java/com/fpt/java4_asm/dto/request/LoginRequest.java`

```java
package com.fpt.java4_asm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String email;      // Đăng nhập bằng email
    private String password;   // Mật khẩu
}
```

### Bước 4: Tạo LoginResponse DTO

**File:** `src/main/java/com/fpt/java4_asm/dto/response/LoginResponse.java`

```java
package com.fpt.java4_asm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String id;
    private String email;
    private String fullName;
    private Date createdDate;
    private String token;      // Dành cho JWT nếu cần
}
```

### Bước 5: Thêm Method Login Vào UserService

**File:** `src/main/java/com/fpt/java4_asm/services/UserService.java`

Thêm method login:

```java
/**
 * Đăng nhập bằng email và password
 */
Optional<UserResponse> login(String email, String password);
```

### Bước 6: Implement Login Trong UserServiceImpl

**File:** `src/main/java/com/fpt/java4_asm/services/impl/UserServiceImpl.java`

Thêm implementation:

```java
@Override
public Optional<UserResponse> login(String email, String password) {
    // Validate input
    UserValidation.validateNotEmpty(email, "Email");
    UserValidation.validateNotEmpty(password, "Password");

    try {
        Optional<User> user = userRepo.findByEmailAndPassword(email, password);
        
        if(user.isEmpty()) {
            throw new AppException(Error.INVALID_DATA, "Email hoặc mật khẩu không chính xác");
        }
        
        return user.map(this::toUserResponse);
    } catch (AppException e) {
        throw e;
    } catch (Exception e) {
        throw new AppException(Error.DATABASE_ERROR, 
            "Lỗi khi đăng nhập: " + e.getMessage());
    }
}
```

### Bước 7: Thêm API Endpoint Cho Login

**File:** `src/main/java/com/fpt/java4_asm/controllers/api/UserAPI.java`

**Option A:** Thêm method xử lý POST /api/users/login

```java
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
        String pathInfo = req.getPathInfo();
        
        // POST /api/users/login - Đăng nhập
        if(pathInfo != null && pathInfo.equals("/login")) {
            LoginRequest request = parseRequestBody(req, LoginRequest.class);
            Optional<UserResponse> response = userService.login(request.getEmail(), request.getPassword());
            
            if(response.isPresent()) {
                sendSuccessResponse(resp, response.get(), "Đăng nhập thành công");
            } else {
                throw new AppException(Error.INVALID_DATA, "Email hoặc mật khẩu không chính xác");
            }
            return;
        }
        
        // POST /api/users - Tạo User mới
        UserRequest request = parseRequestBody(req, UserRequest.class);
        UserResponse response = userService.create(request);
        
        resp.setStatus(HttpServletResponse.SC_CREATED);
        sendSuccessResponse(resp, response, "Tạo user thành công");
        
    } catch (AppException e) {
        throw e;
    } catch (Exception e) {
        throw new AppException(Error.INTERNAL_SERVER_ERROR, "Lỗi khi tạo User");
    }
}
```

---

## 📋 Checklist Chi Tiết Theo Yêu Cầu

### CRUD User - Entity, DTO, Repository, Service, API Servlet + Validation

#### 1. **Entity User**
- [x] Entity với @Table(name = "User")
- [x] Field `id` (String, Primary Key)
- [x] Field `username` (String, unique, not null) - GIỮ NGUYÊN (chưa dùng cho login)
- [x] Field `email` (String, unique, not null) - **DÙNG CHO LOGIN**
- [x] Field `password` (String, not null)
- [x] Field `fullName` (String, optional)
- [x] Field `createdDate` (Date)
- [x] Field `updatedDate` (Date)
- [x] Field `confirmPassword` (@Transient)
- [?] Field `admin` (Boolean, optional) - BẠN CÓ THÊM, KHÔNG CÓ TRONG YC

#### 2. **DTO Request**
- [x] `UserRequest` có: username, email, password, confirmPassword, fullName

**💡 Lưu ý:** Nên rename thành `CreateUserRequest` để rõ ràng hơn

#### 3. **DTO Response**
- [x] `UserResponse` có: id, username, email, fullName, createdDate
- [x] KHÔNG trả về password

#### 4. **Repository**
- [x] `UserRepo` interface kế thừa `BaseRepository<User, String>`
- [x] `UserRepo` có `findByUsername()`
- [x] `UserRepo` có `findByEmail()`
- [ ] `UserRepo` **CẦN THÊM**: `findByEmailAndPassword()` - cho login

- [x] `UserRepoImpl` implement tất cả 6 method từ BaseRepository
- [x] `UserRepoImpl` implement 2 method từ UserRepo
- [ ] `UserRepoImpl` **CẦN IMPLEMENT**: `findByEmailAndPassword()`

#### 5. **Validation**
- [ ] **CHƯA CÓ** `UserValidation` class
- [ ] Cần validate: username, email, password, confirmPassword
- [ ] Cần validate độ dài password >= 6 ký tự
- [ ] Cần validate password == confirmPassword
- [ ] Cần check duplicate username bằng Repository
- [ ] Cần check duplicate email bằng Repository

#### 6. **Service**
- [ ] **TRỐNG** `UserService` interface
- [ ] **TRỐNG** `UserServiceImpl` 
- [ ] Cần implement: create, update, delete, getById, getAll, exists, count
- [ ] **THÊM**: login(email, password)

#### 7. **API Servlet**
- [ ] **TRỐNG** `UserAPI` servlet
- [ ] Cần implement: doGet (GET /api/users, GET /api/users/{id})
- [ ] Cần implement: doPost (POST /api/users)
- [ ] Cần implement: doPut (PUT /api/users/{id})
- [ ] Cần implement: doDelete (DELETE /api/users/{id})
- [ ] **THÊM**: POST /api/users/login

---

## 🔑 Khác Biệt Chính Giữa Username Login và Email Login

| Tiêu Chí | Username Login | Email Login (Đề Xuất) |
|----------|----------------|----------------------|
| Field login | username | **email** ✅ |
| Độc nhất | Có, unique=true | Có, unique=true ✅ |
| Format | Không cấu trúc | Có định dạng rõ ràng ✅ |
| Bảo mật | Có thể bị đoán | Phức tạp hơn ✅ |
| Tiêu chuẩn | Cũ hơn | Hiện đại hơn ✅ |
| DB thay đổi | Cần alter table | **Không cần** ✅ |

**Kết luận:** Email login là lựa chọn tốt hơn vì:
1. Email đã là unique trong DB
2. Không cần thay đổi Database
3. Tiêu chuẩn hiện đại hơn
4. Bảo mật tốt hơn

---

## 📝 Các Bước Tiếp Theo Để Hoàn Thành CRUD

### Ưu Tiên:

1. **Tạo UserValidation Helper** - đây là nền tảng để validate tất cả
2. **Implement UserService Interface** - logic nghiệp vụ chính
3. **Implement UserServiceImpl** - các method CRUD + login
4. **Implement UserAPI Servlet** - các endpoint HTTP

### Lưu ý Quan Trọng:
- ✅ Giữ nguyên field `username` (chuẩn bị cho các tính năng khác sau)
- ✅ Sử dụng `email` làm credential chính cho login
- ✅ Validate tất cả input trước khi lưu DB
- ✅ Không trả về password trong response

---

## ❓ Câu Hỏi Cần Xác Nhận

1. **Field `admin`** - Bạn có thêm vào Entity nhưng không có trong yêu cầu. Có cần xóa?
2. **Password hashing** - Hiện tại plain text. Sau này cần hash bằng BCrypt?
3. **Phân quyền** - Có cần role/permission system?
4. **Session/JWT** - Login cần lưu session hay JWT token?

Hãy cho tôi biết để tôi tạo hướng dẫn chi tiết hơn! 🚀
