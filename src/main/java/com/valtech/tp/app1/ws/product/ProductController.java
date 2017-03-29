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
        return new ResponseEntity<Product>(productService.getProduct(Long.valueOf(id)), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Insert a new product")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Product created"),
            @ApiResponse(code = 400, message = "Invalid product")
    })
    public ResponseEntity<Product> insertProduct(@RequestBody @Valid Product product) throws EntityAlreadyExist {
        if (product.getId() != null) {
            throw new IllegalArgumentException("The id has to be null");
        }
        productService.insert(product);
        return new ResponseEntity<Product>(product, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @ApiOperation("Update a product")
    public ResponseEntity<Product> updateProduct(@PathVariable(name = "id") String id, @RequestBody Product productUpdater) {
        Product product = productService.updateProduct(Long.valueOf(id), productUpdater);
        return new ResponseEntity<Product>(product, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    @ApiOperation("Delete a product")
    public ResponseEntity<Product> deleteProduct(@PathVariable(name = "id") String id) {
        productService.deleteProduct(Long.valueOf(id));
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @GetMapping
    @ApiOperation(value = "Get a list of products")
    @ApiImplicitParams({
            @ApiImplicitParam()
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            //@ApiResponse(code = 400, message = "Invalid product")
    })
    public List<ProductLite> getProducts(@RequestParam(required = false) String name) {
        ProductCriteria productCriteria = new ProductCriteria();
        productCriteria.setName(name);
        return productService.findProductsByCriteria(productCriteria);
    }
}
