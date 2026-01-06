# --- Giai đoạn 1: Builder ---
FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app
COPY . .

# Build ra file .war
RUN mvn clean package -DskipTests

# -------------------------------------------

# --- Giai đoạn 2: Runner ---
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Tạo User bảo mật
RUN adduser -D javauser

# SỬA LỖI Ở ĐÂY:
# 1. Đổi nguồn copy từ *.jar thành *.war
# 2. Đổi tên đích thành app.war
COPY --from=builder /app/target/*.war app.war

# Phân quyền cho file war
RUN chown javauser:javauser app.war

# Kích hoạt user
USER javauser

# Chạy file war (Spring Boot có thể chạy war bằng java -jar bình thường)
CMD ["java", "-jar", "app.war"]