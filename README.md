# ViewAccountStatementApi

The server will handle requests to view statements by performing simple search on date and amount ranges.
- The request should specify the account id.
- The request can specify from date and to date (the date range).
- The request can specify from amount and to amount (the amount range).
- If the request does not specify any parameter, then the search will return three months back statement.
- If the parameters are invalid a proper error message should be sent to user.
- The account number should be hashed before sent to the user.
- All the exceptions should be handled on the server properly.

The authenticated users are:
User1: Username: admin & Password: admin
User2: Username: user & Password: user

- The ‘admin’ can perform all the requests (specify date and amount range).
- The ‘user’ can only do a request without parameters which will return the three months back statement.
- When the test user tries to specify any parameter, then HTTP unauthorized (401) access error will be sent.
- The user cannot login twice (the user should logout before login).
- The session time out is 5 minutes.

Database file: accountsdb.accdb

Java Framework used - 
1. Spring Boot for creating microservices
2. Lombok for code concise and logging support
3. Spring Security for authorization and authentication
4. ucanaccess source code for connecting to MS-Access DB
5. Common Codec to implement SHA256 hashing algorithm
6. Junit and Rest Assured for unit testing
7. Sonarqube plugin to generate Sonarqube report

Prerequisite-
1. Maven should be installed
2. Create folder C:\dev\ms-access-db to place accountsdb.accdb DB file from \ViewAccountStatementApi\database folder

Base URI- 
http://localhost:8080

Request Structure - 
1. /login - To login using admin or user credentials
2. /account/{id} - Get request to get the statement for id for 3 months. 
Example : http://localhost:8080/account/3
3. /statement/parameter?id=?&fromDate=?&toDate=?&fromAmount=?&toAmount=? - Get request to get the statement for range of amount and range of date for particular id
Example : http://localhost:8080/statement/parameter?id=5&fromDate=2011-01-01&toDate=2012-12-31&fromAmount=100&toAmount=200
4. /logout - To logout the current user

Sample Response - 
1. Successful response -[{"accountNumber":"b67b64ab19f2c023f460876e84dd917ed8f04798c06e3012092c633e0e9818e7","accountType":"current","datefield":"24.01.2021","amount":"564.982890505824"}]

2. Error response - {"message":"UNAUTHORIZED : 401"}

SonarQube report - 
![image](https://user-images.githubusercontent.com/81948411/114399744-535cb280-9bb2-11eb-8d33-ea805e8bf668.png)

