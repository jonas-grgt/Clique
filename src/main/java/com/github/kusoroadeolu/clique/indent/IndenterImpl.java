package com.github.kusoroadeolu.clique.indent;

import com.github.kusoroadeolu.clique.config.IndenterConfiguration;
import com.github.kusoroadeolu.clique.core.exceptions.DeprecatedMethodException;
import com.github.kusoroadeolu.clique.core.utils.Constants;

import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

import static com.github.kusoroadeolu.clique.core.utils.Constants.EMPTY;
import static com.github.kusoroadeolu.clique.core.utils.StringUtils.clearStringBuilder;

public class IndenterImpl implements Indenter{
    private final Deque<Indent> indents;
    private String currentFlag;
    private int currentLevel; //The current indent level
    private final StringBuilder sb;
    private final IndenterConfiguration configuration;

    public IndenterImpl(){
        this(IndenterConfiguration.immutableBuilder().build());
    }

    public IndenterImpl(IndenterConfiguration indenterConfiguration){
        this.indents = new ArrayDeque<>();
        this.sb = new StringBuilder();
        this.configuration = indenterConfiguration;
        this.currentFlag = String.valueOf(this.configuration.getDefaultFlag());
    }

    public Indenter configuration(IndenterConfiguration configuration){
        throw new DeprecatedMethodException("Deprecated method. Use constructor configurations instead");

    }

    public Indenter indent(int level, String flag){

        if(level < 0) throw new IllegalArgumentException("Level cannot be less than 0");

        this.currentLevel += level;
        flag = parseString(flag);

        this.currentFlag = flag;
        this.indents.push(new Indent(this.currentFlag, this.currentLevel));
        return this;
    }

    public Indenter indent(int level){
        return this.indent(level, Constants.BLANK);
    }

    public Indenter indent(int level, Flag flag){
        return this.indent(level, flag.flag()) ;
    }

    public Indenter indent(String flag){
        if(this.indents.isEmpty()) return this.indent(this.configuration.getIndentLevel(), EMPTY);
        return this.indent(this.configuration.getIndentLevel(), flag);
    }

    public Indenter indent(Flag flag){
        return this.indent(flag.flag()) ;
    }

    public Indenter indent(){
        return this.indent(Constants.BLANK);
    }

    public Indenter add(String... arr){
        for (String s : arr){
            this.add(s);
        }

        return this;
    }

    public Indenter add(Collection<String> list){
        list.forEach(this::add);
        return this;
    }

    public Indenter add(String str){
        if (this.currentLevel == 0){ //If the current level hasn't been set, indent the str
            this.indent();
        }

        if(!this.indents.isEmpty()){
            this.sb.append(Constants.BLANK.repeat(this.currentLevel - 1));
        }

        this.sb.append(this.currentFlag)
                .append(parseString(str))
                .append(Constants.NEWLINE);
        return this;
    }

    public Indenter add(Object object){
        return this.add(object.toString());
    }

    public Indenter unindent(){
        if(this.indents.isEmpty()){
            return this;
        }

        this.indents.pop(); //Remove the top flag

        if(this.indents.isEmpty()){
            this.currentFlag = String.valueOf(this.configuration.getDefaultFlag());
            this.currentLevel = 0;
            return this;
        }

        this.currentFlag = this.indents.peek().flag(); //Set the current flag to this
        this.currentLevel = this.indents.peek().level(); //Set the current level to this
        return this;
    }

    public void print(PrintStream stream){
        stream.println(this.sb);
    }

    public String get(){
        return this.sb.toString();
    }


    public void clear(){
        clearStringBuilder(this.sb);
    }

    public void flush() {
        clearStringBuilder(this.sb);
        this.resetLevel();
    }

    public String parseString(String str){
        if (this.configuration.getParser() != null){
            str = this.configuration.getParser().parse(str);
        }
        return str;
    }

    public Indenter resetLevel(){
        this.currentLevel = 0;
        return this;
    }
}
