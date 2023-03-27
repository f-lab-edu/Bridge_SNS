pipeline {
    agent any

    tools {
        jdk 'Java17'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out from Git repository...'
                cleanWs()
                checkout([$class: 'GitSCM', branches: [[name: '*/feature/Adding_Jenkins']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'CleanCheckout']], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'hobulian_git', url: 'https://github.com/f-lab-edu/Bridge_SNS.git']]])
            }
        }

        stage('Set Gradlew Permissions') {
            steps {
                echo 'Setting permissions for Gradle Wrapper...'
                sh 'chmod +x gradlew'
            }
        }

        stage('Build') {
            steps {
                echo 'Building the project...'
                sh './gradlew clean build --stacktrace'
            }
        }

        stage('Static Analysis') {
            steps {
                echo 'Running static analysis and code quality checks...'
                sh './gradlew check'
            }
        }

        stage('Unit Test') {
            steps {
                echo 'Running unit tests...'
                sh './gradlew test --stacktrace'
            }
        }

/*         stage('Integration Test') {
            steps {
                echo 'Running integration tests...'
                sh './gradlew integrationTest'
            }
        } */

        stage('Archive Artifacts') {
            steps {
                echo 'Archiving artifacts...'
                archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
            }
        }

/*         stage('Deploy') {
            steps {
                echo 'Deploying the application...'
                // blah..blah..
            }
        }

        post {
            success {
                echo 'Build and tests succeeded.'
                // blah..blah..
            }
            failure {
                echo 'Build or tests failed.'
                // blah..blah..
            }
        } */
    }
}
