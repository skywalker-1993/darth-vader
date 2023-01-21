FROM gradle:jdk11-alpine
ARG TEST_ENV=qa
ENV TEST_ENV=${TEST_ENV}
WORKDIR /home/test_repo/
COPY . /home/test_repo/
CMD gradle clean build -Ptest_env=${TEST_ENV}