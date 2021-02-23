package scripts

import groovy.io.FileType
import jenkins.model.Jenkins
import com.cloudbees.hudson.plugins.folder.Folder

def sourceDir = new File(Jenkins.instance.rootDir, "jobs-library/")
if (sourceDir.exists()) {
    println("===== Loading main jobs")
    sourceDir.eachFile(FileType.FILES) { file ->
        if (file.name.endsWith('pipeline.groovy')) {
            evaluate(new File("${file}"))
        }
    }

    // Also load project from the Pipeline library
    File pipelineLibSource = new File(Jenkins.instance.rootDir, "jobs_external/")
    if (pipelineLibSource.exists()) {
        pipelineLibSource.eachFile(FileType.FILES) { file ->
            if (file.name.endsWith('.groovy')) {
                evaluate(new File("${file}"))
            }
        }
    }
}