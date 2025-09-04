package br.com.joaojunio.cloudkeeper.service;

import br.com.joaojunio.cloudkeeper.config.B2Properties;
import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.contentHandlers.B2ContentSink;
import com.backblaze.b2.client.contentSources.B2ByteArrayContentSource;
import com.backblaze.b2.client.contentSources.B2ContentTypes;
import com.backblaze.b2.client.exceptions.B2Exception;
import com.backblaze.b2.client.structures.B2Bucket;
import com.backblaze.b2.client.structures.B2FileVersion;
import com.backblaze.b2.client.structures.B2ListFileNamesRequest;
import com.backblaze.b2.client.structures.B2UploadFileRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class CloudFileStorageService {

    private final Logger logger = LoggerFactory.getLogger(CloudFileStorageService.class.getName());

    private final B2StorageClient client;
    private final B2Properties props;

    public CloudFileStorageService(B2StorageClient client, B2Properties props) {
        this.client = client;
        this.props = props;
    }

    private B2Bucket getBucket() throws B2Exception {
        final B2Bucket bucket = client.getBucketOrNullByName(props.getBucketName());
        if (bucket == null) {
            throw new IllegalArgumentException("Bucket not found : " + props.getBucketName());
        }
        return bucket;
    }

    public B2FileVersion uploadFile(MultipartFile file) throws Exception {
        logger.info("Saving in Cloud");

        if (file == null) {
            throw new RuntimeException("Could not to cloud, file is null!");
        }

        final B2Bucket bucket = getBucket();
        final String bucketId = bucket.getBucketId();
        final B2ByteArrayContentSource source = (B2ByteArrayContentSource) B2ByteArrayContentSource.build(file.getBytes());
        final B2UploadFileRequest request = B2UploadFileRequest
            .builder(
                bucketId,
                file.getOriginalFilename(),
                B2ContentTypes.B2_AUTO,
                source
            )
            .build();
        return client.uploadSmallFile(request);
    }

    public InputStream downloadFile(String fileName) throws Exception {
        B2Bucket bucket = getBucket();
        B2FileVersion fileVersion = client.getFileInfoByName(bucket.getBucketName(), fileName);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        B2ContentSink sink = (headers, inputStream) -> {
            try (InputStream in = inputStream) {
                in.transferTo(outputStream);
            }
        };

        client.downloadById(fileVersion.getFileId(), sink);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    public List<B2FileVersion> listFiles(int maxFiles) throws Exception {
        B2Bucket bucket = getBucket();

        B2ListFileNamesRequest request = B2ListFileNamesRequest
            .builder(bucket.getBucketId())
            .setMaxFileCount(maxFiles)
            .build();

        var iterable = client.fileNames(request);
        List<B2FileVersion> result = new ArrayList<>();
        for (B2FileVersion fv : iterable) {
            result.add(fv);
        }
        return result;
    }

    public void deleteFile(String fileName) throws B2Exception {
        B2Bucket bucket = getBucket();
        B2FileVersion file = client.getFileInfoByName(bucket.getBucketName(), fileName);
        client.deleteFileVersion(file);
    }
}
