# Splitwise App - Backend Server

A comprehensive backend server for a Splitwise-like expense splitting application built with Spring Boot, featuring user management, group management, expense tracking, and automated debt calculations.

## Features

### 🔐 Authentication & Security
- JWT-based authentication
- User registration with email verification
- Password reset functionality
- Role-based access control
- Secure password hashing with BCrypt

### 👥 User Management
- User registration and login
- Profile management
- Email verification system
- Password reset capabilities

### 🏠 Group Management
- Create and manage expense groups
- Add/remove group members
- Different group types (General, Trip, Home, Office, Friends, Couple, Other)
- Group status management (Active, Archived, Deleted)

### 💰 Expense Tracking
- Add expenses with detailed information
- Multiple split types (Equal, Exact, Percentage, Shares)
- Transaction status tracking
- Receipt upload support
- Currency support (default: USD)

### 📊 Debt Management
- Automatic debt calculations
- Settlement tracking
- Payment method support
- Debt simplification algorithms

### 📧 Email Notifications
- Welcome emails
- Expense notifications
- Settlement confirmations
- Password reset emails

## Technology Stack

- **Java 8+**
- **Spring Boot 2.7.16**
- **Spring Security** with JWT
- **Spring Data JPA**
- **MySQL Database**
- **Lombok** for reducing boilerplate
- **Maven** for dependency management

## Prerequisites

- Java 8 or higher
- Maven 3.6+
- MySQL 8.0+
- SMTP server access (for email functionality)

## Setup Instructions

### 1. Database Setup
```sql
CREATE DATABASE splitwise_db;
CREATE USER 'splitwise_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON splitwise_db.* TO 'splitwise_user'@'localhost';
FLUSH PRIVILEGES;
```

### 2. Configuration
Update `src/main/resources/application.properties` with your configuration:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/splitwise_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=splitwise_user
spring.datasource.password=your_password

# Email Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password

# JWT Configuration
jwt.secret=your-super-secret-jwt-key-here-make-it-very-long-and-secure
jwt.expiration=86400000
```

### 3. Build and Run
```bash
# Clean and compile
./mvnw clean compile

# Run the application
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Authentication (`/api/auth`)
- `POST /register` - User registration
- `POST /login` - User login
- `GET /verify-email` - Email verification
- `POST /resend-verification` - Resend verification email
- `POST /forgot-password` - Initiate password reset
- `POST /reset-password` - Reset password
- `POST /validate-token` - Validate JWT token

### Groups (`/api/groups`)
- `POST /` - Create new group
- `GET /{id}` - Get group details
- `GET /` - Get user's groups
- `PUT /{id}` - Update group
- `POST /{id}/members/{memberId}` - Add member to group
- `DELETE /{id}/members/{memberId}` - Remove member from group
- `DELETE /{id}` - Delete group

### Test (`/api/test`)
- `GET /health` - Health check
- `GET /ping` - Simple ping endpoint

## Database Schema

The application uses the following main entities:
- **User** - User accounts and profiles
- **Group** - Expense groups
- **Transaction** - Individual expenses/payments
- **TransactionSplit** - How expenses are split among users
- **Settlement** - Debt settlements between users

## Security Features

- JWT token-based authentication
- Password encryption with BCrypt
- Email verification for new accounts
- Secure password reset process
- Role-based access control
- CORS configuration for frontend integration

## Email Configuration

The application supports multiple email providers. For Gmail:
1. Enable 2-factor authentication
2. Generate an App Password
3. Use the App Password in the configuration

## Development

### Project Structure
```
src/main/java/com/splitwise/app/
├── config/          # Configuration classes
├── controller/      # REST controllers
├── dto/            # Data Transfer Objects
├── entity/         # JPA entities
├── exception/      # Custom exceptions
├── repository/     # Data access layer
├── security/       # Security configuration
├── service/        # Business logic
└── SplitwiseApplication.java
```

### Adding New Features
1. Create entity classes in the `entity` package
2. Create DTOs in the `dto` package
3. Create repository interfaces in the `repository` package
4. Implement business logic in the `service` package
5. Create REST endpoints in the `controller` package

## Testing

```bash
# Run tests
./mvnw test

# Run with coverage
./mvnw jacoco:report
```

## Deployment

### Docker (Recommended)
```dockerfile
FROM openjdk:8-jre-alpine
COPY target/splitwise-app-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### Traditional Deployment
1. Build the JAR: `./mvnw clean package`
2. Deploy the JAR from `target/` directory
3. Configure environment variables
4. Run with: `java -jar splitwise-app.jar`

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License

This project is licensed under the MIT License.

## Support

For issues and questions:
1. Check the existing issues
2. Create a new issue with detailed description
3. Include logs and error messages

## Roadmap

- [ ] Transaction categories and tags
- [ ] Recurring expenses
- [ ] Export functionality (PDF, CSV)
- [ ] Mobile app API endpoints
- [ ] Real-time notifications
- [ ] Multi-currency support
- [ ] Advanced debt optimization algorithms