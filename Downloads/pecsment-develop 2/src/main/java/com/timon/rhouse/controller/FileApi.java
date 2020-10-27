package com.timon.rhouse.controller;

import com.timon.rhouse.domain.File;
import com.timon.rhouse.domain.Flat;
import com.timon.rhouse.repository.FlatRepository;
import com.timon.rhouse.service.FileService;
import com.timon.rhouse.service.message.ResponseFile;
import com.timon.rhouse.service.message.ResponseMessage;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FileApi {

    @Autowired
    private FileService storageService;

    @Autowired
    private FlatRepository flatRepository;
    public FileApi() {
    }

    @PostMapping("/upload")
    public void uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("id") Long id) throws Exception {

        long f= file.getSize();
        if(f>500000){
            throw new Exception("Too large file");

        }else{
            Flat flat= flatRepository.findById(id).get();
            try {
                storageService.store(file,flat);

//            message = "Uploaded the file successfully: " + file.getOriginalFilename();
//            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
//            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
                throw  new Exception(e);
            }
        }

    }

//    @GetMapping("/files")
//    public ResponseEntity<List<ResponseFile>> getListFiles() {
//        List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
//            String fileDownloadUri = ServletUriComponentsBuilder
//                    .fromCurrentContextPath()
//                    .path("/files/")
//                    .path(dbFile.getId().toString())
//                    .toUriString();
//
//            return new ResponseFile(
//                    dbFile.getFileName(),
//                    fileDownloadUri,
//                    dbFile.getType(),
//                    dbFile.getData().length);
//        }).collect(Collectors.toList());
//
//        return ResponseEntity.status(HttpStatus.OK).body(files);
//    }
//
//    @GetMapping("/fil")
//    public ResponseEntity<StreamedContent> getFile() {
////        File fileDB = storageService.getFile(id);
////
////        return ResponseEntity.ok()
////                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getFileName() + "\"")
////                .body(fileDB.getData());
//
//       File img = storageService.getFile((long)10);
//        InputStream stream = new ByteArrayInputStream(img.getData());
//        StreamedContent streamedContent = new DefaultStreamedContent(stream, img.getType());
//        return  new ResponseEntity<>(streamedContent,HttpStatus.OK);
//    }
}
