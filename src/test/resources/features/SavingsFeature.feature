Feature: Savings feature in top menu display correct value

  Scenario: As logged out customer add Half price item to basket
    Given a customer on Browse Shop page
    When customer add item in Half price promo to basket
    Then saving field is updated
    And contains correct savings value