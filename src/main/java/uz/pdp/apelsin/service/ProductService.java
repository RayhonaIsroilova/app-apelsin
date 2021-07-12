package uz.pdp.apelsin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.apelsin.entity.Category;
import uz.pdp.apelsin.entity.Detail;
import uz.pdp.apelsin.entity.Product;
import uz.pdp.apelsin.entity.picture.Attachment;
import uz.pdp.apelsin.entity.template.ApiResponse;
import uz.pdp.apelsin.payload.ProductDTO;
import uz.pdp.apelsin.repository.AttachmentRepository;
import uz.pdp.apelsin.repository.CategoryRepository;
import uz.pdp.apelsin.repository.DetailRepository;
import uz.pdp.apelsin.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    DetailRepository detailRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    AttachmentService attachmentService;

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public ApiResponse getId(Integer id){
        Optional<Product> byId = productRepository.findById(id);
        return byId.map(product -> new ApiResponse("Take it", true, product)).orElseGet(() -> new ApiResponse("This id not found", false));
    }

    public ApiResponse getDetailsByProductId(Integer id){
        List<Detail> byProductId = detailRepository.findByProductId(id);
        return new ApiResponse("Success",true,byProductId);
    }

    public ApiResponse addWithPhotoProduct(ProductDTO productDTO, MultipartHttpServletRequest request){
        Optional<Category> byId = categoryRepository.findById(productDTO.getCategoryId());
        if (!byId.isPresent())
            return new ApiResponse("Category is not found",false);
        ApiResponse upload = attachmentService.upload(request);
        productRepository.save(new Product(
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getPrice(),
                (Attachment) upload.getObject(),
                byId.get()
        ));
        return new ApiResponse("Product added successfully",true);
    }

    public ApiResponse addWithProduct(ProductDTO productDTO){
        Optional<Category> byId = categoryRepository.findById(productDTO.getCategoryId());
        if (!byId.isPresent())
            return new ApiResponse("Category is not found",false);
        productRepository.save(new Product(
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getPrice(),
                byId.get()
        ));
        return new ApiResponse("Product saved successfully",true);
    }

    public ApiResponse editProduct(ProductDTO productDTO,Integer id){
        Optional<Product> byId = productRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("This product id is not found",false);

        Optional<Category> byId1 = categoryRepository.findById(productDTO.getCategoryId());
        if (!byId1.isPresent())
            return new ApiResponse("This Category id is not found",false);

        Product product = byId.get();
        product.setName(productDTO.getName());
        product.setCategory(byId1.get());
        product.setPrice(productDTO.getPrice());
        productRepository.save(product);
        return new ApiResponse("Product edited successfully",true);
    }

    public ApiResponse deleteProduct(Integer id){
        Optional<Product> byId = productRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("This product is not found",false);
        productRepository.deleteById(id);
        return new ApiResponse("Product delete success",true);
    }


}
