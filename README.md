[![License: MIT](https://img.shields.io/badge/license-mit-ff69b4.svg)](https://opensource.org/licenses/MIT)
[![Build Status](https://travis-ci.org/kovaloid/iresolver.svg?branch=master)](https://travis-ci.org/kovaloid/iresolver)

# Issue Resolver

The purpose of this tool is to reduce the complexity of resolving customer requests in issue tracking systems such as Jira.

## Getting Started

In order to build the distribution, enter in the console:
```sh
gradlew
```

After the distribution is built, it is located in the following path:
```
build/distributions
```

The last release version of distribution can be downloaded from [the release tab of the repository](https://github.com/kovaloid/iresolver/releases).

Unzip the distribution, go to the bin folder and launch one of the scripts below.

| Script | Description |
| ------------- | ------------- |
| create-issues-data-set[.bat]  | Create data set with resolved issues content  |
| create-issues-vector-model[.bat]  | Create doc2vec model based on issues data set  |
| create-documentation-data-set[.bat]  | Create data set with documentation files content  |
| create-documentation-vector-model[.bat]  | Create doc2vec model based on documentation data set  |
| create-confluence-data-set[.bat]  | Create data set with confluence pages content  |
| create-confluence-vector-model[.bat]  | Create doc2vec model based on confluence data set  |
| print-issue-fields[.bat]  | Print all possible issue fields with ids in bug tracking system  |
| run[.bat]  | Run the tool  |
| run-ui[.bat]  | Open UI launch panel  |

The settings of the entire application is in the configuration.yml file, which is located in:
* _config/_ folder in distribution
* _common-api/src/main/resources/_ folder in sources

## How It Works

The tool consists of modules. There are three types of modules:
* Connectors, which task is to receive data from data source (Jira, BugZilla, Confluence, etc.)
* Processors, which task is to handle each unresolved issue and generate issue analyzing result
* Reporters, which task is to get processors results and create reports for user in specific format (html, txt)

## Standalone Docker Image
1. Build app with Gradle
2. Run:
```
docker build . -t iresolver
```
3. Use:
```
docker run -v {$custom_dir}:/usr/src/iresolver/iresolver/data iresolver <command>
```

## Planned To Do

* Add connectors for YouTrack, Wrike, Slack, GitHub, etc.
* Add converters for audio, images, webex videos and MS Office documents to simple text
* Add reporter to email
* Add tracking for data set retrieving process so that it can be downloaded in parts
