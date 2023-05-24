package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.enumm.Status;
import com.example.springsecurityapplication.models.Category;
import com.example.springsecurityapplication.models.Image;
import com.example.springsecurityapplication.models.Order;
import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.repositories.CategoryRepository;
import com.example.springsecurityapplication.repositories.OrderRepositoryForAdmin;
import com.example.springsecurityapplication.services.OrderService;
import com.example.springsecurityapplication.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class AdminController {

    private final ProductService productService;
    private final OrderService orderService;

    @Value("${upload.path}")
    private String uploadPath;

    private final CategoryRepository categoryRepository;
    private final OrderRepositoryForAdmin orderRepositoryForAdmin;

    public AdminController(ProductService productService, OrderService orderService, CategoryRepository categoryRepository, OrderRepositoryForAdmin orderRepositoryForAdmin) {
        this.productService = productService;
        this.orderService = orderService;
        this.categoryRepository = categoryRepository;
        this.orderRepositoryForAdmin = orderRepositoryForAdmin;
    }

    @GetMapping("admin/product/add")
    public String addProduct(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("category", categoryRepository.findAll());
        return "product/addProduct";
    }

    @PostMapping("/admin/product/add")
    public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @RequestParam("file_one")MultipartFile file_one, @RequestParam("file_two")MultipartFile file_two, @RequestParam("file_three")MultipartFile file_three, @RequestParam("file_four")MultipartFile file_four, @RequestParam("file_five")MultipartFile file_five, @RequestParam("category") int category, Model model) throws IOException {
        Category category_db = (Category) categoryRepository.findById(category).orElseThrow();
        System.out.println(category_db.getName());
        if(bindingResult.hasErrors()){
            model.addAttribute("category", categoryRepository.findAll());
            return "product/addProduct";
        }

        if(file_one != null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_one.getOriginalFilename();
            file_one.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);

        }

        if(file_two != null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_two.getOriginalFilename();
            file_two.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }

        if(file_three != null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_three.getOriginalFilename();
            file_three.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }

        if(file_four != null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_four.getOriginalFilename();
            file_four.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }

        if(file_five != null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_five .getOriginalFilename();
            file_five .transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }
        productService.saveProduct(product, category_db);
        return "redirect:/admin";
    }


    @GetMapping("/admin")
    public String admin(Model model)
    {
        model.addAttribute("products", productService.getAllProduct());
        return "admin";
    }

    @GetMapping("admin/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        productService.deleteProduct(id);
        return "redirect:/admin";
    }

    @GetMapping("admin/product/edit/{id}")
    public String editProduct(Model model, @PathVariable("id") int id){
        model.addAttribute("product", productService.getProductId(id));
        model.addAttribute("category", categoryRepository.findAll());
        return "product/editProduct";
    }

    @PostMapping("admin/product/edit/{id}")
    public String editProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @PathVariable("id") int id, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("category", categoryRepository.findAll());
            return "product/editProduct";
        }
        productService.updateProduct(id, product);
        return "redirect:/admin";
    }

    @GetMapping("admin/orders")
    public String displayOrders(Model model){
        List<Order> orderList = orderRepositoryForAdmin.findAll();
        model.addAttribute("orders", orderList);
        return "/orders";
    }

    @PostMapping("admin/orders/searchOrd")
    public String orderSearch(@RequestParam("searchOrd") String searchOrd, Model model){
        model.addAttribute("orders", orderRepositoryForAdmin.findAll());
        model.addAttribute("value_searchOrd", searchOrd);
        if(!searchOrd.isEmpty()){
            model.addAttribute("search_order",
                    orderRepositoryForAdmin.findByForLastStringsIgnorCase(searchOrd));
        }else{
            model.addAttribute("orders", orderRepositoryForAdmin.findAll());
        }
        return ("/orders");
    }

    @GetMapping("admin/orders/changeStatus/{id}")
    public String changeProduct(Model model, @PathVariable("id") int id){

        model.addAttribute("order", orderService.getOrderId(id));
        model.addAttribute("status", Status.values());
        return "changeStatus";
    }

    @PostMapping("admin/orders/changeStatus/{id}")
    public String changeProduct(@ModelAttribute("order") @Valid Order order, @PathVariable("id") int id, @RequestParam("status") String status,Model model){
        //List<Status> enumCollection = Arrays.asList(Status.values());
//        if(bindingResult.hasErrors()){
//            model.addAttribute("status", Status.values());
//            return "changeStatus";
//        }
//        orderService.updateStatusOrder(status, id, order);
        model.addAttribute("status");
        model.addAttribute("order", orderService.getOrderId(id));
        System.out.println(order.getNumber());
        Order newOrder = new Order(order.getNumber(), order.getProduct(), order.getPerson(), order.getCount(), order.getPrice(), Status.valueOf(status));
        orderService.updateStatusOrder(id, newOrder);
        return "redirect:/admin/orders";
    }

}
