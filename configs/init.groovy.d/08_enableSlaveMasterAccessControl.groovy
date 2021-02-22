import jenkins.security.s2m.AdminWhitelistRule
import jenkins.model.Jenkins

Jenkins.instance.injector.getInstance(AdminWhitelistRule.class).setMasterKillSwitch(false)

// This configuration survives a restart. If you want to re-enable the CLI, you can use the following groovy script:
// -Djenkins.CLI.disabled=true
// Jenkins.CLI.get().setEnabled(false)

Jenkins.instance.save()