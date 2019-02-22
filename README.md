[![License: MIT](https://img.shields.io/badge/license-mit-ff69b4.svg)](https://opensource.org/licenses/MIT)
[![Build Status](https://travis-ci.org/kovaloid/jresolver.svg?branch=master)](https://travis-ci.org/kovaloid/jresolver)

# Jira Resolver

The goal of this project is to make life easier for Jira users.

## Getting Started

In order to build the distribution, enter in the console:
```sh
gradlew
```

After the distribution is built, it is located in the following path:
```
build/distributuions
```

Unzip the distribution, go to the bin folder and launch one of the scripts below.

Create dataset:
```sh
create-data-set[.bat]
```
Create doc2vec model:
```sh
create-vector-model[.bat]
```
Clean work folder with dataset and model:
```sh
clean[.bat]
```
Run the tool:
```sh
run[.bat]
```
