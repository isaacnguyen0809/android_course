package rxmobileteam.lecture1.service;

import rxmobileteam.lecture1.interfaces.IProduct;

import java.util.List;

public class ProductService {

    IProduct iProduct;

    public ProductService(IProduct iProduct) {
        this.iProduct = iProduct;
    }

    public void addProduct(String key, Product product) {
        iProduct.addProduct(key, product);
    }

    public List<Product> searchProducts(String query) {
        return iProduct.searchProduct(query);
    }
}