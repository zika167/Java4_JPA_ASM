package com.fpt.java4_asm.services.impl;

import com.fpt.java4_asm.dto.ShareVideoRequest;
import com.fpt.java4_asm.models.entities.Share;
import com.fpt.java4_asm.models.entities.User;
import com.fpt.java4_asm.models.entities.Video;
import com.fpt.java4_asm.repositories.ShareRepo;
import com.fpt.java4_asm.repositories.UserRepo;
import com.fpt.java4_asm.repositories.VideoRepo;
import com.fpt.java4_asm.repositories.impl.ShareRepoImpl;
import com.fpt.java4_asm.repositories.impl.UserRepoImpl;
import com.fpt.java4_asm.repositories.impl.VideoRepoImpl;
import com.fpt.java4_asm.services.ShareService;
import com.fpt.java4_asm.services.base.BaseService;
import com.fpt.java4_asm.utils.helpers.ValidationHelper;
import com.google.gson.Gson;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class ShareServiceImpl implements ShareService, BaseService<Share> {
    private final ShareRepo shareRepo = new ShareRepoImpl();
    private final UserRepo userRepo = new UserRepoImpl();
    private final VideoRepo videoRepo = new VideoRepoImpl();
    private final Gson gson = new Gson();
    
    @Override
    public List<Share> findAll() {
        return shareRepo.findAll();
    }

    @Override
    public Optional<Share> findById(String id) {
        return shareRepo.findById(id);
    }

    @Override
    public Share save(Share entity) {
        return shareRepo.save(entity);
    }

    @Override
    public void delete(String id) {
        shareRepo.delete(id);
    }

    @Override
    public Share shareVideo(ShareVideoRequest request) {
        // Validate request
        if (request.getUserId() == null || request.getVideoId() == null) {
            throw new IllegalArgumentException("User ID và Video ID không được để trống");
        }
        if (request.getEmails() == null || request.getEmails().isEmpty()) {
            throw new IllegalArgumentException("Danh sách email không được để trống");
        }

        try {
            // Lấy thông tin user và video
            User user = userRepo.findById(request.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng"));
            Video video = videoRepo.findById(request.getVideoId())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy video"));

            // Lưu thông tin chia sẻ
            Share share = new Share();
            share.setUser(user);
            share.setVideo(video);
            share.setEmails(gson.toJson(request.getEmails()));
            share.setShareDate(new Date());
            
            Share savedShare = shareRepo.save(share);

            // Gửi email (nếu có cấu hình email)
            try {
                sendEmails(request.getEmails(), user, video);
            } catch (Exception e) {
                // Ghi log lỗi nhưng không dừng tiến trình
                System.err.println("Không thể gửi email: " + e.getMessage());
            }

            return savedShare;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi chia sẻ video: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Share> getSharesByVideoId(String videoId) {
        return shareRepo.findByVideoId(videoId);
    }

    @Override
    public List<Share> getSharesByUserId(String userId) {
        return shareRepo.findByUserId(userId);
    }

    /**
     * Gửi email thông báo chia sẻ
     * Sử dụng biến môi trường để cấu hình SMTP:
     * - MAIL_HOST: smtp.gmail.com
     * - MAIL_PORT: 587
     * - MAIL_USERNAME: your-email@gmail.com
     * - MAIL_PASSWORD: your-app-password
     * - MAIL_FROM: your-email@gmail.com
     */
    private void sendEmails(List<String> emails, User sender, Video video) {
        // Lấy cấu hình từ biến môi trường hoặc sử dụng giá trị mặc định
        String host = System.getenv("MAIL_HOST");
        String port = System.getenv("MAIL_PORT");
        String username = System.getenv("MAIL_USERNAME");
        String password = System.getenv("MAIL_PASSWORD");
        String from = System.getenv("MAIL_FROM");

        // Nếu không có cấu hình, sử dụng giá trị mặc định (cần thay đổi)
        if (host == null) host = "smtp.gmail.com";
        if (port == null) port = "587";
        if (username == null) username = "your-email@gmail.com";
        if (password == null) password = "your-app-password";
        if (from == null) from = "your-email@gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        try {
            Session session = Session.getInstance(props,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            
            // Thêm danh sách người nhận
            for (String email : emails) {
                if (ValidationHelper.isValidEmail(email)) {
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                }
            }

            message.setSubject(String.format("%s đã chia sẻ một video với bạn", sender.getUsername()));
            message.setText(String.format(
                "Xin chào,\n\n" +
                "%s đã chia sẻ một video với bạn:\n\n" +
                "Tiêu đề: %s\n" +
                "Mô tả: %s\n\n" +
                "Chúc bạn xem video vui vẻ!",
                sender.getUsername(),
                video.getTitle(),
                video.getDescription()
            ));

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Gửi email thất bại: " + e.getMessage(), e);
        }
    }
            return;
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port == null ? "587" : port);

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        for (String to : emails) {
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from == null ? username : from));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject(sender.getFullname() + " đã chia sẻ một video với bạn");
                String body = String.format("%s đã chia sẻ video: %s\n\nMô tả: %s\nLink: /videos/%s\n\n", sender.getFullname(), video.getTitle(), video.getDescription(), video.getId());
                message.setText(body);
                Transport.send(message);
            } catch (MessagingException e) {
                System.err.println("Failed to send share email to " + to + ": " + e.getMessage());
            }
        }
    }
}
