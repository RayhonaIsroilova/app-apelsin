package uz.pdp.apelsin.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.apelsin.entity.picture.Attachment;
import uz.pdp.apelsin.entity.picture.AttachmentContent;
import uz.pdp.apelsin.entity.template.ApiResponse;
import uz.pdp.apelsin.repository.AttachmentContentRepository;
import uz.pdp.apelsin.repository.AttachmentRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Optional;

@Service
public class AttachmentService {
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    AttachmentContentRepository contentRepository;

    @SneakyThrows
    public ApiResponse upload(MultipartHttpServletRequest request){
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        Attachment attachment = new Attachment();
        if (file!=null){
            attachment.setName(file.getOriginalFilename());
            attachment.setSize(file.getSize());
            attachment.setContentType(file.getContentType());
            Attachment savedAttachment = attachmentRepository.save(attachment);

            AttachmentContent attachmentContent = new AttachmentContent();
            attachmentContent.setBytes(file.getBytes());
            attachmentContent.setAttachment(savedAttachment);
            AttachmentContent save = contentRepository.save(attachmentContent);
            return new ApiResponse("Saved successfully",true,save);
        }
        return new ApiResponse("Not Saved you have error",false);
    }

    @SneakyThrows
    public ApiResponse download(Integer id, HttpServletResponse response){
        Optional<Attachment> byId = attachmentRepository.findById(id);
        if (!byId.isPresent()){
            return new ApiResponse("This id not found",false);
        }
        Attachment attachment = byId.get();
        response.setHeader("Content","attachment");
        response.setContentType(attachment.getContentType());
        FileInputStream fileInputStream = new FileInputStream("uploadFiles"+"/"+attachment.getName());
        FileCopyUtils.copy(fileInputStream,response.getOutputStream());
        return new ApiResponse("Success",true);
    }
}
