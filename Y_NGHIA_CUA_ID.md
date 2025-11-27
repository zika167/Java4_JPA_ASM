# 🔑 ID (Primary Key) Mang Ý Nghĩa Gì?

## 🤔 Câu Hỏi Của Bạn

```
"ID ở User.java có ý nghĩa gì?
- Nó khác username như thế nào?
- Tại sao phải có cả 2?"
```

## ✅ Trả Lời

```
ID = Primary Key (Khóa Chính)
└─ Ý nghĩa: Định danh duy nhất cho bản ghi trong database

Ví dụ:
├─ ID: abc-123-def-456 (UUID - máy sinh)
├─ Username: john_doe (user nhập)
├─ Email: john@example.com
└─ Fullname: John Doe
```

---

## 🎯 ID Mang 3 Ý Nghĩa Chính

### 1️⃣ Primary Key (Khóa Chính)

**Trong Database:**
```sql
CREATE TABLE User (
    Id VARCHAR(255) PRIMARY KEY,  ← ID là khóa chính
    Username VARCHAR(255) UNIQUE,
    Email VARCHAR(255) UNIQUE,
    Password VARCHAR(255),
    ...
);
```

**Ý nghĩa:**
- ✅ Mỗi bản ghi User phải có ID duy nhất
- ✅ Không có 2 user nào có ID giống nhau
- ✅ Database sử dụng ID để index, tìm kiếm nhanh
- ✅ Khi delete, update dựa vào ID

---

### 2️⃣ Định Danh Duy Nhất (Unique Identifier)

**Trong Relationships:**
```
User (Id = abc-123)
  ↓
  └─→ Favorite (userId = abc-123)
        - Video 1
        - Video 2
  ↓
  └─→ Comment (userId = abc-123)
        - Comment 1
        - Comment 2
```

**Ví dụ Câu Query:**
```sql
-- Tìm tất cả comment của user có ID abc-123
SELECT * FROM Comment WHERE userId = 'abc-123'

-- Tìm tất cả favorite của user có ID abc-123
SELECT * FROM Favorite WHERE userId = 'abc-123'
```

---

### 3️⃣ Database Internal (Không Công Khai)

**Ý nghĩa:**
- ✅ User không cần biết ID của họ là gì
- ✅ ID là để database quản lý
- ✅ Frontend không hiển thị ID trực tiếp
- ✅ Sử dụng Username để login, không phải ID

---

## 📊 Bảng So Sánh Chi Tiết

| Tiêu Chí | ID | Username | Email |
|----------|-------|----------|-------|
| **Ví Dụ** | `abc-123-def-456` | `john_doe` | `john@example.com` |
| **Role** | Primary Key | Identifier (human) | Contact + Identifier |
| **Sinh bởi** | UUID (máy) | User nhập | User nhập |
| **Unique** | ✅ Bắt buộc | ✅ Bắt buộc | ✅ Bắt buộc |
| **User thấy** | ❌ Không | ✅ Có | ✅ Có |
| **Dùng Login** | ❌ Không | ✅ Có | ⚠️ Có thể |
| **Dùng URL** | ❌ Không | ✅ Dùng | ⚠️ Có thể |
| **Có thể đổi** | ❌ Không | ❌ Khó | ⚠️ Có |
| **Database Index** | ✅ Yes | ⚠️ Optional | ⚠️ Optional |

---

## 🔍 Ví Dụ Thực Tế

### Database Schema

```
┌─────────────────────────────────────────────┐
│ User Table                                  │
├─────────────┬────────────┬──────────────────┤
│ Id (PK)     │ Username   │ Email            │
├─────────────┼────────────┼──────────────────┤
│ uuid-001    │ john_doe   │ john@example.com │
│ uuid-002    │ jane_smith │ jane@example.com │
│ uuid-003    │ bob_jones  │ bob@example.com  │
└─────────────┴────────────┴──────────────────┘

┌────────────────────────────────────────┐
│ Favorite Table                         │
├────────┬───────────┬──────────────────┤
│ Id     │ userId    │ videoId          │
├────────┼───────────┼──────────────────┤
│ 1      │ uuid-001  │ video-101        │ ← john_doe
│ 2      │ uuid-001  │ video-102        │ ← john_doe
│ 3      │ uuid-002  │ video-103        │ ← jane_smith
└────────┴───────────┴──────────────────┘
```

**Nhận xét:**
- ✅ userId lưu ID của user (uuid-001, uuid-002)
- ❌ Không lưu username trong Favorite
- ✅ Khi need username, join 2 table lại

---

## 🔗 Relationships (ID Là Cầu Nối)

### Foreign Key (Khóa Ngoài)

```
User (One)
  │
  │ Id = uuid-001
  │
  └─→ (One-to-Many) ←─ Favorite (Many)
                        └─ userId = uuid-001

User (One)
  │
  │ Id = uuid-001
  │
  └─→ (One-to-Many) ←─ Comment (Many)
                        └─ userId = uuid-001
```

**Ý nghĩa:**
- ✅ userId trong Favorite/Comment là Foreign Key
- ✅ Nó reference tới User.Id
- ✅ Bảo đảm data integrity (Referential Integrity)

---

### SQL Example

```sql
-- Tạo Favorite table với Foreign Key
CREATE TABLE Favorite (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    userId VARCHAR(255),              ← Foreign Key
    videoId VARCHAR(255),
    likeDate TIMESTAMP,
    
    FOREIGN KEY (userId) REFERENCES User(Id)  ← Link tới User.Id
);

-- Query: Lấy tất cả video mà user uuid-001 yêu thích
SELECT v.* FROM Video v
JOIN Favorite f ON v.Id = f.videoId
WHERE f.userId = 'uuid-001';
```

---

## 💡 Tại Sao ID Là String (UUID)?

### Truyền Thống: ID Là Integer (Auto Increment)

```
❌ OLD STYLE:
CREATE TABLE User (
    Id INT PRIMARY KEY AUTO_INCREMENT,  ← 1, 2, 3, 4...
    Username VARCHAR(255),
    ...
);
```

**Vấn đề:**
- ❌ Auto increment chia sẻ trên server → Có thể predict
- ❌ Khi migrate database khó
- ❌ Không distributed friendly

---

### Modern: ID Là String (UUID)

```
✅ NEW STYLE:
CREATE TABLE User (
    Id VARCHAR(255) PRIMARY KEY,  ← abc-123-def-456 (UUID)
    Username VARCHAR(255),
    ...
);
```

**Ưu điểm:**
- ✅ UUID tạo từ máy, không predict được
- ✅ Mỗi máy tạo ID khác nhau (distributed)
- ✅ Không cần server generate
- ✅ An toàn hơn

---

## 📝 Khi Nào Dùng ID?

### ✅ Dùng ID Khi:

1. **Query từ Database**
   ```java
   // UserRepoImpl
   User user = em.find(User.class, "uuid-001");
   ```

2. **Foreign Key trong Relationships**
   ```java
   @ManyToOne
   @JoinColumn(name = "userId")
   private User user;  // userId = user.id
   ```

3. **Delete/Update bản ghi**
   ```sql
   DELETE FROM User WHERE Id = 'uuid-001';
   UPDATE User SET Email = '...' WHERE Id = 'uuid-001';
   ```

4. **Internal Database Operations**

---

### ❌ KHÔNG Dùng ID Khi:

1. **Login**
   ```
   ❌ Login: uuid-001 / password (quá dài, quá khó)
   ✅ Login: john_doe / password
   ```

2. **URL Route**
   ```
   ❌ /users/uuid-001 (xấu, dài)
   ✅ /users/john_doe (đẹp, ngắn)
   ```

3. **Display cho User**
   ```
   ❌ "Posted by: uuid-001" (lạ)
   ✅ "Posted by: john_doe" (tự nhiên)
   ```

4. **Search/Filter**
   ```
   ❌ Search: uuid-001 (không có ý nghĩa)
   ✅ Search: john_doe (tìm user)
   ```

---

## 🎬 Flow: ID Được Sử Dụng Ở Đâu?

### 1. User Tạo Tài Khoản

```
Frontend:
  POST /api/users
  {
    "username": "john_doe",
    "email": "john@example.com",
    "password": "abc123456",
    "fullName": "John Doe"
    // ❌ KHÔNG gửi ID
  }

Backend (UserServiceImpl):
  User user = new User();
  user.setId(UUID.randomUUID().toString());  ← Sinh UUID
  user.setUsername("john_doe");
  user.setEmail("john@example.com");
  userRepo.save(user);

Database:
  INSERT INTO User VALUES ('uuid-001', 'john_doe', 'john@example.com', ...);
```

---

### 2. User Login

```
Frontend:
  POST /api/auth/login
  {
    "username": "john_doe",
    "password": "abc123456"
    // ❌ KHÔNG dùng ID
  }

Backend (AuthService):
  User user = userRepo.findByUsername("john_doe");  ← Tìm bằng username
  if (user != null && checkPassword(password, user.getPassword())) {
    // Return JWT token có chứa user.id
    return {
      "token": "eyJhbGc...",
      "user": {
        "id": "uuid-001",      ← Backend lấy id từ database
        "username": "john_doe"
      }
    }
  }

Frontend lấy user.id để:
  - Lưu trong localStorage
  - Dùng trong subsequent API calls
```

---

### 3. User Thích Video (Create Favorite)

```
Frontend:
  POST /api/favorites
  {
    "userId": "uuid-001",    ← Gửi user id (có từ token)
    "videoId": "video-101"
  }

Backend (FavoriteServiceImpl):
  Optional<User> user = userRepo.findById("uuid-001");  ← Tìm bằng ID
  if (user.isEmpty()) throw "User not found";
  
  Favorite fav = new Favorite();
  fav.setUserId("uuid-001");    ← Lưu user id
  fav.setVideoId("video-101");
  favoriteRepo.save(fav);

Database:
  INSERT INTO Favorite VALUES (1, 'uuid-001', 'video-101', ...);
```

---

### 4. Lấy Favorite Của User

```
Frontend:
  GET /api/favorites/user/uuid-001    ← Dùng user id

Backend (FavoriteServiceImpl):
  List<Favorite> favs = favoriteRepo.findByUserId("uuid-001");
  
  for (Favorite fav : favs) {
    User user = userRepo.findById(fav.getUserId());  ← Tìm bằng ID
    Video video = videoRepo.findById(fav.getVideoId());
    // Create response
  }

SQL:
  SELECT * FROM Favorite WHERE userId = 'uuid-001';
```

---

## 🎯 Tóm Tắt: ID Là Gì?

### ID (Primary Key)

```
Định Nghĩa:
  "Một giá trị duy nhất để identify một bản ghi trong database"

Ý Nghĩa:
  1. Primary Key (khóa chính để index)
  2. Foreign Key (kết nối relationships)
  3. Internal Database ID (không công khai)

Ví Dụ:
  ID: abc-123-def-456 (UUID)

Sinh Bởi:
  Backend (UUID.randomUUID())

Dùng Để:
  - Query database
  - Foreign key
  - Delete/Update
  - Internal operations

Không Dùng Để:
  - Login
  - URL
  - Display
  - User interaction
```

---

## 📊 So Sánh: ID vs Username vs Email

| Mục Đích | Trường | Ví Dụ |
|----------|--------|--------|
| **Database Management** | ID | `abc-123-def-456` |
| **User Authentication** | Username | `john_doe` |
| **User Contact** | Email | `john@example.com` |

**Tất cả 3 đều Unique (duy nhất), nhưng mục đích khác nhau!**

---

## 🎓 Câu Hỏi Thường Gặp

### Q: Tại sao phải có cả ID lẫn Username?

A: **Khác mục đích**
```
ID  → Database management (internal)
      └─ Không user biết
      
Username → User authentication (external)
      └─ User biết, user dùng login
```

---

### Q: Tôi có thể dùng Username làm Primary Key không?

A: **Có được, nhưng không nên**

**Ưu điểm:**
- Tiết kiệm 1 column
- Đơn giản hơn

**Nhược điểm:**
- ❌ Username có thể đổi → Update FK ở toàn DB khó
- ❌ Không follow best practice
- ❌ Email/Phone cũng unique → Nên dùng ID

**Khuyến khích:** Luôn dùng ID là PK

---

### Q: Có thể dùng Email làm Primary Key không?

A: **Cũng không nên**

```
Email có thể:
  - Người dùng đổi
  - Người dùng xóa
  - Invalid email cũ
  
→ Update FK ở toàn DB khó
```

**Khuyến khích:** Luôn dùng ID (UUID hoặc Auto Increment) là PK

---

### Q: ID và UUID khác nhau gì?

A: **UUID là 1 loại ID**

```
ID (general)
├─ Auto Increment (1, 2, 3, ...) ← OLD
├─ UUID (abc-123-...) ← MODERN  ← Dự án này dùng
├─ ULID (...)
└─ GUID (...)

UUID (Universally Unique Identifier)
  - Format: xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
  - Ví dụ: 550e8400-e29b-41d4-a716-446655440000
  - Distributed, không predict được
```

---

## 🚀 Kết Luận

### ID Mang Ý Nghĩa:

```
"Khóa chính duy nhất để database quản lý và kết nối relationships"

Sinh tự động → UUID
    ↓
Lưu vào database
    ↓
Dùng khi:
  - Query, update, delete
  - Foreign key
  - Identification

Không dùng khi:
  - Login (dùng username)
  - URL (dùng username)
  - Display (dùng fullname/username)
```

---

## 📚 Sơ Đồ Tổng Quan

```
User Entity:
├─ ID: uuid-001 (sinh tự động)          → Database management
├─ Username: john_doe                   → User authentication
├─ Email: john@example.com              → Contact + recovery
├─ Fullname: John Doe                   → Display
├─ Password: hashed                     → Authentication
└─ Admin: true                          → Authorization

Relationships (dùng ID):
├─ Favorite.userId = uuid-001 (Foreign Key)
├─ Comment.userId = uuid-001 (Foreign Key)
└─ Share.userId = uuid-001 (Foreign Key)

Frontend:
├─ Login: POST /api/auth/login (username + password)
├─ Create favorite: POST /api/favorites (userId + videoId)
├─ Get user profile: GET /api/users/john_doe (username)
└─ Display: "Posted by john_doe" (username, không ID)
```

**Rõ ràng chưa?** 🎉
