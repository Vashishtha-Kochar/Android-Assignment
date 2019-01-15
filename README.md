# Android-Assignment
Demonstration of my knowledge and usage of Android, Firebase realtime database and Firebase Cloud Functions.

# Tasks

1. Create an Android UI screen (Activity) which displays a list (Recycled View) of 10 users with details (Horizontal Layout) as name and age. You can hardcode the user values in this task.

2. Attach a listener to the firebase realtime database (JSON Tree), the database has a list of 10 users. Listners keeps UI synced with database values, any change in database value is updated at UI in realtime. Please setup your own firebase account and create realtime database. You can manually insert the user details in database from Firebase Admin Console.
In database structure (JSON Tree)
-> ROOT
--> 	USER (Contains list of unique ids)
--->		UniqueID
---->			Name
---->			Age

3. Write Firebase cloud function which triggers on create of a new user in realtime database (tast#2). It removes any user older than latest 10 users.

# Notes
UniqueID that I used was numbers starting from 1 with an increment of one.

The Firebase cloud function has been deployed and runs perfectly
