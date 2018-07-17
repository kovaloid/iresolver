[![License: MIT](https://img.shields.io/badge/license-mit-ff69b4.svg)](https://opensource.org/licenses/MIT)
[![Build Status](https://travis-ci.org/kovaloid/jresolver.svg?branch=master)](https://travis-ci.org/kovaloid/jresolver)

# Rule Editor for Jira Resolver

...

## Getting Started

Before loading:
```
gradlew rule-editor:client:npmInstall
```
For standalone loading on localhost:8000
```
gradlew rule-editor:client:serveStandaloneClient
```
Or build in backend\src\main\resources\static for Loading from spring
```
gradlew rule-editor:client:buildClient