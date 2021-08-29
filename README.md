# finance-fund-web-service 

  (current version: 0.0.1, session-based REST)

* Spring boot as a REST backend
* Use filters to check session-account and account role
* Session hold for 15 minutes for logged-in account

### Common User Cases
* login & logout
* view price of funds
* view self-profile
* update self-profile
* update self-password

### Normal/Custom Account User Cases
* buy funds
* sell funds
* request to get checks

### Admin Account User Cases
* view all current accounts
* register new custom accounts
* create new funds
* deposit checks for normal user
* change fund price in transition day
* reset custom-password

### Notes
* in a current session, password has been protected no we need to check DB using username
* once self-profile is updated successfully, current session will be refreshed

-------------------------
## API Endpoints in Development

baseUrl = localhost:8080, all URIs below based on baseUrl

### Common actions

* POST `/login` with payload JSON, request example:
```json
{
    "username": "dev",
    "password": "dev"
}
```

success response:
```json
{
    "message": "Welcome dev."
}
```
failed response:
```json
{
    "message": "There seems to be an issue with the username/password combination that you entered."
}
```

* POST `/logout` directly without payload
```json
{
    "message": "You have been successfully logged out."
}
```

* GET `/funds` view information a list of all funds (JSON Array)
```json
[
    {
        "id": 1,
        "name": "Amazon",
        "symbol": "AMZN",
        "price": 3312.19
    },
    {
        "id": 2,
        "name": "Apple",
        "symbol": "AAPL",
        "price": 462.25
    },
    {
        "id": 3,
        "name": "Facebook",
        "symbol": "FB",
        "price": 262.34
    },
    {
        "id": 4,
        "name": "Google",
        "symbol": "GOOGL",
        "price": 1555.78
    }
]
```
* GET `/funds/{id}` view information an individual fund
```json
{
    "id": 2,
    "name": "Apple",
    "symbol": "AAPL",
    "price": 462.25
}
```

* GET `/profile` view self-Profile information
```json
{
    "name": "NameNormal",
    "message": "You don't have any funds in your portfolio.",
    "cash": 12000.0,
    "email": "normal111@security.com",
    "username": "normal"
}
```

* PUT `/profile` update self-profile information
```json
{
    "email": "updated@aa.c",
    "name": "updatedNameC"
}
```
```json
{
    "message": "Your profile has been updated successfully"
}
```

* PUT `/password` update self-password
```json
{
    "currentPassword": "dev",
    "newPassword": "112",
    "newPasswordConfirm": "112"
}
```
```json
{
    "message": "Your password has been updated successfully."
}
```

### Custom only actions

* POST `/funds/buy` buy fund with request payload
```json
{
    "symbol": "FB",
    "cashValue": "1234"
}
```
```json
{
    "message": "The fund has been successfully purchased."
}
```

* POST `/funds/sell` sell fund with request payload
```json
{
    "symbol": "FB",
    "numShares": "3"
}
```
```json
{
    "message": "The shares have been successfully sold."
}
```

* POST `/requestCheck` request to get paycheck
```json
{
    "cashValue": "368"
}
```
```json
{
    "message": "Your check request has been initialized."
}
```

### Admin only actions

* GET `/admin/accounts/` view all current accounts
```json
[
    {
        "id": "id-0000",
        "email": "dev@security.com",
        "username": "dev",
        "name": "NameDev",
        "password": "[***protected***]",
        "role": "SUPER_ADMIN",
        "cash": 0.0
    },
    {
        "id": "id-0001",
        "email": "sa@sa.com",
        "username": "sa",
        "name": "NameSuperAdmin",
        "password": "[***protected***]",
        "role": "SUPER_ADMIN",
        "cash": 0.0
    }
]
```

* GET `/admin/accounts/{id}` view one accounts
```json
{
    "id": "id-0000",
    "email": "dev@security.com",
    "username": "dev",
    "name": "NameDev",
    "password": "dev",
    "role": "SUPER_ADMIN",
    "cash": 0.0
}
```

* POST `/admin/accounts` create new accounts
```json
{
    "email": "add@bb.ccc",
    "username": "dev2",
    "name": "dev2",
    "password": "111",
    "password2": "111",
    "cash": 2000.0
}
```
```json
{
    "message": "The account was successfully created."
}
```

* POST `/admin/funds` create new funds
```json
{
    "name": "test",
    "symbol": "TEST",
    "initialPrice": "1234.05"
}
```
```json
{
    "message": "The fund was successfully created."
}
```

* POST `/admin/depositCheck` deposit money for normal user
```json
{
    "username": "normal",
    "amount": "999"
}
```
```json
{
    "message": "The check was successfully deposited."
}
```

* POST `/admin/transitionDay` change fund price in transition day (no payload needed)
```json
{
    "message": "The fund prices have been successfully recalculated",
    "fluctuation": {
        "GOOGL": "1684.11 (8.25%)",
        "AAPL": "504.45 (9.13%)",
        "TEST": "1173.12 (-4.94%)",
        "FB": "275.82 (5.14%)",
        "AMZN": "3482.21 (5.13%)"
    }
}
```

* POST `/reset-custom-password` reset custom-password
```json
{
    "username": "normal"
}
```
```json
{
    "message": "Successfully generated new password for normal: 886ef877."
}
```
