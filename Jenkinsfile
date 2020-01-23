pipeline {
    agent { label 'digital-ocean-agent' }
  
  	environment {
		 DBURL = vault path: 'secret/blog-api/prod', key: 'blog-db-cred.url', vaultUrl: 'http://vault-server:8200', credentialsId: 'vault-cred-id', engineVersion: "1"
		 USERNAME = vault path: 'secret/blog-api/prod', key: 'blog-db-cred.username', vaultUrl: 'http://vault-server:8200', credentialsId: 'vault-cred-id', engineVersion: "1"
		 PASSWORD = vault path: 'secret/blog-api/prod', key: 'blog-db-cred.password', vaultUrl: 'http://vault-server:8200', credentialsId: 'vault-cred-id', engineVersion: "1"
	}
  
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
            	// build search-api image 
            	sh 'make build'
            	// build solr image
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
            	// run search-api 
              	sh 'make build && make run'
                // run solr
                sh 'cd solr-docker && make run BLOG_MYSQL_URL=${DBURL} BLOG_MYSQL_USERNAME=${USERNAME} BLOG_MYSQL_PASSWORD=${PASSWORD}'
                sh 'sleep 60'
                sh 'cd solr-docker && make import-data'
            }
        }        
        stage("Remove temporary files"){
            steps {
                echo 'cleaning ...'
            }
        }
    }
}