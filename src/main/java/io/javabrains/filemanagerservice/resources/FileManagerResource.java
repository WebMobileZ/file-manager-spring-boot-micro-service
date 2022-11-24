package io.javabrains.filemanagerservice.resources;


import io.javabrains.filemanagerservice.models.Path;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FileManagerResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    WebClient.Builder webClientBuilder;

    @RequestMapping("/createZipFile")
    public ResponseEntity<String> createZipFile(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Path  createPath1  = new Path();
        createPath1.setPath("src/main/resources/files/test/child2.docx");

        Path  createPath2  = new Path();
        createPath2.setPath("src/main/resources/files/test1.docx");

        Path  createPath3  = new Path();
        createPath3.setPath("src/main/resources/files/test2.txt");
        final List<Path> srcFiles =  Arrays.asList(createPath1,createPath2,createPath3);
        HttpEntity<List<Path>> httpEntity = new HttpEntity<List<Path>>(srcFiles, headers);

        ResponseEntity<String> zipFileConvertorResponse = restTemplate.exchange("http://localhost:8082/api/zipConverter", HttpMethod.POST, httpEntity,String.class);

        return new ResponseEntity<String>(zipFileConvertorResponse.getBody(), HttpStatus.CREATED);


    }
}
