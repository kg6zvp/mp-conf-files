# MP-Conf-files

Enhancements to MP-Config for handling files and strings

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/es.eisig/mp-conf-files.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22es.eisig%22%20AND%20a:%22mp-conf-files%22)
[![Build Status](https://travis-ci.com/kg6zvp/mp-conf-files.svg?branch=master)](https://travis-ci.com/kg6zvp/mp-conf-files)

## Example files

persistence.xml using variables from MP-Config

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://xmlns.jcp.org/xml/ns/persistence"
	xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd"
	version="2.1">
	<persistence-unit name="MyPU">
		<provider>org.hibernate.ogm.jpa.HibernateOgmPersistence</provider>
		<properties>
			<property name="hibernate.ogm.datastore.provider" value="mongodb"/>
			<property name="hibernate.ogm.datastore.host" value="${app.db.host}"/>
			<property name="hibernate.ogm.datastore.database" value="${app.db.name}"/>
			<property name="hibernate.ogm.datastore.create_database" value="${app.devmode}"/>
			<property name="javax.persistence.schema-generation.database.action" value="${jpa.initaction}" />
			<property name="hibernate.show_sql" value="${app.debugging}"/>
		</properties>
	</persistence-unit>
</persistence>
```

## Use Cases

Loading Strings and files with values pre-filled from Microprofile Config is very handy

Variables in Strings and files are in the format: `${var.name}` and can be skipped by adding a slash: `\${var.name}`

1. Load a String

```java
import eisiges.mp_conf_files.StringInterpolator;
...
System.setProperty("user.name", "Steve"); //set system properties read by MP-Config
System.setProperty("user.age", "101");
String originalString = "Hello, ${user.name}. You are ${user.age} years old."
System.out.println(StringInterpolator.interpolate(originalString));
// prints "Hello, Steve. You are 101 years old."
```

2. Load a String with escaped variable names

```java
import eisiges.mp_conf_files.StringInterpolator;
...
System.setProperty("app.timezone", "-0700"); //set system properties read by MP-Config
String originalString = "Timezone set to ${app.timezone} with \\${app.timezone}"
System.out.println(StringInterpolator.interpolate(originalString));
// prints "Timezone set to -0700 with ${app.timezone}"
```

3. Load a resource via URL

```java
import eisiges.mp_conf_files.FileInterpolator;
...
URL originalURL = ...
URL fixedUpURL = FileInterpolator.load(originalURL); // now can be used to read from URL with values filled in from mp-config
```

4. Load a File without modifying the original

```java
import eisiges.mp_conf_files.FileInterpolator;
...
File originalFile = ...
File fixedUpFile = FileInterpolator.load(originalFile); // now can be used to read from File with values filled in from mp-config
```

## Getting started

1. Add a Microprofile Config implementation to your project. There is a list of them [here](https://wiki.eclipse.org/MicroProfile/Implementation#MP_Config)

2. Add mp-conf-files to your project

Maven:
```xml
		<dependency>
			<groupId>es.eisig</groupId>
			<artifactId>mp-conf-files</artifactId>
			<version>1.0.0</version>
		</dependency>
```

Gradle:
```groovy
repositories {
	mavenCentral()
}

dependencies {
	implementation 'es.eisig:mp-conf-files:1.0.0'
}
```
