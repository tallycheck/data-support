package com.taoswork.tallycheck.authority.provider.onmongo.common.domain.resource;

import com.taoswork.tallycheck.authority.domain.IClassifiable;
import com.taoswork.tallycheck.datadomain.base.entity.CollectionField;
import com.taoswork.tallycheck.datadomain.base.entity.CollectionMode;
import com.taoswork.tallycheck.datadomain.onmongo.AbstractDocument;
import org.apache.commons.lang3.RandomStringUtils;
import org.mongodb.morphia.annotations.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Gao Yuan on 2016/2/10.
 */
@Entity
public class XFile extends AbstractDocument implements IClassifiable {
    private String title;
    private String content;

    @CollectionField(mode = CollectionMode.Basic)
    private List<String> classifications = new ArrayList<String>();
    public static final String FN_TAGS = "classifications";

    public XFile() {
        int length = (new Random()).nextInt(50) + 50;
        content = RandomStringUtils.randomAlphabetic(length);
        Class<Integer> xls = Integer.class;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void clearClassifications() {
        this.classifications.clear();
    }

    @Override
    public void addClassification(String classification) {
        this.classifications.add(classification);
    }

    @Override
    public void addClassifications(String... classifications) {
        for(String tag : classifications) {
            this.classifications.add(tag);
        }
    }

    @Override
    public List<String> getClassifications() {
        return classifications;
    }

    @Override
    public void setClassifications(List<String> classifications) {
        this.classifications = classifications;
    }

    @Override
    public String getInstanceName() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        XFile xFile = (XFile) o;

        if (title != null ? !title.equals(xFile.title) : xFile.title != null) return false;
        if (content != null ? !content.equals(xFile.content) : xFile.content != null) return false;
        return !(classifications != null ? !classifications.equals(xFile.classifications) : xFile.classifications != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (classifications != null ? classifications.hashCode() : 0);
        return result;
    }
}
