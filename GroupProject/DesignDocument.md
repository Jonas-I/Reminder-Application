# Design Document

*This is the template for your design document. The parts in italics are concise explanations of what should go in the corresponding sections and should not appear in the final document.*

**Author**: Torendra Rasik

## 1 Design Considerations

*The subsections below describe the issues that need to be addressed or resolved prior to or while completing the design, as well as issues that may influence the design process.*

### 1.1 Assumptions

*Describe any assumption, background, or dependencies of the software, its use, the operational environment, or significant project issues.*

### 1.2 Constraints

*Describe any constraints on the system that have a significant impact on the design of the system.*

### 1.3 System Environment

*Describe the hardware and software that the system must operate in and interact with.*

## 2 Architectural Design

*The architecture provides the high-level design view of a system and provides a basis for more detailed design work. These subsections describe the top-level components of the system you are building and their relationships.*

### 2.1 Component Diagram

![](Design-Team/images/Component.png)

### 2.2 Deployment Diagram

- This diagram seems to be unnecessary in our case because we a designing and creating a simple system with one functionality. This Application will be ran on devices that are running the Android software. It will also be using the onboard Database found in the Android OS rather than something external. Therefore, in terms of deployment it is unnecessary to create a diagram.

## 3 Low-Level Design

*Describe the low-level design for each of the system components identified in the previous section. For each component, you should provide details in the following UML diagrams to show its internal structure.*

### Reminder Manager
- This component accesses the data in the Reminder component in order to provide management instructions for the User.
- It provides the functionality for the User in order to create, delete and edit ReminderLists. It also allows Users to create, delete, select, and re(name) their Reminders.
- This component also allows for Reminders to be saved through the database automatically.

### Reminder
- This component provides the interface that is needed in order for the Reminder Manager to work.
- We have a Reminder class within this component that is created through a hierarchical system. This allows for better creation and grouping of Reminders so that it makes it easier on the User to view and manage their Reminders/ReminderLists
- We have a ReminderList class within this component that allows the User to create as many Lists as they want. It allows for concurrency support for the User in order to manage multiple Lists simultaneously.
- We have a Alert class within this component that if deemed necessary will allow the User to repeat Reminders based on their location, geolocation and also on a specified time. This class works in joint with the Android Operating System, which provides an interface to have data access to the location, geolocation and time of the User.

### Operating System
- This component provides an interface for the Reminders Component
- It allows for data access of the User's location, geolocation, and time 
- It's an on demand system that needs no development as it exists already on the Android Operating System.

### Persistence
- This component provides an interface for the Reminders Component
- This is the one component that is the middle man between Reminders component and the database which allows for automatic saving
- This class allows for the backup saving and on demand saving of Reminders to allow the User to never worry about losing their data.

### Database
- This is the most important component within the Application that holds all the data for the User.
- It provides for automatic saving for the Application through the Persistence component.

### 3.1 Class Diagram

![](Design-Team/images/Team-Design.png)

### 3.2 Other Diagrams
- There is no need for other diagrams as this is a simple application. It has basic functionality of managing Reminders which is shown through the use of the Class Diagram, Use Case Model, and Component diagram. These three diagrams provide more than enough information in order to plan out the construction of the Application.

## 4 User Interface Design
*For GUI-based systems, this section should provide the specific format/layout of the user interface of the system (e.g., in the form of graphical mockups).*
![](Design-Team/images/UserInterface_1.png)

![](Design-Team/images/UserInterface_2.png)
