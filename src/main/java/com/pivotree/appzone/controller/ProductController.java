package com.pivotree.appzone.controller;

import com.pivotree.appzone.entity.Customer;
import com.pivotree.appzone.entity.Product;
import com.pivotree.appzone.enums.UserType;
import com.pivotree.appzone.exception.ResponseMessage;
import com.pivotree.appzone.intemplate.PriceAndCategory;
import com.pivotree.appzone.intemplate.ProductInput;
import com.pivotree.appzone.intemplate.ProductSearchByName;
import com.pivotree.appzone.intemplate.UsernameInput;
import com.pivotree.appzone.outtemplate.ProductOutput;
import com.pivotree.appzone.repository.PriceRepository;
import com.pivotree.appzone.repository.ProductRepository;
import com.pivotree.appzone.service.CustomerService;
import com.pivotree.appzone.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerService customerService;

    @Autowired
    private PriceRepository priceRepository;
    /**
     * Creates a new product in the system.
     *
     * @param prods a list of products to be created
     * @return a list of products that were created
     * @throws IllegalArgumentException if the input is invalid
     */

    @PostMapping("/create")
    ResponseEntity<Object> createProd(@Valid @RequestBody List<ProductInput> prods) throws IllegalArgumentException {

        List<Product> products = productService.createProduct(prods);

        return new ResponseEntity<>(new ResponseMessage(products), HttpStatusCode.valueOf(201));

    }

    private ProductOutput convertProductToProductOutput(Product product) {
        return new ProductOutput(Math.toIntExact(product.getId()), product.getProductReference(), product.getDescription(),
                product.getPrice(), product.getBrand(), product.getManufactureDate(), product.getExpiryDate(), product.getCategories(), product.getDimensions());
    }

    /**
     * Returns a list of all products in the system.
     *
     * @return a list of products
     */

    @GetMapping("/getAll")
    public ResponseEntity<Object> getProduct(){
        List<Product> products=productRepository.findAll();
        return new ResponseEntity<>(products,HttpStatusCode.valueOf(200));
    }

    @GetMapping("/products")
    public ResponseEntity<Object> getAllProducts(
            int page,
            int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.findAll(pageable);
//        List<Product> products = productPage.getContent();
        return new ResponseEntity<>(productPage, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN','CUSTOMER')")
    @PostMapping("/productRef")
    public ResponseEntity<List<Product>> getTopProductsByName(@RequestBody ProductSearchByName apiInput) {
        List<Product> topProducts = productService.getTopProductsByName(apiInput.getProductReference()); // Fetch top 2 products
        return new ResponseEntity<>(topProducts, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN','CUSTOMER')")
    @PostMapping("/productsByPriceAndCategory")
    public ResponseEntity<Object> getProductsByPriceRange(
            @RequestBody PriceAndCategory priceAndCategory,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = priceRepository.findProductsByPriceAndCategory(priceAndCategory, pageable);

        if (products.isEmpty()) {
            return new ResponseEntity<>(Page.empty(), HttpStatus.OK); // Return an empty page object
        }

        if (priceAndCategory.getKey() == null && products.getTotalElements() >= 2) {
            List<Product> twoProd = new ArrayList<>();
            twoProd.add(products.getContent().get(0));
            twoProd.add(products.getContent().get(1));
            log.info(twoProd);
            return new ResponseEntity<>(new ResponseMessage(twoProd), HttpStatusCode.valueOf(201));
        } else {
            return new ResponseEntity<>(new ResponseMessage(products), HttpStatus.OK);
        }


    }

    @PreAuthorize("hasRole('ADMIN','CUSTOMER')")
    @GetMapping("/{productRef}")
    public ResponseEntity<Object> getProductByProductRef(@PathVariable String productRef){
        // Call the service method to retrieve the product by productRef
        Optional<Product> product = productService.getProductByProductRef(productRef);

        if(product.isPresent()){
            ResponseEntity<Object> res = new ResponseEntity<>(product.get(),HttpStatusCode.valueOf(200));
            return res;
        } else {
            return ResponseEntity.ok().build();
        }
    }



}

