package com.timon.rhouse.bean;

import com.timon.rhouse.domain.File;
import com.timon.rhouse.domain.Flat;
import com.timon.rhouse.repository.FileRepository;
import com.timon.rhouse.repository.FlatRepository;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;





import org.springframework.stereotype.Component;


import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



@ApplicationScoped
@Component(value = "fileJsfService")
@ELBeanName(value = "fileJsfService")
public class FileBean {



    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private FlatRepository flatRepository;


    public StreamedContent getImage() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        if(context.getCurrentPhaseId()==PhaseId.RENDER_RESPONSE){
            return new DefaultStreamedContent();
        }else{
            String flatid = context.getExternalContext().getRequestParameterMap().get("fileId");
            File file = fileRepository.findById(Long.valueOf(flatid)).get();
            byte[] data=file.getData();

//            return new DefaultStreamedContent(new ByteArrayInputStream(data));
            return DefaultStreamedContent.builder().contentType(file.getType()).name(file.getFileName())
                    .stream( ()->new ByteArrayInputStream(data)).build();

        }

            // So, browser is requesting the image. Return a real StreamedContent with the image
    }

    public List<String> getImages(long id){
        Flat flat = flatRepository.findById(id).get();
        List<File> files = flat.getFiles();
        List<String> ids = new ArrayList<String>();
        for(File file:files)
            ids.add(file.getId().toString());
        return ids;
    }





}
