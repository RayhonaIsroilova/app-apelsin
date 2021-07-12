package uz.pdp.apelsin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.apelsin.entity.*;
import uz.pdp.apelsin.entity.template.ApiResponse;
import uz.pdp.apelsin.payload.OrderDTO;
import uz.pdp.apelsin.payload.OrderEditDTO;
import uz.pdp.apelsin.payload.OrderOutDTO;
import uz.pdp.apelsin.repository.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    DetailRepository detailRepository;
    @Autowired
    InvoiceRepository invoiceRepository;

    public List<OrderOutDTO> getAll() {
        List<OrderOutDTO> orderOutDTOS = new ArrayList<>();
        List<Order> orderList = orderRepository.findAll();
        for (Order order : orderList) {
            Optional<Order> byId = orderRepository.findById(order.getId());
            if (!byId.isPresent())
                return null;
            List<Detail> detailList = detailRepository.findByOrderId(order.getId());
            orderOutDTOS.add(new OrderOutDTO(byId.get(), detailList));
        }
        return orderOutDTOS;
    }

    public ApiResponse getOrder(Integer id) {
        Optional<Order> byId = orderRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("This order is not found", false);
        List<Detail> byOrderId = detailRepository.findByOrderId(id);
        return new ApiResponse("Success", true, new OrderOutDTO(byId.get(), byOrderId));
    }

    public ApiResponse addOrder(OrderDTO orderDTO) {
        Optional<Customer> byId = customerRepository.findById(orderDTO.getCustomerId());
        if (!byId.isPresent())
            return new ApiResponse("This Customer is not found", false);

        Optional<Product> byId1 = productRepository.findById(orderDTO.getProductId());
        if (!byId1.isPresent())
            return new ApiResponse("This product id is not found", false);
        Order savedOrder = orderRepository.save(new Order(
                new Date(System.currentTimeMillis()),
                byId.get()
        ));
        detailRepository.save(new Detail(
                orderDTO.getQuantity(),
                savedOrder,
                byId1.get()
        ));
        Invoice invoice = new Invoice();
        invoice.setOrder(savedOrder);
        double price = byId1.get().getPrice();
        invoice.setAmount(orderDTO.getQuantity() * price);
        invoice.setIssued(new Date(System.currentTimeMillis()));
        Date date = savedOrder.getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 3);
        invoice.setDue(new Date(calendar.getTimeInMillis()));
        Invoice save = invoiceRepository.save(invoice);
        return new ApiResponse("Success, Order is added successfully", true, "Invoice ID:" + save.getId());
    }

    public ApiResponse editOrder(OrderEditDTO editDTO, Integer id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent())
            return new ApiResponse("Order not found", false);

        Optional<Customer> optionalCustomer = customerRepository.findById(editDTO.getCustomerId());
        if (!optionalCustomer.isPresent())
            return new ApiResponse("This customer is not found", false);

        Optional<Product> optionalProduct = productRepository.findById(editDTO.getProductId());
        if (!optionalProduct.isPresent())
            return new ApiResponse("Product is not found",false);

        Optional<Detail> optionalDetail = detailRepository.findById(editDTO.getDetailedId());
        if (!optionalDetail.isPresent())
            return new ApiResponse("This detail is not found",false);

        Optional<Invoice> optionalInvoice = invoiceRepository.findById(editDTO.getInvoiceId());
        if (!optionalInvoice.isPresent())
            return new ApiResponse("This invoice is not found",false);

        Order order = optionalOrder.get();
        order.setCustomer(order.getCustomer());
        order.setDate(new Date(System.currentTimeMillis()));
        Order savedOrder = orderRepository.save(order);

        Detail detail = optionalDetail.get();
        detail.setOrder(savedOrder);
        detail.setProduct(optionalProduct.get());
        detail.setQuantity(editDTO.getQuantity());
        Detail savedDetail = detailRepository.save(detail);

        Invoice invoice = optionalInvoice.get();
        invoice.setOrder(savedOrder);
        double price = optionalProduct.get().getPrice();
        invoice.setAmount(editDTO.getQuantity()*price);
        invoice.setIssued(new Date(System.currentTimeMillis()));
        Date date = savedOrder.getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,3);
        invoice.setDue(new Date(calendar.getTimeInMillis()));
        Invoice save = invoiceRepository.save(invoice);
        return new ApiResponse("" +
                "Saved successfully",true,save);
    }

    public ApiResponse deleteOrder(Integer id){
        Optional<Order> byId = orderRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("This order id is not found",false);
        orderRepository.deleteById(id);
        return new ApiResponse("Delete successfully",true);

    }


}
