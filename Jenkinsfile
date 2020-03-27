pipeline {
    agent  any
    environment {
        dockerHome = tool 'docker'
        //env.PATH="${dockerHome}/bin:${env.PATH}"
    }
    stages {
        stage ('Compile') {
            steps {
                withMaven(maven: 'maven_3_6_3') {
                    sh 'mvn clean package'
                }
            }
        }
        stage ('Build') {
            steps {
                sh 'docker build -t atulkumar1981/cakemanager .'
            }
        }
        stage ('Push') {
            steps {
                sh 'docker push atulkumar1981/cakemanager'
            }
        }
        stage ('Deploy') {
            steps {
                timeout(time: 500, unit: 'SECONDS') {
                    pushToCloudFoundry(cloudSpace: 'development', credentialsId: 'PCF_LOGIN', organization: 'atul-kumar-org', target: 'api.run.pivotal.io')
                }
            }
        }
    }
}