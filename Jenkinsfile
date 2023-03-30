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
    }
}