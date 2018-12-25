# junit-docker-http [![Build Status](https://travis-ci.org/jameshnsears/junit-docker-http.svg?branch=master)](https://travis-ci.org/jameshnsears/junit-docker-http) [![Coverage Status](https://coveralls.io/repos/github/jameshnsears/junit-docker-http/badge.svg?branch=master)](https://coveralls.io/github/jameshnsears/junit-docker-http?branch=master) [![sonarcloud.io](https://sonarcloud.io/api/project_badges/measure?project=jameshnsears_junit-docker-http&metric=alert_status)](https://sonarcloud.io/dashboard?id=jameshnsears_junit-docker-http) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/05fe7d847b4c40afab79dd6b3b6404cb)](https://www.codacy.com/app/jameshnsears/junit-docker-http?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jameshnsears/junit-docker-http&amp;utm_campaign=Badge_Grade)

* [JUnit 5](https://junit.org/junit5/docs/current/api/org/junit/jupiter/api/extension/ParameterResolver.html) Parameter Resolver & API that minimally leverages the [Docker Engine API](https://docs.docker.com/engine/api/v1.39/).

## 1. Introduction
* A JUnit 5 Parameter Resolver & API that lets you easily:
    * pull images - if not already pulled.
    * start containers - with various, optional, parameters - i.e. ports; networks; volumes; commands.
    * stop containers.

### 1.1. Example Usage
* see: [DockerConfigurationXqaTest.java](https://github.com/jameshnsears/junit-docker-http/blob/master/src/test/java/com/github/jameshnsears/DockerConfigurationXqaTest.java) and [config-xqa.json](https://github.com/jameshnsears/junit-docker-http/blob/master/src/test/resources/fixtures/config-xqa.json)

## 2. Publishing to [Maven Central](https://search.maven.org/)

### 2.1. settings.xml
* decrypt settings.xml.pgp
* cp settings.xml ~/.m2

###  2.2. Deploy
export JAVA_HOME="/usr/lib/jvm/java-11-openjdk-amd64"

#### 2.2.1. To staging repo
* mvn -DperformRelease=true clean deploy
    * enter private pgp key password
* visit:
    * [https://oss.sonatype.org/#nexus-search;quick~junit-docker-http](https://oss.sonatype.org/#nexus-search;quick~junit-docker-http)

#### 2.2.2 To release repo
* (optionally) [https://www.youtube.com/watch?v=dXR4pJ_zS-0&feature=youtu.be](https://www.youtube.com/watch?v=dXR4pJ_zS-0&feature=youtu.be)
* remove "-SNAPSHOT" in pom.xml + increment version #.
* mvn -DperformRelease=true clean deploy
    * enter private pgp key password
