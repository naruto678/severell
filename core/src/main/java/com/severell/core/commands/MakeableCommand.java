package com.severell.core.commands;

import com.squareup.javapoet.JavaFile;

import java.io.IOException;
import java.io.Writer;

public abstract class MakeableCommand extends Command{

    protected Writer writer;

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    public void make(JavaFile javaFile) {
        try {
            javaFile.writeTo(writer);
        } catch (IOException e) {
            System.out.println("Failed to create migration");
        }
    }
}
