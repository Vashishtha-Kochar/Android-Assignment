import * as functions from 'firebase-functions'
import * as admin from 'firebase-admin'
admin.initializeApp()

// userId starts from 1 and increments by 1, everytime a new user is added
export const onUserAdd = functions.database.ref(`/users/{userID}`)
.onCreate((snapshot, context) => {
    return removeOld(Number(snapshot.key))
})

// Loop through the entries and remove the oldest if it doesn't belong to latest 10 users
// latestId is the id of the latest entry added to the database
async function removeOld(latestId : number){

    // Flag is used to terminate the loop when the current oldest user belongs to latest 10 users
    var flag = 1
    const dbref = admin.database().ref('users')
    console.log("Entered removeOld")

    while(flag == 1){

        // The database is ordered by age and snapshot of the oldest entry is taken
        await dbref.orderByChild("age").limitToLast(1)
        .once("value").then( async function(usersSnapshot) {
            for(const userid in usersSnapshot.val()){
                // userid is the id of the oldest user
                
                if(Number(userid) > (latestId - 10)){

                    // Termiate the loop
                    flag = 0
                }
                else{

                    //Remove the user
                    await dbref.child(String(userid)).remove()
                    .then(function() {
                        console.log("ID " + String(userid) + " remove succeeded.")
                    })
                    .catch(function(error) {
                        console.log("ID " + String(userid) + " remove failed: " + error.message)
                    });
                }
            }
        }).catch(error => {
            console.log(error)
        })
    }
}