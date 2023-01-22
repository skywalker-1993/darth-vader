<!-- GETTING STARTED -->

## Summary
This is the result out of challenge I've enrolled in, comprising some work done with Java with TestNG + Selenium + Docker.

### How to run

Before running this Test Suite, make sure that Docker is properly installed and working on your machine. 
If you wish to run this locally (via the IDE, meaning without a test container), comment out the 'testng_test' service on the docker-compose file and then, just run the 'deploy.sh' file.
If you're running this via test container, simply run the 'deploy.sh' file without any further modifications.

### Visualize the Web Browser

While the tests are running, go the following page: http://localhost:4444/ui#/sessions

Then click on the camera icon for any currently running browser and enter the following password: **secret**

Et voilà, you've accessed the VNC viewer!

### Analyzing the results

Since the tests can be run in multiple ways, here's how you're able to retrieve the test reports containing all the tests cases that were executed, with their steps properly described.

1. Tests ran via Docker containers:
   1. Go to the volume related to the test container
   2. Open the 'build' folder and look for the 'testngOutput'
   3. Open the 'index.html' file to check the test results
   4. For the snapshots and text files, go to the browser_storage/${browser_name}/ folders
   
2. Tests ran via IDE using the gradle plugin:
   1. Simply run the project with 'gradle clean build'
   2. Reports can also be found on the 'build' folder but this time, on the system's own directory
   3. For the snapshots and text files, go to the browser_storage/${browser_name}/ folders that should be under the user's main folder (e.g.:
      C:\Users\some.user)
