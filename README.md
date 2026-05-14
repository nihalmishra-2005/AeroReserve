# AeroReserve ✈️



AeroReserve is a full-stack Airline Reservation System developed using Java Spring Boot, Thymeleaf, MySQL, and Spring Security.

The project provides a complete flight booking experience where users can search flights, select seats, book tickets, and manage reservations through a secure and user-friendly interface.



---



# 🚀 Features



## User Features



* User Registration and Login

* Secure Authentication with Spring Security

* Search Flights by Source and Destination

* Dynamic Flight Availability

* Economy, Business, and First-Class Seat Selection

* Real-Time Seat Booking

* Booking Confirmation

* Booking Cancellation

* User Dashboard for Managing Reservations



## Admin Features



* Admin Dashboard

* Add and Manage Flights

* Manage Seats

* View All Users

* View All Bookings

* Monitor Flight Availability



---



# 🛠️ Tech Stack



## Backend



* Java 17

* Spring Boot

* Spring MVC

* Spring Security

* Spring Data JPA

* Hibernate



## Frontend



* Thymeleaf

* HTML5

* CSS3

* JavaScript



## Database



* MySQL



## Build Tool



* Maven



---



# 📂 Project Structure



```bash

src/

 ├── main/

 │    ├── java/com/airline

 │    │      ├── controller

 │    │      ├── model

 │    │      ├── repository

 │    │      ├── security

 │    │      └── service

 │    └── resources/

 │           ├── static

 │           ├── templates

 │           └── application.properties

```



---



# ⚙️ Installation & Setup



## 1️⃣ Clone the Repository



```bash

git clone https://github.com/nihalmishra-2005/AeroReserve.git

```



---



## 2️⃣ Open Project



Open the project in:



* IntelliJ IDEA

* VS Code

* Spring Tool Suite (STS)



---



## 3️⃣ Configure MySQL Database



Create a database in MySQL:



```sql

CREATE DATABASE airline_db;

```



---



## 4️⃣ Update application.properties



Update your MySQL username and password:



```properties

spring.datasource.url=jdbc:mysql://localhost:3306/airline_db

spring.datasource.username=root

spring.datasource.password=your_password



spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

```



---



# ▶️ Run the Application



Open terminal inside the project folder and run:



```bash

mvn spring-boot:run

```



---



# 🌐 Access the Application



Open browser and visit:



```bash

http://localhost:8080

```



Login Page:



```bash

http://localhost:8080/auth/login

```



---



# ✈️ Available Flight Routes



The system currently supports multiple routes such as:



* Delhi → Mumbai

* Mumbai → Bangalore

* Hyderabad → Agra

* Chandigarh → Delhi

* Agra → Hyderabad

* New Delhi → Mumbai

* Mumbai → Hyderabad

* Bangalore → Delhi



Flight search supports:



* Any future date

* Multiple destinations

* Dynamic flight availability



---



# 💺 Seat Categories



The project supports:



* Economy Class

* Business Class

* First Class



Each category contains:



* Separate pricing

* Different seat allocation

* Real-time booking availability



---



# 🔒 Security



* Spring Security Authentication

* Password Encryption

* Role-Based Access

* Protected Admin Routes



---



# 📊 Database Tables



* users

* flights

* seats

* bookings



---



# 📸 Main Modules



* Authentication Module

* Flight Search Module

* Seat Booking Module

* Booking Management Module

* Admin Management Module



---



# 🧠 Future Enhancements



* Online Payment Gateway

* Email Notifications

* Ticket PDF Download

* Flight API Integration

* Responsive Mobile UI

* Live Flight Status Tracking



---



# 👨‍💻 Author



Nihal Mishra

MCA Student — Lovely Professional University



---



# 📌 Repository



GitHub Repository:

https://github.com/nihalmishra-2005/AeroReserve



---



# ⭐ Project Status



✅ Completed and Working Successfully

