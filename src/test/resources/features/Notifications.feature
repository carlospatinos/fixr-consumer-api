Feature: Notifications
    As a user
    I want to receive notifications
    So that I stay updated with important information

    Scenario: Retrieving a notification by ID
        Given a notification with ID "123"
        When I retrieve the notification
        Then the notification details should be returned