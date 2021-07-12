package uz.pdp.apelsin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.apelsin.entity.Category;
import uz.pdp.apelsin.entity.template.ApiResponse;
import uz.pdp.apelsin.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository repository;

    public List<Category> getList(){
        return repository.findAll();
    }

    public ApiResponse getId(Integer id){
        Optional<Category> byId = repository.findById(id);
        return byId.map(category -> new ApiResponse("Take it", true, category)).orElseGet(() -> new ApiResponse("This id not found", false));
    }

    public ApiResponse getProductId(Integer id){
              return new ApiResponse("take it",true,  repository.findByProductId(id));
    }

    public ApiResponse add(String category){
        Category category1 = new Category();
        category1.setName(category);
        repository.save(category1);
        return new ApiResponse("Success added",true,category1);
    }

    public ApiResponse edit(String category,Integer id) {
        Optional<Category> byId = repository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("This id not found",false);
        Category category1 = byId.get();
        category1.setName(category);
        repository.save(category1);
        return new ApiResponse("Success edited", true, category1);
    }

    public ApiResponse delete(Integer id) {
        Optional<Category> byId = repository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("This id not found", false);
       repository.deleteById(id);
        return new ApiResponse("Success delete", true);
    }

}
