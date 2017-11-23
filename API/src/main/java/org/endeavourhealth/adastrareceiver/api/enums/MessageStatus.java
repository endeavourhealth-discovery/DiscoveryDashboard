package org.endeavourhealth.adastrareceiver.api.enums;

public enum MessageStatus {
    ALL((byte)-1),
    RECEIVED((byte)0),
    PROCESSED((byte)1),
    ERROR((byte)2);


    private byte messageStatus;

    MessageStatus(byte messageStatus) {
        this.messageStatus = messageStatus;
    }

    public byte getMessageStatus() {
        return messageStatus;
    }
}
