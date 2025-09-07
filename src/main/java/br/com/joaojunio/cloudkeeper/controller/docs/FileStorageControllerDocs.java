package br.com.joaojunio.cloudkeeper.controller.docs;

import br.com.joaojunio.cloudkeeper.data.dto.file.DeleteFileResponseDTO;
import br.com.joaojunio.cloudkeeper.data.dto.file.UploadFileResponseDTO;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageControllerDocs {
    ResponseEntity<UploadFileResponseDTO> uploadFile(Long id, MultipartFile file, String folderName);
    ResponseEntity<Resource> downloadFile(String type, String fileId) throws Exception;
    ResponseEntity<DeleteFileResponseDTO> delete(String fileId);
}
