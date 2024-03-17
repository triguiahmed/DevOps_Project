pipeline {
    agent any
    stages {
        stage('Retrieve source code') {
            steps {
                git 'https://github.com/triguiahmed/DevOps_Project.git'
            }
        }
        stage('Display system date') {
            steps {
                script {
                    def now = new Date()
                    echo "The system date is ${now}"
                }
            }
        }
    }
    options {
        disableConcurrentBuilds()
    }
}
