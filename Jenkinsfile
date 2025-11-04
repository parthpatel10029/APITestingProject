pipeline {
  agent any

  tools {
    jdk   'JDK17'
    maven 'Maven_3.9.11'
  }

  stages {

    stage('Checkout') {
      steps {
        echo 'Checking out source code from GitHub...'
        checkout scm
      }
    }

    stage('Build & Test API') {
      steps {
        echo 'Running API Tests via Maven...'
        bat 'mvn clean test'
      }
    }

    stage('Publish Reports') {
      steps {
        echo 'Publishing API Test Reports...'

        // Publish JUnit/TestNG results
        junit testResults: '**/target/surefire-reports/*.xml', allowEmptyResults: true

        // Publish Cucumber HTML if available
        script {
          def candidates = [
            'target/cucumber-reports/cucumber.html',
            'target/cucumber-reports/index.html',
            'target/cucumber/cucumber.html'
          ]
          def found = candidates.find { fileExists(it) }
          if (found) {
            echo "Publishing Cucumber report: ${found}"
            def dir  = found.substring(0, found.lastIndexOf('/'))
            def file = found.substring(found.lastIndexOf('/') + 1)
            publishHTML([
              reportDir: dir,
              reportFiles: file,
              reportName: 'Cucumber API Report',
              keepAll: true,
              alwaysLinkToLastBuild: true,
              allowMissing: false
            ])
          } else {
            echo "No Cucumber HTML report found in: ${candidates}"
          }
        }

        archiveArtifacts artifacts: 'target/**', fingerprint: true
      }
    }
  }

  post {
    always {
      echo 'Pipeline finished.'
    }
  }
}
