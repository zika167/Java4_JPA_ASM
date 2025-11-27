# 🎯 GIẢI PHÁP CUỐI CÙNG - Tối Ưu Cho Docker Environment

## 📊 Phân Tích DB Schema Hiện Tại

### User Table Hiện Tại:

```sql
CREATE TABLE `User` (
    Id          VARCHAR(50) PRIMARY KEY,
    Password    VARCHAR(255) NOT NULL,
    Email       VARCHAR(100) NOT NULL UNIQUE,      ← EMAIL đã UNIQUE ✅
    Fullname    VARCHAR(100) NOT NULL,
    Admin       BOOLEAN NOT NULL DEFAULT FALSE,
    CreatedDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UpdatedDate DATETIME NULL ON UPDATE CURRENT_TIMESTAMP
);
```

### ✅ Tình Trạng:
- [x] Email **ĐÃ UNIQUE** trong DB
- [x] Email có index (IX_User_Email)
- [x] Fullname là display name (có thể trùng)
- [x] Có 4 users sample trong DB

---

## 🎬 GIẢI PHÁP ĐỀ XUẤT: Email Login (Solution 2)

### ✅ LÝ DO CHỌN EMAIL LOGIN:

```
1. EMAIL ĐÃ UNIQUE TRONG DB (không cần thay đổi)
   ✅ Không cần thêm column
   ✅ Không cần rebuild container
   ✅ Không cần modify init.sql

2. EMAIL ĐÃ CÓ SAMPLE DATA
   ✅ User có thể test login ngay
   ✅ Không cần data migration

3. DOCKER FRIENDLY
   ✅ Chỉ cần sửa Entity + Code Java
   ✅ Không cần touch init.sql
   ✅ Không cần rebuild container
   ✅ Không cần restart database

4. TIÊU CHUẨN & SẠC
   ✅ Email = duy nhất
   ✅ Email = authentication credential
   ✅ Email = recovery email
```

---

## ❌ TẠI SAO KHÔNG CHỌN USERNAME?

```
❌ Phải add column Username vào init.sql
❌ Phải rebuild container (fresh start)
❌ Phải wait DB tạo lại (~2-3 phút)
❌ Data migration phức tạp
❌ Mà email đã unique rồi, why bother?
```

---

## 🛠️ ACTION PLAN - Sửa Entity + Code

### Bước 1: Sửa User Entity

**File:** `src/main/java/com/fpt/java4_asm/models/entities/User.java`

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User")
public class User {
    @Id
    @Column(name = "Id")
    private String id;

    // ❌ XÓA username field
    // @Column(name = "Username", unique = true, nullable = false)
    // private String username;

    // ✅ EMAIL - credential duy nhất
    @Column(name = "Email", unique = true, nullable = false)
    private String email;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "Fullname")
    private String fullname;

    @Column(name = "Admin")
    private Boolean admin;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreatedDate")
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UpdatedDate")
    private Date updatedDate;

    // ✅ CONFIRM PASSWORD - chỉ dùng validation
    @Transient
    private String confirmPassword;
}
```

---

### Bước 2: Sửa DTO Request

**File:** `src/main/java/com/fpt/java4_asm/dto/request/UserRequest.java`

Rename thành: `CreateUserRequest.java`

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    // ❌ REMOVE username
    // private String username;

    // ✅ EMAIL là credential
    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;
}
```

---

### Bước 3: Repository - Chỉ Giữ findByEmail()

**File:** `src/main/java/com/fpt/java4_asm/repositories/UserRepo.java`

```java
public interface UserRepo extends BaseRepository<User, String> {
    // ❌ REMOVE findByUsername
    // Optional<User> findByUsername(String username);

    // ✅ GIỮ findByEmail
    Optional<User> findByEmail(String email);
    
    // ✅ THÊM này - cho login
    Optional<User> findByEmailAndPassword(String email, String password);
}
```

---

### Bước 4: Repository Implementation

**File:** `src/main/java/com/fpt/java4_asm/repositories/impl/UserRepoImpl.java`

```java
@Override
public Optional<User> findByEmail(String email) {
    if(email == null || email.trim().isEmpty()) return Optional.empty();
    try{
        String jpql = "SELECT u FROM User u WHERE u.email = :email";
        TypedQuery<User> query = em.createQuery(jpql, User.class);
        query.setParameter("email", email);
        List<User> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
    }catch(Exception e){
        throw new RuntimeException("Lỗi tìm User theo email: " + email, e);
    }
}

// ✅ THÊM method này - cho login
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
        throw new RuntimeException("Lỗi login: " + e.getMessage(), e);
    }
}

// ❌ XÓA findByUsername()
```

---

### Bước 5: Validation

**File:** `src/main/java/com/fpt/java4_asm/utils/helpers/ValidationHelper.java`

```java
public class ValidationHelper {
    private static final UserRepo userRepo = new UserRepoImpl();
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    private ValidationHelper() {
        throw new UnsupportedOperationException("Cannot instantiate utility class");
    }

    public static void validateCreateUserRequest(CreateUserRequest request) {
        if (request == null) {
            throw new AppException(Error.INVALID_DATA, "Request không được null");
        }

        // ✅ Validate Email
        validateNotEmpty(request.getEmail(), "Email");
        validateEmailFormat(request.getEmail());
        validateDuplicateEmail(request.getEmail());

        // ✅ Validate Password
        validateNotEmpty(request.getPassword(), "Password");
        validatePasswordLength(request.getPassword());

        // ✅ Validate Confirm Password
        validateNotEmpty(request.getConfirmPassword(), "Confirm Password");
        validatePasswordMatch(request.getPassword(), request.getConfirmPassword());

        // ✅ Validate Fullname (optional)
        if (request.getFullName() != null && request.getFullName().trim().isEmpty()) {
            throw new AppException(Error.INVALID_DATA, "Full Name không được để trắng");
        }
    }

    public static void validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new AppException(Error.INVALID_DATA, fieldName + " không được để trống");
        }
    }

    public static void validateEmailFormat(String email) {
        if (!email.matches(EMAIL_REGEX)) {
            throw new AppException(Error.INVALID_DATA, "Email không hợp lệ");
        }
    }

    public static void validatePasswordLength(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new AppException(Error.INVALID_DATA, 
                "Password phải có ít nhất " + MIN_PASSWORD_LENGTH + " ký tự");
        }
    }

    public static void validatePasswordMatch(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new AppException(Error.INVALID_DATA, "Password và Confirm Password không khớp");
        }
    }

    public static void validateDuplicateEmail(String email) {
        if (userRepo.findByEmail(email).isPresent()) {
            throw new AppException(Error.INVALID_DATA, "Email đã tồn tại");
        }
    }
    
    // ❌ XÓA validateDuplicateUsername()
}
```

---

### Bước 6: Service Interface

**File:** `src/main/java/com/fpt/java4_asm/services/UserService.java`

```java
public interface UserService {
    UserResponse create(CreateUserRequest request);
    Optional<UserResponse> update(String id, CreateUserRequest request);
    Optional<UserResponse> getById(String id);
    List<UserResponse> getAll();
    boolean delete(String id);
    boolean exists(String id);
    long count();
    
    // ✅ THÊM login method
    Optional<UserResponse> login(String email, String password);
}
```

---

### Bước 7: Service Implementation

**File:** `src/main/java/com/fpt/java4_asm/services/impl/UserServiceImpl.java`

```java
@Override
public UserResponse create(CreateUserRequest request) {
    ValidationHelper.validateCreateUserRequest(request);

    try {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // TODO: Hash với BCrypt
        user.setFullname(request.getFullName());
        user.setAdmin(false);
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());

        User savedUser = userRepo.save(user);
        return toUserResponse(savedUser);
    } catch (AppException e) {
        throw e;
    } catch (Exception e) {
        throw new AppException(Error.DATABASE_ERROR, "Lỗi tạo user: " + e.getMessage());
    }
}

@Override
public Optional<UserResponse> login(String email, String password) {
    ValidationHelper.validateNotEmpty(email, "Email");
    ValidationHelper.validateNotEmpty(password, "Password");

    try {
        Optional<User> user = userRepo.findByEmailAndPassword(email, password);
        if (user.isEmpty()) {
            throw new AppException(Error.INVALID_DATA, "Email hoặc password không chính xác");
        }
        return Optional.of(toUserResponse(user.get()));
    } catch (AppException e) {
        throw e;
    } catch (Exception e) {
        throw new AppException(Error.DATABASE_ERROR, "Lỗi đăng nhập: " + e.getMessage());
    }
}

// ... các method khác (update, getById, getAll, delete, exists, count)

private UserResponse toUserResponse(User user) {
    UserResponse response = new UserResponse();
    response.setId(user.getId());
    response.setEmail(user.getEmail());
    response.setFullName(user.getFullname());
    response.setCreatedDate(user.getCreatedDate());
    return response;
}
```

---

### Bước 8: Servlet API

**File:** `src/main/java/com/fpt/java4_asm/controllers/api/UserAPI.java`

```java
@WebServlet(ApiConstants.API_USERS + "/*")
public class UserAPI extends BaseApiServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String pathInfo = req.getPathInfo();

            if (pathInfo == null || pathInfo.equals("/")) {
                List<UserResponse> response = userService.getAll();
                sendSuccessResponse(resp, response, "Lấy danh sách thành công");
                return;
            }

            String[] pathParts = pathInfo.split("/");
            if (pathParts.length == 2) {
                String id = pathParts[1];
                Optional<UserResponse> response = userService.getById(id);
                if (response.isPresent()) {
                    sendSuccessResponse(resp, response.get(), "Lấy thành công");
                } else {
                    throw new AppException(Error.NOT_FOUND, "User không tìm thấy");
                }
                return;
            }

            throw new AppException(Error.INVALID_INPUT, "URL không hợp lệ");
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.INTERNAL_SERVER_ERROR, "Lỗi: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String pathInfo = req.getPathInfo();

            // ✅ POST /api/users/login - Đăng nhập
            if (pathInfo != null && pathInfo.equals("/login")) {
                LoginRequest loginRequest = parseRequestBody(req, LoginRequest.class);
                Optional<UserResponse> response = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
                
                sendSuccessResponse(resp, response.get(), "Đăng nhập thành công");
                return;
            }

            // ✅ POST /api/users - Tạo user mới
            CreateUserRequest request = parseRequestBody(req, CreateUserRequest.class);
            UserResponse response = userService.create(request);
            
            resp.setStatus(HttpServletResponse.SC_CREATED);
            sendSuccessResponse(resp, response, "Tạo user thành công");
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.INTERNAL_SERVER_ERROR, "Lỗi: " + e.getMessage());
        }
    }

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
            
            sendSuccessResponse(resp, response, "Cập nhật thành công");
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.INTERNAL_SERVER_ERROR, "Lỗi: " + e.getMessage());
        }
    }

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
            throw new AppException(Error.INTERNAL_SERVER_ERROR, "Lỗi: " + e.getMessage());
        }
    }
}
```

---

### Bước 9: Login Request DTO

**File:** `src/main/java/com/fpt/java4_asm/dto/request/LoginRequest.java`

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
}
```

---

## 📝 Update DTO Response

**File:** `src/main/java/com/fpt/java4_asm/dto/response/UserResponse.java`

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String email;
    private String fullName;
    private Date createdDate;
    // ❌ KHÔNG có username
    // ❌ KHÔNG có password
}
```

---

## 🧪 Testing Endpoints

### 1. Create User (POST /api/users)

```bash
POST /api/users
Content-Type: application/json

{
  "email": "newuser@example.com",
  "password": "password123",
  "confirmPassword": "password123",
  "fullName": "New User"
}

# Response 201:
{
  "status": "success",
  "data": {
    "id": "uuid-123",
    "email": "newuser@example.com",
    "fullName": "New User",
    "createdDate": "2025-11-27T10:00:00"
  },
  "message": "Tạo user thành công"
}
```

### 2. Login (POST /api/users/login)

```bash
POST /api/users/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "password123"
}

# Response 200:
{
  "status": "success",
  "data": {
    "id": "user001",
    "email": "john@example.com",
    "fullName": "John Doe",
    "createdDate": "2025-11-27T10:00:00"
  },
  "message": "Đăng nhập thành công"
}
```

### 3. Get All Users (GET /api/users)

```bash
GET /api/users

# Response 200:
{
  "status": "success",
  "data": [
    {
      "id": "user001",
      "email": "john@example.com",
      "fullName": "John Doe",
      "createdDate": "..."
    }
  ],
  "message": "Lấy danh sách thành công"
}
```

### 4. Get User By ID (GET /api/users/{id})

```bash
GET /api/users/user001

# Response 200:
{
  "status": "success",
  "data": {
    "id": "user001",
    "email": "john@example.com",
    "fullName": "John Doe",
    "createdDate": "..."
  },
  "message": "Lấy thành công"
}
```

### 5. Update User (PUT /api/users/{id})

```bash
PUT /api/users/user001
Content-Type: application/json

{
  "email": "john.doe@example.com",
  "password": "newpass123",
  "confirmPassword": "newpass123",
  "fullName": "John Doe Updated"
}

# Response 200:
{
  "status": "success",
  "data": {
    "id": "user001",
    "email": "john.doe@example.com",
    "fullName": "John Doe Updated",
    "createdDate": "..."
  },
  "message": "Cập nhật thành công"
}
```

### 6. Delete User (DELETE /api/users/{id})

```bash
DELETE /api/users/user001

# Response 204: No Content
```

---

## ✅ Summary of Changes

### ✅ KHÔNG CẦN THAY ĐỔI:
- [x] init.sql (không touch)
- [x] Docker container (không rebuild)
- [x] Database schema (email đã unique)

### ✅ THAY ĐỔI ENTITY:
- [x] Remove `username` field
- [x] Keep `email` (unique)
- [x] Keep `confirmPassword` (@Transient)

### ✅ THAY ĐỔI DTO:
- [x] Rename `UserRequest` → `CreateUserRequest`
- [x] Remove `username` field
- [x] Keep `email`, `password`, `confirmPassword`, `fullName`
- [x] Create `LoginRequest` DTO

### ✅ THAY ĐỔI REPOSITORY:
- [x] Remove `findByUsername()`
- [x] Keep `findByEmail()`
- [x] Add `findByEmailAndPassword()` (cho login)

### ✅ THAY ĐỔI VALIDATION:
- [x] Validate email format
- [x] Check duplicate email
- [x] Remove duplicate username check

### ✅ THAY ĐỔI SERVICE:
- [x] Implement tất cả CRUD methods
- [x] Add `login()` method

### ✅ THAY ĐỔI SERVLET:
- [x] GET /api/users (getAll)
- [x] GET /api/users/{id} (getById)
- [x] POST /api/users (create)
- [x] POST /api/users/login (login)
- [x] PUT /api/users/{id} (update)
- [x] DELETE /api/users/{id} (delete)

---

## 🎯 QUICK SUMMARY

```
✅ EMAIL LOGIN STRATEGY:
  - Dùng email (đã unique trong DB) làm credential
  - Không cần username
  - Không cần modify init.sql
  - Không cần rebuild container
  - Không cần data migration

✅ READY TO IMPLEMENT:
  - Entity
  - DTO (CreateUserRequest, LoginRequest)
  - Repository (remove findByUsername, keep findByEmail, add findByEmailAndPassword)
  - Validation
  - Service (CRUD + login)
  - Servlet (API endpoints)

✅ DOCKER FRIENDLY:
  - Just build project
  - No container changes needed
  - No database restart needed
```

---

**Bạn sẵn sàng để tôi implement hết code không?** 🚀
