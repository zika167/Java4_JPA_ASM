# Danh sách API Endpoints
## Base URL
http://localhost:8080/
## Auth API (/api/auth)
- **POST /api/auth/login** - Đăng nhập
  ### JSON Request Body
  ```json
  {
    "email": "user@example.com",
    "password": "password123"
  }
  ```
  ### JSON Response Body
  ```json
  {
    "id": "user001",
    "email": "user@example.com",
    "fullname": "John Doe",
    "admin": false,
    "token": "550e8400-e29b-41d4-a716-446655440000",
    "createdDate": "2024-12-02 00:00:00"
  }
  ```

- **POST /api/auth/logout** - Đăng xuất
  ### Header
  ```
  Authorization: Bearer <token>
  ```
  ### Response
  ```json
  {
    "success": true,
    "message": "Đăng xuất thành công",
    "data": 1,
    "timestamp": "2024-12-02 00:52:00"
  }
  ```

- **GET /api/auth/validate** - Xác thực token
  ### Header
  ```
  Authorization: Bearer <token>
  ```
  ### Response (Valid Token)
  ```json
  {
    "success": true,
    "message": "Token hợp lệ",
    "data": "user001",
    "timestamp": "2024-12-02 00:52:00"
  }
  ```

- **GET /api/auth/admin** - Kiểm tra quyền admin
  ### Header
  ```
  Authorization: Bearer <token>
  ```
  ### Response (Is Admin)
  ```json
  {
    "success": true,
    "message": "Bạn có quyền admin",
    "data": true,
    "timestamp": "2024-12-02 00:52:00"
  }
  ```

- **POST /api/auth/register** - Đăng ký
  ### JSON Request Body
  ```json
  {
    "id": "ps45143",
    "fullName": "John Doe",
    "email": "user@example.com",
    "password": "password123",
    "confirmPassword": "password123",
    "admin": false
  }
  ```
  ### JSON Response Body
  ```json
  {
    "id": "ps45143",
    "fullName": "John Doe",
    "email": "user@example.com",
    "admin": false,
    "createdDate": "2024-12-02 00:00:00"
  }
  ```

- **POST /api/auth/change-password** - Đổi mật khẩu
  ### Header
  ```
  Authorization: Bearer <token>
  ```
  ### JSON Request Body
  ```json
  {
    "oldPassword": "oldpassword",
    "newPassword": "newpassword",
    "confirmPassword": "newpassword"
  }
  ```
  ### Response
  ```json
  {
    "success": true,
    "message": "Đổi mật khẩu thành công",
    "data": 1,
    "timestamp": "2024-12-02 00:52:00"
  }
  ```

## User API (/api/users)
- **GET /api/users?page=1&size=10** - Lấy danh sách user (phân trang)
- **GET /api/users/{id}** - Lấy thông tin user theo ID
- **GET /api/users/email/{email}** - Lấy thông tin user theo email
- **POST /api/users** - Tạo user mới
  ### JSON Request Body
  ```json
  {
    "id": "ps45143",
    "email": "user@example.com",
    "password": "password123",
    "fullName": "John Doe",
    "admin": true
  }
  ```
- **PUT /api/users/{id}** - Cập nhật thông tin user
  ### JSON Request Body
  ```json
  {
    "id": "ps45143",
    "email": "user@example.com",
    "password": "password123",
    "fullName": "John Doe",
    "admin": false
  }
  ```
- **DELETE /api/users/{id}** - Xóa user

## Video API (/api/videos)
- **GET /api/videos?page=1&size=10** - Lấy danh sách video (phân trang)
- **GET /api/videos/{id}** - Lấy video theo ID
- **GET /api/videos/user/{userId}** - Lấy video theo user
- **GET /api/videos/search?q=keyword** - Tìm kiếm video
- **POST /api/videos** - Tạo video mới
  ### JSON Request Body
  ```json
  {
    "id": "vid123",
    "title": "Video Title",
    "poster": "poster.jpg",
    "description": "Video description",
    "active": true,
    "userId": "user001"
  }
  ```
- **PUT /api/videos/{id}** - Cập nhật video
  ### JSON Request Body
  ```json
  {
    "id": "vid123",
    "title": "Skibidi",
    "poster": "poster.jpg",
    "description": "Video description",
    "active": true,
    "userId": "user001"
  }
  ```
- **DELETE /api/videos/{id}** - Xóa video
- **POST /api/videos/{id}/view** - Tăng lượt xem video

## Favorite API (/api/favorites)
- **GET /api/favorites?page=1&size=10** - Lấy tất cả yêu thích (phân trang)
- **GET /api/favorites/{id}** - Lấy yêu thích theo ID
- **GET /api/favorites/user/{userId}?page=1&size=10** - Lấy yêu thích theo user (phân trang)
- **POST /api/favorites** - Tạo yêu thích mới
  ### JSON Request Body
  ```json
  {
    "user": {
      "id": "user001"
    },
    "video": {
      "id": "vid001"
    }
  }
  ```
  ### JSON Response Body
  ```json
  {
    "id": 1,
    "userId": "user001",
    "videoId": "vid001",
    "likeDate": "2024-12-02 00:16:30"
  }
  ```
- **PUT /api/favorites/{id}** - Cập nhật yêu thích
  ### JSON Request Body
  ```json
  {
    "user": {
      "id": "user001"
    },
    "video": {
      "id": "vid001"
    }
  }
  ```
  ### JSON Response Body
  ```json
  {
    "id": 1,
    "userId": "user001",
    "videoId": "vid001",
    "likeDate": "2024-12-02 00:16:30"
  }
  ```
- **DELETE /api/favorites/{id}** - Xóa yêu thích

## Comment API (/api/comments)
- **GET /api/comments?page=1&size=10** - Lấy tất cả comment (phân trang)
- **GET /api/comments/{id}** - Lấy comment theo ID
- **GET /api/comments/user/{userId}?page=1&size=10** - Lấy comment theo user (phân trang)
- **GET /api/comments/video/{videoId}?page=1&size=10** - Lấy comment theo video (phân trang)
- **POST /api/comments** - Tạo comment mới
  ### JSON Request Body
  ```json
  {
    "user": {
      "id": "user001"
    },
    "video": {
      "id": "vid001"
    },
    "content": "Nice video!"
  }
  ```
  ### JSON Response Body
  ```json
  {
    "id": 1,
    "userId": "user001",
    "videoId": "vid001",
    "content": "Nice video!",
    "createdDate": "2024-12-02 00:26:30",
    "updatedDate": "2024-12-02 00:26:30"
  }
  ```
- **PUT /api/comments/{id}** - Cập nhật comment
  ### JSON Request Body
  ```json
  {
    "user": {
      "id": "user001"
    },
    "video": {
      "id": "vid001"
    },
    "content": "Updated comment"
  }
  ```
  ### JSON Response Body
  ```json
  {
    "id": 1,
    "userId": "user001",
    "videoId": "vid001",
    "content": "Updated comment",
    "createdDate": "2024-12-02 00:26:30",
    "updatedDate": "2024-12-02 00:27:00"
  }
  ```
- **DELETE /api/comments/{id}** - Xóa comment

## Share API (/api/shares)
- **GET /api/shares?page=1&size=10** - Lấy tất cả chia sẻ (phân trang)
- **GET /api/shares/{id}** - Lấy chia sẻ theo ID
- **GET /api/shares/user/{userId}?page=1&size=10** - Lấy chia sẻ theo user (phân trang)
- **GET /api/shares/video/{videoId}?page=1&size=10** - Lấy chia sẻ theo video (phân trang)
- **POST /api/shares** - Tạo chia sẻ mới
  ### JSON Request Body
  ```json
  {
    "user": {
      "id": "user001"
    },
    "video": {
      "id": "vid001"
    },
    "emails": "user1@example.com,user2@example.com"
  }
  ```
  ### JSON Response Body
  ```json
  {
    "id": 1,
    "userId": "user001",
    "videoId": "vid001",
    "emails": "user1@example.com,user2@example.com",
    "shareDate": "2024-12-02 00:36:30"
  }
  ```
- **PUT /api/shares/{id}** - Cập nhật chia sẻ
  ### JSON Request Body
  ```json
  {
    "user": {
      "id": "user001"
    },
    "video": {
      "id": "vid001"
    },
    "emails": "user3@example.com,user4@example.com"
  }
  ```
  ### JSON Response Body
  ```json
  {
    "id": 1,
    "userId": "user001",
    "videoId": "vid001",
    "emails": "user3@example.com,user4@example.com",
    "shareDate": "2024-12-02 00:36:45"
  }
  ```
- **DELETE /api/shares/{id}** - Xóa chia sẻ

## Ghi chú
- Tất cả API trả về JSON response với format thống nhất.
- Sử dụng phân trang cho các endpoint lấy danh sách.
- Xác thực và phân quyền có thể được thêm vào sau.