package fr.ups.co_lendar.helpers;

import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import fr.ups.co_lendar.EventRequest;
import fr.ups.co_lendar.fragments.NotificationFragment;

public class Request {

    public enum Type{Feedback, Acceptation}

    public enum Object{Event, Group, Follow}

    private String senderID;
    private String receiverID;
    private String message;
    private String eventID;
    private String groupID;
    private String requestID;
    private Type type;
    private String TAG = "Request";


    /**
     * Only called at creation
     * @param groupID
     * @param senderID
     * @param receiverID
     * @param message
     */
    public Request(String groupID, String senderID, String receiverID, String message){
        this.groupID = groupID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.message = message;
        registerInDatabase();
    }

    /*
     * Only called at creation
     * @param eventID
     * @param senderID
     * @param receiverID
     */
    //TODO: Refactor those 2 constructors to be different
    /*
    public Request(String eventID, String senderID, String receiverID){
        this.eventID = eventID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        registerInDatabase();
    }

    public Request(String senderID, String receiverID, String message){ //Follow request ?
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.message = message;
        registerInDatabase();
    } */

    public String getSenderID(){
        return this.senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public String getMessage(){
        return this.getMessage();
    }

    public String getEventID() {
        return eventID;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
//TODO: refactor
    /*
    public String getTopicName(){
        if(getEventID() != null){
            return getEventID().getName();
        } else if(getGroupID() != null){
            /*issue here: the requests store the groupID, not the group itself so if you want to get the
            you have to get the group first
            //return getGroupID().getName();
        }
        return ""; //Follow ?
    } */

    public void acceptRequest(){
        //Modify database & delete request from database
        sendFeedback(true);
    }

    public void refuseRequest(){
        //Delete quest from database
        sendFeedback(false);
    }


    /**
     * Create a notification fragment corresponding to the request (Event, Group, Feedback)
     * @return
     */
    public NotificationFragment createFragment(){
        if(this.eventID != null){
            return new EventRequest(this);
        } else if(this.groupID != null){
            //Display group request
            return null;
        } else {
            //Display feedback
            return null;
        }
    }

    private void registerInDatabase(){

        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

        Map<String, java.lang.Object> request = new HashMap<>();
        if (this.message != null) {
            request.put("message", this.message);
        }
        if (this.groupID != null) {
            request.put("groupID", this.groupID);
        }
        if (this.eventID != null) {
            request.put("eventID", this.eventID);
        }
        request.put("senderID", this.senderID);
        request.put("receiverID", this.receiverID);

        mFirestore.collection("requests")
                .add(request)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }

    public void removeFromDatabase(){
        if (requestID != null) {
            FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
            mFirestore.collection("requests").document(this.requestID)
                    .delete()
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully deleted!"))
                    .addOnFailureListener(e -> Log.w(TAG, "Error deleting document", e));
        }
    }

    /**
     * Send feedback to the user through the database
     * @param accepted has the request been accepted
     */
    private void sendFeedback(boolean accepted){

    }
}
