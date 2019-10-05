pipeline {
    agent { label 'aws-agent' }
  
    stages {
        stage("Environment configuration") {
            steps {
                sh 'git --version'
                echo "Branch: ${env.BRANCH_NAME}"
                sh 'docker -v'
                sh 'printenv'
            }
        }
        stage("Build") {
            steps {
                echo 'Cloning git ...'
                git([url: 'https://github.com/jovanibrasil/search-api.git', branch: 'master', credentialsId: '9bae9c61-0a29-483c-a07f-47273c351555'])
            	// search-api build
            	sh 'make build'
            	// solr build
            	sh 'cd solr-docker && make build' 
            }
        }

        stage("Test"){
            steps {
                echo 'Todo'
            }
        }

        stage("Registry image"){
            steps {
                echo 'TODO'
            }
        }
        
        stage("Deploy") {
            steps {
                
                script {
                    node {
                        // define the secrets and the env variables
                        def secrets = [
                        [path: 'secret/blog-api/prod', secretValues: [
                          [envVar: 'url', vaultKey: 'blog-cred.url'],
                          [envVar: 'username', vaultKey: 'blog-cred.username'],
                          [envVar: 'password', vaultKey: 'blog-cred.password']
                          ]]
                        ]
                        
                        // optional configuration, if you do not provide this the next higher configuration
                        // (e.g. folder or global) will be used
                        def configuration = [vaultUrl: 'http://vault-server:8200',
                                          vaultCredentialId: 'vault-cred-id']
                        
                        // inside this block your credentials will be available as env variables
                        withVault([configuration: configuration, vaultSecrets: secrets]) {
                            // run solr
        	                sh 'make run BLOG_MYSQL_URL=$url BLOG_MYSQL_USERNAME=$username BLOG_MYSQL_PASSWORD=$password && cd ..'
        	                // run search-api
        	                sh 'make run'
        	                sh 'sleep 60'
        	                sh 'cd solr-docker && make import-data'
                        }
                    }   
                }
            }
        }        
        stage("Remove temporary files"){
            steps {
                echo 'cleaning ...'
            }
        }
    }
}