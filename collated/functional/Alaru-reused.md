# Alaru-reused
###### \java\seedu\address\commons\util\HashUtil.java
``` java
    private static String toHex(byte[] hashValue) {
        StringBuilder sb = new StringBuilder();
        for (byte b : hashValue) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

}
```
