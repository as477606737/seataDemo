FROM java
VOLUME /tmp
ADD target/seataDemo.jar seataDemo.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/seataDemo.jar"]