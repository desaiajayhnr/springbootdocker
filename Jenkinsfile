node {
        def app
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
       //sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar'
    }
  }

       stage('Build image') {
        /* This builds the actual image; synonymous to
         * docker build on the command line */
       
       sh 'docker build -t trydocker29/eureka-service:prod ./eureka-server'
      }
    
     /*   stage('--test--') {
            steps {
                sh "mvn test"
            }
        }
        stage('--package--') {
            steps {
                sh "mvn package"
            }
        }*/
}
