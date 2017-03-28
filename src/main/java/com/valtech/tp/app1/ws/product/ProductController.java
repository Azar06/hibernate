package com.valtech.tp.app1.ws.product;

import com.google.common.collect.Lists;
import com.valtech.tp.app1.domain.model.commun.EntityAlreadyExist;
import com.valtech.tp.app1.domain.model.product.Product;
import com.valtech.tp.app1.domain.model.product.ProductCriteria;
import com.valtech.tp.app1.domain.model.product.ProductLite;
import com.valtech.tp.app1.domain.repository.product.ProductRepository;
import com.valtech.tp.app1.domain.service.product.ProductService;
import com.valtech.tp.app1.ws.common.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController extends AbstractController {

    @Autowired
    private ProductService productService;

    @GetMapping("{id}")
    public ResponseEntity<Product> getProduct(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<Product>(new Product(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> insertProduct(@RequestBody Product product) throws EntityAlreadyExist {
        productService.insert(product);
        return new ResponseEntity<Product>(product, HttpStatus.CREATED);
    }

    @GetMapping
    public List<ProductLite> getProducts(@RequestParam(required = false) String name) {
        ProductCriteria productCriteria = new ProductCriteria();
        productCriteria.setName(name);
        return productService.findProductsByCriteria(productCriteria);
    }
}
