import hudson.security.*
import jenkins.model.*

def instance = Jenkins.getInstance()

// enable signup
def hudsonRealm = new HudsonPrivateSecurityRealm(false)

instance.setSecurityRealm(hudsonRealm)
instance.save()
