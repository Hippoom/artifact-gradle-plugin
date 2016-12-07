package io.github.hippoom.artifact

import spock.lang.Specification

class GitRevisionFetchSpec extends Specification {

    def "it should get current git short revision"() {
        given:
        def subject = new GitRevisionFetcher()

        when:
        def actual = subject.fetch()

        then:
        println(actual)
        assert actual != null
    }
}
