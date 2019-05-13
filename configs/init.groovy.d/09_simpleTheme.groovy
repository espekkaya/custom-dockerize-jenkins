import jenkins.model.Jenkins;
import org.jenkinsci.plugins.simpletheme.CssUrlThemeElement;

Jenkins jenkins = Jenkins.get()

def themeDecorator = jenkins.getExtensionList(org.codefirst.SimpleThemeDecorator.class).first()

themeDecorator.setElements([
  // new CssUrlThemeElement('http://afonsof.com/jenkins-material-theme/dist/material-teal.css')
  new CssUrlThemeElement('http://172.17.26.238:8080/userContent/style/jenkins-material-theme.css')
])

jenkins.save()