pipeline {
    agent any

    environment {
        REPO_URL = 'https://github.com/hasan2001jk/WebApplication-CakeStudio-GitLab'
        BRANCH = 'main'
    }

    stages {
        stage('Clone Repository') {
            steps {
                echo 'Cloning repository...'
                git branch: "${BRANCH}", url: "${REPO_URL}", credentialsId: 'github-credentials'
            }
        }

        stage('Regenerate Maven Wrapper') {
            steps {
                echo 'Regenerating Maven wrapper files...'
                // Ensure Maven is installed on the Jenkins agent
                sh '''
                mvn -N wrapper:wrapper
                chmod +x mvnw
                '''
            }
        }

        stage('Build') {
            steps {
                echo 'Building the project using Maven wrapper...'
                sh './mvnw clean install -Dmaven.test.failure.ignore=true'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                sh './mvnw test'
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging the application...'
                sh './mvnw package -Dmaven.test.skip=true'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image from Dockerfile1...'
                sh 'docker build -t app-image-1 -f Dockerfile1 .'

                echo 'Building Docker image from Dockerfile2...'
                sh 'docker build -t app-image-2 -f Dockerfile2 .'
            }
        }

        stage('Run with Docker Compose') {
            steps {
                echo 'Starting services with docker-compose...'
                sh 'docker-compose up -d'
            }
        }

        stage('Deploy with Ansible') {
            steps {
                echo 'Deploying application using Ansible...'
                sh 'ansible-playbook ansible/deploy.yml'
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
