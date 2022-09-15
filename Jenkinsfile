pipeline {
    agent any

    stages {

        stage('Build') {
            steps {
                withGradle {
                    sh 'cp ../env/PROD.properties ./src/main/resources/'
                    echo 'Building the app'
                    sh './gradlew build --stacktrace'
                }
            }
        }

        stage('Test') {
            steps {
                withGradle {
                    echo 'Testing...'
                    sh './gradlew test'
                }
            }
        }

        stage('Deploy') {
            steps {
                withEnv(['JENKINS_NODE_COOKIE=dontkill']) {
                    sh 'nohup docker-compose up -d'
                    echo 'Deploying...'
                    sh './gradlew shadowJar'
                    sh "nohup java -jar ./build/libs/WaterBotTelegram-1.0-SNAPSHOT-all.jar &"
                }
            }
        }
    }
}