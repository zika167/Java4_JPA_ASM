# 🚨 Cách Xử Lý Exception Trong UserRepoImpl

## 🤔 Câu Hỏi Của Bạn

```
"Khi có lỗi trong UserRepoImpl, tôi nên:
1. Tạo file Exception riêng?
2. Hay ném AppException thẳng?
3. Hay ném RuntimeException?"
```

## ✅ Trả Lời: KHÔNG TẠO FILE MỚI, CHỈ NÊM EXCEPTION

```
❌ KHÔNG TẠO thêm file
✅ DÙNG AppException (đã có sẵn)
✅ NÉM nó như bạn làm trong FavoriteRepoImpl
```

---

## 📁 Exception Folder Hiện Tại

**File:** `src/main/java/com/fpt/java4_asm/exception/`

```
exception/
├── Error.java          ← Enum error code (tất cả)
├── AppException.java   ← Custom exception (dùng chung)
└── GlobalExceptionHandler.java (xử lý global)
```

**Đã có sẵn, không cần tạo thêm!** ✅

---

## 🎯 Cách Làm Đúng

### Dùng AppException (Đã Có)

```java
public class UserRepoImpl implements UserRepo {
    EntityManager em = HibernateUtil.getEntityManager();

    @Override
    public User save(User entity) {
        if (entity == null) {
            throw new IllegalArgumentException("User không được để trống");
        }

        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            // ✅ NÊM EXCEPTION NHƯ BẠN LÀM
            throw new RuntimeException("Lỗi khi lưu User: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return Optional.empty();
        }

        try {
            String jpql = "SELECT u FROM User u WHERE u.username = :username";
            TypedQuery<User> query = em.createQuery(jpql, User.class)
                    .setParameter("username", username);
            List<User> results = query.getResultList();
            return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
        } catch (Exception e) {
            // ✅ NÊM RUNTIME EXCEPTION
            throw new RuntimeException("Lỗi khi tìm User theo username: " + username, e);
        }
    }
}
```

---

## 🔍 Khi Nào Nên Ném Cái Gì?

### Level 1: Repository (UserRepoImpl) - Ném RuntimeException

```java
// UserRepoImpl - Database layer
throw new RuntimeException("Lỗi database: " + e.getMessage());
```

**Lý do:**
- Repository chỉ làm database work
- Nếu có lỗi DB, nên ném RuntimeException
- Để Service bắt và convert thành AppException

---

### Level 2: Service (UserServiceImpl) - Ném AppException

```java
// UserServiceImpl - Business logic layer
try {
    userRepo.findByUsername(request.getUsername());
} catch (RuntimeException e) {
    // Catch RuntimeException từ Repo
    // Ném AppException (business exception)
    throw new AppException(Error.DATABASE_ERROR, 
        "Lỗi khi tìm User: " + e.getMessage());
}
```

**Lý do:**
- Service xử lý logic
- Biết lỗi là gì (duplicate, not found,...)
- Ném AppException để API xử lý

---

### Level 3: API (UserAPI) - Xử Lý AppException

```java
// UserAPI - HTTP layer
try {
    userService.create(request);
} catch (AppException e) {
    // AppException tự mapping HTTP status
    throw e;  // Ném tiếp cho BaseApiServlet
}
```

**Lý do:**
- API chỉ cần ném AppException
- BaseApiServlet sẽ xử lý tự động

---

## 📊 Flow Exception

```
UserRepoImpl (Database layer)
    ↓
    catch (Exception e)
    ↓
    throw new RuntimeException(...)
    ↓
UserServiceImpl (Business logic layer)
    ↓
    catch (RuntimeException e)
    ↓
    throw new AppException(Error.DATABASE_ERROR, ...)
    ↓
UserAPI (HTTP layer)
    ↓
    catch (AppException e)
    ↓
    throw e
    ↓
BaseApiServlet
    ↓
    sendErrorResponse(response, e)
    ↓
Client nhận JSON response
```

---

## ✅ Ví Dụ Hoàn Chỉnh

### UserRepoImpl (Ném RuntimeException)

```java
public class UserRepoImpl implements UserRepo {
    EntityManager em = HibernateUtil.getEntityManager();

    @Override
    public User save(User entity) {
        if (entity == null) {
            throw new IllegalArgumentException("User không được để trống");
        }

        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            // ✅ RuntimeException
            throw new RuntimeException("Lỗi khi lưu User: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<User> findById(String id) {
        if (id == null) {
            return Optional.empty();
        }

        try {
            User entity = em.find(User.class, id);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            // ✅ RuntimeException
            throw new RuntimeException("Lỗi khi tìm User với ID: " + id, e);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return Optional.empty();
        }

        try {
            String jpql = "SELECT u FROM User u WHERE u.username = :username";
            TypedQuery<User> query = em.createQuery(jpql, User.class)
                    .setParameter("username", username);
            List<User> results = query.getResultList();
            return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
        } catch (Exception e) {
            // ✅ RuntimeException
            throw new RuntimeException("Lỗi khi tìm User theo username: " + username, e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return Optional.empty();
        }

        try {
            String jpql = "SELECT u FROM User u WHERE u.email = :email";
            TypedQuery<User> query = em.createQuery(jpql, User.class)
                    .setParameter("email", email);
            List<User> results = query.getResultList();
            return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
        } catch (Exception e) {
            // ✅ RuntimeException
            throw new RuntimeException("Lỗi khi tìm User theo email: " + email, e);
        }
    }

    // ... những method khác cũng tương tự
}
```

### UserServiceImpl (Catch RuntimeException, Ném AppException)

```java
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo = new UserRepoImpl();

    @Override
    public UserResponse create(CreateUserRequest request) {
        // Validate request
        UserValidation.validateCreateUserRequest(request);

        try {
            // Check duplicate username
            try {
                if (userRepo.findByUsername(request.getUsername()).isPresent()) {
                    throw new AppException(Error.INVALID_DATA, "Username đã tồn tại");
                }
            } catch (RuntimeException e) {
                // ✅ Catch RuntimeException từ Repo
                throw new AppException(Error.DATABASE_ERROR, 
                    "Lỗi khi kiểm tra username: " + e.getMessage());
            }

            // Check duplicate email
            try {
                if (userRepo.findByEmail(request.getEmail()).isPresent()) {
                    throw new AppException(Error.INVALID_DATA, "Email đã tồn tại");
                }
            } catch (RuntimeException e) {
                // ✅ Catch RuntimeException từ Repo
                throw new AppException(Error.DATABASE_ERROR, 
                    "Lỗi khi kiểm tra email: " + e.getMessage());
            }

            // Create user
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setFullName(request.getFullName());
            user.setCreatedDate(new Date());
            user.setUpdatedDate(new Date());

            try {
                // ✅ Save sẽ ném RuntimeException nếu có lỗi
                User savedUser = userRepo.save(user);
                return toUserResponse(savedUser);
            } catch (RuntimeException e) {
                // ✅ Catch và convert thành AppException
                throw new AppException(Error.DATABASE_ERROR, 
                    "Lỗi khi lưu User: " + e.getMessage());
            }
        } catch (AppException e) {
            // ✅ Nếu đã là AppException, ném tiếp
            throw e;
        } catch (Exception e) {
            // ✅ Catch exception không mong muốn
            throw new AppException(Error.INTERNAL_SERVER_ERROR, 
                "Lỗi không xác định: " + e.getMessage());
        }
    }
}
```

### UserAPI (Xử Lý AppException)

```java
@WebServlet(ApiConstants.API_USERS + "/*")
public class UserAPI extends BaseApiServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        try {
            CreateUserRequest request = parseRequestBody(req, CreateUserRequest.class);
            UserResponse response = userService.create(request);
            
            resp.setStatus(HttpServletResponse.SC_CREATED);
            sendSuccessResponse(resp, response, ApiConstants.MSG_CREATED);
            
        } catch (AppException e) {
            // ✅ AppException sẽ được xử lý tại đây
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.INTERNAL_SERVER_ERROR, 
                "Lỗi khi tạo User");
        }
    }
}
```

---

## 🎯 Bảng Tóm Tắt

| Layer | Exception Type | Cách Xử Lý | Ví Dụ |
|-------|---|---|---|
| **Repository** | RuntimeException | Ném nó | `throw new RuntimeException(...)` |
| **Service** | AppException | Catch RuntimeException từ Repo, Ném AppException | `throw new AppException(Error.DATABASE_ERROR, ...)` |
| **API** | AppException | Ném nó tiếp | `throw e` |
| **BaseApiServlet** | AppException | Xử lý + trả JSON response | Tự động |

---

## ⚠️ QUAN TRỌNG: Đừng Tạo Exception Mới

### ❌ KHÔNG NÊN LÀM

```java
// ❌ KHÔNG TẠO file này
// UserException.java (KHÔNG CẦN)
public class UserException extends Exception {
    // Dư thừa!
}

// ❌ KHÔNG DÙNG
throw new UserException("...");
```

**Lý do:**
- Exception đã có sẵn (AppException, RuntimeException)
- Tạo thêm = dư thừa
- Phức tạp hóa code

---

### ✅ DÙNG CÁI CÓ SẴN

```java
// ✅ DÙNG AppException (có sẵn)
throw new AppException(Error.INVALID_DATA, "Username đã tồn tại");

// ✅ DÙNG RuntimeException (có sẵn)
throw new RuntimeException("Lỗi database");
```

---

## 📝 Quy Tắc Chung

### Repository Layer

```java
// UserRepoImpl
try {
    // DB operation
} catch (Exception e) {
    // ✅ RuntimeException - Database layer exception
    throw new RuntimeException("Lỗi: " + e.getMessage(), e);
}
```

### Service Layer

```java
// UserServiceImpl
try {
    // Call Repository
    userRepo.save(user);
} catch (RuntimeException e) {
    // ✅ AppException - Business layer exception
    throw new AppException(Error.DATABASE_ERROR, "Lỗi: " + e.getMessage());
}
```

### API Layer

```java
// UserAPI
try {
    // Call Service
    userService.create(request);
} catch (AppException e) {
    // ✅ Ném tiếp, BaseApiServlet xử lý
    throw e;
}
```

---

## 🎓 Kết Luận

### Trong UserRepoImpl:

```
❌ KHÔNG: Tạo file Exception riêng
✅ KHÔNG: Ném AppException (quá high-level)
✅ CÓ: Ném RuntimeException

throw new RuntimeException("Lỗi: " + e.getMessage(), e);
```

### Exception Handling Flow:

```
Repository (RuntimeException)
    ↓
Service (catch + convert → AppException)
    ↓
API (throw AppException)
    ↓
BaseApiServlet (xử lý + send response)
```

### Exception Folder:

```
✅ Exception folder đã có đủ
❌ Không cần thêm file
✅ Dùng AppException (từ Error enum)
✅ Dùng RuntimeException (Java built-in)
```

---

## 💡 Một Vài Lưu Ý

### 1. Luôn Truyền Cause (Original Exception)

```java
// ❌ KHÔNG NÊN
throw new RuntimeException("Lỗi");

// ✅ NÊN
throw new RuntimeException("Lỗi", e);  // ← Truyền exception gốc
```

**Lý do:** Để trace stack trace gốc, dễ debug

---

### 2. Không Swallow Exception

```java
// ❌ KHÔNG NÊN
try {
    userRepo.save(user);
} catch (RuntimeException e) {
    // Không làm gì - swallow!
}

// ✅ NÊN
try {
    userRepo.save(user);
} catch (RuntimeException e) {
    // Convert thành AppException
    throw new AppException(Error.DATABASE_ERROR, e.getMessage());
}
```

---

### 3. Log Exception Nếu Cần

```java
// Optional: log error
catch (RuntimeException e) {
    System.err.println("Database error: " + e.getMessage());
    e.printStackTrace();
    
    throw new AppException(Error.DATABASE_ERROR, e.getMessage());
}
```

---

## 🎬 Ví Dụ Đơn Giản Nhất

```java
@Override
public Optional<User> findByUsername(String username) {
    if (username == null || username.trim().isEmpty()) {
        return Optional.empty();
    }

    try {
        String jpql = "SELECT u FROM User u WHERE u.username = :username";
        TypedQuery<User> query = em.createQuery(jpql, User.class)
                .setParameter("username", username);
        List<User> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    } catch (Exception e) {
        // ✅ CHỈ CẦN CÓ CÁI NÀY
        throw new RuntimeException("Lỗi khi tìm User theo username: " + username, e);
    }
}
```

**Rõ ràng chưa?** 🎉
