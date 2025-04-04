pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test with Maven') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t xperience-server .'
            }
        }

        stage('Run Docker Container') {
            steps {
                sh 'docker run -d --name xserver -p 9020:9020 xperience-server 9020 passwords.txt'
            }
        }

        stage('Verify Container Logs') {
            steps {
                sh 'sleep 2 && docker logs xserver'
            }
        }

        stage('Copy JAR to ~/Program') {
            steps {
                // Copies built JAR from Jenkins workspace to your local folder
                sh 'cp target/app-jar-with-dependencies.jar /home/kaylinb2/Program/'
            }
        }
    }

    post {
        always {
            // Clean up container after pipeline runs
            sh 'docker rm -f xserver || true'
        }
    }
}
