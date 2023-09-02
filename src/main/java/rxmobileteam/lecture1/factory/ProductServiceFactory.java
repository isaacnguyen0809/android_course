package rxmobileteam.lecture1.factory;

import rxmobileteam.lecture1.service.ProductService;
import rxmobileteam.utils.ExerciseNotCompletedException;

/**
 * {@link ProductServiceFactory} is used to create an instance of {@link ProductService}
 * <p>
 * TODO: 1. Implement method {@link ProductServiceFactory#createProductService()}
 */
public class ProductServiceFactory {

    /**
     * Create a new instance of {@link ProductService}
     *
     * @return ProductService
     */
    public ProductService createProductService() {
        return new ProductService();
    }
}