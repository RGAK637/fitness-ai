# 🏋️‍♂️ IntelliFit: AI-Powered Fitness Application (Microservices Architecture)

**IntelliFit** is a scalable, full-stack, AI-powered fitness application built using a **microservices architecture**.  
It leverages **Spring Boot**, **React**, and **modern cloud-native tools** to deliver personalized health recommendations powered by AI.

---

## 🚀 Project Overview
This project demonstrates the development of a modern, distributed fitness platform using **Spring Cloud Microservices**.  
It integrates **AI**, **containerization**, and **secure authentication** to provide a real-world, production-ready fitness solution.

### 🎯 Key Highlights
- Fully featured fitness application using **Microservices Architecture**  
- **AI Integration** using Google Gemini API  
- **User & Activity Management** with PostgreSQL and MongoDB  
- **Secure Authentication** using Keycloak  
- **Scalable Communication** with RabbitMQ  
- **Centralized Configuration** with Spring Cloud Config Server  
- **Service Discovery** using Eureka  
- **Containerized Deployment** with Docker & Kubernetes  

---

## 🧩 Tech Stack

**Backend:**
- Spring Boot (Microservices)
- Spring Cloud Gateway
- Eureka Server (Service Registry)
- RabbitMQ (Async Messaging)
- Spring Cloud Config Server
- Keycloak (Authentication & Authorization)
- PostgreSQL / MySQL
- MongoDB
- Google Gemini API (AI Service)

**Frontend:**
- React.js

**DevOps:**
- Docker
- Kubernetes
- AWS (for cloud deployment)
- Prometheus + Grafana (Monitoring)

---

## 🏗️ Microservices Implemented

| Service | Description | Status |
|----------|--------------|--------|
| **User Service** | Handles user registration, authentication, and profile management | ✅ Completed |
| **Activity Service** | Manages fitness activity data and logs | ✅ Completed |
| **AI Service** | Generates personalized recommendations using Google Gemini API | 🔄 In Progress |

---

## ⚙️ Architecture Diagram
*(Placeholder – Will be added soon)*  
The architecture follows a **Spring Cloud Microservices** pattern with Eureka, API Gateway, Config Server, and independent services for user, activity, and AI modules.

---

## 💡 Future Enhancements
- Add AI model fine-tuning for improved recommendations  
- Build admin dashboard for analytics  
- Enable CI/CD pipeline using GitHub Actions  

---

## 🧑‍💻 Author
**AK**  
MCA Final Year Project, SRM University  
*Guided by [Guide]*

---

## 📅 Project Timeline
| Phase | Deadline |
|--------|-----------|
| Abstract Submission | August 24, 2025 |
| Zeroth Review | September 7, 2025 |
| First Review | September 21, 2025 |
| Second Review | October 12, 2025 |
| Final Review | November 2, 2025 |
| Report Submission | November 10, 2025 |
| Mock Viva | November 16, 2025 |

---

## 📜 License
This project is for **academic and learning purposes only**.  
All trademarks and technologies belong to their respective owners.

---

## 🧩 Project Progress Update (as of October 11, 2025)

### ✅ Completed
- **AI Service Microservice**
    - Created new **AI-Service** (Spring Boot + MongoDB + Eureka Client).
    - Configured **RabbitMQ** integration with custom `exchange`, `queue`, and `routingKey`.
    - Implemented `RabbitMqConfig` and `@RabbitListener` to consume activity messages.
    - Verified successful **end-to-end asynchronous communication**:  
      **User → Activity → RabbitMQ → AI-Service**.
    - Fixed Eureka IP resolution issues using `prefer-ip-address: true`.
    - Confirmed service registration in **Eureka Server** and live message flow in **RabbitMQ UI**.
    - Connected **MongoDB (`fitnessrecommendation`)** and verified data persistence.

- **Activity Service Enhancements**
    - Integrated `RabbitTemplate` for publishing activity data asynchronously.
    - Configured exchange and routing properties via `application.yml`.
    - Added `Jackson2JsonMessageConverter` in `RabbitMqConfig`.
    - Verified message publishing to RabbitMQ on new activity events.

- **Eureka & Interservice Communication**
    - Completed **Eureka Service Discovery** setup for all microservices.
    - Fixed DNS/NXDOMAIN issue and validated service registration using localhost IPs.
    - Ensured seamless communication among **User Service**, **Activity Service**, and **AI Service**.

### 🔄 In Progress
- **AI Service Recommendation Logic**
    - Implementing logic to store and analyze activity data for personalized recommendations.
    - Adding persistence with `RecommendationRepository.save()` and idempotency via `existsByActivityId`.
    - Planning retry and DLQ (Dead Letter Queue) handling for message processing.

### 🧠 Next Steps
- Complete **AI recommendation generation and persistence layer**.
- Add observability using **Micrometer / OpenTelemetry** for message flow tracking.
- Begin **Docker containerization** for all microservices.
- Prepare **System Architecture Diagram** and update documentation.
- Develop **React Frontend** for user activity visualization and AI recommendations.

📌 *Next milestone:* Full AI-driven recommendation workflow with persistence and analytics integration.

---

## 🧩 Project Progress Update (as of October 10, 2025)

### ✅ Completed
- **User Service Microservice**
  - Implemented user registration, authentication, and profile management.
  - Integrated **PostgreSQL** for persistent data storage.
  - Secured endpoints using **Keycloak** authentication.
  - Successfully tested all endpoints.

- **Activity Service Microservice**
  - Developed RESTful APIs for logging, updating, and fetching user fitness activities.
  - Integrated **MongoDB** for flexible activity data storage.
  - Connected with **User Service** via REST calls and **RabbitMQ** for asynchronous event handling.
  - Completed **inter-service communication testing** using **Eureka Service Registry**.

- **Project Documentation**
  - Completed detailed sections:
    - *Introduction*  
    - *Analysis & Requirement*  
    - *Problem Description / Modules Description*
  - Ready for inclusion in First/Second Review submission.

### 🔄 In Progress
- **AI Service Microservice**
  - Building service to process activity data and generate personalized recommendations.
  - Integrating with **Google Gemini API** for AI-driven analysis.

### 🧠 Next Steps
- Complete **AI Service** implementation and connect with Activity Service.
- Prepare **System Architecture Diagram** for documentation.
- Begin **Docker containerization** for all microservices.
- Develop **React Frontend** for user dashboard and AI recommendations.

📌 *Next milestone:* Deploy all services locally with Docker and connect frontend to backend microservices.
