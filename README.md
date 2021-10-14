# README #

The following is a summary of the steps that need to be followed in order for the services swarm to be up and running.

### What is this repository for? ###

* Quick summary
This MOITRACK SERVICES DOCKER SWARM communicates with the tracking unit in order to save the incoming data to the backend database, which is theen sent to any subscribers via the vernemq message broker, and is viewable via the moitrack UI, and the backend database is accessible via the API service.


### Versioning
1.2.8
- Fix bug with mongo properties not commented out for PROD deployment.

1.0.15
```
We created endpoints for Teltonika Tracking units.
``` 

### How do I get set up?

* Dependencies
  In order to run this, docker and docker-machine need to both be installed. Docker needs to be running.
  For local swarm tests, you will also need to have virtualbox running.

* Deployment instructions
Deploying is as easy as running the shell script.

```bash
    /bin/bash local-create-swarm.sh
```

### Contribution guidelines ###

### Who do I talk to? ###

* Moifleet/@nguni52
