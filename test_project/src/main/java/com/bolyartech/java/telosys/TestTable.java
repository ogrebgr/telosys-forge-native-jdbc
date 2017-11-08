package com.bolyartech.java.telosys;

import java.time.LocalDateTime;


public interface TestTable {
     long getId();
     String getMyText();
     String getMyString();
     boolean getMyBool();
     int getMyInt();
     int getMyLong();
     float getMyFloat();
     double getMyDouble();
     LocalDateTime getTsDt();
}