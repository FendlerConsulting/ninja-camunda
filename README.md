# Ninja Camunda Module
This project provides integration of the [Camunda BPMN process engine](https://camunda.org/) into the [Ninja Framework](https://github.com/ninjaframework/ninja).

Basic Usage:
------------

- ninja-camunda has been published to Maven Central. Add the following dependency to your Ninja application's `pom.xml`:

```xml

    <dependency>
        <groupId>com.jensfendler</groupId>
        <artifactId>ninja-camunda</artifactId>
        <version>0.0.1</version>
    </dependency>

```

- Configure a basic JDBC connection for Camunda to use through your `application.conf`. E.g. for MySQL:
```
camunda.jdbc.url=jdbc:mysql://my.db.server.com:3306/camunda
camunda.jdbc.username=camundaUser
camunda.jdbc.password=camundaPassword
camunda.jdbc.driver=com.mysql.cj.jdbc.Driver
```
You can also use your application's existing database. Camunda will automatically amend your DB schema with all necessary tables during the first start.
For advanced configuration options, have a look at the `application.conf` property descriptions below.

- Install an instance of the `NinjaCamundaModule` in your Ninja application's `conf.Modules` class:

```java

	install(new NinjaCamundaModule());

```

- Inject a `Provider<Camunda>` into your classes (e.g. controllers) as necessary, and use the provider's `get()` method to get a singleton instance of the `Camunda` interface, which provides convenience methods to access all of the Camunda BPMN engine's internals (e.g. `RepositoryService`, `RuntimeService`, `TaskService`, ...)

```java
	@Inject
	Provider<Camunda> camundaProvider;

    public void deploySomething(void) {
		RepositoryService repoService = camundaProvider.get().getRepositoryService();
		// ...
		repoService.createDeployment().addInputStream(resourceName, bpmnStream).enableDuplicateFiltering(true).deploy();
		// ...
    }

```

Configuration Options through application.conf
----------------------------------------------

The following configuration options are available through `application.conf` to setup the Camunda process engine according to your requirements:

- `camunda.jdbc.username` (String) the JDBC username

- `camunda.jdbc.password` (String) the JDBC password

- `camunda.jdbc.driver` (String) the JDBC driver class to use

- `camunda.jobexecutor.deploymentAware` (boolean) if the JobExecutor should be deployment-aware (default: true)

- `camunda.jobexecutor.activate` (boolean) if the JobExecutor should immediately be activated (default: true)

- `camunda.db.inMemory` (boolean) if true, the JDBC configuration options above are ignored and an H2 in-memory database is used for the process engine (default: false)

- `camunda.db.schemaUpdate` (String) if "true" the camunda schema is created and updated, if "false" the schema is not changed, if "create-drop" any existing schema is dropped and re-created (default: "true")

- `camunda.useAuthorization` (boolean) if true, camunda uses user/group authorization

- `camunda.smtp.from` (String) the "FROM" address for camunda-sent emails. If empty (or not specified), SMTP is not configured and Camunda will not be able to send mails via SMTP

- `camunda.smtp.host` (String) the SMTP server name (defaults to Ninja's Postoffice SMTP server)
 
- `camunda.smtp.port` (int) the SMTP port number (defaults to Ninja's Postoffice SMTP port number, and to 25 if not set)

- `camunda.smtp.username` (String) SMTP username (defaults to Ninja's Postoffice SMTP username)

- `camunda.smtp.password` (String) the SMTP password (defaults to Ninja's Postoffice SMTP password)

- `camunda.smtp.tls` (boolean) if true, SMTP connections will be encrypted via TLS (defaults to Ninja's Postoffice SSL setting)



## License

Copyright (C) 2016 Fendler Consulting cc.
This work is licensed under the Apache License, Version 2.0. See LICENSE for details.
