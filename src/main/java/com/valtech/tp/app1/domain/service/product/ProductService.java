package com.valtech.tp.app1.domain.service.product;


import com.valtech.tp.app1.domain.model.commun.EntityAlreadyExist;
import com.valtech.tp.app1.domain.model.product.Product;
import com.valtech.tp.app1.domain.model.product.ProductCriteria;
import com.valtech.tp.app1.domain.model.product.ProductLite;
import com.valtech.tp.app1.domain.repository.product.ProductRepository;
import com.valtech.tp.app1.domain.service.commun.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService extends DomainService {

    @Autowired
    private ProductRepository productRepository;

    public void insert(Product product) throws EntityAlreadyExist {
        productRepository.insert(product);
    }

    public List<ProductLite> findProductsByCriteria(ProductCriteria productCriteria) {
        return productRepository.findProductsByCriteria(productCriteria);
    }
}
