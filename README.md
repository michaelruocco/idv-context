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

*   Move integration tests for resetting lockout state to work with posting results tests
    so that we can remove the endpoint / facade functionality for posting attempts to lockout state

*   Remove duplication between RecordAttemptRequest, DefaultRecordAttempt request, this may be covered by
    removing posting attempts to lockout state

*   Tidy up cloud formation templates and try out creating API gateway

*   Json error handling for context errors (context policy not found, context not found)

*   Add registering context expiry job -> via publishing context created event

*   Add publishing context expired event

*   Ensure attempts merged when identities merged -> via publishing identity merged event

*   Make stub delays and identity lookup timeout configurable (by channel / add identity eligibility policy?)

*   Extract common dynamo adapter to its own separate library

*   Try out junit tagging for slow tests

*   Add specific method result types, e.g. for OTP delivery time, actual and used otp codes, verify type, 
    maybe delivery id for sending message

## Useful commands

### Gradle

```gradle
// runs tests and builds code
./gradlew clean build
```

```gradle
// runs tests (including integration tests which will be slower) and builds code
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

### AWS

The below commands can be used to use cloudformation to deploy the service on AWS.
A great description of how these templates work is [here](https://reflectoring.io/aws-cloudformation-deploy-docker-image/).

#### Creating AWS resources

```aws
//generate network resources using cloud formation
aws cloudformation create-stack --stack-name idv-dev-network --template-body file://cloud-formation/network.yml --capabilities CAPABILITY_IAM
```

```aws
//generate repository resources using cloud formation (relies on network stack already being created)
aws cloudformation create-stack --stack-name idv-dev-repositories --template-body file://cloud-formation/repositories.yml
```

```aws
//generate service resources using cloud formation (relies on network stack already being created
aws cloudformation create-stack --stack-name idv-dev-service --template-body file://cloud-formation/service.yml
```

#### Deleting AWS resources

```aws
aws cloudformation delete-stack --stack-name idv-dev-service;
aws cloudformation delete-stack --stack-name idv-dev-repositories;
aws cloudformation delete-stack --stack-name idv-dev-network;
```