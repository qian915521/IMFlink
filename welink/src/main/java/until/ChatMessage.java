package until;

import java.io.Serializable;

/**
 * Created by mango on 2018/5/2.
 */

public class ChatMessage implements Serializable {
    public long  id ;
    public String name ;
    public String  content ;
    public String  time;
    public  long  toId ;
    public  String toName ;

    public long getId(){
        return id;
    }

    public String getName(){
        return  name;
    }

    public  String getContent(){
        return content ;
    }

     public  String   getTime(){
        return time ;
    }

    public  long  getToId(){
        return  toId ;
    }

    public  String getToName(){
        return  toName ;
    }
}
