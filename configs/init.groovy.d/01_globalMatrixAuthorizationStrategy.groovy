import java.util.logging.Logger
import jenkins.model.*
import hudson.security.*

def logger = Logger.getLogger("")
def instance = Jenkins.getInstance()

def strategy = instance.getAuthorizationStrategy()
if (!(strategy instanceof GlobalMatrixAuthorizationStrategy)) {
  logger.info("Changing the authorization strategy to GlobalMatrixAuthorizationStrategy and restarting to activate it")
  strategy = new GlobalMatrixAuthorizationStrategy()
  instance.setAuthorizationStrategy(strategy)
  instance.save()
  instance.doSafeRestart()
}