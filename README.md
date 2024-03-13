# SecureKey App

This Java Swing application provides secure storage for usernames and passwords.

## Database Setup

Before running the application, you need to set up a local SQL Server database. Follow these steps:

1. Install SQL Server on your machine. You can download it from the official Microsoft website.

2. Create a new database named `users`.

3. Inside the `users` database, create a new table named `credentials` with the following columns:
   - `username` (type VARCHAR)
   - `password` (type VARCHAR)

4. Replace the following lines in the `databaseManager` class with your actual database details:

private final String url = "jdbc:mysql://127.0.0.1:3306/users";
private final String username = "root";
private final String password = "sanda";


## Running the Application

Once the database is set up, you can run the application. Follow these steps:

1. Clone this repository.

2. Open the project in your favorite IDE.

3. Run the `Main` class to start the application.

