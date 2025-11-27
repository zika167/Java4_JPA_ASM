# 🤔 Tại Sao Phải Tách Repository và Service? (Không Gộp Lại Được)

## ❌ Điều Bạn Đang Hiểu

```
"Repository và Service là cùng 1 việc, tại sao ko gộp lại?"

UserRepository = UserService = 1 class duy nhất
```

## ✅ Tư Duy Đúng

```
Repository    ≠    Service
(Database)         (Business Logic)

Khác nhau về MỤC ĐÍCH, không phải thêm "dư thừa"
```

---

## 🔍 So Sánh: Repository vs Service

### Repository (Repo)

**Trách Nhiệm:** ❌ **KHÔNG** xử lý logic, chỉ **CÓ** database

```java
// UserRepoImpl làm CÁI GÌ?
public class UserRepoImpl implements UserRepo {
    
    // ✅ Query: Lấy user theo ID
    findById(id) → SELECT * FROM User WHERE id = ?
    
    // ✅ Query: Lấy user theo username  
    findByUsername(username) → SELECT * FROM User WHERE username = ?
    
    // ✅ Query: Lấy tất cả
    findAll() → SELECT * FROM User
    
    // ✅ Query: Lưu user
    save(user) → INSERT INTO User VALUES (...)
    
    // ✅ Query: Cập nhật
    update(user) → UPDATE User SET ...
    
    // ✅ Query: Xóa
    deleteById(id) → DELETE FROM User WHERE id = ?
}
```

**Đặc điểm:** Pure database, raw queries, transaction management

---

### Service (ServiceImpl)

**Trách Nhiệm:** ✅ **CÓ** xử lý logic, ✅ **CÓ** gọi Repository

```java
// UserServiceImpl làm CÁI GÌ?
public class UserServiceImpl implements UserService {
    
    // ✅ Logic: Tạo user mới
    create(request) {
        // 1. Validate request
        UserValidation.validateCreateUserRequest(request);
        
        // 2. Check duplicate username (gọi Repo)
        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            throw "Username đã tồn tại";
        }
        
        // 3. Check duplicate email (gọi Repo)
        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            throw "Email đã tồn tại";
        }
        
        // 4. Hash password (logic)
        String hashedPassword = hashPassword(request.getPassword());
        
        // 5. Set created date (logic)
        user.setCreatedDate(new Date());
        
        // 6. Save to DB (gọi Repo)
        return userRepo.save(user);
    }
    
    // ✅ Logic: Cập nhật user
    update(id, request) {
        // 1. Validate
        // 2. Check user exists (gọi Repo)
        // 3. Check duplicate (trừ user hiện tại)
        // 4. Hash password mới
        // 5. Update (gọi Repo)
    }
    
    // ✅ Logic: Xóa user
    delete(id) {
        // 1. Check user exists
        // 2. Check user có yêu thích không (check với FavoriteRepo)
        // 3. Check user có comment không (check với CommentRepo)
        // 4. Nếu có, không cho xóa
        // 5. Mới cho xóa (gọi Repo)
    }
}
```

**Đặc điểm:** Xử lý logic phức tạp, orchestration, validation, cross-entity checks

---

## 📊 Bảng So Sánh Chi Tiết

| Tiêu Chí | Repository | Service | Khác Nhau? |
|----------|-----------|---------|-----------|
| **Trách nhiệm** | Database operations | Business logic | ✅ **KHÁC** |
| **Gọi cái gì** | Query database | Gọi Repository | ✅ **KHÁC** |
| **Có validation** | ❌ Không | ✅ Có | ✅ **KHÁC** |
| **Xử lý exception** | Raw DB exception | Business exception | ✅ **KHÁC** |
| **Orchestration** | ❌ Không | ✅ Có | ✅ **KHÁC** |
| **Có logic phức tạp** | ❌ Không | ✅ Có | ✅ **KHÁC** |

---

## 🎯 Ví Dụ Thực Tế: Tại Sao Không Gộp?

### ❌ Nếu Gộp Lại (1 class duy nhất)

```java
// ❌ KHÔNG NÊN LÀM: UserRepositoryService (gộp lại)
public class UserRepositoryService {
    
    // Trộn lẫn: database + logic
    public void deleteUser(String userId) {
        // DB check
        User user = findById(userId);
        
        // Logic check: User có comment không?
        List<Comment> comments = ??? // Gọi cái gì? CommentRepository? 
                                      // Nhưng UserRepositoryService ko có nó!
        
        // Logic check: User có share không?
        List<Share> shares = ??? // Lại gọi cái khác?
        
        // DB delete
        deleteById(userId);
    }
}
```

**Vấn đề:**
- 🔴 UserRepositoryService cần gọi CommentRepository, ShareRepository
- 🔴 Dependency lộn xộn
- 🔴 Khó test
- 🔴 Khó bảo trì

---

### ✅ Nếu Tách Riêng (Repository + Service)

```java
// ✅ ĐÚNG: Tách riêng

// Repository: Chỉ database
public class UserRepoImpl implements UserRepo {
    public Optional<User> findById(String id) {
        return em.find(User.class, id);
    }
    
    public boolean deleteById(String id) {
        em.remove(user);
    }
}

// Service: Xử lý logic
public class UserServiceImpl implements UserService {
    private UserRepo userRepo = new UserRepoImpl();
    private CommentRepo commentRepo = new CommentRepoImpl();
    private ShareRepo shareRepo = new ShareRepoImpl();
    
    public void deleteUser(String userId) {
        // Logic: Check user exists
        if (!userRepo.existsById(userId)) {
            throw new AppException(Error.NOT_FOUND);
        }
        
        // Logic: Check user có comment không?
        if (commentRepo.findByUserId(userId).size() > 0) {
            throw new AppException(Error.CONFLICT, "User có comment, không thể xóa");
        }
        
        // Logic: Check user có share không?
        if (shareRepo.findByUserId(userId).size() > 0) {
            throw new AppException(Error.CONFLICT, "User có share, không thể xóa");
        }
        
        // Cuối cùng mới xóa
        userRepo.deleteById(userId);
    }
}
```

**Lợi ích:**
- ✅ Rõ ràng: Repo chỉ DB, Service chỉ logic
- ✅ Dễ test: Mock mỗi layer riêng
- ✅ Tái sử dụng: UserService có thể dùng từ nhiều nơi
- ✅ Bảo trì: Thay đổi 1 layer không ảnh hưởng khác

---

## 💡 Ví Dụ Cụ Thể: Create User

### Khi Create User, Service Làm GÌ?

```
Gọi create(CreateUserRequest):

UserServiceImpl.create(request)
    ↓
    [1] Validation - check null, empty (Service)
    UserValidation.validateCreateUserRequest(request)
    
    ↓
    [2] Check duplicate username (Repository)
    userRepo.findByUsername(request.getUsername())
    
    ↓
    [3] Check duplicate email (Repository)
    userRepo.findByEmail(request.getEmail())
    
    ↓
    [4] Hash password (Service logic)
    hashPassword(request.getPassword())
    
    ↓
    [5] Generate ID (Service logic)
    UUID.randomUUID().toString()
    
    ↓
    [6] Set timestamps (Service logic)
    user.setCreatedDate(new Date())
    user.setUpdatedDate(new Date())
    
    ↓
    [7] Save to DB (Repository)
    userRepo.save(user)
    
    ↓
    [8] Convert Entity → DTO (Service)
    toUserResponse(user)
    
    ↓
    Return UserResponse
```

**Nhìn thấy không:**
- Repository **chỉ** làm: [2], [3], [7]
- Service **làm**: [1], [2], [3], [4], [5], [6], [7], [8]

**Repository và Service KHÁC NHAU CẤP ĐỘ TRÁCH NHIỆM!**

---

## 🔗 Dependency Graph (Để Rõ)

### Nếu Gộp Lại (❌ KHÔNG NÊN)

```
UserRepositoryService
    ├─ Dependency quá nhiều
    ├─ Khó test (tất cả 1 class)
    ├─ Khó reuse
    └─ Khó bảo trì
```

### Nếu Tách Riêng (✅ ĐÚNG)

```
API Layer
    ↓
UserServiceImpl
    ├─ Orchestration
    ├─ Validation
    ├─ Business logic
    └─ Dependency: UserRepo, CommentRepo, ShareRepo (clean)
    
    ↓
UserRepoImpl
    ├─ Database queries
    ├─ Transaction
    └─ Dependency: EntityManager (clean)
    
    ↓
Database
```

**Mỗi layer chỉ biết về layer bên dưới, không biết layer bên trên!**

---

## 🎓 Design Pattern: Separation of Concerns

### Nguyên Tắc: 1 Class = 1 Mục Đích

```
UserRepository
    → Purpose: Database operations
    → Class chỉ làm: CRUD queries
    → Không quan tâm: Logic thế nào

UserService  
    → Purpose: Business logic
    → Class chỉ làm: Xử lý yêu cầu
    → Không quan tâm: Database lưu thế nào
```

### Lợi Ích:

1. **Single Responsibility Principle (SRP)**
   - UserRepo: Trách nhiệm = Database
   - UserService: Trách nhiệm = Logic

2. **Open/Closed Principle (OCP)**
   - Mở rộng Service? Thêm logic mới không ảnh hưởng Repo
   - Mở rộng Repo? Thêm query mới không ảnh hưởng Service

3. **Dependency Inversion Principle (DIP)**
   - Service phụ thuộc vào Repository Interface
   - Có thể swap implementation (mock, fake, real)

4. **Testability**
   ```java
   // Test Service:
   @Test
   public void testCreateUser() {
       // Mock UserRepository
       UserRepo mockRepo = mock(UserRepo.class);
       UserService service = new UserServiceImpl(mockRepo);
       
       // Test logic mà không cần database thực
       service.create(request);
   }
   ```

---

## 📌 Tổng Kết

### Repository Là GÌ?

```
"Database Access Layer"

Mục đích: Tách biệt business logic khỏi database code
Làm: Query, save, delete, transaction management
Không làm: Validation, orchestration, business logic
```

### Service Là GÌ?

```
"Business Logic Layer"

Mục đích: Xử lý yêu cầu từ API
Làm: Validation, orchestration, business logic, DTO conversion
Không làm: Direct database access (qua Repository)
```

### Tại Sao Tách?

```
✅ SRP: 1 class = 1 mục đích
✅ Reusability: Service có thể dùng từ API khác
✅ Testability: Mock mỗi layer riêng
✅ Maintainability: Thay đổi 1 layer không ảnh hưởng khác
✅ Dependency Management: Rõ ràng ai phụ thuộc ai
```

### Tại Sao Không Gộp?

```
❌ Trách nhiệm lộn xộn
❌ Khó test (phải test cả database)
❌ Khó reuse (Service phụ thuộc tất cả repository)
❌ Khó bảo trì (thay đổi 1 vài dòng ảnh hưởng nhiều)
❌ Code trở nên spaghetti (lẫn lộn)
```

---

## 🎯 Câu Hỏi & Trả Lời

### Q: Nếu tôi gộp Repository + Service, liệu có chạy được không?

A: ✅ **CÓ**, nhưng:
- 🔴 Code sẽ trở nên messy
- 🔴 Khó test
- 🔴 Khó bảo trì
- 🔴 Không follow best practice

### Q: Khi nào nên tách Repository và Service?

A: **LUÔN LUÔN** (99% trường hợp)
- Dù project nhỏ hay lớn
- Dù team 1 người hay 100 người
- Dù simple hay complex

### Q: Repository.Impl và Service.Impl là gì?

A: 
```
Interface (UserRepository)
    ↓
Implementation (UserRepositoryImpl)
    - Concrete implementation
    - Ghi chi tiết cách làm
    - Có thể thay đổi implementation

Interface (UserService)
    ↓
Implementation (UserServiceImpl)
    - Concrete implementation
    - Ghi chi tiết cách làm
    - Có thể thay đổi implementation
```

**Lợi ích: Có thể swap implementation**
```java
// Ngày hôm nay: dùng MySQL
UserRepo repo = new UserMySQLRepoImpl();

// Ngày mai: chuyển sang MongoDB
UserRepo repo = new UserMongoDBRepoImpl();

// Service không thay đổi!
```

### Q: Vì sao có Interface riêng?

A: **Dependency Inversion**
```
❌ Service phụ thuộc UserRepositoryImpl (cụ thể)
    → Khó đổi implementation

✅ Service phụ thuộc UserRepository (interface)
    → Dễ đổi implementation
    → Dễ mock cho test
```

---

## 🎬 Kết Luận

**Repository ≠ Service. Họ KHÁC NHAU:**

| Điểm | Repository | Service |
|------|-----------|---------|
| Đối tượng | Database | Logic |
| Xử lý | Query | Orchestration |
| Validation | ❌ Không | ✅ Có |
| Dependency | EntityManager | Repository |
| Testable | Cần DB | Mock Repo |

**Tách riêng = Best Practice (SOLID Principles)**

**Gộp lại = Code smell (violation của SRP)**

---

## 📚 Ví Dụ Thực Tế - Bạn Sẽ Hiểu Rõ Khi Implement

Khi bạn implement UserServiceImpl, bạn sẽ thấy:

```java
public class UserServiceImpl implements UserService {
    private UserRepo userRepo; // Chỉ gọi này để DB
    
    public UserResponse create(CreateUserRequest request) {
        // [Service logic] Validate
        UserValidation.validateCreateUserRequest(request);
        
        // [Service logic] Check duplicate (gọi Repo)
        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            throw new AppException(...);
        }
        
        // [Service logic] Hash password
        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        
        // [Service logic] Tạo entity
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setPassword(hashedPassword);
        user.setCreatedDate(new Date());
        
        // [Repository] Lưu vào DB
        User saved = userRepo.save(user);
        
        // [Service logic] Convert sang DTO
        return toUserResponse(saved);
    }
}
```

**Nhìn rõ không:**
- Repo chỉ làm: `userRepo.findByUsername()`, `userRepo.save()`
- Service làm: Validation, Hash, UUID, DTO conversion, Exception handling
- **Hoàn toàn khác nhau!**

Đó là lý do tại sao phải tách! 🎉
