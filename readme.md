# Digital Animal Zoo

Takes in user input to gather information for a ZooAnimal (animal type, population, natural habitat).
Data will be stored in a file that we will write to as well as read from.
Information previously stored in the file should not be overwritten.

## Some Features
- Notifies user that an animal already exists if they try to add one that is already on the list and asks them to try again
- Animals are stored in alphabetical order in the file
- Able to add new animals to the file
- Can read all the animals available

Notes
---
In regards to the part where the information previously stored in file is not overwritten, it is assumed that as long as there is no information lost, the information is considered not overwritten. In this case, the file is completely overwritten each time a new animal is added in order to store the animals in alphabetical order within the file. We can only add to the file a new animal, and it is not possible to delete or alter any of the existing information that was stored.
