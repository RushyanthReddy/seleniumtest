Feature: App to send Notifications
  Either by SMS or Mail

  Scenario: Create a new page for an app
    Given Go to quickfuse
    And Create a new app
    When We get started
    And Create a new page
    Then A new page is created

  Scenario: Add Elements to send notifications
    Given Go to Messaging
    When We add a SMS method
    And Connect the start to SMS method
    But If SMS is not sent add mail
    And Connect SMS to mail
    Then Add exit at all end points
    And Connect all the end points
