<!-- GETTING STARTED -->

## Summary
This is the result out of challenge I've enrolled in, comprising some work done with Java with TestNG + Selenium + Docker.

### How to run

Before running this Test Suite, make sure that Docker is properly installed and working on your machine. 
If you wish to run this locally (via the IDE, meaning without a test container), comment out the 'testng_test' service on the docker-compose file and then, just run the 'deploy.sh' file.
If you're running this via test container, simply run the 'deploy.sh' file without any further modifications.

### Analyzing the results

Since the tests can be run in multiple ways, here's how you're able to retrieve the test reports containing all the tests cases that were executed, with their steps properly described.

1. Tests ran via Docker containers:
   1. Go to the volume related to the test container
   2. Open the 'build' folder and look for the 'testngOutput'
   3. Open the 'index.html' file to check the test results
   
2. Tests ran via IDE using the gradle plugin:
   1. Simply run the project with 'gradle clean build'
   2. Reports can also be found on the 'build' folder but this time, on the system's own directory
