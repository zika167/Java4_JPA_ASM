# 📁 Cấu trúc dự án chi tiết

## Các file/folder đã tạo mới

### 1. **Config Package** (`config/`)
- `HibernateUtil.java` - Quản lý EntityManagerFactory, cung cấp EntityManager

### 2. **Filter Package** (`filter/`)
- `CorsFilter.java` - Xử lý CORS cho phép frontend gọi API từ domain khác

### 3. **Exception Package** (cập nhật)
- `Error.java` - Enum định nghĩa các error code
- `AppException.java` - Custom exception với mapping HTTP status
- `GlobalExceptionHandler.java` - (sẵn có)

### 4. **DTO Response** (cập nhật)
- `ApiResponse.java` - Generic response wrapper với factory methods
- `PaginatedResponse.java` - Response cho dữ liệu phân trang

### 5. **Utils Package**
- `constants/ApiConstants.java` - Hằng số API (paths, messages, status)
- `helpers/ValidationHelper.java` - Validate email, phone, ID, string

### 6. **Base Classes**
- `controllers/BaseApiServlet.java` - Base servlet với utility methods
- `repositories/BaseRepository.java` - Interface CRUD chung
- `services/BaseService.java` - Interface service chung

### 7. **Configuration Files** (cập nhật)
- `web.xml` - Cấu hình web app (session, error pages)
- `persistence.xml` - Cấu hình Hibernate (connection pool, dialect)

## Luồng xử lý API

```
Request từ Frontend
    ↓
CorsFilter (xử lý CORS)
    ↓
BaseApiServlet (doGet/doPost/doPut/doDelete)
    ↓
Service (xử lý logic)
    ↓
Repository (truy vấn database)
    ↓
Hibernate/JPA (ORM)
    ↓
MariaDB
    ↓
Response (ApiResponse<T>)
    ↓
Frontend nhận JSON
```

## Quy trình tạo API mới

### 1. Tạo Entity
```
models/entities/Product.java
```

### 2. Tạo DTO (nếu cần)
```
dto/request/CreateProductRequest.java
dto/response/ProductResponse.java
```

### 3. Tạo Repository
```
repositories/impl/ProductRepository.java (implements BaseRepository)
```

### 4. Tạo Service
```
services/impl/ProductService.java (implements BaseService)
```

### 5. Tạo Servlet
```
controllers/api/ProductServlet.java (extends BaseApiServlet)
```

## Ví dụ sử dụng các class hỗ trợ

### ApiResponse
``` java
// Success
ApiResponse.success(data, "Tạo mới thành công")

// Error
ApiResponse.error("Dữ liệu không hợp lệ")
```

### ValidationHelper
``` java
ValidationHelper.validateNotEmpty(email, "Email");
ValidationHelper.validateId(id);
Long id = ValidationHelper.parseAndValidateId(idStr);
```

### BaseApiServlet
``` java
sendSuccessResponse(response, data);
sendBadRequest(response, "Invalid input");
sendNotFound(response, "Not found");
sendInternalServerError(response, ex.getMessage());
```

## Dependencies chính

- **jakarta.servlet-api** - Servlet API
- **hibernate-core** - ORM
- **mariadb-java-client** - Database driver
- **jackson-databind** - JSON serialization
- **lombok** - Reduce boilerplate
- **junit-jupiter** - Testing

## Cấu hình quan trọng

### persistence.xml
- Database URL, user, password
- Hibernate dialect (MariaDBDialect)
- Connection pool (HikariCP)
- DDL strategy (update)

### web.xml
- Session configuration
- Error page mapping
- Welcome files

## Best Practices áp dụng

1. **Separation of Concerns** - Tách biệt Controller, Service, Repository
2. **Generic Programming** - Sử dụng Generics cho Base classes
3. **Exception Handling** - Custom exception với HTTP status mapping
4. **Validation** - Validate dữ liệu đầu vào
5. **Consistent Response Format** - Tất cả API trả về ApiResponse<T>
6. **CORS Support** - Filter xử lý cross-origin requests
7. **Lombok** - Giảm boilerplate code
