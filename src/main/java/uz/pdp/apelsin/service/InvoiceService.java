package uz.pdp.apelsin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.apelsin.entity.Invoice;
import uz.pdp.apelsin.entity.template.ApiResponse;
import uz.pdp.apelsin.repository.InvoiceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    @Autowired
    InvoiceRepository invoiceRepository;

    public ApiResponse getInvoice(Integer id) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        return optionalInvoice.map(invoice -> new ApiResponse("Success", true, invoice)).orElseGet(() -> new ApiResponse("Invoice is not found", false));
    }

    public List<Invoice> getAll() {
        return invoiceRepository.findAll();
    }
}
