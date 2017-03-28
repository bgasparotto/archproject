# Arch Project

Arch Project is a basic Java EE application that can be used as an entry point to develop a web application that fits to your specific needs.

Some of the Arch Project's aspects are the following:
  - Developed under Java 8 and Java EE 7 spec;
  - Uses Gradle for building and dependency management;
  - Deployable on Wildfly 8.2;
  - Multi-layered architecture;
  - Clean code, Sun's code conventions, some of Object Calisthenics, S.O.L.I.D. and best practices are the main goal.

## Important!
This project is currently under development under studying purposes. Being said that, feel free to fork and/or use it as a basis to your own application, but you may find some bugs and improvement points.

## Installation
##### 1. PostgreSQL
Download and install the version 9.2.x from [this link](https://www.postgresql.org/download/), setting `postgres/postgres` as user and password.
Create a new `UTF-8` database named `archproject`.
Run the queries from [this file](https://github.com/bgasparotto/archproject/blob/master/archproject.sql) on this new database to create the required resources.

##### 2. Wildfly
Download the version 8.2.1.Final from [this link](http://wildfly.org/downloads/) and extract in your machine.
Create an environment variable `JBOSS_HOME` pointing to the path where you extracted it.

##### 3. Gradle
Download and install the version 2.2.1 from [this link](https://gradle.org/releases).
Create an environment variable `GRADLE_HOME` pointing to the path where you extracted it.
Add `%GRADLE_HOME%/bin` to your `PATH` environment variable.

## Configuration
Download the database driver `postgresql-9.4-1200-jdbc41.jar` from [this location](http://central.maven.org/maven2/org/postgresql/postgresql/9.4-1200-jdbc41/postgresql-9.4-1200-jdbc41.jar) and place it somewhere safe.
Copy [this file](https://github.com/bgasparotto/archproject/blob/master/archproject.cli) and place it inside the `bin` folder of your Wildfly. Edit it replacing `--resources=/opt/drivers/postgresql-9.4-1200-jdbc41.jar` with the location where you placed the jar file. Save and close.
Start Wildfly and run:
```
<JBOSS_HOME>/bin: jboss-cli.sh --file=archproject.cli
```
Stop Wildfly.

## Build
Go to the project root folder where the `build.gradle` resides and run:
```
gradle build
```

Happy coding!
