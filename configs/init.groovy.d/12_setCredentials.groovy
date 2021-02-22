import jenkins.model.*
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.common.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.plugins.credentials.impl.*
import com.cloudbees.jenkins.plugins.sshcredentials.impl.*
import hudson.plugins.sshslaves.*;

domain = Domain.global()
store = Jenkins.instance.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()

priveteKey = new BasicSSHUserPrivateKey(
CredentialsScope.GLOBAL,
"jenkins-slave-key",
"root",
new BasicSSHUserPrivateKey.UsersPrivateKeySource(),
"",
""
)

usernameAndPassword = new UsernamePasswordCredentialsImpl(
  CredentialsScope.GLOBAL,
  "git", "Git with Password Configuration",
  "username",
  "password"
)

// store.addCredentials(domain, priveteKey)
store.addCredentials(domain, usernameAndPassword)
