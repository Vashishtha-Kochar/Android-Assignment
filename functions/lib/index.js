"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : new P(function (resolve) { resolve(result.value); }).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
Object.defineProperty(exports, "__esModule", { value: true });
const functions = require("firebase-functions");
const admin = require("firebase-admin");
admin.initializeApp();
// userId starts from 1 and increments by 1, everytime a new user is added
exports.onUserAdd = functions.database.ref(`/users/{userID}`)
    .onCreate((snapshot, context) => {
    return removeOld(Number(snapshot.key));
});
// Loop through the entries and remove the oldest if it doesn't belong to latest 10 users
// latestId is the id of the latest entry added to the database
function removeOld(latestId) {
    return __awaiter(this, void 0, void 0, function* () {
        // Flag is used to terminate the loop when the current oldest user belongs to latest 10 users
        var flag = 1;
        const dbref = admin.database().ref('users');
        console.log("Entered removeOld");
        while (flag == 1) {
            // The database is ordered by age and snapshot of the oldest entry is taken
            yield dbref.orderByChild("age").limitToLast(1)
                .once("value").then(function (usersSnapshot) {
                return __awaiter(this, void 0, void 0, function* () {
                    for (const userid in usersSnapshot.val()) {
                        // userid is the id of the oldest user
                        if (Number(userid) > (latestId - 10)) {
                            // Termiate the loop
                            flag = 0;
                        }
                        else {
                            //Remove the user
                            yield dbref.child(String(userid)).remove()
                                .then(function () {
                                console.log("ID " + String(userid) + " remove succeeded.");
                            })
                                .catch(function (error) {
                                console.log("ID " + String(userid) + " remove failed: " + error.message);
                            });
                        }
                    }
                });
            }).catch(error => {
                console.log(error);
            });
        }
    });
}
//# sourceMappingURL=index.js.map