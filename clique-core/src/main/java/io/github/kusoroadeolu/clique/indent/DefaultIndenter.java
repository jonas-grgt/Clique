package io.github.kusoroadeolu.clique.indent;

import io.github.kusoroadeolu.clique.config.IndenterConfiguration;
import io.github.kusoroadeolu.clique.core.utils.Constants;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Objects;

import static io.github.kusoroadeolu.clique.core.utils.Constants.EMPTY;
import static io.github.kusoroadeolu.clique.core.utils.StringUtils.clearStringBuilder;

public class DefaultIndenter implements Indenter{
    private final Deque<Indent> indents;
    private String currentFlag;
    private int currentLevel; //The current indent level
    private final StringBuilder sb;
    private final IndenterConfiguration configuration;

    public DefaultIndenter(){
        this(IndenterConfiguration.DEFAULT);
    }

    public DefaultIndenter(IndenterConfiguration indenterConfiguration){
        this.indents = new ArrayDeque<>();
        this.sb = new StringBuilder();
        this.configuration = indenterConfiguration;
        this.currentFlag = String.valueOf(this.configuration.getDefaultFlag());
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


    public String get(){
        return this.sb.toString();
    }

    public void flush() {
        clearStringBuilder(this.sb);
        this.resetLevel();
    }

    private String parseString(String str){
        if (this.configuration.getParser() != null){
            str = this.configuration.getParser().parse(str);
        }

        return str;
    }

    public Indenter resetLevel(){
        this.currentLevel = 0;
        return this;
    }


    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        DefaultIndenter indenter = (DefaultIndenter) object;
        return currentLevel == indenter.currentLevel && indents.equals(indenter.indents) && currentFlag.equals(indenter.currentFlag) && sb.equals(indenter.sb) && configuration.equals(indenter.configuration);
    }

    public int hashCode() {
        return Objects.hash(indents, currentFlag, currentLevel, sb, configuration);
    }

    @Override
    public String toString() {
        return "Indenter[" +
                "indents=" + indents +
                ", currentFlag='" + currentFlag + '\'' +
                ", currentLevel=" + currentLevel +
                ", sb=" + sb +
                ", configuration=" + configuration +
                ']';
    }
}
