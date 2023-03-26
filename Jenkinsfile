pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out from Git repository...'
                git credentialsId: 'hobulian_git', url: 'https://github.com/f-lab-edu/Bridge_SNS.git'
            }
        }

        stage('Set Gradlew Permissions') {
            steps {
                echo 'Setting permissions for Gradle Wrapper...'
                sh 'chmod +x gradlew'
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
