# Currency Conversion Spring Boot Application

This is a simple Spring Boot application that integrates with the Exchange Rates API to provide real-time currency conversion.

## Requirements

- JDK 11+
- Maven
- A valid API key for the Exchange Rates API (https://exchangeratesapi.io/)

## Setup

1. Clone the repository:
git clone https://github.com/your-repo/currency-conversion.git

css
Copy

2. Navigate to the project directory:
cd currency-conversion

markdown
Copy

3. Add your API key to `application.properties`:
exchange.api.key=YOUR_API_KEY

markdown
Copy

4. Build the project:
mvn clean install

markdown
Copy

5. Run the application:
mvn spring-boot:run

markdown
Copy

## API Endpoints

1. **Get Exchange Rates**

- **URL**: `/api/rates?base=USD`
- **Method**: GET
- **Query Parameters**: `base` (default `USD`)
- **Response**: JSON with exchange rates

2. **Convert Currency**

- **URL**: `/api/convert`
- **Method**: POST
- **Request Body**:
  ```json
  {
    "from": "USD",
    "to": "EUR",
    "amount": 100
  }
  ```
- **Response**:
  ```json
  {
    "from": "USD",
    "to": "EUR",
    "amount": 100,
    "convertedAmount": 94.5
  }
  ```

## Running Tests

Run the tests with Maven:
mvn test

csharp
Copy

## License

This project is licensed under the MIT License.