# finance-fund-web-service (v1, session-based REST)

* Spring boot as a REST backend
* Use filters to check session-account and account role
* Session hold for 15 minutes for logged-in account

### Common User Cases
* login & logout
* check price of funds
* check self-profile
* update self-profile
* change self-password

### Normal/Custom Account User Cases
* buy funds
* sell funds
* request checks

### Admin Account User Cases
* register new custom accounts
* create new funds
* deposit checks for normal user
* transition day to change fund price
* check all current accounts
* reset custom-password

### Notes
* in a current session, password has been protected no we need to check DB using username
* once self-profile is updated successfully, current session will be refreshed

-------------------------
## API Endpoints in Development

baseUrl = localhost:8080, all URIs below based on baseUrl

### common

* POST `/login` with payload JSON, example:
```json
{
    "username": "dev",
    "password": "dev"
}
```

```json
{
    "message": "Welcome dev"
}
```

* POST `/logout` directly without payload
```json
{
    "message": "You have been successfully logged out"
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
    "message": "You don't have any funds in your Portfolio",
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
    "message": "Your password has been updated successfully"
}
```
