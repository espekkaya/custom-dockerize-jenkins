ARG JENKINS_VERSION=2.280
FROM jenkins/jenkins:$JENKINS_VERSION
LABEL maintainer="S. Enes PEKKAYA <espekkaya@gmail.com>"

USER root

# Set Timezone of Jenkins
ENV TZ=Europe/Istanbul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Install the latest Docker CE binaries
RUN apt-get update && \
    apt-get -y install apt-transport-https ca-certificates curl gnupg2 software-properties-common && \
    curl -fsSL https://download.docker.com/linux/$(. /etc/os-release; echo "$ID")/gpg > /tmp/dkey; apt-key add /tmp/dkey && \
    add-apt-repository \
      "deb [arch=amd64] https://download.docker.com/linux/$(. /etc/os-release; echo "$ID") \
      $(lsb_release -cs) \
      stable" && \
   apt-get update && \
   apt-get -y install docker-ce=18.06.0~ce~3-0~debian

# Install the latest Docker Compose
RUN curl -L "https://github.com/docker/compose/releases/download/1.23.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
RUN chmod +x /usr/local/bin/docker-compose
   
# Added Plugins / https://github.com/jenkinsci/docker
COPY configs/plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt
# to indicate that this Jenkins installation is fully configured. Otherwise a banner will appear prompting the user to install additional plugins, which may be inappropriate.
RUN echo 2.0 > /usr/share/jenkins/ref/jenkins.install.UpgradeWizard.state

# Copy files needed in the container
COPY configs/init.groovy.d/* /usr/share/jenkins/ref/init.groovy.d/

# Directory for Pipeline Library
ARG LOCAL_PIPELINE_LIBRARY_PATH=/var/jenkins_home/jobs-external
ENV LOCAL_PIPELINE_LIBRARY_PATH=${LOCAL_PIPELINE_LIBRARY_PATH}

VOLUME /var/jenkins_home/jobs-external

COPY jobs /var/jenkins_home/jobs-library

# Change Jenkins Styles
COPY style/jenkins-material-theme.css /usr/share/jenkins/ref/userContent/style/jenkins-material-theme.css

# Clean up
RUN apt-get autoremove -y \
  && apt-get clean \
  && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*