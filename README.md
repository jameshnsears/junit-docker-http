# junit-docker-http [![Build Status](https://travis-ci.org/jameshnsears/junit-docker-http.svg?branch=master)](https://travis-ci.org/jameshnsears/junit-docker-http) [![Coverage Status](https://coveralls.io/repos/github/jameshnsears/junit-docker-http/badge.svg?branch=master)](https://coveralls.io/github/jameshnsears/junit-docker-http?branch=master) [![sonarcloud.io](https://sonarcloud.io/api/project_badges/measure?project=jameshnsears_junit-docker-http&metric=alert_status)](https://sonarcloud.io/dashboard?id=jameshnsears_junit-docker-http) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/05fe7d847b4c40afab79dd6b3b6404cb)](https://www.codacy.com/app/jameshnsears/junit-docker-http?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jameshnsears/junit-docker-http&amp;utm_campaign=Badge_Grade)

* [JUnit 5](https://junit.org/junit5/docs/current/api/org/junit/jupiter/api/extension/ParameterResolver.html) Parameter Resolver & API that minimally leverages the [Docker Engine API](https://docs.docker.com/engine/api/v1.39/).

## 1. Introduction
* A JUnit 5 Parameter Resolver & API that lets you easily:
    * pull images - if not already pulled.
    * start containers - with various, optional, parameters - i.e. ports; networks; volumes; commands.
    * stop containers.
