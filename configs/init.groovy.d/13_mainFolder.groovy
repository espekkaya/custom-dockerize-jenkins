package scripts

import groovy.io.FileType
import jenkins.model.Jenkins
import com.cloudbees.hudson.plugins.folder.Folder

def sourceDir = new File(Jenkins.instance.rootDir, "pipelines/")
if (sourceDir.exists()) {
    println("===== Loading main pipelines")
    sourceDir.eachFile(FileType.FILES) { file ->
        if (file.name.endsWith('.groovy')) {
            evaluate(new File("${file}"))
        }
    }

    // Also load project from the Pipeline library
    File pipelineLibSource = new File(Jenkins.instance.rootDir, "pipeline-library")
    if (pipelineLibSource.exists()) {
        pipelineLibSource.eachFile(FileType.FILES) { file ->
            if (file.name.endsWith('.groovy')) {
                evaluate(new File("${file}"))
            }
        }
    }
}