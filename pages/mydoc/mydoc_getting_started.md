---
title: Getting started with the GeoRegistry
tags: [getting_started]
sidebar: mydoc_sidebar
permalink: mydoc_getting_started.html
folder: mydoc
summary: The GeoRegistry is an integrated platform for data management and visualization.
---

## General Software Requirements

* Java 8 (Oracle jre recommended)
* PostgreSQL 9.5 +
* PostGIS 2.2 +
* [GeoPrism](https://github.com/terraframe/geoprism.git)

## Development Environment Recommendations

* Eclipse IDE for Java EE developers. You may not be able to use the latest version of Eclipse because there may not be AspectJ support yet. Check the [Eclipse AJDT website](https://www.eclipse.org/ajdt/) first to find out what version of Eclipse you want to download.
* AspectJ Development Tools Eclipse plugin (Must be the correct version for your Eclipse. For example with Eclipse Neon, use version 2.2.4).
* These should come standard with Eclipse: Maven, m2e

## What about Tomcat and Geoserver?

Tomcat and Geoserver will automatically be installed as part of our automated Maven tasks.

{% include links.html %}
