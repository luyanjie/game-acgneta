package com.tongbu.game.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author jokin
 * @date 2018/12/7 17:26
 *
 * 加上了@Document注解之后，默认情况下这个实体中所有的属性都会被建立索引、并且分词
 */
@Document(indexName = "anima", type = "doc", createIndex = false)
public class Animations {
    @Id
    private int id;
    private String name;
    private String briefintro;
    private String namepe;
    private double score;
    private String jpname;
    private String updatetime;
    private int episodetype;
    private String inserttime;
    private String author;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBriefintro() {
        return briefintro;
    }

    public void setBriefintro(String briefintro) {
        this.briefintro = briefintro;
    }

    public String getNamepe() {
        return namepe;
    }

    public void setNamepe(String namepe) {
        this.namepe = namepe;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getJpname() {
        return jpname;
    }

    public void setJpname(String jpname) {
        this.jpname = jpname;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public int getEpisodetype() {
        return episodetype;
    }

    public void setEpisodetype(int episodetype) {
        this.episodetype = episodetype;
    }

    public String getInserttime() {
        return inserttime;
    }

    public void setInserttime(String inserttime) {
        this.inserttime = inserttime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


}
