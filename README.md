# 🥊 BoxingHub — Boxing Club Management System

A boxing club management web application built with **Spring Boot 3 + Spring Security + JPA + MySQL + Thymeleaf**.

---

## Requirements

- JDK 17+
- MySQL 8.0+
- Git

---

## Installation & Running

**1. Clone the project**
```bash
git clone https://github.com/AnhLove/boxinghub.git
cd boxinghub
```

**2. Import database**
```bash
mysql -u root -p < database/boxinghub.sql
```
Or open **MySQL Workbench** → Server → Data Import → select file `database/boxinghub.sql` → Start Import.

**3. Configure database**

Open `src/main/resources/application.properties` and update:
```properties
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

**4. Run the application**
```bash
# Windows
mvnw.cmd spring-boot:run

# macOS / Linux
./mvnw spring-boot:run
```

**5. Access**
```
http://localhost:8080
```

---

## Default Accounts

| Role | Email | Password |
|---|---|---|
| Admin | admin@boxinghub.com | admin123 |
| Member | member@boxinghub.com | member123 |

---

## Tech Stack

Spring Boot 3 · Spring Security · Spring Data JPA · MySQL · Thymeleaf

---
---

# 🥊 BoxingHub — Hệ thống quản lý câu lạc bộ Boxing

Ứng dụng web quản lý câu lạc bộ boxing xây dựng bằng **Spring Boot 3 + Spring Security + JPA + MySQL + Thymeleaf**.

---

## Yêu cầu

- JDK 17+
- MySQL 8.0+
- Git

---

## Cài đặt & Chạy

**1. Clone dự án**
```bash
git clone https://github.com/AnhLove/boxinghub.git
cd boxinghub
```

**2. Import database**
```bash
mysql -u root -p < database/boxinghub.sql
```
Hoặc mở **MySQL Workbench** → Server → Data Import → chọn file `database/boxinghub.sql` → Start Import.

**3. Cấu hình database**

Mở `src/main/resources/application.properties`, sửa lại:
```properties
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

**4. Chạy ứng dụng**
```bash
# Windows
mvnw.cmd spring-boot:run

# macOS / Linux
./mvnw spring-boot:run
```

**5. Truy cập**
```
http://localhost:8080
```

---

## Tài khoản mặc định

| Role | Email | Mật khẩu |
|---|---|---|
| Admin | admin@boxinghub.com | admin123 |
| Member | member@boxinghub.com | member123 |

---

## Công nghệ

Spring Boot 3 · Spring Security · Spring Data JPA · MySQL · Thymeleaf
