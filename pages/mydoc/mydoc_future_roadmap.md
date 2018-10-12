---
title: Road Map
sidebar: mydoc_sidebar
permalink: mydoc_future_roadmap.html
folder: mydoc
---

## Road Map Overview

GeoPrism has it's roots in providing intuitive solutions for people with limited technical resources to better manage, aggregate, and visualize their data.  The user stories used to define functionality for these purposes are expanding over time and so is the collection of features built to support those user stories.  Because of this, GeoPrism is undergoing regular changes designed to help improve usability and flexibility.  With every feature considered the GeoPrism dev team carefully considers software design, extensibility, and scalability.

## Considerations For Software Integrations

GeoPrism relies heavily on the incredible software resources provided by the open source community. GeoPrism is provided under an open source license that helps to continue the evolution of the product by enabling it's usage by multiple organizations. As such, any software integrated into GeoPrism MUST have an open source license that allows for royalty free usage by any company choosing to deploy GeoPrism for commercial purposes.  These tools must also exhibit reasonable levels of stability, contributor base, and momentum.  While any project may have varying levels of any one of those categories a reasonable judgment and testing must be used to ensure the usage of that software adheres to the high standards of the GeoPrism project.

## Kaleidoscope

GeoPrism Kaleidoscopes are the primary data analysis tools for building and sharing stories from data.  The Kaleidoscope engine is intended to be a robust analysis suite requiring a moderate technical skill level.  Currently the Kaleidoscope engine is fully featured for basic mapping, charting, and analysis.  However, future enhancements are being considered for increasing functionality and optimizing pain points.

High Level Future Goals (not-exhaustive or final):

* Combining multiple indicators as a single combined indicator.
* In-line chart builder that enables a more tightly integrated chart design environment.
* Ability to save snapshots of a Kaleidoscope to a snapshot report (see next topic area).

## Snapshot Reports (future addition)

Snapshot reports are web-based simplified versions of a Kaleidoscope that tells a clear story from data.  The idea is to enable analysts to disseminate reports to any device that are performant and help decision makers make decisions fast.

High Level Future Goals (not-exhaustive or final):

* Easily sharable via URLs.
* Highly performant and interactive when viewed on a digital device.
* Viewable on desktop or mobile devices.
* Easily created from a Kaleidoscope.
* Exportable to PDF for additional dissemination support.

## Geo-Ontology Management (system data)

Ontologies are the core of the underlying technology (RunwaySDK) used to power GeoPrism.  They provide a formal structure for data in the sytem.  Geo-Ontologies are the core objects used to model location as ontologies.  Management of these locations are currently supported through two primary mechanisms (GeoEntity tree and Location Manager).  However, these tools are being improved to better support large ontologies.

High Level Future Goals (not-exhaustive or final):

* Better navigation through a GeoEntity tree with more than 100,000 location nodes.
* Better performance for location lookups when querying the GeoEntity tree.
* More robust widgets for managing ontology relationships and geometric attribution.

## User Data Management

User data includes any data integrated to GeoPrism through either GeoPrism data up-loader tools or directly by a GeoPrism expert.  User data does not include Geo-Ontology data which is considered system data.  An example would be a spreadsheet uploaded through the spreadsheet up-loader.

High Level Future Goals (not-exhaustive or final):

* In-line configuration of integrations with web-based data sources (i.e. Fulcrum, US Census, etc...).
* Shapefile up-loader.

## System Management

Coming soon...

## Theming

Basic theming is currently possible for integrating custom logos.  

High Level Future Goals (not-exhaustive or final):

* Better configuration of page color pallets.

## Performance

Performance in general is an on-going consideration for various areas of the platform.  The unique nature of how data is managed in GeoPrism and RunwaySDK enables robust query flexibility sometimes at the cost of performance.  That said, there are multiple areas where improvements can be made.

High Level Future Goals (not-exhaustive or final):

* Geo-Ontology lookups when assigning location to user data.
* Querying the GeoEntity tree with large Geo-Ontology models.
* Rendering of large layers in a Kaleidoscope.
* Rendering large geographic data sets in the browser.

## Mobile Support

Some parts of GeoPrism are more mobile friendly than others based on the nature of the widget being used.  For example, managing geographic data isn't as practical on mobile devices as viewing a snapshot report.  For this reason tools where chosen and pages where build with the underlying ability to support mobile operations.  However, some improvements are needed to improve the mobile experience.

## Off-line Support

Coming soon...

## Developer Operations

Coming soon...

## Deployment Environments
GeoPrism can be deployed to a number of environments both local or in the cloud.  This is possible through the use of Maven, Jenkins, Ansible, and Docker which all can be used to deploy with a relatively easy process.  However, this is a very large and robust platform environment and so should not be expected to be an "easy install" even if being done by an experienced developer familiar with these tools.

The most common environments used are:

* Linux on AWS EC2
* Linux on AWS Elastic Beanstalk
* Linux on Switch




{% include links.html %}
