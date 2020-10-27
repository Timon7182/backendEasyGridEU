package com.timon.rhouse.domain;

import javax.persistence.*;


@Entity(name = File.TBL_NAME)
public class File extends AbstractEntity<Long>{
    public static final String TBL_NAME="images";

    public static final String FLD_FILENAME="fileName";
    public static final String FLD_DATA="data";
    public static final String FLD_TYPE="type";

    public static final String FLD_FLAT="flats";



    @Column(name = FLD_FILENAME,unique = false,nullable = true)
    private String fileName;

    @Column(name = FLD_TYPE,unique = false,nullable = true)
    private String type;

    @Column(name = FLD_DATA,unique = false,nullable = true)
    private byte[] data;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = FLD_FLAT)
    private Flat flats;


    public File(){

    }

    public File(String fileName,String type,byte[] data,Flat flat){
        this.fileName=fileName;
        this.type=type;
        this.data=data;
        this.flats=flat;
    }

    public Flat getFlats() {
        return flats;
    }

    public void setFlats(Flat flats) {
        this.flats = flats;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
