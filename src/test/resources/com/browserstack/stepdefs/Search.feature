Feature: Search for BrowserStack wikipedia page
  Scenario: Enter search term and view related pages
    Given I am on the 'Wikipedia' app
    When I submit the search term 'BrowserStack'
    Then The search result is not empty