package bussiness.entity;

public class Admin {
    private int admin_id;
    private String admin_username;
    private String admin_password;

    public Admin(String admin_username, String admin_password) {
        this.admin_username = admin_username;
        this.admin_password = admin_password;
    }
}
