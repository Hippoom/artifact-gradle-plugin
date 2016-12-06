package io.github.hippoom.artifact

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

class ArtifactPluginTest {

    @Test
    void it_should_add_write_artifact_build_number_task() {

        Project project = ProjectBuilder.builder().build()

        project.pluginManager.apply 'io.github.hippoom.artifact'

        assert project.tasks.hello != null
    }
}