package com.example.Ecommerce.service;

import com.example.Ecommerce.dto.order.OrderDTO;
import com.example.Ecommerce.dto.order.OrderInfoMapper;
import com.example.Ecommerce.dto.product.ProductDTO;
import com.example.Ecommerce.entity.CartAndProductEntity;
import com.example.Ecommerce.entity.CartEntity;
import com.example.Ecommerce.entity.OrderEntity;
import com.example.Ecommerce.entity.ProductEntity;
import com.example.Ecommerce.enums.AppLanguage;
import com.example.Ecommerce.enums.OrderStatus;
import com.example.Ecommerce.exp.AppBadException;
import com.example.Ecommerce.repository.CartAndProductRepository;
import com.example.Ecommerce.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartAndProductRepository cartAndProductRepository;

    @Autowired
    private ResourceBundleService resourceBundleService;

    @Autowired
    private ProductService productService;

    public String create(Long customerId, OrderDTO dto, AppLanguage language) {
        OrderEntity entity = new OrderEntity();
//        CartEntity cartEntity = cartService.get(dto.getCartId(), language);
//
//        Long profileId = cartEntity.getProfileId();
//
//        if (!Objects.equals(customerId, profileId)) {
//            log.warn("Order not allowed");
//            throw new AppBadException("Sizga ruxsat yo'q");
//        }
        check(customerId,dto.getCartId(),language);

        entity.setCustomerId(customerId);
        entity.setCartId(dto.getCartId());
        entity.setDeliveredAddress(dto.getDeliveredAddress());
        entity.setDeliveredContact(dto.getDeliveredContact());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setStatus(OrderStatus.PREPARING);
        orderRepository.save(entity);

        return "Buyurtma muvaffaqiyatli yaratildi";
    }

    public String cancelById(Long orderId, AppLanguage language) {

        OrderEntity entity = get(orderId, language);

        entity.setStatus(OrderStatus.CANCELLED);

        orderRepository.save(entity);

        return "Order has cancelled successfully";
    }

    public OrderEntity get(Long orderId, AppLanguage language) {
        return orderRepository.findById(orderId).orElseThrow(() -> {
            log.warn("Order not found{}", orderId);
            return new AppBadException(resourceBundleService.getMessage("order.not.found", language));
        });
    }

    public String cancelOrderByIdForClient(Long orderId, Long profileId, AppLanguage language) {
        OrderEntity orderEntity = get(orderId, language);

        if (orderEntity.getStatus() != OrderStatus.PREPARING) {
            log.warn("Order cannot be cancelled as it is not in 'preparing' state");
            throw new AppBadException("Buyurtma 'tayyorlashda' holatida emas");
        }

        if (!Objects.equals(orderEntity.getCustomerId(), profileId)) {
            log.warn("Order not allowed");
            throw new AppBadException("Sizga ruxsat yoq");
        }
        orderEntity.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(orderEntity);

        return "Buyurtma bekor qilindi";
    }


    public String deliveredById(Long orderId, AppLanguage language) {

        OrderEntity entity = get(orderId, language);

        entity.setStatus(OrderStatus.DELIVERED);

        orderRepository.save(entity);

        return "Order has delivered successfully";
    }

    public String onTheWayById(Long orderId, AppLanguage language) {

        OrderEntity entity = get(orderId, language);

        entity.setStatus(OrderStatus.ON_THE_WAY);

        orderRepository.save(entity);

        return "Order has cancelled successfully";
    }


//    public OrderDTO getOrderByIdWithProducts(Long orderId, AppLanguage language) {
//        Optional<OrderInfoMapper> orderInfoOptional = orderRepository.getInfoOrder(orderId);
//
//        if(orderInfoOptional.isEmpty()){
//            log.warn("Order not found");
//            throw new AppBadException(resourceBundleService.getMessage("order.not.found",language));
//        }
//        OrderInfoMapper orderInfoMapper = orderInfoOptional.get();
//        return toDTO(orderInfoMapper, language);
//    }
//
//    private OrderDTO toDTO(OrderInfoMapper orderInfoMapper, AppLanguage language) {
//        OrderDTO dto=new OrderDTO();
//
//        dto.setId(orderInfoMapper.getOrderId());
//        dto.setCustomerId(orderInfoMapper.getOrderCustomerId());
//        dto.setDeliveredAddress(orderInfoMapper.getOrderAddress());
//        dto.setDeliveredContact(orderInfoMapper.getOrderContact());
//        dto.setCreatedDate(orderInfoMapper.getOrderCreatedDate());
//
//        CartEntity cart = cartService.get(orderInfoMapper.getOrderCartId(), language);
//
//        List<CartAndProductEntity> byCartId = cartAndProductRepository.findByCartId(cart.getId());
//
//        List<ProductDTO> productDTOS=new ArrayList<>();
//        for (CartAndProductEntity cartAndProductEntity : byCartId) {
//            ProductDTO productDTO=new ProductDTO();
//            ProductEntity productEntity = productService.get(cartAndProductEntity.getProductId(), language);
//
//            productDTO.setId(productEntity.getId());
//            switch (language) {
//                case UZ -> {
//                    productDTO.setDescription(productEntity.getDescriptionUz());
//                    productDTO.setName(productEntity.getNameUz());
//                }
//                case RU -> {
//                    productDTO.setDescription(productEntity.getDescriptionRu());
//                    productDTO.setName(productEntity.getNameRu());
//                }
//                case EN -> {
//                    productDTO.setDescription(productEntity.getDescriptionEn());
//                    productDTO.setName(productEntity.getNameEn());
//                }
//            }
//            productDTO.setPrice(productEntity.getPrice());
//            productDTOS.add(productDTO);
//
//        }
//
//        dto.setProduct(productDTOS);
//
//        return dto;
//    }
public OrderDTO getOrderByIdWithProducts(Long orderId, AppLanguage language) {
    Optional<OrderInfoMapper> orderInfoOptional = orderRepository.getInfoOrder(orderId);

    if (orderInfoOptional.isEmpty()) {
        log.warn("Order not found");
        throw new AppBadException(resourceBundleService.getMessage("order.not.found", language));
    }

    OrderInfoMapper orderInfoMapper = orderInfoOptional.get();
    return toDTO(orderInfoMapper, language);
}

    private OrderDTO toDTO(OrderInfoMapper orderInfoMapper, AppLanguage language) {
        // CartEntity obyektini olish uchun ishlatilmaydi
        // CartEntity cart = cartService.get(orderInfoMapper.getOrderCartId(), language);

        List<CartAndProductEntity> byCartId = cartAndProductRepository.findByCartId(orderInfoMapper.getOrderCartId());

        List<ProductDTO> productDTOS = new ArrayList<>();
        for (CartAndProductEntity cartAndProductEntity : byCartId) {
            ProductDTO productDTO = new ProductDTO();
            ProductEntity productEntity = productService.get(cartAndProductEntity.getProductId(), language);

            productDTO.setId(productEntity.getId());
            switch (language) {
                case UZ:
                    productDTO.setDescription(productEntity.getDescriptionUz());
                    productDTO.setName(productEntity.getNameUz());
                    break;
                case RU:
                    productDTO.setDescription(productEntity.getDescriptionRu());
                    productDTO.setName(productEntity.getNameRu());
                    break;
                case EN:
                    productDTO.setDescription(productEntity.getDescriptionEn());
                    productDTO.setName(productEntity.getNameEn());
                    break;
            }
            productDTO.setPrice(productEntity.getPrice());
            productDTOS.add(productDTO);
        }

        OrderDTO dto = new OrderDTO();
        dto.setId(orderInfoMapper.getOrderId());
        dto.setCustomerId(orderInfoMapper.getOrderCustomerId());
        dto.setDeliveredAddress(orderInfoMapper.getOrderAddress());
        dto.setDeliveredContact(orderInfoMapper.getOrderContact());
        dto.setCreatedDate(orderInfoMapper.getOrderCreatedDate());
        dto.setProduct(productDTOS);

        return dto;
    }

    public OrderDTO getOrderByIdWithProducts2(Long orderId, AppLanguage language) {
        // OrderInfoMapper obyektini olish
        Optional<OrderInfoMapper> orderInfoOptional = orderRepository.getInfoOrder(orderId);

        if (orderInfoOptional.isEmpty()) {
            log.warn("Order not found");
            throw new AppBadException(resourceBundleService.getMessage("order.not.found",language));
        }

        OrderInfoMapper orderInfoMapper = orderInfoOptional.get();

        // CartAndProductEntity obyektlarini olish
        List<CartAndProductEntity> byCartId = cartAndProductRepository.findByCartId(orderInfoMapper.getOrderCartId());

        List<ProductDTO> productDTOS = new ArrayList<>();
        for (CartAndProductEntity cartAndProductEntity : byCartId) {
            ProductDTO productDTO = new ProductDTO();
            ProductEntity productEntity = productService.get(cartAndProductEntity.getProductId(), language);

            productDTO.setId(productEntity.getId());
            switch (language) {
                case UZ:
                    productDTO.setDescription(productEntity.getDescriptionUz());
                    productDTO.setName(productEntity.getNameUz());
                    break;
                case RU:
                    productDTO.setDescription(productEntity.getDescriptionRu());
                    productDTO.setName(productEntity.getNameRu());
                    break;
                case EN:
                    productDTO.setDescription(productEntity.getDescriptionEn());
                    productDTO.setName(productEntity.getNameEn());
                    break;
            }
            productDTO.setPrice(productEntity.getPrice());
            productDTOS.add(productDTO);
        }

        // OrderDTO obyektini tuzatish
        OrderDTO dto = new OrderDTO();
        dto.setId(orderInfoMapper.getOrderId());
        dto.setCustomerId(orderInfoMapper.getOrderCustomerId());
        dto.setDeliveredAddress(orderInfoMapper.getOrderAddress());
        dto.setDeliveredContact(orderInfoMapper.getOrderContact());
        dto.setCreatedDate(orderInfoMapper.getOrderCreatedDate());
        dto.setProduct(productDTOS);

        return dto;
    }

    public PageImpl<OrderDTO> getAllPagination(Long clientId, Integer size, Integer page,AppLanguage language){
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<OrderEntity> all = orderRepository.findAll(pageable);
        List<OrderDTO> dtoList=new ArrayList<>();
        long totalElements = all.getTotalElements();
        for (OrderEntity entity : all.getContent()) {
            check(clientId,entity.getCartId(),language);
            OrderDTO dto=new OrderDTO();
            dto.setCartId(entity.getCartId());
            dto.setId(entity.getId());
            dto.setCustomerId(entity.getCustomerId());
            dto.setStatus(entity.getStatus());
            dto.setDeliveredContact(entity.getDeliveredContact());
            dto.setDeliveredAddress(entity.getDeliveredAddress());
            dto.setCreatedDate(entity.getCreatedDate());
            dtoList.add(dto);
        }
        return new PageImpl<>(dtoList,pageable,totalElements);

    }


    private void check(Long profileId,Long cartId, AppLanguage language){
        CartEntity entity = cartService.get(cartId,language);

        Long profileId1 = entity.getProfileId();

        if(!Objects.equals(profileId,profileId1)){
            log.warn("Order not allowed");
            throw new AppBadException("Sizga ruxsat yo'q");
        }

    }


}
