# (Simple) Migration Maven Plugin


## Overview

This plugin will help you with migration dependency.
The migration will not be the completed, it is more a starting point.

Features:
* setup sources location
* replacing java/groovy package imports

## Migrations:
* [QueryDSL 3.x to 4.x](querydsl.form.3.to.4.migration.xml)

## How to run install plugin


```
git clone https://github.com/rakk/migration-maven-plugin.git
cd migration-maven-plugin
mvn clean install
```

## How to run plugin in your maven project

### Add you configurations for migrations

Put your migration configuration in root project.
Migration configuration must ends with ```.migration.xml```
Example configuration you can find below.

### Running plugin:

Execute this command to run plugin:

```
mvn org.rakk:migration-maven-plugin:migrate
```

## Example configuration: example.migration.xml
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<migrationConfiguration>
    <name>Migration ... from version 3.x to 4.x</name>
    <sources>
        <source>
            <filePattern>.*\.java</filePattern>
            <path>src/main/java</path>
        </source>
        <source>
            <filePattern>.*\.java</filePattern>
            <path>src/test/java</path>
        </source>
        <source>
            <filePattern>.*\.groovy</filePattern>
            <path>src/test/groovy</path>
        </source>
    </sources>
    <importUpdates>
        <importUpdate>
            <oldPath>some.library.old.path.OldClass</oldPath>
            <newPath>some.library.new.NewClass</newPath>
        </importUpdate>
    </importUpdates>
</migrationConfiguration>

```

## FAQ

Where can I found example configuration for commons library?
> Be the first one who will create such configuration.

What kind of guarantee I got that migration will be successful?
> None. Zero. Nothing.
