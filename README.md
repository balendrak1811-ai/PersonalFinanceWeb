# Personal Finance Management Platform

**Team Name:** BAREBONES  
**Team Leader:** Balendra Kumar  
**Team Members:** Saurabh Kumar Singh, Sahil Kumar

## Overview

Personal Finance Management Platform is a Java web application for organising personal financial information. The application uses Spring Boot for the web layer, Thymeleaf for server-rendered HTML pages, and MySQL for database storage.

## Current Pages

The repository's `src/main/resources/templates/` directory contains these Thymeleaf templates:

- `login.html` — user login page
- `register.html` — user registration page
- `dashboard.html` — dashboard page
- `expenses.html` — expenses page
- `edit-expense.html` — edit expense page
- `budgets.html` — budgets page
- `financial-goals.html` — financial goals page

The presence of a page does not by itself guarantee that every feature is fully implemented or working; verify functionality in the running application.

## Technology Stack

- Java 17
- Spring Boot 4.1.1
- Thymeleaf
- Gradle
- MySQL Connector/J
- JDBC / MySQL database access
- Docker (deployment setup)
- GitHub
- Render (deployment platform)

## Project Structure

```text
PersonalFinanceWeb/
├── gradle/
│   └── wrapper/
├── src/
│   ├── main/
│   │   ├── java/com/example/personalfinanceweb/
│   │   │   ├── dao/
│   │   │   ├── model/
│   │   │   ├── util/
│   │   │   ├── HomeController.java
│   │   │   └── PersonalFinanceWebApplication.java
│   │   └── resources/
│   │       ├── templates/
│   │       │   ├── login.html
│   │       │   ├── register.html
│   │       │   ├── dashboard.html
│   │       │   ├── expenses.html
│   │       │   ├── edit-expense.html
│   │       │   ├── budgets.html
│   │       │   └── financial-goals.html
│   │       └── application.properties
│   └── test/
├── build.gradle
├── Dockerfile
└── README.md
```

## Requirements

- JDK 17
- Git
- Access to a MySQL database with the application's required tables
- Internet access to download Gradle dependencies

## Database Configuration

The `application.properties` file currently sets the application name. Configure the database connection according to the settings in `DatabaseConnection.java`.

The application connection utility uses the `DB_PASSWORD` environment variable for the database password. Set it before starting the application.

Windows Command Prompt (current terminal session):

```bat
set DB_PASSWORD=your_database_password
```

macOS / Linux:

```bash
export DB_PASSWORD=your_database_password
```

Replace the example with your actual password in your own environment. Never commit passwords, API keys, or other secrets to GitHub. Confirm that the database URL, username, SSL settings, and schema in the connection utility match your database service.

## Run Locally

1. Clone the repository:

   ```bash
   git clone https://github.com/balendrak1811-ai/PersonalFinanceWeb.git
   cd PersonalFinanceWeb
   ```

2. Install JDK 17.

3. Configure `DB_PASSWORD` and verify the MySQL connection details and required tables.

4. On Windows, run:

   ```bat
   gradlew.bat bootRun
   ```

   On macOS / Linux, run:

   ```bash
   ./gradlew bootRun
   ```

5. Open the local address shown in the application logs. Spring Boot commonly uses `http://localhost:8080` unless configured otherwise.

If the application fails to start, inspect the Gradle output, database connectivity, credentials, and table/column names.

## Deployment

The repository includes a `Dockerfile` for deployment. The application has been deployed using Render, with a MySQL database hosted separately. Deployment requires correct environment variables and an accessible database.

Live deployment: https://personal-finance-manager-55v8.onrender.com/

## Team

- **Balendra Kumar** — Team Leader
- **Saurabh Kumar Singh** — Team Member
- **Sahil Kumar** — Team Member

## Repository

https://github.com/balendrak1811-ai/PersonalFinanceWeb
