package until;

import java.io.Serializable;

/**
 * Created by mango on 2018/4/29.
 */

public class SocketMessage implements Serializable {
    public  String  what ; //表示Message的类型
    public  String  state ;
    public  String type ;
    public  long  id;
    public  User user =new User() ;
    public  ChatMessage chatMessage=new ChatMessage();
    public  LinkUser[] linkTable ;
    public  String time;
    public  LinkUser linkUser;

}
