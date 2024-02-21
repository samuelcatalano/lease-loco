# Technical Test

The aim of these tasks is to build a working solution in Java and Spring Boot that will import and return consolidated data that has been provided by third parties.

Things we are looking for:

- Simple, elegant code
- Nice, clean structure (assume this is something that would be put into production)
- Comments to clarify anything ambiguous
- Unit tests
- Instructions on how to build and run the code and any other services used e.g. database service


Things we will consider but are not essential:
- API documentation
- Integration tests
- Added comments to highlight any potential tech debt incurred
- Containerised solution using Docker

## Task 1

Given two CSV files with leasing offers from different suppliers with differing formats, produce an automated solution to read the data from the CSV files and import it into a relational database. The database schema should be designed to store the data in a uniform way that would allow for the records in both files to be combined, inserted and queried to find entries with matching attributes.

It's important to note that the CSV files represent leasing offers for the same two cars (Audi A5 and Audi A4) with three different prices for each, dependent on the term and mileage. They are in different formats - it is important to find a common format to represent the data in the database.

As an example, the row:

`2,"Audi","A5",£242.16,48,20k`

from the 'pricing.csv' file is referencing the same car as the row:

`"K9YC25FV439U","AUDI","A5","AUDI A5",20000,263.43,48`

from the 'data.csv' file.


#### 'pricing.csv' file from amazingcars.co.uk:

| id | name   | type | price   | term | mileage |
|----|--------|------|---------|------|---------|
| 1  | "Audi" | "A5" | £232.90 | 24   | "10k"   |
| 2  | "Audi" | "A5" | £242.16 | 48   | "20k"   |
| 3  | "Audi" | "A5" | £252.18 | 36   | "30k"   |
| 4  | "Audi" | "A4" | £172.73 | 36   | "20k"   |
| 5  | "Audi" | "A4" | £143.82 | 24   | "10k"   |
| 6  | "Audi" | "A4" | £171.29 | 48   | "30k"   |


### 'data.csv' file from prettygoodcardeals.com:

| ID             | MAKE   | MODEL | DESCRIPTION | MILEAGE | PRICE  | TERM |
|----------------|--------|-------|-------------|---------|--------|------|
| "49QAC4QM3ZDG" | "AUDI" | "A5"  | "AUDI A5"   | 10000   | 223.37 | 24   |
| "XS5237XMKB4J" | "AUDI" | "A4"  | "AUDI A4"   | 10000   | 192.23 | 24   |
| "37SKBZ85ADH6" | "AUDI" | "A4"  | "AUDI A4"   | 20000   | 172.23 | 36   |
| "QCG6C68AER68" | "AUDI" | "A4"  | "AUDI A4"   | 30000   | 158.27 | 48   |
| "49QAC4QM3ZDG" | "AUDI" | "A5"  | "AUDI A5"   | 30000   | 241.62 | 36   |
| "K9YC25FV439U" | "AUDI" | "A5"  | "AUDI A5"   | 20000   | 263.43 | 48   |

## Task 2

Create an API endpoint to query the data from `Task 1`:

API Resource path:
`/offers`

Method:
`GET`

This endpoint needs to accept the below URL parameters, but it doesn't need to be limited to them.

| param     | description                            |  optional? |
|-----------|----------------------------------------|------------|
| `make`    | the make of the vehicle                |    yes     |
| `model`   | the model of the vehicle               |    yes     |
| `mileage` | mileage allowance for this offer       |    yes     |
| `term`    | the payment term of the offer in months|    yes     |
| `price`   | the payment price                      |    yes     |


Some example requests and results:

| url                                              | result                                                                             |
|--------------------------------------------------|------------------------------------------------------------------------------------|
| `/offers?make=audi`                               | return entries with a make of `audi`                                              |
| `/offers?make=audi&model=a4`                      | return entries with a make of `audi` and a model of `a4`                          |
| `/offers?mileage=10000`                           | return entries with a mileage of `10000`                                          |
| `/offers?make=audi&model=a5&mileage=10000`        | return entries with a make of `audi`, a model of `a5` and a mileage of `10000`  |


Although there are only a few offers provided in the CSV files, when designing your solution, you should assume that there might be millions of offers in the supplier files and in your data store.

## Solution

### Tech Stack
| Technology | Version |
|--|--|
| **Java** | 17.0.1 2022-10-19 |
| **Spring Boot** | 3.2.2 |
| **Spring JPA** | 3.2.2 |
| **OpenCSV** | 5.9 |
| **Project Lombok** | 1.18.30 |
| **Jupiter JUnit** | 3.2.2 |
| **Mockito** | 4.8.1 |
| **H2 Memory** | 2.1.212 |
| **Springdoc OpenAPI Swagger** | 2.0.2 |

### Approach

As it was only defined that we should use an SQL database, I chose to use the H2 memory database to make things simpler. Therefore, once the application is started the following flow occurs:

The application creates an instance of H2 in memory and uses the `schema.sql` file to create the table referring to the convergence of offers from multiple providers.

After this, the `CSVLoanDataProvider` auxiliary class receives the parameters responsible for reading the `.csv` files previously defined through the `LeaseOffersNormaliseService` class.

In other words, after initializing the application, we have an in-memory database with a table called `lease_offer` that represents the consolidated data from the files.

### Access H2 Memory Database:

Once the application running you can access: http://localhost:8080/h2-console

jdbc: `jdbc:h2:mem:leaseloco`  
user: _sa_  
pass: _1234_

### API documentation
- http://localhost:8080/swagger-ui.html

## Running the application:
> IDE (IntelliJ, Eclipse, NetBeans):
- Importing the project as a Maven project on your favourite IDE.
- Build the project using Java 17
- Run/Debug project from Main Application Class :: `LeaseLocoApplication.java`

> Terminal:
- `mvn spring-boot:run`

## Running the tests
> Terminal:
- `mvn test`

## Docker
A `Dockerfile` is prepared to download an OpenJDK 17 Slim and install the application.

- Download the image `docker pull samuelcatalano/leaseloco:release`  
  _or_
- Run the command: `docker build --no-cache -t leaseloco/leaseloco:release .`
- Run the command: `docker run -p 8080:8080 leaseloco/leaseloco:release`

## APIs:
The basic URL path is: http://localhost:8080/offers

Requests examples and results (updated):

| url                                              | result                                                                             |
|--------------------------------------------------|------------------------------------------------------------------------------------|
| `/offers?make=audi`                               | return entries with a make of `audi`                                              |
| `/offers?make=audi&model=a4`                      | return entries with a make of `audi` and a model of `a4`                          |
| `/offers?mileage=10000`                           | return entries with a mileage of `10000`                                          |
| `/offers?make=audi&model=a5&mileage=10000`        | return entries with a make of `audi`, a model of `a5` and a mileage of `10000`    |
| `/offers?term=24`                                 | return entries with a term of `24`                                                |
| `offers?minPrice=200`                             | return entries with a minimum price of `200`                                      |
| `offers?maxPrice=170`                             | return entries with a maximum price of `170`                                      |
| `offers?minPrice=150&maxPrice=170`                | return entries with a price between `150` and `170`                               |


