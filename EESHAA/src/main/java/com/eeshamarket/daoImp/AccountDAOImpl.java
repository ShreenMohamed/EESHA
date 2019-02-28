package com.eeshamarket.daoImp;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eeshamarket.dao.AccountDAO;
import com.eeshamarket.entity.Account;
@Repository
@Transactional
public class AccountDAOImpl implements AccountDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public Account findAccount(String userName ) {    
    	return (Account) sessionFactory.getCurrentSession().get(
        		Account.class, userName);
    }
 
}