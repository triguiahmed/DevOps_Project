pipeline {
    agent any
    environment {
        IMAGE_TAG = "${env.BUILD_NUMBER}"
        DOCKERHUB_USERNAME =  "${env.DOCKERHUB_USERNAME}"
        DOCKERHUB_PASSWORD =  "${env.DOCKERHUB_PASSWORD}"
        }
    stages {
        stage('Fetch Source Code') {
			steps {
				script {
					try {
                        echo "Cleaning Workspace"
                        cleanWs()
                        echo "Fetching source code..."
						git 'https://github.com/triguiahmed/DevOps_Project.git'
						echo "Source code fetched successfully."
					} catch (Exception e) {
						error "Fail in Fetch Source Code stage: ${e.message}"
					}
				}
			}
			}

        stage('MVN Clean') {
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

        stage('MVN Compile') {
            steps {
                script {
                        try {
                            echo 'Compile the project...'
                            sh 'cd DevOps_Project && mvn compile'
                            echo 'Project built successfully.'
                        } catch (Exception e) {
                            error "Fail in Build stage: ${e.message}"
                        }
                    }
            }

        }
        /* stage('SonarQube tests') {
             steps {
                withSonarQubeEnv('sonar') {
                     sh "cd DevOps_Project && mvn sonar:sonar -Dsonar.projectKey=DevOps -Dsonar.projectName='DevOps' -Dsonar.host.url=http://sonarqube:9000"
                 }
            }
        }
        stage('MVN TEST'){
                steps{
                    sh 'mvn clean test';
                }
            }
        */
            stage("MVN Build") {
               steps {
                sh 'mvn install -DskipTests=true'
                }
            }

        

        stage('Nexus Deploy') {
            steps {
                script {
                        try {
                            echo 'Deploying project...'
                            sh "cd DevOps_Project && mvn deploy -U -DaltDeploymentRepository=deploymentRepo::default::http://nexus:8081/repository/maven-releases/"
                            echo 'Project deployed successfully.'
                        } catch (Exception e) {
                            error "Fail in Nexus Deploy stage: ${e.message}"
                        }
                    }
            }
        }

        stage('Build backend docker image') {
                steps {
                    echo "Building backend docker image"
                    sh 'docker build -t $DOCKERHUB_USERNAME/devops_project-2alinfo03:$IMAGE_TAG .'
                        }
                    }
        stage('Push images to Dockerhub') {
                steps{
                        script{
                        sh 'docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD'
                        sh 'docker push $DOCKERHUB_USERNAME/devops_project-2alinfo03:$IMAGE_TAG'
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
    }
    
