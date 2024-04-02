pipeline {
    agent any
    environment {
        IMAGE_TAG = "${env.BUILD_NUMBER}"
        }
    stages {
        stage('Fetch Source Code') {
			steps {
				script {
					try {
                        echo "Fetching source code..."
						git 'https://github.com/triguiahmed/DevOps_Project.git'
						echo "Source code fetched successfully."
					} catch (Exception e) {
						error "Fail in Fetch Source Code stage: ${e.message}"
					}
				}
			}
			}

        stage('Clean') {
            steps {
                script {
                        try {
                            echo "Cleaning the project..."
                            sh 'cd DevOps_Project && ls && mvn clean'
                            echo "Project cleaned successfully."
                        } catch (Exception e) {
                            error "Fail in Clean stage: ${e.message}"
                        }
                    }
            }
        }

        stage('Build') {
            steps {
                script {
                        try {
                            echo 'Building the project...'
                            sh 'cd DevOps_Project && mvn compile'
                            echo 'Project built successfully.'
                        } catch (Exception e) {
                            error "Fail in Build stage: ${e.message}"
                        }
                    }
            }

        }
        
        // stage('SonarQube tests') {
        //     steps {
        //         withSonarQubeEnv('sonar') {
        //             sh "cd DevOps_Project && mvn sonar:sonar -Dsonar.projectKey=DevOps -Dsonar.projectName='DevOps' -Dsonar.host.url=http://sonarqube:9000"
        //         }
        //     }
        // }
        stage('Nexus Deploy') {
            /*steps {
                script {
                        try {
                            echo 'Deploying project...'
                            sh "cd DevOps_Project && mvn deploy -U -DaltDeploymentRepository=deploymentRepo::default::http://nexus:8081/repository/maven-releases/"
                            echo 'Project deployed successfully.'
                        } catch (Exception e) {
                            error "Fail in Nexus Deploy stage: ${e.message}"
                        }
                    }
            }*/
            steps {
                nexusArtifactUploader artifacts: [
                    [
                        artifactId: 'devops_project',
                        classifier: '',
                        file: 'target/devops_project.jar',
                        type: 'jar'
                    ]
                ],
                 credentialsId: 'nexus3',
                 groupId: 'tn.esprit.devops',
                 nexusUrl: 'nexus:8081',
                 nexusVersion: 'nexus3',
                 protocol: 'http',
                 repository: 'devop_project',
                 version: '0.0.1-SNAPSHOT'
            }
        }

        stage('Build backend docker image') {
                steps {
                    sh 'docker build -t $DOCKERHUB_USERNAME/devops_project-2alinfo03:$IMAGE_TAG .'
                        }
                    }
        stage('Push images to Dockerhub') {
                steps{
                        script{
                        sh 'docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD'
                        sh 'docker push  $DOCKERHUB_USERNAME/devops_project-2alinfo03:$IMAGE_TAG'
                        }
                    }
                }


        
        
    }
    }
    post {
        success {
            // Actions to perform on successful build
            echo 'Build successful!'
        }
        failure {
            // Actions to perform on build failure
            echo 'Build failed!'
        }
    }
