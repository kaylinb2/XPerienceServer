pipeline {
    agent any

    parameters {
        string(name: 'CUSTOM_PORT', defaultValue: '9020', description: 'Port to run the app on')
    }

    environment {
        PORT = "${params.CUSTOM_PORT}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean compile assembly:single'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def imageName = "xperience-server:${PORT}"
                    sh "docker build --no-cache -t ${imageName} ."
                }
            }
        }

        stage('Run Docker Container') {
            steps {
                script {
                    def containerName = "xperience-server-${PORT}"
                    def imageName = "xperience-server:${PORT}"

                    // Stop and remove any existing container with the same name
                    sh "docker ps -q --filter name=${containerName} | xargs -r docker rm -f || true"

                    // Run the new container on the given port
                    sh """
                        docker run -d \
                        --name ${containerName} \
                        -p ${PORT}:${PORT} \
                        ${imageName} java -jar target/*jar-with-dependencies.jar ${PORT}
                    """
                }
            }
        }
    }
}
