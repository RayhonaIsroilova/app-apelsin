package uz.pdp.apelsin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.apelsin.entity.Invoice;
import uz.pdp.apelsin.entity.Payment;
import uz.pdp.apelsin.entity.template.ApiResponse;
import uz.pdp.apelsin.payload.PaymentDTO;
import uz.pdp.apelsin.repository.InvoiceRepository;
import uz.pdp.apelsin.repository.PaymentRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    public List<Payment> getAll(){
        return paymentRepository.findAll();
    }

    public ApiResponse getId(Integer id){
        Optional<Payment> byId = paymentRepository.findById(id);
        return byId.map(payment -> new ApiResponse("Take it", true, payment)).orElseGet(() -> new ApiResponse("This id not found", false));
    }

    public ApiResponse addPayment(PaymentDTO paymentDTO){
        Optional<Invoice> byId = invoiceRepository.findById(paymentDTO.getInvoiceId());
        if (!byId.isPresent())
            return new ApiResponse("This invoice id not found",false);
        Invoice invoice = byId.get();
        if (invoice.isPaid())
            return new ApiResponse("This invoice is already paid",false);
        if (invoice.getAmount()<paymentDTO.getAmount() || 0 > paymentDTO.getAmount())
            return new ApiResponse("Please enter the valid amount , your amount is more than invoice or less than 0",false);
        if (invoice.getAmount() - paymentDTO.getAmount() == 0){
            invoice.setPaid(true);
        }
        invoice.setAmount(invoice.getAmount() - paymentDTO.getAmount());
        paymentRepository.save(new Payment(new Timestamp(System.currentTimeMillis()),paymentDTO.getAmount(),invoice));
        invoiceRepository.save(invoice);
        return new ApiResponse("Saved Success",true);
    }











}
