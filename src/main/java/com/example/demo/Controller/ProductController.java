package com.example.demo.Controller;


import com.example.demo.Entity.Product;
import com.example.demo.Service.CategoryService;
import com.example.demo.Service.GemPriceListService;
import com.example.demo.Service.MaterialPriceListService;
import com.example.demo.Service.ProductService;
import com.example.demo.Service.TypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final GemPriceListService gemPriceListService;
    private final MaterialPriceListService materialPriceListService;
    private final TypeService typeService;

    public ProductController(ProductService productService, CategoryService categoryService,GemPriceListService gemPriceListService,
    		MaterialPriceListService materialPriceListService,TypeService typeService ) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.gemPriceListService = gemPriceListService;
        this.materialPriceListService = materialPriceListService;
        this.typeService = typeService;
    }
    @GetMapping("seller/products")
    public String showProductSeller(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Product> productPage = productService.findAll(PageRequest.of(page, 10));
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", productPage.getNumber());
        model.addAttribute("totalPages", productPage.getTotalPages());
        return "seller/productList";
    }
  

    @GetMapping("manager/products")
    public String showProductList(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Product> productPage = productService.findAll(PageRequest.of(page, 10));
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", productPage.getNumber());
        model.addAttribute("totalPages", productPage.getTotalPages());
        return "manager/productList";
    }
    @GetMapping("seller/products/detail-product/{productID}") 
    public String showProductDetailSeller(@PathVariable Integer productID, Model model) { 
        Product product = productService.findById(productID).orElseThrow(() -> new RuntimeException("Product not found")); 
        model.addAttribute("product", product);
        return "seller/detailProduct";
    }

    @GetMapping("manager/products/detail-product/{productID}") 
    public String showProductDetail(@PathVariable Integer productID, Model model) { 
        Product product = productService.findById(productID).orElseThrow(() -> new RuntimeException("Product not found")); 
        model.addAttribute("product", product);
        return "manager/detailProduct";
    }
    
     
    @PostMapping("manager/products/detail-product/{productID}/update") 
    public String updateProduct(@PathVariable Integer productID, Product product, Model model) { 
        product.setProductID(productID);
        productService.updateProduct(product);
        model.addAttribute("product", product);
        model.addAttribute("updateSuccess", true); 
        return "manager/detailProduct"; 
    }

    @PostMapping("manager/products/{productID}/soldOut")
    public String deleteProduct(@PathVariable Integer productID, Model model) { 
        Product product = productService.findById(productID).orElseThrow(() -> new RuntimeException("Product not found")); 
        product.setActive(false);
        productService.updateProduct(product);
        return "manager/productList";
    }

    @GetMapping("manager/products/create-product")
    public String showCreateProductForm(Model model) {
    	 model.addAttribute("product", new Product());
         model.addAttribute("categories", categoryService.getAllCategorys());
         model.addAttribute("gemPriceLists", gemPriceListService.getAllGemPriceLists());
         model.addAttribute("materialPriceLists", materialPriceListService.getAllMaterialPriceLists());
         model.addAttribute("types", typeService.getAllTypes());
         model.addAttribute("products", productService.findAllProduct());
        return "manager/createNewProduct";
    }

    @PostMapping("manager/products/create-product/create")
    public String saveroduct(Product product,Model model) {
        productService.saveProduct(product);
        model.addAttribute("product",product);
        return "manager/createNewProduct";
    }
    @GetMapping("/dashboard")
    public String showDashboard() {
    	return "manager/dashboard";
    }
    @GetMapping("/promotion")
    public String showPromotion() {
        return "manager/promotion";
    }
    @GetMapping("/price-list")
    public String showPriceList() {
        return "manager/priceList";
    }
    
}
