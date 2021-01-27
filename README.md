## Setup
* Clone the repo
* Install dependencies `mvn install`
* Set environment variables with your [BrowserStack Username and Access Key](https://www.browserstack.com/accounts/settings). 

## Running your tests
* To run the test, run `mvn test`

## Notes
* You can view your test results on the [BrowserStack Automate dashboard](https://www.browserstack.com/app-automate)
* You can export the environment variables for the Username and Access Key of your BrowserStack account. 

  ```
  export BROWSERSTACK_USERNAME=<browserstack-username> &&
  export BROWSERSTACK_ACCESS_KEY=<browserstack-access-key>
  ```