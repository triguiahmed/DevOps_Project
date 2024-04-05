pipeline {
    agent any
    environment {
        IMAGE_TAG = "latest"
        DOCKERHUB_USERNAME =  "${env.DOCKERHUB_USERNAME}"
        DOCKERHUB_PASSWORD =  "${env.DOCKERHUB_PASSWORD}"
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
        stage('SonarQube tests') {
             steps {
                withSonarQubeEnv('sonar') {
                     sh "cd DevOps_Project && mvn sonar:sonar -Dsonar.projectKey=DevOps -Dsonar.projectName='DevOps' -Dsonar.host.url=http://sonarqube:9000"
                 }
            }
        }
 //        stage('MVN TEST'){
 //                steps{
 //                    sh 'cd DevOps_Project && mvn test';
 //                }
	// }
            stage("MVN Build") {
               steps {
                sh 'cd DevOps_Project &&  mvn install -DskipTests=true'
                }
            }

        /*

        stage('Nexus Deploy') {
            steps {
                script {
                        try {
                            echo 'Deploying project...'
                            sh "cd DevOps_Project && mvn deploy -U -DaltDeploymentRepository=deploymentRepo::default::http://nexus:8081/repository/maven-releases/ -DskipTests=true"
                            echo 'Project deployed successfully.'
                        } catch (Exception e) {
                            error "Fail in Nexus Deploy stage: ${e.message}"
                        }
                    }
            }
        }*/

        stage('Build backend docker image') {
                steps {
                    echo "Building backend docker image"
                    sh 'docker build -t $DOCKERHUB_USERNAME/devops_project-2alinfo03-backend:$IMAGE_TAG .'
                        }
                    }
	stage('Build frontend docker image') {
                steps {
                    echo "Building frontend docker image"
                    sh 'docker build -t $DOCKERHUB_USERNAME/devops_project-2alinfo03-frontend:$IMAGE_TAG -f frontend.Dockerfile .'
                        }
                    }
        /* stage('Push images to Dockerhub') {
                 steps{
                         script{
                         sh 'docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD'
                         sh 'docker push $DOCKERHUB_USERNAME/devops_project-2alinfo03-backend:$IMAGE_TAG'
			 sh 'docker push $DOCKERHUB_USERNAME/devops_project-2alinfo03-frontend:$IMAGE_TAG'
                         }
                     }
                 }*/
	stage('Deploy') {
                steps{
                        script{
				            sh 'docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD'
                            sh 'docker compose down' // Stop and remove existing containers
                        	sh 'docker compose up -d'
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

        always {
            script {
                cleanWs()
                currentBuild.result = currentBuild.currentResult
           
                        emailext subject: "Pipeline Status ${currentBuild.projectName} | ${currentBuild.result}",
                        body: """
                            <html>
                            <head>
                                <style>
                                    body {
                                        font-family: Arial, sans-serif;
                                        background-color: #f5f5f5;
                                        padding: 20px;
                                    }
                                    .container {
                                        max-width: 600px;
                                        margin: 0 auto;
                                        background-color: #ffffff;
                                        border-radius: 5px;
                                        padding: 20px;
                                        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                                    }
                                    h1 {
                                        color: #333;
                                    }
                                    p {
                                        font-size: 16px;
                                        line-height: 1.5;
                                        color: #555;
                                    }
                                    a {
                                        color: #007bff;
                                        text-decoration: none;
                                    }
                                </style>
                            </head>
                            <body>
                                <div class="container">
                                    <h1>${currentBuild.projectName} Pipeline Status</h1>
                                    <p>Dear Team,</p>
                                    <p>The pipeline for project <strong>${currentBuild.projectName}</strong> has completed with the status: <strong>${currentBuild.result}</strong>.</p>
                                    <p>You can view the detailed pipeline information <a href="https://${env.JENKINS_URL}/job/${env.JOB_NAME}/-${env.BUILD_NUMBER}/">here</a>.</p>
                                    <p>Thank you,</p>
                                    <p>Your Jenkins Server</p>
                                </div>
                            </body>
                            </html>
                        """.stripIndent(),
                        to: "trigui.ahmed@esprit.tn",
                        mimeType: 'text/html'


            }
            }
    }
    }
    
