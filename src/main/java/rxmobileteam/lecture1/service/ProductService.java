package rxmobileteam.lecture1.service;

import org.jetbrains.annotations.NotNull;
import rxmobileteam.lecture1.data.ProductDao;

import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link ProductService} provides an API that allows to manage {@link Product}s.
 * <p>
 * TODO: 1. Using {@link ProductDao} implement method {@link ProductService#addProduct(Product)}}
 * TODO: 2. Using {@link ProductDao} implement method {@link ProductService#searchProducts(String)}
 */
public class ProductService {

    ProductDao productDao = new ProductDao();

    /**
     * Adds a new product to the system.
     *
     * @param product a product to add
     * @return {@code true} if a product was added, {@code false} otherwise.
     */
    public boolean addProduct(@NotNull Product product) {
        return productDao.add(product);
    }

    /**
     * Returns all products that contains the given query in the name or description.
     *
     * @param query a search query
     * @return a list of found products
     */
    public List<Product> searchProducts(String query) {
        return productDao.findAll()
                .stream()
                .filter(element -> (element.getName().toLowerCase().contains(query.toLowerCase()) || element.getDescription().toLowerCase().contains(query.toLowerCase())))
                .collect(Collectors.toList());
    }
}