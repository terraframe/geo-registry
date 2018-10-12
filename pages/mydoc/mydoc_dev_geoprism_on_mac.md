---
title: Install the GeoRegistry on Mac
tags: [getting_started, troubleshooting]
keywords:
summary: "While the GeoRegistry is primarily used in production on Linux servers setup on a Mac for development purposes works well."
sidebar: mydoc_sidebar
permalink: mydoc_dev_geoprism_on_mac.html
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

## Install And Setup The Database

Install PostgreSQL 9.5+ using whatever method you prefer.

```
CREATE EXTENSION postgis;
```

## Download and Load the GeoRegistry Into Eclipse
<!-- ### 1. Download RunwaySDK

1.  Download or clone the RunwaySDK project from the [Github repo](https://github.com/terraframe/Runway-SDK.git).
2.  Import RunwaySDK into you Eclipse environment.

### 2. Download RunwaySDK-GIS

1.  Download or clone the RunwaySDK-GIS project from the [Github repo](https://github.com/terraframe/Runway-SDK-GIS.git).
2.  Import RunwaySDK-GIS into you Eclipse environment. -->

### 1. Load the GeoRegistry Into Eclipse

1.  In Eclipse go to 'File -> Import -> Git -> Projects From Git -> Clone URI'.
2.  Set:
*  URI : git@github.com:terraframe/geoprism.git
*  Host : github.com
*  Repository path : terraframe/geoprism.git
*  User : git
3.  Click "Next".  Follow the wizard, and when asked to select projects to import select the following (at a minimum):
*  dev
*  master
4.  Click to download the repository
5.  Select "Import existing projects"
6.  Select all of the projects to import and make sure that "Search for nested projects" is checked.

### 2. Configure the GeoRegistry
1.  Configure the database properties by modifying the database properties in server.properties in geoprism-server to:
*  user=georegistry
*  password=georegistry
*  databaseName=georegistry

### 3. Create And Configure The GeoRegistry Database
1.  Create a new database called 'georegistry' using template-postgis as a template to ensure PostGIS is included.
2.  If you don't have a template-postgis database you can enable postgis by running the SQL command:

```
CREATE EXTENSION postgis;
```

## Install Oracle Java 8

1.  If you don't already have it installed you should install the Oracle version of Java 8.  The Oracle version is faster than the open source competitors.

## Install The Build Tools

### 1. Install Maven

1.  Install Maven in the appropriate way for your OS version.

### 1. Configure A Basic Maven Build
1.  Create a new Maven build defining the following properties:
*  goals : clean install -P geoprism-cargo-run-debug
*  params : configuration.dir
3. Define the base directory as ${workspace_loc:/geoprism}
2. Add a new parameter:
* Name: configuration.dir
* Value: ${workspace_loc:/geoprism-platform}/Customer_projects/<Project Config Directory>

### 2. Install Ansible (optional)

1. Follow the Ansible instructions: [here](http://docs.ansible.com/ansible/intro_installation.html)




{% include links.html %}
