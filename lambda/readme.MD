##安装erlang  ###
###安装rabbitMQ server 启动服务 ###
C:\Program Files\RabbitMQ Server\rabbitmq_server-3.6.3\sbin>rabbitmq-server.bat

## 生成项目结构 ##
1. mvn archetype:generate -DgroupId=me.zzw.app -DartifactId=rabbitMQ -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
2. mvn archetype:create -DgroupId=me.zzw.app -DartifactId=rabbitMQ  -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
##导入jar##
    <dependency>
      <groupId>com.rabbitmq</groupId>
      <artifactId>amqp-client</artifactId>
      <version>3.6.3</version>
    </dependency>

[http://www.rabbitmq.com/tutorials/tutorial-one-java.html](http://www.rabbitmq.com/tutorials/tutorial-one-java.html "rabbitMQ_tutorial_java")

[https://github.com/rabbitmq/rabbitmq-tutorials/tree/master/java](https://github.com/rabbitmq/rabbitmq-tutorials/tree/master/java "rabbitMQ_tutorial_java_source")