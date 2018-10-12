---
title: Formatting Spreadsheets
keywords: georegistry, spreadsheet, upload, data integration
last_updated: August 1, 2018
tags: [getting_started]
summary: "Requirements and tips on formatting spreadsheets for integration with the GeoRegistry."
sidebar: mydoc_sidebar
permalink: mydoc_formatting_spreadsheets.html
folder: mydoc
---

The spreadsheet uploader can be a very useful and efficient way to integrate data. While the uploader makes historically challenging tasks easier it is important to take steps to make sure your spreadsheet is formatted and has the information needed to upload smoothly. Before we get into more details it's important to understand how the uploader works to integrate your system with the GeoRegistry (see next section).

## How The Spreadsheet Uploader Integrates Data With The GeoRegistry
The GeoRegistry is built around the notion of maintaining a single source of truth for data. This means that system level data such as locations or other ontology based data networks will model instances of data in a formal yet flexible system with relationships between data nodes.

As an example, a location hierarchy for the U.S.A would have a set of locations for each layer in a hierarchy. One of those layers could be Counties which would have a relationship to States where Counties are 'located in' States. In this hierarchy there would be one and only one instance of Denver County which would be 'located in' Colorado State because the hierarchy defines the 'located in' relationship between the two locations.

With this location hierarchy in place tools like the spreadsheet uploader allows you to upload data while trying to resolve incoming data against the system data. If your spreadsheet has locations the uploader will try to determine if a location in a spreadsheet row matches a location in the system hierarchy.

Going back to our example, if one of those locations are Denver County the uploader will try to determine if there is a Denver County in the system location hierarchy. In this example it would be successful and that row in your spreadsheet would be imported into the system with a reference to that location (Denver County). By referencing the hierarchy we enable the ability to maintain that single definition of Denver County rather than each spreadsheet you import having it's own definition of Denver County.

Now that we better understand how the GeoRegistry manages system data, like locations, we can start to look at how spreadsheet data should be formatted to best take advantage of this system.

## Basic Requirements

1.  A spreadsheet must be in the XLSX format.
2.  A spreadsheet must only include data for a single country.
3.  A header must exist on the first row that contains unique names for each field.
4.  XLSX columns must be set to the appropriate data type.
5.  Only the first worksheet in an XLSX document will be accepted. All others will be ignored.
6.  Formulas are not supported. If your spreadsheet has formulas they must be pre-evaluated into basic types (number, text, or date) by the user.
7.  Aggregate rows or functions, such as grouping or subgrouping, are not supported.
8.  If including geometry fields, a unique id field must be included in the input data set.
9.  Geometric coordinates must be projected to EPSG:4326.
10. Geometric coordinates must be stored as two separate columns where one column contains the latitude and the other contains the longitude.


## Handling Semantic Locations
Semantic locations are locations expressed in text (ex: Denver). There are many ways to describe a location in text. Here are a few examples:

* Denver
* Denver City
* City of Denver
* Denver Colorado
* Denver, Colorado
* City and County of Denver
* denver
* denver city
* etc...

As you can see there are many variations for a single location and this example doesn't even include miss-spellings or language variations (tribal transliterations).

There are two main challenges we are trying to solve when formatting text based locations in your spreadsheet:

### 1. Handling variations in location names
The GeoRegistry has functionality that allows synonyms to be registered with a location in the hierarchy. This is very useful because it allows you to add all of the variations of a location name with the location in the hierarchy so that data coming into the system can match against a location with different semantic representations. However, whether the location hierarchy has the name that your data uses depends entirely on the hierarchy so your data may not match immediately. To prevent the need for registering new synonyms for a location in a later step of the upload it can be easier to format your spreadsheet to a naming convention that you know they hierarchy uses.

For example: If you know Counties are named with the base name only (i.e. Denver) and your data names locations with additional terminology (i.e. Denver County) it can be useful to remove "County" from the location names before importing.

It's important to realize that this isn't a requirement but can help depending on the situation. If your data has a way of naming locations which no location in the system has a synonym for you will have to manually register the new synonym with the corresponding location later in the upload. This is relatively easy but can be time consuming if you're dealing with a lot of locations.

### 2. Ensuring a unique location name for the locations you are uploading to
When uploading data with locations you must specify the level in the hierarchy that the locations represent (i.e. city, county, state, etc...). It's important to consider whether there are duplicate names across those locations you are matching against or the uploader will require you to resolve duplicate name name matches in a late step. It can be easier to format your spreadsheet to avoid this situation by adding one or more context location columns. By "context columns" I mean a set of columns that contain location names representing their place in the location hierarchy.

For example: A spreadsheet to be matched against Counties that has a single location name field of "Denver Colorado" could be split into two columns. One column would be "Denver" and the other would be "Colorado".

This is useful because when you are configuring attributes in the upload process you can specify that these two columns are describing Denver as being within Colorado.


## Handling ID Based Locations
ID based locations can be handled in a very similar way as semantic based location names. However, ID base matching tends to be more accurate and require less synonyms registered in the location hierarchy.

The same two primary challenges described for semantic based locations apply to ID location:

### 1. Handling variations in location IDs
For the U.S.A Census there are many variations used for location IDs depending on the type of location you are working with. Just like for semantic location names it can be useful to format location ID fields in a way that the system location hierarchy is already using in order to prevent the need for you to register new synonyms late in the upload process.

For example: If you know the system location hierarchy has County IDs registered as the only base County ID without the pre-pended State ID change your data to match that. An ID for Denver County can be changed from 08031 where "08" represents the state and "031" represents the county to simply "031".

### 2. Ensuring a unique ID for the locations you are uploading to
Just like how semantic location names can benefit from context columns so can ID based locations. In the last example we described how U.S.A. Census County ID can be split apart to better match existing IDs in the system location hierarchy. To create context columns from this same ID you can create a new column for States so that you end up with "08" in the State column and "031" in the County column.


## Handling Coordinate Locations
Coordinate locations are a latitude and longitude pair that represent a point on the earth. It is required that any coordinate locations you upload to be projected to EPSG:4326.

## Enhancing Semantic Or ID Based Locations With Coordinate Locations
If you expect your data might have troubles matching to unique locations because your data doesn't have context columns you can take advantage of a location matching algorithm by adding coordinate locations to your data. If you don't already have coordinate locations that fall within the locations you want to match against then you will have to generate them in an external tool (GIS, PostGIS, etc...).

For this approach you must configure your spreadsheet in the attributes configuration page to have location fields and coordinate locations (latitude and longitude). When configuring the location fields in the next step of the upload process you will be given an option to "Enhance location matches for ambiguous location names with coordinates from your data". This will use geometric analysis to calculate if the coordinate in your spreadsheet falls within one of the locations in the level of the hierarchy specified for your other location fields WHEN the matching process finds more than one location in the system location hierarchy matching your data.


{% include links.html %}
