package io.github.kusoroadeolu.clique.tables.structures;

import java.util.ArrayList;
import java.util.List;

public class WidthAwareList {
    private int longest;
    private final List<Cell> list;

    public WidthAwareList(){
        this.longest = 0;
        this.list = new ArrayList<>();
    }


    public void add(Cell c){
        this.updateLongest(c);
        this.list.add(c);
    }

    public void update(int i, Cell c){
        this.updateLongest(c);
        this.list.set(i, c);
    }


    public void updateLongest(Cell c){
        final int len = c.width();
        if(len > this.longest){
            this.longest = len;
        }
    }


    //Gets the styled text from the table
    public String getStyledText(int pos){
        return this.list.get(pos).styledText();
    }

    public String getOriginalText(int pos){
        return this.list.get(pos).text();
    }

    public Cell get(int pos){
        return this.list.get(pos);
    }


    public void remove(Cell c){
        if(c == null) return;

        final int len = c.width();
        this.list.remove(c);

        if(this.list.isEmpty()){
            this.longest = 0;
            return;
        }

        if(len == this.longest){
            this.longest = this.recalculateLongest();
        }

    }

    public int longest() {
        return this.longest;
    }

    //Get the styled text from the list
    public List<String> list() {
        return this.list.stream()
                .map(Cell::styledText)
                .toList();
    }

    public int size(){
        return this.list.size();
    }

    private int recalculateLongest(){
        return this.list.stream()
                .mapToInt(Cell::width)
                .max()
                .orElse(0);
    }

}
