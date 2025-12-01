# 🚀 Java 4 Assignment - Video Sharing Platform API

## 📋 Mô tả dự án

Dự án là một **REST API** cho nền tảng chia sẻ video, được xây dựng với kiến trúc **Clean Architecture**:

- **Backend**: Java 21 + Jakarta Servlet 6.0
- **ORM**: Hibernate 6.4.5 (JPA 3.1)
- **Database**: MariaDB 10.6+
- **Build Tool**: Maven 3.9+
- **Server**: Apache Tomcat 10.1.x

### Tính năng chính:
- ✅ Quản lý người dùng (User Management)
- ✅ Quản lý video (Video Management)
- ✅ Yêu thích video (Favorites)
- ✅ Bình luận video (Comments)
- ✅ Chia sẻ video (Share)
- ✅ Xác thực & phân quyền (Authentication & Authorization)
- ✅ Phân trang (Pagination)
- ✅ Xử lý lỗi toàn cục (Global Error Handling)

## 🛠 Công nghệ sử dụng

### Backend

- **Ngôn ngữ**: Java 21
- **Framework**:
  - Jakarta Servlet 6.0 (REST API)
  - Hibernate ORM 6.4.5 (JPA 3.1)
  - Jackson 2.13.0 (JSON serialization)
- **Build Tool**: Maven 3.9+
- **Server**: Apache Tomcat 10.1.x

### Database

- **Hệ quản trị**: MariaDB 10.6+
- **ORM**: Hibernate 6.4.5
- **Kết nối**: JDBC (MariaDB Driver 3.4.1)

### Tiện ích

- **Lombok**: Giảm boilerplate code (getters, setters, constructors)
- **JUnit 5**: Unit testing
- **Jackson**: Xử lý JSON
- **JavaMail**: Gửi email (nếu cần)

## 📁 Cấu trúc thư mục dự án

```
java4_asm/
├── src/
│   ├── main/
│   │   ├── java/com/fpt/java4_asm/
│   │   │   ├── config/                  # Cấu hình Hibernate
│   │   │   │   └── HibernateUtil.java
│   │   │   │
│   │   │   ├── controllers/             # HTTP Request Handlers
│   │   │   │   ├── api/                 # REST API Servlets
│   │   │   │   │   ├── AuthAPI.java
│   │   │   │   │   ├── UserAPI.java
│   │   │   │   │   ├── VideoAPI.java
│   │   │   │   │   ├── FavoriteAPI.java
│   │   │   │   │   ├── CommentAPI.java
│   │   │   │   │   └── ShareAPI.java
│   │   │   │   └── BaseApiServlet.java  # Base class cho API
│   │   │   │
│   │   │   ├── convert/                 # Entity ↔ DTO Converters
│   │   │   │   ├── UserConvert.java
│   │   │   │   ├── VideoConvert.java
│   │   │   │   ├── FavoriteConvert.java
│   │   │   │   ├── CommentConvert.java
│   │   │   │   └── ShareConvert.java
│   │   │   │
│   │   │   ├── dto/                     # Data Transfer Objects
│   │   │   │   ├── request/             # Request DTOs
│   │   │   │   │   ├── LoginRequest.java
│   │   │   │   │   ├── UserRequest.java
│   │   │   │   │   ├── VideoRequest.java
│   │   │   │   │   ├── FavoriteRequest.java
│   │   │   │   │   ├── CommentRequest.java
│   │   │   │   │   └── ShareRequest.java
│   │   │   │   │
│   │   │   │   └── response/            # Response DTOs
│   │   │   │       ├── LoginResponse.java
│   │   │   │       ├── UserResponse.java
│   │   │   │       ├── VideoResponse.java
│   │   │   │       ├── FavoriteResponse.java
│   │   │   │       ├── CommentResponse.java
│   │   │   │       ├── ShareResponse.java
│   │   │   │       └── PaginatedResponse.java
│   │   │   │
│   │   │   ├── exception/               # Exception Handling
│   │   │   │   ├── AppException.java
│   │   │   │   └── Error.java
│   │   │   │
│   │   │   ├── filter/                  # Servlet Filters
│   │   │   │   └── CorsFilter.java
│   │   │   │
│   │   │   ├── models/                  # JPA Entities
│   │   │   │   └── entities/
│   │   │   │       ├── User.java
│   │   │   │       ├── Video.java
│   │   │   │       ├── Favorite.java
│   │   │   │       ├── Comment.java
│   │   │   │       └── Share.java
│   │   │   │
│   │   │   ├── repositories/            # Data Access Layer
│   │   │   │   ├── BaseRepository.java  # Generic interface
│   │   │   │   ├── UserRepo.java
│   │   │   │   ├── VideoRepo.java
│   │   │   │   ├── FavoriteRepo.java
│   │   │   │   ├── CommentRepo.java
│   │   │   │   ├── ShareRepo.java
│   │   │   │   └── impl/                # Repository implementations
│   │   │   │       ├── UserRepoImpl.java
│   │   │   │       ├── VideoRepoImpl.java
│   │   │   │       ├── FavoriteRepoImpl.java
│   │   │   │       ├── CommentRepoImpl.java
│   │   │   │       └── ShareRepoImpl.java
│   │   │   │
│   │   │   ├── services/                # Business Logic Layer
│   │   │   │   ├── AuthService.java
│   │   │   │   ├── UserService.java
│   │   │   │   ├── VideoService.java
│   │   │   │   ├── FavoriteService.java
│   │   │   │   ├── CommentService.java
│   │   │   │   ├── ShareService.java
│   │   │   │   └── impl/                # Service implementations
│   │   │   │       ├── AuthServiceImpl.java
│   │   │   │       ├── UserServiceImpl.java
│   │   │   │       ├── VideoServiceImpl.java
│   │   │   │       ├── FavoriteServiceImpl.java
│   │   │   │       ├── CommentServiceImpl.java
│   │   │   │       └── ShareServiceImpl.java
│   │   │   │
│   │   │   └── utils/                   # Utilities
│   │   │       ├── constants/
│   │   │       │   └── ApiConstants.java
│   │   │       └── helpers/
│   │   │           ├── UserValidation.java
│   │   │           ├── VideoValidation.java
│   │   │           ├── FavoriteValidation.java
│   │   │           ├── CommentValidation.java
│   │   │           ├── ShareValidation.java
│   │   │           └── PasswordValidation.java
│   │   │
│   │   ├── resources/
│   │   │   └── META-INF/
│   │   │       └── persistence.xml      # JPA Configuration
│   │   │
│   │   └── webapp/
│   │       └── WEB-INF/
│   │           └── web.xml              # Web Application Config
│   │
│   └── test/                            # Unit Tests
│       └── java/
│
├── .docker/                             # Docker Configuration
├── .github/                             # GitHub Configuration
├── .mvn/                                # Maven Wrapper
├── .gitignore
├── pom.xml                              # Maven Dependencies
├── mvnw & mvnw.cmd                      # Maven Wrapper Scripts
├── docker-compose.yml                   # Docker Compose Config
├── API_ENDPOINT.md                      # API Documentation
└── README.md                            # Project Documentation
```

### Mô tả chi tiết các layer

#### 1. **Controllers (API Layer)**
- `BaseApiServlet.java` - Base class cho tất cả API servlets
  - Cung cấp methods: `sendSuccessResponse()`, `sendErrorResponse()`, `parseRequestBody()`
  - Xử lý JSON serialization/deserialization
  - Quản lý HTTP status codes

- `AuthAPI.java` - Xử lý authentication (login/logout/validate)
- `UserAPI.java` - Quản lý người dùng (CRUD + pagination)
- `VideoAPI.java` - Quản lý video (CRUD + pagination)
- `FavoriteAPI.java` - Quản lý yêu thích (CRUD + pagination)
- `CommentAPI.java` - Quản lý bình luận (CRUD + pagination)
- `ShareAPI.java` - Quản lý chia sẻ (CRUD + pagination)

#### 2. **Services (Business Logic Layer)**
- `AuthService` & `AuthServiceImpl` - Xác thực người dùng
- `UserService` & `UserServiceImpl` - Logic quản lý user
- `VideoService` & `VideoServiceImpl` - Logic quản lý video
- `FavoriteService` & `FavoriteServiceImpl` - Logic quản lý yêu thích
- `CommentService` & `CommentServiceImpl` - Logic quản lý bình luận
- `ShareService` & `ShareServiceImpl` - Logic quản lý chia sẻ

#### 3. **Repositories (Data Access Layer)**
- `BaseRepository<T, ID>` - Generic interface cho CRUD operations
- `UserRepo` & `UserRepoImpl` - Truy vấn User từ database
- `VideoRepo` & `VideoRepoImpl` - Truy vấn Video từ database
- `FavoriteRepo` & `FavoriteRepoImpl` - Truy vấn Favorite từ database
- `CommentRepo` & `CommentRepoImpl` - Truy vấn Comment từ database
- `ShareRepo` & `ShareRepoImpl` - Truy vấn Share từ database

#### 4. **Models (Entity Layer)**
- `User.java` - Entity người dùng
- `Video.java` - Entity video
- `Favorite.java` - Entity yêu thích
- `Comment.java` - Entity bình luận
- `Share.java` - Entity chia sẻ

#### 5. **DTOs (Data Transfer Objects)**
- **Request DTOs**: Dữ liệu từ client gửi lên
  - `LoginRequest`, `UserRequest`, `VideoRequest`, `FavoriteRequest`, `CommentRequest`, `ShareRequest`
- **Response DTOs**: Dữ liệu trả về cho client
  - `LoginResponse`, `UserResponse`, `VideoResponse`, `FavoriteResponse`, `CommentResponse`, `ShareResponse`, `PaginatedResponse`

#### 6. **Converters (Entity ↔ DTO)**
- Chuyển đổi giữa Entity (database) và DTO (API)
- Xử lý mapping dữ liệu, nested objects, pagination

#### 7. **Utilities**
- `HibernateUtil.java` - Quản lý EntityManagerFactory (Singleton)
- `ApiConstants.java` - Chứa các hằng số API (paths, messages)
- `*Validation.java` - Validate dữ liệu input cho mỗi entity
- `PasswordValidation.java` - Xử lý mật khẩu

#### 8. **Exception Handling**
- `AppException.java` - Custom exception cho ứng dụng
- `Error.java` - Enum chứa các error codes
- Global error handling trong `BaseApiServlet`

#### 9. **Filters**
- `CorsFilter.java` - Xử lý CORS requests

## 🚀 Hướng dẫn cài đặt

### Yêu cầu hệ thống

- JDK 21+
- Apache Maven 3.9+
- MariaDB 10.6+
- Tomcat 10.1.x

### Bước 1: Cài đặt cơ sở dữ liệu

1. Tạo database mới:

   ```sql
   CREATE DATABASE java4_db_asm
   CHARACTER SET utf8mb4
   COLLATE utf8mb4_unicode_ci;
   ```

2. Cấu hình kết nối database trong file `src/main/resources/META-INF/persistence.xml`:
   ```xml
   <property name="jakarta.persistence.jdbc.url"
             value="jdbc:mariadb://localhost:3309/java4_db_asm"/>
   <property name="jakarta.persistence.jdbc.user"
             value="root"/>
   <property name="jakarta.persistence.jdbc.password"
             value="your_password"/>
   ```

### Bước 2: Build project

```bash
# Xóa build cũ và build mới
mvn clean install

# Hoặc chỉ build mà không chạy test
mvn clean install -DskipTests
```

### Bước 3: Chạy ứng dụng

```bash
# Cách 1: Sử dụng Maven Tomcat plugin
mvn tomcat7:run

# Cách 2: Deploy WAR file lên Tomcat
# Build WAR file
mvn clean package

# Copy target/java4_asm.war vào TOMCAT_HOME/webapps/
# Khởi động Tomcat
```

### Bước 4: Truy cập ứng dụng

```
http://localhost:8080/java4_asm
```

## 📚 Hướng dẫn phát triển

### Tạo Entity (Model)

```java
package com.fpt.java4_asm.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
```

### Tạo Repository

```java
package com.fpt.java4_asm.repositories.impl;

import com.fpt.java4_asm.models.entities.User;
import com.fpt.java4_asm.repositories.BaseRepository;
import jakarta.persistence.EntityManager;
import java.util.*;

public class UserRepository implements BaseRepository<User, Long> {
    private EntityManager em;

    public UserRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public User save(User entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        return entity;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    // Implement các method khác...
}
```

### Tạo Service

```java
package com.fpt.java4_asm.services.impl;

import com.fpt.java4_asm.repositories.BaseRepository;

import java.util.*;

public class UserService implements BaseService<User, Long> {
    private BaseRepository<User, Long> repository;

    public UserService(BaseRepository<User, Long> repository) {
        this.repository = repository;
    }

    @Override
    public User create(User entity) {
        // Validate dữ liệu
        // Xử lý logic
        return repository.save(entity);
    }

    // Implement các method khác...
}
```

### Tạo API Servlet

```java
package com.fpt.java4_asm.controllers.api;

import com.fpt.java4_asm.controllers.BaseApiServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/users")
public class UserServlet extends BaseApiServlet {
    private BaseService<User, Long> userService;

    @Override
    public void init() throws ServletException {
        // Initialize service
        userService = new UserService(new UserRepository(HibernateUtil.getEntityManager()));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String id = request.getParameter("id");

            if (id != null) {
                // GET /api/users?id=1
                Long userId = Long.parseLong(id);
                var user = userService.getById(userId);

                if (user.isPresent()) {
                    sendSuccessResponse(response, user.get());
                } else {
                    sendNotFound(response, "User not found");
                }
            } else {
                // GET /api/users
                var users = userService.getAll();
                sendSuccessResponse(response, users);
            }
        } catch (Exception e) {
            sendInternalServerError(response, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            User user = parseRequestBody(request, User.class);
            User created = userService.create(user);
            sendSuccessResponse(response, created, "User created successfully");
        } catch (Exception e) {
            sendBadRequest(response, e.getMessage());
        }
    }
}
```

## 🌟 API Response Format

Tất cả API endpoints đều trả về format JSON chung:

### Success Response (200 OK)

```json
{
  "success": true,
  "message": "Success",
  "data": {
    "id": 1,
    "email": "user@example.com",
    "fullName": "John Doe"
  },
  "timestamp": 1700000000000
}
```

### Error Response (4xx, 5xx)

```json
{
  "success": false,
  "message": "User not found",
  "data": null,
  "timestamp": 1700000000000
}
```

## 🌟 API Endpoints

### Base URL
```
http://localhost:8080/
```
### Authentication (Auth API)

- `POST   /api/auth/login` - Đăng nhập (nhận email & password, trả về token)
- `POST   /api/auth/logout` - Đăng xuất (xóa token)
- `GET    /api/auth/validate` - Xác thực token (kiểm tra token có hợp lệ)
- `GET    /api/auth/admin` - Kiểm tra quyền admin

**Header yêu cầu:**
```
Authorization: Bearer <token>
```

### Người dùng (User)

- `GET    /api/users?page=1&size=10` - Lấy danh sách người dùng (phân trang)
- `GET    /api/users/{id}` - Lấy thông tin chi tiết người dùng theo ID
- `GET    /api/users/email/{email}` - Lấy thông tin người dùng theo email
- `POST   /api/users` - Tạo mới người dùng
- `PUT    /api/users/{id}` - Cập nhật thông tin người dùng
- `DELETE /api/users/{id}` - Xóa người dùng

### Video (Video API)

- `GET    /api/videos?page=1&size=10` - Lấy danh sách video (phân trang)
- `GET    /api/videos/{id}` - Lấy thông tin video theo ID
- `POST   /api/videos` - Tạo mới video
- `PUT    /api/videos/{id}` - Cập nhật video
- `DELETE /api/videos/{id}` - Xóa video

### Favorite (Yêu thích)

- `GET    /api/favorites?page=1&size=10` - Lấy danh sách yêu thích (phân trang)
- `GET    /api/favorites/{id}` - Lấy thông tin yêu thích theo ID
- `GET    /api/favorites/user/{userId}?page=1&size=10` - Lấy video yêu thích của user
- `GET    /api/favorites/video/{videoId}?page=1&size=10` - Lấy người dùng yêu thích video
- `POST   /api/favorites` - Thêm video vào yêu thích
- `PUT    /api/favorites/{id}` - Cập nhật yêu thích
- `DELETE /api/favorites/{id}` - Xóa khỏi yêu thích

### Comment (Bình luận)

- `GET    /api/comments?page=1&size=10` - Lấy danh sách bình luận (phân trang)
- `GET    /api/comments/{id}` - Lấy thông tin bình luận theo ID
- `GET    /api/comments/user/{userId}?page=1&size=10` - Lấy bình luận của user
- `GET    /api/comments/video/{videoId}?page=1&size=10` - Lấy bình luận của video
- `POST   /api/comments` - Tạo bình luận mới
- `PUT    /api/comments/{id}` - Cập nhật bình luận
- `DELETE /api/comments/{id}` - Xóa bình luận

### Share (Chia sẻ)

- `GET    /api/shares?page=1&size=10` - Lấy danh sách chia sẻ (phân trang)
- `GET    /api/shares/{id}` - Lấy thông tin chia sẻ theo ID
- `GET    /api/shares/user/{userId}?page=1&size=10` - Lấy chia sẻ của user
- `GET    /api/shares/video/{videoId}?page=1&size=10` - Lấy chia sẻ của video
- `POST   /api/shares` - Tạo chia sẻ mới
- `PUT    /api/shares/{id}` - Cập nhật chia sẻ
- `DELETE /api/shares/{id}` - Xóa chia sẻ

## 📊 Các Entity (Bảng dữ liệu)

### User (Người dùng)
- `id` (String UUID) - Khóa chính
- `email` (String) - Email duy nhất
- `password` (String) - Mật khẩu
- `fullname` (String) - Tên đầy đủ
- `admin` (Boolean) - Vai trò (true = admin, false = user)
- `createdDate` (Date) - Ngày tạo
- `updatedDate` (Date) - Ngày cập nhật

### Video (Video)
- `id` (String UUID) - Khóa chính
- `title` (String) - Tiêu đề video
- `description` (String) - Mô tả
- `url` (String) - Đường dẫn video
- `poster` (String) - Hình ảnh đại diện
- `views` (Long) - Số lượt xem
- `user` (User) - Người tải lên (FK)
- `createdDate` (Date) - Ngày tạo
- `updatedDate` (Date) - Ngày cập nhật

### Favorite (Yêu thích)
- `id` (Integer) - Khóa chính (auto-increment)
- `user` (User) - Người dùng (FK)
- `video` (Video) - Video yêu thích (FK)
- `likeDate` (Date) - Ngày yêu thích

### Comment (Bình luận)
- `id` (Long) - Khóa chính (auto-increment)
- `content` (String) - Nội dung bình luận
- `user` (User) - Người bình luận (FK)
- `video` (Video) - Video được bình luận (FK)
- `createdDate` (Date) - Ngày tạo
- `updatedDate` (Date) - Ngày cập nhật

### Share (Chia sẻ)
- `id` (Integer) - Khóa chính (auto-increment)
- `user` (User) - Người chia sẻ (FK)
- `video` (Video) - Video được chia sẻ (FK)
- `emails` (String) - Danh sách email nhận chia sẻ (JSON)
- `shareDate` (Date) - Ngày chia sẻ

## 🔧 Các class hỗ trợ chính

### BaseApiServlet

- Cung cấp các method utility cho việc ghi response JSON
- Xử lý parse request body
- Quản lý HTTP status codes
- Methods: `sendSuccessResponse()`, `sendErrorResponse()`, `parseRequestBody()`

### HibernateUtil

- Quản lý EntityManagerFactory
- Cung cấp EntityManager cho các repository
- Singleton pattern

### BaseRepository<T, ID>

- Interface generic cho tất cả repository
- Methods cơ bản: `save()`, `update()`, `findById()`, `findAll()`, `deleteById()`, `existsById()`, `count()`

### ValidationHelper Classes

- `UserValidation` - Validate dữ liệu User
- `VideoValidation` - Validate dữ liệu Video
- `FavoriteValidation` - Validate dữ liệu Favorite
- `CommentValidation` - Validate dữ liệu Comment
- `ShareValidation` - Validate dữ liệu Share

### Convert Classes

- `UserConvert` - Chuyển đổi User entity ↔ UserRequest/UserResponse
- `VideoConvert` - Chuyển đổi Video entity ↔ VideoRequest/VideoResponse
- `FavoriteConvert` - Chuyển đổi Favorite entity ↔ FavoriteRequest/FavoriteResponse
- `CommentConvert` - Chuyển đổi Comment entity ↔ CommentRequest/CommentResponse
- `ShareConvert` - Chuyển đổi Share entity ↔ ShareRequest/ShareResponse

### AppException

- Custom exception cho ứng dụng
- Mapping error code sang HTTP status
- Error codes: `INVALID_DATA`, `NOT_FOUND`, `DATABASE_ERROR`, `INVALID_CREDENTIALS`, `INVALID_INPUT`

### ApiConstants

- Chứa các hằng số API
- Paths: `/api/users`, `/api/videos`, `/api/favorites`, `/api/comments`, `/api/shares`, `/api/auth`
- Messages: `MSG_SUCCESS`, `MSG_CREATED`, `MSG_UPDATED`, `MSG_DELETED`

## 🔐 Authentication & Authorization

### Login Flow

1. Client gửi POST `/api/auth/login` với email & password
2. Server xác thực thông tin
3. Tạo token (UUID) và lưu vào tokenStore
4. Trả về LoginResponse với token
5. Client lưu token và gửi trong header `Authorization: Bearer <token>` cho các request tiếp theo

### Token Usage

- **Validate Token**: `GET /api/auth/validate` - Kiểm tra token có hợp lệ
- **Check Admin**: `GET /api/auth/admin` - Kiểm tra user có phải admin
- **Logout**: `POST /api/auth/logout` - Xóa token khỏi hệ thống

### Role-Based Access Control

- `admin = true` - Có quyền quản trị (xóa user, xóa video, v.v.)
- `admin = false` - User thường (chỉ quản lý nội dung của mình)

## 📝 Pagination

Tất cả endpoint GET danh sách đều hỗ trợ phân trang:

```
GET /api/users?page=1&size=10
```

**Parameters:**
- `page` (int) - Số trang (bắt đầu từ 1)
- `size` (int) - Số bản ghi mỗi trang

**Response:**
```json
{
  "success": true,
  "message": "Success",
  "data": {
    "content": [...],
    "page": 1,
    "size": 10,
    "totalElements": 100,
    "totalPages": 10
  },
  "timestamp": "2024-12-02 01:00:00"
}
```

## 🛡️ Error Handling

Tất cả lỗi đều trả về format JSON chung:

```json
{
  "success": false,
  "message": "Thông báo lỗi chi tiết",
  "data": null,
  "timestamp": "2024-12-02 01:00:00"
}
```

**HTTP Status Codes:**
- `200 OK` - Thành công
- `201 Created` - Tạo mới thành công
- `204 No Content` - Xóa thành công
- `400 Bad Request` - Dữ liệu không hợp lệ
- `401 Unauthorized` - Chưa xác thực
- `403 Forbidden` - Không có quyền
- `404 Not Found` - Không tìm thấy
- `500 Internal Server Error` - Lỗi server

## 📚 Tính năng chi tiết

### 1. **User Management (Quản lý người dùng)**
- Tạo tài khoản mới
- Cập nhật thông tin cá nhân
- Xóa tài khoản
- Phân quyền (admin/user)
- Lấy danh sách phân trang

### 2. **Video Management (Quản lý video)**
- Tải lên video mới
- Cập nhật thông tin video
- Xóa video
- Tìm kiếm video theo tiêu đề
- Theo dõi lượt xem
- Phân trang danh sách

### 3. **Favorites (Yêu thích)**
- Thêm video vào danh sách yêu thích
- Xóa khỏi yêu thích
- Xem danh sách video yêu thích
- Phân trang danh sách

### 4. **Comments (Bình luận)**
- Bình luận trên video
- Cập nhật bình luận
- Xóa bình luận
- Xem bình luận của video
- Xem bình luận của user
- Phân trang danh sách

### 5. **Share (Chia sẻ)**
- Chia sẻ video cho bạn bè
- Gửi danh sách email
- Cập nhật chia sẻ
- Xóa chia sẻ
- Xem lịch sử chia sẻ
- Phân trang danh sách

### 6. **Authentication & Authorization**
- Đăng nhập với email & password
- Token-based authentication (UUID)
- Đăng xuất
- Xác thực token
- Kiểm tra quyền admin
- Role-based access control

## 🎯 Kiến trúc Clean Architecture

```
Request → Controller (API) → Service (Business Logic) → Repository (Data Access) → Database
                ↓                    ↓                          ↓
            Validation          Conversion              JPQL Queries
            Error Handling      Pagination             Entity Mapping
```

### Các nguyên tắc:
- ✅ Separation of Concerns (Tách biệt trách nhiệm)
- ✅ Dependency Injection (Tiêm phụ thuộc)
- ✅ Single Responsibility (Một trách nhiệm duy nhất)
- ✅ Open/Closed Principle (Mở rộng, đóng sửa)
- ✅ Interface Segregation (Tách biệt interface)

## 🔄 Request/Response Flow

```
1. Client gửi HTTP Request
   ↓
2. Controller (API Servlet) nhận request
   ↓
3. Parse request body thành DTO
   ↓
4. Validate dữ liệu (Validation Helper)
   ↓
5. Gọi Service (Business Logic)
   ↓
6. Service gọi Repository (Database Access)
   ↓
7. Repository thực hiện JPQL query
   ↓
8. Trả về Entity từ database
   ↓
9. Convert Entity → Response DTO
   ↓
10. Trả về JSON Response cho client
```

## 📖 Tài liệu tham khảo

- [Jakarta EE Documentation](https://jakarta.ee/)
- [Hibernate ORM 6.4 Documentation](https://hibernate.org/orm/documentation/6.4/)
- [MariaDB Documentation](https://mariadb.com/kb/en/documentation/)
- [Maven Documentation](https://maven.apache.org/guides/)
- [Jackson JSON Documentation](https://github.com/FasterXML/jackson)
- [API_ENDPOINT.md](./API_ENDPOINT.md) - Chi tiết tất cả API endpoints

## 📄 License

MIT License - Dự án học tập
