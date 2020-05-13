pipeline {
	
	agent any

	stages {

		stage('Build') {

			steps {

				echo 'make build'

			}

		}

		stage('Deploy') {

			when {

				expression {
					currentBuild.result == null || currentBuild.result == 'SUCCESS'
					}
			}

			steps {

				echo 'make publish'
			}

		}
	}
}