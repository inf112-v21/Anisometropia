package multiplayer;

import java.time.LocalTime;

public class Message {
    String sender = "", text = "";
    LocalTime timeStamp;
    public Message(){};
    public Message(String sender, String text){
        this.sender = sender;
        this.text = text;
    }

    public String getMessage() {
        return "["+ timeStamp.toString().substring(0,5) + " " + sender +"] " + text + "\n";
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
