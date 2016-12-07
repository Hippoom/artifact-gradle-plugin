package io.github.hippoom.artifact

class GitRevisionFetcher {

    String fetch() {
        def proc = "git rev-parse --short HEAD".execute()
        def outputStream = new StringBuffer()
        proc.waitForProcessOutput(outputStream, System.err)
        return outputStream.toString()
    }
}
