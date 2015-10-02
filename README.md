# ocado-tests

Example of usage Java + Selenium Webdriver + Cucumber to perform functional tests.

## Installation

You need on you computer Maven (installation:http://www.mkyong.com/maven/how-to-install-maven-in-windows/) and Java JDK 8.
After checkout this project, please be sure it was correctly build with maven.
(Perform 'mvn compile' in main project directory)

## To run tests:
To run test using cmoand line from main project directory:
```
mvn test -Dsuite="ocado" -Dbrowser="chrome"
```
You can also run it from your IDE using TestNG plugin. 
(Remember to uncomment "browser" parameter in ocado.xml file for that)

## Tested on

OS X 10.10.5
chrome 44.0
chromedriver 2.19

Windows 7
chrome 44.0
chromedriver 2.19