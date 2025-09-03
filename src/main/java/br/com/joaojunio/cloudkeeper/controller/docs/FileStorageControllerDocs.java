package br.com.joaojunio.cloudkeeper.controller.docs;

import br.com.joaojunio.cloudkeeper.data.dto.file.UploadFileResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageControllerDocs {
    ResponseEntity<UploadFileResponseDTO> uploadFile(Long id, MultipartFile file, String folderName);
    List<UploadFileResponseDTO> uploadMulitpleFiles(MultipartFile[] files);
    ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request);
}
