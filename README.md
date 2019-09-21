# money-transfer
A simple system for money exchange between different accounts

## Overview
This is a simple system through which three core entities are maintained which are as under:
1) Account
2) User
3) Transaction

All of these entities are related to each other and together they form this system. 

## Specification

### User
Each `User` in the system can exist as an independent user/customer without the need to have any account.

`User` entity has following attributes 
```
1. Id
2. First Name
3. Last Name
4. Email
5. Country
6. Currency
7. Creation Date
8. Update Date
``` 

Each `User` has a unique identifier which is `id` in the system. All fields are self explanatory.
While creation, valid `Country` should be provided which is validated by a library called <b>nv-i18n</b>.
If the `Currency` provided is not amongst the list of supported currencies, the user's currency would 
change to `USD`

###### User APIs
| API | Http Method | Usage |
|:---:|:-----------:|:-----:|
|localhost:8080/user/|PUT|Add a user|
|localhost:8080/user/|PATCH|Update a user|
|localhost:8080/user/id/:id|DELETE|Delete a user|
|localhost:8080/user/id/:id|GET|Get a user by id|
|localhost:8080/user/all|GET|Get all users|


### Account
Each `Account` in the system must be associated with a `User` and must have an account type. A `User`
can open multiple accounts of the same type and can close an account as well. An `Account` has a balance 
as well which is updated on every transaction.

`Account` entity has following attributes
```
1. Id
2. User Id 
3. Account Balance
4. Account Type
5. Open
6. Creation Date
7. Update Date
``` 

###### Account APIs
| API | Http Method | Usage |
|:---:|:-----------:|:-----:|
|localhost:8080/account/|PUT|Add an account|
|localhost:8080/account/|PATCH|Update an account|
|localhost:8080/account/id/:id|DELETE|Close an account|
|localhost:8080/account/id/:id|GET|Get an account by id|
|localhost:8080/account/user/:userId|GET|Get an account by user id|
|localhost:8080/account/user/:userId/accountType/:accountType|GET|Get an account by user id and account type|
|localhost:8080/account/all|GET|Get all accounts|
|localhost:8080/account/transfer/sender/:senderId/recipient/:recipientId/amount/:amount|POST|Transfer between accounts|
|localhost:8080/account/deposit/:id/amount/:amount|POST|Deposit to an  account|
|localhost:8080/account/withdraw/:id/amount/:amount|POST|Withdraw from an account|

### Transaction
Each interaction between the same accounts or different accounts is termed as `Transaction`. 
Each `Transaction` in the system must be between valid accounts and recipient and sender accounts can be 
same for in one transaction. The transaction amount must be in the currency of the recipient. In case
of currency mismatch, exchange is applied where the sender currency is changed to `USD` first and then
changed to the local currency of the recipient.

`Transaction` entity has following attributes
```
1. Id
2. From Account
3. To Account
4. Amount
5. Creation Date
6. Update Date
``` 

#### Supported Currencies
`PKR, USD (default currency), GBP, EUR, INR, YEN, AED`

#### Supported Account Types
`CURRENT(1), SAVINGS(2)`


## Built With

* [IntelliJ](https://www.jetbrains.com/idea/) - IDE used for Development
* [Maven](https://maven.apache.org/) - Dependency Management
* [Spark](http://sparkjava.com) - Java framework used
* [H2](https://www.h2database.com) - In memory database

## Authors

* **Muhammad Umer** 



## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details