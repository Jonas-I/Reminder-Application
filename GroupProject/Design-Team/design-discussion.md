# Design Discussion

## Design 1 (Daniel Rand):

![](images/Daniel.png)

### Pros:
    1. Very detailed oriented in a way that all the method names and attributes are clear/understandable. -T.R
    2. The relationships between (User and Reminder) and (Reminder and ReminderList) justify the requirements between them. -T.R
    3. Straightforward and simple design that consists of several methods that would be implemented into the application. -J.I
    4. The User class’ attributes allows for the application to log into a device with their information rather than to a designated device. -J.I
	5. Very fleshed out diagram with method names that are clear how they interact with other classes. - J.K


### Cons:
    1. Although there are methods within the User class to create ReminderList, the design shows no relationship between Users and the ReminderLists they create. -T.R
    2. I believe that within the design, you could possibly include utility classes to handle arbitrary values of Time, Date, Location. -T.R
    3. One of the requirements asks for a hierarchical format for Reminders where their type and names are seperate which could be added to the design. -T.R
    4. There seems to be no relationship between the User and the ReminderList to allow the User to manage it. -J.I
	5. Can use a connection between the User class and ReminderList class so User can create ReminderLists directly like they do Reminders. - J.K

## Design 2 (Nana Kodjo Acquah):

![](images/Kodjo.png)

### Pros:
    1. I like how you included a Priority class to check whether or not a Reminder is important or not. -T.R
    2. Very nice use of the dependency relationships along with aggregations to explicitly show that the ReminderList has Reminders. -T.R
    3. The explanations in the relationships is helpful to understand the design of the application. -J.I
    4. The idea of a User directly receiving an Alert seems to be better than a User receiving an Alert from a Reminder. -J.I
	5. Nice use of relationships to show how everything is connected. -J.K

### Cons:
    1. There is redundant information within the design where the methods within the User class seem to be classes of their own. -T.R
    2. This design could be refactored more to a point where we can get rid of 6 exisiting classes inorder to have a more compact, crisp layout. -T.R
    3. The design appears to be convoluted and could be simplified. Instead of creating new classes, it can be handled as a method. -J.I
	4. The extra classes to create, edit, and delete can just be methods in the ReminderList and Reminder class. -J.K
	5. Can use more information of what kind of data is stored in the database. -J.K


## Design 3 (Torendra Rasik):

![](images/Torendra.png)

### Pros:
    1. The design displays all of the possible classes and necessary relationships needed to complete the requirements. -J.I
    2. The use of a Utility class gets rid of the need to create separate classes to get the Date/Time/Location for an Alert. -J.I
	3. Having a separate class for ReminderType can be useful for keeping track of what types are available. -J.K

### Cons:
    1. The hierarchical aspect in the requirements could possibly be handled in the Reminder class instead of in its own separate class. -J.I
    2. The use of a ReminderList in the Database class may not be necessary as it should only contain Reminders. -J.I
	3. Give the User class methods to create and modify ReminderLists like how they can for Reminders. -J.K


## Design 4 (Jeffrey Kim):

![](images/Jeffrey.png)

### Pros:
    1. Very nice use of including a Utility class to show we can get Time, Date, and Location from the Android OS. -T.R
    2. Very compact design that gives enough information to answer all requirements, while clearly showing the inheritance of Reminder to List. -T.R
    3. Simple and clear design that meets the requirements of the application. -J.I
    

### Cons:
    1. I do believe we can split the type and name of the Reminder into two seperate classes where we can save them correspondingly and lets us display them in the UI easier. -T.R
    2. Maybe we shouldn't say that there's an aggregation between a Reminder and the Database because the database basically saves everything within the App, so the App is just dependant of the database. -T.R
    3. The User may need to be able to create a Reminder on its own and a List on its own. The current design implies that the User cannot create a Reminder without first creating a List. -J.I

## Design 5 (Jonas Improgo):

![](images/Jonas.png)

### Pros:
    1. I like how you used a Persistence class as the middleware between the ReminderList and the Database to show all the saving is done through that class. -T.R
    2. Really good use of the dependency relationship between classes to show that if one changes there may be changes throughout the Application. -T.R
	3. Use of a Persistence class to keep track of saving and deleting information. -J.K

### Cons:
    1. I believe the User Interface class shouldn't be there because it's intuitive, therefore we can move the methods within it to a User class. -T.R
    2. I believe we could replace the composition relationships between some classes e.g ReminderList and Reminder with Aggregation relationships to show that a ReminderList has Reminders. -T.R
    3. We could see the OS to retrieve the Date, Time, and Location for us instead of creating classes for them as Jeffrey did. -T.R
	4. Instead of the UserInterface, a User class can be made to be able to create and modify Reminders and ReminderLists. -J.K

# Team Design:

# Summary:



