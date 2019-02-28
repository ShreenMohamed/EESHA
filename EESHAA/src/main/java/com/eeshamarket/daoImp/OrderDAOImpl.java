package com.eeshamarket.daoImp;

import com.eeshamarket.entity.Product;
import com.eeshamarket.model.CartLineInfo;
import com.eeshamarket.dao.ProductDAO;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.eeshamarket.dao.OrderDAO;
import com.eeshamarket.entity.Order;
import com.eeshamarket.entity.OrderDetail;
import com.eeshamarket.model.CartInfo;
import com.eeshamarket.model.OrderDetailInfo;
import com.eeshamarket.model.OrderInfo;

@Repository
@Transactional
public class OrderDAOImpl implements OrderDAO {
 
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private ProductDAO productDAO;

    private int getMaxOrderNum() {
        String sql = "Select max(o.orderNum) from " + Order.class.getName() + " o ";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        Integer value = (Integer) query.uniqueResult();
        if (value == null) {
            return 0;
        }
        return value;
    }
    
    public void saveOrder(CartInfo cartInfo) {
        int orderNum = this.getMaxOrderNum() + 1;
        cartInfo.setOrderNum(orderNum);
        
    	   Order order = new Order();
           order.setId(UUID.randomUUID().toString());
           order.setCustomerName(cartInfo.getCustomerInfo().getName());
           order.setCustomerAddress(cartInfo.getCustomerInfo().getAddress());
           order.setCustomerEmail(cartInfo.getCustomerInfo().getEmail());
           order.setCustomerPhone(cartInfo.getCustomerInfo().getPhone());
           order.setAmount(cartInfo.getAmountTotal());
           order.setOrderDate(new Date());
           order.setOrderNum(orderNum);      
           sessionFactory.getCurrentSession().save(order);

           List<CartLineInfo> lines = cartInfo.getCartLines();
           for (CartLineInfo linee : lines) {
         	 OrderDetail orderDetail=new OrderDetail();
           	orderDetail.setId(UUID.randomUUID().toString());
      	    orderDetail.setOrder(order);
           	orderDetail.setAmount(linee.getAmount());
            orderDetail.setPrice(linee.getProductInfo().getPrice());
     	    orderDetail.setQuanity(linee.getQuantity());

     	    String code = linee.getProductInfo().getCode();
            Product product = productDAO.findProduct(code);


            orderDetail.setProduct(product);
         	sessionFactory.getCurrentSession().save(orderDetail);    
}
           cartInfo.setOrderNum(orderNum);
       
    }
    
    public List<OrderInfo> listOrderInfo1() {
        String sql = " from " + Order.class.getName() + " order by orderNum desc";
    	List list = sessionFactory.getCurrentSession().createQuery(sql).list();
		return list;
    }
    
    public Order findOrder(String orderId) {
        return (Order) sessionFactory.getCurrentSession().get(
        		Order.class, orderId);
    }
 
    public OrderInfo getOrderInfo(String orderId) {
        Order order = this.findOrder(orderId);
        if (order == null) {
            return null;
        }
        return new OrderInfo(order.getId(), order.getOrderDate(), 
                order.getOrderNum(), order.getAmount(), order.getCustomerName(), 
                order.getCustomerAddress(), order.getCustomerEmail(), order.getCustomerPhone());
    }
 
        
  public List< OrderDetailInfo> listOrderDetailInfos(String orderId) {
        String sql = "Select new " + OrderDetailInfo.class.getName() 
                + "(d.id, d.product.code, d.product.name ,d.product.category.name ,d.quanity,d.price,d.amount) "
                + " from " + OrderDetail.class.getName() + " d "
                + " where d.order.id = :orderId ";
 
        Session session = this.sessionFactory.getCurrentSession();
 
        Query query = session.createQuery(sql);
        query.setParameter("orderId", orderId);
 
        return query.list();
    }
   
}
