[![License: MIT](https://img.shields.io/badge/license-mit-ff69b4.svg)](https://opensource.org/licenses/MIT)
[![Build Status](https://travis-ci.org/kovaloid/jresolver.svg?branch=master)](https://travis-ci.org/kovaloid/jresolver)

# Jira Resolver

The goal of this project is to make life easier for Jira users.

## Getting Started

In order to compile the distribution, enter in the console:
```sh
gradlew
```

After build will be created the archive in next path
```
build/distributuions
```

Unzip the distribution, launch cmd and go to bin directory.

Create dataset:
```sh
jresolver prepare
```
Create doc2vec model:
```sh
jresolver configure
```
Run tool:
```sh
jresolver
```
