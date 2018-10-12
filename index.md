---
title: Introduction to the GeoRegistry
keywords: homepage
tags: [introduction]
sidebar: mydoc_sidebar
permalink: index.html
summary: The GeoRegistry is a formal management system for ontologically-enabled geospatial data.
---


## Overview

The GeoRegistry is a formal management system for ontologically-enabled geospatial data.

## An Ontological Approach to GIS Development

By utilizing an ontological approach to software development the GeoRegistry enables the utilization of almost any data set even if no geometries exist for data points. This is one of the great strengths of the GeoRegistry as it allows for analysing and visualizing data in a geographic context even when geometry information is lacking. Additionally, ontologies and geo-ontologies allow for navigating relationship hierarchies (including spatial) where such relationships would typically be computationally derived from geometric relationships or manually assigned by an individual.

## Important Concepts of the GeoRegistry

In GeoDashboard a Universal represents a feature set type to which data points can be related (EX: Countries, States, or Cities). A GeoEntity represents the actual geographic feature for a Universal (EX: USA, Colorado, or Denver). In essence, a Universal defines a geographic heirarchy to which GeoEntities are assigned. Relationships are then defined between data injested into the system and one or more GeoNodes which in turn have a relationship to one or more Universals.

## Visualization Features Overview

* Some of the more prominent features of this theme include the following:
* Dynamically map and style layers based on data attributes.
* Dynamically aggregate layers by spatial boundaries.
* Enable/disable reference boundary layers for gegraphic context and cartography.
* Filter data by date, text, ontology term, number, and/or location.
* Save data filters for a map or dashboard.
* Add custom BIRT charts and graphs that update based on layer feature click.
* Export maps to .png file format.
* Create and clone new dashboards.
* Switch between different saved dashboards and maps.

## Data Management Features Overview

* Visualize Universal relationship heirarchy.
* Edit Universals and Universal relationship heirarchy.
* Create Universal subtypes.
* Export Universal definitions.
* Visualize GeoEntity relationship heirarchy.
* Edit GeoEntities (including their geometry) and GeoEntity relationship heirarchy.
* Create GeoEntity synonyms.
* Export GeoEntity definitions.
* Identify system detected GeoEntity data issues.
* Fix system identified GeoEntity data issues.
* Edit ontology term and term heirarchy.
* Export ontology term definitions.
* Visualize data in a tabular form.
* Create and edit scheduled data upload jobs.
* View scheduled data upload job execution history.

## System Administration Features Overview

* Create and administer new users to the system.
* Assign users to system roles.
* Assign users privileges to dashboards.

## Developer Features Overview

* Metadata driven development environment.
* Integrated Object Relational Mapping (ORM).
* Full localization support.

## Getting started

To get started, see [Getting Started][index].

{% include links.html %}
