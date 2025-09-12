package br.com.joaojunio.cloudkeeper.controller.docs;

import br.com.joaojunio.cloudkeeper.data.dto.file.DeleteFileResponseDTO;
import br.com.joaojunio.cloudkeeper.data.dto.file.MoveFileResponseDTO;
import br.com.joaojunio.cloudkeeper.data.dto.file.UploadFileResponseDTO;
import br.com.joaojunio.cloudkeeper.mediatypes.MediaTypes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageControllerDocs {

    @Operation(
        tags = {"File"},
        summary = "Upload a one file",
        description = "Uploading one file",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Success",
                content = @Content(schema = @Schema(implementation = UploadFileResponseDTO.class))
            ),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
        }
    )
    ResponseEntity<UploadFileResponseDTO> uploadFile(Long id, MultipartFile file, String folderName);

    @Operation(
        tags = {"File"},
        summary = "Download a one File",
        description = "Downloading one File",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Success",
                content = {
                    @Content(
                        mediaType = MediaTypes.APPLICATION_PDF_VALUE,
                        schema = @Schema(implementation = Resource.class)),
                    @Content(
                        mediaType = MediaTypes.APPLICATION_CSV_VALUE,
                        schema = @Schema(implementation = Resource.class)),
                    @Content(
                        mediaType = MediaTypes.APPLICATION_XLSX_VALUE,
                        schema = @Schema(implementation = Resource.class)),
                    @Content(
                        mediaType = MediaTypes.APPLICATION_JPEG_VALUE,
                        schema = @Schema(implementation = Resource.class)),
                    @Content(
                        mediaType = MediaTypes.APPLICATION_PNG_VALUE,
                        schema = @Schema(implementation = Resource.class)),
                    @Content(
                        mediaType = MediaTypes.APPLICATION_GIF_VALUE,
                        schema = @Schema(implementation = Resource.class)),
                    @Content(
                        mediaType = MediaTypes.APPLICATION_WEBP_VALUE,
                        schema = @Schema(implementation = Resource.class)),
                }
            ),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
        }
    )
    ResponseEntity<Resource> downloadFile(String type, String fileId) throws Exception;

    @Operation(
        tags = {"File"},
        summary = "Move file a other folder",
        description = "Moving file a other folder",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Success",
                content = @Content(schema = @Schema(implementation = MoveFileResponseDTO.class))
            ),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
        }
    )
    ResponseEntity<MoveFileResponseDTO> moveFileToOtherFolder(Long userId, String fileId, String nameFolder);

    @Operation(
        tags = {"File"},
        summary = "Delete a one File",
        description = "Delete a one File",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Success",
                content = @Content(schema = @Schema(implementation = DeleteFileResponseDTO.class))
            ),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
        }
    )
    ResponseEntity<DeleteFileResponseDTO> delete(Long userId, String fileId);
}
