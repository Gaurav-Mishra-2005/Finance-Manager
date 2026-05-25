# 📊 Personal Finance Manager API

[![Java 17](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Build Status](https://img.shields.io/badge/Build-Passing-brightgreen.svg)]()
[![Coverage](https://img.shields.io/badge/Coverage-80%25%2B-success.svg)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

A production-grade, secure RESTful API built with Spring Boot to manage personal finances. Designed with a strict layered architecture, this system supports complex financial tracking, custom categorization, dynamic savings goals, and automated analytics.

**🚀 Live API Endpoint:** `https://finance-manager-8t7u.onrender.com/api`

---

## ✨ Core Features
- **Secure Authentication:** Session-based security with strict cross-user data isolation.
- **Transaction Engine:** Full CRUD capabilities with validation enforcing positive amounts and past/present date restrictions.
- **Dynamic Savings Goals:** Real-time progress calculation based on the user's active income and expense transactions.
- **Categorization:** Immutable global default categories combined with user-specific custom categories.
- **Automated Analytics:** On-the-fly generation of monthly and yearly financial reports using Java Streams.

---

## 🛠️ Tech Stack & Libraries
- **Core:** Java 17, Spring Boot 3.x, Spring Web
- **Security:** Spring Security (Stateful Session Authentication)
- **Data Persistence:** Spring Data JPA, Hibernate, H2 In-Memory Database
- **Validation:** Jakarta Bean Validation (Hibernate Validator)
- **Testing:** JUnit 5, Mockito, End-to-End Bash Scripting
- **Deployment:** Docker (Multi-stage builds), Render

---

## 🏗️ Project Architecture & Structure

The application strictly adheres to the **Controller-Service-Repository** pattern to decouple HTTP routing from business logic and data access. 

```text
src/main/java/com/finance/dashboard/
├── config/              # Spring Security and Bean configurations
├── controller/          # REST API endpoints and HTTP routing
├── dto/                 # Data Transfer Objects (Request/Response contracts)
├── entity/              # JPA Database Models (User, Transaction, Goal, Category)
├── exception/           # @ControllerAdvice and Custom Exceptions (404, 409, 403)
├── repository/          # Spring Data JPA Interfaces with custom JPQL queries
├── service/             # Core business logic and aggregation streams
└── DashboardApplication.java