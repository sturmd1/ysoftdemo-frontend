FROM tomcat:8.0
COPY /view/target/ysoftdemo.war ${CATALINA_HOME}/webapps/
