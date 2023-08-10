# Library-app

Library test application for interview

## Checkout
To clone repo with modules you need to run following command in root directory of project:

```shell
git clone --recurse-submodules https://github.com/MherSaribekyan/Library.git
```

# Application running

By application Running both Users and books are loading into DB,

You will see 1 SUPER-ADMIN and all other users loaded from CSV file have USER role,

SUPER_ADMIN haa permission to create user with ADMIN role or grant ADMIN role to any existed user.

only ADMIN users have access to purchase endpoint, which can be implemented in the future.

# LogIn

After succeed authentication you will be redirected to openapi Swagger UI,
All endpoints configurations are available there.


## Build

To build and run all tests just run following command in root directory of project:
```shell
./gradlew clean check
```

## Running

Run following command in root directory of project to start application:
```shell
./gradlew bootRun
```

## Requirements

- Java 17

## Docker integration

To build docker container you need to build it using gradle:

```shell
./gradlew clean build
```

Then you need to build container using optional external configuration file location:
```shell
docker build --build-arg CONFIG_FILE=application.properties -t lybrary/app .
```

Then you can run it using:
```shell
docker run -p 8082:8082 lybrary/app
```



