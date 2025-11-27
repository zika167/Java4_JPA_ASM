# ✅ CHECKLIST CRUD USER - Tiêu Chuẩn Thực Hiện

## 📋 Yêu Cầu Tổng Quan

```
CRUD User - Entity, DTO, Repository, Service, API Servlet + Validation, confirm field
```

---

## 1️⃣ Entity: User

### Yêu Cầu:
- [ ] Entity với `@Transient confirmPassword`
- [ ] Field `id` (String, Primary Key)
- [ ] Field `username` (String, unique, not null)
- [ ] Field `email` (String, unique, not null)
- [ ] Field `password` (String, not null)
- [ ] Field `fullName` (String, optional)
- [ ] Field `createdDate` (Date, @Temporal)
- [ ] Field `updatedDate` (Date, @Temporal)
- [ ] `confirmPassword` (@Transient) - **KHÔNG lưu DB**

### File:
```
src/main/java/com/fpt/java4_asm/models/entities/User.java
```

### Code Mẫu:
```java
@Entity
@Table(name = "User")
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreatedDate")
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UpdatedDate")
    private Date updatedDate;

    @Transient
    private String confirmPassword;
}
```

### ✅ Status:
- [x] **HOÀN THÀNH**

---

## 2️⃣ DTO: Request & Response

### 2.1 CreateUserRequest

### Yêu Cầu:
- [ ] Field `username` (String)
- [ ] Field `email` (String)
- [ ] Field `password` (String)
- [ ] Field `confirmPassword` (String) - **BẮT BUỘC**
- [ ] Field `fullName` (String, optional)

### File:
```
src/main/java/com/fpt/java4_asm/dto/request/CreateUserRequest.java
```

### Code Mẫu:
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;  // BẮT BUỘC
    private String fullName;
}
```

### Status:
- [?] **ĐANG CÓ** (tên file là `UserRequest` - nên rename thành `CreateUserRequest`)

---

### 2.2 UserResponse

### Yêu Cầu:
- [ ] Field `id` (String)
- [ ] Field `username` (String)
- [ ] Field `email` (String)
- [ ] Field `fullName` (String)
- [ ] Field `createdDate` (Date)
- [ ] **KHÔNG trả về `password`**

### File:
```
src/main/java/com/fpt/java4_asm/dto/response/UserResponse.java
```

### Code Mẫu:
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private String fullName;
    private Date createdDate;
}
```

### Status:
- [x] **HOÀN THÀNH**

---

## 3️⃣ Repository

### 3.1 UserRepo Interface

### Yêu Cầu:
- [ ] Kế thừa `BaseRepository<User, String>`
- [ ] Thêm `findByUsername(String username)` → `Optional<User>`
- [ ] Thêm `findByEmail(String email)` → `Optional<User>`

### File:
```
src/main/java/com/fpt/java4_asm/repositories/UserRepo.java
```

### Code Mẫu:
```java
public interface UserRepo extends BaseRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
```

### Status:
- [x] **HOÀN THÀNH**

---

### 3.2 UserRepoImpl

### Yêu Cầu:
- [ ] Implement `BaseRepository<User, String>` → 6 methods:
  - `save(User entity)` → `User`
  - `update(User entity)` → `Optional<User>`
  - `findById(String id)` → `Optional<User>`
  - `findAll()` → `List<User>`
  - `deleteById(String id)` → `boolean`
  - `existsById(String id)` → `boolean`
  - `count()` → `long`

- [ ] Implement 2 methods từ UserRepo:
  - `findByUsername(String username)` → `Optional<User>` (sử dụng JPQL)
  - `findByEmail(String email)` → `Optional<User>` (sử dụng JPQL)

### File:
```
src/main/java/com/fpt/java4_asm/repositories/impl/UserRepoImpl.java
```

### ✅ Status:
- [x] **HOÀN THÀNH** (đã implement tất cả 8 methods)

---

## 4️⃣ Validation (ValidationHelper)

### Yêu Cầu:
- [ ] Class `ValidationHelper` hoặc `UserValidation` (dùng static methods)
- [ ] Sử dụng `AppException` để throw lỗi
- [ ] Các method validate:

#### 4.1 `validateNotEmpty(String value, String fieldName)`
```java
// Validate username, email, password không null/empty
// Throw: AppException nếu null/empty
```

#### 4.2 `validatePasswordLength(String password)`
```java
// Validate password >= 6 ký tự
// Throw: AppException nếu < 6
```

#### 4.3 `validatePasswordMatch(String password, String confirmPassword)`
```java
// Validate password == confirmPassword
// Throw: AppException nếu khác nhau
```

#### 4.4 `validateDuplicateUsername(String username)`
```java
// Gọi userRepo.findByUsername()
// Throw: AppException nếu đã tồn tại
```

#### 4.5 `validateDuplicateEmail(String email)`
```java
// Gọi userRepo.findByEmail()
// Throw: AppException nếu đã tồn tại
```

#### 4.6 `validateCreateUserRequest(CreateUserRequest request)`
```java
// Gọi tất cả các method validate trên
// Throw: AppException nếu có lỗi
```

### File:
```
src/main/java/com/fpt/java4_asm/utils/helpers/ValidationHelper.java
hoặc
src/main/java/com/fpt/java4_asm/utils/helpers/UserValidation.java
```

### ⛔ Status:
- [ ] **CHƯA HOÀN THÀNH** (chưa tạo file)

---

## 5️⃣ Service

### 5.1 UserService Interface

### Yêu Cầu:
- [ ] Không kế thừa BaseService (tự define methods)
- [ ] Các methods CRUD:

```java
public interface UserService {
    UserResponse create(CreateUserRequest request);
    Optional<UserResponse> update(String id, CreateUserRequest request);
    Optional<UserResponse> getById(String id);
    List<UserResponse> getAll();
    boolean delete(String id);
    boolean exists(String id);
    long count();
}
```

### File:
```
src/main/java/com/fpt/java4_asm/services/UserService.java
```

### ⛔ Status:
- [ ] **CHƯA HOÀN THÀNH** (file trống)

---

### 5.2 UserServiceImpl

### Yêu Cầu:
- [ ] Implement `UserService`
- [ ] Gọi `UserValidation.validateCreateUserRequest()` trước tạo/update
- [ ] Gọi `userRepo` để save/update/delete/getById/getAll/exists/count
- [ ] Convert `User` Entity → `UserResponse` DTO (trong method `toUserResponse()`)
- [ ] Xử lý `AppException` và throw lại

### Logic Chi Tiết:

#### `create(CreateUserRequest request)`:
```
1. Validate: ValidationHelper.validateCreateUserRequest(request)
   - validateNotEmpty(username, "Username")
   - validateNotEmpty(email, "Email")
   - validateNotEmpty(password, "Password")
   - validateNotEmpty(confirmPassword, "Confirm Password")
   - validatePasswordLength(password)
   - validatePasswordMatch(password, confirmPassword)
   - validateDuplicateUsername(username)
   - validateDuplicateEmail(email)
2. Tạo User entity:
   - id = UUID.randomUUID().toString()
   - username, email, password, fullName = từ request
   - createdDate = new Date()
   - updatedDate = new Date()
3. Gọi userRepo.save(user)
4. Convert User → UserResponse
5. Return UserResponse
```

#### `update(String id, CreateUserRequest request)`:
```
1. Validate id không null/empty
2. Validate request (ValidationHelper.validateCreateUserRequest)
3. Kiểm tra user có tồn tại: userRepo.findById(id)
4. Nếu không tồn tại: throw AppException(NOT_FOUND)
5. Kiểm tra username/email duplicate (nếu thay đổi)
   - Nếu username thay đổi: check duplicate
   - Nếu email thay đổi: check duplicate
6. Update user entity: username, email, password, fullName, updatedDate
7. Gọi userRepo.update(user)
8. Convert User → UserResponse
9. Return Optional<UserResponse>
```

#### `getById(String id)`:
```
1. Validate id không null/empty
2. Gọi userRepo.findById(id)
3. Convert User → UserResponse
4. Return Optional<UserResponse>
```

#### `getAll()`:
```
1. Gọi userRepo.findAll()
2. Convert List<User> → List<UserResponse>
3. Return List<UserResponse>
```

#### `delete(String id)`:
```
1. Validate id không null/empty
2. Kiểm tra user có tồn tại: userRepo.existsById(id)
3. Nếu không tồn tại: throw AppException(NOT_FOUND)
4. Gọi userRepo.deleteById(id)
5. Return boolean (true nếu xóa thành công)
```

#### `exists(String id)`:
```
1. Validate id không null/empty
2. Gọi userRepo.existsById(id)
3. Return boolean
```

#### `count()`:
```
1. Gọi userRepo.count()
2. Return long
```

### File:
```
src/main/java/com/fpt/java4_asm/services/impl/UserServiceImpl.java
```

### ⛔ Status:
- [ ] **CHƯA HOÀN THÀNH** (file trống)

---

## 6️⃣ Servlet API

### Yêu Cầu:
- [ ] Class extends `BaseApiServlet`
- [ ] Annotate: `@WebServlet(ApiConstants.API_USERS + "/*")`
- [ ] Implement 4 HTTP methods:

#### 6.1 `doGet()`
```
GET /api/users           → lấy tất cả User
GET /api/users/{id}      → lấy User theo id
```

Logic:
```
1. Lấy pathInfo từ request
2. Nếu pathInfo = null hoặc "/" → gọi userService.getAll()
3. Nếu pathInfo = "/{id}" → gọi userService.getById(id)
4. Gọi sendSuccessResponse(resp, data, message)
5. Catch AppException → throw lại (BaseApiServlet xử lý)
```

#### 6.2 `doPost()`
```
POST /api/users          → tạo User mới
```

Logic:
```
1. Parse request body → CreateUserRequest
2. Gọi userService.create(request)
3. Set response status = 201 CREATED
4. Gọi sendSuccessResponse(resp, response, "Created successfully")
5. Catch AppException → throw lại
```

#### 6.3 `doPut()`
```
PUT /api/users/{id}      → cập nhật User
```

Logic:
```
1. Lấy id từ pathInfo
2. Parse request body → CreateUserRequest
3. Gọi userService.update(id, request)
4. Gọi sendSuccessResponse(resp, response, "Updated successfully")
5. Catch AppException → throw lại
```

#### 6.4 `doDelete()`
```
DELETE /api/users/{id}   → xóa User
```

Logic:
```
1. Lấy id từ pathInfo
2. Gọi userService.delete(id)
3. Set response status = 204 NO_CONTENT
4. Catch AppException → throw lại
```

### File:
```
src/main/java/com/fpt/java4_asm/controllers/api/UserAPI.java
```

### ⛔ Status:
- [ ] **CHƯA HOÀN THÀNH** (file trống)

---

## 7️⃣ Reference

### Yêu Cầu:
- [ ] **Không có** (User là entity gốc, không tham chiếu entity khác)

### Status:
- [x] **N/A** (User không cần reference)

---

## 📊 Tóm Tắt Progress

| Thành Phần | Status | Ghi Chú |
|-----------|--------|---------|
| Entity User | ✅ XONG | Có @Transient confirmPassword |
| DTO Request | 🟡 TRÊN ĐƯỜNG | Cần rename UserRequest → CreateUserRequest |
| DTO Response | ✅ XONG | Không trả về password |
| Repository Interface | ✅ XONG | Có findByUsername/Email |
| Repository Impl | ✅ XONG | Implement đủ 8 methods |
| Validation | ⛔ CHƯA | Cần tạo ValidationHelper |
| Service Interface | ⛔ CHƯA | File trống |
| Service Impl | ⛔ CHƯA | File trống |
| Servlet API | ⛔ CHƯA | File trống |

---

## 🚀 Thứ Tự Thực Hiện Đề Xuất

1. **Validation** - Nền tảng cho tất cả (validate input)
2. **Service Interface** - Define các methods
3. **Service Impl** - Implement logic
4. **Servlet API** - Tạo endpoints HTTP
5. **Rename DTO** - UserRequest → CreateUserRequest (cuối cùng)

---

## 📝 Lưu Ý Quan Trọng

### Validation:
- ✅ Sử dụng `AppException` với error code phù hợp
- ✅ Validate trong Service (không Servlet)
- ✅ Check duplicate username/email bằng Repository

### Service:
- ✅ Tất cả business logic ở đây
- ✅ Gọi Validation trước CRUD
- ✅ Convert Entity → DTO trước return

### Servlet:
- ✅ Chỉ parse request → gọi Service → return response
- ✅ Extend BaseApiServlet (sử dụng helper methods)
- ✅ Không có business logic ở đây

### Error Handling:
- ✅ Throw AppException từ Service/Validation
- ✅ BaseApiServlet sẽ catch và trả về ApiResponse lỗi
- ✅ Không catch lỗi ở Servlet

---

## ✅ Checklist Cuối Cùng

Sau khi hoàn thành tất cả, kiểm tra:

- [ ] Build project: `mvn clean install` - **KHÔNG CÓ LỖI**
- [ ] Test POST /api/users (create):
  - [ ] Với dữ liệu hợp lệ → 201 CREATED
  - [ ] Với username duplicate → 400 (AppException)
  - [ ] Với email duplicate → 400 (AppException)
  - [ ] Với password < 6 → 400 (AppException)
  - [ ] Với password != confirmPassword → 400 (AppException)
  - [ ] Với field null/empty → 400 (AppException)

- [ ] Test GET /api/users (getAll) → 200 OK, return danh sách
- [ ] Test GET /api/users/{id} (getById):
  - [ ] Với id hợp lệ → 200 OK
  - [ ] Với id không tồn tại → 404 NOT_FOUND

- [ ] Test PUT /api/users/{id} (update):
  - [ ] Với dữ liệu hợp lệ → 200 OK
  - [ ] Với id không tồn tại → 404 NOT_FOUND
  - [ ] Với dữ liệu không hợp lệ → 400 BAD_REQUEST

- [ ] Test DELETE /api/users/{id} (delete):
  - [ ] Với id hợp lệ → 204 NO_CONTENT
  - [ ] Với id không tồn tại → 404 NOT_FOUND

---

**🎯 Mục tiêu:** Hoàn thành CRUD User đầy đủ với validation & error handling sạch sẽ ✨
