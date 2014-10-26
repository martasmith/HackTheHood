package com.codepath.hackthehood.util;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by thomasharte on 26/10/2014.
 */
public abstract class ParseIterator implements Iterator<ParseObject[]> {
    private ParseObject[] nextObject;
    protected void setNextObject(ParseObject object) {
        ParseObject[] array = {object};
        nextObject = array;
    }
    protected boolean considerNextObject(ParseObject object) {
        if(!object.isDataAvailable()) {
            setNextObject(object);
            return true;
        }
        return false;
    }
    protected boolean considerNextObjects(ParseObject[] objects) {
        ArrayList<ParseObject> objectsWithoutData = new ArrayList<ParseObject>();
        for(ParseObject object: objects) {
            if(!object.isDataAvailable())
                objectsWithoutData.add(object);
        }

        if(objectsWithoutData.size() > 0) {
            nextObject = objectsWithoutData.toArray(new ParseObject[objectsWithoutData.size()]);
            return true;
        }

        return false;
    }
    protected boolean considerNextObjects(List<ParseObject> objects) {
        return considerNextObjects(objects.toArray(new ParseObject[objects.size()]));
    }
    private ParseObject[] nextObject() {
        if(nextObject != null) return nextObject;
        findNextObject();
        return nextObject;
    }

    protected abstract void findNextObject();

    @Override
    public boolean hasNext() {
        return nextObject() != null;
    }

    @Override
    public ParseObject[] next() {
        ParseObject[] next = nextObject();
        nextObject = null;
        return next;
    }

    @Override
    public void remove() {
    }
}
