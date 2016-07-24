# ninja-camunda
This project integrates the [Camunda BPMN process engine](https://camunda.org/) into the [Ninja Framework](https://github.com/ninjaframework/ninja).

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

- Configure a JDBC connection for Camunda to use through your `application.conf`. E.g. for MySQL:
```
camunda.jdbc.url=jdbc:mysql://my.db.server.com:3306/camunda
camunda.jdbc.username=camundaUser
camunda.jdbc.password=camundaPassword
camunda.jdbc.driver=com.mysql.cj.jdbc.Driver
```
You can also use your application's existing database. Camunda will automatically amend your DB schema with all necessary tables during the first start.

Have a look at the class `com.jensfendler.ninjacamunda.CamundaProvider` for an overview of additional properties. 

- Install an instance of the `NinjaCamundaModule` in your Ninja application's `conf.Modules class`:

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

## License

Copyright (C) 2016 Fendler Consulting cc.
This work is licensed under the Apache License, Version 2.0. See LICENSE for details.
