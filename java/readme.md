The program animalObservationRegistry is for creating, reading, updating and deleting observations of animals in the future, after the year 2337; space travel is a thing, so each observation can be in a different planet.

Each observation has several objects and variables such as: 

    - id of the observation
    
    - name of the observation
    
    - an animal object, either reptile, amphibian or bird
    
    - a planet object where it was seen
    
    - a picture of the observation
    
    - time and date of the observation
    
    - comment about the observation


The program parkingRental is for renting parkingspots from other users; a users can rent out their spots or rent spots from others. It's a lot of code, used SOLID principles and a bunch of tests, unit and integration testing.

There are three different user types:

    - user: a normal user with no elevated rights. Can only perform actions on their own acccount. Can create a parkinglot with parkingspots to rent out to other users. A user can also just rent from other users without renting out anything.
    
    - manager: an employee at the service that has elevated system rights. They work in customer service and can perform actions on behalf of normal users.
    
    - administrator: full rights.
    
 
