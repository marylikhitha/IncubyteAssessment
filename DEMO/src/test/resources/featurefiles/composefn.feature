Feature: Compose function in Gmail

  Scenario Outline: Validation of Compose Function in Gmail
    Given : User navigate to gmail page
    And : User log in using valid username as "<username>" and password as "<password>"
    When : User sends an email to the recipient as "<torecepient>" with subject as "<subject>" and body as "<body>"
    Then : User should be able to see email in sent folder with subject as "<subject>"

    Examples:
      |subject|   |body|                            |torecepient|              |username|                 |password|
      |Incubyte|  |Automation QA test for Incubyte| |marylikhitha22@gmail.com| |example8473@gmail.com|   |Example_7|

