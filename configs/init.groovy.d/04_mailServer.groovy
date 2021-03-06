import java.util.logging.Logger
import jenkins.model.*
import hudson.security.*
import hudson.tasks.Mailer

def logger = Logger.getLogger("")
def env = System.getenv()
def instance = Jenkins.getInstance()
def pluginManager = instance.getPluginManager()

if (pluginManager.getPlugin('mailer')) {
  def smtpHost = env.SMTP_HOST
  def smtpPort = env.SMTP_PORT
  if (smtpHost) {
    def mailer = instance.getDescriptor("hudson.tasks.Mailer")

    //mailer.setSmtpAuth(user, pass)
    //mailer.setReplyToAddress(...)
    mailer.setSmtpHost(smtpHost)
    //mailer.setUseSsl(...)
    if (smtpPort) {
      mailer.setSmtpPort(smtpPort)
    }
    if (env.SMTP_CHARSET) {
      mailer.setCharset(env.SMTP_CHARSET)
    }

    mailer.save()
    instance.save()
  }
} else {
  logger.info("The 'mailer' plugin is not installed; skipping mail server configuration")
}

if (pluginManager.getPlugin('email-ext')) {
    def emailExt = instance.getDescriptor("hudson.plugins.emailext.ExtendedEmailPublisher")
    // This method is not documented in Javadoc, but can be found here:
    // https://github.com/jenkinsci/email-ext-plugin/blob/master/src/main/java/hudson/plugins/emailext/ExtendedEmailPublisherDescriptor.java#L546
    emailExt.setAllowUnregisteredEnabled(true)

    emailExt.save()
    instance.save()
} else {
  logger.info("The 'email-ext' plugin is not installed; skipping mail server configuration")
}