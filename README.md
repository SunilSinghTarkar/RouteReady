# Online-Cab-Booking-Application (RouteReady)
This application allows customers to view and book available cabs for their desired locations, and it provides administrators with tools to manage cabs, drivers, and bookings. 

### Project Details

- Project Name: RouteReady
- Project URL: [https://65293c65df9a13315fc57226--precious-paprenjak-aa7841.netlify.app/](https://65293c65df9a13315fc57226--precious-paprenjak-aa7841.netlify.app/)
- GitHub Repository: [https://github.com/SunilSinghTarkar/potent-voyage-8716](https://github.com/SunilSinghTarkar/potent-voyage-8716)
  
 <h2 align="center">Features </h2>
<h3>Customer Module</h3>
<!--  <br /> -->
<b>  User Registration: </b>  Customers can register with the application by providing their personal information and contact details.

Login: Registered customers can log in to their accounts securely using Spring Security.

<b>View Available Cabs:</b> Customers can browse a list of available cabs with details such as cab type, driver information, and current location.

<b>Book a Cab:</b> Customers can book a cab for their desired destination from a specified pickup location. They can choose a cab type and view the estimated fare before confirming the booking.

<b>View Booking History:</b> Customers can access their booking history to see their past trips and their details.

<h3>Admin Module</h3>
<b>Login:</b> Admins can log in to the system securely using Spring Security.

<b>Cab Management:</b> Admins can manage the list of available cabs, including adding new cabs, updating cab information, and marking cabs as unavailable when needed.

<b>Driver Management:</b> Admins can manage the pool of drivers, including adding new drivers, updating driver information, and marking drivers as unavailable when required.

<b>Booking Management:</b> Admins can view and manage all bookings, including approving or rejecting booking requests and resolving any issues that may arise.

<h3>Cab Management Module</h3>
<b>Add New Cab:</b> Admins can add new cabs to the system, specifying details such as cab type, registration number, and driver assigned.

<b>Update Cab Information:</b> Admins can modify cab information, such as the current location or availability status.

<b>Remove Cab:</b> Admins can mark cabs as unavailable or remove them from the system when necessary.

<h3>Driver Management Module</h3>
<b>Add New Driver:</b> Admins can add new drivers to the system, providing driver details and contact information.

<b>Update Driver Information:</b> Admins can edit driver information and update their status (available/unavailable).

<b>Remove Driver</b>: Admins can mark drivers as unavailable or remove them from the system if needed.

<h3>Booking Management Module</h3>
<b>View Booking Requests:</b> Admins can view all booking requests, including the customer's details, pickup location, and destination.

<b>Approve/Reject Bookings:</b> Admins can approve or reject booking requests based on availability and other factors.

<b>View Booking History:</b> Admins can access the history of all bookings, including details of completed, ongoing, and cancelled trips.

Technologies Used
Backend: Java, Spring Boot, Spring Security, JPA

<b>Database:</b> MySQL

<b>Frontend:</b> HTML, CSS, Javascript

<b>Authentication:</b> Spring Security with JWT (JSON Web Tokens)

<h3>How to Run the Application</h3>
Clone the repository from GitHub.

Set up the database by executing the provided SQL scripts in MySQL.

Configure the database connection properties in the application properties file.

Build and run the Spring Boot application.

Access the application through a web browser and register/login as a customer or admin.

Start using the application, booking cabs, and managing the system as an admin.

## Installation & Run

Before running the API server, please follow these steps:

1. Update the database configuration inside the `application.yml` file located in your project:
 ```yaml
server:
  port: 8088
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    password: your_password
    url: jdbc:mysql://localhost/your_database_name
    username: your_username
```
Future Enhancements
Implement real-time tracking of booked cabs for customers.

Integrate a payment gateway for fare transactions.

Develop a mobile application for wider accessibility.

Add a rating and feedback system for customers and drivers.


<H3>Contributors</H3>

Thank you to the following contributors for their valuable contributions to this project:
- [Sunil Singh Tarkar](https://github.com/SunilSinghTarkar)
- [Amit Roy](https://github.com/Amit0841)
- [Tushar Deshmukh](https://github.com/deshmukh-tushar)


<H3>Database architecture</H3>

![image](https://github.com/SunilSinghTarkar/potent-voyage-8716/assets/115461383/17e7f8af-48f6-444d-9312-2ea75aaf8917)


