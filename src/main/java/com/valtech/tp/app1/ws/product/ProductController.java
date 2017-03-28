package com.valtech.tp.app1.ws.product;

import com.valtech.tp.app1.domain.model.commun.EntityAlreadyExist;
import com.valtech.tp.app1.domain.model.product.Product;
import com.valtech.tp.app1.domain.model.product.ProductCriteria;
import com.valtech.tp.app1.domain.model.product.ProductLite;
import com.valtech.tp.app1.domain.service.product.ProductService;
import com.valtech.tp.app1.ws.common.AbstractController;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController extends AbstractController {

    @Autowired
    private ProductService productService;

    @GetMapping("{id}")
    public ResponseEntity<Product> getProduct(@PathVariable(name = "id") String id) {
        return new ResponseEntity<Product>(new Product(Long.valueOf(id)), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Insert a new product")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Product created"),
            @ApiResponse(code = 400, message = "Invalid product")
    })
    public ResponseEntity<Product> insertProduct(@RequestBody @Valid Product product) throws EntityAlreadyExist {
        if(product.getId() != null) {
            throw new IllegalArgumentException("The id has to be null");
        }
        productService.insert(product);
        return new ResponseEntity<Product>(product, HttpStatus.CREATED);
    }

    @GetMapping
    @ApiOperation(value = "Get a list of products")
    /*
    @ApiImplicitParams({
            @ApiImplicitParam()
    })
    */
    @ApiResponses({
            @ApiResponse(code = 201, message = "Product created"),
            @ApiResponse(code = 400, message = "Invalid product")
    })
    public List<ProductLite> getProducts(@RequestParam(required = false) String name) {
        ProductCriteria productCriteria = new ProductCriteria();
        productCriteria.setName(name);
        return productService.findProductsByCriteria(productCriteria);
    }
}