package tables.abstracttable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WidthAwareList {
    private int longest;
    private final List<String> list;

    public WidthAwareList(){
        this.longest = 0;
        this.list = new ArrayList<>();
    }

    public WidthAwareList(WidthAwareList list){
        this();
        this.addAll(list);
    }


    public void add(String s){
        this.validateString(s);
        this.list.add(s);
    }

    public void addFirst(String s){
        this.validateString(s);
        this.list.addFirst(s);
    }

    public void validateString(String s){
        if(s == null) s = "";

        final int len = s.length();

        if(len > this.longest){
            this.longest = len;
        }
    }

    public String get(int pos){
        return this.list.get(pos);
    }

    public boolean remove(String s){

        if(s == null) return false;

        final int len = s.length();
        this.list.remove(s);

        if(this.list.isEmpty()){
            this.longest = 0;
            return true;
        }

        if(len == this.longest){
            this.longest = this.calculateLongest();
        }

        return true;
    }

    public int longest() {
        return this.longest;
    }

    public List<String> list() {
        return this.list;
    }

    public int size(){
        return this.list.size();
    }

    private int calculateLongest(){
        return this.list.stream()
                .mapToInt(String::length)
                .max()
                .orElse(0);
    }

    private void addAll(WidthAwareList list) {
        for(int i = 0; i < list.list().size(); i++){
            this.add(list.list().get(i));
        }
    }
}
