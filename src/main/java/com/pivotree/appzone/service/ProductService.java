//package com.example.backend_mini_project.service;
//
//import com.example.backend_mini_project.repository.CategoryRepository;
//import com.example.backend_mini_project.repository.ProductRepository;
//import com.example.backend_mini_project.entity.Category;
//import com.example.backend_mini_project.entity.Dimension;
//import com.example.backend_mini_project.entity.Product;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.service;
//
//import java.util.List;
//
//@service
//public class ProductService {
//    @Autowired
//    private ProductRepository repository;
//    @Autowired
//    private CategoryRepository categoryRepository;
//    public Product saveproduct(Product product){
//        Integer catId=product.getCategory().getCategoryId();
//        Category category=categoryRepository.findById(catId).orElse(null);
//        product.setCategory(category);
//
//        Dimension dimension=new Dimension();
//        dimension.setHeight(product.getDimension().getHeight());
//        dimension.setLength(product.getDimension().getLength());
//        dimension.setWidth(product.getDimension().getWidth());
//        dimension.setVolume(dimension.getHeight()*dimension.getWidth()*dimension.getLength());
//        product.setDimension(dimension);
//        System.out.println(product);
//        return repository.save(product);
//
//    }
//    public List<Product> getProducts(){
//        return repository.findAll();
//    }
//
//    public Product getProductById(int id){
//        return repository.findById(id).orElse(null);
//    }
//    public Product getProductByReference(String reference){
//        return repository.findByProductReference(reference);
//    }
//    public String deleteProductById(int id){
//        repository.deleteById(id);
//        return "Product removed id is" + " " +id;
//    }
//
//    public Product updateProduct(Product product){
//        Product existinProduct=repository.findByProductReference(product.getProductReference());
//        existinProduct.setProductReference(product.getProductReference());
//        existinProduct.setBrand(product.getBrand());
//        existinProduct.setPrice(product.getPrice());
//        existinProduct.setDescription(product.getDescription());
//        existinProduct.setExpDate(product.getExpDate());
//        existinProduct.setManDate(product.getManDate());
//
////        existinProduct.setCategoryId(product.getCategoryId());
//        return repository.save(existinProduct);
//    }
//}

package com.pivotree.appzone.service;

import com.pivotree.appzone.entity.ImageUrls;
import com.pivotree.appzone.intemplate.CategoryInput;
import com.pivotree.appzone.intemplate.ProductInput;
import com.pivotree.appzone.repository.CategoryManager;
import com.pivotree.appzone.repository.CategoryRepository;
import com.pivotree.appzone.repository.ProductManager;
import com.pivotree.appzone.repository.ProductRepository;
import com.pivotree.appzone.entity.Category;
import com.pivotree.appzone.entity.Dimension;
import com.pivotree.appzone.entity.Product;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductManager productManager;

    @Autowired
    private CategoryManager categoryManager;

    /**
     * Returns a list of all products in the system.
     *
     * @return a list of all products in the system
     */
//    public List<Product> getProducts() {
//        List<Product> products = productManager.listProductV2();
//        return products;
//    }
//

    /**
     * Creates a list of products based on the input list of products.
     *
     * @param products the list of products to create
     * @return the list of created products
     */
//    @Transactional(rollbackOn = Exception.class)
//    public List<Product> createProduct(List<ProductInput> products) {
//        List<Product> productList = new ArrayList<>();
//        for (ProductInput prod : products) {
//            try {
//                // Creates a new Dimension object based on the input dimensions
//                Dimension dimension = new Dimension(prod.getDimensions().getLength(), prod.getDimensions().getWidth(),
//                        prod.getDimensions().getHeight());
//                // Creates a new list of ImageUrls objects based on the input image URLs
//                List<ImageUrls> allUrls = new ArrayList<>();
//                for (String img : prod.getImageUrls()) {
//                    allUrls.add(new ImageUrls(img));
//                }
//                // Finds the Category object with the given name, or null if no match is found
//                Optional<Category> category = Optional.ofNullable(categoryManager.findByCategoryName(prod.getCategoryName()));
//                if (category.isEmpty()) {
//                    // Creates a new Product object with the given properties and an empty Category object
//                    Product prod1 = new Product(prod.getProductReference(), prod.getDescription(), prod.getPrice(),
//                            prod.getBrand(), prod.getManufactureDate(), prod.getExpiryDate(), allUrls,
//                            dimension);
//                    productList.add(prod1);
//                    // Saves the Product object to the database
//                    productManager.addProduct(prod1);
//                } else {
//                    // Creates a new Product object with the given properties and the matching Category object
//                    Product prod1 = new Product(prod.getProductReference(), prod.getDescription(), prod.getPrice(), prod.getBrand(), prod.getManufactureDate()
//                            , prod.getExpiryDate(), allUrls, dimension, category.get());
//                    productList.add(prod1);
//                    // Saves the Product object to the database
//                    productManager.addProduct(prod1);
//                }
//            } catch (Exception e) {
//                System.out.println(e);
//            }
//        }
//        return productList;
//    }

    //shravya
    @Transactional(rollbackOn = Exception.class)
        public List<Product> createProduct(List<ProductInput> products) {
            List<Product> productList = new ArrayList<>();
            for (ProductInput prod : products) {
                try {
                    // Creates a new Dimension object based on the input dimensions
                    Dimension dimension = new Dimension(prod.getDimensions().getLength(),
                            prod.getDimensions().getWidth(), prod.getDimensions().getHeight());

                    List<ImageUrls> allUrls = new ArrayList<>();
                       for (String img:prod.getImageUrls()) {
                           allUrls.add(new ImageUrls(img));
                       }

                    List<Category> categoryList=new ArrayList<>();
                    for (CategoryInput category: prod.getCategories()){
                        Category retrievedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
                        if (retrievedCategory != null) {
                            categoryList.add(retrievedCategory);
                        }
                    }
                    Product prod1 = new Product(prod.getProductReference(), prod.getDescription(),
                                    prod.getPrice(), prod.getBrand(), prod.getManufactureDate(),
                                    prod.getExpiryDate(), allUrls, dimension, categoryList );
                    productList.add(prod1);
                    productManager.addProduct(prod1);


                } catch (Exception e) {
                    System.out.println(e);
                    // Handle the exception accordingly
                }
            }
            return productList;
        }

    public Product getProductById(Long productId) {

        return productRepository.findById(productId).orElse(null);
    }


    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Transactional
    public List<Product> getTopProductsByName(String productName) {
        return productRepository.findByNameContainingIgnoreCase(productName);
    }

    public Optional<Product> getProductByProductRef(String productRef) {
        return Optional.ofNullable(productRepository.findByProductReference(productRef));
    }
}



