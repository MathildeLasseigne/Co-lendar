package fr.ups.co_lendar.helpers;

import fr.ups.co_lendar.EventRequest;

public class Request {

    public enum Type{Feedback, Acceptation}

    public enum Object{Event, Group, Follow}

    private User sender;
    private User receiver;

    private String message;

    private Event event;
    private Group group;

    private Type type;

    public Request(/*DataBase*/){
        //Choose the type;
    }

    /**
     * Only called at creation
     * @param group
     * @param sender
     * @param receiver
     * @param message
     */
    public Request(Group group, User sender, User receiver, String message){
        this.group = group;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        registerInDataBase();
    }

    /**
     * Only called at creation
     * @param event
     * @param sender
     * @param receiver
     */
    public Request(Event event, User sender, User receiver){
        this.event = event;
        this.sender = sender;
        this.receiver = receiver;
        registerInDataBase();
    }

    public Request(User sender, User receiver, String message){ //Follow request ?
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        registerInDataBase();
    }

    //Follow ?

    public User getSender(){
        return this.sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public String getMessage(){
        return this.getMessage();
    }

    public Event getEvent() {
        return event;
    }

    public Group getGroup() {
        return group;
    }

    public String getTopicName(){
        if(getEvent() != null){
            return getEvent().getName();
        } else if(getGroup() != null){
            return getGroup().getName();
        }
        return ""; //Follow ?
    }

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
        if(this.event != null){
            return new EventRequest(this);
        } else if(this.group != null){
            //Display group request
            return null;
        } else {
            //Display feedback
            return null;
        }
    }

    private void registerInDataBase(){

    }

    public void removeFromDatabase(){

    }

    /**
     * Send feedback to the user through the database
     * @param accepted has the request been accepted
     */
    private void sendFeedback(boolean accepted){

    }
}
