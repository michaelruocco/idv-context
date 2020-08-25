# Verification Context

[![Build Status](https://travis-ci.org/michaelruocco/verification-context.svg?branch=master)](https://travis-ci.org/michaelruocco/verification-context)
[![codecov](https://codecov.io/gh/michaelruocco/verification-context/branch/master/graph/badge.svg)](https://codecov.io/gh/michaelruocco/verification-context)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/50ea0e3060f540e7aa0ddebc10591862)](https://www.codacy.com/manual/michaelruocco/verification-context?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=michaelruocco/verification-context&amp;utm_campaign=Badge_Grade)
[![BCH compliance](https://bettercodehub.com/edge/badge/michaelruocco/verification-context?branch=master)](https://bettercodehub.com/)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_verification-context&metric=alert_status)](https://sonarcloud.io/dashboard?id=michaelruocco_verification-context)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_verification-context&metric=sqale_index)](https://sonarcloud.io/dashboard?id=michaelruocco_verification-context)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_verification-context&metric=coverage)](https://sonarcloud.io/dashboard?id=michaelruocco_verification-context)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_verification-context&metric=ncloc)](https://sonarcloud.io/dashboard?id=michaelruocco_verification-context)

## Todo

*   Add karate tests for lockout and attempt endpoints
*   Add json handling for lockout attempt and policy errors
*   Add initial functionality for loading identity and checking lockout state when creating verification context

## Useful commands

```gradle
// runs tests and builds code
./gradlew clean build
```

```gradle
// runs tests (including integration tests which in some cases can be slower) and builds code
./gradlew clean build integrationTest
```

```gradle
// runs all tests and builds code and applies rules to clean up code formatting etc
./gradlew clean spotlessApply build integrationTest
```

```gradle
// runs all tests as above but also builds application docker image and
// then runs application docker image with AWS local stack image to provide
// a local instance of dynamo db for the service to use for data persistence
// service will be running on port 8081 by default
./gradlew clean spotlessApply build integrationTest buildImage composeUp
```

```gradle
// runs spring application on your local machine on port 8081 by default
// using an in memory repository in place of dynamo db database
./gradlew bootRun
```

```gradle
// check that dependencies are up to date
./gradlew dependencyUpdates
```

```gradle
// generate dependency graph images at build/reports/dependency-graph
./gradlew generateDependencyGraph
./gradlew generateDependencyGraphVerificationContext
```