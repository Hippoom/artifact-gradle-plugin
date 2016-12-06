package io.github.hippoom.artifact

import org.gradle.api.Plugin
import org.gradle.api.Project

class ArtifactPlugin implements Plugin<Project> {

    void apply(Project project) {
        project.task('writeArtifactBuildNumber') {
            doLast {
                def artifactsDir = project.file("${project.buildDir}/artifacts")
                if (artifactsDir.exists()) {
                    //skip
                } else {
                    artifactsDir.mkdirs()
                }
                def artifactBuildNumberFile = project.file("${project.buildDir}/artifacts/build-number")
                artifactBuildNumberFile.text = project.version
            }
        }
    }
}