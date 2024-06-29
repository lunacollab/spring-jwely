package com.example.demo.Controller;


import com.example.demo.Entity.Counter;
import com.example.demo.Entity.GemPriceList;
import com.example.demo.Entity.Material;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.Promotion;
import com.example.demo.Entity.Staff;
import com.example.demo.Repository.StaffRepository;
import com.example.demo.Service.CategoryService;
import com.example.demo.Service.CounterService;
import com.example.demo.Service.GemPriceListService;
import com.example.demo.Service.MaterialPriceListService;
import com.example.demo.Service.MaterialService;
import com.example.demo.Service.ProductService;
import com.example.demo.Service.PromotionService;
import com.example.demo.Service.TypeService;
import com.example.demo.saveLog.csvlog;
import com.example.demo.Entity.MaterialPriceList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {

    private ProductService productService;
    private  CategoryService categoryService;
    private GemPriceListService gemPriceListService;
    private  MaterialPriceListService materialPriceListService;
    private  TypeService typeService;
    private PromotionService promotionService;
    private CounterService counterService;
    private MaterialService materialService;
    @Autowired
    private StaffRepository staffRepository;
    private static final Logger logger = LogManager.getLogger(ProductController.class);

    public ProductController(ProductService productService, CategoryService categoryService,GemPriceListService gemPriceListService,
    		MaterialPriceListService materialPriceListService,TypeService typeService,PromotionService promotionService,
    		CounterService counterService,MaterialService materialService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.gemPriceListService = gemPriceListService;
        this.materialPriceListService = materialPriceListService;
        this.typeService = typeService;
        this.promotionService = promotionService;
        this.counterService=counterService;
        this.materialService=materialService;
    }
    @GetMapping("seller/products")
    public String showProductSeller(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Product> productPage = productService.findAll(PageRequest.of(page, 10));
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", productPage.getNumber());
        model.addAttribute("totalPages", productPage.getTotalPages());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
	       Staff staff = staffRepository.findByEmail(email);
	       model.addAttribute("staff", staff);
        return "seller/productList";
    }
  

    @GetMapping("manager/products")
    public String showProductList(Model model, @RequestParam(defaultValue = "0") int page) {
    	Page<Product> productPage = productService.findAll(
    	        PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "productID"))
    	    );
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", productPage.getNumber());
        model.addAttribute("totalPages", productPage.getTotalPages()); 
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
	       Staff staff = staffRepository.findByEmail(email);
	       model.addAttribute("staff", staff);
        return "manager/productList";
    }
    @GetMapping("seller/products/detail-product/{productID}") 
    public String showProductDetailSeller(@PathVariable Integer productID, Model model) { 
        Product product = productService.findById(productID).orElseThrow(() -> new RuntimeException("Product not found")); 
        model.addAttribute("product", product);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
	       Staff staff = staffRepository.findByEmail(email);
	       model.addAttribute("staff", staff);
        return "seller/detailProduct";
    }

    @GetMapping("manager/products/detail-product/{productID}") 
    public String showProductDetail(@PathVariable Integer productID, Model model) { 
        Product product = productService.findById(productID).orElseThrow(() -> new RuntimeException("Product not found")); 
        model.addAttribute("product", product);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
	       Staff staff = staffRepository.findByEmail(email);
	       model.addAttribute("staff", staff);
        return "manager/detailProduct";
    }
    
     
    @PostMapping("manager/products/detail-product/{productID}/update") 
    public String updateProduct(@PathVariable Integer productID, Product product, Model model) { 
        product.setProductID(productID);
        productService.updateProduct(product);
        model.addAttribute("product", product);
        model.addAttribute("updateSuccess", true); 
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
	       Staff staff = staffRepository.findByEmail(email);
	       model.addAttribute("staff", staff);
        return "manager/detailProduct"; 
    }

    @PostMapping("manager/products/{productID}/soldOut")
    public String deleteProduct(@PathVariable Integer productID, Model model) { 
        Product product = productService.findById(productID).orElseThrow(() -> new RuntimeException("Product not found")); 
        product.setActive(false);
        productService.updateProduct(product);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
	       Staff staff = staffRepository.findByEmail(email);
	       model.addAttribute("staff", staff);
	       return "redirect:/manager/products";
    }

    @GetMapping("manager/products/create-product")
    public String showCreateProductForm(Model model) {
    	 model.addAttribute("product", new Product());
         model.addAttribute("categories", categoryService.getAllCategorys());
         model.addAttribute("gemPriceLists", gemPriceListService.getAllGemPriceLists());
         model.addAttribute("materialPriceLists", materialPriceListService.getAllMaterialPriceLists());
         model.addAttribute("types", typeService.getAllTypes());
         model.addAttribute("products", productService.findAllProduct());
         model.addAttribute("counters", counterService.findAll());
         String email = SecurityContextHolder.getContext().getAuthentication().getName();
	       Staff staff = staffRepository.findByEmail(email);
	       model.addAttribute("staff", staff);
        return "manager/createNewProduct";
    }

    @PostMapping("manager/products/create-product/create")
    public String saveroduct(Product product,Model model) {
        productService.saveProduct(product);
        model.addAttribute("product",product);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
	       Staff staff = staffRepository.findByEmail(email);
	       model.addAttribute("staff", staff);
        return "manager/createNewProduct";
    }

    @GetMapping("/promotion")
    public String showPromotionList(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Promotion> promotionPage = promotionService.findAll(PageRequest.of(page, 10));
        model.addAttribute("promotions", promotionPage); 
        model.addAttribute("currentPage", promotionPage.getNumber());
        model.addAttribute("totalPages", promotionPage.getTotalPages()); 
        model.addAttribute("promotion", new Promotion()); 
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
	       Staff staff = staffRepository.findByEmail(email);
	       model.addAttribute("staff", staff);
        return "manager/promotion";
    }

    @PostMapping("/promotion")
 	public String savePromotionList(Model model,Promotion promotion) {
    	 String email = SecurityContextHolder.getContext().getAuthentication().getName();
	       Staff staff = staffRepository.findByEmail(email);

	       model.addAttribute("staff", staff);
    	 promotionService.save(promotion); 
    	 return "redirect:/promotion";
     }
    @GetMapping("/price-list")
    public String showPriceList(Model model, @RequestParam(defaultValue = "0") int page, 
                                @RequestParam(defaultValue = "10") int size) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Staff staff = staffRepository.findByEmail(email);
        PageRequest pageable = PageRequest.of(page, size,Sort.by(Sort.Direction.DESC, "applyDate"));
        Page<MaterialPriceList> materialPriceListPage = materialPriceListService.findAllMaterial(pageable);
        model.addAttribute("currentPage", materialPriceListPage.getNumber());
        model.addAttribute("totalPages", materialPriceListPage.getTotalPages()); 
        model.addAttribute("materialPriceListPage", materialPriceListPage);
        model.addAttribute("materialPrice", new MaterialPriceList()); 
        model.addAttribute("staff", staff);
        return "manager/priceList";
    }

    private String getMaterialName(int materialID) {
        switch (materialID) {
            case 1:
                return "18k gold";
            case 2:
                return "24k gold";
            case 3:
                return "9999k gold";
            default:
                return "Unknown material";
        }
    }
    @PostMapping("/price-list")
    public String savePriceList(Model model, MaterialPriceList materialPriceList) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Staff staff = staffRepository.findByEmail(email);

        materialPriceList.setApplyDate(new Date());
        model.addAttribute("staff", staff);
        materialPriceListService.save(materialPriceList);

        String materialName = getMaterialName(materialPriceList.getMaterialID());
        String logEntry = String.format("%s, Saved material price list: ApplyDate: %s, Name: %s, BuyPrice: %.2f, SellPrice: %.2f",
                                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                                        materialPriceList.getApplyDate(), 
                                        materialName, 
                                        materialPriceList.getBuyPrice(), 
                                        materialPriceList.getSellPrice());
        csvlog.log(logEntry);

        return "redirect:/price-list";
    } 
    
    @GetMapping("/gem-price-list")
    public String showGemPriceList(Model model, @RequestParam(defaultValue = "0") int page, 
                                @RequestParam(defaultValue = "10") int size) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Staff staff = staffRepository.findByEmail(email);
        PageRequest pageable = PageRequest.of(page, size,Sort.by(Sort.Direction.DESC, "applyDate"));
        Page<GemPriceList> gemPriceListPage = gemPriceListService.findAllGemPriceList(pageable);
        model.addAttribute("currentPage", gemPriceListPage.getNumber());
        model.addAttribute("totalPages", gemPriceListPage.getTotalPages()); 
        model.addAttribute("gemPriceListPage", gemPriceListPage);
        model.addAttribute("gemPriceList", new GemPriceList()); 
        model.addAttribute("staff", staff);
        return "manager/gemPriceList";
    }
    @PostMapping("/gem-price-list")
    public String saveGemPriceList(Model model, GemPriceList gemPriceList) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Staff staff = staffRepository.findByEmail(email);
        gemPriceList.setApplyDate(new Date());
        model.addAttribute("staff", staff);
        gemPriceListService.save(gemPriceList);
        return "redirect:/gem-price-list";
    } 
	@GetMapping("/counter")
	public String counterList(Model model,@RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "10") int size) {
		 String email = SecurityContextHolder.getContext().getAuthentication().getName();
         Staff staff = staffRepository.findByEmail(email);
         PageRequest pageable = PageRequest.of(page, size);
         Page<Counter> counterPage = counterService.findPaginated(pageable);
         model.addAttribute("currentPage", counterPage.getNumber());
         model.addAttribute("totalPages", counterPage.getTotalPages()); 
         model.addAttribute("counterPage", counterPage);
         model.addAttribute("counter", new Counter());
         model.addAttribute("staff", staff);
		return "manager/counterList";
	}
    @PostMapping("/saveCounter")
    public String saveCounter(@ModelAttribute Counter counter) {
    	counter.setActive(true);
        counterService.saveCounter(counter);
        return "redirect:/counter";
    }
    @GetMapping("counter/editCounter/{counterID}")
    public String editCounter(@PathVariable("counterID") Integer counterID, Model model) {
    	Optional<Counter> counter = counterService.getCounterById(counterID);
    	String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Staff staff = staffRepository.findByEmail(email);
    
        model.addAttribute("staff", staff);
        model.addAttribute("counter", counter.get());
 	   return "manager/editCounter";
    }
    @PostMapping("counter/editCounter/{counterID}/update")
    public String updateCounter(@ModelAttribute("counter") Counter counter, @PathVariable("counterID") Integer counterID, Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Staff staff = staffRepository.findByEmail(email);
        model.addAttribute("staff", staff);
        counterService.updateCounter(counter);
        return "redirect:/counter";
    }

	@GetMapping("/counter/counterDetail/{counterID}")
	public String counterDetail(@PathVariable("counterID") Integer counterID, Model model) {
		 String email = SecurityContextHolder.getContext().getAuthentication().getName();
         Staff staff = staffRepository.findByEmail(email);
         Optional<Counter> counter = counterService.getCounterById(counterID);
         model.addAttribute("staff", staff);
         model.addAttribute("counter", counter.get());
         return "manager/counterDetail";
	}
	  @GetMapping("/manage-material")
	    public String showManageMaterial(Model model, @RequestParam(defaultValue = "0") int page, 
	                                @RequestParam(defaultValue = "10") int size) {
		      Page<Product> productPage = productService.findAll(PageRequest.of(page, 10));
	        model.addAttribute("products", productPage.getContent());
	        model.addAttribute("currentPage", productPage.getNumber());
	        model.addAttribute("totalPages", productPage.getTotalPages());
	           String email = SecurityContextHolder.getContext().getAuthentication().getName();
		       Staff staff = staffRepository.findByEmail(email);
		       model.addAttribute("staff", staff);
	        return "manager/manageMaterial";
	    }
}
