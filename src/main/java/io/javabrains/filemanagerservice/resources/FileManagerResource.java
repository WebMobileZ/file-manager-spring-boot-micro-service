package io.javabrains.filemanagerservice.resources;


import io.javabrains.filemanagerservice.models.Path;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FileManagerResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    WebClient.Builder webClientBuilder;
    @RequestMapping("/sendFileToZipFileReceiver")
    public ResponseEntity<String> sendFileToZipFileReceiver() throws Exception {
        try {
            String filePath = "C:\\zipfiles\\zipfiles2.zip";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);


            MultiValueMap<String, Object> body
                    = new LinkedMultiValueMap<>();
            body.add("file", getTestFile(filePath));


            HttpEntity<MultiValueMap<String, Object>> requestEntity
                    = new HttpEntity<>(body, headers);


            String serverUrl = "http://localhost:8082/api/zipReceiver/";

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate
                    .postForEntity(serverUrl, requestEntity, String.class);

        } catch (Exception exception) {
            return new ResponseEntity<String>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
            return new ResponseEntity<String>("File Sent Success!", HttpStatus.CREATED);
    }

    private File getTestFile(String path) {
        File file = new File(path);
        return file;
    }

    @RequestMapping("/createZipFile")
    public ResponseEntity<String> createZipFile() throws Exception {
        try {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Path  createPath1  = new Path();
        createPath1.setPath("src/main/resources/files/test/child2.docx");

        Path  createPath2  = new Path();
        createPath2.setPath("src/main/resources/files/test1.docx");

        Path  createPath3  = new Path();
        createPath3.setPath("C:\\Users\\WebMobileZ\\Downloads\\event.png");

        final List<Path> srcFiles =  Arrays.asList(createPath1,createPath2,createPath3);

        HttpEntity<List<Path>> httpEntity = new HttpEntity<List<Path>>(srcFiles, headers);

        String serverUrl ="http://localhost:8082/api/zipConverter";
        ResponseEntity<String> zipFileConvertorResponse = restTemplate.exchange(serverUrl, HttpMethod.POST, httpEntity,String.class);


        } catch (Exception exception) {
            return new ResponseEntity<String>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Files Zipped successfully!", HttpStatus.CREATED);
    }
}
