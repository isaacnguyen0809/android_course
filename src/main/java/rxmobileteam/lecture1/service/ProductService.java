package rxmobileteam.lecture1.service;

import rxmobileteam.lecture1.data.ProductDao;

import java.util.List;

public class ProductService {

    ProductDao productDao = new ProductDao();

    public void addProduct(String key, Product product) {
        productDao.addProduct(key, product);
    }

    public List<Product> searchProducts(String query) {
        return productDao.searchProduct(query);
    }
}