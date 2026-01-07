package com.paras.tradeflow.serviceimpl;

import com.paras.tradeflow.dto.ProductRequest;
import com.paras.tradeflow.dto.ProductResponse;
import com.paras.tradeflow.entity.Product;
import com.paras.tradeflow.entity.ProductStatus;
import com.paras.tradeflow.repository.ProductRepository;
import com.paras.tradeflow.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponse create(ProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .status(ProductStatus.ACTIVE)
                .createdAt(Instant.now())
                .build();
        return map(productRepository.save(product));
    }

    @Override
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not Found"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());

        return map(productRepository.save(product));
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductResponse getById(Long id) {
        return map(productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not Found")));
    }

    @Override
    public void activate(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not Found"));

        product.setStatus(ProductStatus.ACTIVE);
        productRepository.save(product);
    }

    @Override
    public void deactivate(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not Found"));

        product.setStatus(ProductStatus.INACTIVE);
        productRepository.save(product);
    }

    @Override
    public List<ProductResponse> getAllForAdmin() {
        return productRepository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public List<ProductResponse> getAllForCustomer() {
        return productRepository.findByStatus(ProductStatus.ACTIVE)
                .stream()
                .map(this::map)
                .toList();
    }

    private ProductResponse map(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .status(product.getStatus())
                .build();
    }

    @Override
    public boolean isInStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not Found"));

        return product.getStatus() == ProductStatus.ACTIVE && product.getStockQuantity() >= quantity;
    }
}
