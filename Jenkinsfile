pipeline {
    agent any
    stages {
        stage('Fetch Source Code') {
			steps {
				script {
					try {
						// Clean any existing workspace to ensure a fresh start
						deleteDir()
						echo "Workspace cleaned successfully."
						
						// Clone the repository
						git 'https://github.com/triguiahmed/DevOps_Project.git'
						echo "Source code fetched successfully."
						
					} catch (Exception e) {
						error "Failed to fetch source code: ${e.message}"
					}
				}
			}
			}

        /*stage('Build') {
            steps {
                sh 'mvn clean install' // Example Maven build command
            }
        }
        stage('Unit Tests') {
            steps {
                sh 'mvn test' // Example Maven test command
            }
        }
        stage('Integration Tests') {
            steps {
                sh 'mvn integration-test' // Example Maven integration test command
            }
        }
        stage('Deploy') {
            steps {
                sh 'kubectl apply -f deployment.yaml' // Example Kubernetes deployment command
            }
        }*/
        stage('Display System Date') {
            steps {
                script {
                    def now = new Date()
                    echo "Current system date: ${now}"
                }
            }
        }
    }
    options {
        disableConcurrentBuilds()
    }
}
