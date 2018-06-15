package until;

import java.io.Serializable;

/**
 * Created by mango on 2018/4/29.
 */

public class User implements Serializable {
    public long id ;
    public String password ;
    public int sex ;
    public int age ;
    public int state;
    public String name ;
    public String mails;
    public String usertext ;
    public String address;
    public String question;
    public String answer;
    public String headImage;
    public long  linktable;
    public LinkUser linUser;
}
