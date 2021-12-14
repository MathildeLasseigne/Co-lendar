package fr.ups.co_lendar.helpers;

import fr.ups.co_lendar.EventRequest;
import fr.ups.co_lendar.FirebaseCallback;
import fr.ups.co_lendar.FollowRequest;
import fr.ups.co_lendar.GroupRequest;
import fr.ups.co_lendar.RequestFeedback;
import fr.ups.co_lendar.fragments.NotificationFragment;

import android.util.Log;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Request {

    public enum Object{Event, Group, Follow}

    private Object object;
    private User sender;
    private User receiver;
    private String message = "";
    private Event event;
    private Group group;
    private boolean feedback = false;
    private boolean accepted = false;
    private String senderID = "";
    private String receiverID = "";
    private String eventID = "";
    private String groupID = "";
    private String requestID = "";

    private String TAG = "Request";

    /**Have the objects been mapped to their id ?*/
    private boolean objectsMapped = false;

    public Request(/*DataBase*/){
        //Choose the type;
    }

    /**
     * Only called at creation
     * Register the request in the database automatically
     * @param objectID
     * @param senderID
     * @param receiverID
     * @param message
     */
    public Request(Object object, String objectID, String senderID, String receiverID, String message){
        this(object, objectID, senderID, receiverID, message, false, false);
    }

    /**
     * Only called at creation
     * Register the request in the database automatically
     * @param objectID
     * @param senderID
     * @param receiverID
     * @param message
     * @param feedback if is a feedback
     * @param accepted if is a feedback and is accepted
     */
    public Request(Object object, String objectID, String senderID, String receiverID, String message, boolean feedback, boolean accepted){
        if(object == Object.Group){
            this.groupID = objectID;
            this.senderID = senderID;
            this.receiverID = receiverID;
            this.message = message;
        } else if(object == Object.Event){
            this.eventID = objectID;
            this.senderID = senderID;
            this.receiverID = receiverID;
        } else if(object == Object.Follow){
            this.senderID = senderID;
            this.receiverID = receiverID;
            this.message = message;
        }
        this.feedback = feedback;
        this.accepted = accepted;

        registerInDatabase();
    }


    public void mapIdToObject(){
        if(!this.senderID.isEmpty()){
            User user = new User(new FirebaseCallback() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(java.lang.Object data) {
                    sender = (User) data;
                }

                @Override
                public void onFailed(DatabaseError databaseError) {
                    Log.v(TAG, "Error while loading the sender user");
                }
            }, senderID);
        }
        if (!this.receiverID.isEmpty()){
            new User(new FirebaseCallback() {
                @Override
                public void onStart() { }

                @Override
                public void onSuccess(java.lang.Object data) {
                    receiver = (User) data;
                }

                @Override
                public void onFailed(DatabaseError databaseError) {
                    Log.v(TAG, "Error while loading the receiver user");
                }
            }, this.receiverID);
        }
        if (!this.eventID.isEmpty()){
            new Event(new FirebaseCallback() {
                @Override
                public void onStart() { }

                @Override
                public void onSuccess(java.lang.Object data) {
                    event = (Event) data;
                }

                @Override
                public void onFailed(DatabaseError databaseError) {
                    Log.v(TAG, "Error while loading the event");
                }
            }, this.eventID);
            this.object = Object.Event;
        } else if (! this.groupID.isEmpty()){
            new Group(new FirebaseCallback() {
                @Override
                public void onStart() { }

                @Override
                public void onSuccess(java.lang.Object data) {
                    group = (Group) data;
                }

                @Override
                public void onFailed(DatabaseError databaseError) {
                    Log.v(TAG, "Error while loading the group");
                }
            }, this.groupID);
            this.object = Object.Group;
        } else {
            this.object = Object.Follow;
        }

        this.objectsMapped = true;
    }

    public Object getObject() {
        if(! objectsMapped){
            mapIdToObject();
        }
        return this.object;
    }

    public User getSender(){
        if(! objectsMapped){
            mapIdToObject();
        }
        return this.sender;
    }

    public User getReceiver() {
        if(! objectsMapped){
            mapIdToObject();
        }
        return receiver;
    }

    public String getMessage(){
        return this.getMessage();
    }

    public Event getEvent() {
        if(!objectsMapped){
            mapIdToObject();
        }
        return event;
    }

    public Group getGroup() {
        if(!objectsMapped){
            mapIdToObject();
        }
        return group;
    }

    public boolean isFeedback() {
        return feedback;
    }

    public boolean isAccepted() {
        return accepted;
    }


    public String getSenderID(){
        return this.senderID;
    }

    public String getReceiverID() {
        return receiverID;
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

    public void setIsAccepted(boolean isAccepted) {
        this.accepted = isAccepted;
    }

    public void setIsFeedback(boolean isFeedback) {
        this.feedback = isFeedback;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getObjectId(){
        if(getObject() == Object.Event){
            return getEventID();
        } else if(getObject() == Object.Group){
            return getGroupID();
        }
        return "";
    }

    public String getTopicName(){
        if(getObject() == Object.Event){
            return getEvent().getName();
        } else if(getObject() == Object.Group){
            return getGroup().getName();
        }
        return "to follow them";
    }

    public void acceptRequest(){
        if(getObject() == Object.Event){
            this.getEvent().addUser(getReceiverID(), new FirebaseCallback() {
                @Override
                public void onStart() { }

                @Override
                public void onSuccess(java.lang.Object data) { }

                @Override
                public void onFailed(DatabaseError databaseError) {
                    Log.v(TAG, "Error while inserting event with new participant in database");
                }
            });
        } else if(getObject() == Object.Group){
            this.getGroup().addUser(getReceiverID(), new FirebaseCallback() {
                @Override
                public void onStart() { }

                @Override
                public void onSuccess(java.lang.Object data) { }

                @Override
                public void onFailed(DatabaseError databaseError) {
                    Log.v(TAG, "Error while inserting group with new member in database");
                }
            });
        }
        removeFromDatabase();
        sendFeedback(true);
    }

    public void refuseRequest(){
        removeFromDatabase();
        sendFeedback(false);
    }


    /**
     * Create a notification fragment corresponding to the request (Event, Group, Feedback)
     * @return
     */
    public NotificationFragment createFragment(){
        NotificationFragment fg;
        if(isFeedback()){
            fg = new RequestFeedback();
        } else {
            if(getObject() == Object.Event){
                fg = new EventRequest();
            } else if(getObject() == Object.Group){
                fg = new GroupRequest();
            } else {
                fg = new FollowRequest();
            }
        }
        fg.setParameters(this);
        return fg;
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

        request.put("feedback", this.feedback);
        request.put("accepted", this.accepted);

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
        Request feedback = new Request(getObject(), getObjectId(), getReceiverID(), getSenderID(), "", true, accepted);
    }
}
