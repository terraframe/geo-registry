---
title: Deploying the GeoRegistry
keywords: georegistry, ansible, sas, docker
last_updated: July 3, 2016
tags: [getting_started]
summary: "Covers the basics of deploying the GeoRegistry as a SAS product."
sidebar: mydoc_sidebar
permalink: mydoc_deploying.html
folder: mydoc
---

TerraFrame uses Ansible scripts to deploy our platform. This allows us to automate all of the tedious tasks like installing Tomcat, Postgres, etc, and allows us to deploy production servers in predicable ways. Our Ansible scripts are open-source and can be found on our [geoprism-cloud](https://github.com/terraframe/geoprism-cloud) github repository.

Official releases of the GeoRegistry are deployed by the TerraFrame team to our online Nexus repository (<http://nexus.terraframe.com>). You will need to obtain credentials to push to this Nexus server, however the public does have read-only access. By default our ansible scripts are set to use 'LATEST' (which is a Maven version keyword). When LATEST is sent to our nexus server, the server will send you the latest official release of the GeoRegistry. We recommend, however, that you put a real version number so that you can keep track of what your current GeoRegistry version is, should there be any issues or incompatibilities with the latest GeoRegistry version.

If you want to do a release from a local build you will have to modify the Ansible scripts yourself as this is not currently supported out-of-the box.

# Hardware Requirements

We recommend using an r4.large server as the GeoRegistry tends to be memory intensive. We are using io1 800 IOPS 100GB hard drives. If you decide to use different hardware make sure to tweak the settings in your inventory file to account for the different hardware.

# Our Docker Containers

Our docker containers are pulled from our AWS ECS server. These containers automate for us all of the manual installation of Postgres, Tomcat, Ubuntu, etc. They also come pre-bundled with our SSL certificates. There is a public and a private flavour of our docker containers, the public contains a self-signed SSL certificate (which will display an SSL error when connecting, because its self-signed), and the private container comes with our private SSL geoprism.net wildcard certificate. The private docker container is only for usage on our SAS® offering. If you wish to use your own SSL certificate, you may build your own version of our public docker image (found [here](https://github.com/terraframe/geoprism-cloud/tree/master/docker/web-public)) and include your SSL certificate in the Dockerfile. Your inventory file will allow you to specify your custom docker image.

# Installing Ansible
<!-- Follow the official instructions [here](http://docs.ansible.com/ansible/intro_installation.html) -->

The current ansible scripts only work on this commit: `a236cbf3b42fa2c51b89e9395b47abe286775829`. Check it out and then run `source ansible-dev/ansible/hacking/env-setup`. This will get around psycop2g errors.

# Deploying with Ansible
Here is how to deploy an existing GeoRegistry release using Ansible.

TODO : Specifying your own project.json file

1. Clone the [geoprism-cloud](https://github.com/terraframe/geoprism-cloud) git repository.
2. Make sure that ports 5432 and 22 are properly forwarded from the Ansible deployment machine to your remote production server. If you want to deploy the georegistry to the same server running the ansible script, you may try putting 'localhost' into your inventory file (see step 3).
3. Create a directory called "inventory" in geoprism-cloud/ansible. Download [this file](https://raw.githubusercontent.com/terraframe/geoprism-cloud/master/examples/example-georegistry-inventory.ini) and place it inside the inventory directory. This inventory file contains all the sensitive security related information for your server. For example, you may change the database password here and you also get to specify whether or not you want to wipe all data from the database as part of the deploy. You also need to specify the URL of your server that you wish to deploy to. Your inventory file is an extremely flexible resource and should be checked before every deploy.
4. Back up your server before every deploy.
5. The geoprism-cloud ansible directory contains configurations for deploying many different geoprism apps, however the one we care about is georegistry.yml. Here's what a deployment command looks like: `ansible-playbook -i ./inventory/your_server_name.ini georegistry.yml`. Execute this command from the geoprism-cloud/ansible directory on your development machine or CI server (not the production server itself).


{% include links.html %}
