pipeline {
    agent any

    tools {
        maven 'Maven_3.9.11'   // Must match the name configured in Jenkins
        jdk 'JDK17'            // Must match your JDK name in Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                // Pull code from your GitHub repo
                git branch: 'main', url: 'https://github.com/parthpatel10029/APITestingProject.git'
            }
        }

        stage('Build & Test') {
            steps {
                // Clean and run all Maven tests
                bat 'mvn clean test'
            }
        }

        stage('Archive Reports') {
            steps {
                // Save surefire test reports in Jenkins build logs
                junit '**/target/surefire-reports/*.xml'
            }
        }
    }

    post {
        always {
            echo 'Cleaning workspace after build...'
            deleteDir()
        }
        success {
            echo '✅ Build and API tests succeeded!'
        }
        failure {
            echo '❌ Build or tests failed!'
        }
    }
}
