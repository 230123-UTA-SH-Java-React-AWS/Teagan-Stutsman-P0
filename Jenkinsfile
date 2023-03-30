pipeline {
    agent any

    stages {

        stage('Build and create .jar file'){
            steps {
                echo 'Building the .jar file'

                sh 'mvn -version'

                sh 'mvn clean package'
            }
        }

        stage('Create docker image') {
            steps {
                // Removes extra docker images
                sh 'sudo docker image prune -f'

                // Builds image of application
                sh 'sudo docker build -t tstutsma/api:latest .'
            }
        }

        stage('Deploying into docker container') {
            steps {
                // Stop running containers
                sh 'sudo docker rm $(sudo docker ps -aq)'

                // Run latest version of image
                sh 'sudo docker run -e url=$url -d -p 80:5050 -t tstutsma/api:latest'
            }
        }
    }
}