-- =====================================================
-- IMPROVED DATABASE SCHEMA WITH COMMENT TABLE
-- Database: java4_db_asm (MariaDB)
-- =====================================================

-- Drop existing tables if they exist (for clean deployment)
DROP TABLE IF EXISTS Comment;
DROP TABLE IF EXISTS Share;
DROP TABLE IF EXISTS Favorite;
DROP TABLE IF EXISTS Video;
DROP TABLE IF EXISTS `User`;

-- =====================================================
-- TABLE: `User`
-- Description: Stores user account information
-- =====================================================
CREATE TABLE `User` (
    Id          VARCHAR(50) PRIMARY KEY,
    Password    VARCHAR(255) NOT NULL,
    Email       VARCHAR(100) NOT NULL UNIQUE,
    Fullname    VARCHAR(100) NOT NULL,
    Admin       BOOLEAN NOT NULL DEFAULT FALSE,
    CreatedDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UpdatedDate DATETIME NULL ON UPDATE CURRENT_TIMESTAMP  -- ✨ CẢI THIỆN: Auto update
);

-- =====================================================
-- TABLE: Video
-- Description: Stores video information
-- =====================================================
CREATE TABLE Video (
    Id          VARCHAR(50) PRIMARY KEY,
    Title       VARCHAR(255) NOT NULL,
    Poster      VARCHAR(500) NULL,
    Views       INT NOT NULL DEFAULT 0,
    Description LONGTEXT NULL,
    Active      BOOLEAN NOT NULL DEFAULT TRUE,
    UserId      VARCHAR(50) NULL,               -- ✨ THÊM: Uploaded by user
    CreatedDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UpdatedDate DATETIME NULL ON UPDATE CURRENT_TIMESTAMP,  -- ✨ CẢI THIỆN: Auto update
    FOREIGN KEY (UserId) REFERENCES `User`(Id) ON DELETE SET NULL
);

-- =====================================================
-- TABLE: Favorite
-- Description: Stores user's favorite videos
-- =====================================================
CREATE TABLE Favorite (
    Id       BIGINT PRIMARY KEY AUTO_INCREMENT,
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
CREATE TABLE Share (
    Id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    UserId    VARCHAR(50) NOT NULL,
    VideoId   VARCHAR(50) NOT NULL,
    Emails    LONGTEXT NOT NULL,
    ShareDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (UserId) REFERENCES `User`(Id) ON DELETE CASCADE,
    FOREIGN KEY (VideoId) REFERENCES Video(Id) ON DELETE CASCADE
);

-- =====================================================
-- TABLE: Comment (✨ MỚI)
-- Description: Stores user comments on videos
-- =====================================================
CREATE TABLE Comment (
    Id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    UserId      VARCHAR(50) NOT NULL,
    VideoId     VARCHAR(50) NOT NULL,
    Content     LONGTEXT NOT NULL,
    CreatedDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UpdatedDate DATETIME NULL ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (UserId) REFERENCES `User`(Id) ON DELETE CASCADE,
    FOREIGN KEY (VideoId) REFERENCES Video(Id) ON DELETE CASCADE
);

-- =====================================================
-- CREATE INDEXES FOR BETTER QUERY PERFORMANCE
-- =====================================================
-- User indexes
CREATE INDEX IX_User_Email ON `User`(Email);

-- Video indexes
CREATE INDEX IX_Video_Active ON Video(Active);
CREATE INDEX IX_Video_UserId ON Video(UserId);
CREATE INDEX IX_Video_Views ON Video(Views);  -- ✨ THÊM: Cho query popular videos

-- Favorite indexes
CREATE INDEX IX_Favorite_UserId ON Favorite(UserId);
CREATE INDEX IX_Favorite_VideoId ON Favorite(VideoId);
CREATE INDEX IX_Favorite_LikeDate ON Favorite(LikeDate);  -- ✨ THÊM: Cho query recent favorites

-- Share indexes
CREATE INDEX IX_Share_UserId ON Share(UserId);
CREATE INDEX IX_Share_VideoId ON Share(VideoId);
CREATE INDEX IX_Share_ShareDate ON Share(ShareDate);  -- ✨ THÊM: Cho query recent shares

-- Comment indexes (✨ MỚI)
CREATE INDEX IX_Comment_UserId ON Comment(UserId);
CREATE INDEX IX_Comment_VideoId ON Comment(VideoId);
CREATE INDEX IX_Comment_CreatedDate ON Comment(CreatedDate);

-- =====================================================
-- SAMPLE DATA (Optional - for testing)
-- =====================================================

-- Insert sample users
INSERT INTO `User` (Id, Password, Email, Fullname, Admin) VALUES
    ('user001', '$2a$10$abcdefghijklmnopqrstuv', 'john@example.com', 'John Doe', 0),
    ('user002', '$2a$10$abcdefghijklmnopqrstuv', 'jane@example.com', 'Jane Smith', 0),
    ('user003', '$2a$10$abcdefghijklmnopqrstuv', 'bob@example.com', 'Bob Wilson', 0),
    ('admin001', '$2a$10$abcdefghijklmnopqrstuv', 'admin@example.com', 'Admin User', 1);

-- Insert sample videos
INSERT INTO Video (Id, Title, Poster, Views, Description, Active, UserId) VALUES
    ('vid001', 'Introduction to Java', 'poster1.jpg', 150, 'Learn Java basics', TRUE, 'user001'),
    ('vid002', 'Advanced SQL Techniques', 'poster2.jpg', 200, 'Master SQL queries', TRUE, 'user001'),
    ('vid003', 'Web Development with HTML5', 'poster3.jpg', 120, 'Build modern websites', TRUE, 'user002'),
    ('vid004', 'Spring Boot Tutorial', 'poster4.jpg', 300, 'Learn Spring Boot', TRUE, 'user002'),
    ('vid005', 'Vue.js for Beginners', 'poster5.jpg', 180, 'Master Vue.js', TRUE, 'user003');

-- Insert sample favorites
INSERT INTO Favorite (UserId, VideoId) VALUES
    ('user001', 'vid001'),
    ('user001', 'vid002'),
    ('user001', 'vid003'),
    ('user002', 'vid001'),
    ('user002', 'vid003'),
    ('user002', 'vid004'),
    ('user003', 'vid002'),
    ('user003', 'vid005');

-- Insert sample shares
INSERT INTO Share (UserId, VideoId, Emails) VALUES
    ('user001', 'vid001', 'friend1@example.com;friend2@example.com'),
    ('user002', 'vid002', 'colleague@example.com'),
    ('user003', 'vid003', 'student1@example.com;student2@example.com');

-- Insert sample comments (✨ MỚI)
INSERT INTO Comment (UserId, VideoId, Content) VALUES
    -- Comments cho vid001
    ('user001', 'vid001', 'Great tutorial! Very helpful for beginners.'),
    ('user002', 'vid001', 'Thanks for sharing this!'),
    ('user003', 'vid001', 'Could you make a part 2?'),
    ('user001', 'vid001', 'Sure! Part 2 coming soon.'),  -- Reply to comment 3

    -- Comments cho vid002
    ('user002', 'vid002', 'The SQL examples are excellent!'),
    ('user003', 'vid002', 'Very detailed explanation.'),
    ('user001', 'vid002', 'I learned a lot from this video.'),

    -- Comments cho vid003
    ('user001', 'vid003', 'HTML5 is so powerful!'),
    ('user002', 'vid003', 'Love the practical examples.'),
    ('user003', 'vid003', 'Can you cover CSS3 next?'),

    -- Comments cho vid004
    ('user001', 'vid004', 'Spring Boot makes development so easy!'),
    ('user002', 'vid004', 'Best Spring Boot tutorial ever!'),

    -- Comments cho vid005
    ('user001', 'vid005', 'Vue.js is amazing!'),
    ('user002', 'vid005', 'Clear and concise tutorial.'),
    ('user003', 'vid005', 'Thanks for this!');

-- =====================================================
-- VERIFY TABLES CREATED SUCCESSFULLY
-- =====================================================
SELECT 'Database setup completed successfully!' AS Status;

SELECT
    TABLE_NAME,
    TABLE_ROWS,
    CREATE_TIME
FROM INFORMATION_SCHEMA.TABLES
WHERE TABLE_SCHEMA = 'java4_db_asm'
ORDER BY TABLE_NAME;

-- Show record counts
SELECT 'User' as TableName, COUNT(*) as RecordCount FROM `User`
UNION ALL
SELECT 'Video', COUNT(*) FROM Video
UNION ALL
SELECT 'Favorite', COUNT(*) FROM Favorite
UNION ALL
SELECT 'Share', COUNT(*) FROM Share
UNION ALL
SELECT 'Comment', COUNT(*) FROM Comment;
