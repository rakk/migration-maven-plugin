# (Simple) Migration Maven Plugin


## Overview

This plugin will help you with migration any maven dependency.

Features:
* setup sources location
* replacing java/groovy package imports

## How to run install plugin


```
git clone https://github.com/rakk/migration-maven-plugin.git
cd migration-maven-plugin
mvn clean install
```

## How to run plugin in your maven project

### Configuring plugin

Add plugin to your project:

```
    <profiles>
        <profile>
            <id>migrate</id>
            <build>
                <plugins>
                    <plugin>
                        <groupId>org.rakk</groupId>
                        <artifactId>migration-maven-plugin</artifactId>
                        <version>1.0-SNAPSHOT</version>
                    </plugin>
                </plugins>
            </build>
        </profile>
    </profiles>
```

Put your migration configuration in root project.
Migration configuration must ends with ```.migration.xml```
Example configuration you can find below.

### Running plugin:

Execute this command to run plugin:

```
mvn org.rakk:migration-maven-plugin:migrate -Pmigrate
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

Where can I found example configuration for commons library?
Be the first one who will create such configuration.
