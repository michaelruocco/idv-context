# Idv Context

[![Build](https://github.com/michaelruocco/idv-context/workflows/pipeline/badge.svg)](https://github.com/michaelruocco/idv-context/actions)
[![codecov](https://codecov.io/gh/michaelruocco/idv-context/branch/master/graph/badge.svg)](https://codecov.io/gh/michaelruocco/idv-context)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/40d05ca3aa5347859953cd583225eee7)](https://www.codacy.com/gh/michaelruocco/idv-context/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=michaelruocco/idv-context&amp;utm_campaign=Badge_Grade)
[![BCH compliance](https://bettercodehub.com/edge/badge/michaelruocco/idv-context?branch=master)](https://bettercodehub.com/)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_idv-context&metric=alert_status)](https://sonarcloud.io/dashboard?id=michaelruocco_idv-context)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_idv-context&metric=sqale_index)](https://sonarcloud.io/dashboard?id=michaelruocco_idv-context)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_idv-context&metric=coverage)](https://sonarcloud.io/dashboard?id=michaelruocco_idv-context)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_idv-context&metric=ncloc)](https://sonarcloud.io/dashboard?id=michaelruocco_idv-context)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.michaelruocco.idv/idv-context-spring-app.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.michaelruocco.idv%22%20AND%20a:%22idv-context-spring-app%22)

## Todo

*   Token validation
*   Performance tests in pipeline / nightly build?
*   Add identity data lookup policy?

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
// then runs application docker image with dependencies running inside docker
// service will be running on port 8081 by default
./gradlew clean spotlessApply build integrationTest buildImage composeUp
```

```gradle
// runs spring application on your local machine on port 8081 by default
// using in memory implementations of dependencies
./gradlew bootRun
```

```gradle
// check that dependencies are up to date
./gradlew dependencyUpdates
```

```gradle
// generate dependency graph images at build/reports/dependency-graph
./gradlew generateDependencyGraph
./gradlew generateDependencyGraphIdvContext
```

### JMeter

#### Running performance tests

This command should be run from the parent project directory and requires the service to
be up and running, which can be done using either the bootRun or composeUp gradle tasks
described above.

```sh
mkdir -p app/spring/build/reports/jmeter/html;
rm -rf app/spring/build/reports/jmeter/log/*;
jmeter --nongui \
       --forceDeleteResultFile \
       --addprop app/spring/src/performanceTest/jmeter/online-purchase.properties \
       --testfile app/spring/src/performanceTest/jmeter/online-purchase.jmx \
       --logfile app/spring/build/reports/jmeter/log/online-purchase.jtl \
       --reportatendofloadtests \
       --reportoutputfolder app/spring/build/reports/jmeter/html
```