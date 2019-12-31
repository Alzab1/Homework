Feature: Test login page
 Scenario Outline: Test positive smoke user behavior
   Given I am on main application page
   When I login as correct user
   Then I see logout link
   When I add letter to spam
   Then I see letter in spam folder
   When I return letter from spam
   Then I see letter in inbox folder
   When I select <qtySelectLetters> letters
   And I flag selected letters
   Then I see selected <qtySelectLetters> letters are flagged
   When I deflag all letters
   Then I see all letters are deflaged
   When I send letter for group
   Then I see window that letters are sent
Examples:
      | qtySelectLetters |
      | 3                |
