package scripts
// Initializes the Development folder, which is fully configurable by the user

import groovy.io.FileType
import jenkins.model.Jenkins
import com.cloudbees.hudson.plugins.folder.Folder

String folderName = "development"

println("=== Initialize the ${folderName} folder")
if (Jenkins.instance.getItem(folderName) != null) {
    println("${folderName} folder has been already initialized, skipping the step")
    return
}

// Development folder
def folder = Jenkins.instance.createProject(Folder.class, folderName.toUpperCase())
folder.description = "${folderName.toUpperCase()} Jobs"
folder.save()

// Loads development from sources
def sourceDir = new File(Jenkins.instance.rootDir, "jobs-library/${folderName}/")

if (sourceDir.exists()) {
    println("===== Loading ${folderName}")
    sourceDir.eachFile(FileType.FILES) { file ->
        if (file.name.endsWith('.pipeline.groovy')) {
                // def bindings = new Binding()
                // bindings.setVariable("folder", folder)
                // def shell = new GroovyShell(bindings)
                // shell.parse(new File(Jenkins.instance.rootDir, "init.groovy.d/${folderName}/${file}"))
                // shell.run()
            evaluate(new File("${file}"))
        }
    }

    // Also load project from the Pipeline library
    File pipelineLibSource = new File(Jenkins.instance.rootDir, "jobs_external/${folderName}/")
    def libs = new File(pipelineLibSource, folderName)
    if (libs.exists()) {
        libs.eachFile(FileType.FILES) { file ->
            if (file.name.endsWith('.groovy')) {
                evaluate(new File("${file}"))
            }
        }
    }
}