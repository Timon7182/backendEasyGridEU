package com.timon.rhouse.service;

import com.timon.rhouse.domain.File;
import com.timon.rhouse.domain.Flat;
import com.timon.rhouse.domain.Landlord;
import com.timon.rhouse.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class FileService {

    @Autowired
    private FileRepository fileDBRepository;

    public File store(MultipartFile file, Flat flat) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File FileDB = new File(fileName, file.getContentType(), file.getBytes(),flat);

        return fileDBRepository.save(FileDB);
    }

    public File getFile(Long id) {
        return fileDBRepository.findById(id).get();
    }

    public Stream<File> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }
}
