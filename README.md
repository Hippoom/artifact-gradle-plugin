# artifact-gradle-plugin[![Build Status](https://travis-ci.org/Hippoom/artifact-gradle-plugin.svg?branch=master)](https://travis-ci.org/Hippoom/artifact-gradle-plugin)

An simple plugin to simplify artifact sharing through [deployment pipeline](https://martinfowler.com/bliki/DeploymentPipeline.html).


## Latest Release
* __0.1.0__

You can follow this [tutorial](https://plugins.gradle.org/plugin/io.github.hippoom.artifact/0.1.0) to apply this plugin.

## Why this plugin

When you're taking software from initial code change to production in an automated fashion, it's important that you deploy the exact same thing you've tested.

Once you've built a binary, you should upload that back to your CD server or artifact repository for later use.

When you're ready to deploy to a downstream system, you should fetch and install that build artifact. This way you make sure that you're running your functional tests on the exact thing you built and that you're going to deploy.

From implementation perspective, the binary needs to be identified so that you can decide which binary to fetch. The semantic version is a good start, but it usually does not provide uniqueness. Let's say, the current version is `1.2.4`,  it does not provide sufficient infomation to identify the binary because several commits are made for it and therefore several binary were built by build pipeline run triggered. You don't want to bump the version every time you push your changes.

| semantic version | git revision | pipeline build number |
| ---------------- | ------------ | --------------------- |
| 1.2.4            | 238sd09      | 13-3                  |
| 1.2.4            | i989ad32     | 13-2                  |
| 1.2.4            | 8sd09323     | 13-1                  |

> semantic version represents a release plan, tons of binaries could be built to deliver this plan

My personal faviroute is to combine semantic version, pipeline build number and git revision, the final identifier (will be written in a file) looks like this: `1.2.4-13.3-238sd09`. It tells me what is the git revision the CD server use to build the binary and which build pipeline run does it.

The implementation is quite simple, I used write a simple script to fetch current git revision with `git commands` and get pipeline build number by environment variables (provided by CD server). But I'm frustrated with copying and pasting the scripts throught projects, so an idea comes up to me, why not ask the build tool do it for me? 


## Quick Start

A task named 'writeArtifactBuildNumber' will be added to the project once the plugin is applied.

The task does not depends on any other task for the time being so you can use the following command in you binary build task in CD server:

```
gradle clean build writeArtifactBuildNumber
```

You'll find a file named `build-number`under `build/artifacts/`directory. The content is in plain text follows this format:

```
<semantic version|$project.version>-<pipeline build number| $PIPELINE_BUILD_NUMBER>-<scm revision | git short revision>
```

Variants:

* semantic version will be skipped if `project.version` is not specified
* pipeline build number will be skipped if `$PIPELINE_BUILD_NUMBER` is not defined as environment variable

## Road Map

1. support default task dependency so that no explicit `writeArtifactBuildNumber`is needed
2. support customizing format for binary identifier
3. support `svn` revision

## Contributing

Any suggestion and pull request is welcome.

## License

Licensed under MIT License (the "License"); You may obtain a copy of the License in the LICENSE file, or at [here](LICENSE).