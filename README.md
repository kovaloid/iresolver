[![License: MIT](https://img.shields.io/badge/license-mit-ff69b4.svg)](https://opensource.org/licenses/MIT)
[![Build Status](https://travis-ci.com/kovaloid/jresolver.svg?branch=master)](https://travis-ci.com/kovaloid/jresolver)

# Jira Resolver

The goal of this project is to make life easier for Jira users.

## Getting Started

In order to build the distribution, enter in the console:
```sh
gradlew
```

After the distribution is built, it is located in the following path:
```
build/distributions
```

The last release version of distribution can be downloaded from [the release tab of the repository](https://github.com/kovaloid/jresolver/releases).

Unzip the distribution, go to the bin folder and launch one of the scripts below.

Create data set:
```sh
create-data-set[.bat]
```
Create doc2vec model:
```sh
create-vector-model[.bat]
```
Run the tool:
```sh
run[.bat]
```
Clean work folder with data set and model:
```sh
clean[.bat]
```
