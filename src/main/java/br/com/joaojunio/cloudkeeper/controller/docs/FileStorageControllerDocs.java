package br.com.joaojunio.cloudkeeper.controller.docs;

import br.com.joaojunio.cloudkeeper.data.dto.file.UploadFileResponseDTO;
import br.com.joaojunio.cloudkeeper.model.folderStructure.node.FolderNode;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageControllerDocs {
    UploadFileResponseDTO uploadFile(MultipartFile file, String folderName);
    List<UploadFileResponseDTO> uploadMulitpleFiles(MultipartFile[] files);
    ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request);
    ResponseEntity<FolderNode> createFolder(Long userId ,String folderNameExists, String folderName);
}
