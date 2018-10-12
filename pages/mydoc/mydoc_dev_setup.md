---
title: Developing on the GeoRegistry
permalink: mydoc_dev_setup.html
keywords: the GeoRegistry on linux
summary: "This page describes how to set up a development environment using Eclipse for contributing to the GeoRegistry."
sidebar: mydoc_sidebar
folder: mydoc
---

{% include tip.html content="For more reliable compatibility use Eclipse IDE (Luna)." %}


## Configure Eclipse

### Install EGit
1. Install EGit if it is not already installed. EGit should be included with Eclipse Luna.

### Install m2e
1. Install Maven Integration for Eclipse - m2e (aka m2eclipse) if it is not already installed.  m2e should be included with Eclipse Luna.

### Install AJDT
1. Install AJDT if it is not already installed.
2. In Eclipse go to 'help -> Install New Software'
3.  Enter this URL in the input field 'http://dist.springsource.org/release/AJDT/configurator/'

### Install M2E connector, buildhelper
1. In Eclipse go to 'window -> Preferences -> Maven -> Discovery -> Open Catalog'.
2. Search for buildhelper (by Sonatype) and install.

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
3.  Click "Next".  Follow the wizard, and when asked to select projects to import select the following (at a minimum):
*  dev
*  master
4.  When asked to specify the destination directory, make sure that you check out this project as a sibling on the filesystem to your Geoprism repositroy.
5.  Select "Import existing projects"
6.  Select all of the projects to import and make sure that "Search for nested projects" is checked.

### 3. Load the GeoRegistry Adapter Into Eclipse

1.  In Eclipse go to 'File -> Import -> Git -> Projects From Git -> Clone URI'.
2.  Set:
*  URI : git@github.com:terraframe/common-geo-registry-adapter.git
*  Host : github.com
*  Repository path : terraframe/common-geo-registry-adapter.git
*  User : git
3.  Click "Next".  Follow the wizard, and when asked to select projects to import select the following (at a minimum):
*  dev
*  master
4.  Click to download the repository
5.  Select "Import existing projects"
6.  Select all of the projects to import and make sure that "Search for nested projects" is checked.

### 4. Configure the GeoRegistry
1. The root database credentials are set in the georegistry-server/pom.xml. If you aren't using postgres/postgres, change it now.
```
    <database.name>georegistry</database.name>
    <database.user>georegistry</database.user>
    <database.pass>georegistry</database.pass>
```

2. By default our patcher script will use these root credentials to create a database and user by name 'georegistry' which the georegistry will use. If you want to change this you can by modifying `georegistry-server/src/main/resources/runwaysdk/server.properties`. You can also modify the database port here.


TODO : Port and also which property is it using the one in the pom or in server.properties. This needs to be cleaned up and probably removed from the pom.


## Install Oracle Java 8

It is not required to install Oracle Java into your Eclipse, however it is recommended because the GeoRegistry is tested with Oracle java and is not guaranteed to work with the Open JDK. Additionally, Oracle java is faster than the open source competitors. Therefore, we recommend installing Oracle Java 8 and configuring your Eclipse to use it.

## NodeJS

TODO


## Run The Build Tools

The GeoRegistry comes preloaded with useful Eclipse launches located at georegistry/launches. Additionally, Geoprism does as well. You can run these launches in Eclipse by right clicking on them and selecting [Run as] -> (the name of the launch).

1. Run [georegistry] patch clean.launch
2. Run [georegistry] cargo-run-debug.launch

The patch clean launch will destroy any database that exists (with the same name as configured in your server.properties) and then it will build a new one from scratch.

TODO : Link to runway docs to talk about metadata management?
TODO : Variable references non-existent resource : ${workspace_loc:/geoprism-registry}


{% include links.html %}
