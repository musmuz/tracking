# Change Log

### Versioning
###1.2.9
- Add Teltonika tracker service and triplist endpoint.

### 1.2.5
###Features
 - Add a vehicle report service to persist report information to the database.
 - Add a vehicle report controller to access the daily reports
 - Add unit tests to test the above functionalities
### 1.2.0
###Features
- Add a vehicle report collection to record daily the number and list of vehicles not reporting.

### 1.1.10
- Check that the vehicle exists, before we retrieve it's registration number. 

### Version 1.0.22
###Features
- Fix bugs on the drive state end point

###Features
- Add driver behavoir data to the payload.

### Version 1.0.19
###Features
- Add Thabiso's email to the reporting email list
- Correct odometer service test errors

### Version 1.0.18
###Features
- Add an endpoint to access the latest odometer readings of all the vehicles
- Add an endpoint to access all readings of a single imei.

### Version 1.0.17
###Features
- Use vehicle managed by moifleet to compile a report of the vehicles that reported in the 
     past 24 hours. Taskid: MOIT-90
     
### Bug Fixes
- Fix null pointer exception

## Version 1.0.16
### Features
- Added odometer collection to record all new odometer records so that we have a historical  data. Taskid: MOIT-85
     

### Bug Fixes    


## Version 1.0.15 ####

### Features:
- add shedlock lock to prevent scheduled @Scheduled annotations from being executed repeatedly.

### Bug Fixes           
           
