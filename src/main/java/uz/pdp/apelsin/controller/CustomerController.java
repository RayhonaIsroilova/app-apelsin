package uz.pdp.apelsin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apelsin.entity.Category;
import uz.pdp.apelsin.entity.Customer;
import uz.pdp.apelsin.entity.template.ApiResponse;
import uz.pdp.apelsin.service.CategoryService;
import uz.pdp.apelsin.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/{id}")
    public HttpEntity<?> getByProductId(@RequestParam Integer productId){
        ApiResponse id = customerService.getId(productId);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/list")
    public HttpEntity<?> getAll(){
        List<Customer> all = customerService.getAll();
        return ResponseEntity.ok(all);
    }

    @PostMapping("/add")
    public HttpEntity<?> addCategory(@RequestBody Customer customer){
        ApiResponse add = customerService.add(customer);
        return ResponseEntity.status(add.isSuccess()?200:409).body(add);
    }

    @PutMapping("/edit/{id}")
    public HttpEntity<?> edit(@RequestBody Customer customer,@PathVariable Integer id){
        ApiResponse edit = customerService.edit(customer,id);
        return ResponseEntity.status(edit.isSuccess()?200:409).body(edit);
    }
    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        ApiResponse delete = customerService.delete(id);
        return ResponseEntity.status(delete.isSuccess()?200:409).body(delete);
    }
}
