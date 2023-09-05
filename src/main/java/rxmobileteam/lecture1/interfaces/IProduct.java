package rxmobileteam.lecture1.interfaces;

import rxmobileteam.lecture1.service.Product;

import java.util.List;

public interface IProduct {
    void addProduct(String key, Product product);

    List<Product> searchProduct(String query);
}
