package TS;

import java.util.LinkedList;

public class TabuList extends LinkedList<Solution> {

    private int maxSize;

    public TabuList(int maxSize) {
        super();
        this.maxSize = maxSize;
    }

    public void insert(Solution toAdd) {
        if(this.size() >= maxSize)
            this.removeFirst();
        this.addLast(toAdd);
    }

    public boolean isForbidden(Solution neighbor) {
        return this.contains(neighbor);
    }
}
