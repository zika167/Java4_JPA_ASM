# 🔍 ID vs Username Trong Database - Phân Tích

## 🤔 Câu Hỏi Của Bạn

```
"Tôi thấy trong DB User:
- Có trường 'Id' (String)
- Có trường 'Fullname'
- KHÔNG CÓ trường 'Username'

Liệu ID chính là Username rồi?
Hay đây là thiếu sót?"
```

## ✅ Trả Lời: ĐÂY LÀ THIẾU SÓT!

```
❌ ID ≠ Username

Database hiện tại THIẾU trường Username!

Phải THÊM trường Username vào User Entity
```

---

## 📊 Hiện Trạng Database

### User Entity Hiện Tại

```java
@Entity
@Table(name = "User")
public class User {
    @Id
    @Column(name = "Id")
    private String id;              // ← ID (Primary Key)
    
    @Column(name = "Password")
    private String password;
    
    @Column(name = "Email")
    private String email;
    
    @Column(name = "Fullname")      // ← ĐÂY (Fullname, không Username)
    private String fullname;
    
    @Column(name = "Admin")
    private Boolean admin;
    
    @Column(name = "CreatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    
    @Column(name = "UpdatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    
    // ❌ THIẾU: Username
}
```

### Database Schema Hiện Tại

```sql
CREATE TABLE User (
    Id          VARCHAR(255) PRIMARY KEY,
    Password    VARCHAR(255),
    Email       VARCHAR(255),
    Fullname    VARCHAR(255),        -- ← Fullname (không Username)
    Admin       BOOLEAN,
    CreatedDate TIMESTAMP,
    UpdatedDate TIMESTAMP
)
```

---

## 🚨 Tại Sao Cần Username?

### Lý Do 1: Login

```
❌ NẾU KHÔNG CÓ USERNAME:
  Login: Id / Password
  → User phải nhớ ID (abc-123-def) - quá khó!

✅ NẾU CÓ USERNAME:
  Login: Username / Password
  → User nhớ username (john_doe) - dễ!
```

---

### Lý Do 2: Duplicate Check

```
❌ NẾU KHÔNG CÓ USERNAME:
  Làm sao check duplicate? Không có gì để check
  
✅ NẾU CÓ USERNAME:
  Check: SELECT * FROM User WHERE Username = 'john_doe'
  → Tránh trùng username
```

---

### Lý Do 3: URL Đẹp

```
❌ KHÔNG CÓ USERNAME:
  /users/abc-123-def        (xấu, khó nhớ)
  
✅ CÓ USERNAME:
  /users/john_doe          (đẹp, dễ nhớ)
```

---

### Lý Do 4: Fullname ≠ Username

```
Fullname có thể:
  - Trùng với người khác
  - Có khoảng trắng
  - Có accent (Nguyễn Văn A)
  - Không phù hợp làm ID

Username phải:
  - Duy nhất (Unique)
  - Không có khoảng trắng
  - Chỉ lowercase + số + underscore
  - Phù hợp làm login
```

---

## 🔧 Cách Sửa: Thêm Username Vào User Entity

### Bước 1: Sửa User.java

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

    // ✅ THÊM Username
    @Column(name = "Username", unique = true, nullable = false)
    private String username;

    @Column(name = "Email", unique = true, nullable = false)
    private String email;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "Fullname")
    private String fullname;

    @Column(name = "Admin")
    private Boolean admin;

    @Column(name = "CreatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "UpdatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    // ✅ THÊM confirmPassword (@Transient)
    @Transient
    private String confirmPassword;
}
```

---

### Bước 2: Xóa Dữ Liệu Cũ (Optional)

Vì User table đã có dữ liệu cũ, có 2 cách:

#### Cách A: Xóa Table và Recreate (Nếu là dev)

```sql
-- Xóa relationships trước (nếu có)
DELETE FROM Favorite WHERE userId IN (SELECT Id FROM User);
DELETE FROM Comment WHERE userId IN (SELECT Id FROM User);
DELETE FROM Share WHERE userId IN (SELECT Id FROM User);

-- Xóa User table
DROP TABLE User;

-- Hibernate sẽ tạo lại từ Entity
```

**Lưu ý:** Sau khi xóa, restart Tomcat → Hibernate sẽ tạo lại table

---

#### Cách B: Add Column (Nếu có dữ liệu quan trọng)

```sql
-- Add Username column
ALTER TABLE User ADD COLUMN Username VARCHAR(255) UNIQUE NOT NULL DEFAULT 'user_' + CAST(UNIX_TIMESTAMP() AS CHAR);

-- Sau đó update values
UPDATE User SET Username = CONCAT('user_', Id) WHERE Username IS NULL;

-- Remove DEFAULT
ALTER TABLE User MODIFY Username VARCHAR(255) UNIQUE NOT NULL;
```

---

## 🎯 ID vs Username - Tóm Tắt

### ID (String)

```
Ví dụ: abc-123-def
├─ Sinh tự động (UUID)
├─ Database internal
├─ Primary Key
├─ Mục đích: Index database
└─ User không cần biết
```

### Username (String)

```
Ví dụ: john_doe
├─ User nhập
├─ Dùng login
├─ Phải Unique
├─ Mục đích: Authentication
└─ User dùng hàng ngày
```

### Fullname (String)

```
Ví dụ: John Doe
├─ User nhập
├─ Dùng display
├─ Có thể trùng
├─ Mục đích: Display name
└─ Có thể để trống
```

---

## 📊 Bảng So Sánh

| Trường | Là PK? | Unique? | User Nhập? | Dùng Để |
|-------|--------|--------|-----------|---------|
| **Id** | ✅ Có | ✅ Duy nhất | ❌ Không | Database index |
| **Username** | ❌ Không | ✅ Duy nhất | ✅ Có | Login, URL |
| **Email** | ❌ Không | ✅ Duy nhất | ✅ Có | Contact, recovery |
| **Fullname** | ❌ Không | ❌ Có thể trùng | ✅ Có | Display |

---

## ✅ Kết Luận

### Hiện Trạng:

```
❌ Database THIẾU Username
✅ Database CÓ Fullname, Email, Password, Admin
```

### Phải Làm:

```
1. ✅ Thêm username column vào User table
2. ✅ Thêm username field vào User Entity
3. ✅ Thêm @Column(unique = true, nullable = false)
4. ✅ Thêm unique constraint ở DB
5. ✅ Update persistence.xml (đã setting hbm2ddl.auto = update)
```

### Khi Nào Thay Đổi:

```
Nếu chạy Hibernate với hbm2ddl.auto = update:
├─ Hibernate sẽ tự add column Username
└─ Nhưng phải restart Tomcat

Nếu muốn manual:
├─ Chạy ALTER TABLE
└─ Hoặc xóa table + recreate (dev environment)
```

---

## 🛠️ Action Plan

### Ngay Bây Giờ:

1. ✅ Cập nhật User.java (thêm username field)
2. ✅ Rebuild project (maven clean install)
3. ✅ Restart Tomcat
4. ✅ Hibernate sẽ add column Username tự động

### Sau Đó:

```
Database sẽ có:
┌──────────────────────────────────────┐
│ User Table                           │
├──────┬──────┬────┬─────┬───────┬───┤
│ Id   │ Username │Email│ Password │...
├──────┼──────┼────┼─────┼───────┼───┤
│ uuid1│john  │... │ hash│       │...
│ uuid2│jane  │... │ hash│       │...
└──────┴──────┴────┴─────┴───────┴───┘
```

---

## 📝 Lưu Ý Quan Trọng

### Khi Thêm Username Vào Entity:

```java
@Column(name = "Username", unique = true, nullable = false)
private String username;
```

**Giải thích:**
- `unique = true` → Database sẽ tạo unique constraint
- `nullable = false` → Không được null

### Persistence.xml Settings:

```xml
<property name="hibernate.hbm2ddl.auto" value="update"/>
```

**Ý nghĩa:**
- `update` → Hibernate sẽ update schema khi detect thay đổi
- Sẽ ADD column Username nếu Entity thay đổi
- Không xóa data cũ

---

## 🎯 Câu Hỏi Thường Gặp

### Q: Tôi có xóa username data cũ không?

A: **Không cần!** Hibernate sẽ:
1. Detect thay đổi Entity
2. Add column Username
3. Để giá trị cũ như bình thường
4. Chỉ update khi user mới create

### Q: Dữ liệu cũ sẽ bị mất không?

A: **Không**, vì `hbm2ddl.auto = update`
- Chỉ add column
- Không xóa data
- Không modify column cũ

### Q: User cũ sẽ login bằng gì?

A: **Tùy thuộc bạn:**
- Cách 1: Generate username từ id (user_abc123def)
- Cách 2: Cho user reset password và tạo username mới
- Cách 3: Dùng email để login thay username

---

## 📚 Ví Dụ Hoàn Chỉnh

### Sau khi thêm Username:

```java
@Entity
@Table(name = "User")
public class User {
    @Id
    @Column(name = "Id")
    private String id;

    @Column(name = "Username", unique = true, nullable = false)
    private String username;      // ← NEW!

    @Column(name = "Email", unique = true, nullable = false)
    private String email;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "Fullname")
    private String fullname;

    @Column(name = "Admin")
    private Boolean admin;

    @Column(name = "CreatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "UpdatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @Transient
    private String confirmPassword;  // ← NEW!
}
```

---

## 🎬 Kết Luận

### Tình Trạng Hiện Tại:

```
❌ THIẾU Username trường
❌ THIẾU unique constraint cho Username
```

### Phải Làm:

```
✅ Thêm username field vào User.java
✅ Thêm @Column(unique = true, nullable = false)
✅ Rebuild project
✅ Restart Tomcat
✅ Hibernate tự add column vào DB
```

### Sau Đó:

```
✅ Database sẽ có Username column
✅ Có thể implement UserRepoImpl.findByUsername()
✅ Có thể implement UserValidation.checkDuplicateUsername()
```

**Rõ ràng chưa?** 🎉
