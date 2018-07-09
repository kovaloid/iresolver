[![License: MIT](https://img.shields.io/badge/license-mit-ff69b4.svg)](https://opensource.org/licenses/MIT)
[![Build Status](https://travis-ci.org/kovaloid/jresolver.svg?branch=master)](https://travis-ci.org/kovaloid/jresolver)

# Jira Resolver

The goal of this project is to make life easier for Jira users.

## Getting Started

In order to compile the distribution, enter in the console:
```
gradlew
```

Unzip the distribution, launch cmd and go to bin directory.

Create dataset:
```
jresolver prepare
```
Create doc2vec model:
```
jresolver configure
```
Run tool:
```
jresolver run
```
All three stages with password:
```
jresolver prepare configure run password
```