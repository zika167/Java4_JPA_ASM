# 🚀 Java 4 Assignment - ASM

## 📋 Mô tả dự án

Dự án được xây dựng nhằm mục đích học tập và thực hành môn Java 4, sử dụng kiến trúc **REST API** với:

- **Java Servlet** (Jakarta EE 10+) - xử lý HTTP requests
- **Hibernate 6.x** (JPA 3.1) - ORM mapping
- **MariaDB** - cơ sở dữ liệu
- **Maven** - build tool

Dự án tuân theo **Clean Architecture** với các layer tách biệt: Controller → Service → Repository → Database

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

## 📁 Hướng dẫn tổ chức thư mục dự án

### Cấu trúc thư mục chính

```
java4_asm/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/fpt/java4_asm/
│   │   │       ├── controllers/    # Chứa các Servlet xử lý request
│   │   │       │   ├── api/       # API Controllers
│   │   │       │   └── web/       # Web Controllers (nếu có)
│   │   │       │
│   │   │       ├── convert/          # Convert dữ liệu giữ entity và dto
│   │   │       │
│   │   │       ├── dto/                # Chuyển đổi dữ liệu
│   │   │       │   ├── request/       # Dữ liệu yêu cầu vào
│   │   │       │   └── response/      # Dữ liệu trả về
│   │   │       │
│   │   │       ├── exception/          # Xử lý ngoại lệ
│   │   │       │
│   │   │       ├── models/         # Các entity JPA
│   │   │       │   └── entities/  # Định nghĩa các bảng database
│   │   │       │
│   │   │       ├── repositories/   # Interface truy vấn database
│   │   │       │   ├── impl/      # Triển khai xử lý logic database
│   │   │       │
│   │   │       ├── services/      # Interface logic nghiệp vụ
│   │   │       │   ├── impl/     # Triển khai xử lý logic nghiệp vụ
│   │   │       │
│   │   │       └── utils/         # Các tiện ích
│   │   │           ├── helpers/  # Các lớp hỗ trợ
│   │   │           └── constants/ # Các hằng số
│   │   │
│   │   ├── resources/
│   │   │   ├── META-INF/         # Cấu hình JPA/Hibernate
│   │   │   │   └── persistence.xml
│   │   │   ├── log4j2.xml       # Cấu hình logging
│   │   │   └── messages/        # File message đa ngôn ngữ (nếu có)
│   │   │
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   ├── web.xml      # Cấu hình ứng dụng web
│   │       │   └── views/       # Các file JSP (nếu có)
│   │       └── assets/          # Tài nguyên tĩnh (CSS, JS, images)
│   │
│   └── test/                    # Test cases
│       ├── java/               # Test source code
│       └── resources/          # Tài nguyên cho test
│
├── .gitignore
├── pom.xml                    # Cấu hình Maven
└── README.md
```

### Mô tả chi tiết các thư mục

#### 1. `src/main/java/com/fpt/java4_asm/`

- **`controllers/`**: Chứa các lớp Servlet xử lý request

  - `api/`: Xử lý các API trả về JSON
  - `web/`: Xử lý các request trả về view (nếu có)

- **`models/`**: Chứa các entity JPA

  - `entities/`: Định nghĩa các bảng database
  - `dtos/`: Các đối tượng truyền dữ liệu (nếu cần)

- **`repositories/`**: Interface truy vấn database

  - `impl/`: Triển khai cụ thể (nếu cần)

- **`services/`**: Interface xử lý logic nghiệp vụ

  - `impl/`: Triển khai các service

- **`utils/`**: Các tiện ích hỗ trợ
  - `helpers/`: Các lớp hỗ trợ
  - `constants/`: Các hằng số
  - `HibernateUtil.java`: Quản lý EntityManagerFactory

#### 2. `src/main/resources/`

- **`META-INF/`**: Cấu hình JPA/Hibernate
- **`log4j2.xml`**: Cấu hình logging
- **`messages/`**: File đa ngôn ngữ (nếu có)

#### 3. `src/main/webapp/`

- **`WEB-INF/`**:
  - `web.xml`: Cấu hình ứng dụng web
  - `views/`: Các file JSP (nếu có)
- **`assets/`**:
  - `css/`: File CSS
  - `js/`: File JavaScript
  - `images/`: Hình ảnh

#### 4. `src/test/`

- Chứa các test case cho ứng dụng
- Cấu trúc tương tự thư mục `main`

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

import com.fpt.java4_asm.services.BaseService;
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
import com.fpt.java4_asm.services.BaseService;
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

### Người dùng (User)

- `GET    /api/users` - Lấy danh sách người dùng
- `GET    /api/users?id=1` - Lấy thông tin chi tiết người dùng
- `POST   /api/users` - Tạo mới người dùng
- `PUT    /api/users?id=1` - Cập nhật thông tin người dùng
- `DELETE /api/users?id=1` - Xóa người dùng

## 🔧 Các class hỗ trợ chính

### BaseApiServlet

- Cung cấp các method utility cho việc ghi response JSON
- Xử lý parse request body
- Quản lý HTTP status codes

### HibernateUtil

- Quản lý EntityManagerFactory
- Cung cấp EntityManager cho các repository

### ValidationHelper

- Validate dữ liệu đầu vào
- Kiểm tra email, phone, ID hợp lệ

### ApiResponse<T>

- Generic response wrapper
- Factory methods: `success()`, `error()`

### AppException

- Custom exception cho ứng dụng
- Mapping error code sang HTTP status

## 📝 Tài liệu tham khảo

- [Jakarta EE Documentation](https://jakarta.ee/)
- [Hibernate ORM 6.4 Documentation](https://hibernate.org/orm/documentation/6.4/)
- [MariaDB Documentation](https://mariadb.com/kb/en/documentation/)
- [Maven Documentation](https://maven.apache.org/guides/)
- [Jackson JSON Documentation](https://github.com/FasterXML/jackson)

## 📄 License

MIT License - Dự án học tập
