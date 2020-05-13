pipeline {
	
	agent any

	parameters {

		string(name: 'Greetings', defaultValue: 'Hello', description: 'How should i greet the world')
	}

	stages {

		stage('Build') {

			steps {

				echo 'make build'
				echo "${params.Greetings} World"

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