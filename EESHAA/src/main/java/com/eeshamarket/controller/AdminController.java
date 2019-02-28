package com.eeshamarket.controller;

import com.eeshamarket.dao.CategoryDAO;
import com.eeshamarket.dao.OrderDAO;
import com.eeshamarket.dao.ProductDAO;
import com.eeshamarket.model.CategoryInfo;
import com.eeshamarket.model.OrderDetailInfo;
import com.eeshamarket.model.OrderInfo;
import com.eeshamarket.model.ProductInfo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eeshamarket.Validator.CategoryInfoValidator;
import com.eeshamarket.Validator.ProductInfoValidator;


@Controller
@Transactional
@EnableWebMvc
public class AdminController {
 
	@Autowired
    private OrderDAO orderDAO;
	@Autowired
    private ProductDAO productDAO;
    @Autowired
    private CategoryDAO categoryDAO;
    @Autowired
    private ProductInfoValidator productInfoValidator;
    @Autowired
    private CategoryInfoValidator categoryInfoValidator;
 
 
    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
 
        if (target.getClass() == ProductInfo.class) {
            dataBinder.setValidator(productInfoValidator);
            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        }
        if (target.getClass() == CategoryInfo.class) {
            dataBinder.setValidator(categoryInfoValidator);
            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        }
    }
 
    @RequestMapping(value = { "/login" }, method = RequestMethod.GET)
    public String login(Model model) {
 
        return "login";
    }
 
    @RequestMapping(value = { "/accountInfo" }, method = RequestMethod.GET)
    public String accountInfo(Model model) {
 
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userDetails", userDetails);
        return "accountInfo";
    }
 
    @RequestMapping(value = { "/orderList" }, method = RequestMethod.GET)
    public ModelAndView orderList(ModelAndView model, //
            @RequestParam(value = "page", defaultValue = "1") String pageStr) {
        int page = 1;
        try {
            page = Integer.parseInt(pageStr);
        } catch (Exception e) {
        }
        
        int total=5;    
        if(page==1){}    
        else{    
        	page=(page-1)*total+1;    
        }    
        List<OrderInfo> list=orderDAO.listOrderInfo1();  
        model.addObject("orderList", list);
        return model; 
    }
 
    @RequestMapping(value = { "/product" }, method = RequestMethod.GET)
    public ModelAndView product(ModelAndView model, @RequestParam(value = "code", defaultValue = "") String code
    		, @RequestParam(value = "category", defaultValue = "") String name) {
        ProductInfo productInfo = null;

        if (code != null && code.length() > 0) {

            productInfo = productDAO.findProductInfo(code);
        }
        if (productInfo == null) {
            productInfo = new ProductInfo();
            productInfo.setNewProduct(true);
            productInfo.setCategory(name);

        }
        model.addObject("productForm", productInfo);
        model.setViewName("product");
        return model;
    }
 
    @RequestMapping(value = { "/product" }, method = RequestMethod.POST)
    @Transactional(propagation = Propagation.NEVER)
    public ModelAndView productSave(ModelAndView model, 
            @ModelAttribute("productForm") @Validated ProductInfo productInfo, 
            BindingResult result, 
            final RedirectAttributes redirectAttributes) {
 
        if (result.hasErrors()) {
            model.setViewName("product");
            return model;
        }
        try {
            productDAO.save(productInfo);
        } catch (Exception e) {
            String message = e.getMessage();
            model.addObject("message", message);
            model.setViewName("product");
            return model;
 
        }
        model.setViewName("redirect:/categoryList");
        return model;
    }
 
    @RequestMapping(value = { "/order" }, method = RequestMethod.GET)
    public ModelAndView orderView(ModelAndView model, @RequestParam("orderId") String orderId) {

        OrderInfo orderInfo = null;
        if (orderId != null) {
            orderInfo = this.orderDAO.getOrderInfo(orderId);
        }
        if (orderInfo == null) {

            model.setViewName("redirect:/orderList");
            return model;
        }
List<OrderDetailInfo> details = this.orderDAO.listOrderDetailInfos(orderId);
orderInfo.setDetails(details);
model.addObject("orderInfo", orderInfo);
        return model;

    }
    
    
    @RequestMapping(value = { "/category" }, method = RequestMethod.GET)
    public ModelAndView category(ModelAndView model, @RequestParam(value = "name" , defaultValue = "") String name) {
    	
    	CategoryInfo categoryInfo= null;
        if (name != null && name.length() > 0) {
         	categoryInfo = categoryDAO.findCategoryInfo(name);
        }
    	 
    	 if (categoryInfo == null) {
         	categoryInfo = new CategoryInfo();
         	categoryInfo.setNewCategory(true);
         }
    	 
    	  model.addObject("categoryForm", categoryInfo);
          model.setViewName("category");
          return model;
    	 
    }
    

    

    @RequestMapping(value = { "/category" }, method = RequestMethod.POST)
    @Transactional(propagation = Propagation.NEVER)
    public ModelAndView categorySave(ModelAndView model, 
            @ModelAttribute("categoryForm") @Validated CategoryInfo categoryInfo, 
            BindingResult result, 
            final RedirectAttributes redirectAttributes) {
    	
        if (result.hasErrors()) {
            model.setViewName("category");
            return model;
        }
        try {
            categoryDAO.save(categoryInfo);
        } catch (Exception e) {
            String message = e.getMessage();
            model.addObject("message", message);
            model.setViewName("category");
            return model;
 
        }

        model.setViewName("redirect:/categoryList");
        return model;
    }
}