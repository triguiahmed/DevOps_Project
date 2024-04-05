pipeline {
    agent any
    environment {
        IMAGE_TAG = "latest"
        DOCKERHUB_USERNAME =  "${env.DOCKERHUB_USERNAME}"
        }
    stages {
        stage('Fetch Source Code') {
			steps {
				script {
					try {
                        echo "Cleaning Workspace"
						sh 'git status'
                        
					} catch (Exception e) {
						error "Fail in Fetch Source Code stage: ${e.message}"
					}
				}
			}
			}

        stage('MVN Clean') {
            steps {
	       dir("./DevOps_Project"){
                  script {
                        try {
                            echo "Cleaning the project..."
                            sh 'mvn clean'
                            echo "Project cleaned successfully."
                        } catch (Exception e) {
                            error "Fail in Clean stage: ${e.message}"
                        }
                    }
		}
            }
        }

        stage('MVN Compile') {
            steps {
	       dir("./DevOps_Project"){
                script {
                        try {
                            echo 'Compile the project...'
                            sh 'mvn compile'
                            echo 'Project built successfully.'
                        } catch (Exception e) {
                            error "Fail in Build stage: ${e.message}"
                        }
                    }
	       }
            }

        }
        stage('SonarQube tests') {
             steps {
	       dir("./DevOps_Project"){
                withSonarQubeEnv('sonar') {
                     sh "mvn sonar:sonar -Dsonar.projectKey=DevOps-aziz-branch -Dsonar.projectName='DevOps-aziz-branch' -Dsonar.host.url=http://sonarqube:9000"
                 }
	       }
            }
        }
        stage('MVN TEST'){
                steps{
		       	dir("./DevOps_Project"){
	                    sh 'mvn test';
	                }
		}
	}
	    stage("MVN Build") {
	       steps {
		       dir("./DevOps_Project"){
				sh 'mvn install -DskipTests=true'
			}
	       }
	    }
        stage('Nexus Deploy') {
            steps {   
	       dir("./DevOps_Project"){
                script {
                        try {
                            echo 'Deploying project...'
                            sh "mvn deploy -U -DaltDeploymentRepository=deploymentRepo::default::http://nexus:8081/repository/maven-releases/ -DskipTests=true"
                            echo 'Project deployed successfully.'
                        } catch (Exception e) {
                            error "Fail in Nexus Deploy stage: ${e.message}"
                        }
                    }
	       }
            }
        }

        stage('Build backend docker image') {
                steps {
                    echo "Building backend docker image"
                    sh 'docker build -t $DOCKERHUB_USERNAME/devops_project-2alinfo03-g2-backend:$IMAGE_TAG .'
                        }
                    }
	stage('Build frontend docker image') {
                steps {
                    echo "Building frontend docker image"
                    sh 'docker build -t $DOCKERHUB_USERNAME/devops_project-2alinfo03-g2-frontend:$IMAGE_TAG -f frontend.Dockerfile .'
                        }
                    }
        stage('Push images to Dockerhub') {
                steps{
                        script{
				withCredentials([string(credentialsId: 'dockerhub_cred', variable:'dockerhub_cred')]) {
		                        sh 'echo ${dockerhub_cred} | docker login -u $DOCKERHUB_USERNAME --password-stdin'
		                        sh 'docker push $DOCKERHUB_USERNAME/devops_project-2alinfo03-g2-backend:$IMAGE_TAG'
					sh 'docker push $DOCKERHUB_USERNAME/devops_project-2alinfo03-g2-frontend:$IMAGE_TAG'
				}
                        }
                    }
                }
	stage('Deploy') {
                steps{
                        script{
				sh 'docker compose down'
                        	sh 'docker compose up -d --build'
                        }
                    }
                }
        
    }
    
    post {
	always {
		cleanWs()
	}
    }
    }
    
