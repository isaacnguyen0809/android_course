package rxmobileteam.lecture1.data;

import rxmobileteam.lecture1.interfaces.IProduct;
import rxmobileteam.lecture1.service.Product;
import rxmobileteam.utils.ExerciseNotCompletedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductDao implements IProduct {

    private final Map<String, Product> products = new HashMap<>();


    @Override
    public void addProduct(String key, Product product) {
        if (!products.containsKey(key)) {
            products.put(key, product);
        } else {
            throw new ExerciseNotCompletedException();
        }
    }


    @Override
    public List<Product> searchProduct(String query) {
        return products.values()
                .stream()
                .filter(product -> (product.getName().toLowerCase().contains(query.toLowerCase()) || product.getDescription().toLowerCase().contains(query.toLowerCase())))
                .collect(Collectors.toList());
    }

}