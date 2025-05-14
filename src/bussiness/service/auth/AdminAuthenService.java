package bussiness.service.auth;

import bussiness.dao.auth.AdminAuthenDAO;

public class AdminAuthenService {
    public static AdminAuthenDAO instance = AdminAuthenDAO.getInstance();

    // có biến đổi dữ liệu gì truước khi đưa qua dao thì bien đổi dưới đây nha
    // chỗ này nè
}
