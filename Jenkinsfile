node {
        stage('Clone repository') {
        /* Let's make sure we have the repository cloned to our workspace */
        checkout scm
     }
        stage('Build Project') {
                sh 'mvn clean package'
        }
       
       stage('SonarQube analysis') {
       def mvnHome = tool name: 'localmaven' , type: 'maven'
       withSonarQubeEnv('sonarqube') {
       sh "${mvnHome}/bin/mvn sonar:sonar"
       
    }
  }

       stage('Build image') {
        /* This builds the actual image; synonymous to
         * docker build on the command line */
       
       sh 'docker build -t trydocker29/springboot-docker .'
      }
        
        stage('Publish Jar to Nexus') {
                
                script {
                    // Read POM xml file using 'readMavenPom' step , this step 'readMavenPom' is included in: https://plugins.jenkins.io/pipeline-utility-steps
                    pom = readMavenPom file: "pom.xml";
                    // Find built artifact under target folder
                    filesByGlob = findFiles(glob: "springbootdocker/target/*.jar");
                    // Print some info from the artifact found
                    echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                    // Extract the path from the File found
                    artifactPath = filesByGlob[0].path;
                    // Assign to a boolean response verifying If the artifact name exists
                    artifactExists = fileExists artifactPath;
                    if(artifactExists) {
                        echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                        nexusArtifactUploader(
                            nexusVersion: 'nexus3',
                            protocol: 'http',
                            nexusUrl: '35.238.136.209:8081',
                            groupId: pom.groupId,
                            version: pom.version ,
                            repository: 'spring',
                                credentialsId: 'nexus-login',
                            artifacts: [
                                // Artifact generated such as .jar, .ear and .war files.
                                [artifactId: pom.artifactId,
                                classifier: '',
                                 file: "${artifactPath}",
                                type: 'jar'],
                                // Lets upload the pom.xml file for additional information for Transitive dependencies
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: "pom.xml",
                                type: "pom"]
                            ]
                        );
                    } else {
                        error "*** File: ${artifactPath}, could not be found";
                    }
                }
            
        }
        
        stage('Publish image to Nexus'){
                echo "Publishing image"
                withCredentials([string(credentialsId: 'dockerhubpass', variable: 'dockerHubPwd')]) {
                 sh "docker login -u trydocker29 -p ${dockerHubPwd}" 
              }
                 sh 'docker push trydocker29/springboot-docker'
                    
        }
        
}
