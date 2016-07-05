package com.opalinskiy.ostap.pastebin.interactor.models;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;
import java.util.List;


public class PasteList {

    public PasteList (){
        pasteList = new LinkedList<>();
    }
    @SerializedName("paste")
    private List<Paste> pasteList;

    public List<Paste> getPasteList() {
        return pasteList;
    }

    public void addPaste(Paste paste){
        this.pasteList.add(paste);
    }
}
