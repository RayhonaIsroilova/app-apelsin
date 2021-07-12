package uz.pdp.apelsin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apelsin.entity.Category;
import uz.pdp.apelsin.entity.template.ApiResponse;
import uz.pdp.apelsin.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/{id}")
    public HttpEntity<?> getByProductId(@RequestParam Integer productId){
        ApiResponse productId1 = categoryService.getProductId(productId);
        return ResponseEntity.ok(productId1);
    }

    @GetMapping("/list")
    public HttpEntity<?> getAll(){
        List<Category> list = categoryService.getList();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/add")
    public HttpEntity<?> addCategory(@RequestParam String name){
        ApiResponse add = categoryService.add(name);
        return ResponseEntity.status(add.isSuccess()?200:409).body(add);
    }

    @PutMapping("/edit/{id}")
    public HttpEntity<?> edit(@RequestBody String name,@PathVariable Integer id){
        ApiResponse edit = categoryService.edit(name, id);
        return ResponseEntity.status(edit.isSuccess()?200:409).body(edit);
    }
    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        ApiResponse delete = categoryService.delete(id);
        return ResponseEntity.status(delete.isSuccess()?200:409).body(delete);
    }
}
