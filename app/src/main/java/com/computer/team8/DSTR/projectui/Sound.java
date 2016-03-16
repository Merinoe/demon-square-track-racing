package com.computer.team8.DSTR.projectui;

/**
 * Created by Catherine on 3/8/2016.
 */
public interface Sound {

    public void addFile(int fileId, String fileName);

    public void play(String fileName);

    public void destroy();
}
