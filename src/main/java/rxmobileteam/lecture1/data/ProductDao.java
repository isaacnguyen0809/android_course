package rxmobileteam.lecture1.data;

import org.jetbrains.annotations.NotNull;
import rxmobileteam.lecture1.service.Product;

import java.util.HashSet;
import java.util.Set;

/**
 * {@link ProductDao} represents a Data Access Object (DAO) for products.
 * The implementation is simplified, so it just uses {@link HashSet} to store.
 * <p>
 * todo: 1. Implement a method {@link ProductDao#add(Product)} that store new product into the set
 * todo: 2. Implement a method {@link ProductDao#findAll()} that returns a set of all products
 */
public class ProductDao {
    private final Set<Product> products = new HashSet<>();

    /**
     * Stores a new product
     *
     * @param product a product to store
     * @return {@code true} if a product was stored, {@code false} otherwise
     */
    public boolean add(@NotNull Product product) {
        if (products.contains(product)) {
            return false;
        }
        products.add(product);
        return true;
    }

    /**
     * Returns all stored products
     *
     * @return a set of all stored products
     */
    public Set<Product> findAll() {
        return products;
    }

}