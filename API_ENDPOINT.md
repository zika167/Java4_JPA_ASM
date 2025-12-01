# Danh sách API Endpoints

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
    "title": "Video Title",
    "poster": "poster.jpg",
    "description": "Video description",
    "active": true,
    "userId": "user123"
  }
  ```
- **PUT /api/videos/{id}** - Cập nhật video
  ### JSON Request Body
  ```json
  {
    "title": "Video Title",
    "poster": "poster.jpg",
    "description": "Video description",
    "active": true
  }
  ```
- **DELETE /api/videos/{id}** - Xóa video
- **POST /api/videos/{id}/view** - Tăng lượt xem video

## Favorite API (/api/favorites)
- **GET /api/favorites?page=1&size=10** - Lấy tất cả yêu thích (phân trang)
- **GET /api/favorites/{id}** - Lấy yêu thích theo ID
- **GET /api/favorites/user/{userId}** - Lấy yêu thích theo user (phân trang)
- **POST /api/favorites** - Tạo yêu thích mới
  ### JSON Request Body
  ```json
  {
    "userId": "user123",
    "videoId": "vid123"
  }
  ```
- **PUT /api/favorites/{id}** - Cập nhật yêu thích
  ### JSON Request Body
  ```json
  {
    "userId": "user123",
    "videoId": "vid123"
  }
  ```
- **DELETE /api/favorites/{id}** - Xóa yêu thích

## Comment API (/api/comments)
- **GET /api/comments?page=1&size=10** - Lấy tất cả comment (phân trang)
- **GET /api/comments/{id}** - Lấy comment theo ID
- **GET /api/comments/user/{userId}** - Lấy comment theo user (phân trang)
- **GET /api/comments/video/{videoId}** - Lấy comment theo video (phân trang)
- **POST /api/comments** - Tạo comment mới
  ### JSON Request Body
  ```json
  {
    "userId": "user123",
    "videoId": "vid123",
    "content": "Nice video!"
  }
  ```
- **PUT /api/comments/{id}** - Cập nhật comment
  ### JSON Request Body
  ```json
  {
    "content": "Updated comment"
  }
  ```
- **DELETE /api/comments/{id}** - Xóa comment

## Share API (/api/shares)
- **GET /api/shares** - Lấy danh sách chia sẻ
- **GET /api/shares/{id}** - Lấy chia sẻ theo ID
- **POST /api/shares** - Tạo chia sẻ mới
  ### JSON Request Body
  ```json
  {
    "userId": "user123",
    "videoId": "vid123",
    "emails": "friend@example.com"
  }
  ```
- **PUT /api/shares/{id}** - Cập nhật chia sẻ
  ### JSON Request Body
  ```json
  {
    "emails": "updated@example.com"
  }
  ```
- **DELETE /api/shares/{id}** - Xóa chia sẻ
- **GET /api/shares/video/{videoId}** - Lấy chia sẻ theo video
- **GET /api/shares/user/{userId}** - Lấy chia sẻ theo user

## Ghi chú
- Tất cả API trả về JSON response với format thống nhất.
- Sử dụng phân trang cho các endpoint lấy danh sách.
- Xác thực và phân quyền có thể được thêm vào sau.