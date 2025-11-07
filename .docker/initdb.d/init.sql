
CREATE DATABASE IF NOT EXISTS ASM_JAVA4;
USE ASM_JAVA4;

-- Drop existing tables if they exist (for clean deployment)
DROP TABLE IF EXISTS `Share`;
DROP TABLE IF EXISTS `Favorite`;
DROP TABLE IF EXISTS `Video`;
DROP TABLE IF EXISTS `User`;

-- =====================================================
-- TABLE: `User`
-- Description: Stores user account information
-- =====================================================
CREATE TABLE `User`
(
    Id          VARCHAR(50) PRIMARY KEY,
    Password    VARCHAR(255) NOT NULL,
    Email       VARCHAR(100) NOT NULL UNIQUE,
    Fullname    VARCHAR(100) NOT NULL,
    Admin       BOOLEAN NOT NULL DEFAULT FALSE,
    CreatedDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UpdatedDate DATETIME NULL
);

-- =====================================================
-- TABLE: Video
-- Description: Stores video information
-- =====================================================
CREATE TABLE Video
(
    Id          VARCHAR(50) PRIMARY KEY,
    Title       VARCHAR(255) NOT NULL,
    Poster      VARCHAR(500) NULL,
    Views       INT      NOT NULL DEFAULT 0,
    Description LONGTEXT NULL, -- Thay thế NVARCHAR(MAX)
    Active      BOOLEAN  NOT NULL DEFAULT TRUE,
    CreatedDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UpdatedDate DATETIME NULL
);

-- =====================================================
-- TABLE: Favorite
-- Description: Stores user's favorite videos
-- =====================================================
CREATE TABLE Favorite
(
    Id       BIGINT PRIMARY KEY AUTO_INCREMENT, -- Thay thế IDENTITY(1,1)
    UserId   VARCHAR(50) NOT NULL,
    VideoId  VARCHAR(50) NOT NULL,
    LikeDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (UserId, VideoId),
    FOREIGN KEY (UserId) REFERENCES `User`(Id) ON DELETE CASCADE,
    FOREIGN KEY (VideoId) REFERENCES Video(Id) ON DELETE CASCADE
);

-- =====================================================
-- TABLE: Share
-- Description: Stores video sharing information
-- =====================================================
CREATE TABLE `Share`
(
    Id        BIGINT PRIMARY KEY AUTO_INCREMENT, -- Thay thế IDENTITY(1,1)
    UserId    VARCHAR(50) NOT NULL,
    VideoId   VARCHAR(50) NOT NULL,
    Emails    LONGTEXT NOT NULL, -- Thay thế NVARCHAR(MAX)
    ShareDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (UserId) REFERENCES `User`(Id) ON DELETE CASCADE,
    FOREIGN KEY (VideoId) REFERENCES Video(Id) ON DELETE CASCADE
);

-- =====================================================
-- CREATE INDEXES FOR BETTER QUERY PERFORMANCE
-- =====================================================
CREATE INDEX IX_Favorite_UserId ON Favorite (UserId);
CREATE INDEX IX_Favorite_VideoId ON Favorite (VideoId);
CREATE INDEX IX_Share_UserId ON `Share` (UserId);
CREATE INDEX IX_Share_VideoId ON `Share` (VideoId);
CREATE INDEX IX_User_Email ON `User`(Email);
CREATE INDEX IX_Video_Active ON Video (Active);

-- =====================================================
-- SAMPLE DATA (Optional - for testing)
-- =====================================================
-- Insert sample users
INSERT INTO `User` (Id, Password, Email, Fullname, Admin)
VALUES
    ('user001', 'hashed_password_123', 'john@example.com', 'John Doe', 0),
    ('user002', 'hashed_password_456', 'jane@example.com', 'Jane Smith', 0),
    ('admin001', 'hashed_admin_password', 'admin@example.com', 'Admin User', 1);

-- Insert sample videos
INSERT INTO Video (Id, Title, Poster, Views, Description, Active)
VALUES ('vid001', 'Introduction to Java', 'poster1.jpg', 150, 'Learn Java basics', TRUE),
       ('vid002', 'Advanced SQL Techniques', 'poster2.jpg', 200, 'Master SQL queries', TRUE),
       ('vid003', 'Web Development with HTML5', 'poster3.jpg', 120, 'Build modern websites', TRUE);

-- Insert sample favorites
INSERT INTO Favorite (UserId, VideoId)
VALUES ('user001', 'vid001'),
       ('user001', 'vid002'),
       ('user002', 'vid003');

-- Insert sample shares
INSERT INTO `Share` (UserId, VideoId, Emails)
VALUES ('user001', 'vid001', 'friend1@example.com;friend2@example.com'),
       ('user002', 'vid002', 'colleague@example.com');

-- =====================================================
-- VERIFY TABLES CREATED SUCCESSFULLY
-- =====================================================
SELECT 'Database setup completed successfully!' AS Status;
SELECT TABLE_NAME
FROM INFORMATION_SCHEMA.TABLES
WHERE TABLE_SCHEMA = 'asm_java4' -- Tên database phải viết thường
ORDER BY TABLE_NAME;