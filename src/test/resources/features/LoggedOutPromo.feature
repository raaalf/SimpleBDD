Feature: Logged out customer is able to add items on promo to basket

  Scenario: Add items on offer 'Buy any XX for XX' to the basket
    Given a customer on Browse Shop page
    When 'Buy any XX for XX' promo is available
    And promotion conditions are met
    Then total amount in basket is calculated correct