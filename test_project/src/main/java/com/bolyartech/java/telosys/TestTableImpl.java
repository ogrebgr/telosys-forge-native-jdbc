package com.bolyartech.java.telosys;


import java.time.LocalDateTime;


public class TestTableImpl implements TestTable {
    private final long id;
    private final String myText;
    private final String myString;
    private final boolean myBool;
    private final int myInt;
    private final int myLong;
    private final float myFloat;
    private final double myDouble;
    private final LocalDateTime tsDt;


    public TestTableImpl(
                            long id, 
                            String myText, 
                            String myString, 
                            boolean myBool, 
                            int myInt, 
                            int myLong, 
                            float myFloat, 
                            double myDouble,
                            LocalDateTime tsDt) {
        this.id = id;
        this.myText = myText;
        this.myString = myString;
        this.myBool = myBool;
        this.myInt = myInt;
        this.myLong = myLong;
        this.myFloat = myFloat;
        this.myDouble = myDouble;
        this.tsDt = tsDt;
    }
    

    @Override
    public long getId() {
        return id;
    }


    @Override
    public String getMyText() {
        return myText;
    }


    @Override
    public String getMyString() {
        return myString;
    }


    @Override
    public boolean getMyBool() {
        return myBool;
    }


    @Override
    public int getMyInt() {
        return myInt;
    }


    @Override
    public int getMyLong() {
        return myLong;
    }


    @Override
    public float getMyFloat() {
        return myFloat;
    }


    @Override
    public double getMyDouble() {
        return myDouble;
    }


    @Override
    public LocalDateTime getTsDt() {
        return tsDt;
    }


}