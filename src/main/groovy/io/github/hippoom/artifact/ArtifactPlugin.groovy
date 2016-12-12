package io.github.hippoom.artifact

import org.gradle.api.Plugin
import org.gradle.api.Project

class ArtifactPlugin implements Plugin<Project> {

    void apply(Project project) {
        def pluginExtension = new ArtifactPluginExtension(project)
        project.task('writeArtifactBuildNumber') {
            doLast {
                write(project, pluginExtension.artifactBuildNumber())
            }
        }
        project.extensions.add("artifactBuildNumber", { -> pluginExtension.artifactBuildNumber()})
    }

    private void write(Project project, String artifactBuildNumber) {
        def artifactsDir = project.file("${project.buildDir}/artifacts")
        if (artifactsDir.exists()) {
            //skip
        } else {
            artifactsDir.mkdirs()
        }
        def artifactBuildNumberFile = project.file("${project.buildDir}/artifacts/build-number")
        artifactBuildNumberFile.text = artifactBuildNumber
    }
}