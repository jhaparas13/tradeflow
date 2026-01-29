

----

# TradeFlow – B2B Order Management System (Spring Boot)

TradeFlow is a backend-focused B2B order management system built using **Spring Boot**.
It demonstrates real-world backend concepts like **authentication, authorization, transactions, concurrency control, and clean architecture**.

This project prioritizes **correct system design** over UI.

---

## 🚀 Features Implemented

### 🔐 Authentication & Security

* JWT-based authentication (Access + Refresh tokens)
* Short-lived access tokens (15 minutes)
* Refresh token rotation and reuse detection
* Secure logout via refresh token invalidation
* Stateless authentication
* Role-based authorization (ADMIN, CUSTOMER)
* Endpoint-level and method-level security
* Custom JWT authentication filter

---

### 📦 Product Management

* Admin-only product CRUD operations
* Product status handling (ACTIVE / INACTIVE)
* Soft deactivation instead of hard delete
* Separate admin and customer product APIs
* Customer product listing (ACTIVE products only)

---

### 📃 Pagination, Sorting & Search

* Pagination using Spring Data `Pageable`
* Dynamic sorting (price, name, etc.)
* Case-insensitive product search
* Stable paginated API responses using DTO wrappers
* Avoided direct `PageImpl` serialization issues

---

### 🛒 Order Management

* Order placement by customers
* Order lifecycle handling (CREATED, CANCELLED, COMPLETED)
* Customer order history
* Admin order view
* Authorization checks for order ownership
* Order cancellation rules

---

### 📦 Stock Management & Concurrency

* Real-time stock validation
* Pessimistic locking (`PESSIMISTIC_WRITE`) to prevent overselling
* Transactional consistency using `@Transactional`
* Automatic stock rollback on order cancellation or payment failure

---

### 💳 Payment (Mock Implementation)

* Fake payment service to simulate real payment flow
* Payment success and failure handling
* Order status updates based on payment outcome
* Clear separation between order and payment logic

---

### 🧱 Architecture & Design

* Layered architecture (Controller → Service → Repository)
* Interface + implementation pattern
* DTO-based API responses (no entity exposure)
* Generic response wrappers using Java generics
* Clean separation of responsibilities
* Business-rule-driven exceptions

---

## 🛠️ Tech Stack

* **Java 17**
* **Spring Boot**
* **Spring Security**
* **Spring Data JPA**
* **Hibernate**
* **PostgreSQL**
* **JWT**
* **REST APIs**

---

## 🧠 Key Learnings

* Designing secure, stateless APIs
* Handling concurrent data updates safely
* Transaction management and rollback behavior
* Real-world authorization flows (admin vs customer)
* API stability and response design
* Building production-grade backend logic without overengineering

---

## 📌 Project Scope Notes

* Payment integration is intentionally mocked
* Pessimistic locking chosen for simplicity and correctness
* Focus is on backend system design, not UI

---

## 📂 How to Run

1. Clone the repository
2. Configure database in `application.yml`
3. Run the Spring Boot application
4. Test APIs using Postman or similar tools

---



