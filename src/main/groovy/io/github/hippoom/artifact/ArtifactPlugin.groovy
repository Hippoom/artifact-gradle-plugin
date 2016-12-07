package io.github.hippoom.artifact

import org.gradle.api.Plugin
import org.gradle.api.Project

class ArtifactPlugin implements Plugin<Project> {

    void apply(Project project) {
        project.task('writeArtifactBuildNumber') {
            doLast {
                write(project, artifactBuildNumberOf(project))
            }
        }
    }

    private String artifactBuildNumberOf(Project project) {
        def semanticVersionMaybe = semanticVersionOf(project)
        def pipelineBuildNumber = System.getenv("PIPELINE_BUILD_NUMBER")
        def scmRevision = gitShortRevision()

        StringJoiner result = new StringJoiner("-")
        if (semanticVersionMaybe != 'unspecified') {
            result.add(semanticVersionMaybe)
        }
        if (pipelineBuildNumber != null && pipelineBuildNumber != "") {
            result.add(pipelineBuildNumber)
        }
        if (scmRevision != null && scmRevision != "") {
            result.add(scmRevision)
        }
        result.toString()
    }

    private String gitShortRevision() {
        return new GitRevisionFetcher().fetch()
    }

    private String semanticVersionOf(Project project) {
        if (project.getRootProject() != null) {
            return project.getRootProject().version
        } else {
            return project.version
        }
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