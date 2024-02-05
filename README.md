# emi-calculator-app-be
Backend for emi-calculator app(Java)

## Description
 -- This project is a full-stack web application developed using Angular for the frontend and Java for the backend.
 -- It provides EMI for house, car etc when provided with loan details and also saves the calculated amount in the database(h2).

 ## Prerequisites
  --Java version - 21
  --Maven version - 3.8.4

## Install dependencies
`mvn clean install`

## Run Project
`mvn spring-boot:run`

## Run Tests
`mvn test`

## Endpoints
  --Post call - `http://localhost:9292/api/calculate/emi`
  --In-memory database console - `http://localhost:9292/h2-console`
    -- jdbc url - `jdbc:h2:mem:testdb`