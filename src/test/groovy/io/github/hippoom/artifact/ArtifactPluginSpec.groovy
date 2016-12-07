package io.github.hippoom.artifact

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class ArtifactPluginSpec extends Specification {

    def "it_should_add_write_artifact_build_number_task"() {

        given:
        Project project = ProjectBuilder.builder().build()

        when:
        project.pluginManager.apply 'io.github.hippoom.artifact'

        then:
        assert project.tasks.writeArtifactBuildNumber != null
    }
}