---
title: Setting up a Development Environment
permalink: mydoc_dev_setup.html
keywords: development environment
summary: "This page describes how to set up a development environment using Eclipse for contributing to the GeoRegistry."
sidebar: mydoc_sidebar
folder: mydoc
---

{% include tip.html content="For more reliable compatibility use Eclipse IDE (Luna)." %}


## Install Eclipse

### Install Eclipse

Go to the Eclipse downloads website and install Eclipse Enterprise Edition. Most likely you don't want to install the latest version of Eclipse because it may have issues with the AJDT plugin. [Check this site first to see what version of Eclipse to download:](https://www.eclipse.org/ajdt/) 

### Install AJDT
1. Install a version of AJDT that is compatible with your Eclipse. You need to make sure the version numbers line up. This is an Aspect weaving plugin which we use on the server project primarily to assist with transaction and request management.
2. In Eclipse go to 'help -> Install New Software'
3.  Enter this URL in the input field 'http://dist.springsource.org/release/AJDT/configurator/'

### Install M2E connector, buildhelper
1. In Eclipse go to 'window -> Preferences -> Maven -> Discovery -> Open Catalog'.
2. Search for buildhelper (by Sonatype) and install.

## Install Oracle Java 8

It is not required to install Oracle Java into your Eclipse, however it is recommended because the GeoRegistry is tested with Oracle java and is not guaranteed to work with the Open JDK. Additionally, Oracle java is faster than the open source competitors. Therefore, we recommend installing Oracle Java 8 and configuring your Eclipse to use it.

## Install The Database Software

1. Install PostgreSQL 9.5+ using whatever method you prefer.
2. Install PostGIS 2.2+ into your PostgreSQL. 

## Download and Load the GeoRegistry Into Eclipse

### 1. Load Geoprism Into Eclipse

1.  In Eclipse go to 'File -> Import -> Git -> Projects From Git -> Clone URI'.
2.  Set:
*  URI : git@github.com:terraframe/geoprism.git
*  Host : github.com
*  Repository path : terraframe/geoprism.git
*  User : git
3.  Click "Next".  Follow the wizard, and when asked to select projects to import select the [runway-v2]. This is the default branch.
4.  Click to download the repository
5.  Select "Import existing projects"
6.  Select all of the projects to import and make sure that "Search for nested projects" is checked.

### 2. Load the GeoRegistry Into Eclipse

1.  In Eclipse go to 'File -> Import -> Git -> Projects From Git -> Clone URI'.
2.  Set:
*  URI : git@github.com:terraframe/geoprism-registry.git
*  Host : github.com
*  Repository path : terraframe/geoprism-registry.git
*  User : git
3.  Click "Next".  Follow the wizard, and when asked to select projects to import select the following:
*  dev
*  master
4.  When asked to specify the destination directory, make sure that you check out this project as a sibling on the filesystem to your Geoprism repositroy.
    TODO: What does this mean?
5.  Select "Import existing projects"
6.  Select all of the projects to import and make sure that "Search for nested projects" is checked.

### 3. Load the GeoRegistry Adapter Into Eclipse

1.  In Eclipse go to 'File -> Import -> Git -> Projects From Git -> Clone URI'.
2.  Set:
*  URI : git@github.com:terraframe/common-geo-registry-adapter.git
*  Host : github.com
*  Repository path : terraframe/common-geo-registry-adapter.git
*  User : git
3.  Click "Next".  Follow the wizard, and when asked to select projects to import select the following:
*  dev
*  master
4.  Click to download the repository
5.  Select "Import existing projects"
6.  Select all of the projects to import and make sure that "Search for nested projects" is checked.

### 4. Configure the GeoRegistry
1. The root database credentials are set in the georegistry-server/pom.xml. If you aren't using postgres/postgres, change it now.

```
    <root.db>postgres</root.db>
    <root.user>postgres</root.user>
    <root.pass>postgres</root.pass>
```

{:start="2"}
2. By default our patcher script will use these root credentials to create a database and user by name 'georegistry' which the georegistry will use. If you want to change this you can by modifying `georegistry-server/src/main/resources/runwaysdk/server.properties`. You can also modify the database port here.


## Install NodeJS

By default Node JS is invoked to compile our ng2 source everytime the server is booted. In order to get this working you will need to install Node JS, refer to their official documentation. Additionally, the npm executable is invoked directly from Eclipse, which means it must be on the classpath of Eclipse's execution environment. Personally I have had issues with this when booting Eclipse from the Ubuntu quickstart launcher. If you run into this issue, try launching Eclipse directly from the file explorer.


## Run The Build Tools

The GeoRegistry comes preloaded with useful Eclipse launches located at georegistry/launches. Additionally, Geoprism does as well. You can run these launches in Eclipse by right clicking on them and selecting [Run as] -> (the name of the launch).

1. Right click on the cgradapter project. Click Run As -> Maven Install.
2. In the georegistry project, run [georegistry] patch clean.launch
3. In the georegistry project, run [georegistry] cargo-run-debug.launch

The patch clean launch will destroy any database that exists (with the same name as configured in your server.properties) and then it will build a new one from scratch. The cargo-run-debug launch will build the georegistry, load it into tomcat, and then boot tomcat. You will then be able to hit the GeoRegistry in a web browser at https://localhost:8443/georegistry

If you run into this error when running a launch: [Variable references non-existent resource : ${workspace_loc:/geoprism-registry}], then you may need to open the launch file and point it to your geogregistry project.


{% include links.html %}
