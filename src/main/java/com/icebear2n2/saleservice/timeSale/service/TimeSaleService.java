package com.icebear2n2.saleservice.timeSale.service;

import com.icebear2n2.saleservice.domain.entity.Product;
import com.icebear2n2.saleservice.domain.repository.ProductRepository;
import com.icebear2n2.saleservice.domain.request.TimeSaleRequest;
import com.icebear2n2.saleservice.domain.response.ProductResponse;
import com.icebear2n2.saleservice.exception.ErrorCode;
import com.icebear2n2.saleservice.exception.ProductServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeSaleService {

    private final ProductRepository productRepository;

    public List<ProductResponse.ProductData> getProductStaredTimeSale() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        return productRepository.findBySaleStartDateBeforeAndSaleEndDateAfter(currentTimestamp, currentTimestamp)
                .stream()
                .map(ProductResponse.ProductData::new)
                .collect(Collectors.toList());
    }

    public void startProductTimeSale(TimeSaleRequest timeSaleRequest) {
        Product product = productRepository.findById(timeSaleRequest.getProductId()).orElseThrow(() -> new ProductServiceException(ErrorCode.PRODUCT_NOT_FOUND));
        product.setDiscountPrice(timeSaleRequest.getDiscountPrice());
        product.setSaleStartDate(timeSaleRequest.getStartDate());
        product.setSaleEndDate(timeSaleRequest.getEndDate());

        productRepository.save(product);
    }

    public void endProductTimeSale(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductServiceException(ErrorCode.PRODUCT_NOT_FOUND));
        product.setDiscountPrice(null);
        product.setSaleStartDate(null);
        product.setSaleEndDate(null);

        productRepository.save(product);
    }

    public boolean isProductStartedTimeSale(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductServiceException(ErrorCode.PRODUCT_NOT_FOUND));
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        return product.getSaleStartDate().before(currentTimestamp) && product.getSaleEndDate().after(currentTimestamp);
    }

    // 매일 자정에 실행되는 스케줄링 작업
//    @Scheduled(cron = "0 0 0 * * ?")

    // 매일 2분마다 실행
    @Scheduled(cron = "0 */2 * * * ?")
    public void handleTimeSaleScheduling() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        List<Product> products = productRepository.findAll();

        for (Product product: products
             ) {
            if (product.getSaleEndDate() != null && product.getSaleEndDate().before(currentTimestamp)) {
                endProductTimeSale(product.getProductId());
            }
        }
    }

}
