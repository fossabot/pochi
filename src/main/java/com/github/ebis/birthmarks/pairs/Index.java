package com.github.ebis.birthmarks.pairs;

class Index{
    private int index;

    public Index(int index){
        this.index = index;
    }

    public int index(){
        int i = index;
        index++;
        return i;
    }
}
