package com.rama;

public class TagLookup {
    private int port;
    private String protocol;
    private String tag;

    public TagLookup(int port, String protocol, String tag) {
        this.port = port;
        this.protocol = protocol;
        this.tag = tag;
    }

    public int getPort() {
        return port;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getTag() {
        return tag;
    }
}

