pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out from Git repository...'
                git credentialsId: 'hobulian', url: 'https://github.com/f-lab-edu/Bridge_SNS.git'
            }
        }

        stage('Unit Test') {
            steps {
                echo 'Running unit tests...'
                sh './gradlew test'
            }
        }

        stage('Build') {
            steps {
                echo 'Building the project...'
                sh './gradlew build'
            }
        }

        stage('Archive Artifacts') {
            steps {
                echo 'Archiving artifacts...'
                archiveArtifacts artifacts: 'target/*.war', fingerprint: true
            }
        }
    }
}
