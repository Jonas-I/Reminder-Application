 # Reminders Android Application
## 370: Software Engineering
## Authors/Developers: Daniel Rand, Jonas Improgo, Torendra Rasik, Jeffrey Kim, Nana Kodjo Acquah

This Application creates and manages Reminders for a User when they need to be reminded on sensitive or important information at a specific time.

## How to Use

1. When the Application is launched, the User will be present a User interface to create and manager their Reminders.
    - When the User presses/clicks on the create button (Plus Sign) they will be taken to a new screen where they will be able to create a new Reminder.
    - The Reminder to be created will be held to certain constaints. It must have a specific type e.g "Appointments", and a description e.g "Dentist Appointment"
    - The User has the option to add an Alert to their Reminder to specify a time and date they want to be reminded by their phone.
    - If there are any lists saved within the User's Application, on start up the already created lists will be displayed in alphabetical order.

2. When the User has created their Reminders, they will be able to manage those Reminders.
    - The Reminders will be displayed based on their Types e.g all Reminders with the type of "Appointments" will be listed under the List Appointments.
    - To view all Reminders under that list, the User must press/click on the list itself and all the Reminders of that type will be shown under that list. 
    - The User will be able to check off reminders by pressing the checkbox next to a specific Reminder
    - Once the User checks off a Reminder, it will turn to an inactive state meaning that the Reminder is done and the User no longer wants to be notified or track that Reminder.

3. Deletion of Reminders/Reminder Lists
    - If a User wishes to delete a specific Reminder, they should navigate to the Reminder within their lists and they will see a red "X".
    - Once pressed/clicked, the User will see that the selected Reminder will be deleted forever from their Application
    - If a User wishes to delete an entire List of Reminders, they should navigate to the specific list they want to delete
    - Once there, they will see a 3 Dots button to the right of the list.
    - When that button is clicked, a popup will appear with the option to delete List
    - Once "Delete List" is clicked/pressed, the list will be deleted forever and the User will notice a message on the bottom stating that the list was deleted.

4. Error Handling
    - If the User tries to create a new Reminder without specifying a type, they will notice a message on the bottom of the screen saying that they need to input a value for both the type and description of a Reminder
    - If the User tries to create a new Reminder without specifying a description, they will notice a message on the bottom of the screen saying that they need to input a value for both the type and description of a Reminder
    - If the user clicks Cancel while on the Create Reminder screen, they will be taken back to the Main Screen where a message will be shown on the bottom, that states the Reminder was not created and was not saved within the database.
    - If the User tries to input an time for their Reminder to be alerted without inputting the date, they will notice a message on the bottom of the screen saying that they need to input a value for both the time and date of the alert. 
    - If the User tries to input a date for their Reminder to be alerted without inputting the time, they will notice a message on the bottom of the screen saying that they need to input a value for both the time and date of the alert.