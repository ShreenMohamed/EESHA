package com.eeshamarket.dao;

 
import java.util.List;

import com.eeshamarket.model.CartInfo;
import com.eeshamarket.model.OrderDetailInfo;
import com.eeshamarket.model.OrderInfo;
 
public interface OrderDAO {
 
    public void saveOrder(CartInfo cartInfo);
    public OrderInfo getOrderInfo(String orderId);
    public List<OrderDetailInfo> listOrderDetailInfos(String orderId);
	public List<OrderInfo> listOrderInfo1();
 
}
