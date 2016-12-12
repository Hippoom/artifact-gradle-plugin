package io.github.hippoom.artifact

import org.gradle.api.Project

class ArtifactPluginExtension {
    private Project project

    ArtifactPluginExtension(Project project) {
        this.project = project
    }

    String artifactBuildNumber() {
        return artifactBuildNumberOf(project)
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

        //see http://stackoverflow.com/questions/6116899/what-escape-character-is-u000a
        result.toString().replaceAll("\\p{Cntrl}", "")

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
}
