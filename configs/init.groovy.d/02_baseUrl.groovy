// Set Jenkins' base URL

import java.util.logging.Logger
import jenkins.model.*
import hudson.security.*

def logger = Logger.getLogger("")
def env = System.getenv()

def baseUrl = env.JENKINS_BASE_URL

if (baseUrl) {
  def instance = Jenkins.getInstance()
  
  jenkinsLocationConfiguration = JenkinsLocationConfiguration.get()
  jenkinsLocationConfiguration.setUrl(baseUrl)
  instance.save()
  logger.info("Jenkins' base URL set to " + baseUrl)
} else {
  logger.warn("Jenkins' base URL not set")
}