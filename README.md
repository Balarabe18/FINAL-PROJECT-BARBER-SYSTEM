# Barbershop Management System
The **Barbershop Management System** is a web application designed to streamline operations for barbers, shop owners, and 
customers. This project focuses on making appointment scheduling, role management, and service tracking efficient and user-friendly.

## Features
- **Appointment Scheduling**: Customers can book, reschedule, and cancel appointments seamlessly.
- **Role Management**: Supports roles for Admin, Barber, and Customer with distinct functionalities.
  - **Admin**: Registers barbers, manages users, and oversees system operations.
  - **Barber**: Views and manages their appointment schedules.
  - **Customer**: Selects a barber and books appointments.
- **Secure Data Storage**: Uses MySQL for robust and reliable database management.
- **RESTful APIs**: Ensures smooth communication between frontend and backend components.

## Tech Stack
- **Backend**: Java with Spring Boot
- **Frontend**: Thymeleaf (HTML, CSS, JavaScript)
- **Database**: MySQL
- **Tools**: Postman (API testing), IntelliJ IDEA (Development IDE)

## Default Admin Credentials
To get started with the application, use the following default admin credentials:
- **Username**: `defaultAdmin`
- **Password**: `password`
The admin can log in, register barbers, and set up the system for other users.

## API Endpoints

### **Authentication**
- `GET /login`: Displays the login page.
- `GET /register`: Displays the registration page.
- `POST /register`: Registers a new user (customer).

### **Appointments**
- `POST /customer/appointments/book`: Books an appointment with a selected barber.
- `POST /customer/appointments/reschedule`: Reschedules an existing appointment.
- `POST /customer/appointments/cancel`: Cancels an appointment.
- `POST /customer/appointments/confirm`: Confirms an appointment (used by barbers).

### **Admin**
- `POST /admin/barbers/add`: Adds a new barber to the system.

### **Dashboard**
- `GET /dashboard`: Displays the user-specific dashboard.
  - Admin: Manage barbers and view system stats.
  - Barber: View appointments assigned to them.
  - Customer: View their appointment history and make bookings.

### **Photos**
- `GET /hair-styles`: Retrieves a list of hairstyle photos from an external API.

### **Home**
- `GET /`: Displays the home page, including a joke retrieved from an external API.



