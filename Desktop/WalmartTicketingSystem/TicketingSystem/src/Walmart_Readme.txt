Building the Application

The project can be build using Maven.
To build the application, right click on the TicketingSystem project and goto Run as -> Maven install. This builds the application and runs all the unit and integration tests. It creates the jar.
To build the application from the command prompt. Type "mvn package"
To run only the integration tests from the command prompt, type "mvn test -P IntrTest" when IntrTest is the profile created to run only the tests. The profile includes only the test having the Integration Test category.

Assignment

The application has the Controller, Service and repository layer.

The controller has the request mapping URI's defined for each method. The findAllSeats() method is implemented and its integration test done.
The controller.hodSeats creates thread pool and submits task of holding the threads. IF the task is completed within 120 seconds, the held seats are reserved. E

The Ticket Service has the following methods:-	
	1. findAllSeats - Return all seats 
	2. findAllAvailableSeats - Return all available seats
	3. numSeatsAvailable - number of available seats
	4. findAndHoldSeats - hold seats
	5. findAllAvailableSeatsByLevelId - Return all available seats for a specified seat level
	6. reserveSeats - reserve the seats which are held
	7. findAllAvailableSeats - Return all available seats for all levels
	8. findAllSeatsByLevel - Return all seats for a specified level, hold and available
	
Service - In the service, the business logic exists. The service and the repository is abstracted through interfaces.
The list of seats, seat holds and the seat level information is stored in ConcurrentHashMap to achieve concurrency with multiple threads.	
The Seat and SeatLevel classes have overloaded equals and hashCode methods. 

	

Assumptions

 - For the purposes of the assignment, real-time data store is not used, instead in-memory storage is used. The repository layer, the in-memory data is constructed in the constructor. 
 - The method signatures of the Controller class is completed and only one method is implemented
 - The integration Test class is configured and only one test case is defined.
 - The user can only specify 2 levels of seats for hold. e.g. Orchestra and Balcony 1. One of the level is optional. If only one level is specified then all seats are held at that level provided they are available.
 - If 2 seat levels are specified for holding, then the highest level seats are booked first then the lower level.
 - If the number of seats to be held is greater than available, an exception is thrown and error code generated.
 - Only seats that are on hold can be reserved.
  
Limitations 
- There is no UI
- Application can be tested only through Unit test and integration test
- Only one Rest API is created and tested.


