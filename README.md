# finance-web-service-session-v1
Self-project-improvement

* Session hold for 15 minutes for logged-in account
* Filters to check session-account and account role

### Normal/Custom Account User Cases
* login & logout
* check all current funds and individual fund
* buy fund
* sell fund
* request check
* update self-information
* change self-password

### Admin Account User Cases
* register new custom account
* check all current accounts
* create new fund
* deposit check for normal user
* transition day to change fund price
* reset custom-password

### Notes
* in a current session, password has been protected no we need to check DB using username
