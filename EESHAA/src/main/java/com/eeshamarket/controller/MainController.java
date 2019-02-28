package com.eeshamarket.controller;

 
 
import com.eeshamarket.dao.CategoryDAO;
import com.eeshamarket.dao.OrderDAO;
import com.eeshamarket.dao.ProductDAO;
import com.eeshamarket.entity.Product;
import com.eeshamarket.model.CartInfo;
import com.eeshamarket.model.CategoryInfo;
import com.eeshamarket.entity.Category;
import com.eeshamarket.model.CustomerInfo;
import com.eeshamarket.model.ProductInfo;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.eeshamarket.Utils.Utils;
import com.eeshamarket.Validator.CustomerInfoValidator;


@Controller
@EnableWebMvc
public class MainController {

 @Autowired
 private OrderDAO orderDAO;
 @Autowired
 private ProductDAO productDAO;
 @Autowired
 private CategoryDAO categoryDAO;
 @Autowired
 private CustomerInfoValidator customerInfoValidator;

 @InitBinder
 public void myInitBinder(WebDataBinder dataBinder) {
     Object target = dataBinder.getTarget();
     if (target == null) {
         return;
     }
     System.out.println("Target=" + target);

     if (target.getClass() == CartInfo.class) {

     }
     else if (target.getClass() == CustomerInfo.class) {
         dataBinder.setValidator(customerInfoValidator);
     }

 }

 @RequestMapping("/403")
 public String accessDenied() {
     return "/403";
 }

 @RequestMapping("/")
 public String home() {
     return "index";
 }

 @RequestMapping({ "/productList" })
 public ModelAndView listProductHandler(ModelAndView model,
         @RequestParam(value = "name", defaultValue = "") String name,
         @RequestParam(value = "page", defaultValue = "1") int page) {
     
    page = 1; 
     int total=5;    
     if(page==1){}    
     else{    
     	page=(page-1)*total+1;    
     }         
     List<ProductInfo> list=null;
      list=productDAO.listProducts(name); 
     model.addObject("productList", list); 
     return model; 
 }
 
 
 @RequestMapping({ "/categoryList" })
 public ModelAndView listCategory(ModelAndView model, 
         @RequestParam(value = "page", defaultValue = "1") int page) {
     
    page = 1; 
     int total=5;    
     if(page==1){}    
     else{    
     	page=(page-1)*total+1;    
     }         
     List<CategoryInfo> list=categoryDAO.getAllCategory(); 
     model.addObject("categoryList", list); 
     return model; 
 }
 

 @RequestMapping({ "/buyProduct" })
 public ModelAndView listProductHandler(HttpServletRequest request, ModelAndView model, 
         @RequestParam(value = "code", defaultValue = "") String code) {

     Product product = null;
     if (code != null && code.length() > 0) {
         product = productDAO.findProduct(code);
     }
     if (product != null) {

         CartInfo cartInfo = Utils.getCartInSession(request);

         ProductInfo productInfo = new ProductInfo(product);

         cartInfo.addProduct(productInfo, 1);
     }
     model.setViewName("redirect:/esshaMarket");
     return model;
 }

 @RequestMapping({ "/esshaMarketRemoveProduct" })
 public ModelAndView removeProductHandler(HttpServletRequest request, ModelAndView model, 
         @RequestParam(value = "code", defaultValue = "") String code) {
     Product product = null;
     if (code != null && code.length() > 0) {
         product = productDAO.findProduct(code);
     }
     if (product != null) {

         CartInfo cartInfo = Utils.getCartInSession(request);

         ProductInfo productInfo = new ProductInfo(product);

         cartInfo.removeProduct(productInfo);

     }
     model.setViewName("redirect:/esshaMarket");
     return model;
 }

 @RequestMapping(value = { "/esshaMarket" }, method = RequestMethod.POST)
 public ModelAndView esshaMarketUpdateQty(HttpServletRequest request, 
         ModelAndView model, 
         @ModelAttribute("cartForm") CartInfo cartForm) {

     CartInfo cartInfo = Utils.getCartInSession(request);
     cartInfo.updateQuantity(cartForm);

     model.setViewName("redirect:/esshaMarket");
     return model;
     }

 @RequestMapping(value = { "/esshaMarket" }, method = RequestMethod.GET)
 public ModelAndView esshaMarketHandler(HttpServletRequest request, ModelAndView model) {
     CartInfo myCart = Utils.getCartInSession(request);
     model.addObject("cartForm", myCart);
     model.setViewName("esshaMarket");
     return model;
 }
 
 @RequestMapping(value = { "/esshaMarketCustomer" }, method = RequestMethod.GET)
 public ModelAndView esshaMarketCustomerForm(HttpServletRequest request, ModelAndView model) {
     CartInfo cartInfo = Utils.getCartInSession(request);
     if (cartInfo.isEmpty()) {
         model.setViewName("redirect:/esshaMarket");
         return model;
     }
     CustomerInfo customerInfo = cartInfo.getCustomerInfo();
     if (customerInfo == null) {
         customerInfo = new CustomerInfo();
     }

     model.addObject("customerForm", customerInfo);
     model.setViewName("esshaMarketCustomer");
     return model;
 }

 @RequestMapping(value = { "/esshaMarketCustomer" }, method = RequestMethod.POST)
 public ModelAndView esshaMarketCustomerSave(HttpServletRequest request, 
         ModelAndView model, 
         @ModelAttribute("customerForm") @Validated CustomerInfo customerForm, 
         BindingResult result, //
         final RedirectAttributes redirectAttributes) {

     if (result.hasErrors()) {
         customerForm.setValid(false);
         model.setViewName("esshaMarketCustomer");
         return model;
     }

     customerForm.setValid(true);
     CartInfo cartInfo = Utils.getCartInSession(request);
     cartInfo.setCustomerInfo(customerForm);
     model.setViewName("redirect:/esshaMarketConfirm");
     return model;
 }
 
 
 @RequestMapping(value = { "/esshaMarketConfirm" }, method = RequestMethod.GET)
 public ModelAndView esshaMarketConfirmationReview(HttpServletRequest request, ModelAndView model) {
     CartInfo cartInfo = Utils.getCartInSession(request);

     if (cartInfo.isEmpty()) {
    	 model.setViewName("redirect:/esshaMarket");
         return model;
     } else if (!cartInfo.isValidCustomer()) {
    	 model.setViewName("redirect:/esshaMarketCustomer");
         return model;
     }
     model.setViewName("esshaMarketConfirm");
     return model;
 }
 
 @RequestMapping(value = { "/esshaMarketConfirm" }, method = RequestMethod.POST)
 @Transactional(propagation = Propagation.NEVER)
 
public ModelAndView esshaMarketConfirmationSave(HttpServletRequest request,ModelAndView model) {
     CartInfo cartInfo = Utils.getCartInSession(request);
   
     if (cartInfo.isEmpty()) {
    	 model.setViewName("redirect:/esshaMarket");
         return model;
     } else if (!cartInfo.isValidCustomer()) {
    	 model.setViewName("redirect:/esshaMarketCustomer");
         return model;
     }
     try {
    	  orderDAO.saveOrder(cartInfo);
    } catch (Exception e) {
       model.setViewName("esshaMarketConfirm");
       return model;
    }    
     Utils.removeCartInSession(request);

     Utils.storeLastOrderedCartInSession(request, cartInfo);

     model.setViewName("redirect:/esshaMarketEnd");
     return model;
	}
 
 @RequestMapping(value = { "/esshaMarketEnd" }, method = RequestMethod.GET)
 public ModelAndView esshaMarketEnd(HttpServletRequest request, ModelAndView model) {
     CartInfo lastOrderedCart = Utils.getLastOrderedCartInSession(request);
     if (lastOrderedCart == null) {
    	 model.setViewName("redirect:/esshaMarket");
         return model;
     }
     model.setViewName("esshaMarketEnd");
     return model;
 }

 @RequestMapping(value = { "/productImage" }, method = RequestMethod.GET)
 public void productImage(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
         @RequestParam("code") String code) throws IOException {
     Product product = null;
     if (code != null) {
         product = this.productDAO.findProduct(code);

     }
     if (product != null && product.getImage() != null) {
         response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
         response.getOutputStream().write(product.getImage());

     }
     response.getOutputStream().close();
 }
 
 
 @RequestMapping(value = { "/categoryImage" }, method = RequestMethod.GET)
 public void categoryImage(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
         @RequestParam("name") String name) throws IOException {
     Category category = null;
     if (name !=null) {
    	 category = this.categoryDAO.findCategoryName(name);

     }
     if (category != null && category.getImage()!= null) {
         response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
         response.getOutputStream().write(category.getImage());

     }
     response.getOutputStream().close();
 }
  
}