pipeline {
    agent any
    stages{
        stage("git"){
            steps{
                git 'https://github.com/wakaleo/game-of-life.git'
            }
        }
        stage("build"){
            steps{
                sh 'mvn package'
                 
            }
        }
        stage("artifact"){
            steps{
               archiveArtifacts artifacts: 'gameoflife-web/target/*.war', followSymlinks: false
                 
            }
        }
        stage("test results"){
            steps{
                junit 'gameoflife-web/target/surefire-reports/*.xml'
                 
            }
        }
        
        stage("deploy"){
            steps{
                sh "curl -v -u manager:manager -T /var/lib/jenkins/workspace/pipe1/gameoflife-web/target/gameoflife.war 'http://34.221.78.81:8090/manager/text/deploy?path=gameoflife'"                 
            }
        }
        



    }
    
}
stage("deploy"){
            steps{
                deploy adapters: [tomcat9(credentialsId: 'deploy1', path: '', url: 'http://34.221.78.81:8090/')], contextPath: 'game-of-life', war: 'gameoflife-web/target/*.war'
                 
            }
        }
 stage("deploy"){
    steps{
        sshagent(['game']) {
        // some block
        scp gameoflife-web/target/gameoflife.war ubuntu@34.221.78.81:/home/tomcat/tomcat/webapps
         }
    }
}