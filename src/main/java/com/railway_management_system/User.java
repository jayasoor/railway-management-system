
package com.railway_management_system;



/**
 *
 * @author Shamika Tissera
 */
public abstract class User {
    private String id;
    private String username;
    private String password;    
    private String first_name;
    private String last_name;
    private String email;
    private String mobile;
    
    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
